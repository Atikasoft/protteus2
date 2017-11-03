/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Leydis Garzon
 * Created: 28/07/2017
 */
INSERT INTO sch_proteus.menus (codigo,usuario_creacion,fecha_creacion,nombre,etiqueta,url,orden,menu_id,vigente,tipo) 
VALUES ('CONS_TRAMITES_ANUL','atk',GETDATE(),'CONSULTA TRÁMITES ANULADOS','Consulta Trámites Anulados',
'/pages/consultas/consulta_tramites_anulados.jsf',10,8,1,'E');
