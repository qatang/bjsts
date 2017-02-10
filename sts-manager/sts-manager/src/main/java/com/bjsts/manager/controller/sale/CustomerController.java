package com.bjsts.manager.controller.sale;

import com.bjsts.manager.core.constants.GlobalConstants;
import com.bjsts.manager.core.controller.AbstractController;
import com.bjsts.manager.entity.sale.CustomerEntity;
import com.bjsts.manager.form.sale.CustomerForm;
import com.bjsts.manager.query.system.UserSearchable;
import com.bjsts.manager.service.sale.CustomerService;
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
 * 客户信息录入
 * @author jinsheng
 * @since 2016-04-26 16:56
 */
@Controller
@RequestMapping("/customer")
@SessionAttributes("customerSearchable")
public class CustomerController extends AbstractController {

    @Autowired
    private CustomerService customerService;

    @RequiresPermissions("sts:customer:list")
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(UserSearchable customerSearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable, ModelMap modelMap) {
        List<CustomerEntity> customerEntityList = customerService.findAll();
        modelMap.addAttribute("list", customerEntityList);
        return "customer/list";
    }

    @RequiresPermissions("sts:customer:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(@ModelAttribute CustomerForm customerForm, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".customerForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        if (Objects.isNull(customerForm.getCustomer())) {
            customerForm.setCustomer(new CustomerEntity());
        }
        modelMap.put("action", "create");
        return "customer/edit";
    }

    @RequiresPermissions("sts:customer:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(CustomerForm customerForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(customerForm);
            return "redirect:/customer/create";
        }
        CustomerEntity customerEntity = customerForm.getCustomer();
        customerService.save(customerEntity);
        return "result";
    }

    @RequiresPermissions("sts:customer:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable Long id, @ModelAttribute CustomerForm customerForm, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".customerForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        CustomerEntity customerEntity = customerService.get(id);
        if (Objects.isNull(customerEntity)) {
            logger.error("修改客户,未查询[id={}]的客户信息", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据!");
            return "redirect:/error";
        }
        customerForm.setCustomer(customerEntity);
        modelMap.put("action", "update");
        return "customer/edit";
    }

    @RequiresPermissions("sts:customer:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(CustomerForm customerForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(customerForm);
            return "redirect:/customer/update/" + customerForm.getCustomer().getId();
        }
        CustomerEntity customer = customerForm.getCustomer();
        CustomerEntity customerEntity = customerService.get(customer.getId());
        customerEntity.setCompanyName(customer.getCompanyName());
        customerService.update(customerEntity);
        return "result";
    }

    @RequiresPermissions("sts:customer:view")
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable Long id, ModelMap modelMap) {
        modelMap.put("customer", customerService.get(id));
        return "customer/view";
    }
}
