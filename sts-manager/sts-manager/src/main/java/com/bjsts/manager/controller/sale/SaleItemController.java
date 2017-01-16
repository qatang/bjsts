package com.bjsts.manager.controller.sale;

import com.bjsts.manager.core.constants.GlobalConstants;
import com.bjsts.manager.core.controller.AbstractController;
import com.bjsts.manager.entity.sale.PlanTraceEntity;
import com.bjsts.manager.form.sale.SaleItemForm;
import com.bjsts.manager.query.user.UserSearchable;
import com.bjsts.manager.service.sale.SaleItemService;
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
 * 项目管理
 * @author wangzhiliang
 */
@Controller
@RequestMapping("/saleItem")
@SessionAttributes("saleItemSearchable")
public class SaleItemController extends AbstractController {

    @Autowired
    private SaleItemService saleItemService;

    @RequiresPermissions("arsenal:saleItem:list")
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(UserSearchable saleItemSearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable, ModelMap modelMap) {
        List<PlanTraceEntity> planTraceEntityList = saleItemService.findAll();
        modelMap.addAttribute("list", planTraceEntityList);
        return "saleItem/list";
    }

    @RequiresPermissions("arsenal:saleItem:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(@ModelAttribute SaleItemForm saleItemForm, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".saleItemForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        if (Objects.isNull(saleItemForm.getSaleItem())) {
            saleItemForm.setSaleItem(new PlanTraceEntity());
        }
        modelMap.put("action", "create");
        return "saleItem/edit";
    }

    @RequiresPermissions("arsenal:saleItem:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(SaleItemForm saleItemForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(saleItemForm);
            return "redirect:/saleItem/create";
        }
        PlanTraceEntity planTraceEntity = saleItemForm.getSaleItem();
        saleItemService.save(planTraceEntity);
        return "result";
    }

    @RequiresPermissions("arsenal:saleItem:update")
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
        return "saleItem/edit";
    }

    @RequiresPermissions("arsenal:saleItem:update")
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
    }

    @RequiresPermissions("arsenal:saleItem:view")
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable Long id, ModelMap modelMap) {
        modelMap.put("saleItem", saleItemService.get(id));
        return "saleItem/view";
    }
}
