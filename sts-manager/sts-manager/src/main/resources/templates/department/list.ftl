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
                                <div style="float:right;margin-top:5px;">
                                    <a class="btn btn-xs btn-success" onclick="diag('部门添加', '${ctx}/department/create');">新增</a>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-xs-12">
                                <table id="simple-table" class="table table-striped table-bordered table-hover"  style="margin-top:5px;">
                                    <thead>
                                    <tr>
                                        <th class="center">部门编号</th>
                                        <th class="center">部门名称</th>
                                        <th class="center">创建时间</th>
                                        <th class="center">操作</th>
                                    </tr>
                                    </thead>

                                    <tbody>
                                        [#list list as department]
                                        <tr>
                                            <td class='center'>${department.id}</td>
                                            <td class="center"><a onclick="diag('部门查看', '${ctx}/department/view/${department.id}')" style="cursor:pointer;">${department.name}</a></td>
                                            <td class="center">${department.createdTime?string("yyyy-MM-dd HH:mm:ss")}</td>
                                            <td class="center">
                                                <div class="hidden-sm hidden-xs btn-group">
                                                    <a class="green" onclick="diag('部门修改', '${ctx}/department/update/${department.id}');" style="cursor: pointer;text-decoration:none;">
                                                        <i class="ace-icon fa fa-pencil bigger-130" title="编辑"></i>
                                                    </a>
                                                </div>
                                            </td>
                                        </tr>
                                        [/#list]
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
    [#include "${ctx}/common/list.ftl"/]
</html>