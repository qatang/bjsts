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
                        <form action="${ctx}/normalPurchase/${action}" name="normalPurchaseForm" id="normalPurchaseForm" method="post">
                            <div style="padding-top: 13px;"></div>
                            [@macro.errors /]
                            <input type="hidden" name="normalPurchase.id" value="${normalPurchaseForm.normalPurchase.id!''}"/>
                            <table id="table_report" class="table table-striped table-bordered table-hover">
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">采购单编号:</td>
                                    <td>
                                    [#if normalPurchaseForm.normalPurchase.id??]
                                            ${normalPurchaseForm.normalPurchase.purchaseNo}
                                        [/#if]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">名称:</td>
                                    <td>
                                        [@macro.inputText name="normalPurchase.name" value=normalPurchaseForm.normalPurchase.name!'' placeholder="名称"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">购买人:</td>
                                    <td>
                                        [@macro.inputText name="normalPurchase.buyer" value=normalPurchaseForm.normalPurchase.buyer!'' placeholder="购买人"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">数量:</td>
                                    <td>
                                    [@macro.inputText name="normalPurchase.quantity" value=normalPurchaseForm.normalPurchase.quantity!'' placeholder="数量"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">金额:</td>
                                    <td>
                                        [@macro.inputMoney name="amount" value=normalPurchaseForm.amount!'' placeholder="金额"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">用途:</td>
                                    <td>
                                        [@macro.inputText name="normalPurchase.description" value=normalPurchaseForm.normalPurchase.description!'' placeholder="用途" required=false/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">购货单位名称:</td>
                                    <td>
                                        [@macro.inputText name="normalPurchase.company" value=normalPurchaseForm.normalPurchase.company!'' placeholder="购货单位名称" required=false/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">采购日期:</td>
                                    <td>
                                        [@macro.datePicker name="normalPurchase.purchaseTime" value=normalPurchaseForm.normalPurchase.purchaseTime!"" placeholder="采购日期"/]
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
</html>