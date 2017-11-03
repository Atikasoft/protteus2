/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


ALTER TABLE sch_proteus.unidades_presupuestarias 
      ADD certificacion_presupuestaria_contratos VARCHAR(20),
          certificacion_presupuestaria_jubilados VARCHAR(20),
          certificacion_presupuestaria_pasantes VARCHAR(20),
          certificacion_presupuestaria_servicios_profesionales VARCHAR(20),
          certificacion_presupuestaria_consejales_alternos VARCHAR(20);