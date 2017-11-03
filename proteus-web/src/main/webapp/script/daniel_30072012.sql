
-- Falta ejecutar en produccion

INSERT INTO siith.acceso(
            id, tipo_recurso, nombre, descripcion, etiqueta, url, estado, 
            orden, acceso_id, fecha_creacion, usuario_creacion, fecha_actualizacion, 
            usuario_actualizacion, sistema_id, es_nodo_final)
    VALUES (619, 'menu', 'Menu vacaciones', 'Menu vacaciones', 'Vacaciones', '', 1, 
            0, 615, '2012-07-16 00:00:00-05', 1, '2012-07-16 00:00:00-05', 
            1, 1, false);

INSERT INTO siith.acceso(
            id, tipo_recurso, nombre, descripcion, etiqueta, url, estado, 
            orden, acceso_id, fecha_creacion, usuario_creacion, fecha_actualizacion, 
            usuario_actualizacion, sistema_id, es_nodo_final)
    VALUES (620, 'menu', 'Menu vacaciones parametrización', 'Menu vacaciones parametrización', 'Parametrización', '', 1, 
            0, 619, '2012-07-16 00:00:00-05', 1, '2012-07-16 00:00:00-05', 
            1, 1, false);


INSERT INTO siith.acceso(
            id, tipo_recurso, nombre, descripcion, etiqueta, url, estado, 
            orden, acceso_id, fecha_creacion, usuario_creacion, fecha_actualizacion, 
            usuario_actualizacion, sistema_id, es_nodo_final)
    VALUES (622, 'menu', 'Por institución', 'Por institución', 'Por institución', '/pages/vacaciones/parametrosInstitucion.jsf', 1, 
            0, 620, '2012-07-16 00:00:00-05', 1, '2012-07-16 00:00:00-05', 
            1, 1, true);



INSERT INTO siith.acceso(
            id, tipo_recurso, nombre, descripcion, etiqueta, url, estado, 
            orden, acceso_id, fecha_creacion, usuario_creacion, fecha_actualizacion, 
            usuario_actualizacion, sistema_id, es_nodo_final)
    VALUES (624, 'menu', 'Menu cronograma de vacaciones', 'Menu cronograma de vacaciones', 'Cronograma de vacaciones', '/pages/vacaciones/cronogramaVacaciones.jsf', 1, 
            0, 619, '2012-07-19 00:00:00-05', 1, '2012-07-19 00:00:00-05', 
            1, 1, true);


INSERT INTO siith.acceso(
            id, tipo_recurso, nombre, descripcion, etiqueta, url, estado, 
            orden, acceso_id, fecha_creacion, usuario_creacion, fecha_actualizacion, 
            usuario_actualizacion, sistema_id, es_nodo_final)
    VALUES (629, 'menu', 'Menu delegado para creacion del cronograma', 'Menu delegado para creacion del cronograma', 'Delegados para crear cronogramas de vacaciones', '/pages/vacaciones/delegadoCronogramaVacacion.jsf', 1, 
            0, 619, '2012-07-19 00:00:00-05', 1, '2012-07-19 00:00:00-05', 
            1, 1, true);


INSERT INTO "siith"."acceso"(
            id, tipo_recurso, nombre, descripcion, etiqueta, url, estado, 
            orden, acceso_id, fecha_creacion, usuario_creacion, fecha_actualizacion, 
            usuario_actualizacion, sistema_id, es_nodo_final) 
    VALUES (628, 'menu', 'Menu Reprogramacion', 'Menú ,para reprogramar vacaciones', 'Reprogramaciones', '', 
            1, 6, 619, '2012-07-24 00:00:00-05', 1, '2012-07-24 00:00:00-05',
            1, 1, false);



INSERT INTO "siith"."acceso" (
            id, tipo_recurso, nombre, descripcion, etiqueta, url, estado, 
            orden, acceso_id, fecha_creacion, usuario_creacion, fecha_actualizacion, 
            usuario_actualizacion, sistema_id, es_nodo_final)
    VALUES (634, 'menu', 'De Cronograma', 'De Cronograma', 'De Cronograma', '/pages/vacaciones/reprogramacion.jsf', 
            1, 0, 628, '2012-08-07 09:52:02-05', 1, '2012-08-07 09:52:17-05',
            1, 1, true);



INSERT INTO "siith"."acceso" (
            id, tipo_recurso, nombre, descripcion, etiqueta, url, estado, 
            orden, acceso_id, fecha_creacion, usuario_creacion, fecha_actualizacion, 
            usuario_actualizacion, sistema_id, es_nodo_final)
    VALUES (635, 'menu', 'De Suspenciones', 'De Suspenciones', 'De Suspenciones', '/pages/vacaciones/reprogramacionSuspendidos.jsf',
            1, 1, 628, '2012-08-07 10:06:09-05', 1, '2012-08-07 10:06:15-05',
            1, 1, true);

INSERT INTO "siith"."acceso" (
            id, tipo_recurso, nombre, descripcion, etiqueta, url, estado, 
            orden, acceso_id, fecha_creacion, usuario_creacion, fecha_actualizacion, 
            usuario_actualizacion, sistema_id, es_nodo_final) 
    VALUES (639, 'menu', 'Menu para gestion de Reportes', 'Menu para gestion de Reportes', 'Reportes', null, 
            1, 3, 615, '2012-08-17 11:24:39-05', 1, null, null, 1, false);


INSERT INTO "siith"."acceso" (
            id, tipo_recurso, nombre, descripcion, etiqueta, url, estado, 
            orden, acceso_id, fecha_creacion, usuario_creacion, fecha_actualizacion, 
            usuario_actualizacion, sistema_id, es_nodo_final) 
    VALUES (640, 'menu', 'Reimpresion de Accion de Personal', 'Reimpresion de Accion de Personal', 'Reimpresión de Acción de Personal', '/pages/reportes/reimpresionAccionPersonal.jsf',
            1, 0, 639, '2012-08-17 11:35:11-05', 
            1, null, null, 1, true);
