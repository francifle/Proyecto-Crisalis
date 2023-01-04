$('#datepicker').datepicker({
    format: "dd/mm/yyyy",
    maxViewMode: 2,
    todayBtn: "linked",
    language: "es",
    autoclose: true,
    todayHighlight: true,
    beforeShowMonth: function(date){
          if (date.getMonth() == 8) {
            return false;
          }
        }
});