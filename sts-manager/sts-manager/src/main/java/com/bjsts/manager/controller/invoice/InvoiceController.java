package com.bjsts.manager.controller.invoice;

import com.bjsts.core.api.component.request.ApiPageRequestHelper;
import com.bjsts.core.api.request.ApiRequest;
import com.bjsts.core.api.request.ApiRequestPage;
import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.core.enums.PageOrderType;
import com.bjsts.core.util.CoreMathUtils;
import com.bjsts.manager.core.constants.GlobalConstants;
import com.bjsts.manager.core.controller.AbstractController;
import com.bjsts.manager.entity.document.DocumentEntity;
import com.bjsts.manager.entity.invoice.InvoiceEntity;
import com.bjsts.manager.entity.sale.PlanEntity;
import com.bjsts.manager.enums.invoice.InvoiceCategory;
import com.bjsts.manager.form.invoice.InvoiceForm;
import com.bjsts.manager.query.invoice.InvoiceSearchable;
import com.bjsts.manager.query.invoice.InvoiceSum;
import com.bjsts.manager.service.document.DocumentService;
import com.bjsts.manager.service.invoice.InvoiceService;
import com.bjsts.manager.service.sale.ProductOrderService;
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
 * @author jinsheng
 * @since 2016-04-26 16:56
 */
@Controller
@RequestMapping("/invoice")
@SessionAttributes("invoiceSearchable")
public class InvoiceController extends AbstractController {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private ProductOrderService productOrderService;

    @Value("${file.external.url}")
    private String fileExternalUrl;

    @ModelAttribute("invoiceCategoryList")
    public List<InvoiceCategory> getInvoiceCategoryList() {
        return InvoiceCategory.list();
    }

    @ModelAttribute("allInvoiceCategoryList")
    public List<InvoiceCategory> getAllInvoiceCategoryList() {
        return InvoiceCategory.listAll();
    }

    @RequiresPermissions("sts:invoice:list")
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(InvoiceSearchable invoiceSearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable, ModelMap modelMap) {
        ApiResponse<InvoiceEntity> apiResponse = invoiceService.findAll(invoiceSearchable, pageable);
        Page<InvoiceEntity> page = new PageImpl<>(Lists.newArrayList(apiResponse.getPagedData()), pageable, apiResponse.getTotal());
        modelMap.addAttribute("page", page);

        if (page.getTotalElements() > 0) {
            InvoiceSum invoiceSum = invoiceService.sumAll(invoiceSearchable);
            String successMessage = String.format("<b>开票总金额</b>: <b style='color:#ff0000'>%s</b>元",
                    CoreMathUtils.formatMoney(invoiceSum.getAmount()));
            modelMap.addAttribute(SUCCESS_MESSAGE_KEY, successMessage);
        }

        return "invoice/invoice/list";
    }

    @RequiresPermissions("sts:invoice:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(@ModelAttribute InvoiceForm invoiceForm, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".invoiceForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        if (Objects.isNull(invoiceForm.getInvoice())) {
            invoiceForm.setInvoice(new InvoiceEntity());
        }

        ApiRequest apiRequest = ApiRequest.newInstance();
        apiRequest.filterEqual("valid", EnableDisableStatus.ENABLE);

        ApiRequestPage apiRequestPage = ApiRequestPage.newInstance().paging(0, 100)
                .addOrder("id", PageOrderType.ASC);

        List<PlanEntity> planEntityList = ApiPageRequestHelper.request(apiRequest, apiRequestPage, productOrderService::findAll);

        modelMap.put("action", "create");
        modelMap.put("planList", planEntityList);
        return "invoice/invoice/edit";
    }

    @RequiresPermissions("sts:invoice:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(InvoiceForm invoiceForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(invoiceForm);
            return "redirect:/invoice/create";
        }
        InvoiceEntity invoiceEntity = invoiceForm.getInvoice();

        Double amount = CoreMathUtils.mul(invoiceForm.getAmount(), 100L);
        invoiceEntity.setAmount(amount.longValue());

