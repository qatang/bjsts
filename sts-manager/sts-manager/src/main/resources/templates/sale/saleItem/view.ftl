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
                        <table id="simple-table" class="table table-striped table-bordered table-hover table-fixed-head" style="margin-top:5px;">
                            <thead>
                            <tr>
                                <th class="center">项目编码</th>
                                <th class="center">记录人</th>
                                <th class="center">追踪时间</th>
                                <th class="center">描述</th>
                            </tr>
                            </thead>
                            <tbody>
                            [#if planTraceList?has_content]
                                [#list planTraceList as planTrace]
                                <tr>
                                    <td class="center">${planTrace.planNo}</td>
                                    <td class="center">${planTrace.realName}</td>
                                    <td class="center">[@macro.displayDate value=planTrace.traceTime!""/]</td>
                                    <td class="center">${planTrace.description}</td>
                                </tr>
                                [/#list]
                            [/#if]
                            </tbody>
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