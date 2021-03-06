package com.bjsts.manager.form.sale;

import com.bjsts.manager.core.form.AbstractForm;
import com.bjsts.manager.entity.document.DocumentEntity;
import com.bjsts.manager.entity.sale.ContractEntity;

/**
 * @author jinsheng
 * @since 2016-05-12 16:00
 */
public class ContractForm extends AbstractForm {

    private static final long serialVersionUID = 5859168020901454685L;

    private ContractEntity contract;

    private Double amount;

    private Double qualityAmount;

    private DocumentEntity document = new DocumentEntity();

    public ContractEntity getContract() {
        return contract;
    }

    public void setContract(ContractEntity contract) {
        this.contract = contract;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getQualityAmount() {
        return qualityAmount;
    }

    public void setQualityAmount(Double qualityAmount) {
        this.qualityAmount = qualityAmount;
    }

    public DocumentEntity getDocument() {
        return document;
    }

    public void setDocument(DocumentEntity document) {
        this.document = document;
    }
}
