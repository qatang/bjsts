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
                            <form action="${ctx}/invoice/list" method="post" name="invoiceForm" id="invoiceForm">
                                    <table style="margin-top:5px;">
                                        <tr>
                                            <td style="padding-left:2px;">[@macro.dateRange fromName="beginCreatedTime" toName="endCreatedTime" fromValue=invoiceSearchable.beginCreatedTime!'' toValue=invoiceSearchable.endCreatedTime!'' placeholder="开票日期"/]</td>
                                            <td style="vertical-align:top;padding-left:2px;">
                                                [@macro.selectEnum name="invoiceCategory" enumObj=invoiceSearchable.invoiceCategory!allInvoiceCategoryList[0] dataList=allInvoiceCategoryList /]
                                            </td>
                                            <td style="vertical-align:top;padding-left:2px;">
                                                [@macro.inputText name="planContent" value=invoiceSearchable.planContent!'' placeholder="项目内容" required=false/]
                                            </td>
                                            <td style="vertical-align:top;padding-left:2px;">
                                                [@macro.inputText name="customer" value=invoiceSearchable.customer!'' placeholder="客户名称" required=false/]
                                            </td>
                                            <td style="vertical-align:top;padding-left:2px;"><button class="btn btn-light btn-xs" title="检索" type="submit"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></button></td>
                                        </tr>
                                    </table>
                                </form>
                            </div>
                            <div class="col-xs-1">
                                <div style="float:right;margin-top:5px;">
                                    <a class="btn btn-xs btn-success" onclick="diag('新增发票', '${ctx}/invoice/create');">新增发票</a>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                [@macro.successMsg close=false /]
                                <table id="simple-table" class="table table-striped table-bordered table-hover table-fixed-head" style="margin-top:5px;">
                                    <thead>
                                        <tr>
                                            <th class="center">ID</th>
                                            <th class="center">项目编号</th>
                                            <th class="center">项目内容</th>
                                            <th class="center">发票类型</th>
                                            <th class="center">发票编号</th>
                                            <th class="center">客户名称</th>
                                            <th class="center">开票日期</th>
                                            <th class="center">开票金额</th>
                                            <th class="center">开票内容</th>
                                            <th class="center">抵扣日期</th>
                                            <th class="center">操作</th>
                                        </tr>
                                    </thead>

                                    <tbody>
                                        [#if page?? && page.content?has_content]
                                            [#list page.content as invoice]
                                            <tr>
                                                <td class="center"><a onclick="diag('发票查看', '${ctx}/invoice/view/${invoice.id}')" style="cursor:pointer;">${invoice.id}</a></td>
                                                <td class="center">${invoice.planNo!""}</td>
                                                <td class="center">${invoice.planContent!""}</a></td>
                                                <td class="center">${invoice.invoiceCategory.getName()}</td>
                                                <td class="center">${invoice.invoiceNo}</td>
                                                <td class="center">${invoice.customer}</td>
                                                <td class="center">[@macro.displayDate value=invoice.invoiceDate!""/]</td>
                                                <td class="center">[@macro.displayMoney value=invoice.amount!""/]</td>
                                                <td class="center">${invoice.content}</td>
                                                <td class="center">[@macro.displayDate value=invoice.deductionDate!""/]</td>
                                                <td class="center">
                                                        <a class="green" onclick="diag('发票修改', '${ctx}/invoice/update/${invoice.id}');" style="cursor: pointer;text-decoration:none;">
                                                            修改
                                                        </a>
                                                        <a class="red" href="${ctx}/invoice/disable/${invoice.id}" onclick="return confirm('确定要删除该发票吗?');">
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