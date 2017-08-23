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
                        <form action="${ctx}/invoice/${action}" name="invoiceForm" id="invoiceForm" method="post">
                            <div style="padding-top: 13px;"></div>
                            [@macro.errors /]
                            <input type="hidden" name="invoice.id" value="${invoiceForm.invoice.id!''}"/>
                            <table id="table_report" class="table table-striped table-bordered table-hover">
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">编号:</td>
                                    <td>
                                        [#if invoiceForm.invoice.id??]
                                            ${invoiceForm.invoice.id?c}
                                        [/#if]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">项目编号:</td>
                                    <td>
                                        <select class="chosen-select form-control" name="invoice.planNo" data-placeholder="请选择" style="" id="planNo">
                                            <option value="0">请选择</option>
                                            [#list planList as data]
                                                <option value="${data.planNo!""}" [#if invoiceForm.invoice.planNo?has_content && data.planNo == invoiceForm.invoice.planNo]selected[/#if]>${data.getName()!""}</option>
                                            [/#list]
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">项目内容:</td>
                                    <td>
                                        [@macro.inputText name="invoice.planContent" value=invoiceForm.invoice.planContent!'' placeholder="项目内容" required=false/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">发票类型:</td>
                                    <td>
                                    [@macro.selectEnum name="invoice.invoiceCategory" enumObj=invoiceForm.invoice.invoiceCategory!invoiceCategoryList[0] dataList=invoiceCategoryList /]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">发票编号:</td>
                                    <td>
                                        [@macro.inputText name="invoice.invoiceNo" value=invoiceForm.invoice.invoiceNo!'' placeholder="发票编号"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">客户名称:</td>
                                    <td>
                                        [@macro.inputText name="invoice.customer" value=invoiceForm.invoice.customer!'' placeholder="客户名称"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">开票日期:</td>
                                    <td>
                                    [@macro.datePicker name="invoice.invoiceDate" value=invoiceForm.invoice.invoiceDate!"" placeholder="开票日期"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">开票金额:</td>
                                    <td>
                                        [@macro.inputMoney name="amount" value=invoiceForm.amount!'' placeholder="开票金额"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">开票内容:</td>
                                    <td>
                                        [@macro.inputText name="invoice.content" value=invoiceForm.invoice.content!'' placeholder="开票内容"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">抵扣日期:</td>
                                    <td>
                                    [@macro.datePicker name="invoice.deductionDate" value=invoiceForm.invoice.deductionDate!"" placeholder="抵扣日期"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">发票扫描附件:</td>
                                    <td>
                                        <input id="input-invoiceUrl" name="file" type="file" multiple class="file-loading" data-show-upload="false">
                                        <input type="hidden" name="document.name" id="fileName" value="${invoiceForm.document.name!''}"/>
                                        <input type="hidden" name="document.url" id="invoiceUrl" value="${invoiceForm.document.url!''}"/>
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
    if ('${invoiceForm.document.name!''}' != "") {
        fileName = "${invoiceForm.document.name!''}";
    }

    $("#input-invoiceUrl").fileinput({
        language: "zh",
        uploadUrl: "/document/upload",
        uploadExtraData: {"group":"invoice"},
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
            $("#invoiceUrl").val(data.response.path);
            $("#fileName").val(data.response.fileName);
        }
    });
</script>
</html>