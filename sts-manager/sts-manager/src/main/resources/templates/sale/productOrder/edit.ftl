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
                        <form action="${ctx}/productOrder/${action}" name="productOrderForm" id="productOrderForm" method="post">
                            <div style="padding-top: 13px;"></div>
                        [@macro.errors /]
                            <input type="hidden" name="productOrder.id" value="${productOrderForm.productOrder.id!''}"/>
                            <table id="table_report" class="table table-striped table-bordered table-hover">
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">项目编码:</td>
                                    <td>
                                        [#if productOrderForm.productOrder.id??]
                                            ${productOrderForm.productOrder.planNo}
                                        [/#if]
                                        </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">项目名称:</td>
                                    <td>
                                        [@macro.inputText name="productOrder.name" value=productOrderForm.productOrder.name!'' placeholder="项目名称"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">询价类型:</td>
                                    <td>
                                        [@macro.selectEnum name="productOrder.planType" enumObj=productOrderForm.productOrder.planType!planTypeList[0] dataList=planTypeList /]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">项目来源:</td>
                                    <td>
                                         [@macro.selectEnum name="productOrder.sourceType" enumObj=productOrderForm.productOrder.sourceType!sourceTypeList[0] dataList=sourceTypeList /]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">询价日期:</td>
                                    <td>
                                        [@macro.datetimePicker name="productOrder.priceTime" value=productOrderForm.productOrder.priceTime placeholder="询价时间"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">项目地点:</td>
                                    <td>
                                        [@macro.inputText name="productOrder.location" value=productOrderForm.productOrder.location!'' placeholder="项目地点"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">联系人姓名:</td>
                                    <td>
                                    [@macro.inputText name="productOrder.linkman" value=productOrderForm.productOrder.linkman!'' placeholder="联系人姓名"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">联系人电话:</td>
                                    <td>
                                    [@macro.inputText name="productOrder.mobile" value=productOrderForm.productOrder.mobile!'' placeholder="联系人电话"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">联系人单位:</td>
                                    <td>
                                    [@macro.inputText name="productOrder.company" value=productOrderForm.productOrder.company!'' placeholder="联系人单位"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">联系人邮箱:</td>
                                    <td>
                                    [@macro.inputText name="productOrder.email" value=productOrderForm.productOrder.email!'' placeholder="联系人邮箱"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">项目说明:</td>
                                    <td>
                                    [@macro.inputText name="productOrder.description" value=productOrderForm.productOrder.description!"" required=false/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">项目资料:</td>
                                    <td>
                                        <input id="input-customerFileUrl" name="file" type="file" multiple class="file-loading" data-show-upload="false">
                                        [@macro.inputText name="customerFileUrl" id="customerFileUrl" value=productOrderForm.customerFileUrl!'' placeholder="文件地址"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="text-align: center;" colspan="10">
                                        <button class="btn btn-mini btn-primary" type="submit">保存</button>
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
    var customerFileUrl = null;
    if ('${productOrderForm.customerFileUrl!''}' != "") {
        customerFileUrl = "${productOrderForm.customerFileUrl!''}";
    }

    $("#input-customerFileUrl").fileinput({
        language: "zh",
        uploadUrl: "/productOrder/upload",
        autoReplace: true,
        uploadAsync: true,
        maxFileCount: 1,
        allowedFileExtensions: ["jpg", "png", "gif", "rar", "zip", "doc", "docx", "pdf"],
        initialPreview: [
            customerFileUrl
        ]
    }).on("filebatchselected", function(event, files) {
        $(this).fileinput("upload");
    }).on("fileuploaded", function(event, data) {
        if (data.response) {
            $("#customerFileUrl").val(data.response.path);
        }
    });
</script>
</html>