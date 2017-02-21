package com.bjsts.manager.controller.normalPurchase;

import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.core.util.CoreMathUtils;
import com.bjsts.manager.core.constants.GlobalConstants;
import com.bjsts.manager.core.controller.AbstractController;
import com.bjsts.manager.entity.purchase.NormalPurchaseEntity;
import com.bjsts.manager.form.purchase.NormalPurchaseForm;
import com.bjsts.manager.query.purchase.NormalPurchaseSearchable;
import com.bjsts.manager.service.purchase.NormalPurchaseService;
import com.google.common.collect.Lists;
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

import java.util.Objects;

/**
 * @author jinsheng
 * @since 2016-04-26 16:56
 */
@Controller
@RequestMapping("/normalPurchase")
@SessionAttributes("normalPurchaseSearchable")
public class NormalPurchaseController extends AbstractController {

    @Autowired
    private NormalPurchaseService normalPurchaseService;

    @RequiresPermissions("sts:normalPurchase:list")
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(NormalPurchaseSearchable normalPurchaseSearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable, ModelMap modelMap) {
        ApiResponse<NormalPurchaseEntity> apiResponse = normalPurchaseService.findAll(normalPurchaseSearchable, pageable);
        Page<NormalPurchaseEntity> page = new PageImpl<>(Lists.newArrayList(apiResponse.getPagedData()), pageable, apiResponse.getTotal());
        modelMap.addAttribute("page", page);
        return "purchase/normalPurchase/list";
    }

    @RequiresPermissions("sts:normalPurchase:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(@ModelAttribute NormalPurchaseForm normalPurchaseForm, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".normalPurchaseForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        if (Objects.isNull(normalPurchaseForm.getNormalPurchase())) {
            normalPurchaseForm.setNormalPurchase(new NormalPurchaseEntity());
        }
        modelMap.put("action", "create");
        return "purchase/normalPurchase/edit";
    }

    @RequiresPermissions("sts:normalPurchase:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(NormalPurchaseForm normalPurchaseForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(normalPurchaseForm);
            return "redirect:/normalPurchase/create";
        }
        NormalPurchaseEntity normalPurchaseEntity = normalPurchaseForm.getNormalPurchase();

        Double amount = CoreMathUtils.mul(normalPurchaseForm.getAmount(), 100L);
        normalPurchaseEntity.setAmount(amount.longValue());

        normalPurchaseService.save(normalPurchaseEntity);
        return "result";
    }

    @RequiresPermissions("sts:normalPurchase:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable Long id, @ModelAttribute NormalPurchaseForm normalPurchaseForm, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".normalPurchaseForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        NormalPurchaseEntity normalPurchaseEntity = normalPurchaseService.get(id);
        if (Objects.isNull(normalPurchaseEntity)) {
            logger.error("修改日常采购单,未查询[id={}]的日常采购单信息", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据!");
            return "redirect:/error";
        }

        normalPurchaseForm.setAmount(Double.valueOf(normalPurchaseEntity.getAmount()));

        normalPurchaseForm.setNormalPurchase(normalPurchaseEntity);
        modelMap.put("action", "update");
        return "purchase/normalPurchase/edit";
    }

    @RequiresPermissions("sts:normalPurchase:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(NormalPurchaseForm normalPurchaseForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(normalPurchaseForm);
            return "redirect:/normalPurchase/update/" + normalPurchaseForm.getNormalPurchase().getId();
        }
        NormalPurchaseEntity normalPurchase = normalPurchaseForm.getNormalPurchase();
        NormalPurchaseEntity normalPurchaseEntity = normalPurchaseService.get(normalPurchase.getId());
        normalPurchaseEntity.setName(normalPurchase.getName());
        normalPurchaseEntity.setBuyer(normalPurchase.getBuyer());
        normalPurchaseEntity.setQuantity(normalPurchase.getQuantity());
        Double amount = CoreMathUtils.mul(normalPurchaseForm.getAmount(), 100L);
        normalPurchaseEntity.setAmount(amount.longValue());
        normalPurchaseEntity.setDescription(normalPurchase.getDescription());
        normalPurchaseEntity.setCompany(normalPurchase.getCompany());
        normalPurchaseEntity.setPurchaseTime(normalPurchase.getPurchaseTime());
        normalPurchaseService.save(normalPurchaseEntity);
        
        return "result";
    }

    @RequiresPermissions("sts:normalPurchase:view")
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable Long id, ModelMap modelMap) {
        NormalPurchaseEntity normalPurchaseEntity = normalPurchaseService.get(id);
        modelMap.put("normalPurchase", normalPurchaseEntity);
        return "purchase/normalPurchase/view";
    }

    @RequiresPermissions("sts:normalPurchase:disable")
    @RequestMapping(value = "/disable/{id}", method = RequestMethod.GET)
    public String disable(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        NormalPurchaseEntity normalPurchaseEntity = normalPurchaseService.get(id);
        if (Objects.isNull(normalPurchaseEntity)) {
            logger.error("删除日常采购单,未查询[id={}]的日常采购单", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "未查询[id={"+id+"}]的日常采购单!");
            return "redirect:/error";
        }
        
        normalPurchaseEntity.setValid(EnableDisableStatus.DISABLE);
        normalPurchaseService.update(normalPurchaseEntity);
        return "redirect:/normalPurchase/list";
    }
}
