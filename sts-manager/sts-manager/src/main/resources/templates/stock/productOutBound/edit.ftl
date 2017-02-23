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
                        <form action="${ctx}/productOutBound/${action}" name="productOutBoundForm" id="productOutBoundForm" method="post">
                            <div style="padding-top: 13px;"></div>
                            [@macro.errors /]
                            <input type="hidden" name="productOutBound.id" value="${productOutBoundForm.productOutBound.id!''}"/>
                            <table id="table_report" class="table table-striped table-bordered table-hover">
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">出库单编号:</td>
                                    <td>
                                        [#if productOutBoundForm.productOutBound.id??]
                                            ${productOutBoundForm.productOutBound.productOutBoundNo}
                                        [/#if]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">订货单位:</td>
                                    <td>
                                        [@macro.inputText name="productOutBound.company" value=productOutBoundForm.productOutBound.company!'' placeholder="订货单位"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">项目名称:</td>
                                    <td>
                                        [@macro.inputText name="productOutBound.planName" value=productOutBoundForm.productOutBound.planName!'' placeholder="项目名称"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">联系人:</td>
                                    <td>
                                    [@macro.inputText name="productOutBound.linkman" value=productOutBoundForm.productOutBound.linkman!'' placeholder="联系人"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">联系电话:</td>
                                    <td>
                                    [@macro.inputText name="productOutBound.mobile" value=productOutBoundForm.productOutBound.mobile!'' placeholder="联系电话"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">发货日期:</td>
                                    <td>
                                        [@macro.datePicker name="productOutBound.deliveryDate" value=productOutBoundForm.productOutBound.deliveryDate!"" placeholder="发货日期"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">产品名称:</td>
                                    <td>
                                    [@macro.inputText name="productOutBound.productName" value=productOutBoundForm.productOutBound.productName!'' placeholder="产品名称"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">规格型号:</td>
                                    <td>
                                    [@macro.inputText name="productOutBound.productModel" value=productOutBoundForm.productOutBound.productModel!'' placeholder="规格型号"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">数量:</td>
                                    <td>
                                    [@macro.inputNumber name="productOutBound.quantity" value=productOutBoundForm.productOutBound.quantity!'' placeholder="数量"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">单位:</td>
                                    <td>
                                    [@macro.inputText name="productOutBound.unit" value=productOutBoundForm.productOutBound.unit!'' placeholder="单位"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">单价:</td>
                                    <td>
                                    [@macro.inputMoney name="singleAmount" value=productOutBoundForm.singleAmount!'' placeholder="单价"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">制单人:</td>
                                    <td>
                                    [@macro.inputText name="productOutBound.operator" value=productOutBoundForm.productOutBound.operator!'' placeholder="制单人"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">提/发货人:</td>
                                    <td>
                                    [@macro.inputText name="productOutBound.shipper" value=productOutBoundForm.productOutBound.shipper!'' placeholder="提/发货人"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">备注:</td>
                                    <td>
                                    [@macro.inputText name="productOutBound.memo" value=productOutBoundForm.productOutBound.memo!'' placeholder="备注" required=false/]
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