INSERT INTO siith.acceso(
            id, tipo_recurso, nombre, descripcion, etiqueta, url, estado, 
            orden, acceso_id, fecha_creacion, usuario_creacion, fecha_actualizacion, 
            usuario_actualizacion, sistema_id, es_nodo_final)
    VALUES (627, 'menu', 'Concesión de vacaciones', 'Menu Concesión de Vacaciones', 'Concesión Vacaciones', '/pages/vacaciones/concesionVaciones', 1, 
            4, 619, '2012-07-23', 1, '2012-07-23', 
            1, 1, TRUE);

INSERT INTO siith.acceso_rol(id, acceso_id, rol_id, estado, fecha_creacion, usuario_creacion, fecha_actualizacion, 
            usuario_actualizacion)
    VALUES (952, 627, 98, 1, '2012-07-23', 11, null, null);

