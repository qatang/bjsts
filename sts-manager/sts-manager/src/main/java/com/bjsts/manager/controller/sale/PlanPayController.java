package com.bjsts.manager.controller.sale;

import com.bjsts.core.api.component.request.ApiPageRequestHelper;
import com.bjsts.core.api.request.ApiRequest;
import com.bjsts.core.api.request.ApiRequestPage;
import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.core.enums.PageOrderType;
import com.bjsts.core.util.CoreMathUtils;
import com.bjsts.manager.core.constants.GlobalConstants;
import com.bjsts.manager.core.controller.AbstractController;
import com.bjsts.manager.entity.sale.ContractEntity;
import com.bjsts.manager.entity.sale.PlanEntity;
import com.bjsts.manager.entity.sale.PlanPayEntity;
import com.bjsts.manager.enums.invoice.MakeOutInvoiceStatus;
import com.bjsts.manager.form.sale.PlanPayForm;
import com.bjsts.manager.query.sale.PlanPaySearchable;
import com.bjsts.manager.service.sale.ContractService;
import com.bjsts.manager.service.sale.PlanPayService;
import com.bjsts.manager.service.sale.ProductOrderService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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
import java.util.Map;
import java.util.Objects;

/**
 * @author jinsheng
 * @since 2016-04-26 16:56
 */
@Controller
@RequestMapping("/planPay")
@SessionAttributes("planPaySearchable")
public class PlanPayController extends AbstractController {

    @Autowired
    private PlanPayService planPayService;

    @Autowired
    private ProductOrderService productOrderService;

    @Autowired
    private ContractService contractService;

    @ModelAttribute("invoiceStatusList")
    public List<MakeOutInvoiceStatus> getInvoiceStatusList() {
        return MakeOutInvoiceStatus.list();
    }

    @RequiresPermissions("sts:planPay:list")
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(PlanPaySearchable planPaySearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable, ModelMap modelMap) {
        ApiResponse<PlanPayEntity> apiResponse = planPayService.findAll(planPaySearchable, pageable);

        List<PlanPayEntity> planPayEntityList = Lists.newArrayList(apiResponse.getPagedData());

        for (PlanPayEntity planPayEntity : planPayEntityList) {
            PlanEntity planEntity = productOrderService.findByPlanNo(planPayEntity.getPlanNo());
            ContractEntity contractEntity = contractService.findByPlanNo(planEntity.getPlanNo());
            List<PlanPayEntity> payedPlanPayEntityList = planPayService.findByPlanNo(planEntity.getPlanNo());
            Long payedAmount = 0L;
            for (PlanPayEntity dbPlanPayEntity : payedPlanPayEntityList) {
                payedAmount = CoreMathUtils.add(payedAmount, dbPlanPayEntity.getAmount());
            }

            planPayEntity.setPlanName(planEntity.getName());
            planPayEntity.setCompany(contractEntity.getCompany());
            planPayEntity.setContractAmount(contractEntity.getAmount());
            planPayEntity.setPayedAmount(payedAmount);
        }
        Page<PlanPayEntity> page = new PageImpl<>(planPayEntityList, pageable, apiResponse.getTotal());
        modelMap.addAttribute("page", page);
        return "sale/planPay/list";
    }

    @RequiresPermissions("sts:planPay:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(@ModelAttribute PlanPayForm planPayForm, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".planPayForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        if (Objects.isNull(planPayForm.getPlanPay())) {
            planPayForm.setPlanPay(new PlanPayEntity());
        }

        ApiRequest apiRequest = ApiRequest.newInstance();
        apiRequest.filterEqual("valid", EnableDisableStatus.ENABLE);

        ApiRequestPage apiRequestPage = ApiRequestPage.newInstance().paging(0, 100)
                .addOrder("id", PageOrderType.ASC);

        List<PlanEntity> planEntityList = ApiPageRequestHelper.request(apiRequest, apiRequestPage, productOrderService::findAll);

        modelMap.put("action", "create");
        modelMap.put("planList", planEntityList);
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
        planPayService.save(planEntity);
        return "result";
    }

