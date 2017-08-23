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
                        <table id="table_report" class="table table-striped table-bordered table-hover">
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">编号:</td>
                                <td>
                                    ${saleInvoice.id?c}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">项目编号:</td>
                                <td>
                                    ${saleInvoice.planNo!""}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">项目内容:</td>
                                <td>
                                    ${saleInvoice.planContent!""}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">发票类型:</td>
                                <td>
                                    ${saleInvoice.invoiceCategory.getName()}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">发票编号:</td>
                                <td>
                                    ${saleInvoice.invoiceNo}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">客户名称:</td>
                                <td>
                                    ${saleInvoice.customer}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">开票日期:</td>
                                <td>
                                [@macro.displayDate value=saleInvoice.invoiceDate!""/]
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">开票金额:</td>
                                <td>
                                [@macro.displayMoney value=saleInvoice.amount!''/]
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">开票内容:</td>
                                <td>
                                    ${saleInvoice.content!""}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">发票状态:</td>
                                <td>
                                    ${saleInvoice.invoiceStatus.getName()}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">发票扫描附件:</td>
                                <td>
                                    [@macro.displayFile document=document!"" /]
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