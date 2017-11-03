/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
**/

CREATE TABLE sch_proteus.cotizaciones_iess_especiales
(
    id NUMERIC(19,0) NOT NULL IDENTITY,
    nombre VARCHAR(150) COLLATE Modern_Spanish_CI_AS NOT NULL,
    porcentaje_aporte_individual numeric(10,2) NULL,
    porcentaje_aporte_patronal numeric(10,2) NULL,
    fecha_creacion DATETIME NOT NULL,
    usuario_creacion VARCHAR(40) COLLATE Modern_Spanish_CI_AS NOT NULL,
    fecha_actualizacion DATETIME,
    usuario_actualizacion VARCHAR(40) COLLATE Modern_Spanish_CI_AS,
    vigente BIT DEFAULT 0,
    CONSTRAINT cotizaciones_iess_especiales_pk PRIMARY KEY (id)
);


INSERT INTO sch_proteus.menus (codigo, nombre, etiqueta, url, orden, menu_id, fecha_creacion, usuario_actualizacion, usuario_creacion, vigente, tipo) 
VALUES ('ADM_COT_IESS_ESP', 'COTIZACIONES IESS ESPECIALES', 'Cotizaciones IESS Especiales', 
'/pages/administracion/cotizacion_iess/lista_cotizacion_iess_especial.jsf', 11, 
(select id from sch_proteus.menus where codigo='ADM_NOM'), '2017-09-09 00:00:00', 'auto', 'auto', 1, 'E');

UPDATE sch_proteus.menus SET orden = 12 WHERE codigo = 'ADM_TIP_ANT';