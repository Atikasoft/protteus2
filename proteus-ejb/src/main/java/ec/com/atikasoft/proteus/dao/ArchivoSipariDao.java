/*
 *  ArchivoSipariDao.java
 *  PROTEUS V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of AtikaSoft
 *  ("Confidential Information").  You shall not disclose such Confidential Information
 *  and shall use it only in accordance with the terms of the license agreement you entered into
 *  with AtikaSoft.
 *
 *
 *  AtikaSoft
 *  Quito - Ecuador
 *  http://www.atikasoft.com.ec/
 *
 *  28/01/2014
 *
 *  Copyright (C) 2014 AtikaSoft.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.ArchivoSipari;
import ec.com.atikasoft.proteus.modelo.Banco;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@LocalBean
@Stateless
public class ArchivoSipariDao extends GenericDAO<ArchivoSipari, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(ArchivoSipariDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public ArchivoSipariDao() {
        super(ArchivoSipari.class);
    }

    /**
     * Este metodo procesa la busqueda de todo por nomina y tipo.
     *
     * @param idNomina
     * @param tipo
     * @return List de registros de ArchivoSipari
     * @throws DaoException DaoException
     */
    public List<ArchivoSipari> buscarTodosPorNominaYTipo(final Long idNomina, final String tipo) throws DaoException {
        try {
            return buscarPorConsultaNombrada(ArchivoSipari.BUSCAR_POR_NOMINA_Y_TIPO, idNomina, tipo);
        } catch (DaoException ex) {
            Logger.getLogger(BancoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Usado para crear un el archivo cabecera del sipari.
     *
     * @param archivoCab
     * @return
     * @throws DaoException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public ArchivoSipari crearArchivoSipari(ArchivoSipari archivoCab) throws DaoException {
        archivoCab = crear(archivoCab);
        flush();
        return archivoCab;
    }
}
