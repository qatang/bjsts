package com.bjsts.manager.controller.purchase;

import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.core.util.CoreMathUtils;
import com.bjsts.manager.core.constants.GlobalConstants;
import com.bjsts.manager.core.controller.AbstractController;
import com.bjsts.manager.entity.sale.ContractEntity;
import com.bjsts.manager.entity.sale.PlanPayEntity;
import com.bjsts.manager.entity.system.UserEntity;
import com.bjsts.manager.enums.invoice.MakeOutInvoiceStatus;
import com.bjsts.manager.enums.sale.ContractStatus;
import com.bjsts.manager.form.sale.PlanPayForm;
import com.bjsts.manager.query.sale.ContractSearchable;
import com.bjsts.manager.service.invoice.InvoiceService;
import com.bjsts.manager.service.sale.ContractService;
import com.bjsts.manager.service.sale.PlanPayService;
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
 * @author jinsheng
 * @since 2016-04-26 16:56
 */
@Controller
@RequestMapping("/purchasePay")
@SessionAttributes("planPaySearchable")
public class PurchasePayController extends AbstractController {

    @Autowired
    private PlanPayService planPayService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private UserService userService;

    @ModelAttribute("invoiceStatusList")
    public List<MakeOutInvoiceStatus> getInvoiceStatusList() {
        return MakeOutInvoiceStatus.list();
    }

    @RequiresPermissions("sts:planPay:list")
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(ContractSearchable contractSearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable, ModelMap modelMap) {
        List<ContractStatus> contractStatusList = Lists.newArrayList(ContractStatus.EXECUTING, ContractStatus.EXECUTED);
        contractSearchable.setContractStatusList(contractStatusList);
        ApiResponse<ContractEntity> apiResponse = contractService.findAll(contractSearchable, pageable);
        List<ContractEntity> contractEntities = Lists.newArrayList(apiResponse.getPagedData());

        if (!contractEntities.isEmpty()) {
            contractEntities.forEach(contractEntity -> {
                //统计合同已付金额
                List<PlanPayEntity> planPayEntityList = planPayService.findByContractNo(contractEntity.getContractNo());
                Long payedAmount = 0L;
                for (PlanPayEntity planPayEntity : planPayEntityList) {
                    payedAmount = CoreMathUtils.add(planPayEntity.getAmount(), payedAmount);
                }
                contractEntity.setPayedAmount(payedAmount);

                //todo
                //统计开票金额
            });
        }

        Page<ContractEntity> page = new PageImpl<>(contractEntities, pageable, apiResponse.getTotal());
        modelMap.addAttribute("page", page);
        return "sale/planPay/list";
    }

    @RequiresPermissions("sts:planPay:create")
    @RequestMapping(value = "/create/{contractNo}", method = RequestMethod.GET)
    public String create(@PathVariable String contractNo, @ModelAttribute PlanPayForm planPayForm, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".planPayForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        if (Objects.isNull(planPayForm.getPlanPay())) {
            planPayForm.setPlanPay(new PlanPayEntity());
        }

        //统计合同已付金额
        List<PlanPayEntity> planPayEntityList = planPayService.findByContractNo(contractNo);
        Long payedAmount = 0L;
        for (PlanPayEntity planPayEntity : planPayEntityList) {
            payedAmount = CoreMathUtils.add(planPayEntity.getAmount(), payedAmount);
            UserEntity userEntity = userService.get(planPayEntity.getOperatorId());
            if (userEntity != null) {
                planPayEntity.setOperator(userEntity.getRealName());
            } else {
                planPayEntity.setOperator("");
            }
        }

        modelMap.put("action", "create");
        modelMap.put("planPayList", planPayEntityList);
        modelMap.put("payedAmount", payedAmount);
        modelMap.put("contractNo", contractNo);
        return "sale/planPay/edit";
    }

    @RequiresPermissions("sts:planPay:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(PlanPayForm planPayForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(planPayForm);
            return "redirect:/planPay/create";
        }
        PlanPayEntity planEntity = planPayForm.getPlanPay();
        Double amount = CoreMathUtils.mul(planPayForm.getAmount(), 100L);
        planEntity.setAmount(amount.longValue());

        UserEntity currentUser = (UserEntity) SecurityUtils.getSubject().getPrincipal();
        planEntity.setOperatorId(currentUser.getId());
        planPayService.save(planEntity);
        return "result";
    }

    @RequiresPermissions("sts:planPay:disable")
    @RequestMapping(value = "/disable/{id}", method = RequestMethod.GET)
    public String disable(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        PlanPayEntity planPayEntity = planPayService.get(id);
        if (Objects.isNull(planPayEntity)) {
            logger.error("删除项目付款信息,未查询[id={}]的项目付款信息", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "未查询到编号为["+id+"]的项目付款信息!");
            return "redirect:/error";
        }
        planPayService.delete(id);
        return "redirect:/planPay/list";
    }
}
