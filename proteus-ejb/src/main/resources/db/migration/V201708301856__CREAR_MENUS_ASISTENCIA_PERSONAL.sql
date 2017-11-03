INSERT INTO sch_proteus.menus (codigo, nombre, etiqueta, url, orden, menu_id, fecha_creacion, usuario_actualizacion, usuario_creacion, vigente, tipo) 
VALUES ('PRC_APE', 'ASISTENCIA DE PERSONAL', 'Asistencia de Personal', '0', 0, 1, '2017-08-30 00:00:00', 'auto', 'auto', 1, 'E');

INSERT INTO sch_proteus.menus (codigo, nombre, etiqueta, url, orden, menu_id, fecha_creacion, usuario_actualizacion, usuario_creacion, vigente, tipo) 
VALUES ('ADM_HOR', 'HORARIOS', 'Horarios', 
'/pages/administracion/asistencia_de_personal/horario/horario.jsf', 1, 
(select id from sch_proteus.menus where codigo='PRC_APE'), '2017-08-30 00:00:00', 'auto', 'auto', 1, 'E');

INSERT INTO sch_proteus.menus (codigo, nombre, etiqueta, url, orden, menu_id, fecha_creacion, usuario_actualizacion, usuario_creacion, vigente, tipo) 
VALUES ('ADM_AHD', 'ASIGNACION DE HORARIOS A DESCONCENTRADOS', 'Asignación de Horarios a Desconcentrados',
 '/pages/administracion/asistencia_de_personal/horario/asignacion_horarios_a_desconcentrados.jsf', 2, 
(select id from sch_proteus.menus where codigo='PRC_APE'), '2017-08-30 00:00:00', 'auto', 'auto', 1, 'E');

INSERT INTO sch_proteus.menus (codigo, nombre, etiqueta, url, orden, menu_id, fecha_creacion, usuario_actualizacion, usuario_creacion, vigente, tipo) 
VALUES ('ADM_AHS', 'ASIGNACION DE HORARIOS A SERVIDORES', 'Asignación de Horarios a Servidores',
 '/pages/administracion/asistencia_de_personal/horario/asignacion_horarios_a_desconcentrados.jsf', 3, 
(select id from sch_proteus.menus where codigo='PRC_APE'), '2017-08-30 00:00:00', 'auto', 'auto', 1, 'E');

INSERT INTO sch_proteus.menus (codigo, nombre, etiqueta, url, orden, menu_id, fecha_creacion, usuario_actualizacion, usuario_creacion, vigente, tipo) 
VALUES ('ADM_REL', 'RELOJES', 'Relojes',
 '/pages/administracion/asistencia_de_personal/reloj/lista_reloj.jsf', 4, 
(select id from sch_proteus.menus where codigo='PRC_APE'), '2017-08-30 00:00:00', 'auto', 'auto', 1, 'E');

INSERT INTO sch_proteus.menus (codigo, nombre, etiqueta, url, orden, menu_id, fecha_creacion, usuario_actualizacion, usuario_creacion, vigente, tipo) 
VALUES ('ADM_EXC', 'EXCEPCIONES', 'Excepciones',
 '/pages/administracion/asistencia_de_personal/excepcion/lista_excepcion.jsf', 5, 
(select id from sch_proteus.menus where codigo='PRC_APE'), '2017-08-30 00:00:00', 'auto', 'auto', 1, 'E');
