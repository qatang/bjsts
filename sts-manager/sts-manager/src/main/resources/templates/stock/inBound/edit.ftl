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
                        <form action="${ctx}/inBound/${action}" name="inBoundForm" id="inBoundForm" method="post">
                            <div style="padding-top: 13px;"></div>
                        [@macro.errors /]
                            <input type="hidden" name="inBound.id" value="${inBoundForm.inBound.id!''}"/>
                            <input type="hidden" name="inBound.purchaseNo" value="${inBoundForm.inBound.purchase.purchaseNo!''}"/>
                            <table id="table_report" class="table table-striped table-bordered table-hover">
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">采购单编号:</td>
                                    <td>
                                        ${inBoundForm.inBound.purchase.purchaseNo}
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">产品名称:</td>
                                    <td>
                                        ${inBoundForm.inBound.purchase.productName!""}
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">规格型号:</td>
                                    <td>
                                        ${inBoundForm.inBound.purchase.productModel!""}
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">采购数量:</td>
                                    <td>
                                        ${inBoundForm.inBound.purchase.quantity!""}
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">采购负责人:</td>
                                    <td>
                                        ${inBoundForm.inBound.purchase.operator!""}
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">到货物品核对:</td>
                                    <td>
                                    [@macro.selectEnum name="inBound.verify" enumObj=inBoundForm.inBound.verify!yesNoStatusList[0] dataList=yesNoStatusList /]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">接收人:</td>
                                    <td>
                                    [@macro.inputText name="inBound.sendee" value=inBoundForm.inBound.sendee!'' placeholder="接收人"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">接受日期:</td>
                                    <td>
                                    [@macro.datePicker name="inbound.sendeeTime" value=inBoundForm.inBound.sendeeTime!"" placeholder="接收日期"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">备注:</td>
                                    <td>
                                    [@macro.inputText name="inBound.memo" value=inBoundForm.inBound.memo!'' placeholder="备注" required=false/]
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

<link href="/plugins/bootstrap-fileinput-master/css/fileinput.min.css" media="all" rel="stylesheet" type="text/css" />
<script src="/plugins/bootstrap-fileinput-master/js/fileinput.min.js"></script>
<script src="/plugins/bootstrap-fileinput-master/js/locales/zh.js"></script>

<script>
    var inBoundContractUrl = null;
    if ('${inBoundForm.inBoundContractUrl!''}' != "") {
        inBoundContractUrl = "${inBoundForm.inBoundContractUrl!''}";
    }

    $("#input-inBoundContractUrl").fileinput({
        language: "zh",
        uploadUrl: "/inBound/upload",
        autoReplace: true,
        uploadAsync: true,
        maxFileCount: 1,
        allowedFileExtensions: ["jpg", "png", "gif", "rar", "zip", "doc", "docx", "pdf"],
        initialPreview: [
            inBoundContractUrl
        ]
    }).on("filebatchselected", function(event, files) {
        $(this).fileinput("upload");
    }).on("fileuploaded", function(event, data) {
        if (data.response) {
            $("#inBoundContractUrl").val(data.response.path);
        }
    });
</script>
</html>