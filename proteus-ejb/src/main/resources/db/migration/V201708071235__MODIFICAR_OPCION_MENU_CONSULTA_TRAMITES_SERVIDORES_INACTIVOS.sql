/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Leydis Garzon
 * Created: 07/08/2017
 */
UPDATE sch_proteus.menus SET codigo='CONS_TRAM_SERV_INACT', url = '/pages/consultas/consulta_tramites_servidores_inactivos.jsf'
WHERE codigo='CONS_TRAMITES_ANUL' AND url = '/pages/consultas/consulta_tramites_servidores_inactivos.xhtml'
