function recalcular399() {
    var l399 = $("INPUT[id$=liquidacion_399]");
    var l301 = parseFloat($("INPUT[id$=liquidacion_301]").val());
    var l303 = parseFloat($("INPUT[id$=liquidacion_303]").val());
    var l305 = parseFloat($("INPUT[id$=liquidacion_305]").val());
    var l307 = parseFloat($("INPUT[id$=liquidacion_307]").val());
    var l351 = parseFloat($("INPUT[id$=liquidacion_351]").val());
    var l353 = parseFloat($("INPUT[id$=liquidacion_353]").val());
    var l361 = parseFloat($("INPUT[id$=liquidacion_361]").val());
    var l363 = parseFloat($("INPUT[id$=liquidacion_363]").val());
    var l365 = parseFloat($("INPUT[id$=liquidacion_365]").val());
    var l367 = parseFloat($("INPUT[id$=liquidacion_367]").val());
    var l369 = parseFloat($("INPUT[id$=liquidacion_369]").val());
    var l371 = parseFloat($("INPUT[id$=liquidacion_371]").val());
    var l373 = parseFloat($("INPUT[id$=liquidacion_373]").val());
    var l381 = parseFloat($("INPUT[id$=liquidacion_381]").val());
    var l399_val = parseFloat(l301 + l303 + l305 + l307);
    l399_val = parseFloat(l399_val - (l351 + l353 + l361 + l363 + l365 + l367 + l369 + l371 + l373));
    l399_val = parseFloat(l399_val + l381);
    l399.val(l399_val);
    formatToFixed2(l399);
}
function recalcular349() {
    var l349 = $("INPUT[id$=liquidacion_349]");
    var l301 = parseFloat($("INPUT[id$=liquidacion_301]").val());
    var l303 = parseFloat($("INPUT[id$=liquidacion_303]").val());
    var l305 = parseFloat($("INPUT[id$=liquidacion_305]").val());
    var l381 = parseFloat($("INPUT[id$=liquidacion_381]").val());
    l349.val(parseFloat(l301 + l303 + l305 + l381));
    formatToFixed2(l349);
}
recalcular399();
recalcular349();

$("INPUT[id*=liquidacion_]").each(function () {
    formatToFixed2(this);
});

$("INPUT[id*=liquidacion_]").keyup(function (e) {
    e.preventDefault();
    recalcular399();
    recalcular349();
    formatToFixed2(this);
});

function start() {
    statusDialog.show();
}

function stop() {
    statusDialog.hide();
}

function formatToFixed2(e) {
    var thisE = $(e);
    thisE.val(new Number(thisE.val()).toFixed(2));
}