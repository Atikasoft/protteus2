/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

INSERT INTO sch_proteus.parametros_globales 
(descripcion, fecha_creacion, nemonico, nombre, tipo_dato, usuario_creacion, valor_texto, vigente) VALUES
 ('DEFINE CUAL ES EL PRIMER DIA DE LA SEMANA, POSIBLES VALORES: LUN o DOM', 
GETDATE(), 'ASIS.MARC.PRIMER.DIA.SEMANA', 
'DEFINE CUAL ES EL PRIMER DIA DE LA SEMANA, POSIBLES VALORES: LUN o DOM', 'T', 'auto', 'LUN', 1);

INSERT INTO sch_proteus.parametros_globales 
(descripcion, fecha_creacion, nemonico, nombre, tipo_dato, usuario_creacion, valor_texto, vigente) VALUES
 ('DEFINE CUAL ES EL DIA DE CORTE EN LA SIGUIENTE SEMANA, POSIBLES VALORES: LUN,MAR,MIE,JUE,VIE,SAB o DOM', 
GETDATE(), 'ASIS.MARC.DIA.CORTE', 
'DEFINE CUAL ES EL DIA DE CORTE EN LA SIGUIENTE SEMANA, POSIBLES VALORES: LUN,MAR,MIE,JUE,VIE,SAB o DOM', 'T', 'auto', 'MAR', 1);
