/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Leydis Garzon
 * Created: Jul 17, 2017
 */

CREATE TABLE sch_proteus.liquidaciones_requisitos
(
    id NUMERIC(19,0) NOT NULL IDENTITY,
    liquidacion_id NUMERIC(19,0) NOT NULL,
    requisito_id NUMERIC(19,0) NOT NULL,
    archivo_id NUMERIC(19,0),
    cumple BIT DEFAULT 0,
    numero_documento VARCHAR(255) COLLATE Modern_Spanish_CI_AS,
    observacion VARCHAR(500) COLLATE Modern_Spanish_CI_AS,
    fecha_documento DATETIME,
    fecha_creacion DATETIME NOT NULL,
    usuario_creacion VARCHAR(40) COLLATE Modern_Spanish_CI_AS NOT NULL,
    fecha_actualizacion DATETIME,
    usuario_actualizacion VARCHAR(40) COLLATE Modern_Spanish_CI_AS,
    vigente BIT DEFAULT 0,
    CONSTRAINT liquidaciones_requisitos_pk PRIMARY KEY (id),
    CONSTRAINT le_requisito_id_fk 
               FOREIGN KEY (requisito_id) 
               REFERENCES sch_proteus.requisitos ("id"),
    CONSTRAINT le_archivo_id_fk 
               FOREIGN KEY (archivo_id) 
               REFERENCES sch_proteus.archivos ("id"),
    CONSTRAINT le_liquidacion_id_fk 
               FOREIGN KEY (liquidacion_id) 
               REFERENCES sch_proteus.liquidaciones ("id")
);