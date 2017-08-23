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
                        <form action="${ctx}/quote/${action}" name="quoteForm" id="quoteForm" method="post">
                            <div style="padding-top: 13px;"></div>
                        [@macro.errors /]
                            <input type="hidden" name="productOrder.id" value="${quoteForm.productOrder.id!''}"/>
                            <table id="table_report" class="table table-striped table-bordered table-hover">
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">项目编码:</td>
                                    <td>
                                        [#if quoteForm.productOrder.id??]
                                            ${quoteForm.productOrder.planNo}
                                        [/#if]
                                        </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">项目名称:</td>
                                    <td>${quoteForm.productOrder.name}</td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">询价类型:</td>
                                    <td>
                                    ${quoteForm.productOrder.planType.getName()}
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">项目来源:</td>
                                    <td>
                                    ${quoteForm.productOrder.sourceType.getName()}
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">询价日期:</td>
                                    <td>
                                    [@macro.displayDate value=quoteForm.productOrder.priceTime!""/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">项目地点:</td>
                                    <td>${quoteForm.productOrder.location}</td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">联系人姓名:</td>
                                    <td>${quoteForm.productOrder.linkman}</td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">联系人电话:</td>
                                    <td>${quoteForm.productOrder.mobile}</td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">联系人单位:</td>
                                    <td>${quoteForm.productOrder.company}</td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">联系人邮箱:</td>
                                    <td>${quoteForm.productOrder.email}</td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">项目说明:</td>
                                    <td>${quoteForm.productOrder.description}</td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">项目资料:</td>
                                    <td>
                                    [#if quoteForm.customerFileUrl??]
                                        <a href="${ctx}/file${quoteForm.customerFileUrl}" target="_blank">${ctx}/file${quoteForm.customerFileUrl}</a>
                                    [/#if]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">报价日期:</td>
                                    <td>
                                    [@macro.datetimePicker name="productOrder.quoteTime" value=quoteForm.productOrder.quoteTime!"" placeholder="报价日期"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">报价单:</td>
                                    <td>
                                        <input id="input-quoteFileUrl" name="file" type="file" multiple class="file-loading" data-show-upload="false">
                                        <input type="hidden" name="document.name" id="fileName" value="${quoteForm.document.name!''}"/>
                                        <input type="hidden" name="document.url" id="quoteFileUrl" value="${quoteForm.document.url!''}"/>
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

<link href="/static/js/bootstrap-fileinput-master/css/fileinput.min.css" media="all" rel="stylesheet" type="text/css" />
<script src="/static/js/bootstrap-fileinput-master/js/fileinput.min.js"></script>
<script src="/static/js/bootstrap-fileinput-master/js/locales/zh.js"></script>

<script>
    var fileName = null;
    if ('${quoteForm.document.name!''}' != "") {
        fileName = "${quoteForm.document.name!''}";
    }

    $("#input-quoteFileUrl").fileinput({
        language: "zh",
        uploadUrl: "/document/upload",
        uploadExtraData: {"group":"quote"},
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
            $("#quoteFileUrl").val(data.response.path);
            $("#fileName").val(data.response.fileName);
        }
    });
</script>
</html>