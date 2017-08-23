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
                        <form action="${ctx}/contract/${action}" name="contractForm" id="contractForm" method="post">
                            <div style="padding-top: 13px;"></div>
                        [@macro.errors /]
                            <input type="hidden" name="contract.id" value="${contractForm.contract.id!''}"/>
                            <table id="table_report" class="table table-striped table-bordered table-hover">
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">项目编码:</td>
                                    <td>
                                        [#if contractForm.contract.id??]
                                            ${contractForm.contract.planNo}
                                            <input type="hidden" name="contract.planNo" value="${contractForm.contract.planNo!''}"/>
                                        [/#if]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">项目名称:</td>
                                    <td>
                                        <input type="text" name="contract.planName" id="planName" value="${contractForm.contract.planName!''}" required/>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">合同编号:</td>
                                    <td>
                                    [#if contractForm.contract.id??]
                                    ${contractForm.contract.contractNo}
                                    [/#if]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">客户名称:</td>
                                    <td>
                                        <input type="text" name="contract.company" id="company" value="${contractForm.contract.company!''}" required/>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">联系人:</td>
                                    <td>
                                        <input type="text" name="contract.linkman" id="linkman" value="${contractForm.contract.linkman!''}" required/>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">签订日期:</td>
                                    <td>
                                    [@macro.datePicker name="contract.signTime" value=contractForm.contract.signTime!"" placeholder="签订日期"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">签订人:</td>
                                    <td>
                                        <input type="text" name="contract.sign" value="${contractForm.contract.sign!''}" required/>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">合同金额:</td>
                                    <td>
                                        [@macro.inputMoney name="amount" value=contractForm.amount!'' placeholder="合同金额"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">质保期限:</td>
                                    <td>
                                    [@macro.datePicker name="contract.qualityTime" value=contractForm.contract.qualityTime!"" placeholder="质保期限"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">质保金额:</td>
                                    <td>
                                    [@macro.inputMoney name="qualityAmount" value=contractForm.qualityAmount!'' placeholder="质保金额"/]
                                    </td>
                                </tr>
                                 <tr>
                                     <td style="width:100px;text-align: right;padding-top: 13px;">合同扫描附件:</td>
                                     <td>
                                         <input id="input-fileName" name="file" type="file" multiple class="file-loading" data-show-upload="false">
                                         <input type="hidden" name="document.name" id="fileName" value="${contractForm.document.name!''}"/>
                                         <input type="hidden" name="document.url" id="contractUrl" value="${contractForm.document.url!''}"/>
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

<link href="/static/js/bootstrap-fileinput-master/css/fileinput.min.css" media="all" rel="stylesheet" type="text/css" />
<script src="/static/js/bootstrap-fileinput-master/js/fileinput.min.js"></script>
<script src="/static/js/bootstrap-fileinput-master/js/locales/zh.js"></script>

<script>
    var fileName = null;
    if ('${contractForm.document.name!''}' != "") {
        fileName = "${contractForm.document.name!''}";
    }

    $("#input-fileName").fileinput({
        language: "zh",
        uploadUrl: "/document/upload",
        uploadExtraData: {"group":"contract"},
        autoReplace: true,
        uploadAsync: true,
        maxFileCount: 1,
        //allowedFileExtensions: ["jpg", "png", "gif", "rar", "zip", "doc", "docx", "pdf"],
        initialPreview: [
            fileName
        ]
    }).on("filebatchselected", function(event, files) {
        $(this).fileinput("upload");
    }).on("fileuploaded", function(event, data) {
        if (data.response) {
            $("#contractUrl").val(data.response.path);
            $("#fileName").val(data.response.fileName);
        }
    });
</script>
</html>