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
                        [@macro.errorMsg/]
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
                                    <a class="btn btn-xs btn-success" onclick="customDiag('新增日常采购单', '${ctx}/normalPurchase/create', 800, 600);">新增日常采购单</a>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                <table id="simple-table" class="table table-striped table-bordered table-hover table-fixed-head" style="margin-top:5px;">
                                    <thead>
                                        <tr>
                                            <th class="center">编号</th>
                                            <th class="center">物品名称</th>
                                            <th class="center">购买人</th>
                                            <th class="center">数量</th>
                                            <th class="center">金额</th>
                                            <th class="center">用途</th>
                                            <th class="center">购货单位名称</th>
                                            <th class="center">采购日期</th>
                                            <th class="center">操作</th>
                                        </tr>
                                    </thead>

                                    <tbody>
                                        [#if page?? && page.content?has_content]
                                            [#list page.content as normalPurchase]
                                                <tr>
                                                    <td class="center"><a onclick="diag('日常采购单查看', '${ctx}/normalPurchase/view/${normalPurchase.id}');">${normalPurchase.purchaseNo}</a></td>
                                                    <td class="center">${normalPurchase.name!""}</td>
                                                    <td class="center">${normalPurchase.buyer!""}</td>
                                                    <td class="center">${normalPurchase.quantity!""}</td>
                                                    <td class="center">[@macro.displayMoney value=normalPurchase.amount!""/]</td>
                                                    <td class="center">${normalPurchase.description}</td>
                                                    <td class="center">${normalPurchase.company}</td>
                                                    <td class="center">[@macro.displayDate value=normalPurchase.purchaseTime!""/]</td>
                                                    <td class="center">
                                                            <a class="green" onclick="diag('修改日常采购单', '${ctx}/normalPurchase/update/${normalPurchase.id?c}');" style="cursor: pointer;text-decoration:none;">
                                                                修改
                                                            </a>
                                                            <a href="${ctx}/normalPurchase/disable/${normalPurchase.id?c}" onclick="return confirm('确定要删除吗?');">删除</a>
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