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
                                    ${customerForm.customer.id?c}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">客户类型:</td>
                                <td>
                                    ${customerForm.customer.customerType.getName()}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">单位名称:</td>
                                <td>
                                    ${customerForm.customer.companyName!""}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">所有者:</td>
                                <td>
                                    ${customerForm.customer.owner!""}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">公司电话:</td>
                                <td>
                                    ${customerForm.customer.tel!""}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">公司网址:</td>
                                <td>
                                    ${customerForm.customer.url!""}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">公司传真:</td>
                                <td>
                                    ${customerForm.customer.fax!""}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">单位地址:</td>
                                <td>
                                    ${customerForm.customer.address!""}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">公司邮编:</td>
                                <td>
                                ${customerForm.customer.postcode!""}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">备注:</td>
                                <td>
                                ${customerForm.customer.memo!""}
                                </td>
                            </tr>
                        </table>
                        <table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">
                            <thead>
                            <tr>
                                <th class="center">姓名</th>
                                <th class="center">电话</th>
                                <th class="center">邮件</th>
                                <th class="center">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            [#if customerItemList?has_content]
                                [#list customerItemList as customerItem]
                                <tr>
                                    <td class="center">${customerItem.realName!""}</td>
                                    <td class="center">${customerItem.mobile!""}</td>
                                    <td class="center">${customerItem.email!""}</td>
                                    <td class="center">
                                        <a href="${ctx}/customer/disableItem/${customerItem.id?c}" onclick="return confirm('确定要删除吗?');">删除</a>
                                    </td>
                                </tr>
                                [/#list]
                            [/#if]
                            </tbody>
                        </table>
                        <form action="${ctx}/customer/createItem" name="customerForm" id="customerForm" method="post">
                            [@macro.errors /]
                            <input type="hidden" name="customerItem.customerId" value="${customerForm.customer.id?c}"/>
                            <table id="table_report" class="table table-striped table-bordered table-hover">
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">姓名:</td>
                                    <td>
                                    [@macro.inputText name="customerItem.realName" value=customerForm.customerItem.realName!"" required=false/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">电话:</td>
                                    <td>
                                    [@macro.inputText name="customerItem.mobile" value=customerForm.customerItem.mobile!"" required=false/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">邮箱:</td>
                                    <td>
                                    [@macro.inputText name="customerItem.email" value=customerForm.customerItem.email!"" required=false/]
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