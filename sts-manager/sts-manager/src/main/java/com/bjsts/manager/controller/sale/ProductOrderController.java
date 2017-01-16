package com.bjsts.manager.controller.sale;

import com.bjsts.manager.core.constants.GlobalConstants;
import com.bjsts.manager.core.controller.AbstractController;
import com.bjsts.manager.entity.document.DocumentEntity;
import com.bjsts.manager.entity.sale.PlanEntity;
import com.bjsts.manager.enums.sale.PlanStatus;
import com.bjsts.manager.enums.sale.PlanType;
import com.bjsts.manager.enums.sale.SourceType;
import com.bjsts.manager.form.sale.ProductOrderForm;
import com.bjsts.manager.query.sale.ProductOrderSearchable;
import com.bjsts.manager.service.document.DocumentService;
import com.bjsts.manager.service.idgenerator.IdGeneratorService;
import com.bjsts.manager.service.sale.ProductOrderService;
import com.bjsts.manager.utils.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@RequestMapping("/productOrder")
@SessionAttributes("productOrderSearchable")
public class ProductOrderController extends AbstractController {

    @Autowired
    private ProductOrderService productOrderService;

    @Autowired
    private IdGeneratorService idGeneratorService;

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

    @RequiresPermissions("sts:productOrder:list")
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(ProductOrderSearchable productOrderSearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable, ModelMap modelMap) {
        List<PlanEntity> planEntityList = productOrderService.findAll();
        modelMap.addAttribute("list", planEntityList);
        return "sale/productOrder/list";
    }

    @RequiresPermissions("sts:productOrder:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(@ModelAttribute ProductOrderForm productOrderForm, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".productOrderForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        if (Objects.isNull(productOrderForm.getProductOrder())) {
            productOrderForm.setProductOrder(new PlanEntity());
        }
        modelMap.put("action", "create");
        return "sale/productOrder/edit";
    }

    @RequiresPermissions("sts:productOrder:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(ProductOrderForm productOrderForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(productOrderForm);
            return "redirect:/sale/productOrder/create";
        }
        PlanEntity planEntity = productOrderForm.getProductOrder();
        planEntity.setPlanNo(idGeneratorService.generateDateFormatted(PlanEntity.SEQ_ID_GENERATOR));
        planEntity.setPlanStatus(PlanStatus.ASK_PRICE);

        String customFileUrl = productOrderForm.getCustomerFileUrl();
        if (StringUtils.isEmpty(customFileUrl)) {
            productOrderService.save(planEntity);
        } else {
            productOrderService.save(planEntity, customFileUrl);
        }
        return "result";
    }

    @RequiresPermissions("sts:productOrder:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable Long id, @ModelAttribute ProductOrderForm productOrderForm, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".productOrderForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        PlanEntity planEntity = productOrderService.get(id);
        if (Objects.isNull(planEntity)) {
            logger.error("修改订单,未查询[id={}]的订单信息", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据!");
            return "redirect:/error";
        }
        productOrderForm.setProductOrder(planEntity);

        Long documentId = planEntity.getDocumentId();
        if (documentId != null) {
            DocumentEntity documentEntity = documentService.get(documentId);
            productOrderForm.setCustomerFileUrl(documentEntity.getUrl());
        }
        modelMap.put("action", "update");
        return "sale/productOrder/edit";
    }

    @RequiresPermissions("sts:productOrder:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(ProductOrderForm productOrderForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(productOrderForm);
            return "redirect:/sale/productOrder/update/" + productOrderForm.getProductOrder().getId();
        }
        PlanEntity productOrder = productOrderForm.getProductOrder();
        PlanEntity planEntity = productOrderService.get(productOrder.getId());
        planEntity.setName(productOrder.getName());
        planEntity.setPlanType(productOrder.getPlanType());
        planEntity.setSourceType(productOrder.getSourceType());
        planEntity.setPriceTime(productOrder.getPriceTime());
        planEntity.setLocation(productOrder.getLocation());
        planEntity.setLinkman(productOrder.getLinkman());
        planEntity.setMobile(productOrder.getMobile());
        planEntity.setCompany(productOrder.getCompany());
        planEntity.setEmail(productOrder.getEmail());
        planEntity.setDescription(productOrder.getDescription());

        String customFileUrl = productOrderForm.getCustomerFileUrl();
        if (StringUtils.isEmpty(customFileUrl)) {
            productOrderService.save(planEntity);
        } else {
            productOrderService.save(planEntity, customFileUrl);
        }
        return "result";
    }

    @RequiresPermissions("sts:productOrder:view")
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable Long id, ModelMap modelMap) {
        PlanEntity planEntity = productOrderService.get(id);

        Long documentId = planEntity.getDocumentId();
        if (documentId != null) {
            DocumentEntity documentEntity = documentService.get(documentId);
            modelMap.addAttribute("customerFileUrl", documentEntity.getUrl());
        }
        modelMap.put("productOrder", planEntity);
        return "sale/productOrder/view";
    }

    @RequiresPermissions("sts:productOrder:upload")
    @RequestMapping("/upload")
    @ResponseBody
    public Map<String, String> upload(@RequestParam(value = "file", required = false) MultipartFile file, ModelMap modelMap) {
        Map<String, String> map = new HashMap<>();
        if (!file.isEmpty()) {
            try {
                InputStream input = file.getInputStream();

                String fullFileDir = fileExternalUrl + File.separator + GlobalConstants.CUSTOMER_FILE + File.separator;
                if (!FileUtils.createDirectory(fullFileDir)) {
                    String message = "创建文件夹失败，请重试!";
                    map.put("message", message);
                    return map;
                }

                String fileName = File.separator + GlobalConstants.CUSTOMER_FILE + File.separator + file.getOriginalFilename();
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
