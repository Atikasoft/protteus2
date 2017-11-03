/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 * Created: Aug 28, 2017
 */

CREATE TABLE sch_proteus.relojes_unidades_organizacionales
(
    id NUMERIC(19,0) NOT NULL IDENTITY,
    relojes_id NUMERIC(19,0),
    unidades_organizacionales_id NUMERIC(19,0),

    fecha_creacion DATETIME NOT NULL,
    usuario_creacion VARCHAR(40) COLLATE Modern_Spanish_CI_AS NOT NULL,
    fecha_actualizacion DATETIME,
    usuario_actualizacion VARCHAR(40) COLLATE Modern_Spanish_CI_AS,
    vigente BIT DEFAULT 0,

    CONSTRAINT relojes_unidades_organizacionales_pk PRIMARY KEY (id),
    CONSTRAINT relojes_unidades_organizacionales_relojes_id_fk 
               FOREIGN KEY (relojes_id) 
               REFERENCES sch_proteus.relojes ("id"),
    CONSTRAINT relojes_unidades_organizacionales_unidades_organizacionales_id_fk 
               FOREIGN KEY (unidades_organizacionales_id) 
               REFERENCES sch_proteus.unidades_organizacionales ("id"),
);