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
                                    <a class="btn btn-xs btn-success" onclick="diag('创建追踪记录', '${ctx}/saleItem/create/${planNo}');">创建追踪记录</a>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                <table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">
                                    <thead>
                                        <tr>
                                            <th class="center">项目编码</th>
                                            <th class="center">记录人</th>
                                            <th class="center">追踪时间</th>
                                            <th class="center">描述</th>
                                            [#--<th class="center" colspan="2">操作</th>--]
                                        </tr>
                                    </thead>

                                    <tbody>
                                        [#if list?has_content]
                                            [#list list as planTrace]
                                                <tr>
                                                    <td class="center">${planTrace.planNo}</td>
                                                    <td class="center">${planTrace.realName}</td>
                                                    <td class="center">${planTrace.traceTime?string("yyyy-MM-dd HH:mm:ss")}</td>
                                                    <td class="center">${planTrace.description}</td>
                                                    [#--<td class="center">
                                                        <div class="hidden-sm hidden-xs btn-group">
                                                            <a class="green" onclick="diag('xiang', '${ctx}/saleItem/update/${planTrace.id}');" style="cursor: pointer;text-decoration:none;">
                                                                修改
                                                            </a>
                                                        </div>
                                                    </td>--]
                                                </tr>
                                            [/#list]
                                        [/#if]
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>

    [#include "${ctx}/common/footer.ftl"/]
</html>