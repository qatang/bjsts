package com.bjsts.manager.core.controller;

import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.core.enums.YesNoStatus;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * @author jinsheng
 * @since 2016-04-27 13:55
 */
public abstract class AbstractController extends BaseController {

    /********************** core **********************/

    @ModelAttribute("disableStatus")
    public EnableDisableStatus getDisableStatus() {
        return EnableDisableStatus.DISABLE;
    }

    @ModelAttribute("noStatus")
    public YesNoStatus getNoStatus() {
        return YesNoStatus.NO;
    }
}
