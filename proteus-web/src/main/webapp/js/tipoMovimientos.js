/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function actualizarClases() {
    var data = JSON.parse($('TEXTAREA[id$=clases_json]').val());
    var clases = data.clases;
    var clase = data.clase;
    var grupo = data.grupo;
    var seleccion = data.seleccion;
    var selectClases = $('TH[id$=columna_clase]').find('SELECT[id$=filter]');
    $(selectClases).html('');
    $('<OPTION>', {value: '',html: seleccion}).appendTo($(selectClases));
    for (var i = 0; i < clases.length; i++) {       
        $('<OPTION>', {value: clases[i].id, html: clases[i].name}).appendTo($(selectClases));
    }
    if(selectClases !== null){
        $(selectClases).val(clase);
    }
    if(grupo !== null){
        var selectGrupos = $('TH[id$=columna_grupo]').find('SELECT[id$=filter]');
        $(selectGrupos).val(grupo);
    }
}
