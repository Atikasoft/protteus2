/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 * Created: Jun 14, 2017
 */

insert into sch_proteus.ejercicios_fiscales 
       (ejercicios_fiscales.fecha_creacion,
       ejercicios_fiscales.fecha_inicio,
       ejercicios_fiscales.fecha_fin,
       ejercicios_fiscales.fraccion_basica,
       ejercicios_fiscales.nombre,
       ejercicios_fiscales.usuario_creacion,
       ejercicios_fiscales.vigente,
       ejercicios_fiscales.maximo_deducible_sobre_fracion_basica,
       ejercicios_fiscales.porcentaje_maximo_deducible_sobre_ingresos,
       ejercicios_fiscales.exoneracion_tercera_edad, 
       ejercicios_fiscales.exoneracion_sobre_discapacidad,
       ejercicios_fiscales.numero_anios_tercera_edad,
       ejercicios_fiscales.es_proximo_ejercicio)
       values
       ('2017-06-01','2018-01-01','2018-12-31',0.00,'2018','atk','false',0.00,0.00,0.00, 0.00,0,'true');

insert into sch_proteus.instituciones_ejercicios_fiscales
       (instituciones_ejercicios_fiscales.contador_acto_administrativo,
       instituciones_ejercicios_fiscales.contador_registro,
       instituciones_ejercicios_fiscales.contador_tramite,
       instituciones_ejercicios_fiscales.fecha_creacion,
       instituciones_ejercicios_fiscales.usuario_creacion,
       instituciones_ejercicios_fiscales.vigente,
       instituciones_ejercicios_fiscales.ejercicio_fiscal_id,
       instituciones_ejercicios_fiscales.institucion_id,
       instituciones_ejercicios_fiscales.contador_nomina)
       
       values
       (0, 0, 0,'2017-06-01','atk', 'true',
        (select id from sch_proteus.ejercicios_fiscales where nombre = '2018'),
        (select id from sch_proteus.instituciones where codigo = 'MDMQ'),
        0.00
       );