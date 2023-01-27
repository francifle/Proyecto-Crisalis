$(document).ready(function() {
 $("#deleteMultipleBtn").click(function(){
	let ids = "";
	let checkbox = "";
	for (var i = 0; i < $('.checkBoxDelete').length; i++) {
		checkbox = $(".checkBoxDelete")[i];
		if(checkbox.checked){
			ids += checkbox.value + ";";
		}
	}
	ids = ids.slice(0, -1);
	$("#deleteIds").val(ids);
})
})