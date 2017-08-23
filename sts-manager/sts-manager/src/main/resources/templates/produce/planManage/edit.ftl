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
                        <form action="${ctx}/planManage/${action}" name="planManageForm" id="planManageForm" method="post">
                            <div style="padding-top: 13px;"></div>
                            [@macro.errors /]
                            <input type="hidden" name="planManage.id" value="${planManageForm.planManage.id!''}"/>
                            <table id="table_report" class="table table-striped table-bordered table-hover">
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">项目编号:</td>
                                    <td>
                                    [#if planManageForm.planManage.id??]
                                        ${planManageForm.planManage.planNo}
                                        <input type="hidden" name="planManage.planNo" value="${planManageForm.planManage.planNo!''}"/>
                                    [#else]
                                        <select class="chosen-select form-control" name="planManage.planNo" data-placeholder="请选择" style="" id="planNo">
                                            <option value="0">请选择</option>
                                            [#list planList as data]
                                                <option value="${data.planNo!""}" [#if planManageForm.planManage.planNo?has_content && data.planNo == planManageForm.planManage.planNo]selected[/#if]>${data.getName()!""}</option>
                                            [/#list]
                                        </select>
                                    [/#if]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">预计交付日期:</td>
                                    <td>
                                    [@macro.datePicker name="planManage.expectDate" value=planManageForm.planManage.expectDate!"" placeholder="预计交付日期"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">项目执行情况:</td>
                                    <td>
                                    [@macro.selectEnum name="planManage.planExecuteStatus" enumObj=planManageForm.planManage.planExecuteStatus!planExecuteStatusList[0] dataList=planExecuteStatusList /]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">实际完成日期:</td>
                                    <td>
                                    [@macro.datePicker name="planManage.actualDate" value=planManageForm.planManage.actualDate!"" placeholder="实际完成日期"/]
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
    var planManageFileUrl = null;
    if ('${planManageForm.planManageFileUrl!''}' != "") {
        planManageFileUrl = "${planManageForm.planManageFileUrl!''}";
    }

    $("#input-planManageUrl").fileinput({
        language: "zh",
        uploadUrl: "/planManage/upload",
        autoReplace: true,
        uploadAsync: true,
        maxFileCount: 1,
        //allowedFileExtensions: ["jpg", "png", "gif", "rar", "zip", "doc", "docx", "pdf"],
        initialPreview: [
            planManageFileUrl
        ]
    }).on("filebatchselected", function(event, files) {
        $(this).fileinput("upload");
    }).on("fileuploaded", function(event, data) {
        if (data.response) {
            $("#planManageFileUrl").val(data.response.path);
        }
    });
</script>
</html>