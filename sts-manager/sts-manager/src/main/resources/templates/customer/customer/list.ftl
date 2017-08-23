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
                        [@macro.errorMsg/]
                        <div class="row">
                            <div class="col-xs-11">
                            <form action="${ctx}/customer/list" method="post" name="customerForm" id="customerForm">
                                <div class="container-fluid">
                                    <div class="row">
                                        <div class="col-xs-2" style="padding:2px;border-top:0;">
                                        [@macro.inputText name="companyName" value=customerSearchable.companyName!'' placeholder="客户名称" required=false /]
                                        </div>
                                        <div class="col-xs-2" style="padding:2px;border-top:0;">
                                        [@macro.inputText name="tel" value=customerSearchable.tel!'' placeholder="公司电话" required=false /]
                                        </div>
                                        <div class="col-xs-1" style="padding:2px;border-top:0;"><button id="btn" class="btn btn-light btn-xs" title="检索" type="submit"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></button></div>
                                    </div>
                                </div>
                                </form>
                            </div>
                            <div class="col-xs-1">
                                <div style="float:right;margin-top:5px;">
                                    <a class="btn btn-xs btn-success" onclick="diag('新增客户', '${ctx}/customer/create');">新增客户</a>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                <table id="simple-table" class="table table-striped table-bordered table-hover table-fixed-head" style="margin-top:5px;">
                                    <thead>
                                        <tr>
                                            <th class="center">客户编号</th>
                                            <th class="center">客户名称</th>
                                            <th class="center">客户类型</th>
                                            <th class="center">公司电话</th>
                                            <th class="center">所有者</th>
                                            <th class="center">操作</th>
                                        </tr>
                                    </thead>

                                    <tbody>
                                        [#if page?? && page.content?has_content]
                                            [#list page.content as customer]
                                                <tr>
                                                    <td class="center"><a onclick="diag('客户查看', '${ctx}/customer/view/${customer.id}')" style="cursor:pointer;">${customer.id}</a></td>
                                                    <td class="center">${customer.companyName!""}</a></td>
                                                    <td class="center">${customer.customerType.getName()}</td>
                                                    <td class="center">${customer.tel!''}</td>
                                                    <td class="center">${customer.owner!""}</td>
                                                    <td class="center">
                                                        <a class="green" onclick="diag('详细信息', '${ctx}/customer/createItem/${customer.id}');" style="cursor: pointer;text-decoration:none;">
                                                            详细信息
                                                        </a>
                                                        <a class="green" onclick="diag('客户修改', '${ctx}/customer/update/${customer.id}');" style="cursor: pointer;text-decoration:none;">
                                                            修改
                                                        </a>
                                                        <a class="red" href="${ctx}/customer/disable/${customer.id}" onclick="return confirm('确定要删除该客户吗?');">
                                                            删除
                                                        </a>
                                                    </td>
                                                </tr>
                                            [/#list]
                                        [/#if]
                                    </tbody>
                                </table>

                                <div class="page-header position-relative">
                                    <table style="width:100%;">
                                        <tr>
                                            <td style="vertical-align:top;">
                                                <div style="float: right;padding-top: 0px;margin-top: 0px;">[@macro.pagination page=page/]</div>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>

    [#include "${ctx}/common/footer.ftl"/]
</html>