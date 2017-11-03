INSERT INTO sch_proteus.menus (codigo, nombre, etiqueta, url, orden, menu_id, fecha_creacion, usuario_actualizacion, usuario_creacion, vigente, tipo) 
VALUES ('PRC_IRE', 'IMPUESTO RENTA', 'Impuesto a la Renta', '0', 0, 1, '2017-08-30 00:00:00', 'auto', 'auto', 1, 'E');

INSERT INTO sch_proteus.menus (codigo, nombre, etiqueta, url, orden, menu_id, fecha_creacion, usuario_actualizacion, usuario_creacion, vigente, tipo) 
VALUES ('CON_IRE', 'CONSULTA DEL IMPUESTA RENTA', 'Consulta del Impuesto a la Renta', 
'/pages/consultas/busqueda_impuesto_renta.jsf', 1, 
(select id from sch_proteus.menus where codigo='PRC_IRE'), '2017-08-30 00:00:00', 'auto', 'auto', 1, 'E');

INSERT INTO sch_proteus.menus (codigo, nombre, etiqueta, url, orden, menu_id, fecha_creacion, usuario_actualizacion, usuario_creacion, vigente, tipo) 
VALUES ('CON_AIR', 'ARCHIVOS IMPUESTO RENTA', 'Archivos del Impuesto a  la Renta',
 '/pages/consultas/sri/informacion_sri.jsf', 2, 
(select id from sch_proteus.menus where codigo='PRC_IRE'), '2017-08-30 00:00:00', 'auto', 'auto', 1, 'E');

