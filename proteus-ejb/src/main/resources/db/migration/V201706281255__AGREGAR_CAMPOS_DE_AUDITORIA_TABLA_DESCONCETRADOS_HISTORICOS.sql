/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 * Created: Jun 28, 2017
 */

ALTER TABLE sch_proteus.desconcentrados_historicos 
    ADD fecha_actualizacion DATETIME,
        usuario_actualizacion VARCHAR(40) COLLATE Modern_Spanish_CI_AS,
        vigente BIT NOT NULL DEFAULT 0;