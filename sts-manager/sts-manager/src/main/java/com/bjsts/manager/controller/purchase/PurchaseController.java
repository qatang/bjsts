package com.bjsts.manager.controller.purchase;

import com.bjsts.core.api.component.request.ApiPageRequestHelper;
import com.bjsts.core.api.request.ApiRequest;
import com.bjsts.core.api.request.ApiRequestPage;
import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.core.enums.PageOrderType;
import com.bjsts.core.enums.YesNoStatus;
import com.bjsts.core.util.CoreMathUtils;
import com.bjsts.manager.core.constants.GlobalConstants;
import com.bjsts.manager.core.controller.AbstractController;
import com.bjsts.manager.entity.document.DocumentEntity;
import com.bjsts.manager.entity.purchase.PurchaseEntity;
import com.bjsts.manager.entity.purchase.PurchaseItemEntity;
import com.bjsts.manager.entity.purchase.SupplierEntity;
import com.bjsts.manager.entity.purchase.SupplierItemEntity;
import com.bjsts.manager.enums.invoice.MakeOutInvoiceStatus;
import com.bjsts.manager.form.purchase.PurchaseForm;
import com.bjsts.manager.query.purchase.PurchaseSearchable;
import com.bjsts.manager.service.document.DocumentService;
import com.bjsts.manager.service.idgenerator.IdGeneratorService;
import com.bjsts.manager.service.purchase.PurchaseItemService;
import com.bjsts.manager.service.purchase.PurchaseService;
import com.bjsts.manager.service.purchase.SupplierItemService;
import com.bjsts.manager.service.purchase.SupplierService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
 * 物料采购
 * @author wangzhiliang
 */
@Controller
@RequestMapping("/purchase")
@SessionAttributes("purchaseSearchable")
public class PurchaseController extends AbstractController {

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private PurchaseItemService purchaseItemService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private SupplierItemService supplierItemService;

    @Autowired
    private IdGeneratorService idGeneratorService;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${file.external.url}")
    private String fileExternalUrl;

    @ModelAttribute("invoiceStatusList")
    public List<MakeOutInvoiceStatus> getInvoiceStatusList() {
        return MakeOutInvoiceStatus.list();
    }

    @RequiresPermissions("sts:purchase:list")
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(PurchaseSearchable purchaseSearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable, ModelMap modelMap) {
        ApiResponse<PurchaseEntity> apiResponse = purchaseService.findAll(purchaseSearchable, pageable);
        List<PurchaseEntity> purchaseEntities = Lists.newArrayList(apiResponse.getPagedData());
        purchaseEntities.forEach(purchaseEntity -> {
            SupplierEntity supplierEntity = supplierService.get(purchaseEntity.getSupplierId());
            purchaseEntity.setSupplier(supplierEntity);
        });
        Page<PurchaseEntity> page = new PageImpl<>(purchaseEntities, pageable, apiResponse.getTotal());
        modelMap.addAttribute("page", page);
        return "purchase/purchase/list";
    }

    @RequiresPermissions("sts:purchase:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(@ModelAttribute PurchaseForm purchaseForm, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".purchaseForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        if (Objects.isNull(purchaseForm.getPurchase())) {
            purchaseForm.setPurchase(new PurchaseEntity());
        }

        ApiRequest apiRequest = ApiRequest.newInstance();
        apiRequest.filterEqual("valid", EnableDisableStatus.ENABLE);

        ApiRequestPage apiRequestPage = ApiRequestPage.newInstance().paging(0, 100)
                .addOrder("id", PageOrderType.ASC);

        List<SupplierEntity> supplierEntityList = ApiPageRequestHelper.request(apiRequest, apiRequestPage, supplierService::findAll);

        modelMap.put("action", "create");
        modelMap.put("supplierList", supplierEntityList);
        return "purchase/purchase/edit";
    }

    @RequiresPermissions("sts:purchase:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(PurchaseForm purchaseForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(purchaseForm);
            return "redirect:/purchase/create";
        }
        PurchaseEntity purchaseEntity = purchaseForm.getPurchase();

