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
import com.bjsts.manager.entity.invoice.SaleInvoiceEntity;
import com.bjsts.manager.entity.sale.PlanEntity;
import com.bjsts.manager.enums.invoice.InvoiceCategory;
import com.bjsts.manager.enums.invoice.InvoiceStatus;
import com.bjsts.manager.form.invoice.SaleInvoiceForm;
import com.bjsts.manager.query.invoice.SaleInvoiceSearchable;
import com.bjsts.manager.query.invoice.SaleInvoiceSum;
import com.bjsts.manager.service.document.DocumentService;
import com.bjsts.manager.service.invoice.SaleInvoiceService;
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
@RequestMapping("/saleInvoice")
@SessionAttributes("saleInvoiceSearchable")
public class SaleInvoiceController extends AbstractController {

    @Autowired
    private SaleInvoiceService saleInvoiceService;

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

    @ModelAttribute("invoiceStatusList")
    public List<InvoiceStatus> getInvoiceStatusList() {
        return InvoiceStatus.list();
    }

    @ModelAttribute("allInvoiceCategoryList")
    public List<InvoiceCategory> getAllInvoiceCategoryList() {
        return InvoiceCategory.listAll();
    }

    @ModelAttribute("allInvoiceStatusList")
    public List<InvoiceStatus> getAllInvoiceStatusList() {
        return InvoiceStatus.listAll();
    }

    @RequiresPermissions("sts:saleInvoice:list")
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(SaleInvoiceSearchable saleInvoiceSearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable, ModelMap modelMap) {
        ApiResponse<SaleInvoiceEntity> apiResponse = saleInvoiceService.findAll(saleInvoiceSearchable, pageable);
        Page<SaleInvoiceEntity> page = new PageImpl<>(Lists.newArrayList(apiResponse.getPagedData()), pageable, apiResponse.getTotal());
        modelMap.addAttribute("page", page);

        if (page.getTotalElements() > 0) {
            SaleInvoiceSum saleInvoiceSum = saleInvoiceService.sumAll(saleInvoiceSearchable);
            String successMessage = String.format("<b>开票总金额</b>: <b style='color:#ff0000'>%s</b>元",
                    CoreMathUtils.formatMoney(saleInvoiceSum.getAmount()));
            modelMap.addAttribute(SUCCESS_MESSAGE_KEY, successMessage);
        }

        return "invoice/saleInvoice/saleList";
    }

    @RequiresPermissions("sts:saleInvoice:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(@ModelAttribute SaleInvoiceForm saleInvoiceForm, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".saleInvoiceForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        if (Objects.isNull(saleInvoiceForm.getSaleInvoice())) {
            saleInvoiceForm.setSaleInvoice(new SaleInvoiceEntity());
        }

        ApiRequest apiRequest = ApiRequest.newInstance();
        apiRequest.filterEqual("valid", EnableDisableStatus.ENABLE);

        ApiRequestPage apiRequestPage = ApiRequestPage.newInstance().paging(0, 100)
                .addOrder("id", PageOrderType.ASC);

        List<PlanEntity> planEntityList = ApiPageRequestHelper.request(apiRequest, apiRequestPage, productOrderService::findAll);

        modelMap.put("action", "create");
        modelMap.put("planList", planEntityList);
        return "invoice/saleInvoice/saleEdit";
    }

    @RequiresPermissions("sts:saleInvoice:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(SaleInvoiceForm saleInvoiceForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(saleInvoiceForm);
            return "redirect:/saleInvoice/create";
        }
        SaleInvoiceEntity saleInvoiceEntity = saleInvoiceForm.getSaleInvoice();

        Double amount = CoreMathUtils.mul(saleInvoiceForm.getAmount(), 100L);
        saleInvoiceEntity.setAmount(amount.longValue());

