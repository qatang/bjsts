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
                                    <a class="btn btn-xs btn-success" onclick="customDiag('新增采购单', '${ctx}/purchase/create', 800, 600);">新增采购单</a>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                <table id="simple-table" class="table table-striped table-bordered table-hover table-fixed-head" style="margin-top:5px;">
                                    <thead>
                                        <tr>
                                            <th class="center">采购单编号</th>
                                            <th class="center">产品名称</th>
                                            <th class="center">规格型号</th>
                                            <th class="center">采购数量</th>
                                            <th class="center">采购负责人</th>
                                            <th class="center">到货商品核对</th>
                                            <th class="center">物料接收人</th>
                                            <th class="center">接收日期</th>
                                            <th class="center">操作</th>
                                        </tr>
                                    </thead>

                                    <tbody>
                                        [#if page?? && page.content?has_content]
                                            [#list page.content as purchase]
                                                <tr>
                                                    <td class="center"><a onclick="diag('采购单查看', '${ctx}/purchase/view/${purchase.id}');">${purchase.purchaseNo}</a></td>
                                                    <td class="center">${purchase.productName!""}</td>
                                                    <td class="center">${purchase.productModel!""}</td>
                                                    <td class="center">${purchase.quantity!""}</td>
                                                    <td class="center">${purchase.operator}</td>
                                                    <td class="center">
                                                        [#if purchase.inBound.getValue() != noStatus.getValue()]
                                                            ${purchase.inBoundEntity.verify.getName()}
                                                        [/#if]
                                                    </td>
                                                    <td class="center">
                                                        [#if purchase.inBound.getValue() != noStatus.getValue()]
                                                            ${purchase.inBoundEntity.sendee}
                                                        [/#if]
                                                    </td>
                                                    <td class="center">
                                                        [#if purchase.inBound.getValue() != noStatus.getValue()]
                                                            [@macro.displayDate value=purchase.inBoundEntity.sendeeTime!""/]
                                                        [/#if]
                                                    </td>
                                                    <td class="center">
                                                        [#if purchase.inBound.getValue() == noStatus.getValue()]
                                                            <a class="green" onclick="diag('入库', '${ctx}/inBound/create/${purchase.purchaseNo}');" style="cursor: pointer;text-decoration:none;">
                                                                入库
                                                            </a>
                                                        [#else]
                                                                <a class="green" onclick="diag('入库', '${ctx}/inBound/update/${purchase.purchaseNo}');" style="cursor: pointer;text-decoration:none;">
                                                                    修改
                                                                </a>
                                                                <a class="green" onclick="diag('入库', '${ctx}/inBound/view/${purchase.purchaseNo}');" style="cursor: pointer;text-decoration:none;">
                                                                    查看
                                                                </a>
                                                        [/#if]
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