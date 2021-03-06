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
                                    <a class="btn btn-xs btn-success" onclick="customDiag('新增快递单', '${ctx}/express/create', 800, 600);">新增快递单</a>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                <table id="simple-table" class="table table-striped table-bordered table-hover table-fixed-head" style="margin-top:5px;">
                                    <thead>
                                        <tr>
                                            <th class="center">编号</th>
                                            <th class="center">快递单号</th>
                                            <th class="center">发货人</th>
                                            <th class="center">付款人</th>
                                            <th class="center">快递费</th>
                                            <th class="center">发货内容</th>
                                            <th class="center">收货人姓名</th>
                                            <th class="center">收货人电话</th>
                                            <th class="center">收货人地址</th>
                                            <th class="center">快递单位名称</th>
                                            <th class="center">投递日期</th>
                                            <th class="center">接收日期</th>
                                            <th class="center">操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        [#if page?? && page.content?has_content]
                                            [#list page.content as express]
                                                <tr>
                                                    <td class="center"><a onclick="diag('快递单查看', '${ctx}/express/view/${express.id}');">${express.id}</a></td>
                                                    <td class="center">${express.expressNo!""}</td>
                                                    <td class="center">${express.shipper!""}</td>
                                                    <td class="center">${express.payer!""}</td>
                                                    <td class="center">[@macro.displayMoney value=express.cost!""/]</td>
                                                    <td class="center">${express.content!""}</td>
                                                    <td class="center">${express.receiver!""}</td>
                                                    <td class="center">${express.mobile!""}</td>
                                                    <td class="center">${express.address!""}</td>
                                                    <td class="center">${express.company!""}</td>
                                                    <td class="center">[@macro.displayDate value=express.deliverDate!""/]</td>
                                                    <td class="center">[@macro.displayDate value=express.receiveDate!""/]</td>
                                                    <td class="center">
                                                            <a class="green" onclick="diag('修改快递单', '${ctx}/express/update/${express.id?c}');" style="cursor: pointer;text-decoration:none;">
                                                                修改
                                                            </a>
                                                            <a href="${ctx}/express/disable/${express.id?c}" onclick="return confirm('确定要删除吗?');">删除</a>
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