$(function(){
    $("INPUT[id$=impuesto_excedente]").keyup(function(e){
        ajustarCampoPorciento($(this));
    });
    function ajustarCampoPorciento(input){
        var valor = parseFloat(input.val());
        if(isNaN(valor) || valor < 0){
          valor = new Number(0);        
        }else  
        if(valor > 100){
          valor = new Number(100);
        }
        valor = new Number(valor).toFixed(2);        
        input.val(valor);
    }
});