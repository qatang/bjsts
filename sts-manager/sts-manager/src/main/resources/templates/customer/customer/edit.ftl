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
                        <form action="${ctx}/customer/${action}" name="customerForm" id="customerForm" method="post">
                            <div style="padding-top: 13px;"></div>
                            [@macro.errors /]
                            <input type="hidden" name="customer.id" value="${customerForm.customer.id!''}"/>
                            <table id="table_report" class="table table-striped table-bordered table-hover">
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">编号:</td>
                                    <td>
                                        [#if customerForm.customer.id??]
                                            ${customerForm.customer.id?c}
                                        [/#if]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">客户类型:</td>
                                    <td>
                                    [@macro.selectEnum name="customer.customerType" enumObj=customerForm.customer.customerType!customerTypeList[0] dataList=customerTypeList /]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">客户名称:</td>
                                    <td>
                                    [@macro.inputText name="customer.companyName" value=customerForm.customer.companyName!'' placeholder="客户名称" required=false/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">所有者:</td>
                                    <td>
                                        [@macro.inputText name="customer.owner" value=customerForm.customer.owner!'' placeholder="所有者" required=false/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">公司电话:</td>
                                    <td>
                                        [@macro.inputText name="customer.tel" value=customerForm.customer.tel!'' placeholder="公司电话" required=false/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">公司网址:</td>
                                    <td>
                                        [@macro.inputText name="customer.url" value=customerForm.customer.url!'' placeholder="公司网址" required=false/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">公司传真:</td>
                                    <td>
                                    [@macro.inputText name="customer.fax" value=customerForm.customer.fax!'' placeholder="公司传真" required=false/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">单位地址:</td>
                                    <td>
                                        [@macro.inputText name="customer.address" value=customerForm.customer.address!'' placeholder="单位地址" required=false/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">邮编:</td>
                                    <td>
                                    [@macro.inputText name="customer.postcode" value=customerForm.customer.postcode!'' placeholder="邮编" required=false/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">备注:</td>
                                    <td>
                                    [@macro.inputText name="customer.memo" value=customerForm.customer.memo!'' placeholder="备注" required=false/]
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