    @RequiresPermissions("sts:planPay:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable Long id, @ModelAttribute PlanPayForm planPayForm, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".planPayForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        PlanPayEntity planEntity = planPayService.get(id);
        if (Objects.isNull(planEntity)) {
            logger.error("修改付款信息,未查询[id={}]的付款信息", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "未查询编号为["+id+"]的付款信息!");
            return "redirect:/error";
        }
        planPayForm.setPlanPay(planEntity);
        planPayForm.setAmount(Double.valueOf(planEntity.getAmount()));

        modelMap.put("action", "update");
        return "sale/planPay/edit";
    }

    @RequiresPermissions("sts:planPay:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(PlanPayForm planPayForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(planPayForm);
            return "redirect:/planPay/update/" + planPayForm.getPlanPay().getId();
        }
        PlanPayEntity planPay = planPayForm.getPlanPay();
        PlanPayEntity planEntity = planPayService.get(planPay.getId());
        Double amount = CoreMathUtils.mul(planPayForm.getAmount(), 100L);
        planEntity.setAmount(amount.longValue());
        planEntity.setPayModel(planPay.getPayModel());
        planEntity.setPayTime(planPay.getPayTime());
        planEntity.setOperator(planPay.getOperator());
        planPayService.save(planEntity);
        return "result";
    }

    @RequiresPermissions("sts:planPay:view")
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable Long id, ModelMap modelMap) {
        PlanPayEntity planPayEntity = planPayService.get(id);

        PlanEntity planEntity = productOrderService.findByPlanNo(planPayEntity.getPlanNo());
        ContractEntity contractEntity = contractService.findByPlanNo(planEntity.getPlanNo());
        List<PlanPayEntity> planPayEntityList = planPayService.findByPlanNo(planEntity.getPlanNo());
        Long payedAmount = 0L;
        for (PlanPayEntity dbPlanPayEntity : planPayEntityList) {
            payedAmount = CoreMathUtils.add(payedAmount, dbPlanPayEntity.getAmount());
        }

        planPayEntity.setPlanName(planEntity.getName());
        planPayEntity.setCompany(contractEntity.getCompany());
        planPayEntity.setContractAmount(contractEntity.getAmount());
        planPayEntity.setPayedAmount(payedAmount);

        modelMap.put("planPay", planPayEntity);
        return "sale/planPay/view";
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

    @RequiresPermissions("sts:contract:findPlan")
    @RequestMapping("/findPlan/{planNo}")
    @ResponseBody
    public Map<String, String> findPlan(@PathVariable String planNo) {
        Map<String, String> result = Maps.newHashMap();
        PlanEntity planEntity = null;
        try {
            planEntity = productOrderService.findByPlanNo(planNo);
        } catch (Exception e) {
            logger.error("调用productOrderService获取项目信息接口出错！");
            logger.error(e.getMessage(), e);
        }

        if (planEntity == null) {
            result.put("code", "-1");
            result.put("message", "项目不存在");
            return result;
        }

        ContractEntity contractEntity = contractService.findByPlanNo(planNo);

        if (contractEntity == null) {
            result.put("code", "-1");
            result.put("message", "项目"+planNo+"还没有创建合同");
            return result;
        }

        result.put("code", "0");
        result.put("planName", planEntity.getName());
        result.put("contractNo", contractEntity.getContractNo());
        result.put("company", contractEntity.getCompany());
        result.put("contractAmount", contractEntity.getAmount() + "");

        List<PlanPayEntity> planPayEntityList = planPayService.findByPlanNo(planNo);
        Long payedAmount = 0L;
        for (PlanPayEntity planPayEntity : planPayEntityList) {
            payedAmount = CoreMathUtils.add(payedAmount, planPayEntity.getAmount());
        }
        result.put("payedAmount", payedAmount + "");

        return result;
    }
}
