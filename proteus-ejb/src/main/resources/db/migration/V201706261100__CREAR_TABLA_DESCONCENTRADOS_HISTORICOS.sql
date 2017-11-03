/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 * Created: Jun 26, 2017
 */

CREATE TABLE sch_proteus.desconcentrados_historicos
(
    id NUMERIC(19,0) NOT NULL IDENTITY,
    desconcentrado_id NUMERIC(19,0) NOT NULL,
    servidor_responsable_id NUMERIC(19,0) NOT NULL,
    desconcentrado_nombre VARCHAR(200) COLLATE Modern_Spanish_CI_AS NOT NULL,
    servidor_responsable_nombre VARCHAR(200) COLLATE Modern_Spanish_CI_AS NOT NULL,
    fecha_creacion DATETIME NOT NULL,
    usuario_creacion VARCHAR(40) COLLATE Modern_Spanish_CI_AS NOT NULL

    CONSTRAINT desconcentrados_historicos_pk PRIMARY KEY (id),

    CONSTRAINT desconcentrados_historicos_desconcentrado_id_fk 
           FOREIGN KEY (desconcentrado_id) 
           REFERENCES sch_proteus.desconcentrados ("id"),

    CONSTRAINT desconcentrados_historicos_servidor_responsable_id_fk 
           FOREIGN KEY (servidor_responsable_id) 
           REFERENCES sch_proteus.servidor ("id")
);