package com.bjsts.manager.controller.purchase;

import com.bjsts.manager.core.constants.GlobalConstants;
import com.bjsts.manager.core.controller.AbstractController;
import com.bjsts.manager.entity.purchase.StockEntity;
import com.bjsts.manager.form.purchase.StockForm;
import com.bjsts.manager.query.system.UserSearchable;
import com.bjsts.manager.service.purchase.StockService;
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
@RequestMapping("/stock")
@SessionAttributes("stockSearchable")
public class StockController extends AbstractController {

    @Autowired
    //private final ThreadLocal<StockService> stockService = new ThreadLocal<>();
    private StockService stockService;

    @RequiresPermissions("arsenal:stock:list")
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(UserSearchable stockSearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable, ModelMap modelMap) {
        List<StockEntity> stockEntityList = stockService.findAll();
        modelMap.addAttribute("list", stockEntityList);
        return "stock/list";
    }

    @RequiresPermissions("arsenal:stock:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(@ModelAttribute StockForm stockForm, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".stockForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        if (Objects.isNull(stockForm.getStock())) {
            stockForm.setStock(new StockEntity());
        }
        modelMap.put("action", "create");
        return "stock/edit";
    }

    @RequiresPermissions("arsenal:stock:create")
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

    @RequiresPermissions("arsenal:stock:update")
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
        return "stock/edit";
    }

    @RequiresPermissions("arsenal:stock:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(StockForm stockForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(stockForm);
            return "redirect:/stock/update/" + stockForm.getStock().getId();
        }
        StockEntity stock = stockForm.getStock();
        StockEntity stockEntity = stockService.get(stock.getId());
        stockService.update(stockEntity);
        return "result";
    }

    @RequiresPermissions("arsenal:stock:view")
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable Long id, ModelMap modelMap) {
        modelMap.put("stock", stockService.get(id));
        return "stock/view";
    }
}
