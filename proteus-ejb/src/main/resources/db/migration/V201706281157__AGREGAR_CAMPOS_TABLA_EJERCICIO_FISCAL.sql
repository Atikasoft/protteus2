/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

ALTER TABLE sch_proteus.ejercicios_fiscales 
            ADD  ruc_contador VARCHAR(13),
                 firma_contador_imagen IMAGE,
                 firma_agente_retencion_imagen IMAGE,
                 fecha_entrega_formulario_107 DATE,
                 impuesto_renta_completado BIT DEFAULT 0;