        DocumentEntity documentEntity = saleInvoiceForm.getDocument();
        if (StringUtils.isEmpty(documentEntity.getName())) {
            saleInvoiceService.save(saleInvoiceEntity);
        } else {
            saleInvoiceService.save(saleInvoiceEntity, documentEntity);
        }
        return "result";
    }

    @RequiresPermissions("sts:saleInvoice:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable Long id, @ModelAttribute SaleInvoiceForm saleInvoiceForm, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".saleInvoiceForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        SaleInvoiceEntity saleInvoiceEntity = saleInvoiceService.get(id);
        if (Objects.isNull(saleInvoiceEntity)) {
            logger.error("修改发票,未查询[id={}]的发票信息", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据!");
            return "redirect:/error";
        }

        ApiRequest apiRequest = ApiRequest.newInstance();
        apiRequest.filterEqual("valid", EnableDisableStatus.ENABLE);

        ApiRequestPage apiRequestPage = ApiRequestPage.newInstance().paging(0, 100)
                .addOrder("id", PageOrderType.ASC);

        List<PlanEntity> planEntityList = ApiPageRequestHelper.request(apiRequest, apiRequestPage, productOrderService::findAll);

        saleInvoiceForm.setAmount(Double.valueOf(saleInvoiceEntity.getAmount()));

        Long documentId = saleInvoiceEntity.getInvoiceUrl();
        if (documentId != null && documentId != 0) {
            DocumentEntity documentEntity = documentService.get(documentId);
            saleInvoiceForm.setDocument(documentEntity);
        }

        saleInvoiceForm.setSaleInvoice(saleInvoiceEntity);
        modelMap.put("action", "update");
        modelMap.put("planList", planEntityList);
        return "invoice/saleInvoice/saleEdit";
    }

    @RequiresPermissions("sts:saleInvoice:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(SaleInvoiceForm saleInvoiceForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(saleInvoiceForm);
            return "redirect:/saleInvoice/update/" + saleInvoiceForm.getSaleInvoice().getId();
        }
        SaleInvoiceEntity saleInvoice = saleInvoiceForm.getSaleInvoice();
        SaleInvoiceEntity saleInvoiceEntity = saleInvoiceService.get(saleInvoice.getId());
        saleInvoiceEntity.setPlanNo(saleInvoice.getPlanNo());
        saleInvoiceEntity.setPlanContent(saleInvoice.getPlanContent());
        saleInvoiceEntity.setInvoiceCategory(saleInvoice.getInvoiceCategory());
        saleInvoiceEntity.setInvoiceNo(saleInvoice.getInvoiceNo());
        saleInvoiceEntity.setCustomer(saleInvoice.getCustomer());
        saleInvoiceEntity.setInvoiceDate(saleInvoice.getInvoiceDate());
        Double amount = CoreMathUtils.mul(saleInvoiceForm.getAmount(), 100L);
        saleInvoiceEntity.setAmount(amount.longValue());
        saleInvoiceEntity.setContent(saleInvoice.getContent());
        saleInvoiceEntity.setInvoiceStatus(saleInvoice.getInvoiceStatus());

        DocumentEntity documentEntity = saleInvoiceForm.getDocument();
        if (StringUtils.isEmpty(documentEntity.getName())) {
            saleInvoiceService.save(saleInvoiceEntity);
        } else {
            saleInvoiceService.save(saleInvoiceEntity, documentEntity);
        }

        return "result";
    }

    @RequiresPermissions("sts:saleInvoice:view")
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable Long id, ModelMap modelMap) {
        SaleInvoiceEntity saleInvoiceEntity = saleInvoiceService.get(id);

        modelMap.put("saleInvoice", saleInvoiceEntity);

        Long documentId = saleInvoiceEntity.getInvoiceUrl();
        if (documentId != null && documentId != 0) {
            DocumentEntity documentEntity = documentService.get(documentId);
            modelMap.addAttribute("document", documentEntity);
        }
        return "invoice/saleInvoice/saleView";
    }

    @RequiresPermissions("sts:saleInvoice:disable")
    @RequestMapping(value = "/disable/{id}", method = RequestMethod.GET)
    public String disable(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        SaleInvoiceEntity saleInvoiceEntity = saleInvoiceService.get(id);
        if (Objects.isNull(saleInvoiceEntity)) {
            logger.error("删除发票,未查询[id={}]的发票", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "未查询[id={"+id+"}]的发票!");
            return "redirect:/error";
        }
        
        saleInvoiceEntity.setValid(EnableDisableStatus.DISABLE);
        saleInvoiceService.update(saleInvoiceEntity);
        return "redirect:/saleInvoice/list";
    }
}
