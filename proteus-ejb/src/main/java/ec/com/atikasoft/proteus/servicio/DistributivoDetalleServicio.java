/*
 *  AdministracionServicio.java
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
package ec.com.atikasoft.proteus.servicio;

import ec.com.atikasoft.proteus.dao.*;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.*;
import ec.com.atikasoft.proteus.servicio.base.BaseServicio;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Nelson Jumbo <nelson.jumbo@atikasoft.com.ec>
 */
@Stateless
@LocalBean
public class DistributivoDetalleServicio extends BaseServicio {

    /**
     * Logger de clase.
     */
    private Logger LOG = Logger.getLogger(DistributivoDetalleServicio.class.getCanonicalName());

    /**
     * 
     */
    @EJB
    private DistributivoDetalleDao distributivoDetalleDao;


    /**
     *
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param idInstitucionEjercicioFiscal
     * @return
     * @throws ServicioException
     */
    public DistributivoDetalle buscarPorServidor(final String tipoIdentificacion, final String numeroIdentificacion,
            final Long idInstitucionEjercicioFiscal) throws ServicioException {
        try {
            return distributivoDetalleDao.buscarPorServidor(tipoIdentificacion, numeroIdentificacion,
                    idInstitucionEjercicioFiscal);

        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     *
     * @param servidorId id del servidor
     * @return
     * @throws ServicioException
     */
    public DistributivoDetalle buscar(final Long servidorId) throws ServicioException {
        try {
            DistributivoDetalle entidad = null;
            List<DistributivoDetalle> lista = distributivoDetalleDao.buscarPorServidor(servidorId);
            if (!lista.isEmpty()) {
                entidad = lista.get(0);
            }
            return entidad;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Recuperar puesto tomando en cuenta el agrupador y la asociacion de la
     * unidad organizacional.
     *
     * @param unidadOrganizacionalId
     * @param servidoresId
     * @return
     * @throws ServicioException
     */
//    public List<DistributivoDetalle> buscarPorUnidadOrganizacional(Long unidadOrganizacionalId, 
//            List<Long> servidoresId) throws ServicioException {
//        try {
//            List<UnidadOrganizacional> unidades = new ArrayList<UnidadOrganizacional>();
//            // verificar si se encuentra con alguna asociacion.
//            Asociacion asociacion = asociacionDao.buscarPorUnidadOrganizacional(unidadOrganizacionalId);
//            if (asociacion == null) {
//                // no se encuentra
//                UnidadOrganizacional uo = unidadOrganizacionalDao.buscarAgrupador(unidadOrganizacionalId);
//                unidades.add(uo);
//            } else {
//                // si se encuentra
//                if (asociacion.getUnidadOrganizacional1() != null) {
//                    unidades.add(asociacion.getUnidadOrganizacional1());
//                }
//                if (asociacion.getUnidadOrganizacional2() != null) {
//                    unidades.add(asociacion.getUnidadOrganizacional2());
//                }
//                if (asociacion.getUnidadOrganizacional3() != null) {
//                    unidades.add(asociacion.getUnidadOrganizacional3());
//                }
//                if (asociacion.getUnidadOrganizacional4() != null) {
//                    unidades.add(asociacion.getUnidadOrganizacional4());
//                }
//                if (asociacion.getUnidadOrganizacional5() != null) {
//                    unidades.add(asociacion.getUnidadOrganizacional5());
//                }
//            }
//            List<DistributivoDetalle> puestos = new ArrayList<DistributivoDetalle>();
//            for (UnidadOrganizacional unidad : unidades) {
//                buscarPorUnidadOrganizacional(unidad, puestos, servidoresId);
//            }
//            return puestos;
//        } catch (Exception e) {
//            throw new ServicioException(e);
//        }
//    }

    /**
     *
     * @param uo
     * @param puestos
     * @throws DaoException
     */
//    private void buscarPorUnidadOrganizacional(UnidadOrganizacional uo, List<DistributivoDetalle> puestos,
//            List<Long> servidoresId) throws DaoException {
//        puestos.addAll(distributivoDetalleDao.buscarPorUnidadOrganizacional(uo.getCodigo()));
//        for (UnidadOrganizacional hijo : uo.getListaUnidadesOrganizacionales()) {
//            if (!hijo.getEsAgrupador()) {
//                buscarPorUnidadOrganizacional(hijo, puestos, servidoresId);
//            }
//        }
//    }
}
