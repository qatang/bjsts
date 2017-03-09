package com.bjsts.manager.controller.sale;

import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.manager.core.constants.GlobalConstants;
import com.bjsts.manager.core.controller.AbstractController;
import com.bjsts.manager.entity.sale.PlanEntity;
import com.bjsts.manager.entity.sale.PlanTraceEntity;
import com.bjsts.manager.enums.sale.PlanStatus;
import com.bjsts.manager.form.sale.SaleItemForm;
import com.bjsts.manager.query.sale.SaleItemSearchable;
import com.bjsts.manager.service.sale.ProductOrderService;
import com.bjsts.manager.service.sale.SaleItemService;
import com.google.common.collect.Lists;
import org.apache.shiro.authz.annotation.Logical;
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

    @ModelAttribute("planStatusList")
    public List<PlanStatus> getPlanStatusList() {
        return PlanStatus.list();
    }

    @RequiresPermissions(value = {"sts:saleItem:list", "sts:saleItem:listAll"}, logical = Logical.OR)
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(SaleItemSearchable saleItemSearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable, ModelMap modelMap) {
        ApiResponse<PlanEntity> apiResponse = productOrderService.findAll(saleItemSearchable, pageable);
        Page<PlanEntity> page = new PageImpl<>(Lists.newArrayList(apiResponse.getPagedData()), pageable, apiResponse.getTotal());
        modelMap.addAttribute("page", page);
        return "sale/saleItem/list";
    }

    @RequiresPermissions("sts:saleItem:create")
    @RequestMapping(value = "/create/{planNo}", method = RequestMethod.GET)
    public String create(@PathVariable String planNo, @ModelAttribute SaleItemForm saleItemForm, ModelMap modelMap, RedirectAttributes redirectAttributes) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".saleItemForm"), modelMap.get(BINDING_RESULT_KEY));
        }

        if (Objects.isNull(saleItemForm.getSaleItem())) {
            PlanTraceEntity planTraceEntity = new PlanTraceEntity();
            planTraceEntity.setPlanNo(planNo);
            saleItemForm.setSaleItem(planTraceEntity);
        }

        PlanEntity planEntity = productOrderService.findByPlanNo(planNo);
        saleItemForm.setProductOrder(planEntity);

        List<PlanTraceEntity> planTraceEntityList = saleItemService.findByPlanNo(planNo);

        modelMap.put("action", "create");
        modelMap.addAttribute("planTraceList", planTraceEntityList);
        return "sale/saleItem/edit";
    }

    @RequiresPermissions("sts:saleItem:view")
    @RequestMapping(value = "/view/{planNo}", method = RequestMethod.GET)
    public String view(@PathVariable String planNo, ModelMap modelMap) {
        List<PlanTraceEntity> planTraceEntityList = saleItemService.findByPlanNo(planNo);
        modelMap.addAttribute("planTraceList", planTraceEntityList);
        return "sale/saleItem/view";
    }

    @RequiresPermissions("sts:saleItem:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(SaleItemForm saleItemForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(saleItemForm);
            return "redirect:/saleItem/create";
        }
        PlanEntity planEntity = saleItemForm.getProductOrder();
        PlanTraceEntity planTraceEntity = saleItemForm.getSaleItem();
        saleItemService.save(planTraceEntity, planEntity);
        return "result";
    }

    @RequiresPermissions("sts:saleItem:disable")
    @RequestMapping(value = "/disable/{id}", method = RequestMethod.GET)
    public String disable(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        PlanTraceEntity planTraceEntity = saleItemService.get(id);
        if (Objects.isNull(planTraceEntity)) {
            logger.error("删除销售跟踪信息,未查询[id={}]的销售跟踪信息", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "未查询[id={"+id+"}]的销售跟踪信息!");
            return "redirect:/error";
        }
        saleItemService.delete(id);
        return "redirect:/saleItem/list/" + planTraceEntity.getPlanNo();
    }
}
