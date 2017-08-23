[#ftl strip_whitespace=true]
[#import "${ctx}/common/macroes.ftl" as macro]
<!DOCTYPE html>
<html>
<head>
[#include "${ctx}/common/head.ftl"/]
    <style>
        body, div, ul, li, h4, a,span,p{padding:0;margin:0; list-style:none; font:normal 14px/30px Arial,"微软雅黑";}
        a{ text-decoration:none;}
        #attend_wrap{ width:100%; height:100%; padding-bottom:15px; width:1200px;}
        #attend_tab{ width:100%; height:40px; background:#3bb4f2; display:none;}
        #attend_tab a{ width:10%; height:40px; line-height:40px; text-align:center; padding:0 10px; display:block; float:left; color:#fff; background:#2da1dc; border-radius:3px 3px 0 0;}
        #attend_tab a:first-child{ margin-left:15px;}
        #attend_tab a.active{ background:#fff; color:#2da1dc;}

        #attend_b{ width:100%; height:100%; padding:15px 0; margin-left:0;}
        .bor_r{ border-right:#CCC solid 1px;}
        .attend_count{ width:94%; height:70px; border:#ccc solid 2px; margin:0 3%; border-radius:5px;}
        #attend_b table{ width:100%; text-align:center;table-layout:fixed;border-collapse: collapse;}
        #attend_b .at_c_name{ width:100%; height:26px; line-height:26px; color:#fff;border-radius:3px 3px 0 0;}
        #attend_b .at_c_num{ font-size:28px; color:#292929; height:44px; line-height:44px;}
        .at_orange{ background:#f29622;}
        .at_blue{ background:#25aadb;}
        .at_green{ background:#7cb924;}
        .at_pink{ background:#ec8943;}
        .at_grass{ background:#66d38f;}
        .at_purple{background:#8590ea;}
        .at_sky{ background:#54cbf2;}
        .data_orange{ color:#f29622; border-color:#f29622;}
        .data_blue{ color:#25aadb;border-color:#25aadb;}
        .data_green{ color:#7cb924;border-color:#7cb924;}
        .data_pink{ color:#ec8943;border-color:#ec8943;}
        .data_grass{ color:#66d38f;border-color:#66d38f;}
        .data_purple{color:#8590ea;border-color:#8590ea;}
        .data_sky{ color:#54cbf2;border-color:#54cbf2;}
        .data_today{ border-color:#999; color:#333; }

        #kalendar{ border:#ccc solid 1px; margin:15px auto;border-collapse: collapse}
        #kalendar td,#kalendar th{ border:1px #ccc solid; padding:5px;  vertical-align: top;}
        #kalendar td{ height:88px;}
        #kalendar th{ text-align:center; background:#f8f8f8;}
        .att_day_false{ color:#ccc;}
        .att_day_true{ color:#444; font-weight:bold;}
        #kalendar td p{ height:20px; line-height:20px; font-size:12px; text-align:right;}
        #kalendar td a{ width:91%; height:50px; margin:0 2% 5px 2%; display:block; line-height:50px; text-decoration:none; border-width:2px; border-style:solid; cursor:pointer;}
        #kalendar td a:hover{border:solid 2px #2d2d2d; color:#2d2d2d;}
        #kalendar td .data_null{ border:solid 2px #ccc; color:#999;}
        #kalendar td .data_normal{ border:solid 2px #999; color:#333;}
        #kalendar td a div{ height:24px; line-height:22px;}
        #kalendar td a .data_txt{font-weight:bold;}

        #date_ch{ width:100%; height:80px; background:#2da1dc; border-radius:5px; text-align:center; color:#fff;}
        #date_ch .d_c_name{ font-weight:bold; font-size:16px;line-height:40px;}
        #date_ch .d_c_form select{ width:65px; height:26px; line-height:26px;color:#333; border:none; border-radius:3px; margin:0 5px;}
        #date_no{ margin:15px 0;}
        #date_no h3{ font-size:16px; font-weight:bold;}
        #date_no h4{ font-weight:bold;}
        #date_no p{ line-height:28px;}

        .col-md-9{ width:73%; float:left; padding:0 1%;}
        .col-md-3{ width:22%; float:left; padding:0 1%;}
        .col-md-3:after{ clear:both; content:'';}
    </style>
</head>
<body class="no-skin">
<div class="main-container" id="main-container">
    <div class="main-content">
        <div class="main-content-inner">
            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">
                        <div id="attend_wrap" class="container">
                            <div id="attend_tab"> <a href="#">考勤签到</a> <a href="#" class="active">我的考勤</a> </div>
                            <div id="attend_a" class="hidden"></div>
                            <div id="attend_b" class="row">
                                <ul class="col-md-9 bor_r">
                                    <table cellpadding="0" cellspacing="0">
                                        <tr>
                                            <td><div class="attend_count">
                                                <p class="at_c_name at_orange">迟到</p>
                                                <p class="at_c_num">2</p>
                                            </div></td>
                                            <td><div class="attend_count">
                                                <p class="at_c_name at_blue">早退</p>
                                                <p class="at_c_num">2</p>
                                            </div></td>
                                            <td><div class="attend_count">
                                                <p class="at_c_name at_green">旷工</p>
                                                <p class="at_c_num">2</p>
                                            </div></td>
                                            <td><div class="attend_count">
                                                <p class="at_c_name at_pink">未签到</p>
                                                <p class="at_c_num">2</p>
                                            </div></td>
                                            <td><div class="attend_count">
                                                <p class="at_c_name at_grass">未签退</p>
                                                <p class="at_c_num">2</p>
                                            </div></td>
                                            <td><div class="attend_count">
                                                <p class="at_c_name at_purple">加班</p>
                                                <p class="at_c_num">2</p>
                                            </div></td>
                                            <td><div class="attend_count">
                                                <p class="at_c_name at_sky">请假</p>
                                                <p class="at_c_num">2</p>
                                            </div></td>
                                        </tr>
                                    </table>
                                    <li id="rili_wrap"></li>
                                </ul>
                                <ul class="col-md-3">
                                    <li id="date_no">
                                        <h3>${staff.realName}</h3>[#--
                                        <h4>管理员说明</h4>
                                        <p>这是考勤系统这是考勤系统这是考勤系统这是考勤系统这是考勤系统这是考勤系统这是考勤系统</p>--]
                                    </li>
                                    <li id="date_ch">
                                        <p class="d_c_name">选择日期</p>
                                        <p class="d_c_form" id="rili_se"></p>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-6">
                    </div>
                    <div class="col-xs-1">
                        <a class="btn btn-mini btn-primary" href="${ctx}/attendance/list">返回</a>
                    </div>
                    <div class="col-xs-5">
                    </div>
                </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

[#include "${ctx}/common/footer.ftl"/]

<script>
    $(function(){
        /*************    方法     **************/
        //判断是否是闰年,计算每个月的天数
        function leapYear(year){
            var isLeap = year%100==0 ? (year%400==0 ? 1 : 0) : (year%4==0 ? 1 : 0);
            return new Array(31,28+isLeap,31,30,31,30,31,31,30,31,30,31);
        }
        //获得某月第一天是周几
        function firstDay(day){
            return day.getDay();
        }

        //获得当天的相关日期变量
        function dateNoneParam(){
            var day = new Date();
            var today = new Array();
            today['year'] = day.getFullYear();
            today['month'] = day.getMonth();
            today['date'] = day.getDate();
            today['week'] = day.getDay();
            today['firstDay'] = firstDay(new Date(today['year'],today['month'],1));
            return today;
        }

        //获得所选日期的相关变量
        function dateWithParam(year,month){
            var day = new Date(year,month);
            var date = new Array();
            date['year'] = day.getFullYear();
            date['month'] = day.getMonth();
            date['firstDay'] = firstDay(new Date(date['year'],date['month'],1));
            return date;
        }

        //设置考勤状态html
        var attend= new Array();
        attend[0]="<a class='data_null'>无记录</a>"//无记录
        attend[1]="<a class='data_orange'><div class='data_txt'>上午</div></a>"//考勤正常
        attend[2]="<a class='data_blue'><div class='data_txt'>下午</div></a>"//考勤正常
        attend[3]="<a class='data_sky'><div class='data_txt'>请假</div></a>"//请假
        //生成日历代码 的方法
        //参数依次为 年 月
        function selectCode(codeYear,codeMonth){
            select_html = "<span id='year'><select name='year'>";
            //年 选择
            for(var i=2016;i<=codeYear+yearfloor;i++){
                if(i == codeYear){
                    select_html += "<option value='"+i+"' selected='true'>"+i+"</option>";
                }else{
                    select_html += "<option value='"+i+"'>"+i+"</option>";
                }
            }

            select_html += "</select>年</span>\n<span id='month'><select name='year'>";

            //月 选择
            for(var j=0;j<=11;j++){
                if(j == codeMonth){
                    select_html += "<option value='"+j+"' selected='true'>"+month[j]+"</option>";
                }else{
                    select_html += "<option value='"+j+"'>"+month[j]+"</option>";
                }
            }
            select_html += "</select>月</span>\n";
            return select_html;
        }
        //参数依次为 年 月 日 当月第一天(是星期几)
        function kalendarCode(codeYear,codeMonth,codeDay,codeFirst){
            kalendar_html="<table id='kalendar' class='table' cellpadding='0' cellspacing='0'><thead><tr id='week'><th>周日</th><th>周一</th><th>周二</th><th>周三</th><th>周四</th><th>周五</th><th>周六</th></tr></thead><tbody id='day'>";
            //日 列表
            var dqxy=1;
            for(var m=0;m<6;m++){//日期共 4-6 行
                kalendar_html += "<tr id='rili_data' class='dayList dayListHide"+m+"'>\n";
                for(var n=0;n<7;n++){//列
                    if((7*m+n) < codeFirst && codeMonth>=1){//非一月前月日期
                        kalendar_html += "<td><p class='att_day_false'>"+((7*m+n-codeFirst)+monthDays[codeMonth-1]+1)+"</p></td>";
                    }
                    else if((7*m+n) < codeFirst && codeMonth==0){//一月前月日期
                        kalendar_html += "<td><p  class='att_day_false'>"+((7*m+n-codeFirst)+monthDays[11]+1)+"</p></td>";
                    }
                    else if((7*m+n) >= (codeFirst+monthDays[codeMonth])){//下月日期
                        kalendar_html += "<td><p  class='att_day_false'>"+(dqxy++)+"</p></td>";
                    }
                    else{//本月日期
                        //if((7*m+n+1-codeFirst)<codeDay){
                        kalendar_html += "<td><p  class='att_day_true'>"+(7*m+n+1-codeFirst)+"</p>"+attend[parseInt(Math.random()*(2+1))]+attend[parseInt(Math.random()*(2+1))]+"</td>";
                        //}
                        //else if((7*m+n+1-codeFirst)==codeDay){
                        //kalendar_html += "<td><p  class='att_day_true'>"+(7*m+n+1-codeFirst)+"</p>"+attend[9]+"</td>";
                        //}
                        //else{
                        //	kalendar_html += "<td><p  class='att_day_true'>"+(7*m+n+1-codeFirst)+"</p></td>";
                        //	}
                    }
                }
                kalendar_html += "</tr>\n";
            }
            kalendar_html += "</tbody></table>";
            return kalendar_html;
        }

        //年-月select框改变数值 的方法
        //参数依次为 1、操作对象(年下拉菜单 或 月下拉菜单) 2、被选中的下拉菜单值
        function y_mChange(obj,stopId){
            obj.val(stopId);
        }

        //修改日历列表 的方法
        //参数依次为 操作对象(每一天) 月份 修改后的第一天是星期几 修改后的总天数 当天的具体日期
        function dateChange(dateObj,dateMonth,dateFirstDay,dateTotalDays,dateCurrentDay){
            dateLine = 6;
            $("#rili_data td a").remove();
            if(dateTotalDays < dateCurrentDay){
                dateCurrentDay = dateTotalDays;
            }
            var xysj=1;
            for(var i=0;i<7*dateLine;i++){
                if(i < dateFirstDay && dateMonth>=1){//非一月上月日期
                    dateObj.eq(i).text((i+1-dateFirstDay)+monthDays[dateMonth-1]);
                    dateObj.eq(i).attr('class','att_day_false');
                }
                else if(i < dateFirstDay && dateMonth==0){//一月上月日期
                    dateObj.eq(i).text((i+1-dateFirstDay)+monthDays[11]);
                    dateObj.eq(i).attr('class','att_day_false');
                }
                else if(i>(dateTotalDays+dateFirstDay-1)){//下月日期
                    dateObj.eq(i).text(xysj);
                    dateObj.eq(i).attr('class','att_day_false');
                    xysj++;
                }
                else{
                    dateObj.eq(i).text(i+1-dateFirstDay);
                    dateObj.eq(i).attr('class','att_day_true');
                    dateObj.eq(i).after(attend[parseInt(Math.random()*(8+1))]);
                }
            }
        }

        /*************    缓存节点和变量     **************/
        var rili_location = $('#rili_wrap');//日历代码的位置
        var rili_select=$("#rili_se");
        var select_html = ''; //年月选择
        var kalendar_html = '';//记录日历自身代码 的变量
        var yearfloor = 0;//选择年份从1980到当前时间的后0年

        var someDay = dateNoneParam();//修改后的某一天,默认是当天
        var yearChange = someDay['year'];//改变后的年份，默认当年
        var monthChange = someDay['month'];//改变后的年份，默认当月

        /*************   将日历代码放入相应位置，初始时显示此处内容      **************/

                //获取时间，确定日历显示内容
        var today = dateNoneParam();//当天
        /*月-日 设置*/
        var month = new Array('1','2','3','4','5','6','7','8','9','10','11','12');
        var monthDays = leapYear(today['year']);//返回数组，记录每月有多少天
        var weekDay = new Array('日','一','二','三','四','五','六');
        kalendar_html = kalendarCode(today['year'],today['month'],today['date'],today['firstDay']);
        select_html = selectCode(today['year'],today['month']);
        rili_location.html(kalendar_html);
        rili_select.html(select_html);

        /*************   js写的日历代码中出现的节点     **************/
        var selectYear = $('#rili_se #year select');//选择年份列表
        var listYear = $('#rili_se #year select option');//所有可选年份
        var selectMonth = $('#rili_se #month  select');//选择月份列表
        var listMonth = $('#rili_se #month select option');//所有可选月份
        var dateLine = Math.ceil((monthDays[today['month']]+today['firstDay'])/7);
        //当前日历中有几行日期，默认是 当年当月
        var dateDay = $('#kalendar tr.dayList td p');//日历中的每一天


        /***************************/
        // 年 选择事件
        selectYear.bind('change',function(){
            //获得年份
            yearChange = $(this).val();
            //修改年份
            y_mChange(selectYear,yearChange);
            //重新获得 每月天数
            monthDays = leapYear(yearChange);
            //新 年-月 下的对象信息
            someDay = dateWithParam(yearChange,monthChange);
            //修改 日期 列表
            dateChange(dateDay,someDay['month'],someDay['firstDay'],monthDays[someDay['month']],today['date']);
        });

        // 月 选择事件
        selectMonth.bind('change',function(){
            //获得 月
            monthChange = $(this).val();
            //修改月份
            y_mChange(selectMonth,monthChange);
            //新 年-月 下的对象信息
            someDay = dateWithParam(yearChange,monthChange);
            //修改 日期 列表
            dateChange(dateDay,someDay['month'],someDay['firstDay'],monthDays[someDay['month']],today['date']);
        });

    });
</script>
</html>