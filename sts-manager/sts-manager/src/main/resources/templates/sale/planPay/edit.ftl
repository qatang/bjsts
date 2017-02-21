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
                        <form action="${ctx}/planPay/${action}" name="planPayForm" id="planPayForm" method="post">
                            <div style="padding-top: 13px;"></div>
                        [@macro.errors /]
                            <input type="hidden" name="planPay.id" value="${planPayForm.planPay.id!''}"/>
                            <table id="table_report" class="table table-striped table-bordered table-hover">
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">项目编号:</td>
                                    <td>
                                        [#if planPayForm.planPay.id??]
                                            ${planPayForm.planPay.planNo}
                                            <input type="hidden" id="planNo" value="${planPayForm.planPay.planNo!''}"/>
                                        [#else]
                                            <select class="chosen-select form-control" name="planPay.planNo" data-placeholder="请选择" style="" onchange="queryPlan(this);" id="planNo">
                                                <option value="0">请选择</option>
                                                [#list planList as data]
                                                    <option value="${data.planNo!""}" [#if planPayForm.planPay.planNo?has_content && data.planNo == planPayForm.planPay.planNo]selected[/#if]>${data.getName()!""}</option>
                                                [/#list]
                                            </select>
                                        [/#if]
                                        </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">项目名称:</td>
                                    <td>
                                        <input type="text" id="planName" value="${planPayForm.planPay.planName!''}" readonly required/>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">合同编号:</td>
                                    <td>
                                        <input type="text" name="planPay.contractNo" id="contractNo" value="${planPayForm.planPay.contractNo!''}" readonly required/>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">客户名称:</td>
                                    <td>
                                        <input type="text" name="planPay.company" id="company" value="${planPayForm.planPay.company!''}" readonly required/>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">合同总额:</td>
                                    <td>
                                        <input type="text" name="planPay.contractAmount" id="contractAmount" value="${planPayForm.planPay.contractAmount!''}" readonly required/>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">开票信息:</td>
                                    <td>
                                        [@macro.selectEnum name="planPay.makeOutInvoiceStatus" enumObj=planPayForm.planPay.makeOutInvoiceStatus!invoiceStatusList[0] dataList=invoiceStatusList /]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">已付金额:</td>
                                    <td>
                                        <input type="text" id="payedAmount" value="${planPayForm.planPay.payedAmount!''}" readonly required/>
                                    </td>
                                </tr>
[#--                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">质保金额:</td>
                                    <td>
                                    --][#-- [@macro.inputText name="planPay.name" value=planPayForm.planPay.name!'' placeholder="项目名称"/]--][#--
                                    </td>
                                </tr>--]
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">本次付款金额:</td>
                                    <td>
                                        [@macro.inputMoney name="amount" value=planPayForm.amount!'' placeholder="本次付款金额"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">本次付款方式:</td>
                                    <td>
                                    [@macro.inputText name="planPay.payModel" value=planPayForm.planPay.payModel!'' placeholder="本次付款方式"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">本次付款日期:</td>
                                    <td>
                                        [@macro.datetimePicker name="planPay.payTime" value=planPayForm.planPay.payTime placeholder="本次付款日期"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">经办人:</td>
                                    <td>
                                    [@macro.inputText name="planPay.operator" value=planPayForm.planPay.operator!'' placeholder="经办人"/]
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

<script>
    /**
     *
     * @param url
     */
    function queryPlan() {
        planNo = $('#planNo').val();

        if (planNo != null && planNo != "" && planNo != "0") {
            $.ajax({
                type: "get",
                url: "${ctx}/planPay/findPlan/" + planNo,
                data: {},
                dataType: "json",
                async: false,
                success: function (data) {
                    if (data.code != "0") {
                        alert(data.message);
                        $('#planName').val("");
                        $('#contractNo').val("");
                        $('#company').val("");
                        $('#contractAmount').val("");
                        $('#payedAmount').val("");
                    } else {
                        $('#planName').val(data.planName);
                        $('#contractNo').val(data.contractNo);
                        $('#company').val(data.company);
                        $('#contractAmount').val(data.contractAmount);
                        $('#payedAmount').val(data.payedAmount/100);
                    }
                },
                error: function (xmlHttpRequest,error) {
                },
                complete: function(xmlHttpRequest) {
                }
            });
        }
    }

    queryPlan();
</script>
</html>