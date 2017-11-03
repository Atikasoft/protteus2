/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function init() {
    var selectGrupos = $('TH[id$=columna_grupo]').find('SELECT[id$=filter]');
    var selectClases = $('TH[id$=columna_clase]').find('SELECT[id$=filter]');
    var selectMovimientos = $('TH[id$=columna_movimientos]').find('SELECT[id$=filter]');
    var numeroTramite = $('TH[id$=numero_tramite]').find('INPUT[id$=filter]');

    selectGrupos.change(function(e) {
        e.preventDefault();
        cargarGrupos();
        isSelect = true;
        filtroTabla();
    });

    numeroTramite.focus(function(e) {
        manualUpdateSelected = true;
        isSelect = false;
    });
    numeroTramite.blur(function(e) {
        manualUpdateSelected = false;
        isSelect = true;
    });

    selectClases.change(function(e) {
        e.preventDefault();
        cargarClases();
        isSelect = true;
        filtroTabla();
    });

    selectMovimientos.change(function(e) {
        e.preventDefault();
        cargarMovimiento();
        isSelect = true;
        filtroTabla();
    });

    actualizarClases();
}

var manualUpdateSelected = false;
var isSelect = false;

function actualizarTabla(){
    isSelect = true;
    manualUpdateSelected = true;
    filtroTabla();
}

function checkLoad() {
    if (manualUpdateSelected) {
        if (isSelect) {
            manualUpdateSelected = false;
        }
        return true;
    }
    return false;
}

function cargarGrupos() {
    var grupo = $('INPUT[id$=grupo_filtro_seleccionado]');
    var clase = $('INPUT[id$=clase_filtro_seleccionado]');
    var movimiento = $('INPUT[id$=movimiento_filtro_seleccionado]');

    var selectGrupos = $('TH[id$=columna_grupo]').find('SELECT[id$=filter]');

    movimiento.val(null);
    clase.val(null);

    grupo.val(selectGrupos.val());

    manualUpdateSelected = false;
}

function cargarClases() {
    var grupo = $('INPUT[id$=grupo_filtro_seleccionado]');
    var clase = $('INPUT[id$=clase_filtro_seleccionado]');
    var movimiento = $('INPUT[id$=movimiento_filtro_seleccionado]');

    var selectClases = $('TH[id$=columna_clase]').find('SELECT[id$=filter]');

    movimiento.val(null);
    grupo.val(null);

    clase.val(selectClases.val());
    manualUpdateSelected = false;
}

function cargarMovimiento() {
    var grupo = $('INPUT[id$=grupo_filtro_seleccionado]');
    var clase = $('INPUT[id$=clase_filtro_seleccionado]');
    var movimiento = $('INPUT[id$=movimiento_filtro_seleccionado]');

    var selectMovimientos = $('TH[id$=columna_movimientos]').find('SELECT[id$=filter]');

    grupo.val(null);
    clase.val(null);
    movimiento.val(selectMovimientos.val());
    manualUpdateSelected = false;
}


function actualizarClases() {
    var data = JSON.parse($('TEXTAREA[id$=clases_json]').val());
    var grupos = data.grupos;
    var clases = data.clases;
    var movimientos = data.movimientos;

    var clase = data.clase;
    var grupo = data.grupo;
    var movimiento = data.movimiento;

    var seleccion = data.seleccion;

    var selectGrupos = $('TH[id$=columna_grupo]').find('SELECT[id$=filter]');
    var selectClases = $('TH[id$=columna_clase]').find('SELECT[id$=filter]');
    var selectMovimientos = $('TH[id$=columna_movimientos]').find('SELECT[id$=filter]');

    $(selectGrupos).html('');
    $(selectClases).html('');
    $(selectMovimientos).html('');

    $('<OPTION>', {value: '', html: seleccion}).appendTo($(selectGrupos));
    $('<OPTION>', {value: '', html: seleccion}).appendTo($(selectClases));
    $('<OPTION>', {value: '', html: seleccion}).appendTo($(selectMovimientos));

    for (var i = 0; i < grupos.length; i++) {
        $('<OPTION>', {value: grupos[i].id, html: grupos[i].name}).appendTo($(selectGrupos));
    }
    for (var i = 0; i < clases.length; i++) {
        $('<OPTION>', {value: clases[i].id, html: clases[i].name}).appendTo($(selectClases));
    }
    for (var i = 0; i < movimientos.length; i++) {
        $('<OPTION>', {value: movimientos[i].id, html: movimientos[i].name}).appendTo($(selectMovimientos));
    }

    if (clase !== null) {
        $(selectClases).val(clase);
    }
    if (movimiento !== null) {
        $(selectMovimientos).val(movimiento);
    }
    if (grupo !== null) {
        $(selectGrupos).val(grupo);
    }
    manualUpdateSelected = true;
    filtroTablaDo();
}
