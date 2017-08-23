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
                        <table id="simple-table" class="table table-striped table-bordered table-hover table-fixed-head" style="margin-top:5px;">
                            <thead>
                            <tr>
                                <th class="center">付款金额</th>
                                <th class="center">付款方式</th>
                                <th class="center">付款日期</th>
                                <th class="center">开票金额</th>
                                <th class="center">经办人</th>
                                <th class="center">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            [#if purchasePayItemList?has_content]
                                [#list purchasePayItemList as purchasePayItem]
                                <tr>
                                    <td class="center">[@macro.displayMoney value=purchasePayItem.amount!''/]</td>
                                    <td class="center">${purchasePayItem.payModel!""}</td>
                                    <td class="center">[@macro.displayDate value=purchasePayItem.payTime!""/]</td>
                                    <td class="center">[@macro.displayMoney value=purchasePayItem.invoiceAmount!''/]</td>
                                    <td class="center">${purchasePayItem.operator!""}</td>
                                    <td class="center">
                                        <a href="${ctx}/purchasePay/disable/item/${purchasePayItem.id?c}" onclick="return confirm('确定要删除吗?');">删除</a>
                                    </td>
                                </tr>
                                [/#list]
                            [/#if]
                            <tr>
                                <td class="center" colspan="6">总计：[@macro.displayMoney value=purchasePay.payedAmount!''/]</td>
                            </tr>
                            </tbody>
                        </table>
                        <form action="${ctx}/purchasePay/create" name="purchasePayForm" id="purchasePayForm" method="post">
                            [@macro.errors /]
                            <input type="hidden" name="purchasePay.id" value="${purchasePay.id!''}"/>
                            <table id="table_report" class="table table-striped table-bordered table-hover">
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">本次付款金额:</td>
                                    <td>
                                        [@macro.inputMoney name="amount" value=purchasePayForm.amount!'' placeholder="本次付款金额"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">本次付款方式:</td>
                                    <td>
                                    [@macro.inputText name="purchasePayItem.payModel" value=purchasePayForm.purchasePayItem.payModel!'' placeholder="本次付款方式"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">本次付款日期:</td>
                                    <td>
                                        [@macro.datePicker name="purchasePayItem.payTime" value=purchasePayForm.purchasePayItem.payTime placeholder="本次付款日期"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">本次开票金额:</td>
                                    <td>
                                    [@macro.inputMoney name="invoiceAmount" value=purchasePayForm.invoiceAmount!'' placeholder="本次开票金额"/]
                                    </td>
                                </tr
                                <tr>
                                    <td style="text-align: center;" colspan="10">
                                        <button class="btn btn-mini btn-primary" type="submit">保存</button>
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