package com.bjsts.manager.controller.sale;

import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.manager.core.constants.GlobalConstants;
import com.bjsts.manager.core.controller.AbstractController;
import com.bjsts.manager.entity.document.DocumentEntity;
import com.bjsts.manager.entity.sale.PlanEntity;
import com.bjsts.manager.entity.sale.PlanTraceEntity;
import com.bjsts.manager.entity.user.UserEntity;
import com.bjsts.manager.enums.sale.PlanStatus;
import com.bjsts.manager.form.sale.SaleItemForm;
import com.bjsts.manager.query.sale.SaleItemSearchable;
import com.bjsts.manager.service.document.DocumentService;
import com.bjsts.manager.service.sale.ProductOrderService;
import com.bjsts.manager.service.sale.SaleItemService;
import com.google.common.collect.Lists;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;

/**
 * 项目追踪管理
 * @author wangzhiliang
 */
@Controller
@RequestMapping("/saleItem")
@SessionAttributes("saleItemSearchable")
public class SaleItemController extends AbstractController {

    @Autowired
    private ProductOrderService productOrderService;

    @Autowired
    private SaleItemService saleItemService;

    @Autowired
    private DocumentService documentService;

    @ModelAttribute("planStatusList")
    public List<PlanStatus> getPlanStatusList() {
        return PlanStatus.list();
    }

    @RequiresPermissions("sts:saleItem:list")
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(SaleItemSearchable saleItemSearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable, ModelMap modelMap) {
        ApiResponse<PlanEntity> apiResponse = productOrderService.findAll(saleItemSearchable, pageable);
        Page<PlanEntity> page = new PageImpl<>(Lists.newArrayList(apiResponse.getPagedData()), pageable, apiResponse.getTotal());
        modelMap.addAttribute("page", page);
        return "sale/saleItem/list";
    }

    @RequiresPermissions("sts:saleItem:list")
    @RequestMapping(value = "/list/{planNo}", method = {RequestMethod.GET, RequestMethod.POST})
    public String listTrace(@PathVariable String planNo, ModelMap modelMap) {
        List<PlanTraceEntity> planTraceEntityList = saleItemService.findByPlanNo(planNo);
        modelMap.addAttribute("list", planTraceEntityList);
        return "sale/saleItem/listTrace";
    }

    @RequiresPermissions("sts:saleItem:create")
    @RequestMapping(value = "/create/{planNo}", method = RequestMethod.GET)
    public String create(@PathVariable String planNo, @ModelAttribute SaleItemForm saleItemForm, ModelMap modelMap, RedirectAttributes redirectAttributes) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".saleItemForm"), modelMap.get(BINDING_RESULT_KEY));
        }

        PlanEntity planEntity = productOrderService.findByPlanNo(planNo);

        if (planEntity == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据!");
            return "redirect:/error";
        } else {
            modelMap.addAttribute("productOrder", planEntity);

            Long documentId = planEntity.getDocumentId();
            if (documentId != null) {
                DocumentEntity documentEntity = documentService.get(documentId);
                modelMap.addAttribute("customerFileUrl", documentEntity.getUrl());
            }

            Long quoteFileId = planEntity.getQuoteFileId();
            if (quoteFileId != null) {
                DocumentEntity documentEntity = documentService.get(quoteFileId);
                modelMap.addAttribute("quoteFileUrl", documentEntity.getUrl());
            }
        }

        if (Objects.isNull(saleItemForm.getSaleItem())) {
            PlanTraceEntity planTraceEntity = new PlanTraceEntity();
            planTraceEntity.setPlanNo(planNo);
            saleItemForm.setSaleItem(planTraceEntity);
        }
        modelMap.put("action", "create");
        return "sale/saleItem/edit";
    }

    @RequiresPermissions("sts:saleItem:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(SaleItemForm saleItemForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(saleItemForm);
            return "redirect:/saleItem/create";
        }
        PlanEntity planEntity = saleItemForm.getProductOrder();
        PlanEntity db = productOrderService.get(planEntity.getId());
        db.setPlanStatus(planEntity.getPlanStatus());
        productOrderService.save(db);

        PlanTraceEntity planTraceEntity = saleItemForm.getSaleItem();
        planTraceEntity.setPlanNo(db.getPlanNo());

        UserEntity userInfo = (UserEntity) SecurityUtils.getSubject().getPrincipal();
        planTraceEntity.setUserId(userInfo.getId());
        planTraceEntity.setRealName(userInfo.getRealName());
        saleItemService.save(planTraceEntity);
        return "result";
    }

    /*@RequiresPermissions("sts:saleItem:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable Long id, @ModelAttribute SaleItemForm saleItemForm, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".saleItemForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        PlanTraceEntity planTraceEntity = saleItemService.get(id);
        if (Objects.isNull(planTraceEntity)) {
            logger.error("修改项目,未查询[id={}]的项目信息", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据!");
            return "redirect:/error";
        }
        saleItemForm.setSaleItem(planTraceEntity);
        modelMap.put("action", "update");
        return "sale/saleItem/edit";
    }

    @RequiresPermissions("sts:saleItem:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(SaleItemForm saleItemForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(saleItemForm);
            return "redirect:/saleItem/update/" + saleItemForm.getSaleItem().getId();
        }
        PlanTraceEntity saleItem = saleItemForm.getSaleItem();
        PlanTraceEntity planTraceEntity = saleItemService.get(saleItem.getId());
        //planTraceEntity.setName(saleItem.getName());
        saleItemService.update(planTraceEntity);
        return "result";
    }*/
}
