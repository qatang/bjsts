[#ftl strip_whitespace=true]

[#macro pagination page]
    [#local range = 4]
    [#local begin = 0]
    [#local end = 0]
    [#local pageNumber = page.number]
    [#local totalPages = page.totalPages]
    [#if range gte totalPages]
        [#local begin = 0]
        [#local end = totalPages - 1]
    [#else]
        [#local avg = range/2]
        [#if (pageNumber - avg) lt begin]
            [#local begin = begin]
        [#else]
            [#local begin = pageNumber - avg ]
        [/#if]
        [#local end = begin + range ]
        [#if end gte totalPages]
            [#local end = totalPages - 1]
            [#local begin = end - range]
        [/#if]
    [/#if]

    [#if end lt 0]
        [#local end = 0]
    [/#if]

    <div class="col12">
        <form id="pageForm" class="form-inline" method="get">
            <input id="page" type="hidden" name="page" />
            <input id="sort" type="hidden" name="sort" />
            <a href="javascript:void(0);" onclick="goPage(0);">首页</a>
            [#list begin..end as i]
                [#if page.number == i]
                    <span class="selected">${i + 1}</span>
                    [#else]
                        <a href="javascript:void(0);" onclick="goPage(${i});">${i + 1}</a>
                [/#if]
            [/#list]
            <a href="javascript:void(0);" onclick="goPage(${page.totalPages - 1});">尾页</a>
            <span>共${page.totalPages}页，每页${page.size}条，共${page.totalElements}条</span>
            <input id="inputPage" type="number" min="1" style="width:50px;height:21px;"/>
            <a class="btn-mini btn-primary" style="height:22px;cursor:pointer;text-decoration:none;" onclick="goInputPage();">Go</a>
        </form>
    </div>

    <script type="text/javascript">
        var goInputPage = function () {
            if ($("#inputPage").val()) {
                goPage($("#inputPage").val() - 1);
            }
        };
        var goPage = function(page) {
            var Request = GetRequest();
            Request["page"] = page;
            var param = GeneParam(Request);

            var url = location.href.split("?")[0];
            url = url.concat("?").concat(param);
            window.location = url;
        };

        function GetRequest() {
            var url = location.search; //获取url中"?"符后的字串
            var theRequest = new Array();
            if (url.indexOf("?") != -1) {
                var str = url.substr(1);
                var strs = str.split("&");
                for(var i = 0; i < strs.length; i ++) {
                    theRequest[strs[i].split("=")[0]]=(strs[i].split("=")[1]);
                }
            }
            return theRequest;
        }

        function GeneParam(request) {
            var url = "";
            var i = 0;
            for(var key in request){
                if (i != 0) {
                    url = url.concat("&");
                }
                url = url.concat(key).concat("=").concat(request[key]);
                i++;
            }
            return url;
        }
    </script>
[/#macro]

[#macro datetimePicker name id="" value="" placeholder="" style="" pattern="" classes="form-control datetime-picker" required=false readonly=true]
    [#if !id?has_content][#local id=name][/#if]
    [#if !pattern?has_content][#local pattern="yyyy-MM-dd HH:mm:ss"][/#if]
    [#if value?has_content][#local value = value?string("${pattern}")][/#if]
    <div class="input-group">
        <span class="input-group-addon"><i class="fa fa-calendar bigger-110"></i></span>
        <input class="${classes}" id="${id}" name="${name}" value="${value}" type="text" [#if readonly]readonly="readonly"[/#if] [#if pattern?has_content]pattern="${pattern}"[/#if] placeholder="这里输入${placeholder}" style="${style}" [#if required]required=""[/#if]/>
    </div>
[/#macro]

[#macro datePicker name id="" value="" placeholder="" style="" pattern="" classes="form-control date-picker" required=false readonly=true]
    [#if !id?has_content][#local id=name][/#if]
    [#if !pattern?has_content][#local pattern="yyyy-MM-dd"][/#if]
    [#if value?has_content][#local value = value?string("${pattern}")][/#if]
<div class="input-group">
    <span class="input-group-addon"><i class="fa fa-calendar bigger-110"></i></span>
    <input class="${classes}" id="${id}" name="${name}" value="${value}" type="text" [#if readonly]readonly="readonly"[/#if] [#if pattern?has_content]pattern="${pattern}"[/#if] placeholder="这里输入${placeholder}" style="${style}" [#if required]required=""[/#if]/>
</div>
[/#macro]

[#macro dateRange fromName toName fromValue="" toValue="" format="yyyy-MM-dd" placeholder="" readonly=true required=false]
<div class="input-daterange input-group">
    <input type="text" class="input-sm form-control date-picker" name="${fromName}" [#if fromValue?has_content]value="${fromValue?string(format)}"[/#if] [#if readonly]readonly="readonly"[/#if] [#if required]required=""[/#if] placeholder="开始${placeholder}" />
    <span class="input-group-addon">
            <i class="fa fa-exchange"></i>
        </span>
    <input type="text" class="input-sm form-control date-picker" name="${toName}" [#if toValue?has_content]value="${toValue?string(format)}"[/#if] [#if readonly]readonly="readonly"[/#if] [#if required]required=""[/#if] placeholder="结束${placeholder}" />
</div>
[/#macro]

[#macro datetimeRange fromName toName fromValue="" toValue="" format="yyyy-MM-dd HH:mm:ss" placeholder="" readonly=true required=false]
    <div class="input-daterange input-group">
        <input type="text" class="input-sm form-control datetime-picker" name="${fromName}" [#if fromValue?has_content]value="${fromValue?string(format)}"[/#if] [#if readonly]readonly="readonly"[/#if] [#if required]required=""[/#if] placeholder="开始${placeholder}" />
        <span class="input-group-addon">
            <i class="fa fa-exchange"></i>
        </span>
        <input type="text" class="input-sm form-control datetime-picker" name="${toName}" [#if toValue?has_content]value="${toValue?string(format)}"[/#if] [#if readonly]readonly="readonly"[/#if] [#if required]required=""[/#if] placeholder="结束${placeholder}" />
    </div>
[/#macro]

[#macro select name dataList classes="chosen-select form-control" id="" dataPlaceholder="请选择" value="" style="" header=true]
    [#if !id?has_content][#local id=name][/#if]
    <select class="${classes}" id="${id}" name="${name}" data-placeholder="${dataPlaceholder}" style="${style}">
        [#if header]
            <option value=""></option>
        [/#if]

        [#if dataList?has_content]
            [#list dataList as data]
                <option value="${data.id?c}" [#if value?has_content && data.id == value]selected[/#if]>${data.getName()}</option>
            [/#list]
        [/#if]
    </select>
[/#macro]

[#macro selectEnum name enumObj id="" dataList="" ignore="" classes="chosen-select form-control" dataPlaceholder="请选择" style="vertical-align:top;width:98%;" header=true]
    [#if !id?has_content][#local id=name][/#if]
    [#if enumObj?has_content][#local enumObjValue = enumObj.value][/#if]
    <select class="${classes}" id="${id}" name="${name}" data-placeholder="${dataPlaceholder}" style="${style}">
        [#if header]
            <option value=""></option>
        [/#if]

        [#if dataList?has_content]
            [#list dataList as data]
                [#if !ignore?contains(data.value?string)]
                    <option value="${data.value?c}" [#if enumObjValue?has_content && data.value == enumObjValue]selected[/#if]>${data.getName()}</option>
                [/#if]
            [/#list]
        [/#if]
    </select>
[/#macro]

[#macro errors]
    [#if bindingResult?has_content]
        [#list bindingResult as error]
            <div class="block clearfix">
                <div class="hr-8"></div>
                <div class="alert alert-danger no-margin">
                    <button type="button" class="close" data-dismiss="alert">
                        <i class="ace-icon fa fa-times"></i>
                    </button>
                    <i class="ace-icon fa fa-times bigger-120"></i>
                    ${error.defaultMessage!''}
                </div>
                <div class="hr-8"></div>
            </div>
        [/#list]
    [/#if]
[/#macro]

[#macro errorMsg classes="alert alert-danger no-margin"]
    [#if errorMessage?has_content]
        <div class="block clearfix">
            <div class="hr-8"></div>
            <div class="${classes}">
                <button type="button" class="close" data-dismiss="alert">
                    <i class="ace-icon fa fa-times"></i>
                </button>
                <i class="ace-icon fa fa-times bigger-120"></i>
                ${errorMessage}
            </div>
            <div class="hr-8"></div>
        </div>
    [/#if]
[/#macro]

[#macro successMsg close=true]
    [#if successMessage?has_content]
        <div class="block clearfix">
            <div class="hr-8"></div>
            <div class="alert alert-success no-margin">
                [#if close]
                    <button type="button" class="close" data-dismiss="alert">
                        <i class="ace-icon fa fa-times"></i>
                    </button>
                [/#if]
                <i class="ace-icon fa fa-check green"></i>
                ${successMessage}
            </div>
            <div class="hr-8"></div>
        </div>
    [/#if]
[/#macro]

[#macro warningMsg content="" class="alert alert-warning no-margin" close=true]
    [#if content?has_content || warningMessage?has_content]
    <div class="block clearfix">
        <div class="hr-8"></div>
        <div class="${class}">
            [#if close]
                <button type="button" class="close" data-dismiss="alert">
                    <i class="ace-icon fa fa-times"></i>
                </button>
            [/#if]
            [#if content?has_content]
                ${content}
            [#else]
                ${warningMessage}
            [/#if]
        </div>
        <div class="hr-8"></div>
    </div>
    [/#if]
[/#macro]

[#macro displayNumber value]
    [#if value?has_content]
        ${value?c}
    [/#if]
[/#macro]

[#macro displayDateTime value]
    [#if value?has_content]
        ${value?string("yyyy-MM-dd HH:mm:ss")}
    [/#if]
[/#macro]

[#macro displayDate value]
    [#if value?has_content]
    ${value?string("yyyy-MM-dd")}
    [/#if]
[/#macro]

[#macro displayMoney value multiple=100 format=",##0.00"]
    [#if value?has_content]
        ${(value/multiple)?string("${format}")}
    [/#if]
[/#macro]

[#macro displayPercentage value format="##.##"]
    [#if value?has_content]
        ${value?string("${format}")}
    [/#if]
[/#macro]

[#macro displayFile document]
    [#if document?has_content]
        <a href="${ctx}/document/download/${document.id}" target="_blank">${document.name}</a>
    [/#if]
[/#macro]

[#macro inputText name value id="" type="text" maxLength="" placeholder="" pattern="" required=true displayPlaceholder=true style="width:98%;" readonly=false]
    [#if !id?has_content][#local id=name][/#if]
    <input id="${id}" type="${type}" name="${name}" value="${value}" [#if maxLength?has_content]maxlength="${maxLength}"[/#if] [#if displayPlaceholder]placeholder="这里输入${placeholder}"[/#if] [#if pattern?has_content]pattern="${pattern}"[/#if] [#if required]required=""[/#if] style="${style}" [#if readonly]readonly=""[/#if]/>
[/#macro]

[#macro inputMoney name value id="" multiple=100 placeholder="" required=true displayPlaceholder=true pattern="^(\\d+|[1-9])(.\\d{0,2})?$" readonly=false]
    [#if value?has_content]
        <input id="${id}" type="text" [#if pattern?has_content]pattern="${pattern}"[/#if] name="${name}" value="${(value/multiple)?string("0.##")}" [#if displayPlaceholder]placeholder="这里输入${placeholder}"[/#if] [#if required]required=""[/#if] [#if readonly]readonly=""[/#if] style="width:98%;"/>
    [#else]
        <input id="${id}" type="text" [#if pattern?has_content]pattern="${pattern}"[/#if] name="${name}" [#if displayPlaceholder]placeholder="这里输入${placeholder}"[/#if] [#if required]required=""[/#if] [#if readonly]readonly=""[/#if] style="width:98%;"/>
    [/#if]
[/#macro]

[#macro inputPercentage name value placeholder="" required=true displayPlaceholder=true pattern="^\\d{0,2}(.\\d{0,2})?$"]
    [#if value?has_content]
        <input type="text" [#if pattern?has_content]pattern="${pattern}"[/#if] name="${name}" value="${value?string("0.##")}" [#if displayPlaceholder]placeholder="这里输入${placeholder}"[/#if] [#if required]required=""[/#if] style="width:98%;"/>
    [#else]
        <input type="text" [#if pattern?has_content]pattern="${pattern}"[/#if] name="${name}" [#if displayPlaceholder]placeholder="这里输入${placeholder}"[/#if] [#if required]required=""[/#if] style="width:98%;"/>
    [/#if]
[/#macro]

[#macro inputNumber name value type="number" placeholder="" max="" min="" required=true displayPlaceholder=true]
    [#if value?has_content]
        <input type="${type}" name="${name}" value="${value?c}" max="${max}" min="${min}" [#if displayPlaceholder]placeholder="这里输入${placeholder}"[/#if] [#if required]required=""[/#if] style="width:98%;"/>
    [#else]
        <input type="${type}" name="${name}" max="${max}" min="${min}" [#if displayPlaceholder]placeholder="这里输入${placeholder}"[/#if] [#if required]required=""[/#if] style="width:98%;"/>
    [/#if]
[/#macro]