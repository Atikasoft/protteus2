/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Leydis Garzon
 * Created: Jul 07, 2017
 */

ALTER TABLE sch_proteus.certificaciones_presupuestarias_historico
   ADD certificacion_presupuestaria_id NUMERIC(19,0) NOT NULL,
       codigo_modalidad_laboral VARCHAR(255) COLLATE Modern_Spanish_CI_AS,
       codigo_unidad_presupuestaria VARCHAR(255) COLLATE Modern_Spanish_CI_AS;

ALTER TABLE sch_proteus.certificaciones_presupuestarias_historico
   ADD CONSTRAINT cph_certificacion_presupuestaria_id_fk FOREIGN KEY (certificacion_presupuestaria_id) REFERENCES
        sch_proteus.certificaciones_presupuestarias ("id");

ALTER TABLE sch_proteus.certificaciones_presupuestarias_historico
   DROP CONSTRAINT cph_modalidad_laboral_id_fk,
                   cph_unidad_presupuestaria_id_fk;

ALTER TABLE sch_proteus.certificaciones_presupuestarias_historico
   DROP COLUMN observacion,
        ruc,
        unidad_presupuestaria_id,
        grupo_presupuestario,
        modalidad_laboral_id;
