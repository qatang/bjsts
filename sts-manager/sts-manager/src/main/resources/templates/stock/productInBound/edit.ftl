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
                        <form action="${ctx}/productInBound/${action}" name="productInBoundForm" id="productInBoundForm" method="post">
                            <div style="padding-top: 13px;"></div>
                        [@macro.errors /]
                            <input type="hidden" name="productInBound.id" value="${productInBoundForm.productInBound.id!''}"/>
                            <table id="table_report" class="table table-striped table-bordered table-hover">
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">项目编号:</td>
                                    <td>
                                    [#if productInBoundForm.productInBound.id??]
                                    ${productInBoundForm.productInBound.planNo}
                                        <input type="hidden" name="productInBound.planNo" value="${productInBoundForm.productInBound.planNo!''}"/>
                                    [#else]
                                        <select class="chosen-select form-control" name="productInBound.planNo" data-placeholder="请选择" style="" id="planNo">
                                            <option value="0">请选择</option>
                                            [#list planList as data]
                                                <option value="${data.planNo!""}" [#if productInBoundForm.productInBound.planNo?has_content && data.planNo == productInBoundForm.productInBound.planNo]selected[/#if]>${data.getName()!""}</option>
                                            [/#list]
                                        </select>
                                    [/#if]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">项目名称:</td>
                                    <td>
                                    [@macro.inputText name="productInBound.planName" value=productInBoundForm.productInBound.planName!'' placeholder="项目名称"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">客户名称:</td>
                                    <td>
                                    [@macro.inputText name="productInBound.company" value=productInBoundForm.productInBound.company!'' placeholder="客户名称"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">合同编号:</td>
                                    <td>
                                    [#if productInBoundForm.productInBound.id??]
                                    ${productInBoundForm.productInBound.contractNo}
                                        <input type="hidden" name="productInBound.contractNo" value="${productInBoundForm.productInBound.contractNo!''}"/>
                                    [#else]
                                        <select class="chosen-select form-control" name="productInBound.contractNo" data-placeholder="请选择" style="" id="planNo">
                                            <option value="0">请选择</option>
                                            [#list contractList as data]
                                                <option value="${data.contractNo!""}" [#if productInBoundForm.productInBound.contractNo?has_content && data.contractNo == productInBoundForm.productInBound.contractNo]selected[/#if]>${data.getPlanName()!""}</option>
                                            [/#list]
                                        </select>
                                    [/#if]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">产品名称:</td>
                                    <td>
                                    [@macro.inputText name="productInBound.productName" value=productInBoundForm.productInBound.productName!'' placeholder="产品名称"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">数量:</td>
                                    <td>
                                    [@macro.inputNumber name="productInBound.quantity" value=productInBoundForm.productInBound.quantity!'' placeholder="数量"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">单位:</td>
                                    <td>
                                    [@macro.inputText name="productInBound.unit" value=productInBoundForm.productInBound.unit!'' placeholder="单位"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">日期:</td>
                                    <td>
                                    [@macro.datePicker name="productInBound.inBoundTime" value=productInBoundForm.productInBound.inBoundTime!"" placeholder="日期"/]
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