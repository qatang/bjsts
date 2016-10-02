<!--[if !IE]> -->
<script type="text/javascript">
    window.jQuery || document.write("<script src='${ctx}/static/ace/js/jquery.js'>"+"<"+"/script>");
</script>
<!-- <![endif]-->
<!--[if IE]>
<script type="text/javascript">
 window.jQuery || document.write("<script src='${ctx}/static/ace/js/jquery1x.js'>"+"<"+"/script>");
</script>
<![endif]-->
<script type="text/javascript">
    if('ontouchstart' in document.documentElement) document.write("<script src='${ctx}/static/ace/js/jquery.mobile.custom.js'>"+"<"+"/script>");
</script>
<script src="${ctx}/static/ace/js/bootstrap.js"></script>
<script src="${ctx}/static/js/myjs/diag.js"></script>

<script src="${ctx}/static/ace/js/bootbox.js"></script>
<script src="${ctx}/static/ace/js/date-time/moment.js"></script>
<script src="${ctx}/static/ace/js/date-time/bootstrap-datepicker.js"></script>
<script src="${ctx}/static/ace/js/date-time/bootstrap-datetimepicker.js"></script>
<script src="${ctx}/static/ace/js/chosen.jquery.js"></script>
<script src="${ctx}/static/js/jquery.tips.js"></script>
<script src="${ctx}/static/js/jquery.cookie.js"></script>

<script>
    $(function() {
        //日期框
        $('.datetime-picker').datetimepicker({
            format: 'YYYY-MM-DD HH:mm:ss',
            use24hours: true,
            sideBySide: true,
            enabledDates: true
        });

        //下拉框
        if(!ace.vars['touch']) {
            $('.chosen-select').chosen({allow_single_deselect:true});
            $(window)
                    .off('resize.chosen')
                    .on('resize.chosen', function() {
                        $('.chosen-select').each(function() {
                            var $this = $(this);
                            $this.next().css({'width': $this.parent().width()});
                        });
                    }).trigger('resize.chosen');
            $(document).on('settings.ace.chosen', function(e, event_name, event_val) {
                if(event_name != 'sidebar_collapsed') return;
                $('.chosen-select').each(function() {
                    var $this = $(this);
                    $this.next().css({'width': $this.parent().width()});
                });
            });
            $('#chosen-multiple-style .btn').on('click', function(e){
                var target = $(this).find('input[type=radio]');
                var which = parseInt(target.val());
                if(which == 2) $('#form-field-select-4').addClass('tag-input-style');
                else $('#form-field-select-4').removeClass('tag-input-style');
            });
        }
        
        $('form').submit(function() {
            $(this).find('button[type=submit]').prop('disabled', true);
        });
    });
</script>