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
                                <td style="width:100px;text-align: right;padding-top: 13px;">采购合同编号:</td>
                                <td>
                                ${purchase.purchaseNo}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">产品名称:</td>
                                <td>${purchase.productName}</td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">规格型号:</td>
                                <td>${purchase.productModel}</td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">采购数量:</td>
                                <td>${purchase.quantity}</td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">采购负责人:</td>
                                <td>${purchase.operator}</td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">供应商名称:</td>
                                <td>${purchase.supplier}</td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">供应商联系人:</td>
                                <td>${purchase.supplierLinkman}</td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">供应商联系电话:</td>
                                <td>${purchase.supplierMobile}</td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">采购日期:</td>
                                <td>
                                [@macro.displayDate value=purchase.purchaseTime!""/]
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">采购金额:</td>
                                <td>[@macro.displayMoney value=purchase.totalAmount!''/]</td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">已付款金额:</td>
                                <td>[@macro.displayMoney value=purchase.payedAmount!''/]</td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">未付款金额:</td>
                                <td>[@macro.displayMoney value=purchase.unPayedAmount!''/]</td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">发票状态:</td>
                                <td>
                                ${purchase.makeOutInvoiceStatus.getName()}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">采购合同附件:</td>
                                <td>
                                [#if purchaseContractUrl??]
                                    <a href="${ctx}/file${purchaseContractUrl}" target="_blank">${ctx}/file${purchaseContractUrl}</a>
                                [/#if]
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