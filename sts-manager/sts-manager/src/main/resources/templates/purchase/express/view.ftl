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
                                <td style="width:100px;text-align: right;padding-top: 13px;">编号:</td>
                                <td>
                                ${express.id}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">快递单号:</td>
                                <td>${express.expressNo!""}</td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">发货人:</td>
                                <td>${express.shipper!""}</td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">付款人:</td>
                                <td>${express.payer!""}</td>
                            </tr>

                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">快递费:</td>
                                <td>[@macro.displayMoney value=express.cost!''/]</td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">发货内容:</td>
                                <td>${express.content!""}</td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">收货人姓名:</td>
                                <td>${express.receiver!""}</td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">收货人电话:</td>
                                <td>${express.mobile!""}</td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">收货人地址:</td>
                                <td>${express.address!""}</td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">快递单位名称:</td>
                                <td>${express.company!""}</td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">投递日期:</td>
                                <td>
                                [@macro.displayDate value=express.deliverDate!""/]
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">接收日期:</td>
                                <td>
                                [@macro.displayDate value=express.receiveDate!""/]
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