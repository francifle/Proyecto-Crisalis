$(document).ready(function() {
    let param = "";
    let href = "";
    $('#btnLogin').on('click', function() {
	param = $("#mail").val() + "-" + $("#pass").val(); 
	href = "http://localhost:8080/LoginRest/checkUser/" + param;
	console.log(param);
        $.ajax({
            url: href,
            type: 'get',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            async: false,
            success: function(data) {
                if (data == true)
                {
                    $('#loginForm').submit();
                }
                else
                {
                    $("#sinUsuarioModal").modal("show");
                }
            }
        });
    });
});