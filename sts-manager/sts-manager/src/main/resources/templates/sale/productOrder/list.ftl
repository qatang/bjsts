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
                                    <a class="btn btn-xs btn-success" onclick="diag('新增项目', '${ctx}/productOrder/create');">新增项目</a>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                <table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">
                                    <thead>
                                        <tr>
                                            <th class="center">项目编码</th>
                                            <th class="center">项目名称</th>
                                            <th class="center">询价类型</th>
                                            <th class="center">信息来源</th>
                                            <th class="center">询价日期</th>
                                            <th class="center">项目地点</th>
                                            <th class="center">联系人姓名</th>
                                            <th class="center">联系人电话</th>
                                            <th class="center">联系人单位</th>
                                            <th class="center">电子邮箱</th>
                                            <th class="center" colspan="2">操作</th>
                                        </tr>
                                    </thead>

                                    <tbody>
                                        [#if page?? && page.content?has_content]
                                            [#list page.content as plan]
                                                <tr>
                                                    <td class="center"><a onclick="diag('查看项目', '${ctx}/productOrder/view/${plan.id}');">${plan.planNo}</a></td>
                                                    <td class="center">${plan.name!''}</td>
                                                    <td class="center">${plan.planType.getName()}</td>
                                                    <td class="center">${plan.sourceType.getName()}</td>
                                                    <td class="center">${plan.priceTime?string("yyyy-MM-dd HH:mm:ss")}</td>
                                                    <td class="center">${plan.location}</td>
                                                    <td class="center">${plan.linkman}</td>
                                                    <td class="center">${plan.mobile}</td>
                                                    <td class="center">${plan.company}</td>
                                                    <td class="center">${plan.email}</td>
                                                    <td class="center">
                                                        <div class="hidden-sm hidden-xs btn-group">
                                                            <a class="green" onclick="diag('项目修改', '${ctx}/productOrder/update/${plan.id}');" style="cursor: pointer;text-decoration:none;">
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