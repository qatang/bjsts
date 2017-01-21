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
                                        [#else]
                                            <input type="text" name="contract.planNo" id="planNo" placeholder="请输入项目编号" onchange="queryPlan(this);"/>
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
                                    <td style="width:10%;text-align: right;padding-top: 13px;">合同内容:</td>
                                    <td>
                                        <input type="hidden" name="contract.content" id="content" placeholder="这里输入合同内容" style="width:98%;" required="required"/>
                                        <script id="editor" type="text/plain" style="width:100%;height:100px;"></script>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:10%;text-align: right;padding-top: 13px;">变更内容:</td>
                                    <td>
                                        <input type="hidden" name="contract.changeContent" id="changeContent" placeholder="这里输入变更内容" style="width:98%;"/>
                                        <script id="changeEditor" type="text/plain" style="width:100%;height:100px;"></script>
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
                                    [@macro.datetimePicker name="contract.signTime" value=contractForm.contract.signTime placeholder="签订日期"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">质保日期:</td>
                                    <td>
                                    [@macro.datetimePicker name="contract.qualityTime" value=contractForm.contract.qualityTime placeholder="质保日期"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">合同金额:</td>
                                    <td>
                                        [#if contractForm.contract.amount??]
                                            [@macro.inputText name="contract.amount" value=contractForm.contract.amount?c placeholder="合同金额"/]
                                        [#else]
                                            [@macro.inputText name="contract.amount" value='' placeholder="合同金额"/]
                                        [/#if]

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
                                        <button class="btn btn-mini btn-primary" type="button" id="submitForm" onclick="sumbitContract()">保存</button>
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
<script type="text/javascript" charset="utf-8" src="/plugins/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="/plugins/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="/plugins/ueditor/lang/zh-cn/zh-cn.js"></script>

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
        allowedFileExtensions: ["jpg", "png", "gif", "rar", "zip", "doc", "docx", "pdf"],
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

    //初始化ueditor
    //UE.getEditor('editor', {
      //  autoHeightEnabled: false
    //});

    //设置内容
    UE.getEditor('editor').ready(function() {
        UE.getEditor('editor').setContent('${contractForm.contract.content!''}');
    });

    //设置内容
    UE.getEditor('changeEditor').ready(function() {
        UE.getEditor('changeEditor').setContent('${contractForm.contract.changeContent!''}');
    });

    //初始化ueditor
    //UE.getEditor('changeEditor', {
      //  autoHeightEnabled: false
    //});

    function sumbitContract() {

        var content = UE.getEditor('editor').getContent();
        if (!content) {
            alert("合同内容未输入！")
            return false;
        }
        $("#content").val(content);

        var changeContent = UE.getEditor('changeEditor').getContent();
        $("#changeContent").val(changeContent);

        $("#contractForm").submit();
    }
</script>
</html>