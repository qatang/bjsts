package com.bjsts.manager.form.stock;

import com.bjsts.manager.core.form.AbstractForm;
import com.bjsts.manager.entity.stock.StockEntity;

/**
 * @author jinsheng
 * @since 2016-05-12 16:00
 */
public class StockForm extends AbstractForm {

    private static final long serialVersionUID = 5859168020901454685L;

    private StockEntity stock;

    public StockEntity getStock() {
        return stock;
    }

    public void setStock(StockEntity stock) {
        this.stock = stock;
    }
}
