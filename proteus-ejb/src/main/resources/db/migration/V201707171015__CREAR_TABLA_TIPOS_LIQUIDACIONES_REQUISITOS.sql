/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Leydis Garzon
 * Created: Jul 17, 2017
 */

CREATE TABLE sch_proteus.tipos_liquidaciones_requisitos
(
    id NUMERIC(19,0) NOT NULL IDENTITY,
    tipo_liquidacion_id NUMERIC(19,0) NOT NULL,
    requisito_id NUMERIC(19,0) NOT NULL,
    obligatorio BIT DEFAULT 0,
    archivo_obligatorio BIT DEFAULT 0,
    fecha_creacion DATETIME NOT NULL,
    usuario_creacion VARCHAR(40) COLLATE Modern_Spanish_CI_AS NOT NULL,
    fecha_actualizacion DATETIME,
    usuario_actualizacion VARCHAR(40) COLLATE Modern_Spanish_CI_AS,
    vigente BIT DEFAULT 0,
    CONSTRAINT tipos_liquidaciones_requisitos_pk PRIMARY KEY (id),
    CONSTRAINT tle_requisito_id_fk 
               FOREIGN KEY (requisito_id) 
               REFERENCES sch_proteus.requisitos ("id"),
    CONSTRAINT tle_tipo_liquidacion_id_fk 
               FOREIGN KEY (tipo_liquidacion_id) 
               REFERENCES sch_proteus.tipos_liquidaciones ("id")
);