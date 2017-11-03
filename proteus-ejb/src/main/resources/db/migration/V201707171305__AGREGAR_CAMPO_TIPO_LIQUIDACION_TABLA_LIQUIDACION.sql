/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

ALTER TABLE sch_proteus.liquidaciones 
            ADD  tipo_liquidacion_id NUMERIC(19,0),
                 CONSTRAINT liquidaciones_tipo_liquidacion_id_fk 
                        FOREIGN KEY (tipo_liquidacion_id) 
                        REFERENCES sch_proteus.tipos_liquidaciones ("id");
