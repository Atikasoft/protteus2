/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


ALTER TABLE sch_proteus.unidades_presupuestarias 
DROP COLUMN certificacion_presupuestaria_contratos,
           certificacion_presupuestaria_jubilados,
           certificacion_presupuestaria_pasantes,
           certificacion_presupuestaria_servicios_profesionales,
           certificacion_presupuestaria_consejales_alternos;