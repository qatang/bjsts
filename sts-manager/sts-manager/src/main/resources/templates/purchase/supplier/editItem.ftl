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
                        <form action="${ctx}/supplier/createItem" name="supplierForm" id="supplierForm" method="post">
                            [@macro.errors /]
                            <input type="hidden" name="supplier.id" value="${supplier.id!''}"/>
                            <table id="table_report" class="table table-striped table-bordered table-hover">
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">产品名称:</td>
                                    <td>
                                    [@macro.inputText name="supplierItem.productName" value=supplierForm.supplierItem.productName!'' placeholder="产品名称"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">规格型号:</td>
                                    <td>
                                    [@macro.inputText name="supplierItem.productModel" value=supplierForm.supplierItem.productModel!'' placeholder="规格型号"/]
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
                                <th class="center">产品名称</th>
                                <th class="center">规格型号</th>
                                <th class="center">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            [#if supplierItemList?has_content]
                                [#list supplierItemList as supplierItem]
                                <tr>
                                    <td class="center">${supplierItem.productName!""}</td>
                                    <td class="center">${supplierItem.productModel!""}</td>
                                    <td class="center">
                                        <a href="${ctx}/supplier/disable/item/${supplierItem.id?c}" onclick="return confirm('确定要删除吗?');">删除</a>
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