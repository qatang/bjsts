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
                            <table id="table_report" class="table table-striped table-bordered table-hover">
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">采购单编号:</td>
                                    <td>
                                    ${inBound.purchase.purchaseNo}
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">产品名称:</td>
                                    <td>
                                    ${inBound.purchase.productName!""}
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">规格型号:</td>
                                    <td>
                                    ${inBound.purchase.productModel!""}
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">采购数量:</td>
                                    <td>
                                    ${inBound.purchase.quantity!""}
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">采购负责人:</td>
                                    <td>
                                    ${inBound.purchase.operator!""}
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">到货物品核对:</td>
                                    <td>
                                        ${inBound.verify.getName()}
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">接收人:</td>
                                    <td>
                                    ${inBound.sendee!""}
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">接收日期:</td>
                                    <td>
                                    [@macro.displayDate value=inBound.sendeeTime!""/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">备注:</td>
                                    <td>
                                        ${inBound.memo!""}
                                    </td>
                                </tr>
                            </table>
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