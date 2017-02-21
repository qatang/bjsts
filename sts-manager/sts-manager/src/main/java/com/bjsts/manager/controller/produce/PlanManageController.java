package com.bjsts.manager.controller.produce;

import com.bjsts.core.api.component.request.ApiPageRequestHelper;
import com.bjsts.core.api.request.ApiRequest;
import com.bjsts.core.api.request.ApiRequestPage;
import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.core.enums.PageOrderType;
import com.bjsts.manager.core.constants.GlobalConstants;
import com.bjsts.manager.core.controller.AbstractController;
import com.bjsts.manager.entity.produce.PlanManageEntity;
import com.bjsts.manager.entity.sale.PlanEntity;
import com.bjsts.manager.enums.produce.PlanExecuteStatus;
import com.bjsts.manager.form.produce.PlanManageForm;
import com.bjsts.manager.query.produce.PlanManageSearchable;
import com.bjsts.manager.service.produce.PlanManageService;
import com.bjsts.manager.service.sale.ProductOrderService;
import com.google.common.collect.Lists;
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
@RequestMapping("/planManage")
@SessionAttributes("planManageSearchable")
public class PlanManageController extends AbstractController {

    @Autowired
    private PlanManageService planManageService;

    @Autowired
    private ProductOrderService productOrderService;

    @ModelAttribute("planExecuteStatusList")
    public List<PlanExecuteStatus> getPlanExecuteStatusList() {
        return PlanExecuteStatus.list();
    }

    @RequiresPermissions("sts:planManage:list")
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(PlanManageSearchable planManageSearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable, ModelMap modelMap) {
        ApiResponse<PlanManageEntity> apiResponse = planManageService.findAll(planManageSearchable, pageable);
        Page<PlanManageEntity> page = new PageImpl<>(Lists.newArrayList(apiResponse.getPagedData()), pageable, apiResponse.getTotal());
        modelMap.addAttribute("page", page);
        return "produce/planManage/list";
    }

    @RequiresPermissions("sts:planManage:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(@ModelAttribute PlanManageForm planManageForm, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".planManageForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        if (Objects.isNull(planManageForm.getPlanManage())) {
            planManageForm.setPlanManage(new PlanManageEntity());
        }

        ApiRequest apiRequest = ApiRequest.newInstance();
        apiRequest.filterEqual("valid", EnableDisableStatus.ENABLE);

        ApiRequestPage apiRequestPage = ApiRequestPage.newInstance().paging(0, 100)
                .addOrder("id", PageOrderType.ASC);

        List<PlanEntity> planEntityList = ApiPageRequestHelper.request(apiRequest, apiRequestPage, productOrderService::findAll);

        modelMap.put("action", "create");
        modelMap.put("planList", planEntityList);
        return "produce/planManage/edit";
    }

    @RequiresPermissions("sts:planManage:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(PlanManageForm planManageForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(planManageForm);
            return "redirect:/planManage/create";
        }
        PlanManageEntity planManageEntity = planManageForm.getPlanManage();

        planManageService.save(planManageEntity);
        return "result";
    }

    @RequiresPermissions("sts:planManage:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable Long id, @ModelAttribute PlanManageForm planManageForm, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".planManageForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        PlanManageEntity planManageEntity = planManageService.get(id);
        if (Objects.isNull(planManageEntity)) {
            logger.error("修改项目管理信息,未查询[id={}]的项目管理信息信息", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据!");
            return "redirect:/error";
        }

        planManageForm.setPlanManage(planManageEntity);
        modelMap.put("action", "update");
        return "produce/planManage/edit";
    }

    @RequiresPermissions("sts:planManage:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(PlanManageForm planManageForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(planManageForm);
            return "redirect:/planManage/update/" + planManageForm.getPlanManage().getId();
        }
        PlanManageEntity planManage = planManageForm.getPlanManage();
        PlanManageEntity planManageEntity = planManageService.get(planManage.getId());
        planManageEntity.setPlanNo(planManage.getPlanNo());
        planManageEntity.setExpectDate(planManage.getExpectDate());
        planManageEntity.setActualDate(planManage.getActualDate());
        planManageEntity.setPlanExecuteStatus(planManage.getPlanExecuteStatus());
        planManageService.save(planManageEntity);
        
        return "result";
    }

    @RequiresPermissions("sts:planManage:view")
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable Long id, ModelMap modelMap) {
        PlanManageEntity planManageEntity = planManageService.get(id);
        modelMap.put("planManage", planManageEntity);
        return "produce/planManage/view";
    }

    @RequiresPermissions("sts:planManage:disable")
    @RequestMapping(value = "/disable/{id}", method = RequestMethod.GET)
    public String disable(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        PlanManageEntity planManageEntity = planManageService.get(id);
        if (Objects.isNull(planManageEntity)) {
            logger.error("删除项目管理信息,未查询[id={}]的项目管理信息", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "未查询[id={"+id+"}]的项目管理信息!");
            return "redirect:/error";
        }
        
        planManageEntity.setValid(EnableDisableStatus.DISABLE);
        planManageService.update(planManageEntity);
        return "redirect:/planManage/list";
    }
}
