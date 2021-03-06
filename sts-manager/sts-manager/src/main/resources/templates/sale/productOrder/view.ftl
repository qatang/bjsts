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
                            <div class="col-xs-12">
                                <div style="padding-top: 13px;"></div>
                                <table id="table_report" class="table table-striped table-bordered table-hover">
                                    <tr>
                                        <td style="width:100px;text-align: right;padding-top: 13px;">项目编码:</td>
                                        <td>
                                            ${productOrder.planNo}
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="width:100px;text-align: right;padding-top: 13px;">项目名称:</td>
                                        <td>${productOrder.name}</td>
                                    </tr>
                                    <tr>
                                        <td style="width:100px;text-align: right;padding-top: 13px;">询价类型:</td>
                                        <td>
                                            ${productOrder.planType.getName()}
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="width:100px;text-align: right;padding-top: 13px;">项目来源:</td>
                                        <td>
                                        ${productOrder.sourceType.getName()}
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="width:100px;text-align: right;padding-top: 13px;">询价日期:</td>
                                        <td>
                                            [@macro.displayDate value=productOrder.priceTime!""/]
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="width:100px;text-align: right;padding-top: 13px;">项目地点:</td>
                                        <td>${productOrder.location!""}</td>
                                    </tr>
                                    <tr>
                                        <td style="width:100px;text-align: right;padding-top: 13px;">联系人姓名:</td>
                                        <td>${productOrder.linkman!""}</td>
                                    </tr>
                                    <tr>
                                        <td style="width:100px;text-align: right;padding-top: 13px;">联系人电话:</td>
                                        <td>${productOrder.mobile!""}</td>
                                    </tr>
                                    <tr>
                                        <td style="width:100px;text-align: right;padding-top: 13px;">联系人单位:</td>
                                        <td>${productOrder.company!""}</td>
                                    </tr>
                                    <tr>
                                        <td style="width:100px;text-align: right;padding-top: 13px;">联系人邮箱:</td>
                                        <td>${productOrder.email!""}</td>
                                    </tr>
                                    <tr>
                                        <td style="width:100px;text-align: right;padding-top: 13px;">项目说明:</td>
                                        <td>${productOrder.description!""}</td>
                                    </tr>
                                    <tr>
                                        <td style="width:100px;text-align: right;padding-top: 13px;">备案登记人:</td>
                                        <td>
                                            ${productOrder.booker!""}
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="width:100px;text-align: right;padding-top: 13px;">项目资料:</td>
                                        <td>
                                            [@macro.displayFile document=planDocument!"" /]
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="width:100px;text-align: right;padding-top: 13px;">报价员:</td>
                                        <td>${productOrder.quoter!""}</td>
                                    </tr>
                                    <tr>
                                        <td style="width:100px;text-align: right;padding-top: 13px;">报价时间:</td>
                                        <td>[@macro.displayDate value=productOrder.quoteTime!""/]</td>
                                    </tr>
                                    <tr>
                                        <td style="width:100px;text-align: right;padding-top: 13px;">报价单:</td>
                                        <td>
                                            [@macro.displayFile document=document!"" /]
                                        </td>
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