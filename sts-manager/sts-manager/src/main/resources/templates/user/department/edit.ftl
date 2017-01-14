[#ftl strip_whitespace=true]
[#import "${ctx}/common/macroes.ftl" as macro]
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css"  href="${ctx}/static/ace/css/chosen.css" />
        [#include "${ctx}/common/head.ftl"/]
    </head>
    <body class="no-skin">
        <div class="main-container" id="main-container">
            <div class="main-content">
                <div class="main-content-inner">
                    <div class="page-content">
                        <div class="row">
                            <div class="col-xs-12">
                                <form action="${ctx}/department/${action}" name="departmentForm" id="departmentForm" method="post">
                                    <div style="padding-top: 13px;"></div>
                                    <input type="hidden" name="department.id" value="${departmentForm.department.id!''}"/>
                                    <table id="table_report" class="table table-striped table-bordered table-hover">
                                        <tr>
                                            <td style="width:79px;text-align: right;padding-top: 13px;">部门名称:</td>
                                            <td><input type="text" name="department.name" id="name" value="${departmentForm.department.name!''}" maxlength="64" placeholder="这里输入部门名称" style="width:98%;"/></td>
                                        </tr>
                                        <tr>
                                            <td style="text-align: center;" colspan="10">
                                                <a class="btn btn-mini btn-primary" onclick="save();">保存</a>
                                                <a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
                                            </td>
                                        </tr>
                                    </table>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>

    [#include "${ctx}/common/footer.ftl"/]

    <script>
        function save(){
            $("#departmentForm").submit();
        }
    </script>
</html>