/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.asistencia.MarcacionMigrada;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * DAO de MarcacionMigrada
 * @author Maikel Pérez Martínez <maikel.perez@atikasoft.ec>
 */
@LocalBean
@Stateless
public class MarcacionMigradaDao extends GenericDAO<MarcacionMigrada, Long> {

    /**
     * Constructor por defecto.
     */
    public MarcacionMigradaDao() {
        super(MarcacionMigrada.class);
    }

}
