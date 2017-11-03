/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 * Created: Jun 26, 2017
 */

CREATE TABLE sch_proteus.desconcentrados
(
    id NUMERIC(19,0) NOT NULL IDENTITY,
    nombre VARCHAR(200) COLLATE Modern_Spanish_CI_AS NOT NULL,
    servidor_responsable_id NUMERIC(19,0) NOT NULL,
    fecha_creacion DATETIME NOT NULL,
    usuario_creacion VARCHAR(40) COLLATE Modern_Spanish_CI_AS NOT NULL,
    fecha_actualizacion DATETIME,
    usuario_actualizacion VARCHAR(40) COLLATE Modern_Spanish_CI_AS,
    vigente BIT DEFAULT 0,
    CONSTRAINT desconcentrados_pk PRIMARY KEY (id),
    CONSTRAINT desconcentrados_servidor_responsable_id_fk 
               FOREIGN KEY (servidor_responsable_id) 
               REFERENCES sch_proteus.servidor ("id")
);