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
                        <form action="${ctx}/supplier/${action}" name="supplierForm" id="supplierForm" method="post">
                            <div style="padding-top: 13px;"></div>
                            [@macro.errors /]
                            <input type="hidden" name="supplier.id" value="${supplierForm.supplier.id!''}"/>
                            <table id="table_report" class="table table-striped table-bordered table-hover">
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">编号:</td>
                                    <td>
                                        [#if supplierForm.supplier.id??]
                                            ${supplierForm.supplier.id?c}
                                        [/#if]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">单位名称:</td>
                                    <td>
                                        [@macro.inputText name="supplier.company" value=supplierForm.supplier.company!'' placeholder="单位名称" required=false/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">联系人:</td>
                                    <td>
                                        [@macro.inputText name="supplier.linkman" value=supplierForm.supplier.linkman!'' placeholder="联系人"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">联系方式:</td>
                                    <td>
                                        [@macro.inputText name="supplier.contact" value=supplierForm.supplier.contact!'' placeholder="联系方式"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">单位地址:</td>
                                    <td>
                                        [@macro.inputText name="supplier.address" value=supplierForm.supplier.address!'' placeholder="单位地址" required=false/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">开户行:</td>
                                    <td>
                                        [@macro.inputText name="supplier.bankName" value=supplierForm.supplier.bankName!'' placeholder="开户行" required=false/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">账号:</td>
                                    <td>
                                        [@macro.inputText name="supplier.bankAccount" value=supplierForm.supplier.bankAccount!'' placeholder="账号" required=false/]
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