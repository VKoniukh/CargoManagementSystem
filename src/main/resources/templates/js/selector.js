$('#OrderRate.BASE').on('change',function()
{
    var divClass = $(this).val();
    $(".op").hide();
    $("."+divClass).slideDown('medium');
});