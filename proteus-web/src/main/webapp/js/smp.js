/**
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */

var ventanaReporte;

function validarCodigo(e) {
    tecla = (document.all) ? e.keyCode : e.which;
    if (validarTeclarGenericas(e)) return true;
    patron =/[A-Za-z0-9_\-\.]/;
    te = String.fromCharCode(tecla);
    return patron.test(te);
}

function validarNumero(e) {
    tecla = (document.all) ? e.keyCode : e.which;
    if (validarTeclarGenericas(e)) return true;
    patron =/[0-9]/;
    te = String.fromCharCode(tecla);
    return patron.test(te);
}


function validarTeclarGenericas(e){
    var teclap = (document.all) ? e.keyCode : e.which;
    if (teclap==8 || e.keyCode==8 || e.keyCode==9 || e.keyCode==16 || e.keyCode==35 || e.keyCode==36 || e.keyCode==37 || e.keyCode==39 || e.keyCode==46 ){
        return true;
    }
    if((String.fromCharCode(e.keyCode)).toUpperCase()=="#"||(String.fromCharCode(e.keyCode)).toUpperCase()=="%"||(String.fromCharCode(e.keyCode)).toUpperCase()=="'"){
        return false;
    }
    return false;
}


/**
 *Se encarga de mostrar una ventana emergente
 */
function mostrarPopup(url, nombre) {
    if(ventanaReporte){
        ventanaReporte.close();
    }
    ventanaReporte=window.open(url,"_bank", "height=550,width=800,location=0,toolbar=0,menubar=0,directories=0,resizable=yes");
    if (window.focus) {
        ventanaReporte.focus();
    }
    return false;
}

function pulsar(e) {
    tecla = (document.all) ? e.keyCode :e.which;
    return (tecla!=13);
}