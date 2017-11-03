/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  LEYDIS GARZON
 * Created: 31/07/2017
 */
ALTER TABLE sch_proteus.tramites ADD fecha_rechazo_anulacion DATETIME,
        comentario_rechazo_anulacion VARCHAR(400) COLLATE Modern_Spanish_CI_AS,
        usuario_rechazo_anulacion VARCHAR(40) COLLATE Modern_Spanish_CI_AS;
GO

UPDATE t SET t.comentario_rechazo_anulacion=d.comentario, 
             t.usuario_rechazo_anulacion=t.usuario_actualizacion,
             t.fecha_rechazo_anulacion=t.fecha_actualizacion
FROM sch_proteus.tramites AS t
INNER JOIN sch_proteus.instancias AS i ON t.id = i.identificador_externo                                
INNER JOIN (SELECT * FROM(
 SELECT ROW_NUMBER() OVER (PARTITION BY instancias_id ORDER BY id DESC) AS Orden, d.*
 FROM sch_proteus.detalles d 
) T1
WHERE Orden = 1) AS d ON i.id = d.instancias_id
WHERE t.vigente=1 AND (t.codigo_fase='ANU' or t.codigo_fase='REC')
