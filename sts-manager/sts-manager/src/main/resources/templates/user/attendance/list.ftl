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
                [@macro.errors /]
                <div class="row">
                    <div class="col-xs-11">
                        <form action="${ctx}/attendance/list" method="post" name="staffForm" id="staffForm">
                            <table style="margin-top:5px;">
                                <tr>
                                    <td>
                                        <div class="nav-search">
                                                    <span class="input-icon">
                                                        <input class="nav-search-input" autocomplete="off" id="nav-search-input" type="text" name="realName" value="${staffSearchable.realName!''}" placeholder="这里输入姓名" />
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
                            [#--<a class="btn btn-xs btn-success" onclick="diag('员工添加', '${ctx}/staff/create');">新增</a>--]
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-12">
                        <div class="table-responsive">
                            <table id="simple-table" class="table table-striped table-bordered table-hover table-fixed-head"  style="margin-top:5px;">
                                <thead>
                                <tr>
                                    <th class="center">职工编号</th>
                                    <th class="center">姓名</th>
                                    <th class="center">性别</th>
                                    <th class="center">部门</th>
                                    <th class="center">职位</th>
                                    <th class="center">操作</th>
                                </tr>
                                </thead>

                                <tbody>
                                [#if page?? && page.content?has_content]
                                    [#list page.content as staff]
                                    <tr>
                                        <td class='center'>${staff.staffNo}</td>
                                        <td class="center">${staff.realName}</td>
                                        <td class="center">${staff.maleType.getName()}</td>
                                        <td class='center'>${staff.departmentName}</td>
                                        <td class="center">${staff.position!''}</td>
                                        <td class="center">
                                            <a class="green" href="${ctx}/attendance/create/${staff.id}">考勤</a>
                                        </td>
                                    </tr>
                                    [/#list]
                                [/#if]
                                </tbody>
                            </table>
                        </div>

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