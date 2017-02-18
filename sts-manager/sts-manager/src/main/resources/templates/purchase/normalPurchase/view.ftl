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
                                ${normalPurchase.id}
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">名称:</td>
                                <td>${normalPurchase.name}</td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">购买人:</td>
                                <td>${normalPurchase.buyer}</td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">数量:</td>
                                <td>${normalPurchase.quantity}</td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">金额:</td>
                                <td>[@macro.displayMoney value=normalPurchase.amount!''/]</td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">用途:</td>
                                <td>${normalPurchase.description!""}</td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">购货单位名称:</td>
                                <td>${normalPurchase.company!""}</td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">采购日期:</td>
                                <td>
                                [@macro.displayDate value=normalPurchase.purchaseTime!""/]
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