        Long supplierId = purchaseEntity.getSupplierId();
        if (supplierId == null) {
            logger.error("请选择供应商");
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "请选择供应商!");
            return "redirect:/purchase/create";
        }

        purchaseEntity.setPurchaseNo(PurchaseEntity.SEQ_ID_PREFIX + idGeneratorService.generateDateFormatted(PurchaseEntity.SEQ_ID_GENERATOR));

        Double totalAmount = CoreMathUtils.mul(purchaseForm.getTotalAmount(), 100L);
        purchaseEntity.setTotalAmount(totalAmount.longValue());

        Double payedAmount = CoreMathUtils.mul(purchaseForm.getPayedAmount(), 100L);
        purchaseEntity.setPayedAmount(payedAmount.longValue());

        Double unpayedAmount = CoreMathUtils.mul(purchaseForm.getUnPayedAmount(), 100L);
        purchaseEntity.setUnPayedAmount(unpayedAmount.longValue());

        purchaseEntity.setInBound(YesNoStatus.NO);
        
        DocumentEntity document = purchaseForm.getDocument();
        if (StringUtils.isEmpty(document.getName())) {
            purchaseService.save(purchaseEntity);
        } else {
            purchaseService.save(purchaseEntity, document);
        }
        return "result";
    }

    @RequiresPermissions("sts:purchase:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable Long id, @ModelAttribute PurchaseForm purchaseForm, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".purchaseForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        PurchaseEntity purchaseEntity = purchaseService.get(id);
        if (Objects.isNull(purchaseEntity)) {
            logger.error("修改进货单,未查询[id={}]的进货单信息", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据!");
            return "redirect:/error";
        }

        purchaseForm.setTotalAmount(Double.valueOf(purchaseEntity.getTotalAmount()));
        purchaseForm.setPayedAmount(Double.valueOf(purchaseEntity.getPayedAmount()));
        purchaseForm.setUnPayedAmount(Double.valueOf(purchaseEntity.getUnPayedAmount()));

        Long documentId = purchaseEntity.getPurchaseContractUrl();
        if (documentId != null) {
            DocumentEntity documentEntity = documentService.get(documentId);
            purchaseForm.setDocument(documentEntity);
        }

        purchaseForm.setPurchase(purchaseEntity);

        ApiRequest apiRequest = ApiRequest.newInstance();
        apiRequest.filterEqual("valid", EnableDisableStatus.ENABLE);
        ApiRequestPage apiRequestPage = ApiRequestPage.newInstance().paging(0, 100)
                .addOrder("id", PageOrderType.ASC);
        List<SupplierEntity> supplierEntityList = ApiPageRequestHelper.request(apiRequest, apiRequestPage, supplierService::findAll);
        List<SupplierItemEntity> supplierItemEntities = supplierItemService.findBySupplierId(purchaseEntity.getSupplierId());

        List<PurchaseItemEntity> purchaseItemEntities = purchaseItemService.findByPurchaseId(purchaseEntity.getId());
        purchaseForm.setPurchaseItemList(purchaseItemEntities);

        modelMap.put("action", "update");
        modelMap.put("supplierList", supplierEntityList);
        modelMap.put("supplierItemList", supplierItemEntities);
        return "purchase/purchase/edit";
    }

    @RequiresPermissions("sts:purchase:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(PurchaseForm purchaseForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(purchaseForm);
            return "redirect:/purchase/update/" + purchaseForm.getPurchase().getId();
        }

        PurchaseEntity purchase = purchaseForm.getPurchase();

        Long supplierId = purchase.getSupplierId();
        if (supplierId == null) {
            logger.error("请选择供应商");
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "请选择供应商!");
            return "redirect:/purchase/update/" + purchaseForm.getPurchase().getId();
        }

        PurchaseEntity purchaseEntity = purchaseService.get(purchase.getId());
        if (purchaseEntity.getInBound() == YesNoStatus.NO) {
            purchaseEntity.setOperator(purchase.getOperator());
            purchaseEntity.setSupplierId(purchase.getSupplierId());
        }
        purchaseEntity.setPurchaseTime(purchase.getPurchaseTime());
        Double totalAmount = CoreMathUtils.mul(purchaseForm.getTotalAmount(), 100L);
        purchaseEntity.setTotalAmount(totalAmount.longValue());
        Double payedAmount = CoreMathUtils.mul(purchaseForm.getPayedAmount(), 100L);
        purchaseEntity.setPayedAmount(payedAmount.longValue());
        Double unPayedAmount = CoreMathUtils.mul(purchaseForm.getUnPayedAmount(), 100L);
        purchaseEntity.setUnPayedAmount(unPayedAmount.longValue());
        purchaseEntity.setMakeOutInvoiceStatus(purchase.getMakeOutInvoiceStatus());

        purchaseEntity.setProposer(purchase.getProposer());
        purchaseEntity.setOperator(purchase.getOperator());

        List<PurchaseItemEntity> purchaseItemList = purchaseForm.getPurchaseItemList();
        List<PurchaseItemEntity> purchaseItemEntities = Lists.newArrayList();

        purchaseItemList.forEach(purchaseItemEntity -> {
            if (purchaseItemEntity.getSupplierItemId() != null && purchaseItemEntity.getSupplierItemId() > 0) {
                purchaseItemEntities.add(purchaseItemEntity);
            }
        });
        purchaseEntity.setPurchaseItemEntityList(purchaseItemEntities);

        DocumentEntity document = purchaseForm.getDocument();
        if (StringUtils.isEmpty(document.getName())) {
            purchaseService.save(purchaseEntity);
        } else {
            purchaseService.save(purchaseEntity, document);
        }
        
        return "result";
    }

    @RequiresPermissions("sts:purchase:view")
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable Long id, ModelMap modelMap) {
        PurchaseEntity purchaseEntity = purchaseService.get(id);
        modelMap.put("purchase", purchaseEntity);

        Long documentId = purchaseEntity.getPurchaseContractUrl();
        if (documentId != null) {
            DocumentEntity documentEntity = documentService.get(documentId);
            modelMap.addAttribute("document", documentEntity);
        }

        return "purchase/purchase/view";
    }

    @RequiresPermissions("sts:purchase:disable")
    @RequestMapping(value = "/disable/{id}", method = RequestMethod.GET)
    public String disable(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        PurchaseEntity purchaseEntity = purchaseService.get(id);
        if (Objects.isNull(purchaseEntity)) {
            logger.error("删除采购合同信息,未查询[id={}]的采购合同信息", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "未查询[id={"+id+"}]的采购合同信息!");
            return "redirect:/error";
        }

        if (purchaseEntity.getInBound() == YesNoStatus.YES) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "[id={"+id+"}]的采购合同信息已入库，无法删除!");
            return "redirect:/purchase/list";
        }
        purchaseEntity.setValid(EnableDisableStatus.DISABLE);
        purchaseService.update(purchaseEntity);
        return "redirect:/purchase/list";
    }

    @RequiresPermissions("sts:purchase:createItem")
    @RequestMapping(value = "/createItem/{purchaseId}", method = RequestMethod.GET)
    public String createItem(@PathVariable Long purchaseId, @ModelAttribute PurchaseForm purchaseForm, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".purchaseForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        if (Objects.isNull(purchaseForm.getPurchaseItem())) {
            purchaseForm.setPurchaseItem(new PurchaseItemEntity());
        }

        PurchaseEntity purchaseEntity = purchaseService.get(purchaseId);

        List<SupplierItemEntity> supplierItemEntities = supplierItemService.findBySupplierId(purchaseEntity.getSupplierId());

        modelMap.put("supplierItemList", supplierItemEntities);
        return "purchase/purchase/editItem";
    }

    @RequiresPermissions("sts:purchase:createItem")
    @RequestMapping(value = "/createItem", method = RequestMethod.POST)
    public String createItem(PurchaseForm purchaseForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(purchaseForm);
            return "redirect:/purchase/createItem/" + purchaseForm.getPurchase().getId();
        }
        PurchaseItemEntity purchaseItemEntity = purchaseForm.getPurchaseItem();
        purchaseItemEntity.setPurchaseId(purchaseForm.getPurchase().getId());

        purchaseItemService.save(purchaseItemEntity);
        return "redirect:/supplier/createItem/" + purchaseForm.getPurchase().getId();
    }

    @RequiresPermissions("sts:purchase:disableItem")
    @RequestMapping(value = "/disable/item/{id}", method = RequestMethod.GET)
    public String disableItem(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        PurchaseItemEntity purchaseItemEntity = purchaseItemService.get(id);
        if (Objects.isNull(purchaseItemEntity)) {
            logger.error("删除产品信息,未查询[id={}]的产品信息", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "未查询到编号为["+id+"]的产品信息!");
            return "redirect:/error";
        }
        supplierItemService.delete(id);
        return "redirect:/purchase/createItem/" + purchaseItemEntity.getPurchaseId();
    }

    @RequiresPermissions("sts:purchase:selectSupplierItem")
    @RequestMapping(value = "/selectSupplierItem/{supplierId}", method = RequestMethod.GET)
    @ResponseBody
    public String selectSupplierItem(@PathVariable Long supplierId, ModelMap modelMap) throws JsonProcessingException {
        List<SupplierItemEntity> supplierItemEntities = supplierItemService.findBySupplierId(supplierId);
        return objectMapper.writeValueAsString(supplierItemEntities);
    }

    @RequiresPermissions("sts:purchase:view")
    @RequestMapping(value = "/viewDetail/{id}", method = RequestMethod.GET)
    public String viewDetail(@PathVariable Long id, ModelMap modelMap) {
        List<PurchaseItemEntity> purchaseItemEntities = purchaseItemService.findByPurchaseId(id);

        purchaseItemEntities.forEach(purchaseItemEntity -> {
            SupplierItemEntity supplierItemEntity = supplierItemService.get(purchaseItemEntity.getSupplierItemId());
            purchaseItemEntity.setSupplierItem(supplierItemEntity);
        });
        modelMap.put("purchaseItemList", purchaseItemEntities);
        return "purchase/purchase/viewDetail";
    }
}
