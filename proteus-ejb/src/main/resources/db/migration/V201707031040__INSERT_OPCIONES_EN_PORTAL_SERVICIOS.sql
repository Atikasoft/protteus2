/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Created: 03/07/2017
 */

INSERT INTO sch_proteus.potal_rhh (codigo, fecha_creacion, nombre, nombre_imagen, url, usuario_creacion, vigente) 
VALUES ('IR', GETDATE(), 'Impuesto Renta', 'impuestoRenta', '/pages/procesos/impuesto_renta/impuesto_renta.jsf', 'atk', 1 );

INSERT INTO sch_proteus.potal_rhh (codigo, fecha_creacion, nombre, nombre_imagen, url, usuario_creacion, vigente) 
VALUES ('BA', GETDATE(), 'Bienes Asignados', 'bienesAsignados', '/pages/consultas/consulta_bienes_asignados.jsf', 'atk', 1 );