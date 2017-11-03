--FALTA CORRER EN PRODUCCION


--Registro de Fecha para el calculo de vacacion
ALTER TABLE siith_movimientos.vacacion_fecha_calculo
   ALTER COLUMN usuario_crea TYPE bigint;
ALTER TABLE siith_movimientos.vacacion_fecha_calculo
   ALTER COLUMN usuario_modifica TYPE bigint;

INSERT INTO siith.acceso(
            id, tipo_recurso, nombre, descripcion, etiqueta, url, estado, 
            orden, acceso_id, fecha_creacion, usuario_creacion, fecha_actualizacion, 
            usuario_actualizacion, sistema_id, es_nodo_final)
    VALUES (631, 'menu', 'Menu registro fecha para el calculo de vacaciones', 'Menu registro fecha para el calculo de vacaciones', 'Registro fecha para el calculo de vacaciones', '/pages/vacaciones/registroFechaCalculoVacaciones.jsf', 1, 
            9, 619, '2012-08-03 00:00:00-05', 1, '2012-08-03 00:00:00-03', 
            1, 1, true);


ALTER TABLE siith_movimientos.vacacion_fecha_calculo
   ALTER COLUMN fecha_hasta DROP NOT NULL;



--Registro de permisos maxima autoridad
ALTER TABLE siith_movimientos.vacacion_saldos ADD COLUMN permiso_maxima_autoridad boolean DEFAULT false;

INSERT INTO siith.acceso(
            id, tipo_recurso, nombre, descripcion, etiqueta, url, estado, 
            orden, acceso_id, fecha_creacion, usuario_creacion, fecha_actualizacion, 
            usuario_actualizacion, sistema_id, es_nodo_final)
    VALUES (638, 'menu', 'Menu registro permiso maxima autoridad', 'Menu registro permiso maxima autoridad', 'Registro permiso maxima autoridad', '/pages/permisos/registroPermisoMaximaAutoridad.jsf', 1, 
            2, 636, '2012-08-15 00:00:00-05', 1, '2012-08-15 00:00:00-03', 
            1, 1, true);