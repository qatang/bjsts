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
                        <div style="padding-top: 13px;"></div>
                        <table id="table_report" class="table table-striped table-bordered table-hover">
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">项目编号:</td>
                                <td>${planPay.planNo}</td>
                            </tr>

                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">项目名称:</td>
                                <td>${planPay.planName}</td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">合同编号:</td>
                                <td>
                                ${planPay.contractNo}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">客户名称:</td>
                                <td>
                                ${planPay.company!""}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">合同总额:</td>
                                <td>
                                [@macro.displayMoney value=planPay.contractAmount!''/]
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">开票信息:</td>
                                <td>
                                ${planPay.invoiceStatus.getName()}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">已付金额:</td>
                                <td>
                                [@macro.displayMoney value=planPay.payedAmount!''/]
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">本次付款金额:</td>
                                <td>
                                [@macro.displayMoney value=planPay.amount!''/]
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">本次付款方式:</td>
                                <td>
                                ${planPay.payModel!""}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">本次付款日期:</td>
                                <td>
                                [@macro.displayDate value=planPay.payTime!""/]

                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">经办人:</td>
                                <td>
                                ${planPay.operator!""}
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
[#include "${ctx}/common/footer.ftl"/]
</html>