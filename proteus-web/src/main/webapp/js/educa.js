/**
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */

var ventanaReporte;

function validarCodigo(e) {
    tecla = (document.all) ? e.keyCode : e.which;
    if (validarTeclarGenericas(e))
        return true;
    patron = /[A-Za-z0-9_\-\.]/;
    te = String.fromCharCode(tecla);
    return patron.test(te);
}
function validarHora(e) {
    tecla = (document.all) ? e.keyCode : e.which;
    if (validarTeclarGenericas(e))
        return true;
    patron = /[0-9:]/;
    te = String.fromCharCode(tecla);
    return patron.test(te);
}

function clearInvalidFileMsg(wiget) {
    wiget.uploadContent.find("tr.ui-state-error").remove();
}

function validarLetras(e) {
    tecla = (document.all) ? e.keyCode : e.which;
    if (validarTeclarGenericas(e))
        return true;
    patron = /[A-Za-zÑñ ]/;
    te = String.fromCharCode(tecla);
    return patron.test(te);
}

function validarNumero(e) {
    tecla = (document.all) ? e.keyCode : e.which;
    if (validarTeclarGenericas(e))
        return true;
    patron = /[0-9]/;
    te = String.fromCharCode(tecla);
    return patron.test(te);
}

function validarNumeroDecimal(e) {
    tecla = (document.all) ? e.keyCode : e.which;
    if (validarTeclarGenericas(e))
        return true;
    patron = /[0-9\.,]/;
    te = String.fromCharCode(tecla);
    return patron.test(te);
}


function validarTeclarGenericas(e) {
    var teclap = (document.all) ? e.keyCode : e.which;
    if (teclap == 8 || e.keyCode == 8 || e.keyCode == 9 || e.keyCode == 16 || e.keyCode == 35 || e.keyCode == 36 || e.keyCode == 37 || e.keyCode == 39 || e.keyCode == 46) {
        return true;
    }
    if ((String.fromCharCode(e.keyCode)).toUpperCase() == "#" || (String.fromCharCode(e.keyCode)).toUpperCase() == "%" || (String.fromCharCode(e.keyCode)).toUpperCase() == "'") {
        return false;
    }
    return false;
}


/**
 *Se encarga de mostrar una ventana emergente
 */
function mostrarPopup(url, nombre) {
    if (ventanaReporte) {
        ventanaReporte.close();
    }
    ventanaReporte = window.open(url, "_bank", "height=550,width=800,location=0,toolbar=0,menubar=0,directories=0,resizable=yes, scrollbars=yes");
    if (window.focus) {
        ventanaReporte.focus();
    }
    return false;
}

function pulsar(e) {
    tecla = (document.all) ? e.keyCode : e.which;
    return (tecla != 13);
}

function mostrarError() {

    var er = document.getElementById("divError");
    var ly = document.getElementById("divLeyendaClave");
    ly.style.display = (ly.style.display != 'none' ? 'none' : '');

}


// version: beta
// created: 2005-08-30
// updated: 2005-08-31
// mredkj.com
// maxVal added by mplungjan 2012-04-14

function extractNumber(obj, decimalPlaces, allowNegative, maxVal)
{
    var temp = obj.value;

    // avoid changing things if already formatted correctly
    var reg0Str = '[0-9]*';
    if (decimalPlaces > 0) {
        reg0Str += '\\.?[0-9]{0,' + decimalPlaces + '}';
    } else if (decimalPlaces < 0) {
        reg0Str += '\\.?[0-9]*';
    }
    reg0Str = allowNegative ? '^-?' + reg0Str : '^' + reg0Str;
    reg0Str = reg0Str + '$';
    var reg0 = new RegExp(reg0Str);
    if (reg0.test(temp))
        return true;

    // first replace all non numbers
    var reg1Str = '[^0-9' + (decimalPlaces != 0 ? '.' : '') + (allowNegative ? '-' : '') + ']';
    var reg1 = new RegExp(reg1Str, 'g');
    temp = temp.replace(reg1, '');

    if (allowNegative) {
        // replace extra negative
        var hasNegative = temp.length > 0 && temp.charAt(0) == '-';
        var reg2 = /-/g;
        temp = temp.replace(reg2, '');
        if (hasNegative)
            temp = '-' + temp;
    }

    if (decimalPlaces != 0) {
        var reg3 = /\./g;
        var reg3Array = reg3.exec(temp);
        if (reg3Array != null) {
            // keep only first occurrence of .
            //  and the number of places specified by decimalPlaces or the entire string if decimalPlaces < 0
            var reg3Right = temp.substring(reg3Array.index + reg3Array[0].length);
            reg3Right = reg3Right.replace(reg3, '');
            reg3Right = decimalPlaces > 0 ? reg3Right.substring(0, decimalPlaces) : reg3Right;
            temp = temp.substring(0, reg3Array.index) + '.' + reg3Right;
        }
    }

    obj.value = temp;
}
function blockNonNumbers(obj, e, allowDecimal, allowNegative, maxVal)
{
    var key;
    var isCtrl = false;
    var keychar;
    var reg;
        
    if(window.event) {
        key = e.keyCode;
        isCtrl = window.event.ctrlKey
    }
    else if(e.which) {
        key = e.which;
        isCtrl = e.ctrlKey;
    }
    
    if (isNaN(key)) return true;
    
    keychar = String.fromCharCode(key);
    
    // check for backspace or delete, or if Ctrl was pressed
    if (key == 8 || isCtrl)
    {
        return true;
    }

    reg = /\d/;
    var val = obj.value;
    var isFirstN = allowNegative ? keychar == '-' && val.indexOf('-') == -1 : false;
    var isFirstD = allowDecimal ? keychar == '.' && val.indexOf('.') == -1 : false;
    var isFirstD = allowDecimal ? keychar == ',' && val.indexOf(',') == -1 : false;
    
    if (maxVal && !isNaN(val) && parseFloat(val)>maxVal) return false;
    return isFirstN || isFirstD || reg.test(keychar);
}
        