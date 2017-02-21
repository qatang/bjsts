package com.bjsts.manager.controller.stock;

import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.core.enums.YesNoStatus;
import com.bjsts.manager.core.constants.GlobalConstants;
import com.bjsts.manager.core.controller.AbstractController;
import com.bjsts.manager.entity.stock.InBoundEntity;
import com.bjsts.manager.entity.purchase.PurchaseEntity;
import com.bjsts.manager.form.stock.InBoundForm;
import com.bjsts.manager.query.purchase.PurchaseSearchable;
import com.bjsts.manager.service.stock.InBoundService;
import com.bjsts.manager.service.purchase.PurchaseService;
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

import java.util.List;
import java.util.Objects;

/**
 * 物料入库登记登记
 * @author wangzhiliang
 */
@Controller
@RequestMapping("/inBound")
@SessionAttributes("inBoundSearchable")
public class InBoundController extends AbstractController {

    @Autowired
    private InBoundService inBoundService;

    @Autowired
    private PurchaseService purchaseService;

    @ModelAttribute("yesNoStatusList")
    public List<YesNoStatus> getYesNoStatusList() {
        return YesNoStatus.list();
    }

    @RequiresPermissions("sts:inBound:list")
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(PurchaseSearchable purchaseSearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable, ModelMap modelMap) {
        ApiResponse<PurchaseEntity> apiResponse = purchaseService.findAll(purchaseSearchable, pageable);
        List<PurchaseEntity> purchaseList = Lists.newArrayList(apiResponse.getPagedData());
        purchaseList.forEach(purchaseEntity -> purchaseEntity.setInBoundEntity(inBoundService.findByPurchaseNo(purchaseEntity.getPurchaseNo())));
        Page<PurchaseEntity> page = new PageImpl<>(purchaseList, pageable, apiResponse.getTotal());
        modelMap.addAttribute("page", page);
        return "purchase/inBound/list";
    }

    @RequiresPermissions("sts:inBound:create")
    @RequestMapping(value = "/create/{purchaseNo}", method = RequestMethod.GET)
    public String create(@PathVariable String purchaseNo, @ModelAttribute InBoundForm inBoundForm, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".inBoundForm"), modelMap.get(BINDING_RESULT_KEY));
        }

        InBoundEntity inBoundEntity = inBoundService.findByPurchaseNo(purchaseNo);

        PurchaseEntity purchaseEntity = purchaseService.findByPurchaseNo(purchaseNo);

        if (Objects.isNull(inBoundEntity)) {
            inBoundEntity = new InBoundEntity();
            inBoundForm.setInBound(inBoundEntity);
        }
        inBoundEntity.setPurchase(purchaseEntity);
        inBoundForm.setInBound(inBoundEntity);

        modelMap.put("action", "create");
        return "purchase/inBound/edit";
    }

    @RequiresPermissions("sts:inBound:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(InBoundForm inBoundForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(inBoundForm);
            return "redirect:/inBound/create";
        }
        InBoundEntity inBoundEntity = inBoundForm.getInBound();
        inBoundService.inBound(inBoundEntity);
        return "result";
    }

    @RequiresPermissions("sts:inBound:update")
    @RequestMapping(value = "/update/{purchaseNo}", method = RequestMethod.GET)
    public String update(@PathVariable String purchaseNo, @ModelAttribute InBoundForm inBoundForm, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".inBoundForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        InBoundEntity inBoundEntity = inBoundService.findByPurchaseNo(purchaseNo);
        if (Objects.isNull(inBoundEntity)) {
            logger.error("修改入库登记,未查询[采购单={}]的入库登记信息", purchaseNo);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据!");
            return "redirect:/error";
        }

        PurchaseEntity purchaseEntity = purchaseService.findByPurchaseNo(purchaseNo);

        inBoundEntity.setPurchase(purchaseEntity);
        inBoundForm.setInBound(inBoundEntity);

        modelMap.put("action", "update");
        return "purchase/inBound/edit";
    }

    @RequiresPermissions("sts:inBound:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(InBoundForm inBoundForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(inBoundForm);
            return "redirect:/inBound/update/" + inBoundForm.getInBound().getPurchaseNo();
        }
        InBoundEntity inBound = inBoundForm.getInBound();
        InBoundEntity inBoundEntity = inBoundService.get(inBound.getId());
        inBoundEntity.setVerify(inBound.getVerify());
        inBoundEntity.setSendee(inBound.getSendee());
        inBoundEntity.setSendeeTime(inBound.getSendeeTime());
        inBoundEntity.setMemo(inBound.getMemo());
        inBoundService.inBound(inBoundEntity);
        return "result";
    }

    @RequiresPermissions("sts:inBound:view")
    @RequestMapping(value = "/view/{purchaseNo}", method = RequestMethod.GET)
    public String view(@PathVariable String purchaseNo, ModelMap modelMap) {
        InBoundEntity inBoundEntity = inBoundService.findByPurchaseNo(purchaseNo);
        PurchaseEntity purchaseEntity = purchaseService.findByPurchaseNo(inBoundEntity.getPurchaseNo());
        inBoundEntity.setPurchase(purchaseEntity);
        modelMap.put("inBound", inBoundEntity);
        return "purchase/inBound/view";
    }

    @RequiresPermissions("sts:inBound:disable")
    @RequestMapping(value = "/disable/{id}", method = RequestMethod.GET)
    public String disable(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        InBoundEntity inBoundEntity = inBoundService.get(id);
        if (Objects.isNull(inBoundEntity)) {
            logger.error("删除入库登记信息,未查询[id={}]的采购合同信息", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "未查询[id={"+id+"}]的入库登记信息!");
            return "redirect:/error";
        }
        inBoundEntity.setValid(EnableDisableStatus.DISABLE);
        inBoundService.update(inBoundEntity);
        return "redirect:/inBound/list";
    }
}
