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
                        <form action="${ctx}/outBound/${action}" name="outBoundForm" id="outBoundForm" method="post">
                            <div style="padding-top: 13px;"></div>
                            [@macro.errors /]
                            <input type="hidden" name="outBound.id" value="${outBoundForm.outBound.id!''}"/>
                            <table id="table_report" class="table table-striped table-bordered table-hover">
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">编号:</td>
                                    <td>
                                        [#if outBoundForm.outBound.id??]
                                            ${outBoundForm.outBound.id}
                                        [/#if]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">项目编号:</td>
                                    <td>
                                    [#if outBoundForm.outBound.id??]
                                    ${outBoundForm.outBound.planNo}
                                        <input type="hidden" id="planNo" value="${outBoundForm.outBound.planNo!''}"/>
                                    [#else]
                                        <select class="chosen-select form-control" name="outBound.planNo" data-placeholder="请选择" style="" id="planNo">
                                            <option value="0">请选择</option>
                                            [#list planList as data]
                                                <option value="${data.planNo!""}" [#if outBoundForm.outBound.planNo?has_content && data.planNo == outBoundForm.outBound.planNo]selected[/#if]>${data.getName()!""}</option>
                                            [/#list]
                                        </select>
                                    [/#if]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">产品名称规格:</td>
                                    <td>
                                    [#if outBoundForm.outBound.id??]
                                    ${outBoundForm.outBound.stockId}
                                        <input type="hidden" id="stockId" value="${outBoundForm.outBound.stockId!''}"/>
                                    [#else]
                                        <select class="chosen-select form-control" name="outBound.stockId" data-placeholder="请选择" style="" id="stockId">
                                            <option value="0">请选择</option>
                                            [#list stockList as data]
                                                <option value="${data.id?c}" [#if outBoundForm.outBound.stockId?has_content && data.id == outBoundForm.outBound.stockId]selected[/#if]>${data.productName!""} ${data.productModel!""}</option>
                                            [/#list]
                                        </select>
                                    [/#if]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">数量:</td>
                                    <td>
                                    [@macro.inputNumber name="outBound.quantity" value=outBoundForm.outBound.quantity!'' placeholder="采购数量"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">出库时间:</td>
                                    <td>
                                    [@macro.datePicker name="outBound.outBoundTime" value=outBoundForm.outBound.outBoundTime placeholder="出库日期"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">领取人:</td>
                                    <td>
                                    [@macro.inputText name="outBound.receiptor" value=outBoundForm.outBound.receiptor!'' placeholder="领取人"/]
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