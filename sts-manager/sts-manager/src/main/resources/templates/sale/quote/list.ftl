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
                                <form action="${ctx}/quote/list" method="post" name="quoteForm" id="quoteForm">
                                    <div class="container-fluid">
                                        <div class="row">
                                            <div class="col-xs-3" style="padding:2px;border-top:0;">
                                            [@macro.dateRange fromName="beginPriceTime" toName="endPriceTime" fromValue=quoteSearchable.beginPriceTime!'' toValue=quoteSearchable.endPriceTime!'' readonly=false placeholder="签订日期"/]
                                            </div>
                                            <div class="col-xs-2" style="padding:2px;border-top:0;">
                                            [@macro.inputText name="booker" value=quoteSearchable.booker!'' placeholder="备案登记人" required=false /]
                                            </div>
                                            <div class="col-xs-2" style="padding:2px;border-top:0;">
                                            [@macro.inputText name="linkman" value=quoteSearchable.linkman!'' placeholder="联系人" required=false /]
                                            </div>
                                            <div class="col-xs-1" style="padding:2px;border-top:0;"><button id="btn" class="btn btn-light btn-xs" title="检索" type="submit"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></button></div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                <table id="simple-table" class="table table-striped table-bordered table-hover table-fixed-head" style="margin-top:5px;">
                                    <thead>
                                        <tr>
                                            <th class="center">项目编码</th>
                                            <th class="center">项目名称</th>
                                            <th class="center">询价日期</th>
                                            <th class="center">联系人姓名</th>
                                            <th class="center">联系人电话</th>
                                            <th class="center">联系人单位</th>
                                            <th class="center">电子邮箱</th>
                                            <th class="center">项目状态</th>
                                            <th class="center" colspan="2">操作</th>
                                        </tr>
                                    </thead>

                                    <tbody>
                                        [#if page?? && page.content?has_content]
                                            [#list page.content as plan]
                                                <tr>
                                                    <td class="center"><a onclick="diag('查看项目', '${ctx}/quote/view/${plan.id}');">${plan.planNo}</a></td>
                                                    <td class="center">${plan.name!''}</td>
                                                    <td class="center">[@macro.displayDate value=plan.priceTime!""/]</td>
                                                    <td class="center">${plan.linkman}</td>
                                                    <td class="center">${plan.mobile}</td>
                                                    <td class="center">${plan.company}</td>
                                                    <td class="center">${plan.email}</td>
                                                    <td class="center">${plan.planStatus.getName()}</td>
                                                    <td class="center">
                                                            <a class="green" onclick="diag('报价', '${ctx}/quote/create/${plan.id}');" style="cursor: pointer;text-decoration:none;">
                                                                报价
                                                            </a>
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