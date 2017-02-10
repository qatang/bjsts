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
                                [#--<form action="${ctx}/staff/list" method="post" name="staffForm" id="staffForm">
                                    <table style="margin-top:5px;">
                                        <tr>
                                            <td>
                                                <div class="nav-search">
                                                    <span class="input-icon">
                                                        <input class="nav-search-input" autocomplete="off" id="nav-search-input" type="text" name="content" value="${staffSearchable.content!''}" placeholder="这里输入关键词" />
                                                        <i class="ace-icon fa fa-search nav-search-icon"></i>
                                                    </span>
                                                </div>
                                            </td>
                                            <td style="padding-left:2px;">[@macro.dateRange fromName="beginCreatedTime" toName="endCreatedTime" fromValue=staffSearchable.beginCreatedTime!'' toValue=staffSearchable.endCreatedTime!'' placeholder="创建日期"/]</td>
                                            <td style="vertical-align:top;padding-left:2px;">
                                                [@macro.selectEnum name="valid" enumObj=staffSearchable.valid!allEnableDisableStatusList[0] dataList=allEnableDisableStatusList style="vertical-align:top;width: 120px;"/]
                                            </td>
                                            <td style="vertical-align:top;padding-left:2px;"><button class="btn btn-light btn-xs" title="检索" type="submit"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></button></td>
                                        </tr>
                                    </table>
                                </form>--]
                            </div>
                            <div class="col-xs-1">
                                <div style="float:right;margin-top:5px;">
                                    <a class="btn btn-xs btn-success" onclick="diag('员工添加', '${ctx}/staff/create');">新增</a>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                <div class="table-responsive">
                                    <table id="simple-table" class="table table-striped table-bordered table-hover"  style="margin-top:5px;">
                                        <thead>
                                        <tr>
                                            <th class="center">部门</th>
                                            <th class="center">职工编码</th>
                                            <th class="center">姓名</th>
                                            <th class="center">性别</th>
                                            <th class="center">职位</th>
                                            <th class="center">身份证号</th>
                                            <th class="center">入职时间</th>
                                            <th class="center">离职时间</th>
                                            <th class="center">文化程度</th>
                                            <th class="center">政治面貌</th>
                                            <th class="center">生日</th>
                                            <th class="center">在离职</th>
                                            <th class="center">联系电话</th>
                                            <th class="center"><i class="ace-icon fa fa-envelope-o"></i>邮箱</th>
                                            <th class="center">操作</th>
                                        </tr>
                                        </thead>

                                        <tbody>
                                        [#if page?? && page.content?has_content]
                                            [#list page.content as staff]
                                            <tr>
                                                <td class='center'>${staff.departmentName}</td>
                                                <td class='center'>${staff.id}</td>
                                                <td class="center"><a onclick="diag('员工信息查看', '${ctx}/staff/view/${staff.id}')" style="cursor:pointer;">${staff.realName}</a></td>
                                                <td class="center">${staff.maleType.getName()}</td>
                                                <td class="center">${staff.position!''}</td>
                                                <td class="center">${staff.idCard!''}</td>
                                                <td class="center">[@macro.displayDate value=staff.entryTime!""/]</td>
                                                <td class="center">[@macro.displayDate value=staff.departureTime!""/]</td>
                                                <td class="center">${staff.educationType.getName()}</td>
                                                <td class="center">${staff.polityType.getName()}</td>
                                                <td class="center">[@macro.displayDate value=staff.birthday!""/]</td>
                                                <td class="center">${staff.onJob.getName()}</td>
                                                <td class="center">${staff.mobile}</td>
                                                <td class="center">${staff.email}</td>
                                                <td class="center">
                                                    <div class="hidden-sm hidden-xs btn-group">
                                                        <a class="green" onclick="diag('员工信息修改', '${ctx}/staff/update/${staff.id}', '${page.number}');" style="cursor: pointer;text-decoration:none;">
                                                            编辑
                                                        </a>
                                                    </div>
                                                    <div class="hidden-sm hidden-xs btn-group">
                                                        <a href="${ctx}/staff/disable/${staff.id?c}" onclick="return confirm('确定要删除吗?');">删除</a>
                                                    </div>
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