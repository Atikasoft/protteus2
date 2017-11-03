/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Leydis Garzon
 * Created: Jul 17, 2017
 */

CREATE TABLE sch_proteus.liquidaciones_reglas
(
    id NUMERIC(19,0) NOT NULL IDENTITY,
    liquidacion_id NUMERIC(19,0) NOT NULL,
    regla_id NUMERIC(19,0) NOT NULL,
    cumple BIT DEFAULT 0,
    mensaje_fallo VARCHAR(200) COLLATE Modern_Spanish_CI_AS,
    fecha_creacion DATETIME NOT NULL,
    usuario_creacion VARCHAR(40) COLLATE Modern_Spanish_CI_AS NOT NULL,
    fecha_actualizacion DATETIME,
    usuario_actualizacion VARCHAR(40) COLLATE Modern_Spanish_CI_AS,
    vigente BIT DEFAULT 0,
    CONSTRAINT liquidaciones_reglas_pk PRIMARY KEY (id),
    CONSTRAINT lr_regla_id_fk 
               FOREIGN KEY (regla_id) 
               REFERENCES sch_proteus.reglas ("id"),
    CONSTRAINT lr_liquidacion_id_fk 
               FOREIGN KEY (liquidacion_id) 
               REFERENCES sch_proteus.liquidaciones ("id")
);