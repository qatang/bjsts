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
                        <form action="${ctx}/user/${action}" name="userForm" id="userForm" method="post">
                            <div style="padding-top: 13px;"></div>
                        [@macro.errors /]
                            <input type="hidden" name="userInfo.id" value="${userForm.userInfo.id!''}"/>
                            <table id="table_report" class="table table-striped table-bordered table-hover">
                                <tr>
                                    <td style="width:79px;text-align: right;padding-top: 13px;">用户名:</td>
                                    <td>
                                    [#if userForm.userInfo.id??]
                                    ${userForm.userInfo.username}
                                    [#else]
                                        <input type="text" name="userInfo.username" id="username" value="${userForm.userInfo.username!''}" maxlength="32" placeholder="这里输入用户名" required="" style="width:98%;"/>
                                    [/#if]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:79px;text-align: right;padding-top: 13px;">部门:</td>
                                    <td>
                                    [@macro.select name="userInfo.departmentId" value=userForm.userInfo.departmentId dataList=departmentList /]
                                    </td>
                                </tr>
                            [#if !userForm.userInfo.id??]
                                <tr>
                                    <td style="width:79px;text-align: right;padding-top: 13px;">密码:</td>
                                    <td><input type="password" name="userInfo.password" id="password" value="${userForm.userInfo.password!''}" maxlength="32" placeholder="这里输入密码" required="" style="width:98%;"/></td>
                                </tr>
                                <tr>
                                    <td style="width:79px;text-align: right;padding-top: 13px;">确认密码:</td>
                                    <td><input type="password" name="conPassword" id="conPassword" value="${userForm.conPassword!''}" maxlength="16" placeholder="这里输入确认密码" required="" style="width:98%;" required=""/></td>
                                </tr>
                            [/#if]
                                <tr>
                                    <td style="width:79px;text-align: right;padding-top: 13px;">邮箱:</td>
                                    <td><input type="email" name="userInfo.email" id="email" value="${userForm.userInfo.email!''}" maxlength="32" placeholder="这里输入邮箱" required="" style="width:98%;"/></td>
                                </tr>
                                <tr>
                                    <td style="width:79px;text-align: right;padding-top: 13px;">手机号:</td>
                                    <td><input type="number" name="userInfo.mobile" id="mobile"  value="${userForm.userInfo.mobile!''}" maxlength="32" placeholder="这里输入手机号" style="width:98%;"/></td>
                                </tr>
                                <tr>
                                    <td style="width:79px;text-align: right;padding-top: 13px;">姓名:</td>
                                    <td><input type="text" name="userInfo.realName" id="name" value="${userForm.userInfo.realName!''}" maxlength="32" placeholder="这里输入姓名" style="width:98%;"/></td>
                                </tr>
                                <tr>
                                    <td style="width:79px;text-align: right;padding-top: 13px;">性别:</td>
                                    <td>
                                    [@macro.selectEnum name="userInfo.maleType" enumObj=userForm.userInfo.maleType!maleTypeList[0] dataList=maleTypeList /]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:79px;text-align: right;padding-top: 13px;">职位:</td>
                                    <td><input type="text" name="userInfo.position" id="position"  value="${userForm.userInfo.position!''}" maxlength="32" placeholder="这里输入职位" style="width:98%;"/></td>
                                </tr>
                                <tr>
                                    <td style="width:79px;text-align: right;padding-top: 13px;">身份证号:</td>
                                    <td><input type="text" name="userInfo.idCard" id="idCard"  value="${userForm.userInfo.idCard!''}" maxlength="32" placeholder="这里输入身份证号" style="width:98%;"/></td>
                                </tr>
                                <tr>
                                    <td style="width:79px;text-align: right;padding-top: 13px;">教育:</td>
                                    <td>
                                    [@macro.selectEnum name="userInfo.educationType" enumObj=userForm.userInfo.educationType!educationTypeList[0] dataList=educationTypeList /]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:79px;text-align: right;padding-top: 13px;">政治面貌:</td>
                                    <td>
                                    [@macro.selectEnum name="userInfo.polityType" enumObj=userForm.userInfo.polityType!polityTypeList[0] dataList=polityTypeList /]
                                    </td>
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