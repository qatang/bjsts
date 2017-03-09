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
                            <div class="col-xs-11">
                                <form action="${ctx}/contract/list" method="post" name="contractForm" id="contractForm">
                                    <div class="container-fluid">
                                        <div class="row">
                                            <div class="col-xs-2"  style="padding:2px;border-top:0;">
                                                [@macro.inputText name="company" value=contractSearchable.company!'' placeholder="客户名称"  required=false /]
                                            </div>
                                            <div class="col-xs-3" style="padding:2px;border-top:0;">
                                            [@macro.datePicker name="signTime" value=contractSearchable.signTime!"" placeholder="签订日期" required=fasle/]
                                            </div>
                                            <div class="col-xs-2"  style="padding:2px;border-top:0;">
                                            [@macro.inputText name="sign" value=contractSearchable.sign!'' placeholder="签订人"  required=false /]
                                            </div>
                                            <div class="col-xs-1" style="padding:2px;border-top:0;"><button id="btn" class="btn btn-light btn-xs" title="检索" type="submit"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></button></div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div class="col-xs-1">
                                <div style="float:right;margin-top:5px;">
                                    <a class="btn btn-xs btn-success" onclick="diag('新增合同', '${ctx}/contract/create');">新增合同</a>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                [@macro.successMsg close=false /]
                                <table id="simple-table" class="table table-striped table-bordered table-hover table-fixed-head" style="margin-top:5px;">
                                    <thead>
                                        <tr>
                                            <th class="center">合同编号</th>
                                            <th class="center">项目编号</th>
                                            <th class="center">项目名称</th>
                                            <th class="center">客户名称</th>
                                            <th class="center">联系人</th>
                                            <th class="center">签订日期</th>
                                            <th class="center">签订人</th>
                                            <th class="center">合同金额</th>
                                            <th class="center">质保日期</th>
                                            <th class="center">质保金额</th>
                                            <th class="center">合同状态</th>
                                            <th class="center" colspan="2">操作</th>
                                        </tr>
                                    </thead>

                                    <tbody>
                                        [#if page?? && page.content?has_content]
                                            [#list page.content as contract]
                                                <tr>
                                                    <td class="center"><a onclick="diag('合同信息', '${ctx}/contract/view/${contract.id}');" style="cursor: pointer;text-decoration:none;">${contract.contractNo}</a></td>
                                                    <td class="center">
                                                        [#if contract.planNo??]
                                                            <a onclick="diag('项目信息', '${ctx}/productOrder/viewByPlanNo/${contract.planNo}');" style="cursor: pointer;text-decoration:none;">${contract.planNo}</a>
                                                        [/#if]
                                                    </td>
                                                    <td class="center">${contract.planName!''}</td>
                                                    <td class="center">${contract.company}</td>
                                                    <td class="center">${contract.linkman}</td>
                                                    <td class="center">[@macro.displayDate value=contract.signTime!""/]</td>
                                                    <td class="center">${contract.sign!""}</td>
                                                    <td class="center">[@macro.displayMoney value=contract.amount!''/]</td>
                                                    <td class="center">[@macro.displayDate value=contract.qualityTime!""/]</td>
                                                    <td class="center">[@macro.displayMoney value=contract.qualityAmount!''/]</td>
                                                    <td class="center">${contract.status.getName()}</td>
                                                    <td class="center">
                                                            <a class="green" onclick="diag('修改合同状态', '${ctx}/contract/updateStatus/${contract.id}');" style="cursor: pointer;text-decoration:none;">
                                                                状态调整
                                                            </a>
                                                            <a class="green" onclick="diag('修改合同', '${ctx}/contract/update/${contract.id}');" style="cursor: pointer;text-decoration:none;">
                                                                修改
                                                            </a>
                                                            <a href="${ctx}/contract/disable/${contract.id?c}" onclick="return confirm('确定要删除吗?');">删除</a>
                                                    </td>
                                                </tr>
                                            [/#list]
                                        [/#if]
                                    </tbody>
                                </table>

                                <div class="page-header position-relative">
                                    <table style="width:100%;">
                                        <tr>
                                            <td style="vertical-align:top;">
                                                <div style="float: right;padding-top: 0px;margin-top: 0px;">[@macro.pagination page=page/]</div>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>

    [#include "${ctx}/common/footer.ftl"/]
</html>