        DocumentEntity documentEntity = invoiceForm.getDocument();
        if (StringUtils.isEmpty(documentEntity.getName())) {
            invoiceService.save(invoiceEntity);
        } else {
            invoiceService.save(invoiceEntity, documentEntity);
        }
        return "result";
    }

    @RequiresPermissions("sts:invoice:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable Long id, @ModelAttribute InvoiceForm invoiceForm, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".invoiceForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        InvoiceEntity invoiceEntity = invoiceService.get(id);
        if (Objects.isNull(invoiceEntity)) {
            logger.error("修改发票,未查询[id={}]的发票信息", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据!");
            return "redirect:/error";
        }

        ApiRequest apiRequest = ApiRequest.newInstance();
        apiRequest.filterEqual("valid", EnableDisableStatus.ENABLE);

        ApiRequestPage apiRequestPage = ApiRequestPage.newInstance().paging(0, 100)
                .addOrder("id", PageOrderType.ASC);

        List<PlanEntity> planEntityList = ApiPageRequestHelper.request(apiRequest, apiRequestPage, productOrderService::findAll);

        invoiceForm.setAmount(Double.valueOf(invoiceEntity.getAmount()));

        Long documentId = invoiceEntity.getInvoiceUrl();
        if (documentId != null && documentId != 0) {
            DocumentEntity documentEntity = documentService.get(documentId);
            invoiceForm.setDocument(documentEntity);
        }

        invoiceForm.setInvoice(invoiceEntity);
        modelMap.put("action", "update");
        modelMap.put("planList", planEntityList);
        return "invoice/invoice/edit";
    }

    @RequiresPermissions("sts:invoice:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(InvoiceForm invoiceForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(invoiceForm);
            return "redirect:/invoice/update/" + invoiceForm.getInvoice().getId();
        }
        InvoiceEntity invoice = invoiceForm.getInvoice();
        InvoiceEntity invoiceEntity = invoiceService.get(invoice.getId());
        invoiceEntity.setPlanNo(invoice.getPlanNo());
        invoiceEntity.setPlanContent(invoice.getPlanContent());
        invoiceEntity.setInvoiceCategory(invoice.getInvoiceCategory());
        invoiceEntity.setInvoiceNo(invoice.getInvoiceNo());
        invoiceEntity.setCustomer(invoice.getCustomer());
        invoiceEntity.setInvoiceDate(invoice.getInvoiceDate());
        Double amount = CoreMathUtils.mul(invoiceForm.getAmount(), 100L);
        invoiceEntity.setAmount(amount.longValue());
        invoiceEntity.setContent(invoice.getContent());
        invoiceEntity.setDeductionDate(invoice.getDeductionDate());

        DocumentEntity documentEntity = invoiceForm.getDocument();
        if (StringUtils.isEmpty(documentEntity.getName())) {
            invoiceService.save(invoiceEntity);
        } else {
            invoiceService.save(invoiceEntity, documentEntity);
        }

        return "result";
    }

    @RequiresPermissions("sts:invoice:view")
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable Long id, ModelMap modelMap) {
        InvoiceEntity invoiceEntity = invoiceService.get(id);

        modelMap.put("invoice", invoiceEntity);

        Long documentId = invoiceEntity.getInvoiceUrl();
        if (documentId != null && documentId != 0) {
            DocumentEntity documentEntity = documentService.get(documentId);
            modelMap.addAttribute("document", documentEntity);
        }
        return "invoice/invoice/view";
    }

    @RequiresPermissions("sts:invoice:disable")
    @RequestMapping(value = "/disable/{id}", method = RequestMethod.GET)
    public String disable(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        InvoiceEntity invoiceEntity = invoiceService.get(id);
        if (Objects.isNull(invoiceEntity)) {
            logger.error("删除发票,未查询[id={}]的发票", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "未查询[id={"+id+"}]的发票!");
            return "redirect:/error";
        }
        
        invoiceEntity.setValid(EnableDisableStatus.DISABLE);
        invoiceService.update(invoiceEntity);
        return "redirect:/invoice/list";
    }
}
