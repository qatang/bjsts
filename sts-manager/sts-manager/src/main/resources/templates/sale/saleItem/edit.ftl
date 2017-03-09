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
                        <table id="simple-table" class="table table-striped table-bordered table-hover table-fixed-head" style="margin-top:5px;">
                            <thead>
                            <tr>
                                <th class="center">项目编码</th>
                                <th class="center">记录人</th>
                                <th class="center">追踪时间</th>
                                <th class="center">描述</th>
                                <th class="center">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            [#if planTraceList?has_content]
                                [#list planTraceList as planTrace]
                                <tr>
                                    <td class="center">${planTrace.planNo}</td>
                                    <td class="center">${planTrace.realName}</td>
                                    <td class="center">[@macro.displayDate value=planTrace.traceTime!""/]</td>
                                    <td class="center">${planTrace.description}</td>
                                    <td class="center">
                                        <a href="${ctx}/saleItem/disable/${planTrace.id?c}" onclick="return confirm('确定要删除吗?');">删除</a>
                                    </td>
                                </tr>
                                [/#list]
                            [/#if]
                            </tbody>
                        </table>
                        <form action="${ctx}/saleItem/${action}" name="saleItemForm" id="saleItemForm" method="post">
                            <div style="padding-top: 13px;"></div>
                            [@macro.errors /]
                            <input type="hidden" name="productOrder.id" value="${saleItemForm.productOrder.id!''}"/>
                            <table id="table_report" class="table table-striped table-bordered table-hover">
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">项目状态:</td>
                                    <td>
                                    [@macro.selectEnum name="productOrder.planStatus" enumObj=saleItemForm.productOrder.planStatus!planStatusList[0] dataList=planStatusList /]
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