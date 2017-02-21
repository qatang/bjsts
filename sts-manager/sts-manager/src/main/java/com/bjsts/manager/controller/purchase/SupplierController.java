package com.bjsts.manager.controller.purchase;

import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.manager.core.constants.GlobalConstants;
import com.bjsts.manager.core.controller.AbstractController;
import com.bjsts.manager.entity.purchase.SupplierEntity;
import com.bjsts.manager.form.purchase.SupplierForm;
import com.bjsts.manager.query.purchase.SupplierSearchable;
import com.bjsts.manager.service.purchase.SupplierService;
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
@RequestMapping("/supplier")
@SessionAttributes("supplierSearchable")
public class SupplierController extends AbstractController {

    @Autowired
    private SupplierService supplierService;

    @RequiresPermissions("sts:supplier:list")
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(SupplierSearchable supplierSearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable, ModelMap modelMap) {
        ApiResponse<SupplierEntity> apiResponse = supplierService.findAll(supplierSearchable, pageable);
        Page<SupplierEntity> page = new PageImpl<>(Lists.newArrayList(apiResponse.getPagedData()), pageable, apiResponse.getTotal());
        modelMap.addAttribute("page", page);
        return "purchase/supplier/list";
    }

    @RequiresPermissions("sts:supplier:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(@ModelAttribute SupplierForm supplierForm, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".supplierForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        if (Objects.isNull(supplierForm.getSupplier())) {
            supplierForm.setSupplier(new SupplierEntity());
        }
        modelMap.put("action", "create");
        return "purchase/supplier/edit";
    }

    @RequiresPermissions("sts:supplier:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(SupplierForm supplierForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(supplierForm);
            return "redirect:/supplier/create";
        }
        SupplierEntity supplierEntity = supplierForm.getSupplier();

        supplierService.save(supplierEntity);
        return "result";
    }

    @RequiresPermissions("sts:supplier:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable Long id, @ModelAttribute SupplierForm supplierForm, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".supplierForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        SupplierEntity supplierEntity = supplierService.get(id);
        if (Objects.isNull(supplierEntity)) {
            logger.error("修改供应商,未查询[id={}]的供应商信息", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据!");
            return "redirect:/error";
        }

        supplierForm.setSupplier(supplierEntity);
        modelMap.put("action", "update");
        return "purchase/supplier/edit";
    }

    @RequiresPermissions("sts:supplier:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(SupplierForm supplierForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(supplierForm);
            return "redirect:/supplier/update/" + supplierForm.getSupplier().getId();
        }
        SupplierEntity supplier = supplierForm.getSupplier();
        SupplierEntity supplierEntity = supplierService.get(supplier.getId());
        supplierEntity.setCompany(supplier.getCompany());
        supplierEntity.setLinkman(supplier.getLinkman());
        supplierEntity.setContact(supplier.getContact());
        supplierEntity.setProduct(supplier.getProduct());
        supplierService.save(supplierEntity);
        
        return "result";
    }

    @RequiresPermissions("sts:supplier:view")
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable Long id, ModelMap modelMap) {
        SupplierEntity supplierEntity = supplierService.get(id);
        modelMap.put("supplier", supplierEntity);
        return "purchase/supplier/view";
    }

    @RequiresPermissions("sts:supplier:disable")
    @RequestMapping(value = "/disable/{id}", method = RequestMethod.GET)
    public String disable(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        SupplierEntity supplierEntity = supplierService.get(id);
        if (Objects.isNull(supplierEntity)) {
            logger.error("删除供应商,未查询[id={}]的供应商", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "未查询[id={"+id+"}]的供应商!");
            return "redirect:/error";
        }
        
        supplierEntity.setValid(EnableDisableStatus.DISABLE);
        supplierService.update(supplierEntity);
        return "redirect:/supplier/list";
    }
}
