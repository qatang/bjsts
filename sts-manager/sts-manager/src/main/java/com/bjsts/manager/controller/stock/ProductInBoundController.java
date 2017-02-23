package com.bjsts.manager.controller.stock;

import com.bjsts.core.api.component.request.ApiPageRequestHelper;
import com.bjsts.core.api.request.ApiRequest;
import com.bjsts.core.api.request.ApiRequestPage;
import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.core.enums.PageOrderType;
import com.bjsts.manager.core.constants.GlobalConstants;
import com.bjsts.manager.core.controller.AbstractController;
import com.bjsts.manager.entity.sale.ContractEntity;
import com.bjsts.manager.entity.sale.PlanEntity;
import com.bjsts.manager.entity.stock.ProductInBoundEntity;
import com.bjsts.manager.form.stock.ProductInBoundForm;
import com.bjsts.manager.query.stock.ProductInBoundSearchable;
import com.bjsts.manager.service.sale.ContractService;
import com.bjsts.manager.service.sale.ProductOrderService;
import com.bjsts.manager.service.stock.ProductInBoundService;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
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
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
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

    @Autowired
    private ProductOrderService productOrderService;

    @Autowired
    private ContractService contractService;

    @RequiresPermissions("sts:productInBound:list")
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(ProductInBoundSearchable productInBoundSearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable, ModelMap modelMap) {
        ApiResponse<ProductInBoundEntity> apiResponse = productInBoundService.findAll(productInBoundSearchable, pageable);
        Page<ProductInBoundEntity> page = new PageImpl<>(Lists.newArrayList(apiResponse.getPagedData()), pageable, apiResponse.getTotal());
        modelMap.addAttribute("page", page);
        return "stock/productInBound/list";
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

        ApiRequest apiRequest = ApiRequest.newInstance();
        apiRequest.filterEqual("valid", EnableDisableStatus.ENABLE);

        ApiRequestPage apiRequestPage = ApiRequestPage.newInstance().paging(0, 100)
                .addOrder("id", PageOrderType.ASC);

        List<PlanEntity> planEntityList = ApiPageRequestHelper.request(apiRequest, apiRequestPage, productOrderService::findAll);

        ApiRequest apiRequestContract = ApiRequest.newInstance();
        apiRequestContract.filterEqual("valid", EnableDisableStatus.ENABLE);

        ApiRequestPage apiRequestPageContract = ApiRequestPage.newInstance().paging(0, 100)
                .addOrder("id", PageOrderType.ASC);

        List<ContractEntity> contractEntityList = ApiPageRequestHelper.request(apiRequestContract, apiRequestPageContract, contractService::findAll);

        modelMap.put("action", "create");
        modelMap.put("planList", planEntityList);
        modelMap.put("contractList", contractEntityList);
        return "stock/productInBound/edit";
    }

    @RequiresPermissions("sts:productInBound:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(ProductInBoundForm productInBoundForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(productInBoundForm);
            return "redirect:/productInBound/create";
        }
        ProductInBoundEntity productInBoundEntity = productInBoundForm.getProductInBound();

        String planNo = productInBoundEntity.getPlanNo();
        if (StringUtils.isEmpty(planNo) || "0".equals(planNo)) {
            result.addError(new ObjectError("planNo", "请选择项目"));
            redirectAttributes.addFlashAttribute(BINDING_RESULT_KEY, result.getAllErrors());
            redirectAttributes.addFlashAttribute(productInBoundForm);
            return "redirect:/productInBound/create";
        }

        String contractNo = productInBoundEntity.getContractNo();
        if (StringUtils.isEmpty(contractNo) || "0".equals(contractNo)) {
            result.addError(new ObjectError("contractNo", "请选择合同"));
            redirectAttributes.addFlashAttribute(BINDING_RESULT_KEY, result.getAllErrors());
            redirectAttributes.addFlashAttribute(productInBoundForm);
            return "redirect:/productInBound/create";
        }

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
            logger.error("修改成品入库单,未查询[id={}]的成品入库单信息", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据!");
            return "redirect:/error";
        }

        productInBoundForm.setProductInBound(productInBoundEntity);
        modelMap.put("action", "update");
        return "stock/productInBound/edit";
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
        productInBoundEntity.setPlanName(productInBound.getPlanName());
        productInBoundEntity.setCompany(productInBound.getCompany());
        productInBoundEntity.setProductName(productInBound.getProductName());
        productInBoundEntity.setQuantity(productInBound.getQuantity());
        productInBoundEntity.setUnit(productInBound.getUnit());
        productInBoundEntity.setInBoundTime(productInBound.getInBoundTime());
        productInBoundService.save(productInBoundEntity);
        
        return "result";
    }

    @RequiresPermissions("sts:productInBound:view")
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable Long id, ModelMap modelMap) {
        ProductInBoundEntity productInBoundEntity = productInBoundService.get(id);
        modelMap.put("productInBound", productInBoundEntity);
        return "stock/productInBound/view";
    }

    @RequiresPermissions("sts:productInBound:disable")
    @RequestMapping(value = "/disable/{id}", method = RequestMethod.GET)
    public String disable(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        ProductInBoundEntity productInBoundEntity = productInBoundService.get(id);
        if (Objects.isNull(productInBoundEntity)) {
            logger.error("删除成品入库单,未查询[id={}]的成品入库单", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "未查询[id={"+id+"}]的成品入库单!");
            return "redirect:/error";
        }
        
        productInBoundEntity.setValid(EnableDisableStatus.DISABLE);
        productInBoundService.update(productInBoundEntity);
        return "redirect:/productInBound/list";
    }
}
