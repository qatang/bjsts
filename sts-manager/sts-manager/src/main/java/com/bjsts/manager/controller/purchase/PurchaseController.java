package com.bjsts.manager.controller.purchase;

import com.bjsts.manager.core.constants.GlobalConstants;
import com.bjsts.manager.core.controller.AbstractController;
import com.bjsts.manager.entity.purchase.PurchaseEntity;
import com.bjsts.manager.form.purchase.PurchaseForm;
import com.bjsts.manager.query.user.UserSearchable;
import com.bjsts.manager.service.purchase.PurchaseService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @author jinsheng
 * @since 2016-04-26 16:56
 */
@Controller
@RequestMapping("/purchase")
@SessionAttributes("purchaseSearchable")
public class PurchaseController extends AbstractController {

    @Autowired
    private PurchaseService purchaseService;

    @RequiresPermissions("arsenal:purchase:list")
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(UserSearchable purchaseSearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable, ModelMap modelMap) {
        List<PurchaseEntity> purchaseEntityList = purchaseService.findAll();
        modelMap.addAttribute("list", purchaseEntityList);
        return "purchase/list";
    }

    @RequiresPermissions("arsenal:purchase:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(@ModelAttribute PurchaseForm purchaseForm, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".purchaseForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        if (Objects.isNull(purchaseForm.getPurchase())) {
            purchaseForm.setPurchase(new PurchaseEntity());
        }
        modelMap.put("action", "create");
        return "purchase/edit";
    }

    @RequiresPermissions("arsenal:purchase:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(PurchaseForm purchaseForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(purchaseForm);
            return "redirect:/purchase/create";
        }
        PurchaseEntity purchaseEntity = purchaseForm.getPurchase();
        purchaseService.save(purchaseEntity);
        return "result";
    }

    @RequiresPermissions("arsenal:purchase:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable Long id, @ModelAttribute PurchaseForm purchaseForm, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".purchaseForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        PurchaseEntity purchaseEntity = purchaseService.get(id);
        if (Objects.isNull(purchaseEntity)) {
            logger.error("修改进货单,未查询[id={}]的进货单信息", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据!");
            return "redirect:/error";
        }
        purchaseForm.setPurchase(purchaseEntity);
        modelMap.put("action", "update");
        return "purchase/edit";
    }

    @RequiresPermissions("arsenal:purchase:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(PurchaseForm purchaseForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(purchaseForm);
            return "redirect:/purchase/update/" + purchaseForm.getPurchase().getId();
        }
        PurchaseEntity purchase = purchaseForm.getPurchase();
        PurchaseEntity purchaseEntity = purchaseService.get(purchase.getId());
        purchaseService.update(purchaseEntity);
        return "result";
    }

    @RequiresPermissions("arsenal:purchase:view")
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable Long id, ModelMap modelMap) {
        modelMap.put("purchase", purchaseService.get(id));
        return "purchase/view";
    }
}
