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
                                    <td style="width:79px;text-align: right;padding-top: 13px;">职工编号:</td>
                                    <td>
                                        [@macro.inputText name="staff.staffNo" value=staffForm.staff.staffNo!"" placeholder="职工编号"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:79px;text-align: right;padding-top: 13px;">姓名:</td>
                                    <td>
                                        [@macro.inputText name="staff.realName" value=staffForm.staff.realName!"" placeholder="姓名"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:79px;text-align: right;padding-top: 13px;">性别:</td>
                                    <td>
                                    [@macro.selectEnum name="staff.maleType" enumObj=staffForm.staff.maleType!maleTypeList[0] dataList=maleTypeList /]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:79px;text-align: right;padding-top: 13px;">职位:</td>
                                    <td>
                                        [@macro.inputText name="staff.position" value=staffForm.staff.position!"" placeholder="职位"/]
                                     </td>
                                </tr>
                                <tr>
                                    <td style="width:79px;text-align: right;padding-top: 13px;">身份证号:</td>
                                    <td>
                                        [@macro.inputText id="idCard" name="staff.idCard" value=staffForm.staff.idCard!"" placeholder="身份证号" pattern="^([1-9][0-9]{5}(18|19|([23][0-9]))[0-9]{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)[0-9]{3}[0-9Xx])|([1-9][0-9]{5}[0-9]{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)[0-9]{3})$" /]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">入职日期:</td>
                                    <td>
                                    [@macro.datePicker name="staff.entryTime" value=staffForm.staff.entryTime placeholder="入职日期"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:100px;text-align: right;padding-top: 13px;">离职日期:</td>
                                    <td>
                                    [@macro.datePicker name="staff.departureTime" value=staffForm.staff.departureTime placeholder="离职日期"/]
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
                                        [@macro.datePicker id="birthday" name="staff.birthday" value=staffForm.staff.birthday placeholder="生日"/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:79px;text-align: right;padding-top: 13px;">在离职:</td>
                                    <td>
                                    [@macro.selectEnum name="staff.onJob" enumObj=staffForm.staff.onJob!onJobList[0] dataList=onJobList /]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:79px;text-align: right;padding-top: 13px;">社保:</td>
                                    <td>
                                    [@macro.selectEnum name="staff.socialSecurity" enumObj=staffForm.staff.socialSecurity!socialSecurityList[0] dataList=socialSecurityList /]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:79px;text-align: right;padding-top: 13px;">联系电话:</td>
                                    <td>
                                        [@macro.inputText name="staff.mobile" value=staffForm.staff.mobile!"" placeholder="手机号" type="number" required=false/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:79px;text-align: right;padding-top: 13px;">邮箱:</td>
                                    <td>
                                        [@macro.inputText name="staff.email" value=staffForm.staff.email!"" placeholder="邮箱" type="email" required=false/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:79px;text-align: right;padding-top: 13px;">备注:</td>
                                    <td>
                                        [@macro.inputText name="staff.memo" value=staffForm.staff.memo!"" placeholder="备注" required=false/]
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

<script>
    $(function() {
        $("#idCard").bind("blur", function () {
            var idCard = $(this).val();

            if (idCard && (idCard.length == 15 || idCard.length == 18)) {
                var year, month, day, birthday;
                if (idCard.length == 15) {
                    year = idCard.substring(6,8);
                    month = idCard.substring(8,10);
                    day = idCard.substring(10,12);
                    birthday = "19" + year + "-" + month + "-" + day;
                } else {
                    year = idCard.substring(6,10);
                    month = idCard.substring(10,12);
                    day = idCard.substring(12,14);
                    birthday = year + "-" + month + "-" + day;
                }
                $("#birthday").val(birthday);
            }
        })
    });
</script>
</html>