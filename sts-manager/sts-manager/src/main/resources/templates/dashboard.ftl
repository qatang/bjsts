<!DOCTYPE html>
<html>
    <head>
        <title>顺天盛综合管理系统</title>
        <#include "${ctx}/common/head.ftl"/>
    </head>
    <body class="no-skin">
    <div id="navbar" class="navbar navbar-default">
        <script type="text/javascript">
            try{ace.settings.check('navbar' , 'fixed');}catch(e){}
        </script>

        <div class="navbar-container" id="navbar-container">
            <!-- #section:basics/sidebar.mobile.toggle -->
            <button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler" data-target="#sidebar">
                <span class="sr-only">Toggle sidebar</span>

                <span class="icon-bar"></span>

                <span class="icon-bar"></span>

                <span class="icon-bar"></span>
            </button>

            <!-- /section:basics/sidebar.mobile.toggle -->
            <div class="navbar-header pull-left">
                <!-- #section:basics/navbar.layout.brand -->
                <a class="navbar-brand">
                    <small> <i class="fa fa-futbol-o"></i> 顺天盛综合管理系统 </small>
                </a>

                <!-- /section:basics/navbar.layout.brand -->

                <!-- #section:basics/navbar.toggle -->

                <!-- /section:basics/navbar.toggle -->
            </div>

            <!-- #section:basics/navbar.dropdown -->
            <div class="navbar-buttons navbar-header pull-right" role="navigation">
                <ul class="nav ace-nav">
                    <li class="light-blue">
                        <a>欢迎登录, ${currentUser.username}</a>
                    </li>
                    <li class="light-blue">
                        <a data-toggle="dropdown"  class="dropdown-toggle" href="#">
                            <img class="nav-user-photo" src="${ctx}/static/ace/avatars/user.jpg" alt="Jason's Photo" />
                            <span class="user-info" id="user_info">
								</span>
                            <i class="ace-icon fa fa-caret-down"></i>
                        </a>

                        <ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
                            <li>
                                <a onclick="diag('修改密码', '${ctx}/user/password/change');" style="cursor:pointer;"><i class="ace-icon fa fa-cog"></i>修改密码</a><!-- editUserH()在 WebRoot\static\js\myjs\head.js中 -->
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a href="${ctx}/signout"><i class="ace-icon fa fa-power-off"></i>退出登录</a>
                            </li>
                        </ul>
                    </li>

                    <!-- /section:basics/navbar.user_menu -->
                </ul>
            </div>
            <!-- /section:basics/navbar.dropdown -->
        </div><!-- /.navbar-container -->
    </div>
    <div id="fhsmsobj"><!-- 站内信声音消息提示 --></div>

        <div id="websocket_button"></div><!-- 少了此处，聊天窗口就无法关闭 -->
        <!-- /section:basics/navbar.layout -->
        <div class="main-container" id="main-container">
            <script type="text/javascript">
                try{ace.settings.check('main-container' , 'fixed')}catch(e){}
            </script>

            <!-- #section:basics/sidebar -->
            <!-- 左侧菜单 -->
            <div id="sidebar" class="sidebar                  responsive">
                <script type="text/javascript">
                    try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
                </script>

                <ul class="nav nav-list">
                    <li class="">
                        <a href="${ctx}/">
                            <i class="menu-icon fa fa-tachometer"></i>
                            <span class="menu-text">后台首页</span>
                        </a>
                        <b class="arrow"></b>
                    </li>
                    <#if menus??>
                    <#list menus as menu>
                    <li class="" id="lm${menu.id}">
                        <a style="cursor:pointer;" class="dropdown-toggle">
                            <i class="${menu.resourceIcon!'menu-icon fa fa-leaf black'}"></i>
                            <span class="menu-text">
                            ${menu.name}
                            </span>
                            <#if menu.children?has_content><b class="arrow fa fa-angle-down"></b></#if>
                        </a>
                        <b class="arrow"></b>
                        <#if menu.children?has_content>
                        <ul class="submenu">
                            <#list menu.children as child>
                            <li class="" id="z${child.id}">
                                <a style="cursor:pointer;" <#if child.url??>target="mainFrame" onclick="siMenu('z${child.id}','lm${menu.id}','${child.name}','${child.url}')"</#if>>
                                <i class="<#if child.resourceIcon??>${child.resourceIcon}<#else>menu-icon fa fa-leaf black</#if>"></i>
                            ${child.name}
                                </a>
                                <b class="arrow"></b>
                            </li>
                            </#list>
                        </ul>
                        </#if>
                    </li>
                    </#list>
                    </#if>
                </ul><!-- /.nav-list -->

                <!-- #section:basics/sidebar.layout.minimize -->
                <div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
                    <i class="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
                </div>

                <!-- /section:basics/sidebar.layout.minimize -->
                <script type="text/javascript">
                    try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
                </script>
            </div>

            <!-- /section:basics/sidebar -->
            <div class="main-content">
                <div class="main-content-inner">

                    <!-- /section:basics/content.breadcrumbs -->
                    <div class="page-content">
                        <div class="row">
                            <div>
                                <iframe name="mainFrame" id="mainFrame" frameborder="0" src="${ctx}/welcome" style="margin:0 auto;width:100%;height:100%;"></iframe>
                            </div>
                        </div><!-- /.row -->
                    </div><!-- /.page-content -->

                </div>
            </div><!-- /.main-content -->

        </div><!-- /.main-container -->

        <#include "${ctx}/common/footer.ftl"/>
        <!-- ace scripts -->
        <script src="${ctx}/static/ace/js/ace/elements.scroller.js"></script>
        <script src="${ctx}/static/ace/js/ace/elements.colorpicker.js"></script>
        <script src="${ctx}/static/ace/js/ace/elements.fileinput.js"></script>
        <script src="${ctx}/static/ace/js/ace/elements.typeahead.js"></script>
        <script src="${ctx}/static/ace/js/ace/elements.wysiwyg.js"></script>
        <script src="${ctx}/static/ace/js/ace/elements.spinner.js"></script>
        <script src="${ctx}/static/ace/js/ace/elements.treeview.js"></script>
        <script src="${ctx}/static/ace/js/ace/elements.wizard.js"></script>
        <script src="${ctx}/static/ace/js/ace/elements.aside.js"></script>
        <script src="${ctx}/static/ace/js/ace/ace.js"></script>
        <script src="${ctx}/static/ace/js/ace/ace.ajax-content.js"></script>
        <script src="${ctx}/static/ace/js/ace/ace.touch-drag.js"></script>
        <script src="${ctx}/static/ace/js/ace/ace.sidebar.js"></script>
        <script src="${ctx}/static/ace/js/ace/ace.sidebar-scroll-1.js"></script>
        <script src="${ctx}/static/ace/js/ace/ace.submenu-hover.js"></script>
        <script src="${ctx}/static/ace/js/ace/ace.widget-box.js"></script>
        <script src="${ctx}/static/ace/js/ace/ace.settings.js"></script>
        <script src="${ctx}/static/ace/js/ace/ace.settings-rtl.js"></script>
        <script src="${ctx}/static/ace/js/ace/ace.settings-skin.js"></script>
        <script src="${ctx}/static/ace/js/ace/ace.widget-on-reload.js"></script>
        <script src="${ctx}/static/ace/js/ace/ace.searchbox-autocomplete.js"></script>

        <script src="${ctx}/static/ace/js/ace/elements.onpage-help.js"></script>
        <script src="${ctx}/static/ace/js/ace/ace.onpage-help.js"></script>

        <!--引入属于此页面的js -->
        <script src="${ctx}/static/js/myjs/head.js"></script>
        <!--引入属于此页面的js -->
        <script src="${ctx}/static/js/myjs/index.js"></script>
        <!--引入弹窗组件start-->
        <script src="${ctx}/plugins/attention/zDialog/zDrag.js"></script>
        <script src="${ctx}/plugins/attention/zDialog/zDialog.js"></script>
        <!--引入弹窗组件end-->
        <!--提示框-->
        <script src="${ctx}/static/js/jquery.tips.js"></script>
    </body>
</html>