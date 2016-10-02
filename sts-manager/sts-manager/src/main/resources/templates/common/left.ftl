[#ftl]
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
        [#if menus??]
            [#list menus as menu]
                <li class="" id="lm${menu.id}">
                    <a style="cursor:pointer;" class="dropdown-toggle">
                        <i class="${menu.resourceIcon!'menu-icon fa fa-leaf black'}"></i>
                            <span class="menu-text">
                            ${menu.name}
                            </span>
                        [#if menu.children?has_content]<b class="arrow fa fa-angle-down"></b>[/#if]
                    </a>
                    <b class="arrow"></b>
                    [#if menu.children?has_content]
                        <ul class="submenu">
                            [#list menu.children as child]
                                <li class="" id="z${child.id}">
                                    <a style="cursor:pointer;" [#if child.url??]target="mainFrame" onclick="siMenu('z${child.id}','lm${menu.id}','${child.name}','${child.url}')"[/#if]>
                                    <i class="[#if child.resourceIcon??]${child.resourceIcon}[#else]menu-icon fa fa-leaf black[/#if]"></i>
                                    ${child.name}
                                    </a>
                                    <b class="arrow"></b>
                                </li>
                            [/#list]
                        </ul>
                    [/#if]
                </li>
            [/#list]
        [/#if]
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