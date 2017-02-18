package com.bjsts.manager.controller.purchase;

import com.bjsts.core.api.component.request.ApiPageRequestHelper;
import com.bjsts.core.api.request.ApiRequest;
import com.bjsts.core.api.request.ApiRequestPage;
import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.core.enums.PageOrderType;
import com.bjsts.manager.core.constants.GlobalConstants;
import com.bjsts.manager.core.controller.AbstractController;
import com.bjsts.manager.entity.purchase.OutBoundEntity;
import com.bjsts.manager.entity.purchase.StockEntity;
import com.bjsts.manager.entity.sale.PlanEntity;
import com.bjsts.manager.form.purchase.OutBoundForm;
import com.bjsts.manager.query.purchase.OutBoundSearchable;
import com.bjsts.manager.service.purchase.OutBoundService;
import com.bjsts.manager.service.purchase.StockService;
import com.bjsts.manager.service.sale.ProductOrderService;
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
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;

/**
 * 物料出库登记
 * @author wangzhiliang
 */
@Controller
@RequestMapping("/outBound")
@SessionAttributes("outBoundSearchable")
public class OutBoundController extends AbstractController {

    @Autowired
    private OutBoundService outBoundService;

    @Autowired
    private ProductOrderService productOrderService;

    @Autowired
    private StockService stockService;

    @RequiresPermissions("sts:outBound:list")
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(OutBoundSearchable outBoundSearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable, ModelMap modelMap) {
        ApiResponse<OutBoundEntity> apiResponse = outBoundService.findAll(outBoundSearchable, pageable);
        List<OutBoundEntity> outBoundEntityList = Lists.newArrayList(apiResponse.getPagedData());
        outBoundEntityList.forEach(outBoundEntity -> {
            outBoundEntity.setPlan(productOrderService.findByPlanNo(outBoundEntity.getPlanNo()));
            outBoundEntity.setStock(stockService.get(outBoundEntity.getStockId()));
        });
        Page<OutBoundEntity> page = new PageImpl<>(outBoundEntityList, pageable, apiResponse.getTotal());
        modelMap.addAttribute("page", page);
        return "purchase/outBound/list";
    }

    @RequiresPermissions("sts:outBound:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(@ModelAttribute OutBoundForm outBoundForm, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".outBoundForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        if (Objects.isNull(outBoundForm.getOutBound())) {
            outBoundForm.setOutBound(new OutBoundEntity());
        }

        ApiRequest apiRequest = ApiRequest.newInstance();
        apiRequest.filterEqual("valid", EnableDisableStatus.ENABLE);

        ApiRequestPage apiRequestPage = ApiRequestPage.newInstance().paging(0, 100)
                .addOrder("id", PageOrderType.ASC);

        List<PlanEntity> planEntityList = ApiPageRequestHelper.request(apiRequest, apiRequestPage, productOrderService::findAll);

        ApiRequest apiRequestStock = ApiRequest.newInstance();
        apiRequestStock.filterEqual("valid", EnableDisableStatus.ENABLE);

        ApiRequestPage apiRequestPageStock = ApiRequestPage.newInstance().paging(0, 100)
                .addOrder("id", PageOrderType.ASC);

        List<StockEntity> stockEntityList = ApiPageRequestHelper.request(apiRequestStock, apiRequestPageStock, stockService::findAll);

        modelMap.put("action", "create");
        modelMap.put("planList", planEntityList);
        modelMap.put("stockList", stockEntityList);
        return "purchase/outBound/edit";
    }

    @RequiresPermissions("sts:outBound:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(OutBoundForm outBoundForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(outBoundForm);
            return "redirect:/outBound/create";
        }
        OutBoundEntity outBoundEntity = outBoundForm.getOutBound();
        try {
            outBoundService.outBound(outBoundEntity);
        } catch (Exception e) {
            result.addError(new ObjectError("planNo", e.getMessage()));
            redirectAttributes.addFlashAttribute(BINDING_RESULT_KEY, result.getAllErrors());
            redirectAttributes.addFlashAttribute(outBoundForm);
            return "redirect:/outBound/create";
        }
        return "result";
    }

    @RequiresPermissions("sts:outBound:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable Long id, @ModelAttribute OutBoundForm outBoundForm, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".outBoundForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        OutBoundEntity outBoundEntity = outBoundService.get(id);
        if (Objects.isNull(outBoundEntity)) {
            logger.error("修改出库,未查询[id={}]的出库信息", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据!");
            return "redirect:/error";
        }
        outBoundForm.setOutBound(outBoundEntity);
        modelMap.put("action", "update");
        return "purchase/outBound/edit";
    }

    @RequiresPermissions("sts:outBound:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(OutBoundForm outBoundForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(outBoundForm);
            return "redirect:/outBound/update/" + outBoundForm.getOutBound().getId();
        }
        OutBoundEntity outBound = outBoundForm.getOutBound();
        OutBoundEntity outBoundEntity = outBoundService.get(outBound.getId());
        outBoundEntity.setStockId(outBound.getStockId());
        outBoundEntity.setQuantity(outBound.getQuantity());
        outBoundEntity.setReceiptor(outBound.getReceiptor());
        outBoundEntity.setOutBoundTime(outBound.getOutBoundTime());
        outBoundService.update(outBoundEntity);
        return "result";
    }

    @RequiresPermissions("sts:outBound:view")
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable Long id, ModelMap modelMap) {
        OutBoundEntity outBoundEntity = outBoundService.get(id);
        PlanEntity planEntity = productOrderService.findByPlanNo(outBoundEntity.getPlanNo());
        outBoundEntity.setPlan(planEntity);

        StockEntity stockEntity = stockService.get(outBoundEntity.getStockId());
        outBoundEntity.setStock(stockEntity);
        modelMap.put("outBound", outBoundEntity);
        return "purchase/outBound/view";
    }

    @RequiresPermissions("sts:outBound:disable")
    @RequestMapping(value = "/disable/{id}", method = RequestMethod.GET)
    public String disable(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        OutBoundEntity outBoundEntity = outBoundService.get(id);
        if (Objects.isNull(outBoundEntity)) {
            logger.error("撤销出库信息,未查询[id={}]的出库信息", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "未查询[id={"+id+"}]的撤销信息!");
            return "redirect:/error";
        }
        try {
            outBoundService.cancelBound(outBoundEntity);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, e.getMessage());
        }
        return "redirect:/outBound/list";
    }
}
