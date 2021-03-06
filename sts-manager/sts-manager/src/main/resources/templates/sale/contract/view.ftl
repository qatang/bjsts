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
                        <div style="padding-top: 13px;"></div>
                        <table id="table_report" class="table table-striped table-bordered table-hover">
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">合同编号:</td>
                                <td>
                                ${contract.contractNo}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">项目编号:</td>
                                <td>${contract.planNo}</td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">项目名称:</td>
                                <td>${contract.planName}</td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">合同状态:</td>
                                <td>
                                ${contract.status.getName()}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">客户名称:</td>
                                <td>
                                ${contract.company}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">联系人:</td>
                                <td>
                                ${contract.linkman}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">签订日期:</td>
                                <td>
                                [@macro.displayDate value=contract.signTime!""/]

                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">签订人:</td>
                                <td>
                                ${contract.sign!""}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">合同金额:</td>
                                <td>[@macro.displayMoney value=contract.amount!''/]</td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">质保期限:</td>
                                <td>
                                [@macro.displayDate value=contract.qualityTime!""/]
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">质保金额:</td>
                                <td>
                                [@macro.displayMoney value=contract.qualityAmount!''/]
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">项目资料:</td>
                                <td>
                                    [@macro.displayFile document=document!"" /]
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