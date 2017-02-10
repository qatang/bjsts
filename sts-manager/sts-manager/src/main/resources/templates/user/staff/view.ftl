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
                                        <td style="width:79px;text-align: right;padding-top: 13px;">职工编号:</td>
                                        <td style="padding-top: 13px;">${staff.id}</td>
                                    </tr>
                                    <tr>
                                        <td style="width:79px;text-align: right;padding-top: 13px;">部门:</td>
                                        <td style="padding-top: 13px;">${staff.departmentName}</td>
                                    </tr>
                                    <tr>
                                        <td style="width:79px;text-align: right;padding-top: 13px;">姓名:</td>
                                        <td style="padding-top: 13px;">${staff.realName}</td>
                                    </tr>
                                    <tr>
                                        <td style="width:79px;text-align: right;padding-top: 13px;">性别:</td>
                                        <td style="padding-top: 13px;">${staff.maleType.getName()}</td>
                                    </tr>
                                    <tr>
                                        <td style="width:79px;text-align: right;padding-top: 13px;">职位:</td>
                                        <td style="padding-top: 13px;">${staff.position!""}</td>
                                    </tr>
                                    <tr>
                                        <td style="width:79px;text-align: right;padding-top: 13px;">身份证号:</td>
                                        <td style="padding-top: 13px;">${staff.idCard!""}</td>
                                    </tr>
                                    <tr>
                                        <td style="width:79px;text-align: right;padding-top: 13px;">入职时间:</td>
                                        <td style="padding-top: 13px;">[@macro.displayDate value=staff.entryTime/]</td>
                                    </tr>
                                    <tr>
                                        <td style="width:79px;text-align: right;padding-top: 13px;">离职时间:</td>
                                        <td style="padding-top: 13px;">[@macro.displayDate value=staff.departureTime/]</td>
                                    </tr>
                                    <tr>
                                        <td style="width:79px;text-align: right;padding-top: 13px;">文化程度:</td>
                                        <td style="padding-top: 13px;">${staff.educationType.getName()}</td>
                                    </tr>
                                    <tr>
                                        <td style="width:79px;text-align: right;padding-top: 13px;">政治面貌:</td>
                                        <td style="padding-top: 13px;">${staff.polityType.getName()}</td>
                                    </tr>
                                    <tr>
                                        <td style="width:79px;text-align: right;padding-top: 13px;">生日:</td>
                                        <td style="padding-top: 13px;">[@macro.displayDate value=staff.birthday/]</td>
                                    </tr>
                                    <tr>
                                        <td style="width:79px;text-align: right;padding-top: 13px;">在离职:</td>
                                        <td style="padding-top: 13px;">${staff.onJob.getName()}</td>
                                    </tr>
                                    <tr>
                                        <td style="width:79px;text-align: right;padding-top: 13px;">联系电话:</td>
                                        <td style="padding-top: 13px;">${staff.mobile!""}</td>
                                    </tr>
                                    <tr>
                                        <td style="width:79px;text-align: right;padding-top: 13px;">邮箱:</td>
                                        <td style="padding-top: 13px;">${staff.email!""}</td>
                                    </tr>
                                    <tr>
                                        <td style="width:79px;text-align: right;padding-top: 13px;">备注:</td>
                                        <td style="padding-top: 13px;">${staff.memo!""}</td>
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