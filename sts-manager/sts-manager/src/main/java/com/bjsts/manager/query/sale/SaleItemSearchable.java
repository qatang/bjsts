package com.bjsts.manager.query.sale;

import com.bjsts.manager.core.query.CommonSearchable;

import java.util.Date;

/**
 * @author jinsheng
 * @since 2016-05-09 10:55
 */
public class SaleItemSearchable extends CommonSearchable {

    private static final long serialVersionUID = -2048350258192544932L;

    private Date beginPriceTime;

    private Date endPriceTime;

    private String booker;

    private String linkman;

    public Date getBeginPriceTime() {
        return beginPriceTime;
    }

    public void setBeginPriceTime(Date beginPriceTime) {
        this.beginPriceTime = beginPriceTime;
    }

    public Date getEndPriceTime() {
        return endPriceTime;
    }

    public void setEndPriceTime(Date endPriceTime) {
        this.endPriceTime = endPriceTime;
    }

    public String getBooker() {
        return booker;
    }

    public void setBooker(String booker) {
        this.booker = booker;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }
}
