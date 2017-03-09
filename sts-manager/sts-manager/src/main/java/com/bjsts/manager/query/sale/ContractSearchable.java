package com.bjsts.manager.query.sale;

import com.bjsts.manager.core.query.CommonSearchable;
import com.bjsts.manager.enums.sale.ContractStatus;

import java.util.Date;
import java.util.List;

/**
 * @author jinsheng
 * @since 2016-05-09 10:55
 */
public class ContractSearchable extends CommonSearchable {

    private static final long serialVersionUID = -2048350258192544932L;

    private String company;

    private Date signTime;

    private String sign;

    private List<ContractStatus> contractStatusList;

    private Date qualityTime;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public List<ContractStatus> getContractStatusList() {
        return contractStatusList;
    }

    public void setContractStatusList(List<ContractStatus> contractStatusList) {
        this.contractStatusList = contractStatusList;
    }

    public Date getQualityTime() {
        return qualityTime;
    }

    public void setQualityTime(Date qualityTime) {
        this.qualityTime = qualityTime;
    }
}
