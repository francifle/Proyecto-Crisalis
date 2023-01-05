$(document).ready(function (){
    try {
        var dni = $('.dni');
        //var segundoTrimestre = $('.segundoTrimestre');
        for (var i = 0; i < dni.length; i++) {
			var val1 = dni.get(i).getInnerHTML().substring(0,2);
			var val2 = dni.get(i).getInnerHTML().substring(2,5);
			var val3 = dni.get(i).getInnerHTML().substring(5,8);
            dni.get(i).innerHTML = val1 + "." + val2 + "." + val3;
         }
         
        var cuit = $('.cuit');
        for (var i = 0; i < cuit.length; i++) {
			var val1 = cuit.get(i).getInnerHTML().substring(0,2);
			var val2 = cuit.get(i).getInnerHTML().substring(2,4);
			var val3 = cuit.get(i).getInnerHTML().substring(4,7);
			var val4 = cuit.get(i).getInnerHTML().substring(7,10);
			var val5 = cuit.get(i).getInnerHTML().substring(10,11);
            cuit.get(i).innerHTML = val1 + "-" + val2 + "." + val3 + "." + val4 + "-" + val5;
         }
        
    } catch (error) {
        console.log(error)
    }

});