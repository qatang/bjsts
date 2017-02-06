package com.bjsts.manager.controller.sale;

import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.manager.core.constants.GlobalConstants;
import com.bjsts.manager.core.controller.AbstractController;
import com.bjsts.manager.entity.document.DocumentEntity;
import com.bjsts.manager.entity.sale.ContractEntity;
import com.bjsts.manager.entity.sale.PlanEntity;
import com.bjsts.manager.enums.sale.ContractStatus;
import com.bjsts.manager.form.sale.ContractForm;
import com.bjsts.manager.query.sale.ContractSearchable;
import com.bjsts.manager.service.document.DocumentService;
import com.bjsts.manager.service.idgenerator.IdGeneratorService;
import com.bjsts.manager.service.sale.ContractService;
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
 * @author jinsheng
 * @since 2016-04-26 16:56
 */
@Controller
@RequestMapping("/contract")
@SessionAttributes("contractSearchable")
public class ContractController extends AbstractController {

    @Autowired
    private ContractService contractService;

    @Autowired
    private ProductOrderService productOrderService;

    @Autowired
    private IdGeneratorService idGeneratorService;

    @Autowired
    private DocumentService documentService;

    @Value("${file.external.url}")
    private String fileExternalUrl;

    @ModelAttribute("contractStatusList")
    public List<ContractStatus> getContractStatusList() {
        return ContractStatus.list();
    }

    @RequiresPermissions("sts:contract:list")
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(ContractSearchable contractSearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable, ModelMap modelMap) {
        ApiResponse<ContractEntity> apiResponse = contractService.findAll(contractSearchable, pageable);
        Page<ContractEntity> page = new PageImpl<>(Lists.newArrayList(apiResponse.getPagedData()), pageable, apiResponse.getTotal());
        modelMap.addAttribute("page", page);
        return "sale/contract/list";
    }

    @RequiresPermissions("sts:contract:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(@ModelAttribute ContractForm contractForm, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".contractForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        if (Objects.isNull(contractForm.getContract())) {
            contractForm.setContract(new ContractEntity());
        }

        modelMap.put("action", "create");
        return "sale/contract/edit";
    }

    @RequiresPermissions("sts:contract:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(ContractForm contractForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(contractForm);
            return "redirect:/contract/create";
        }
        ContractEntity contractEntity = contractForm.getContract();
        contractEntity.setContractNo(idGeneratorService.generateDateFormatted(ContractEntity.SEQ_ID_GENERATOR));

        String contractUrl = contractForm.getContractUrl();
        if (StringUtils.isEmpty(contractUrl)) {
            contractService.save(contractEntity);
        } else {
            contractService.save(contractEntity, contractUrl);
        }
        return "result";
    }

    @RequiresPermissions("sts:contract:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable Long id, @ModelAttribute ContractForm contractForm, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".contractForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        ContractEntity contractEntity = contractService.get(id);
        if (Objects.isNull(contractEntity)) {
            logger.error("修改合同,未查询[id={}]的合同信息", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据!");
            return "redirect:/error";
        }
        contractForm.setContract(contractEntity);

        Long documentId = contractEntity.getContractUrl();
        if (documentId != null) {
            DocumentEntity documentEntity = documentService.get(documentId);
            contractForm.setContractUrl(documentEntity.getUrl());
        }

        modelMap.put("action", "update");
        return "sale/contract/edit";
    }

    @RequiresPermissions("sts:contract:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(ContractForm contractForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(contractForm);
            return "redirect:/contract/update/" + contractForm.getContract().getId();
        }
        ContractEntity contract = contractForm.getContract();
        ContractEntity contractEntity = contractService.get(contract.getId());
        contractEntity.setStatus(contract.getStatus());
        contractEntity.setContent(contract.getContent());
        contractEntity.setChangeContent(contract.getChangeContent());
        contractEntity.setQualityTime(contract.getQualityTime());
        contractEntity.setSignTime(contract.getSignTime());
        contractEntity.setAmount(contract.getAmount());

        String contractUrl = contractForm.getContractUrl();
        if (StringUtils.isEmpty(contractUrl)) {
            contractService.save(contractEntity);
        } else {
            contractService.save(contractEntity, contractUrl);
        }
        return "result";
    }

    @RequiresPermissions("sts:contract:view")
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable Long id, ModelMap modelMap) {
        ContractEntity contractEntity = contractService.get(id);

        modelMap.put("contract", contractEntity);

        Long documentId = contractEntity.getContractUrl();
        if (documentId != null) {
            DocumentEntity documentEntity = documentService.get(documentId);
            modelMap.addAttribute("contractUrl", documentEntity.getUrl());
        }

        return "sale/contract/view";
    }

    @RequiresPermissions("sts:contract:findPlan")
    @RequestMapping("/findPlan/{planNo}")
    @ResponseBody
    public PlanEntity findPlan(@PathVariable String planNo) {
        PlanEntity planEntity = null;
        try {
            planEntity = productOrderService.findByPlanNo(planNo);
        } catch (Exception e) {
            logger.error("调用productOrderService获取项目信息接口出错！");
            logger.error(e.getMessage(), e);
        }
        return planEntity;
    }

    @RequiresPermissions("sts:contract:upload")
    @RequestMapping("/upload")
    @ResponseBody
    public Map<String, String> upload(@RequestParam(value = "file", required = false) MultipartFile file, ModelMap modelMap) {
        Map<String, String> map = new HashMap<>();
        if (!file.isEmpty()) {
            try {
                InputStream input = file.getInputStream();

                String fullFileDir = fileExternalUrl + File.separator + GlobalConstants.CONTRACT_FILE + File.separator;
                if (!FileUtils.createDirectory(fullFileDir)) {
                    String message = "创建文件夹失败，请重试!";
                    map.put("message", message);
                    return map;
                }

                String fileName = File.separator + GlobalConstants.CONTRACT_FILE + File.separator + file.getOriginalFilename();
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
