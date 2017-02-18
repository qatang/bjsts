[#ftl strip_whitespace=true]
[#import "${ctx}/common/macroes.ftl" as macro]
<!DOCTYPE html>
<html>
<head>
[#include "${ctx}/common/head.ftl"/]

</head>
<body class="no-skin">
<div class="main-container" id="main-container">
    <div class="main-content">
        <div class="main-content-inner">
            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">
                        <form action="${ctx}/purchase/${action}" name="purchaseForm" id="purchaseForm" method="post">
                            <div style="padding-top: 13px;"></div>
                            [@macro.errors /]
                            <input type="hidden" name="purchase.id" value="${purchaseForm.purchase.id!''}"/>
                            <table id="table_report" class="table table-striped table-bordered table-hover">
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">采购单编号:</td>
                                    <td>
                                        [#if purchaseForm.purchase.id??]
                                            ${purchaseForm.purchase.purchaseNo}
                                        [/#if]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">产品名称:</td>
                                    <td>
                                        [#if purchaseForm.purchase.inBound.getValue() == noStatus.getValue()]
                                            [@macro.inputText name="purchase.productName" value=purchaseForm.purchase.productName!'' placeholder="产品名称"/]
                                        [#else]
                                            ${purchaseForm.purchase.productName!''}
                                        [/#if]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">规格型号:</td>
                                    <td>
                                        [#if purchaseForm.purchase.inBound.getValue() == noStatus.getValue()]
                                            [@macro.inputText name="purchase.productModel" value=purchaseForm.purchase.productModel!'' placeholder="规格型号"/]
                                        [#else]
                                            ${purchaseForm.purchase.productModel!''}
                                        [/#if]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">采购数量:</td>
                                    <td>
                                        [#if purchaseForm.purchase.inBound.getValue() == noStatus.getValue()]
                                            [@macro.inputNumber name="purchase.quantity" value=purchaseForm.purchase.quantity!'' placeholder="采购数量"/]
                                        [#else]
                                            ${purchaseForm.purchase.quantity!''}
                                        [/#if]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">采购负责人:</td>
                                    <td>
                                        [#if purchaseForm.purchase.inBound.getValue() == noStatus.getValue()]
                                            [@macro.inputText name="purchase.operator" value=purchaseForm.purchase.operator!'' placeholder="采购负责人"/]
                                        [#else]
                                            ${purchaseForm.purchase.operator!''}
                                        [/#if]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">供应商名称:</td>
                                    <td>
                                        [#if purchaseForm.purchase.inBound.getValue() == noStatus.getValue()]
                                            [@macro.inputText name="purchase.supplier" value=purchaseForm.purchase.supplier!'' placeholder="供应商名称"/]
                                        [#else]
                                            ${purchaseForm.purchase.supplier!''}
                                        [/#if]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">供应商联系人:</td>
                                    <td>
                                        [#if purchaseForm.purchase.inBound.getValue() == noStatus.getValue()]
                                            [@macro.inputText name="purchase.supplierLinkman" value=purchaseForm.purchase.supplierLinkman!'' placeholder="供应商联系人"/]
                                        [#else]
                                            ${purchaseForm.purchase.supplierLinkman!''}
                                        [/#if]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">供应商联系电话:</td>
                                    <td>
                                        [#if purchaseForm.purchase.inBound.getValue() == noStatus.getValue()]
                                            [@macro.inputText name="purchase.supplierMobile" value=purchaseForm.purchase.supplierMobile!'' placeholder="供应商联系电话"/]
                                        [#else]
                                            ${purchaseForm.purchase.supplierMobile!''}
                                        [/#if]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">采购日期:</td>
                                    <td>
                                    [#if purchaseForm.purchase.inBound.getValue() == noStatus.getValue()]
                                        [@macro.datetimePicker name="purchase.purchaseTime" value=purchaseForm.purchase.purchaseTime!"" placeholder="采购日期"/]
                                    [#else]
                                        [@macro.displayDate value=purchaseForm.purchase.purchaseTime!''/]
                                    [/#if]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">采购金额:</td>
                                    <td>
                                        [@macro.inputMoney name="totalAmount" value=purchaseForm.totalAmount!'' placeholder="采购金额"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">已付款金额:</td>
                                    <td>
                                        [@macro.inputMoney name="payedAmount" value=purchaseForm.payedAmount!'' placeholder="已付款金额"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">未付款金额:</td>
                                    <td>
                                        [@macro.inputMoney name="unPayedAmount" value=purchaseForm.unPayedAmount!'' placeholder="未付款金额"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">发票状态:</td>
                                    <td>
                                        [@macro.selectEnum name="purchase.invoiceStatus" enumObj=purchaseForm.purchase.invoiceStatus!invoiceStatusList[0] dataList=invoiceStatusList /]
                                    </td>
                                </tr>
                                 <tr>
                                     <td style="width:100px;text-align: right;padding-top: 13px;">采购合同扫描附件:</td>
                                     <td>
                                         <input id="input-purchaseContractUrl" name="file" type="file" multiple class="file-loading" data-show-upload="false">
                                         [@macro.inputText name="purchaseContractUrl" id="purchaseContractUrl" value=purchaseForm.purchaseContractUrl!'' placeholder="采购合同扫描附件"/]
                                     </td>
                                 </tr>
                                <tr>
                                    <td style="text-align: center;" colspan="10">
                                        <button class="btn btn-mini btn-primary" type="submit" id="submitForm">保存</button>
                                        <button class="btn btn-mini btn-danger" type="button" onclick="top.Dialog.close();">取消</button>
                                    </td>
                                </tr>
                            </table>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

[#include "${ctx}/common/footer.ftl"/]

<link href="/plugins/bootstrap-fileinput-master/css/fileinput.min.css" media="all" rel="stylesheet" type="text/css" />
<script src="/plugins/bootstrap-fileinput-master/js/fileinput.min.js"></script>
<script src="/plugins/bootstrap-fileinput-master/js/locales/zh.js"></script>

<script>
    var purchaseContractUrl = null;
    if ('${purchaseForm.purchaseContractUrl!''}' != "") {
        purchaseContractUrl = "${purchaseForm.purchaseContractUrl!''}";
    }

    $("#input-purchaseContractUrl").fileinput({
        language: "zh",
        uploadUrl: "/purchase/upload",
        autoReplace: true,
        uploadAsync: true,
        maxFileCount: 1,
        allowedFileExtensions: ["jpg", "png", "gif", "rar", "zip", "doc", "docx", "pdf"],
        initialPreview: [
            purchaseContractUrl
        ]
    }).on("filebatchselected", function(event, files) {
        $(this).fileinput("upload");
    }).on("fileuploaded", function(event, data) {
        if (data.response) {
            $("#purchaseContractUrl").val(data.response.path);
        }
    });
</script>
</html>