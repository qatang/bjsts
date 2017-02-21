package com.bjsts.manager.controller.stock;

import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.manager.core.constants.GlobalConstants;
import com.bjsts.manager.core.controller.AbstractController;
import com.bjsts.manager.entity.stock.ProductInBoundEntity;
import com.bjsts.manager.form.stock.ProductInBoundForm;
import com.bjsts.manager.query.stock.ProductInBoundSearchable;
import com.bjsts.manager.service.stock.ProductInBoundService;
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
@RequestMapping("/productInBound")
@SessionAttributes("productInBoundSearchable")
public class ProductInBoundController extends AbstractController {

    @Autowired
    private ProductInBoundService productInBoundService;

    @RequiresPermissions("sts:productInBound:list")
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(ProductInBoundSearchable productInBoundSearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable, ModelMap modelMap) {
        ApiResponse<ProductInBoundEntity> apiResponse = productInBoundService.findAll(productInBoundSearchable, pageable);
        Page<ProductInBoundEntity> page = new PageImpl<>(Lists.newArrayList(apiResponse.getPagedData()), pageable, apiResponse.getTotal());
        modelMap.addAttribute("page", page);
        return "purchase/productInBound/list";
    }

    @RequiresPermissions("sts:productInBound:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(@ModelAttribute ProductInBoundForm productInBoundForm, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".productInBoundForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        if (Objects.isNull(productInBoundForm.getProductInBound())) {
            productInBoundForm.setProductInBound(new ProductInBoundEntity());
        }
        modelMap.put("action", "create");
        return "purchase/productInBound/edit";
    }

    @RequiresPermissions("sts:productInBound:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(ProductInBoundForm productInBoundForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(productInBoundForm);
            return "redirect:/productInBound/create";
        }
        ProductInBoundEntity productInBoundEntity = productInBoundForm.getProductInBound();

        //Double cost = CoreMathUtils.mul(productInBoundForm.getCost(), 100L);
        //productInBoundEntity.setCost(cost.longValue());

        productInBoundService.save(productInBoundEntity);
        return "result";
    }

    @RequiresPermissions("sts:productInBound:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable Long id, @ModelAttribute ProductInBoundForm productInBoundForm, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".productInBoundForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        ProductInBoundEntity productInBoundEntity = productInBoundService.get(id);
        if (Objects.isNull(productInBoundEntity)) {
            logger.error("修改快递单,未查询[id={}]的快递单信息", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据!");
            return "redirect:/error";
        }

        //productInBoundForm.setCost(Double.valueOf(productInBoundEntity.getCost()));

        productInBoundForm.setProductInBound(productInBoundEntity);
        modelMap.put("action", "update");
        return "purchase/productInBound/edit";
    }

    @RequiresPermissions("sts:productInBound:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(ProductInBoundForm productInBoundForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(productInBoundForm);
            return "redirect:/productInBound/update/" + productInBoundForm.getProductInBound().getId();
        }
        ProductInBoundEntity productInBound = productInBoundForm.getProductInBound();
        ProductInBoundEntity productInBoundEntity = productInBoundService.get(productInBound.getId());
       /* productInBoundEntity.setShipper(productInBound.getShipper());
        productInBoundEntity.setPayer(productInBound.getPayer());
        Double cost = CoreMathUtils.mul(productInBoundForm.getCost(), 100L);
        productInBoundEntity.setCost(cost.longValue());
        productInBoundEntity.setContent(productInBound.getContent());
        productInBoundEntity.setReceiver(productInBound.getReceiver());
        productInBoundEntity.setMobile(productInBound.getMobile());
        productInBoundEntity.setAddress(productInBound.getAddress());
        productInBoundEntity.setCompany(productInBound.getCompany());
        productInBoundEntity.setDeliverDate(productInBound.getDeliverDate());*/
        productInBoundService.save(productInBoundEntity);
        
        return "result";
    }

    @RequiresPermissions("sts:productInBound:view")
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable Long id, ModelMap modelMap) {
        ProductInBoundEntity productInBoundEntity = productInBoundService.get(id);
        modelMap.put("productInBound", productInBoundEntity);
        return "purchase/productInBound/view";
    }

    @RequiresPermissions("sts:productInBound:disable")
    @RequestMapping(value = "/disable/{id}", method = RequestMethod.GET)
    public String disable(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        ProductInBoundEntity productInBoundEntity = productInBoundService.get(id);
        if (Objects.isNull(productInBoundEntity)) {
            logger.error("删除快递单,未查询[id={}]的快递单", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "未查询[id={"+id+"}]的快递单!");
            return "redirect:/error";
        }
        
        productInBoundEntity.setValid(EnableDisableStatus.DISABLE);
        productInBoundService.update(productInBoundEntity);
        return "redirect:/productInBound/list";
    }
}
