[#ftl strip_whitespace=true]
[#import "${ctx}/common/macroes.ftl" as macro]
<!DOCTYPE html>
<html>
<head>
    [#include "${ctx}/common/head.ftl"/]
    <link rel="stylesheet" href="${ctx}/static/css/sts-manager.css" />
</head>
<body class="hold-transition login-page">
<div class="login-box">
    <div class="login-logo">
        <a href="#"><b>顺天盛综合管理系统</b></a>
    </div>
    <!-- /.login-logo -->
    <div class="login-box-body">
        <p class="login-box-msg">管理员登录</p>

        <form action="${ctx}/signin" method="post" id="loginform">
            <div class="form-group has-feedback">
                <label>用户名：</label>
                <input class="form-control" name="userInfo.username">
                <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <label>密码：</label>
                <input type="password" name="userInfo.password" class="form-control">
                <span class="glyphicon glyphicon-lock form-control-feedback"></span>
            </div>
            <div class="row">
                <div class="col-xs-8">
[#--                    <div class="form-group checkbox">
                        <label> <input type="checkbox" name="rememberMe">
                            记住我的登录信息
                        </label>
                    </div>--]
                </div>
                <!-- /.col -->
                <div class="col-xs-4">
                    <button type="submit" class="btn btn-primary btn-block btn-flat">登录</button>
                </div>
                <!-- /.col -->
            </div>
            <div class="row">
                <div class="col-xs-12">
                [@macro.errors /]
                </div>
            </div>
        </form>

        <!-- /.social-auth-links -->

    </div>
    <!-- /.login-box-body -->
</div>
<!-- /.login-box -->
</body>
<script>
/*    var cancel = function() {
        $("#loginname").val('');
        $("#password").val('');
    }*/

    if (window != top) {
        top.location.href = location.href;
    }
</script>
</html>
