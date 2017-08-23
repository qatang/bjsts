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
                        <table id="table_report" class="table table-striped table-bordered table-hover">
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">编号:</td>
                                <td>
                                    ${customer.id?c}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">客户类型:</td>
                                <td>
                                    ${customer.customerType.getName()}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">单位名称:</td>
                                <td>
                                    ${customer.companyName!""}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">所有者:</td>
                                <td>
                                    ${customer.owner!""}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">公司电话:</td>
                                <td>
                                    ${customer.tel!""}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">公司网址:</td>
                                <td>
                                    ${customer.url!""}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">公司传真:</td>
                                <td>
                                    ${customer.fax!""}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">单位地址:</td>
                                <td>
                                    ${customer.address!""}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">公司邮编:</td>
                                <td>
                                ${customer.postcode!""}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">备注:</td>
                                <td>
                                ${customer.memo!""}
                                </td>
                            </tr>
                        </table>
                        <table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">
                            <thead>
                            <tr>
                                <th class="center">姓名</th>
                                <th class="center">电话</th>
                                <th class="center">邮件</th>
                            </tr>
                            </thead>
                            <tbody>
                            [#if customerItemList?has_content]
                                [#list customerItemList as customerItem]
                                <tr>
                                    <td class="center">${customerItem.realName!""}</td>
                                    <td class="center">${customerItem.mobile!""}</td>
                                    <td class="center">${customerItem.email!""}</td>
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