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
                            [#--<form action="${ctx}/socialSecurity/list" method="post" name="socialSecurityForm" id="socialSecurityForm">
                                    <table style="margin-top:5px;">
                                        <tr>
                                            <td>
                                                <div class="nav-search">
                                                    <span class="input-icon">
                                                        <input class="nav-search-input" autocomplete="off" id="nav-search-input" type="text" name="content" value="${socialSecuritySearchable.content!''}" placeholder="这里输入关键词" />
                                                        <i class="ace-icon fa fa-search nav-search-icon"></i>
                                                    </span>
                                                </div>
                                            </td>
                                            <td style="vertical-align:top;padding-left:2px;"><button class="btn btn-light btn-xs" title="检索" type="submit"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></button></td>
                                        </tr>
                                    </table>
                                </form>--]
                            </div>
                            <div class="col-xs-1">
                                <div style="float:right;margin-top:5px;">
                                    <a class="btn btn-xs btn-success" onclick="diag('新增项目付款', '${ctx}/planPay/create');">新增项目付款</a>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                <table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">
                                    <thead>
                                        <tr>
                                            <th class="center">付款编码</th>
                                            <th class="center">项目编码</th>
                                            <th class="center">项目名称</th>
                                            <th class="center">合同编码</th>
                                            <th class="center">客户名称</th>
                                            <th class="center">合同总额</th>
                                            <th class="center">开票信息</th>
                                            <th class="center">已付金额</th>
                                            [#--<th class="center">质保金额</th>--]
                                            <th class="center">本次付款信息</th>
                                            <th class="center">本次付款方式</th>
                                            <th class="center">本次付款日期</th>
                                            <th class="center">经办人</th>
                                            <th class="center" colspan="2">操作</th>
                                        </tr>
                                    </thead>

                                    <tbody>
                                        [#if page?? && page.content?has_content]
                                            [#list page.content as planPay]
                                                <tr>
                                                    <td class="center"><a onclick="diag('查看项目付款', '${ctx}/planPay/view/${planPay.id}');">${planPay.id}</a></td>
                                                    <td class="center">${planPay.planNo}</td>
                                                    <td class="center">${planPay.planName!""}</td>
                                                    <td class="center">${planPay.contractNo}</td>
                                                    <td class="center">${planPay.company!""}</td>
                                                    <td class="center">[@macro.displayMoney value=planPay.contractAmount!''/]</td>
                                                    <td class="center">${planPay.invoiceStatus.getName()}</td>
                                                    <td class="center">[@macro.displayMoney value=planPay.payedAmount!''/]</td>
                                                    [#--<td class="center"></td>--]
                                                    <td class="center">[@macro.displayMoney value=planPay.amount!''/]</td>
                                                    <td class="center">${planPay.payModel}</td>
                                                    <td class="center">${planPay.payTime?string("yyyy-MM-dd HH:mm:ss")}</td>
                                                    <td class="center">${planPay.operator}</td>
                                                    <td class="center">
                                                        <div class="hidden-sm hidden-xs btn-group">
                                                            <a class="green" onclick="diag('项目修改', '${ctx}/planPay/update/${planPay.id}');" style="cursor: pointer;text-decoration:none;">
                                                                编辑
                                                            </a>
                                                        </div>
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