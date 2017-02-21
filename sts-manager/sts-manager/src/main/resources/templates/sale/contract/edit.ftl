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
                                        [#else]
                                            <select class="chosen-select form-control" name="contract.planNo" data-placeholder="请选择" style="" onchange="queryPlan(this);" id="planNo">
                                                <option value="0">请选择</option>
                                                [#list planList as data]
                                                    <option value="${data.planNo!""}" [#if contractForm.contract.planNo?has_content && data.planNo == contractForm.contract.planNo]selected[/#if]>${data.getName()!""}</option>
                                                [/#list]
                                            </select>
                                        [/#if]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">项目名称:</td>
                                    <td>
                                        <input type="text" name="contract.planName" id="planName" value="${contractForm.contract.planName!''}" readonly required/>
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
                                    <td style="width:100px;text-align: right;padding-top: 13px;">合同状态:</td>
                                    <td>
                                        [@macro.selectEnum name="contract.status" enumObj=contractForm.contract.status!contractStatusList[0] dataList=contractStatusList /]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">客户名称:</td>
                                    <td>
                                        <input type="text" name="contract.company" id="company" value="${contractForm.contract.company!''}" readonly required/>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">联系人:</td>
                                    <td>
                                        <input type="text" name="contract.linkman" id="linkman" value="${contractForm.contract.linkman!''}" readonly required/>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">签订日期:</td>
                                    <td>
                                    [@macro.datetimePicker name="contract.signTime" value=contractForm.contract.signTime!"" placeholder="签订日期"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">质保日期:</td>
                                    <td>
                                    [@macro.datetimePicker name="contract.qualityTime" value=contractForm.contract.qualityTime!"" placeholder="质保日期"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">合同金额:</td>
                                    <td>
                                        [@macro.inputMoney name="amount" value=contractForm.amount!'' placeholder="合同金额"/]
                                    </td>
                                </tr>
                                 <tr>
                                     <td style="width:100px;text-align: right;padding-top: 13px;">合同扫描附件:</td>
                                     <td>
                                         <input id="input-contractUrl" name="file" type="file" multiple class="file-loading" data-show-upload="false">
                                         [@macro.inputText name="contractUrl" id="contractUrl" value=contractForm.contractUrl!'' placeholder="合同扫描附件"/]
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
    var contractUrl = null;
    if ('${contractForm.contractUrl!''}' != "") {
        contractUrl = "${contractForm.contractUrl!''}";
    }

    $("#input-contractUrl").fileinput({
        language: "zh",
        uploadUrl: "/contract/upload",
        autoReplace: true,
        uploadAsync: true,
        maxFileCount: 1,
        //allowedFileExtensions: ["jpg", "png", "gif", "rar", "zip", "doc", "docx", "pdf"],
        initialPreview: [
            contractUrl
        ]
    }).on("filebatchselected", function(event, files) {
        $(this).fileinput("upload");
    }).on("fileuploaded", function(event, data) {
        if (data.response) {
            $("#contractUrl").val(data.response.path);
        }
    });

    /**
     *
     * @param url
     */
    function queryPlan() {
        planNo = $('#planNo').val();
        $.ajax({
            type: "get",
            url: "${ctx}/contract/findPlan/" + planNo,
            data: {},
            dataType: "json",
            async: false,
            success: function (data) {
                $('#planName').val(data.name);
                $('#company').val(data.company);
                $('#linkman').val(data.linkman);
            },
            error: function (xmlHttpRequest,error) {
            },
            complete: function(xmlHttpRequest) {
            }
        });
    }
</script>
</html>