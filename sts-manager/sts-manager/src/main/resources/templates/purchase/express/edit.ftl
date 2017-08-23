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
                        <form action="${ctx}/express/${action}" name="expressForm" id="expressForm" method="post">
                            <div style="padding-top: 13px;"></div>
                            [@macro.errors /]
                            <input type="hidden" name="express.id" value="${expressForm.express.id!''}"/>
                            <table id="table_report" class="table table-striped table-bordered table-hover">
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">编号:</td>
                                    <td>
                                        [#if expressForm.express.id??]
                                            ${expressForm.express.id?c}
                                        [/#if]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">快递单号:</td>
                                    <td>
                                    [@macro.inputText name="express.expressNo" value=expressForm.express.expressNo!'' placeholder="快递单号"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">发货人:</td>
                                    <td>
                                        [@macro.inputText name="express.shipper" value=expressForm.express.shipper!'' placeholder="发货人"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">付款人:</td>
                                    <td>
                                        [@macro.inputText name="express.payer" value=expressForm.express.payer!'' placeholder="付款人"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">快递费:</td>
                                    <td>
                                        [@macro.inputMoney name="cost" value=expressForm.cost!'' placeholder="快递费"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">发货内容:</td>
                                    <td>
                                        [@macro.inputText name="express.content" value=expressForm.express.content!'' placeholder="发货内容"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">收货人姓名:</td>
                                    <td>
                                        [@macro.inputText name="express.receiver" value=expressForm.express.receiver!'' placeholder="收货人姓名"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">收货人电话:</td>
                                    <td>
                                        [@macro.inputText name="express.mobile" value=expressForm.express.mobile!'' placeholder="收货人电话"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">收货人地址:</td>
                                    <td>
                                        [@macro.inputText name="express.address" value=expressForm.express.address!'' placeholder="收货人地址"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">快递单位名称:</td>
                                    <td>
                                        [@macro.inputText name="express.company" value=expressForm.express.company!'' placeholder="快递单位名称" required=false/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">投递日期:</td>
                                    <td>
                                        [@macro.datePicker name="express.deliverDate" value=expressForm.express.deliverDate!"" placeholder="投递日期"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">接收日期:</td>
                                    <td>
                                    [@macro.datePicker name="express.receiveDate" value=expressForm.express.receiveDate!"" placeholder="接收日期"/]
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