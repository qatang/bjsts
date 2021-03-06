package com.bjsts.manager.controller.purchase;

import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.core.util.CoreMathUtils;
import com.bjsts.manager.core.constants.GlobalConstants;
import com.bjsts.manager.core.controller.AbstractController;
import com.bjsts.manager.entity.purchase.ExpressEntity;
import com.bjsts.manager.form.purchase.ExpressForm;
import com.bjsts.manager.query.purchase.ExpressSearchable;
import com.bjsts.manager.service.purchase.ExpressService;
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
@RequestMapping("/express")
@SessionAttributes("expressSearchable")
public class ExpressController extends AbstractController {

    @Autowired
    private ExpressService expressService;

    @RequiresPermissions("sts:express:list")
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(ExpressSearchable expressSearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable, ModelMap modelMap) {
        ApiResponse<ExpressEntity> apiResponse = expressService.findAll(expressSearchable, pageable);
        Page<ExpressEntity> page = new PageImpl<>(Lists.newArrayList(apiResponse.getPagedData()), pageable, apiResponse.getTotal());
        modelMap.addAttribute("page", page);
        return "purchase/express/list";
    }

    @RequiresPermissions("sts:express:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(@ModelAttribute ExpressForm expressForm, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".expressForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        if (Objects.isNull(expressForm.getExpress())) {
            expressForm.setExpress(new ExpressEntity());
        }
        modelMap.put("action", "create");
        return "purchase/express/edit";
    }

    @RequiresPermissions("sts:express:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(ExpressForm expressForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(expressForm);
            return "redirect:/express/create";
        }
        ExpressEntity expressEntity = expressForm.getExpress();

        Double cost = CoreMathUtils.mul(expressForm.getCost(), 100L);
        expressEntity.setCost(cost.longValue());

        expressService.save(expressEntity);
        return "result";
    }

    @RequiresPermissions("sts:express:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable Long id, @ModelAttribute ExpressForm expressForm, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".expressForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        ExpressEntity expressEntity = expressService.get(id);
        if (Objects.isNull(expressEntity)) {
            logger.error("修改快递单,未查询[id={}]的快递单信息", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据!");
            return "redirect:/error";
        }

        expressForm.setCost(Double.valueOf(expressEntity.getCost()));

        expressForm.setExpress(expressEntity);
        modelMap.put("action", "update");
        return "purchase/express/edit";
    }

    @RequiresPermissions("sts:express:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(ExpressForm expressForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(expressForm);
            return "redirect:/express/update/" + expressForm.getExpress().getId();
        }
        ExpressEntity express = expressForm.getExpress();
        ExpressEntity expressEntity = expressService.get(express.getId());
        expressEntity.setShipper(express.getShipper());
        expressEntity.setPayer(express.getPayer());
        Double cost = CoreMathUtils.mul(expressForm.getCost(), 100L);
        expressEntity.setCost(cost.longValue());
        expressEntity.setContent(express.getContent());
        expressEntity.setReceiver(express.getReceiver());
        expressEntity.setMobile(express.getMobile());
        expressEntity.setAddress(express.getAddress());
        expressEntity.setCompany(express.getCompany());
        expressEntity.setDeliverDate(express.getDeliverDate());
        expressEntity.setReceiveDate(express.getReceiveDate());
        expressEntity.setExpressNo(express.getExpressNo());
        expressService.save(expressEntity);
        
        return "result";
    }

    @RequiresPermissions("sts:express:view")
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable Long id, ModelMap modelMap) {
        ExpressEntity expressEntity = expressService.get(id);
        modelMap.put("express", expressEntity);
        return "purchase/express/view";
    }

    @RequiresPermissions("sts:express:disable")
    @RequestMapping(value = "/disable/{id}", method = RequestMethod.GET)
    public String disable(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        ExpressEntity expressEntity = expressService.get(id);
        if (Objects.isNull(expressEntity)) {
            logger.error("删除快递单,未查询[id={}]的快递单", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "未查询[id={"+id+"}]的快递单!");
            return "redirect:/error";
        }
        
        expressEntity.setValid(EnableDisableStatus.DISABLE);
        expressService.update(expressEntity);
        return "redirect:/express/list";
    }
}
