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
                                <td style="width:100px;text-align: right;padding-top: 13px;">出库单编号:</td>
                                <td>
                                    ${productOutBound.productOutBoundNo}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">订货单位:</td>
                                <td>
                                    ${productOutBound.company!""}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">项目名称:</td>
                                <td>
                                    ${productOutBound.planName!''}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">联系人:</td>
                                <td>
                                    ${productOutBound.linkman!''}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">联系电话:</td>
                                <td>
                                    ${productOutBound.mobile!''}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">发货日期:</td>
                                <td>
                                    [@macro.displayDate value=productOutBound.deliveryDate!""/]
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">产品名称:</td>
                                <td>
                                    ${productOutBound.productName!''}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">规格型号:</td>
                                <td>
                                    ${productOutBound.productModel!''}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">数量:</td>
                                <td>
                                    ${productOutBound.quantity!''}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">单位:</td>
                                <td>
                                    ${productOutBound.unit!''}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">单价:</td>
                                <td>
                                    [@macro.displayMoney value=productOutBound.singleAmount!''/]
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">总价:</td>
                                <td>
                                    [@macro.displayMoney value=productOutBound.totalAmount!''/]
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">制单人:</td>
                                <td>
                                    ${productOutBound.operator!''}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">提/发货人:</td>
                                <td>
                                    ${productOutBound.shipper!''}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">备注:</td>
                                <td>
                                    ${productOutBound.memo!''}
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