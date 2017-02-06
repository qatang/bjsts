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
                            <div class="col-xs-11">
                            [#--<form action="${ctx}/socialSecurity/list" method="post" name="socialSecurityForm" id="socialSecurityForm">
                                    <table style="margin-top:5px;">
                                        <tr>
                                            <td>
                                                <div class="nav-search">
                                                    <span class="input-icon">
                                                        <input class="nav-search-input" autocomplete="off" id="nav-search-input" type="text" name="content" value="${socialSecuritySearchable.content!''}" placeholder="这里输入关键词" />
                                                        <i class="ace-icon fa fa-search nav-search-icon"></i>
                                                    </span>
                                                </div>
                                            </td>
                                            <td style="vertical-align:top;padding-left:2px;"><button class="btn btn-light btn-xs" title="检索" type="submit"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></button></td>
                                        </tr>
                                    </table>
                                </form>--]
                            </div>
                            <div class="col-xs-1">
                                <div style="float:right;margin-top:5px;">
                                    <a class="btn btn-xs btn-success" onclick="customDiag('新增合同', '${ctx}/contract/create', 800, 600);">新增合同</a>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                <table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">
                                    <thead>
                                        <tr>
                                            <th class="center">合同编号</th>
                                            <th class="center">项目编号</th>
                                            <th class="center">项目名称</th>
                                            <th class="center">合同状态</th>
                                            <th class="center">客户名称</th>
                                            <th class="center">联系人</th>
                                            <th class="center">签订日期</th>
                                            <th class="center">质保日期</th>
                                            <th class="center">合同金额</th>
                                            <th class="center" colspan="2">操作</th>
                                        </tr>
                                    </thead>

                                    <tbody>
                                        [#if page?? && page.content?has_content]
                                            [#list page.content as contract]
                                                <tr>
                                                    <td class="center"><a href="${ctx}/contract/view/${contract.id}">${contract.contractNo}</a></td>
                                                    <td class="center">${contract.planNo}</td>
                                                    <td class="center">${contract.planName!''}</td>
                                                    <td class="center">${contract.status.getName()}</td>
                                                    <td class="center">${contract.company}</td>
                                                    <td class="center">${contract.linkman}</td>
                                                    <td class="center">
                                                        [#if contract.signTime??]
                                                            ${contract.signTime?string("yyyy-MM-dd HH:mm:ss")}
                                                        [/#if]
                                                    </td>
                                                    <td class="center">
                                                        [#if contract.qualityTime??]
                                                            ${contract.qualityTime?string("yyyy-MM-dd HH:mm:ss")}
                                                        [/#if]
                                                    </td>
                                                    <td class="center">${contract.amount?c}</td>
                                                    <td class="center">
                                                        <div class="hidden-sm hidden-xs btn-group">
                                                            <a class="green" onclick="customDiag('修改合同', '${ctx}/contract/update/${contract.id}', 800, 600);" style="cursor: pointer;text-decoration:none;">
                                                                修改
                                                            </a>
                                                        </div>
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