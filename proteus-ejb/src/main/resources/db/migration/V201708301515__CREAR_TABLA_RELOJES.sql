/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 * Created: Aug 23, 2017
 */

CREATE TABLE sch_proteus.relojes
(
    id      NUMERIC(19,0) NOT NULL IDENTITY,
    codigo  VARCHAR(20)  COLLATE Modern_Spanish_CI_AS NOT NULL,
    nombre  VARCHAR(200) COLLATE Modern_Spanish_CI_AS NOT NULL,
    ip      VARCHAR(15)  COLLATE Modern_Spanish_CI_AS NOT NULL,
    catalogo_modelo_id NUMERIC(19,0) NOT NULL,
 
    fecha_creacion DATETIME NOT NULL,
    usuario_creacion VARCHAR(40) COLLATE Modern_Spanish_CI_AS NOT NULL,
    fecha_actualizacion DATETIME,
    usuario_actualizacion VARCHAR(40) COLLATE Modern_Spanish_CI_AS,
    vigente BIT DEFAULT 0,

    CONSTRAINT relojes_pk PRIMARY KEY (id),
    CONSTRAINT catalogo_modelo_id_fk 
               FOREIGN KEY (catalogo_modelo_id) 
               REFERENCES sch_proteus.catalogo ("id")
);