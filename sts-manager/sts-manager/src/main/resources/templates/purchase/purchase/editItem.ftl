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
                        <form action="${ctx}/purchase/createItem" name="purchaseForm" id="purchaseForm" method="post">
                            [@macro.errors /]
                            <input type="hidden" name="purchase.id" value="${purchase.id!''}"/>
                            <table id="table_report" class="table table-striped table-bordered table-hover">
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">产品信息:</td>
                                    <td>
                                        <select class="chosen-select form-control" id="supplierItemId" name="purchase.supplierItemId" data-placeholder="请选择产品" style="">
                                            <option value="">请选择</option>
                                        [#list supplierList as data]
                                            <option value="${data.id?c}" [#if purchaseForm.purchase.supplierId?has_content && data.id == purchaseForm.purchase.supplierId]selected[/#if]>${data.productName} ${data.productModel}</option>
                                        [/#list]
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">数量:</td>
                                    <td>
                                    [@macro.inputText name="purchaseItem.quantity" value=purchaseForm.purchaseItem.quantity!'' placeholder="数量"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">单位:</td>
                                    <td>
                                    [@macro.inputText name="purchaseItem.unit" value=purchaseForm.purchaseItem.unit!'' placeholder="单位"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">单价:</td>
                                    <td>
                                    [@macro.inputText name="purchaseItem.unitPrice" value=purchaseForm.purchaseItem.unitPrice!'' placeholder="单价"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">金额:</td>
                                    <td>
                                    [@macro.inputText name="purchaseItem.amount" value=purchaseForm.purchaseItem.amount!'' placeholder="金额"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="text-align: center;" colspan="10">
                                        <button class="btn btn-mini btn-primary" type="submit">保存</button>
                                        <button class="btn btn-mini btn-danger" type="button" onclick="top.Dialog.close();">取消</button>
                                    </td>
                                </tr>
                            </table>
                        </form>
                        <table id="simple-table" class="table table-striped table-bordered table-hover table-fixed-head" style="margin-top:5px;">
                            <thead>
                            <tr>
                                <th class="center">编号</th>
                               [#-- <th class="center">品牌</th>--]
                                <th class="center">名称</th>
                                <th class="center">规格型号</th>
                                <th class="center">数量</th>
                                <th class="center">单位</th>
                                <th class="center">单价</th>
                                <th class="center">金额</th>
                                <th class="center">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            [#if purchaseItemList?has_content]
                                [#list purchaseItemList as purchaseItem]
                                <tr>
                                    <td class="center">${purchaseItem.id!""}</td>
                                    <td class="center">${purchaseItem.supplierItem.productName!""}</td>
                                    <td class="center">${purchaseItem.supplierItem.productModel!""}</td>
                                    <td class="center">${purchaseItem.quantity!""}</td>
                                    <td class="center">${purchaseItem.unit!""}</td>
                                    <td class="center">${purchaseItem.unitPirce!""}</td>
                                    <td class="center">${purchaseItem.amount!""}</td>
                                    <td class="center">
                                        <a href="${ctx}/purchase/disable/item/${purchaseItem.id?c}" onclick="return confirm('确定要删除吗?');">删除</a>
                                    </td>
                                </tr>
                                [/#list]
                            [/#if]
                            </tbody>
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