package com.bjsts.manager.controller.sale;

import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.core.util.CoreMathUtils;
import com.bjsts.manager.core.constants.GlobalConstants;
import com.bjsts.manager.core.controller.AbstractController;
import com.bjsts.manager.entity.document.DocumentEntity;
import com.bjsts.manager.entity.sale.ContractEntity;
import com.bjsts.manager.enums.sale.ContractStatus;
import com.bjsts.manager.form.sale.ContractForm;
import com.bjsts.manager.query.sale.ContractSearchable;
import com.bjsts.manager.query.sale.ContractSum;
import com.bjsts.manager.service.document.DocumentService;
import com.bjsts.manager.service.idgenerator.IdGeneratorService;
import com.bjsts.manager.service.sale.ContractService;
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
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
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
        List<ContractEntity> contractEntities = Lists.newArrayList(apiResponse.getPagedData());
        Page<ContractEntity> page = new PageImpl<>(contractEntities, pageable, apiResponse.getTotal());
        modelMap.addAttribute("page", page);
        if (!contractEntities.isEmpty()) {
            ContractSum contractSum = contractService.sumAll(contractSearchable);
            String successMessage = String.format("<b>合同总金额</b>: <b style='color:#ff0000'>%s</b>元",
                    CoreMathUtils.formatMoney(contractSum.getAmount()));
            modelMap.addAttribute(SUCCESS_MESSAGE_KEY, successMessage);
        }
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
            contractForm.setAmount(CoreMathUtils.mul(contractForm.getAmount(),100));
            redirectAttributes.addFlashAttribute(contractForm);
            return "redirect:/contract/create";
        }

        ContractEntity contractEntity = contractForm.getContract();

        String planNo = contractEntity.getPlanNo();
        ContractEntity existContract = contractService.findByPlanNo(planNo);
        if (existContract != null) {
            contractForm.setAmount(CoreMathUtils.mul(contractForm.getAmount(),100));
            result.addError(new ObjectError("planNo", "该项目已经创建合同"));
            redirectAttributes.addFlashAttribute(BINDING_RESULT_KEY, result.getAllErrors());
            redirectAttributes.addFlashAttribute(contractForm);
            return "redirect:/contract/create";
        }

        contractEntity.setContractNo(ContractEntity.SEQ_ID_PREFIX + idGeneratorService.generateDateFormatted(ContractEntity.SEQ_ID_GENERATOR));

        Double amount = CoreMathUtils.mul(contractForm.getAmount(), 100L);
        contractEntity.setAmount(amount.longValue());

        Double qualityAmount = CoreMathUtils.mul(contractForm.getQualityAmount(), 100L);
        contractEntity.setQualityAmount(qualityAmount.longValue());

        contractEntity.setStatus(ContractStatus.EXECUTE_NOT);

        String contractUrl = contractForm.getDocument().getUrl();
        if (StringUtils.isEmpty(contractUrl)) {
            contractService.save(contractEntity);
        } else {
            contractService.save(contractEntity, contractForm.getDocument());
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
        contractForm.setAmount(Double.valueOf(contractEntity.getAmount()));
        contractForm.setQualityAmount(Double.valueOf(contractEntity.getQualityAmount()));

        Long documentId = contractEntity.getContractUrl();
        if (documentId != null) {
            DocumentEntity documentEntity = documentService.get(documentId);
            contractForm.setDocument(documentEntity);
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
        contractEntity.setQualityTime(contract.getQualityTime());
        contractEntity.setSignTime(contract.getSignTime());
        Double amount = CoreMathUtils.mul(contractForm.getAmount(), 100L);
        contractEntity.setAmount(amount.longValue());

        Double qualityAmount = CoreMathUtils.mul(contractForm.getQualityAmount(), 100L);
        contractEntity.setQualityAmount(qualityAmount.longValue());

        contractEntity.setSign(contract.getSign());

        String contractUrl = contractForm.getDocument().getUrl();
        if (StringUtils.isEmpty(contractUrl)) {
            contractService.save(contractEntity);
        } else {
            contractService.save(contractEntity, contractForm.getDocument());
        }
        return "result";
    }

    @RequiresPermissions("sts:contract:updateStatus")
    @RequestMapping(value = "/updateStatus/{id}", method = RequestMethod.GET)
    public String updateStatus(@PathVariable Long id, @ModelAttribute ContractForm contractForm, RedirectAttributes redirectAttributes, ModelMap modelMap) {
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
        contractForm.setAmount(Double.valueOf(contractEntity.getAmount()));
        contractForm.setQualityAmount(Double.valueOf(contractEntity.getQualityAmount()));

        Long documentId = contractEntity.getContractUrl();
        if (documentId != null) {
            DocumentEntity documentEntity = documentService.get(documentId);
            contractForm.setDocument(documentEntity);
        }

        return "sale/contract/editStatus";
    }

    @RequiresPermissions("sts:contract:updateStatus")
    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    public String updateStatus(ContractForm contractForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(contractForm);
            return "redirect:/contract/update/" + contractForm.getContract().getId();
        }
        ContractEntity contract = contractForm.getContract();
        contractService.updateStatus(contract.getId(), contract.getStatus());
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
            modelMap.addAttribute("document", documentEntity);
        }

        return "sale/contract/view";
    }

    @RequiresPermissions("sts:contract:disable")
    @RequestMapping(value = "/disable/{id}", method = RequestMethod.GET)
    public String disable(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        ContractEntity contractEntity = contractService.get(id);
        if (Objects.isNull(contractEntity)) {
            logger.error("删除合同信息,未查询[id={}]的合同信息", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "未查询[id={"+id+"}]的合同信息!");
            return "redirect:/error";
        }
        contractEntity.setValid(EnableDisableStatus.DISABLE);
        contractService.update(contractEntity);
        return "redirect:/contract/list";
    }
}
