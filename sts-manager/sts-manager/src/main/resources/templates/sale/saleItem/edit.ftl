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
                        <form action="${ctx}/saleItem/${action}" name="saleItemForm" id="saleItemForm" method="post">
                            <div style="padding-top: 13px;"></div>
                        [@macro.errors /]
                            <input type="hidden" name="productOrder.id" value="${productOrder.id!''}"/>
                            <table id="table_report" class="table table-striped table-bordered table-hover">
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">项目编码:</td>
                                    <td>
                                        [#if productOrder.id??]
                                            ${productOrder.planNo}
                                        [/#if]
                                        </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">项目名称:</td>
                                    <td>${productOrder.name}</td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">联系人姓名:</td>
                                    <td>${productOrder.linkman}</td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">联系人电话:</td>
                                    <td>${productOrder.mobile}</td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">联系人单位:</td>
                                    <td>${productOrder.company}</td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">联系人邮箱:</td>
                                    <td>${productOrder.email}</td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">报价单:</td>
                                    <td>
                                    [#if quoteFileUrl??]
                                        <a href="${ctx}/file${quoteFileUrl}" target="_blank">${ctx}/file${quoteFileUrl}</a>
                                    [/#if]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">项目状态:</td>
                                    <td>
                                    [@macro.selectEnum name="productOrder.planStatus" enumObj=productOrder.planStatus!planStatusList[0] dataList=planStatusList /]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">追踪记录:</td>
                                    <td>
                                    [@macro.inputText name="saleItem.description" value=saleItemForm.saleItem.description!"" required=false/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="text-align: center;" colspan="10">
                                        <button class="btn btn-mini btn-primary" type="submit">提交</button>
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