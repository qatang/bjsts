[#ftl strip_whitespace=true]
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
                                <div style="padding-top: 13px;"></div>
                                <table id="table_report" class="table table-striped table-bordered table-hover">
                                    <tr>
                                        <td style="width:100px;text-align: right;padding-top: 13px;">项目编码:</td>
                                        <td>
                                            ${quote.planNo}
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="width:100px;text-align: right;padding-top: 13px;">项目名称:</td>
                                        <td>${quote.name}</td>
                                    </tr>
                                    <tr>
                                        <td style="width:100px;text-align: right;padding-top: 13px;">询价类型:</td>
                                        <td>
                                            ${quote.planType.getName()}
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="width:100px;text-align: right;padding-top: 13px;">项目来源:</td>
                                        <td>
                                        ${quote.sourceType.getName()}
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="width:100px;text-align: right;padding-top: 13px;">询价日期:</td>
                                        <td>
                                        ${quote.priceTime?string("yyyy-MM-dd HH:mm:ss")}
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="width:100px;text-align: right;padding-top: 13px;">项目地点:</td>
                                        <td>${quote.location}</td>
                                    </tr>
                                    <tr>
                                        <td style="width:100px;text-align: right;padding-top: 13px;">联系人姓名:</td>
                                        <td>${quote.linkman}</td>
                                    </tr>
                                    <tr>
                                        <td style="width:100px;text-align: right;padding-top: 13px;">联系人电话:</td>
                                        <td>${quote.mobile}</td>
                                    </tr>
                                    <tr>
                                        <td style="width:100px;text-align: right;padding-top: 13px;">联系人单位:</td>
                                        <td>${quote.company}</td>
                                    </tr>
                                    <tr>
                                        <td style="width:100px;text-align: right;padding-top: 13px;">联系人邮箱:</td>
                                        <td>${quote.email}</td>
                                    </tr>
                                    <tr>
                                        <td style="width:100px;text-align: right;padding-top: 13px;">项目说明:</td>
                                        <td>${quote.description}</td>
                                    </tr>
                                    <tr>
                                        <td style="width:100px;text-align: right;padding-top: 13px;">项目资料:</td>
                                        <td><a href="${ctx}/file${customerFileUrl}" target="_blank">${ctx}/file${customerFileUrl}</a></td>
                                    </tr>
                                    <tr>
                                        <td style="width:100px;text-align: right;padding-top: 13px;">报价员:</td>
                                        <td>${quote.quoter}</td>
                                    </tr>
                                    <tr>
                                        <td style="width:100px;text-align: right;padding-top: 13px;">报价时间:</td>
                                        <td>${quote.quoteTime?string("yyyy-MM-dd HH:mm:ss")}</td>
                                    </tr>
                                    <tr>
                                        <td style="width:100px;text-align: right;padding-top: 13px;">项目资料:</td>
                                        <td><a href="${ctx}/file${quoteFileUrl}" target="_blank">${ctx}/file${quoteFileUrl}</a></td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
    [#include "${ctx}/common/footer.ftl"/]
</html>