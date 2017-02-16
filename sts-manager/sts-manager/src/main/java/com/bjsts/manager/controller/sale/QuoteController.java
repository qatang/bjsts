package com.bjsts.manager.controller.sale;

import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.manager.core.constants.GlobalConstants;
import com.bjsts.manager.core.controller.AbstractController;
import com.bjsts.manager.entity.document.DocumentEntity;
import com.bjsts.manager.entity.sale.PlanEntity;
import com.bjsts.manager.enums.sale.PlanStatus;
import com.bjsts.manager.enums.sale.PlanType;
import com.bjsts.manager.enums.sale.SourceType;
import com.bjsts.manager.form.sale.QuoteForm;
import com.bjsts.manager.query.sale.QuoteSearchable;
import com.bjsts.manager.service.document.DocumentService;
import com.bjsts.manager.service.sale.ProductOrderService;
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
 * 报价管理
 * @author jinsheng
 * @since 2016-04-26 16:56
 */
@Controller
@RequestMapping("/quote")
@SessionAttributes("QuoteSearchable")
public class QuoteController extends AbstractController {

    @Autowired
    private ProductOrderService productOrderService;

    @Autowired
    private DocumentService documentService;

    @Value("${file.external.url}")
    private String fileExternalUrl;

    @ModelAttribute("planTypeList")
    public List<PlanType> getPlanTypeList() {
        return PlanType.list();
    }

    @ModelAttribute("sourceTypeList")
    public List<SourceType> getSourceTypeList() {
        return SourceType.list();
    }

    @RequiresPermissions("sts:quote:list")
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(QuoteSearchable quoteSearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable, ModelMap modelMap) {
/*        List<PlanStatus> planStatuses = Lists.newArrayList();
        planStatuses.add(PlanStatus.ASK_PRICE);
        quoteSearchable.setPlanStatusList(planStatuses);*/
        ApiResponse<PlanEntity> apiResponse = productOrderService.findAll(quoteSearchable, pageable);
        Page<PlanEntity> page = new PageImpl<>(Lists.newArrayList(apiResponse.getPagedData()), pageable, apiResponse.getTotal());
        modelMap.addAttribute("page", page);
        return "sale/quote/list";
    }

    @RequiresPermissions("sts:quote:create")
    @RequestMapping(value = "/create/{id}", method = RequestMethod.GET)
    public String update(@PathVariable Long id, @ModelAttribute QuoteForm quoteForm, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".quoteForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        PlanEntity planEntity = productOrderService.get(id);
        if (Objects.isNull(planEntity)) {
            logger.error("修改订单,未查询[id={}]的订单信息", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据!");
            return "redirect:/error";
        }
        quoteForm.setProductOrder(planEntity);

        Long documentId = planEntity.getDocumentId();
        if (documentId != null) {
            DocumentEntity documentEntity = documentService.get(documentId);
            quoteForm.setCustomerFileUrl(documentEntity.getUrl());
        }

        Long quoteFileId = planEntity.getQuoteFileId();
        if (quoteFileId != null) {
            DocumentEntity documentEntity = documentService.get(quoteFileId);
            quoteForm.setQuoteFileUrl(documentEntity.getUrl());
        }
        modelMap.put("action", "create");
        return "sale/quote/edit";
    }

    @RequiresPermissions("sts:quote:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String update(QuoteForm quoteForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(quoteForm);
            return "redirect:/sale/quote/update/" + quoteForm.getProductOrder().getId();
        }
        PlanEntity quote = quoteForm.getProductOrder();
        PlanEntity planEntity = productOrderService.get(quote.getId());
        planEntity.setQuoter(quote.getQuoter());
        planEntity.setQuoteTime(quote.getQuoteTime());
        planEntity.setPlanStatus(PlanStatus.QUOTE_FOR_SALE);

        String quoteFileUrl = quoteForm.getQuoteFileUrl();
        if (StringUtils.isEmpty(quoteFileUrl)) {
            productOrderService.save(planEntity);
        } else {
            productOrderService.saveQuote(planEntity, quoteFileUrl);
        }
        return "result";
    }

    @RequiresPermissions("sts:quote:view")
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable Long id, ModelMap modelMap) {
        PlanEntity planEntity = productOrderService.get(id);

        Long documentId = planEntity.getDocumentId();
        if (documentId != null) {
            DocumentEntity documentEntity = documentService.get(documentId);
            modelMap.addAttribute("customerFileUrl", documentEntity.getUrl());
        }

        Long quoteFileId = planEntity.getQuoteFileId();
        if (quoteFileId != null) {
            DocumentEntity documentEntity = documentService.get(quoteFileId);
            modelMap.addAttribute("quoteFileUrl", documentEntity.getUrl());
        }
        modelMap.put("quote", planEntity);
        return "sale/quote/view";
    }

    @RequiresPermissions("sts:quote:upload")
    @RequestMapping("/upload")
    @ResponseBody
    public Map<String, String> upload(@RequestParam(value = "file", required = false) MultipartFile file, ModelMap modelMap) {
        Map<String, String> map = new HashMap<>();
        if (!file.isEmpty()) {
            try {
                InputStream input = file.getInputStream();

                String fullFileDir = fileExternalUrl + File.separator + GlobalConstants.QUOTE_FILE + File.separator;
                if (!FileUtils.createDirectory(fullFileDir)) {
                    String message = "创建文件夹失败，请重试!";
                    map.put("message", message);
                    return map;
                }

                String fileName = File.separator + GlobalConstants.QUOTE_FILE + File.separator + file.getOriginalFilename();
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
}
