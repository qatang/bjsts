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
                                <form action="${ctx}/attendance/list" method="post" name="attendanceForm" id="attendanceForm">
                                    <table style="margin-top:5px;">
                                        <tr>
                                            <td>
                                                <div class="nav-search">
                                                    <span class="input-icon">
                                                        <input class="nav-search-input" autocomplete="off" id="nav-search-input" type="text" name="content" value="${attendanceSearchable.content!''}" placeholder="这里输入关键词" />
                                                        <i class="ace-icon fa fa-search nav-search-icon"></i>
                                                    </span>
                                                </div>
                                            </td>
                                            <td style="padding-left:2px;">[@macro.dateRange fromName="beginCreatedTime" toName="endCreatedTime" fromValue=attendanceSearchable.beginCreatedTime!'' toValue=attendanceSearchable.endCreatedTime!'' placeholder="创建日期"/]</td>
                                            <td style="vertical-align:top;padding-left:2px;"><button class="btn btn-light btn-xs" title="检索" type="submit"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></button></td>
                                        </tr>
                                    </table>
                                </form>
                            </div>
                            <div class="col-xs-1">
                                <div style="float:right;margin-top:5px;">
                                    <a class="btn btn-xs btn-success" onclick="diag('考勤录入', '${ctx}/attendance/create');">考勤录入</a>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                <table id="simple-table" class="table table-striped table-bordered table-hover"  style="margin-top:5px;">
                                    <thead>
                                    <tr>
                                        <th class="center">姓名</th>
                                        <th class="center"><i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>上班时间</th>
                                        <th class="center"><i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>下班时间</th>
                                        <th class="center">操作</th>
                                    </tr>
                                    </thead>

                                    <tbody>
                                    [#if page?? && page.content?has_content]
                                        [#list page.content as attendance]
                                        <tr>
                                            <td class="center">${attendance.realName!""}</td>
                                            <td class="center">${attendance.startTime?string("yyyy-MM-dd HH:mm:ss")}</td>
                                            <td class="center">${attendance.endTime?string("yyyy-MM-dd HH:mm:ss")}</td>
                                            <td class="center">
                                                <div class="hidden-sm hidden-xs btn-group">
                                                    <a class="green" onclick="diag('考勤修改', '${ctx}/attendance/update/${attendance.id}', '${page.number}');" style="cursor: pointer;text-decoration:none;">
                                                        <i class="ace-icon fa fa-pencil bigger-130" title="编辑"></i>
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
    [#include "${ctx}/common/list.ftl"/]
</html>