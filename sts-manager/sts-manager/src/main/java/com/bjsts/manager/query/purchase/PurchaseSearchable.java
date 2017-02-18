package com.bjsts.manager.query.purchase;

import com.bjsts.core.enums.YesNoStatus;
import com.bjsts.manager.core.query.CommonSearchable;

/**
 * @author wangzhiliang
 */
public class PurchaseSearchable extends CommonSearchable {

    private static final long serialVersionUID = -2048350258192544932L;

    private YesNoStatus inBound;

    public YesNoStatus getInBound() {
        return inBound;
    }

    public void setInBound(YesNoStatus inBound) {
        this.inBound = inBound;
    }
}
