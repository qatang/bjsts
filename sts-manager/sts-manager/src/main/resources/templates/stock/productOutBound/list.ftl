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
                            <a class="btn btn-xs btn-success" onclick="diag('新增成品出库单', '${ctx}/productOutBound/create');">新增成品出库单</a>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-12">
                        <table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">
                            <thead>
                            <tr>
                                <th class="center">序号</th>
                                <th class="center">出库单编号</th>
                                <th class="center">订货单位</th>
                                <th class="center">项目名称</th>
                                <th class="center">联系人</th>
                                <th class="center">联系电话</th>
                                <th class="center">发货时间</th>
                                <th class="center">产品名称</th>
                                <th class="center">规格型号</th>
                                <th class="center">数量</th>
                                <th class="center">单位</th>
                                <th class="center">单价</th>
                                <th class="center">金额</th>
                                <th class="center">制单人</th>
                                <th class="center">提/发货人</th>
                                <th class="center">操作</th>
                            </tr>
                            </thead>

                            <tbody>
                            [#if page?? && page.content?has_content]
                                [#list page.content as productOutBound]
                                <tr>
                                    <td class="center"><a onclick="diag('成品出库单查看', '${ctx}/productOutBound/view/${productOutBound.id}');">${productOutBound.id}</a></td>
                                    <td class="center">${productOutBound.productOutBoundNo!""}</td>
                                    <td class="center">${productOutBound.company!""}</td>
                                    <td class="center">${productOutBound.planName!""}</td>
                                    <td class="center">${productOutBound.linkman!""}</td>
                                    <td class="center">${productOutBound.mobile!""}</td>
                                    <td class="center">[@macro.displayDate value=productOutBound.deliveryDate!"" /]</td>
                                    <td class="center">${productOutBound.productName!""}</td>
                                    <td class="center">${productOutBound.productModel!""}</td>
                                    <td class="center">${productOutBound.quantity!""}</td>
                                    <td class="center">${productOutBound.unit!""}</td>
                                    <td class="center">[@macro.displayMoney value=productOutBound.singleAmount!""/]</td>
                                    <td class="center">[@macro.displayMoney value=productOutBound.totalAmount!""/]</td>
                                    <td class="center">${productOutBound.operator!""}</td>
                                    <td class="center">${productOutBound.shipper!""}</td>
                                    <td class="center">
                                        <div class="hidden-sm hidden-xs btn-group">
                                            <a class="green" onclick="diag('修改', '${ctx}/productOutBound/update/${productOutBound.id}');" style="cursor: pointer;text-decoration:none;">
                                                修改
                                            </a>
                                        </div>
                                        <div class="hidden-sm hidden-xs btn-group">
                                            <a class="red" href="${ctx}/productOutBound/disable/${productOutBound.id?c}" onclick="return confirm('确定要删除吗?');">删除</a>
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