package com.bjsts.manager.controller.sale;

import com.bjsts.manager.core.constants.GlobalConstants;
import com.bjsts.manager.core.controller.AbstractController;
import com.bjsts.manager.entity.sale.ProductOrderEntity;
import com.bjsts.manager.form.sale.ProductOrderForm;
import com.bjsts.manager.query.user.UserSearchable;
import com.bjsts.manager.service.sale.ProductOrderService;
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
@RequestMapping("/productOrder")
@SessionAttributes("productOrderSearchable")
public class ProductOrderController extends AbstractController {

    @Autowired
    private ProductOrderService productOrderService;

    @RequiresPermissions("arsenal:productOrder:list")
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(UserSearchable productOrderSearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable, ModelMap modelMap) {
        List<ProductOrderEntity> productOrderEntityList = productOrderService.findAll();
        modelMap.addAttribute("list", productOrderEntityList);
        return "productOrder/list";
    }

    @RequiresPermissions("arsenal:productOrder:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(@ModelAttribute ProductOrderForm productOrderForm, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".productOrderForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        if (Objects.isNull(productOrderForm.getProductOrder())) {
            productOrderForm.setProductOrder(new ProductOrderEntity());
        }
        modelMap.put("action", "create");
        return "productOrder/edit";
    }

    @RequiresPermissions("arsenal:productOrder:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(ProductOrderForm productOrderForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(productOrderForm);
            return "redirect:/productOrder/create";
        }
        ProductOrderEntity productOrderEntity = productOrderForm.getProductOrder();
        productOrderService.save(productOrderEntity);
        return "result";
    }

    @RequiresPermissions("arsenal:productOrder:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable Long id, @ModelAttribute ProductOrderForm productOrderForm, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".productOrderForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        ProductOrderEntity productOrderEntity = productOrderService.get(id);
        if (Objects.isNull(productOrderEntity)) {
            logger.error("修改订单,未查询[id={}]的订单信息", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据!");
            return "redirect:/error";
        }
        productOrderForm.setProductOrder(productOrderEntity);
        modelMap.put("action", "update");
        return "productOrder/edit";
    }

    @RequiresPermissions("arsenal:productOrder:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(ProductOrderForm productOrderForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(productOrderForm);
            return "redirect:/productOrder/update/" + productOrderForm.getProductOrder().getId();
        }
        ProductOrderEntity productOrder = productOrderForm.getProductOrder();
        ProductOrderEntity productOrderEntity = productOrderService.get(productOrder.getId());
        productOrderService.update(productOrderEntity);
        return "result";
    }

    @RequiresPermissions("arsenal:productOrder:view")
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable Long id, ModelMap modelMap) {
        modelMap.put("productOrder", productOrderService.get(id));
        return "productOrder/view";
    }
}
