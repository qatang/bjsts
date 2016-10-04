package com.bjsts.manager.controller.sale;

import com.bjsts.manager.core.constants.GlobalConstants;
import com.bjsts.manager.core.controller.AbstractController;
import com.bjsts.manager.entity.sale.ContractEntity;
import com.bjsts.manager.form.sale.ContractForm;
import com.bjsts.manager.query.user.UserSearchable;
import com.bjsts.manager.service.sale.ContractService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/contract")
@SessionAttributes("contractSearchable")
public class ContractController extends AbstractController {

    @Autowired
    private ContractService contractService;

    @RequiresPermissions("arsenal:contract:list")
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(UserSearchable contractSearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable, ModelMap modelMap) {
        List<ContractEntity> contractEntityList = contractService.findAll();
        modelMap.addAttribute("list", contractEntityList);
        return "contract/list";
    }

    @RequiresPermissions("arsenal:contract:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(@ModelAttribute ContractForm contractForm, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".contractForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        if (Objects.isNull(contractForm.getContract())) {
            contractForm.setContract(new ContractEntity());
        }
        modelMap.put("action", "create");
        return "contract/edit";
    }

    @RequiresPermissions("arsenal:contract:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(ContractForm contractForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(contractForm);
            return "redirect:/contract/create";
        }
        ContractEntity contractEntity = contractForm.getContract();
        contractService.save(contractEntity);
        return "result";
    }

    @RequiresPermissions("arsenal:contract:update")
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
        modelMap.put("action", "update");
        return "contract/edit";
    }

    @RequiresPermissions("arsenal:contract:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(ContractForm contractForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(contractForm);
            return "redirect:/contract/update/" + contractForm.getContract().getId();
        }
        ContractEntity contract = contractForm.getContract();
        ContractEntity contractEntity = contractService.get(contract.getId());
        contractEntity.setContractNo(contract.getContractNo());
        contractService.update(contractEntity);
        return "result";
    }

    @RequiresPermissions("arsenal:contract:view")
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable Long id, ModelMap modelMap) {
        modelMap.put("contract", contractService.get(id));
        return "contract/view";
    }
}
