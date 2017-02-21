package com.bjsts.manager.controller.stock;

import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.manager.core.constants.GlobalConstants;
import com.bjsts.manager.core.controller.AbstractController;
import com.bjsts.manager.entity.stock.ProductOutBoundEntity;
import com.bjsts.manager.form.stock.ProductOutBoundForm;
import com.bjsts.manager.query.stock.ProductOutBoundSearchable;
import com.bjsts.manager.service.stock.ProductOutBoundService;
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
@RequestMapping("/productOutBound")
@SessionAttributes("productOutBoundSearchable")
public class ProductOutBoundController extends AbstractController {

    @Autowired
    private ProductOutBoundService productOutBoundService;

    @RequiresPermissions("sts:productOutBound:list")
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(ProductOutBoundSearchable productOutBoundSearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable, ModelMap modelMap) {
        ApiResponse<ProductOutBoundEntity> apiResponse = productOutBoundService.findAll(productOutBoundSearchable, pageable);
        Page<ProductOutBoundEntity> page = new PageImpl<>(Lists.newArrayList(apiResponse.getPagedData()), pageable, apiResponse.getTotal());
        modelMap.addAttribute("page", page);
        return "purchase/productOutBound/list";
    }

    @RequiresPermissions("sts:productOutBound:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(@ModelAttribute ProductOutBoundForm productOutBoundForm, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".productOutBoundForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        if (Objects.isNull(productOutBoundForm.getProductOutBound())) {
            productOutBoundForm.setProductOutBound(new ProductOutBoundEntity());
        }
        modelMap.put("action", "create");
        return "purchase/productOutBound/edit";
    }

    @RequiresPermissions("sts:productOutBound:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(ProductOutBoundForm productOutBoundForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(productOutBoundForm);
            return "redirect:/productOutBound/create";
        }
        ProductOutBoundEntity productOutBoundEntity = productOutBoundForm.getProductOutBound();

        //Double cost = CoreMathUtils.mul(productOutBoundForm.getCost(), 100L);
        //productOutBoundEntity.setCost(cost.longValue());

        productOutBoundService.save(productOutBoundEntity);
        return "result";
    }

    @RequiresPermissions("sts:productOutBound:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable Long id, @ModelAttribute ProductOutBoundForm productOutBoundForm, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".productOutBoundForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        ProductOutBoundEntity productOutBoundEntity = productOutBoundService.get(id);
        if (Objects.isNull(productOutBoundEntity)) {
            logger.error("修改快递单,未查询[id={}]的快递单信息", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据!");
            return "redirect:/error";
        }

        //productOutBoundForm.setCost(Double.valueOf(productOutBoundEntity.getCost()));

        productOutBoundForm.setProductOutBound(productOutBoundEntity);
        modelMap.put("action", "update");
        return "purchase/productOutBound/edit";
    }

    @RequiresPermissions("sts:productOutBound:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(ProductOutBoundForm productOutBoundForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(productOutBoundForm);
            return "redirect:/productOutBound/update/" + productOutBoundForm.getProductOutBound().getId();
        }
        ProductOutBoundEntity productOutBound = productOutBoundForm.getProductOutBound();
        ProductOutBoundEntity productOutBoundEntity = productOutBoundService.get(productOutBound.getId());
       /* productOutBoundEntity.setShipper(productOutBound.getShipper());
        productOutBoundEntity.setPayer(productOutBound.getPayer());
        Double cost = CoreMathUtils.mul(productOutBoundForm.getCost(), 100L);
        productOutBoundEntity.setCost(cost.longValue());
        productOutBoundEntity.setContent(productOutBound.getContent());
        productOutBoundEntity.setReceiver(productOutBound.getReceiver());
        productOutBoundEntity.setMobile(productOutBound.getMobile());
        productOutBoundEntity.setAddress(productOutBound.getAddress());
        productOutBoundEntity.setCompany(productOutBound.getCompany());
        productOutBoundEntity.setDeliverDate(productOutBound.getDeliverDate());*/
        productOutBoundService.save(productOutBoundEntity);
        
        return "result";
    }

    @RequiresPermissions("sts:productOutBound:view")
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable Long id, ModelMap modelMap) {
        ProductOutBoundEntity productOutBoundEntity = productOutBoundService.get(id);
        modelMap.put("productOutBound", productOutBoundEntity);
        return "purchase/productOutBound/view";
    }

    @RequiresPermissions("sts:productOutBound:disable")
    @RequestMapping(value = "/disable/{id}", method = RequestMethod.GET)
    public String disable(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        ProductOutBoundEntity productOutBoundEntity = productOutBoundService.get(id);
        if (Objects.isNull(productOutBoundEntity)) {
            logger.error("删除快递单,未查询[id={}]的快递单", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "未查询[id={"+id+"}]的快递单!");
            return "redirect:/error";
        }
        
        productOutBoundEntity.setValid(EnableDisableStatus.DISABLE);
        productOutBoundService.update(productOutBoundEntity);
        return "redirect:/productOutBound/list";
    }
}
