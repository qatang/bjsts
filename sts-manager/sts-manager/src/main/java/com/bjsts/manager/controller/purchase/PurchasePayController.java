package com.bjsts.manager.controller.purchase;

import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.core.util.CoreMathUtils;
import com.bjsts.manager.core.constants.GlobalConstants;
import com.bjsts.manager.core.controller.AbstractController;
import com.bjsts.manager.entity.purchase.PurchasePayEntity;
import com.bjsts.manager.entity.purchase.PurchasePayItemEntity;
import com.bjsts.manager.entity.system.UserEntity;
import com.bjsts.manager.enums.invoice.MakeOutInvoiceStatus;
import com.bjsts.manager.form.purchase.PurchasePayForm;
import com.bjsts.manager.query.purchase.PurchasePaySearchable;
import com.bjsts.manager.service.purchase.PurchasePayItemService;
import com.bjsts.manager.service.purchase.PurchasePayService;
import com.bjsts.manager.service.system.UserService;
import com.google.common.collect.Lists;
import org.apache.shiro.SecurityUtils;
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
 * @author wangzhiliang
 */
@Controller
@RequestMapping("/purchasePay")
@SessionAttributes("purchasePaySearchable")
public class PurchasePayController extends AbstractController {

    @Autowired
    private PurchasePayService purchasePayService;

    @Autowired
    private PurchasePayItemService purchasePayItemService;

    @Autowired
    private UserService userService;

    @ModelAttribute("invoiceStatusList")
    public List<MakeOutInvoiceStatus> getInvoiceStatusList() {
        return MakeOutInvoiceStatus.list();
    }

    @RequiresPermissions("sts:purchasePay:list")
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(PurchasePaySearchable purchasePaySearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable, ModelMap modelMap) {
        ApiResponse<PurchasePayEntity> apiResponse = purchasePayService.findAll(purchasePaySearchable, pageable);
        List<PurchasePayEntity> purchasePayEntities = Lists.newArrayList(apiResponse.getPagedData());
        Page<PurchasePayEntity> page = new PageImpl<>(purchasePayEntities, pageable, apiResponse.getTotal());
        modelMap.addAttribute("page", page);
        return "purchase/purchasePay/list";
    }

    @RequiresPermissions("sts:purchasePay:create")
    @RequestMapping(value = "/create/{purchasePayId}", method = RequestMethod.GET)
    public String create(@PathVariable Long purchasePayId, @ModelAttribute PurchasePayForm purchasePayForm, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".purchasePayForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        if (Objects.isNull(purchasePayForm.getPurchasePayItem())) {
            purchasePayForm.setPurchasePayItem(new PurchasePayItemEntity());
        }

        PurchasePayEntity purchasePayEntity = purchasePayService.get(purchasePayId);

        List<PurchasePayItemEntity> purchasePayItemEntities = purchasePayItemService.findByPurchasePayId(purchasePayId);

        for (PurchasePayItemEntity purchasePayItemEntity : purchasePayItemEntities) {
            UserEntity userEntity = userService.get(purchasePayItemEntity.getOperatorId());
            if (userEntity != null) {
                purchasePayItemEntity.setOperator(userEntity.getRealName());
            } else {
                purchasePayItemEntity.setOperator("");
            }
        }

        modelMap.put("purchasePayItemList", purchasePayItemEntities);
        modelMap.put("purchasePay", purchasePayEntity);
        return "purchase/purchasePay/edit";
    }

    @RequiresPermissions("sts:purchasePay:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(PurchasePayForm purchasePayForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(purchasePayForm);
            return "redirect:/purchasePay/create/" + purchasePayForm.getPurchasePay().getId();
        }
        PurchasePayItemEntity purchasePayItemEntity = purchasePayForm.getPurchasePayItem();
        purchasePayItemEntity.setPurchasePayId(purchasePayForm.getPurchasePay().getId());

        Double amount = CoreMathUtils.mul(purchasePayForm.getAmount(), 100L);
        purchasePayItemEntity.setAmount(amount.longValue());

        Double invoiceAmount = CoreMathUtils.mul(purchasePayForm.getInvoiceAmount(), 100L);
        purchasePayItemEntity.setInvoiceAmount(invoiceAmount.longValue());

        UserEntity currentUser = (UserEntity) SecurityUtils.getSubject().getPrincipal();
        purchasePayItemEntity.setOperatorId(currentUser.getId());
        purchasePayItemService.save(purchasePayItemEntity);
        return "result";
    }

    @RequiresPermissions("sts:purchasePayItem:disable")
    @RequestMapping(value = "/disable/item/{id}", method = RequestMethod.GET)
    public String disable(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        PurchasePayItemEntity purchasePayItemEntity = purchasePayItemService.get(id);
        if (Objects.isNull(purchasePayItemEntity)) {
            logger.error("删除付款记录信息,未查询[id={}]的付款记录信息", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "未查询到编号为["+id+"]的付款记录信息!");
            return "redirect:/error";
        }
        purchasePayItemService.delete(id);
        return "redirect:/purchasePay/create/" + purchasePayItemEntity.getPurchasePayId();
    }
}
