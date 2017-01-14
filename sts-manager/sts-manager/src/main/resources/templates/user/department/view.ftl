[#ftl strip_whitespace=true]
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
                                <div style="padding-top: 13px;"></div>
                                <table id="table_report" class="table table-striped table-bordered table-hover">
                                    <tr>
                                        <td style="width:79px;text-align: right;padding-top: 13px;">部门编号:</td>
                                        <td style="padding-top: 13px;">${department.id}</td>
                                    </tr>
                                    <tr>
                                        <td style="width:79px;text-align: right;padding-top: 13px;">部门名称:</td>
                                        <td style="padding-top: 13px;">${department.username}</td>
                                    </tr>
                                    <tr>
                                        <td style="width:79px;text-align: right;padding-top: 13px;">创建时间:</td>
                                        <td style="padding-top: 13px;">${department.createdTime?string("yyyy-MM-dd HH:mm:ss")}</td>
                                    </tr>
                                    <tr>
                                        <td style="width:79px;text-align: right;padding-top: 13px;">修改时间:</td>
                                        <td style="padding-top: 13px;">${department.updatedTime?string("yyyy-MM-dd HH:mm:ss")}</td>
                                    </tr>
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