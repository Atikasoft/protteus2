/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 * Created: Aug 21, 2017
 */

ALTER TABLE sch_proteus.servidor ADD horario_id NUMERIC(19,0);
ALTER TABLE sch_proteus.servidor ADD CONSTRAINT servidor_horario_id_fk FOREIGN KEY (horario_id) REFERENCES sch_proteus.horarios ("id");