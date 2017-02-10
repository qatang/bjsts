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
                        <form action="${ctx}/staff/${action}" name="staffForm" id="staffForm" method="post">
                            <div style="padding-top: 13px;"></div>
                            [@macro.errors /]
                            <input type="hidden" name="staff.id" value="${staffForm.staff.id!''}"/>
                            <table id="table_report" class="table table-striped table-bordered table-hover">
                                <tr>
                                    <td style="width:79px;text-align: right;padding-top: 13px;">部门:</td>
                                    <td>
                                    [@macro.select name="staff.departmentId" value=staffForm.staff.departmentId dataList=departmentList /]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:79px;text-align: right;padding-top: 13px;">姓名:</td>
                                    <td><input type="text" name="staff.realName" id="name" value="${staffForm.staff.realName!''}" maxlength="32" placeholder="这里输入姓名" required="" style="width:98%;"/></td>
                                </tr>
                                <tr>
                                    <td style="width:79px;text-align: right;padding-top: 13px;">性别:</td>
                                    <td>
                                    [@macro.selectEnum name="staff.maleType" enumObj=staffForm.staff.maleType!maleTypeList[0] dataList=maleTypeList /]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:79px;text-align: right;padding-top: 13px;">职位:</td>
                                    <td><input type="text" name="staff.position" id="position"  value="${staffForm.staff.position!''}" maxlength="32" placeholder="这里输入职位" style="width:98%;"/></td>
                                </tr>
                                <tr>
                                    <td style="width:79px;text-align: right;padding-top: 13px;">身份证号:</td>
                                    <td><input type="text" name="staff.idCard" id="idCard"  value="${staffForm.staff.idCard!''}" maxlength="32" placeholder="这里输入身份证号" style="width:98%;"/></td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">入职日期:</td>
                                    <td>
                                    [@macro.datetimePicker name="staff.entryTime" value=staffForm.staff.entryTime placeholder="入职日期"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">离职日期:</td>
                                    <td>
                                    [@macro.datetimePicker name="staff.departureTime" value=staffForm.staff.departureTime placeholder="离职日期"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:79px;text-align: right;padding-top: 13px;">教育:</td>
                                    <td>
                                    [@macro.selectEnum name="staff.educationType" enumObj=staffForm.staff.educationType!educationTypeList[0] dataList=educationTypeList /]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:79px;text-align: right;padding-top: 13px;">政治面貌:</td>
                                    <td>
                                    [@macro.selectEnum name="staff.polityType" enumObj=staffForm.staff.polityType!polityTypeList[0] dataList=polityTypeList /]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">生日:</td>
                                    <td>
                                    [@macro.datetimePicker name="staff.birthday" value=staffForm.staff.birthday placeholder="生日"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:79px;text-align: right;padding-top: 13px;">在离职:</td>
                                    <td>
                                    [@macro.selectEnum name="staff.onJob" enumObj=staffForm.staff.onJob!onJobList[0] dataList=onJobList /]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:79px;text-align: right;padding-top: 13px;">联系电话:</td>
                                    <td><input type="number" name="staff.mobile" id="mobile"  value="${staffForm.staff.mobile!''}" maxlength="32" placeholder="这里输入手机号" style="width:98%;"/></td>
                                </tr>
                                <tr>
                                    <td style="width:79px;text-align: right;padding-top: 13px;">邮箱:</td>
                                    <td><input type="email" name="staff.email" id="email" value="${staffForm.staff.email!''}" maxlength="32" placeholder="这里输入邮箱" style="width:98%;"/></td>
                                </tr>
                                <tr>
                                    <td style="width:79px;text-align: right;padding-top: 13px;">备注:</td>
                                    <td><input type="memo" name="staff.memo" id="email" value="${staffForm.staff.memo!''}" maxlength="32" placeholder="这里输入备注" style="width:98%;"/></td>
                                </tr>
                                <tr>
                                    <td style="text-align: center;" colspan="10">
                                        <button class="btn btn-mini btn-primary" type="submit">保存</button>
                                        <button class="btn btn-mini btn-danger" type="button" onclick="top.Dialog.close();">取消</button>
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
</html>