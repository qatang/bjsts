package com.bjsts.manager.controller.stock;

import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.manager.core.constants.GlobalConstants;
import com.bjsts.manager.core.controller.AbstractController;
import com.bjsts.manager.entity.stock.StockEntity;
import com.bjsts.manager.form.stock.StockForm;
import com.bjsts.manager.query.stock.StockSearchable;
import com.bjsts.manager.service.stock.StockService;
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
 * 物料库存登记
 * @author wangzhiliang
 */
@Controller
@RequestMapping("/stock")
@SessionAttributes("stockSearchable")
public class StockController extends AbstractController {

    @Autowired
    private StockService stockService;

    @RequiresPermissions("sts:stock:list")
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(StockSearchable stockSearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable, ModelMap modelMap) {
        ApiResponse<StockEntity> apiResponse = stockService.findAll(stockSearchable, pageable);
        Page<StockEntity> page = new PageImpl<>(Lists.newArrayList(apiResponse.getPagedData()), pageable, apiResponse.getTotal());
        modelMap.addAttribute("page", page);
        return "purchase/stock/list";
    }

    @RequiresPermissions("sts:stock:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(@ModelAttribute StockForm stockForm, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".stockForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        if (Objects.isNull(stockForm.getStock())) {
            stockForm.setStock(new StockEntity());
        }
        modelMap.put("action", "create");
        return "purchase/stock/edit";
    }

    @RequiresPermissions("sts:stock:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(StockForm stockForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(stockForm);
            return "redirect:/stock/create";
        }
        StockEntity stockEntity = stockForm.getStock();
        stockService.save(stockEntity);
        return "result";
    }

    @RequiresPermissions("sts:stock:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable Long id, @ModelAttribute StockForm stockForm, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".stockForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        StockEntity stockEntity = stockService.get(id);
        if (Objects.isNull(stockEntity)) {
            logger.error("修改库存,未查询[id={}]的库存信息", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据!");
            return "redirect:/error";
        }
        stockForm.setStock(stockEntity);
        modelMap.put("action", "update");
        return "purchase/stock/edit";
    }

    @RequiresPermissions("sts:stock:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(StockForm stockForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(stockForm);
            return "redirect:/stock/update/" + stockForm.getStock().getId();
        }
        StockEntity stock = stockForm.getStock();
        StockEntity stockEntity = stockService.get(stock.getId());
        stockEntity.setProductName(stock.getProductName());
        stockEntity.setProductModel(stock.getProductModel());
        stockEntity.setQuantity(stock.getQuantity());
        stockService.update(stockEntity);
        return "result";
    }

    @RequiresPermissions("sts:stock:view")
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable Long id, ModelMap modelMap) {
        modelMap.put("stock", stockService.get(id));
        return "purchase/stock/view";
    }

    @RequiresPermissions("sts:stock:disable")
    @RequestMapping(value = "/disable/{id}", method = RequestMethod.GET)
    public String disable(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        StockEntity stockEntity = stockService.get(id);
        if (Objects.isNull(stockEntity)) {
            logger.error("删除库存信息,未查询[id={}]的采购合同信息", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "未查询[id={"+id+"}]的库存信息!");
            return "redirect:/error";
        }
        stockEntity.setValid(EnableDisableStatus.DISABLE);
        stockService.update(stockEntity);
        return "redirect:/stock/list";
    }
}
