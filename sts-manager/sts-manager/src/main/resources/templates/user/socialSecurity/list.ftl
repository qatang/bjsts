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
                                <form action="${ctx}/socialSecurity/list" method="post" name="socialSecurityForm" id="socialSecurityForm">
                                    <table style="margin-top:5px;">
                                        <tr>
                                            <td>
                                                <div class="nav-search">
                                                    <span class="input-icon">
                                                        <input class="nav-search-input" autocomplete="off" id="nav-search-input" type="text" name="realName" value="${socialSecuritySearchable.realName!''}" placeholder="这里输入姓名" />
                                                        <i class="ace-icon fa fa-search nav-search-icon"></i>
                                                    </span>
                                                </div>
                                            </td>
                                            <td style="vertical-align:top;padding-left:2px;"><button class="btn btn-light btn-xs" title="检索" type="submit"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></button></td>
                                        </tr>
                                    </table>
                                </form>
                            </div>
                            <div class="col-xs-1">
                                <div style="float:right;margin-top:5px;">
                                    <a class="btn btn-xs btn-success" onclick="diag('社保录入', '${ctx}/socialSecurity/create');">社保录入</a>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                <table id="simple-table" class="table table-striped table-bordered table-hover table-fixed-head"  style="margin-top:5px;">
                                    <thead>
                                    <tr>
                                        <th class="center">序号</th>
                                        <th class="center">姓名</th>
                                        <th class="center">身份证号</th>
                                        <th class="center">联系电话</th>
                                        <th class="center">操作</th>
                                    </tr>
                                    </thead>

                                    <tbody>
                                    [#if page?? && page.content?has_content]
                                        [#list page.content as socialSecurity]
                                        <tr>
                                            <td class="center">${socialSecurity.id}</td>
                                            <td class="center">${socialSecurity.realName!""}</td>
                                            <td class="center">${socialSecurity.idCard!""}</td>
                                            <td class="center">${socialSecurity.mobile!""}</td>
                                            <td class="center">
                                                    <a class="green" onclick="diag('社保修改', '${ctx}/socialSecurity/update/${socialSecurity.id}', '${page.number}');" style="cursor: pointer;text-decoration:none;">
                                                        编辑
                                                    </a>
                                                    <a href="${ctx}/socialSecurity/disable/${socialSecurity.id?c}" onclick="return confirm('确定要删除吗?');">删除</a>
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
    [#include "${ctx}/common/list.ftl"/]
</html>