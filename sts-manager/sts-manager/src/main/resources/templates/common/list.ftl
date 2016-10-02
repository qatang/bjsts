<script>
    jQuery(function($) {
        $('.input-daterange input:eq(0)').datepicker({autoclose:true, clearBtn: true, format: 'yyyy-mm-dd 00:00:00'});
        $('.input-daterange input:eq(1)').datepicker({autoclose:true, clearBtn: true, format: 'yyyy-mm-dd 23:59:59'});
    });
</script>