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
                        <form action="${ctx}/contract/updateStatus" name="contractForm" id="contractForm" method="post">
                            <div style="padding-top: 13px;"></div>
                            [@macro.errors /]
                            <input type="hidden" name="contract.id" value="${contractForm.contract.id!''}"/>
                            <table id="table_report" class="table table-striped table-bordered table-hover">
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">合同编号:</td>
                                <td>
                                ${contractForm.contract.contractNo}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">项目编号:</td>
                                <td>${contractForm.contract.planNo}</td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">项目名称:</td>
                                <td>${contractForm.contract.planName}</td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">合同状态:</td>
                                <td>
                                [@macro.selectEnum name="contract.status" enumObj=contractForm.contract.status!contractStatusList[0] dataList=contractStatusList /]
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">客户名称:</td>
                                <td>
                                ${contractForm.contract.company}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">联系人:</td>
                                <td>
                                ${contractForm.contract.linkman}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">签订日期:</td>
                                <td>
                                [@macro.displayDate value=contractForm.contract.signTime!""/]

                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">签订人:</td>
                                <td>
                                ${contractForm.contract.sign!""}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">合同金额:</td>
                                <td>[@macro.displayMoney value=contractForm.contract.amount!''/]</td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">质保期限:</td>
                                <td>
                                [@macro.displayDate value=contractForm.contract.qualityTime!""/]
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">质保金额:</td>
                                <td>
                                [@macro.displayMoney value=contractForm.contract.qualityAmount!''/]
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">项目资料:</td>
                                <td>
                                [#if contractUrl??]
                                    <a href="${ctx}/file${contractUrl}" target="_blank">${ctx}/file${contractUrl}</a>
                                [/#if]
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