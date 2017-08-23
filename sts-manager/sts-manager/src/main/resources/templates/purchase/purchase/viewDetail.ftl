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
                        <table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">
                            <thead>
                            <tr>
                                <th class="center">编号</th>
                                <th class="center">名称</th>
                                <th class="center">规格型号</th>
                                <th class="center">数量</th>
                                <th class="center">单位</th>
                                <th class="center">单价</th>
                                <th class="center">金额</th>
                            </tr>
                            </thead>
                            <tbody id="tbody">
                            [#if purchaseItemList?? && purchaseItemList?has_content]
                                [#list purchaseItemList as purchaseItem]
                                <tr>
                                    <td class="center" style="width:30%;">
                                        ${purchaseItem.id}
                                    </td>
                                    <td class="center" style="width:30%;">
                                        ${purchaseItem.supplierItem.productName!""}
                                    </td>
                                    <td class="center" style="width:30%;">
                                        ${purchaseItem.supplierItem.productModel!""}
                                    </td>
                                    <td class="center" style="width:15%;">
                                        ${purchaseItem.quantity}
                                    </td>
                                    <td class="center"  style="width:15%;">
                                        ${purchaseItem.unit}
                                    </td>
                                    <td class="center" style="width:15%;">
                                        ${purchaseItem.unitPrice}
                                    </td>
                                    <td class="center" style="width:15%;">
                                        ${purchaseItem.amount}
                                    </td>
                                </tr>
                                [/#list]
                            [/#if]
                            </tbody>
                            <tr>
                                <td style="text-align: center;" colspan="7">
                                    <button class="btn btn-mini btn-primary" type="button" onclick="top.Dialog.close();">返回</button>
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