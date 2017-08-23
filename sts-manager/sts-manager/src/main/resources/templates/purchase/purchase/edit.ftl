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
                        <form action="${ctx}/purchase/${action}" name="purchaseForm" id="purchaseForm" method="post">
                            <div style="padding-top: 13px;"></div>
                            [@macro.errors /]
                            <input type="hidden" name="purchase.id" value="${purchaseForm.purchase.id!''}"/>
                            <table id="table_report" class="table table-striped table-bordered table-hover">
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">采购单编号:</td>
                                    <td>
                                        [#if purchaseForm.purchase.id??]
                                            ${purchaseForm.purchase.purchaseNo}
                                        [/#if]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">采购申请人:</td>
                                    <td>
                                    [#if purchaseForm.purchase.inBound.getValue() == noStatus.getValue()]
                                            [@macro.inputText name="purchase.proposer" value=purchaseForm.purchase.proposer!'' placeholder="采购申请人"/]
                                        [#else]
                                            ${purchaseForm.purchase.proposer!''}
                                    [/#if]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">采购负责人:</td>
                                    <td>
                                        [#if purchaseForm.purchase.inBound.getValue() == noStatus.getValue()]
                                            [@macro.inputText name="purchase.operator" value=purchaseForm.purchase.operator!'' placeholder="采购负责人"/]
                                        [#else]
                                            ${purchaseForm.purchase.operator!''}
                                        [/#if]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">供应商:</td>
                                    <td>
                                        <select class="chosen-select form-control" id="supplierId" name="purchase.supplierId" data-placeholder="请选择供应商" style="">
                                            <option value="">请选择</option>
                                            [#list supplierList as data]
                                                <option value="${data.id?c}" [#if purchaseForm.purchase.supplierId?has_content && data.id == purchaseForm.purchase.supplierId]selected[/#if]>${data.company}</option>
                                            [/#list]
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">采购日期:</td>
                                    <td>
                                    [#if purchaseForm.purchase.inBound.getValue() == noStatus.getValue()]
                                        [@macro.datetimePicker name="purchase.purchaseTime" value=purchaseForm.purchase.purchaseTime!"" placeholder="采购日期"/]
                                    [#else]
                                        [@macro.displayDate value=purchaseForm.purchase.purchaseTime!''/]
                                    [/#if]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">采购金额:</td>
                                    <td>
                                        [@macro.inputMoney name="totalAmount" value=purchaseForm.totalAmount!'' placeholder="采购金额"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">已付款金额:</td>
                                    <td>
                                        [@macro.inputMoney name="payedAmount" value=purchaseForm.payedAmount!'' placeholder="已付款金额"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">未付款金额:</td>
                                    <td>
                                        [@macro.inputMoney name="unPayedAmount" value=purchaseForm.unPayedAmount!'' placeholder="未付款金额"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">发票状态:</td>
                                    <td>
                                        [@macro.selectEnum name="purchase.makeOutInvoiceStatus" enumObj=purchaseForm.purchase.makeOutInvoiceStatus!invoiceStatusList[0] dataList=invoiceStatusList /]
                                    </td>
                                </tr>
                                 <tr>
                                     <td style="width:100px;text-align: right;padding-top: 13px;">采购合同扫描附件:</td>
                                     <td>
                                         <input id="input-purchaseContractUrl" name="file" type="file" multiple class="file-loading" data-show-upload="false">
                                         <input type="hidden" name="document.name" id="fileName" value="${purchaseForm.document.name!''}"/>
                                         <input type="hidden" name="document.url" id="purchaseContractUrl" value="${purchaseForm.document.url!''}"/>
                                     </td>
                                 </tr>
                                <tr>
                                    <td style="text-align: center;" colspan="10">
                                        <table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">
                                            <thead>
                                            <tr>[#--
                                                <th class="center">编号</th>--]
                                                <th class="center">产品</th>
                                                <th class="center">数量</th>
                                                <th class="center">单位</th>
                                                <th class="center">单价</th>
                                                <th class="center">金额</th>
                                                <th class="center">操作</th>
                                            </tr>
                                            </thead>
                                            <tbody id="tbody">
                                            [#if purchaseForm.purchaseItemList?? && purchaseForm.purchaseItemList?has_content]
                                                [#list purchaseForm.purchaseItemList as purchaseItem]
                                                <tr>
                                                    <td class="center" style="width:30%;">
                                                        <input type="hidden" name="purchaseItemList[${purchaseItem_index}].id" value="${purchaseItem.id}"/>
                                                        <select id="supplierItemId${purchaseItem_index}" class="supplierItem form-control" name="purchaseItemList[${purchaseItem_index}].supplierItemId" data-placeholder="请选择产品" style="vertical-align:top;width:98%;" data="${purchaseItem.supplierItemId}">
                                                            <option value="">请选择</option>
                                                        </select>
                                                    </td>
                                                    <td class="center" style="width:15%;">
                                                        <input type="text" name="purchaseItemList[${purchaseItem_index}].quantity" maxlength="20" style="width:50px;" value="${purchaseItem.quantity}"/>
                                                    </td>
                                                    <td class="center"  style="width:15%;">
                                                        <input type="text" name="purchaseItemList[${purchaseItem_index}].unit" maxlength="20" style="width:50px;" value="${purchaseItem.unit}"/>
                                                    </td>
                                                    <td class="center" style="width:15%;">
                                                        <input type="text" name="purchaseItemList[${purchaseItem_index}].unitPrice" maxlength="20" style="width:50px;" value="${purchaseItem.unitPrice}"/>
                                                    </td>
                                                    <td class="center" style="width:15%;">
                                                        <input type="text" name="purchaseItemList[${purchaseItem_index}].amount" maxlength="20" style="width:50px;" value="${purchaseItem.amount}"/>
                                                    </td>
                                                    <td class="center" style="width: 10%;">
                                                        <a class="prize_item_plus green" style="cursor:pointer;text-decoration:none;" onclick="addTr()">
                                                            <i class="ace-icon fa fa-plus bigger-130" title="增加一条记录"></i>
                                                        </a>
                                                        <a class="prize_item_minus red" style="cursor:pointer;text-decoration:none;" onclick="delTr(this)">
                                                            <i class="ace-icon fa fa-minus bigger-130" title="减少一条记录"></i>
                                                        </a>
                                                    </td>
                                                </tr>
                                                [/#list]
                                            [#else]
                                                <tr>
                                                   [#-- <td class="center">
                                                        <input type="text" name="purchaseItemList[0].id" maxlength="32" style="width:98%;"/>
                                                    </td>--]
                                                    <td class="center" style="width:30%;">
                                                        <select id="supplierItemId0" class="supplierItem form-control" name="purchaseItemList[0].supplierItemId" data-placeholder="请选择产品" style="vertical-align:top;width:98%;">
                                                            <option value="">请选择</option>
                                                        </select>
                                                    </td>
                                                    <td class="center" style="width:15%;">
                                                        <input type="text" name="purchaseItemList[0].quantity" maxlength="20" style="width:50px;"/>
                                                    </td>
                                                    <td class="center"  style="width:15%;">
                                                        <input type="text" name="purchaseItemList[0].unit" maxlength="20" style="width:50px;"/>
                                                    </td>
                                                    <td class="center" style="width:15%;">
                                                        <input type="text" name="purchaseItemList[0].unitPrice" maxlength="20" style="width:50px;"/>
                                                    </td>
                                                    <td class="center" style="width:15%;">
                                                        <input type="text" name="purchaseItemList[0].amount" maxlength="20" style="width:50px;"/>
                                                    </td>
                                                    <td class="center" style="width: 10%;">
                                                        <a class="prize_item_plus green" style="cursor:pointer;text-decoration:none;" onclick="addTr()">
                                                            <i class="ace-icon fa fa-plus bigger-130" title="增加一条记录"></i>
                                                        </a>
                                                        <a class="prize_item_minus red" style="cursor:pointer;text-decoration:none;" onclick="delTr(this)">
                                                            <i class="ace-icon fa fa-minus bigger-130" title="减少一条记录"></i>
                                                        </a>
                                                    </td>
                                                </tr>
                                            [/#if]
                                            </tbody>
                                        </table>
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
    var fileName = null;
    if ('${purchaseForm.document.name!''}' != "") {
        fileName = "${purchaseForm.document.name!''}";
    }

    $("#input-purchaseContractUrl").fileinput({
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
            $("#purchaseContractUrl").val(data.response.path);
            $("#fileName").val(data.response.fileName);
        }
    });

    $('#supplierId').chosen().change(function() {
        if (!confirm("变更供应商将清空采购明细, 确认变更供应商吗？")) {
            $(this).val($.data(this, 'current')); // added parenthesis (edit)
            $("#supplierId").trigger("chosen:updated");
            return false;
        }

        $.data(this, 'current', $(this).val());
        $("#supplierId").trigger("chosen:updated");

        changeItem($.data(this, 'current'));
    });

    function changeItem(supplierId) {
        $("#tbody").children().each(function(){
            if (!$(this).is(":first-child")) {
                $(this).remove();
            }
        });
//        /alert(supplierId);
        getSupplierItemList(supplierId, $(".supplierItem"));
    }


    var count = $("#tbody").find("tr").length;
    var addTr = function () {
        var supplierId = $("#supplierId").val();
        if (!checkSupplier(supplierId)) {
            alert("请先选择供应商");
            return;
        }

        var firstTr = $("#tbody").find("tr:first");
        var cloneTr = firstTr.clone();
        var select = cloneTr.find("select[name='purchaseItemList[0].supplierItemId']");
        select.attr("id", "supplierItemId" + count).attr("name", "purchaseItemList[" + count + "].supplierItemId").attr("data","");
        select.find("option").each(function(){
            $(this).attr('selected', false);
        });
        cloneTr.find("input[name='purchaseItemList[0].quantity']").val("");
        cloneTr.find("input[name='purchaseItemList[0].quantity']").attr("name", "purchaseItemList[" + count + "].quantity");
        cloneTr.find("input[name='purchaseItemList[0].unit']").val("");
        cloneTr.find("input[name='purchaseItemList[0].unit']").attr("name", "purchaseItemList[" + count + "].unit");
        cloneTr.find("input[name='purchaseItemList[0].unitPrice']").val("");
        cloneTr.find("input[name='purchaseItemList[0].unitPrice']").attr("name", "purchaseItemList[" + count + "].unitPrice");
        cloneTr.find("input[name='purchaseItemList[0].amount']").val("");
        cloneTr.find("input[name='purchaseItemList[0].amount']").attr("name", "purchaseItemList[" + count + "].amount");
        $("#tbody").append(cloneTr);
        count ++;

        //$(select).chosen();
        //getSupplierItemList(supplierId, $(select));
    };

    var delTr = function (obj) {
        if (!$(obj).parent().parent().is(":first-child")) {
            $(obj).parent().parent().remove();
        } else {
            addTr();
            $(obj).parent().parent().remove();
        }
    };

    function getSupplierItemList(supplierId, select, selectItem) {
        $(select).empty().append("<option value=''>请选择</option>");
        if (!checkSupplier(supplierId)) {
            alert("请先选择供应商");
            return;
        }
        $.ajax({
            type:"get",
            dataType:"json",
            contentType:"application/json;charset=utf-8",
            url:"${ctx}/purchase/selectSupplierItem/" + supplierId,
            success:function(result){
                $.each(result, function (index, value) {
                    if (selectItem != null && selectItem != '' && selectItem == value.id) {
                        $(select).append("<option value='" + value.id + "' selected>" + value.productName + "</option>");
                    }else{
                        $(select).append("<option value='" + value.id + "'>" + value.productName + "</option>");
                    }
                    //$(select).trigger("chosen:updated");
                });
            },
            error : function(XMLHttpRequest, textStatus, errorThrown) {
                alert(errorThrown);
            },
            async:false//false表示同步
        });
    }

    function checkSupplier(supplierId) {
        if (supplierId == '') {
            alert("请先选择供应商");
            return false;
        }
        return true;
    }

    function initSelect() {
        $("#tbody").find("tr").find("select").each(function() {
            var supplierId = $("#supplierId").val();
            if (checkSupplier(supplierId)) {
                var data = $(this).attr("data");
                getSupplierItemList(supplierId, $(this), data);
            }
        })
    }

    $(document).ready(function () {
        initSelect();
    });
</script>
</html>