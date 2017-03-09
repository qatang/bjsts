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
                                    <a class="btn btn-xs btn-success" onclick="diag('新增物料出库', '${ctx}/outBound/create');">新增物料出库</a>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                <table id="simple-table" class="table table-striped table-bordered table-hover table-fixed-head" style="margin-top:5px;">
                                    <thead>
                                        <tr>
                                            <th class="center">编号</th>
                                            <th class="center">项目编号</th>
                                            <th class="center">项目名称</th>
                                            <th class="center">询价日期</th>
                                            <th class="center">联系人姓名</th>
                                            <th class="center">联系电话</th>
                                            <th class="center">联系单位</th>
                                            <th class="center">电子邮箱</th>
                                            <th class="center">产品名称</th>
                                            <th class="center">规格型号</th>
                                            <th class="center">数量</th>
                                            <th class="center">出库日期</th>
                                            <th class="center">领取人</th>
                                            <th class="center">状态</th>
                                            <th class="center">操作</th>
                                        </tr>
                                    </thead>

                                    <tbody>
                                        [#if page?? && page.content?has_content]
                                            [#list page.content as outBound]
                                                <tr>
                                                    <td class="center"><a onclick="diag('库存查看', '${ctx}/outBound/view/${outBound.id}')" style="cursor:pointer;">${outBound.id}</a></td>
                                                    <td class="center">${outBound.plan.planNo!""}</td>
                                                    <td class="center">${outBound.plan.name!""}</td>
                                                    <td class="center">[@macro.displayDate value=outBound.plan.priceTime!""/]</td>
                                                    <td class="center">${outBound.plan.linkman}</td>
                                                    <td class="center">${outBound.plan.mobile}</td>
                                                    <td class="center">${outBound.plan.company}</td>
                                                    <td class="center">${outBound.plan.email}</td>
                                                    <td class="center">${outBound.stock.productName!""}</td>
                                                    <td class="center">${outBound.stock.productModel!""}</td>
                                                    <td class="center">${outBound.quantity}</td>
                                                    <td class="center">[@macro.displayDate value=outBound.outBoundTime!""/]</td>
                                                    <td class="center">${outBound.receiptor!""}</td>
                                                    <td class="center">
                                                        [#if outBound.valid.getValue() == disableStatus.getValue()]
                                                            已撤销
                                                        [#else ]
                                                            正常
                                                        [/#if]
                                                    </td>
                                                    <td class="center">
                                                        [#if outBound.valid.getValue() != disableStatus.getValue()]
                                                            <a href="${ctx}/outBound/disable/${outBound.id?c}" onclick="return confirm('确定要撤销吗?');">撤销</a>
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