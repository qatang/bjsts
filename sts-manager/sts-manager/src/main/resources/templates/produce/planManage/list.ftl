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
                                    <a class="btn btn-xs btn-success" onclick="diag('新增项目管理', '${ctx}/planManage/create');">新增项目管理</a>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                <table id="simple-table" class="table table-striped table-bordered table-hover table-fixed-head" style="margin-top:5px;">
                                    <thead>
                                        <tr>
                                            <th class="center">ID</th>
                                            <th class="center">项目编号</th>
                                            <th class="center">预计交付日期</th>
                                            <th class="center">项目执行情况</th>
                                            <th class="center">实际完成日期</th>
                                            <th class="center">操作</th>
                                        </tr>
                                    </thead>

                                    <tbody>
                                        [#if page?? && page.content?has_content]
                                            [#list page.content as planManage]
                                            <tr>
                                                <td class="center"><a onclick="diag('项目管理查看', '${ctx}/planManage/view/${planManage.id}')" style="cursor:pointer;">${planManage.id}</a></td>
                                                <td class="center">${planManage.planNo!""}</td>
                                                <td class="center">[@macro.displayDate value=planManage.expectDate!""/]</td>
                                                <td class="center">${planManage.planExecuteStatus.getName()}</td>
                                                <td class="center">[@macro.displayDate value=planManage.actualDate!""/]</td>
                                                <td class="center">
                                                        <a class="green" onclick="diag('项目管理修改', '${ctx}/planManage/update/${planManage.id}');" style="cursor: pointer;text-decoration:none;">
                                                            修改
                                                        </a>
                                                        <a class="red" href="${ctx}/planManage/disable/${planManage.id}" onclick="return confirm('确定要删除该项目管理吗?');">
                                                            删除
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