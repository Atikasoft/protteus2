/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Leydis Garzon
 * Created: 27/07/2017
 */
INSERT INTO sch_proteus.parametros_institucionales (fecha_creacion,usuario_creacion,valor_texto,vigente,institucion_id,parametros_institucionales_catalogos_id) 
VALUES (GETDATE(),'atk','EL ADMINISTRADOR GENERAL POR DELEGACION DEL SR ALCALDE CONSTANTE EN RESOLUCION ADMINISTRATIVA N.- A-0005 DEL 13 DE JUNIO DEL 2014, EXPIDE LA SIGUIENTE ACCION DE PERSONAL',
1,1,(SELECT id FROM sch_proteus.parametros_institucionales_catalogos WHERE parametros_institucionales_catalogos.nemonico='DESC.RESOL.ACCION.PERSONAL.OTRA.INST.REGISTRO'))

INSERT INTO sch_proteus.parametros_institucionales (fecha_creacion,usuario_creacion,valor_texto,vigente,institucion_id,parametros_institucionales_catalogos_id) 
VALUES (GETDATE(),'atk','EL ADMINISTRADOR GENERAL POR DELEGACION DEL SR ALCALDE CONSTANTE EN RESOLUCION ADMINISTRATIVA N.- A-0005 DEL 13 DE JUNIO DEL 2014, EXPIDE LA SIGUIENTE ACCION DE PERSONAL',
1,1,(SELECT id FROM sch_proteus.parametros_institucionales_catalogos WHERE parametros_institucionales_catalogos.nemonico='DESC.RESOL.ACCION.PERSONAL.OTRA.INST.TERMINAC'))

