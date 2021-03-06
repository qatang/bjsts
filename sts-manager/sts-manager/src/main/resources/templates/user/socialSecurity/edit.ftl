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
                        <form action="${ctx}/socialSecurity/${action}" name="socialSecurityForm" id="socialSecurityForm" method="post">
                            <div style="padding-top: 13px;"></div>
                        [@macro.errors /]
                            <input type="hidden" name="socialSecurity.id" value="${socialSecurityForm.socialSecurity.id!''}"/>
                            <table id="table_report" class="table table-striped table-bordered table-hover">
                                <tr>
                                    <td style="width:79px;text-align: right;padding-top: 13px;">姓名:</td>
                                    <td>
                                        [@macro.inputText name="socialSecurity.realName" value=socialSecurityForm.socialSecurity.realName!""/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:79px;text-align: right;padding-top: 13px;">身份证号:</td>
                                    <td>
                                        [@macro.inputText name="socialSecurity.idCard" value=socialSecurityForm.socialSecurity.idCard!""/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:79px;text-align: right;padding-top: 13px;">联系电话:</td>
                                    <td>
                                        [@macro.inputText name="socialSecurity.mobile" value=socialSecurityForm.socialSecurity.mobile!""/]
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:79px;text-align: right;padding-top: 13px;">备注:</td>
                                    <td>
                                    [@macro.inputText name="socialSecurity.memo" value=socialSecurityForm.socialSecurity.memo!"" required=false/]
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