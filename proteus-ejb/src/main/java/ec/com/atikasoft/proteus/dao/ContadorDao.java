/*
 *  ClaseDao.java
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
 *  22/10/2012
 *
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.Contador;
import ec.com.atikasoft.proteus.modelo.InstitucionEjercicioFiscal;
import ec.com.atikasoft.proteus.util.UtilMatematicas;
import java.math.BigDecimal;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Dao de contadores
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 */
@LocalBean
@Stateless
public class ContadorDao extends GenericDAO<Contador, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(ContadorDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public ContadorDao() {
        super(Contador.class);
    }

    /**
     * Genera el codigo para el servidor
     *
     * @return Numero generado.
     * @throws DaoException Error de ejecucion.
     */
    public Long generarCodigoServidor() throws DaoException {
        Contador c = buscarTodos().get(0);
        lock(c);
        c.setContadorCodigoPuestos(c.getContadorCodigoPuestos() + 1);
        actualizar(c);
        return c.getContadorCodigoPuestos();
    }

}
