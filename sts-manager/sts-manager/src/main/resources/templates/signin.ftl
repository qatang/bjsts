<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>signin</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/login/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/static/login/css/camera.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/static/login/bootstrap-responsive.min.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/static/login/matrix-login.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/static/login/font-awesome.css" />
    <script src="${ctx}/static/login/js/jquery-1.5.1.min.js"></script>
</head>
<body>
<div style="width:100%;text-align: center;margin: 0 auto;position: absolute;">
    <div id="loginbox">
        <form action="${ctx}/signin" method="post" name="userForm" id="loginForm">
            <div class="control-group normal_text">
                <h3>
                    <img src="${ctx}/static/login/logo.png" alt="Logo" />
                </h3>
            </div>
            <div class="control-group">
                <div class="controls">
                    <div class="main_input_box">
                                <span class="add-on bg_lg">
                                <i><img height="37" src="${ctx}/static/login/user.png" /></i>
                                </span><input type="text" name="userInfo.username" id="loginname" value="" placeholder="请输入用户名" />
                    </div>
                </div>
            </div>
            <div class="control-group">
                <div class="controls">
                    <div class="main_input_box">
                                <span class="add-on bg_ly">
                                <i><img height="37" src="${ctx}/static/login/suo.png" /></i>
                                </span><input type="password" name="userInfo.password" id="password" placeholder="请输入密码" value=""/>
                    </div>
                </div>
            </div>
            <div class="form-actions">
                <div style="width:86%;padding-left:8%;">
                    <span class="pull-right" style="padding-right:3%;"><a href="javascript:cancel();" class="btn btn-success">取消</a></span>
                    <span class="pull-right">
                                <button type="submit" class="flip-link btn btn-info">登录</button>
                            </span>
                </div>
            </div>
        </form>
    </div>
</div>

<div id="templatemo_banner_slide" class="container_wapper">
    <div class="camera_wrap camera_emboss" id="camera_slide">
        <div data-src="${ctx}/static/login/images/banner_slide_01.jpg"></div>
        <div data-src="${ctx}/static/login/images/banner_slide_02.jpg"></div>
        <div data-src="${ctx}/static/login/images/banner_slide_03.jpg"></div>
    </div>
</div>

<script src="${ctx}/static/login/js/bootstrap.min.js"></script>
<script src="${ctx}/static/js/jquery-1.7.2.js"></script>
<script src="${ctx}/static/login/js/jquery.easing.1.3.js"></script>
<script src="${ctx}/static/login/js/jquery.mobile.customized.min.js"></script>
<script src="${ctx}/static/login/js/camera.min.js"></script>
<script src="${ctx}/static/login/js/templatemo_script.js"></script>
<script src="${ctx}/static/js/jquery.tips.js"></script>
<script src="${ctx}/static/js/jquery.cookie.js"></script>

<script>
    var cancel = function() {
        $("#loginname").val('');
        $("#password").val('');
    }
</script>
</body>
</html>