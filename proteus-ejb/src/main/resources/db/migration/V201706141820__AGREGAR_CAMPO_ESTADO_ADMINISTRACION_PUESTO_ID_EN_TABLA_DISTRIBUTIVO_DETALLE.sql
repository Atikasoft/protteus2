/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 * Created: Jun 14, 2017
 */

ALTER TABLE sch_proteus.distributivo_detalles ADD estados_administracion_puestos_regimen_laboral_id NUMERIC(19,0);

ALTER TABLE sch_proteus.distributivo_detalles ADD CONSTRAINT estados_administracion_puestos_regimen_laboral_id_fk FOREIGN KEY
        (estados_administracion_puestos_regimen_laboral_id) 
REFERENCES sch_proteus.estados_administracion_puestos_regimen_laboral ("id");