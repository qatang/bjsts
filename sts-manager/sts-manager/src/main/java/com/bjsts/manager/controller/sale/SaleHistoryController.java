package com.bjsts.manager.controller.sale;

import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.manager.core.constants.GlobalConstants;
import com.bjsts.manager.core.controller.AbstractController;
import com.bjsts.manager.entity.sale.PlanEntity;
import com.bjsts.manager.enums.sale.PlanStatus;
import com.bjsts.manager.query.sale.SaleHistorySearchable;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

/**
 * 项目归档管理
 * @author wangzhiliang
 */
@Controller
@RequestMapping("/saleHistory")
@SessionAttributes("saleHistorySearchable")
public class SaleHistoryController extends AbstractController {

    @Autowired
    private ProductOrderService productOrderService;

    @ModelAttribute("planStatusList")
    public List<PlanStatus> getPlanStatusList() {
        return PlanStatus.list();
    }

    @RequiresPermissions("sts:saleHistory:list")
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(SaleHistorySearchable saleHistorySearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable, ModelMap modelMap) {
        ApiResponse<PlanEntity> apiResponse = productOrderService.findAll(saleHistorySearchable, pageable);
        Page<PlanEntity> page = new PageImpl<>(Lists.newArrayList(apiResponse.getPagedData()), pageable, apiResponse.getTotal());
        modelMap.addAttribute("page", page);
        return "sale/saleHistory/list";
    }
}
