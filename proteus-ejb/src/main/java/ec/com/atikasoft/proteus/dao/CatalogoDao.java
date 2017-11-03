/*
 *  CatalogoDao.java
 *  Proteus V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of Proteus
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with Proteus.
 *
 *  Proteus
 *  Quito - Ecuador
 *  
 *
 *  01/10/2013
 *
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.Catalogo;
import ec.com.atikasoft.proteus.modelo.EstadoPersonal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * CatalogoDao
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@LocalBean
@Stateless
public class CatalogoDao extends GenericDAO<Catalogo, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(Catalogo.class.getCanonicalName());

    /**
     * Constructor por defecto.
     */
    public CatalogoDao() {
        super(Catalogo.class);
    }

    /**
     * Este metodo procesa la busqueda de todo por codigo.
     *
     * @param codigo String
     * @return List Lista de registros de Dato Adicional
     * @throws DaoException DaoException
     */
    public List<Catalogo> buscarPorTipoCatalogoId(final Long codigo) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Catalogo.BUSCAR_POR_TIPO_CATALOGO_ID, codigo);
        } catch (DaoException ex) {
            Logger.getLogger(DatoAdicionalDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    public List<Catalogo> buscarPorTipoCatalogoCodigo(final String codigo) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Catalogo.BUSCAR_TIPO_CATALOGO_CODIGO, codigo);
        } catch (DaoException ex) {
            Logger.getLogger(DatoAdicionalDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Este metodo procesa la busqueda de todo por nemonico.
     *
     * @param nemonico String
     * @return List
     * @throws DaoException DaoException
     */
    public List<Catalogo> buscarPorNemonico(final String codigo) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Catalogo.BUSCAR_POR_NEMONICO, codigo);
        } catch (DaoException ex) {
            Logger.getLogger(CatalogoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     *
     * @param codigoCatalogo
     * @param codigoTipoCatalogo
     * @return
     * @throws DaoException
     */
    public Catalogo buscar(final String codigoCatalogo, final String codigoTipoCatalogo) throws DaoException {
        try {
            Catalogo entidad = null;
            List<Catalogo> lista = buscarPorConsultaNombrada(Catalogo.BUSCAR_TIPO_CATALOGO, codigoCatalogo,
                    codigoTipoCatalogo);
            if (!lista.isEmpty()) {
                entidad = lista.get(0);
            }
            return entidad;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
