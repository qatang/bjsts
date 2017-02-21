package com.bjsts.manager.controller.purchase;

import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.core.enums.YesNoStatus;
import com.bjsts.core.util.CoreMathUtils;
import com.bjsts.manager.core.constants.GlobalConstants;
import com.bjsts.manager.core.controller.AbstractController;
import com.bjsts.manager.entity.document.DocumentEntity;
import com.bjsts.manager.entity.purchase.PurchaseEntity;
import com.bjsts.manager.enums.invoice.MakeOutInvoiceStatus;
import com.bjsts.manager.form.purchase.PurchaseForm;
import com.bjsts.manager.query.purchase.PurchaseSearchable;
import com.bjsts.manager.service.document.DocumentService;
import com.bjsts.manager.service.idgenerator.IdGeneratorService;
import com.bjsts.manager.service.purchase.PurchaseService;
import com.bjsts.manager.utils.FileUtils;
import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author jinsheng
 * @since 2016-04-26 16:56
 */
@Controller
@RequestMapping("/purchase")
@SessionAttributes("purchaseSearchable")
public class PurchaseController extends AbstractController {

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private IdGeneratorService idGeneratorService;

    @Autowired
    private DocumentService documentService;

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
        Page<PurchaseEntity> page = new PageImpl<>(Lists.newArrayList(apiResponse.getPagedData()), pageable, apiResponse.getTotal());
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
        modelMap.put("action", "create");
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

        purchaseEntity.setPurchaseNo(idGeneratorService.generateDateFormatted(PurchaseEntity.SEQ_ID_GENERATOR));

        Double totalAmount = CoreMathUtils.mul(purchaseForm.getTotalAmount(), 100L);
        purchaseEntity.setTotalAmount(totalAmount.longValue());

        Double payedAmount = CoreMathUtils.mul(purchaseForm.getPayedAmount(), 100L);
        purchaseEntity.setPayedAmount(payedAmount.longValue());

        Double unpayedAmount = CoreMathUtils.mul(purchaseForm.getUnPayedAmount(), 100L);
        purchaseEntity.setUnPayedAmount(unpayedAmount.longValue());

        purchaseEntity.setInBound(YesNoStatus.NO);
        
        String purchaseUrl = purchaseForm.getPurchaseContractUrl();
        if (StringUtils.isEmpty(purchaseUrl)) {
            purchaseService.save(purchaseEntity);
        } else {
            purchaseService.save(purchaseEntity, purchaseUrl);
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
            purchaseForm.setPurchaseContractUrl(documentEntity.getUrl());
        }

        purchaseForm.setPurchase(purchaseEntity);
        modelMap.put("action", "update");
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
        PurchaseEntity purchaseEntity = purchaseService.get(purchase.getId());
        if (purchaseEntity.getInBound() == YesNoStatus.NO) {
            purchaseEntity.setProductName(purchase.getProductName());
            purchaseEntity.setProductModel(purchase.getProductModel());
            purchaseEntity.setQuantity(purchase.getQuantity());
            purchaseEntity.setOperator(purchase.getOperator());
            purchaseEntity.setSupplier(purchase.getSupplier());
            purchaseEntity.setSupplierLinkman(purchase.getSupplierLinkman());
            purchaseEntity.setSupplierMobile(purchase.getSupplierMobile());
        }
        purchaseEntity.setPurchaseTime(purchase.getPurchaseTime());
        Double totalAmount = CoreMathUtils.mul(purchaseForm.getTotalAmount(), 100L);
        purchaseEntity.setTotalAmount(totalAmount.longValue());
        Double payedAmount = CoreMathUtils.mul(purchaseForm.getPayedAmount(), 100L);
        purchaseEntity.setPayedAmount(payedAmount.longValue());
        Double unPayedAmount = CoreMathUtils.mul(purchaseForm.getUnPayedAmount(), 100L);
        purchaseEntity.setUnPayedAmount(unPayedAmount.longValue());
        purchaseEntity.setMakeOutInvoiceStatus(purchase.getMakeOutInvoiceStatus());

        String purchaseUrl = purchaseForm.getPurchaseContractUrl();
        if (StringUtils.isEmpty(purchaseUrl)) {
            purchaseService.save(purchaseEntity);
        } else {
            purchaseService.save(purchaseEntity, purchaseUrl);
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
            modelMap.addAttribute("purchaseContractUrl", documentEntity.getUrl());
        }

        return "purchase/purchase/view";
    }

    @RequiresPermissions("sts:purchase:upload")
    @RequestMapping("/upload")
    @ResponseBody
    public Map<String, String> upload(@RequestParam(value = "file", required = false) MultipartFile file, ModelMap modelMap) {
        Map<String, String> map = new HashMap<>();
        if (!file.isEmpty()) {
            try {
                InputStream input = file.getInputStream();

                String fullFileDir = fileExternalUrl + File.separator + GlobalConstants.PURCHASE_FILE + File.separator;
                if (!FileUtils.createDirectory(fullFileDir)) {
                    String message = "创建文件夹失败，请重试!";
                    map.put("message", message);
                    return map;
                }

                String fileName = File.separator + GlobalConstants.PURCHASE_FILE + File.separator + file.getOriginalFilename();
                OutputStream output = new FileOutputStream(fileExternalUrl + fileName);
                IOUtils.copy(input, output);

                output.close();
                input.close();

                String message = "上传成功!";

                map.put("path", fileName);
                map.put("message", message);
            } catch (Exception e) {
                map.put("message", "上传失败 => " + e.getMessage());
            }
        } else {
            map.put("message", "上传失败，文件为空.");
        }
        return map;
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
}
