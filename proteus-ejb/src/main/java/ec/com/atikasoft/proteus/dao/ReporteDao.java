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

import ec.com.atikasoft.proteus.enums.ParametroGlobalEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.ParametroGlobal;
import ec.com.atikasoft.proteus.modelo.Reporte;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Dao de Reportes.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 */
@LocalBean
@Stateless
public class ReporteDao extends GenericDAO<Reporte, Long> {

    @EJB
    private ParametroGlobalDao parametroGlobalDao;

    /**
     * Constructor sin argumentos.
     */
    public ReporteDao() {
        super(Reporte.class);
    }

    /**
     * Recupera un reporte dado el rpt desing de birt.
     *
     * @param rptDesing Rpt desing
     * @return Reporte
     * @throws DaoException Error de persistencia.
     */
    public Reporte buscarPorReporte(final String rptDesing) throws DaoException {
        try {
            Reporte entidad = null;
            List<Reporte> lista = buscarPorConsultaNombrada(Reporte.BUSCAR_POR_REPORTE, rptDesing);
            if (!lista.isEmpty()) {
                entidad = lista.get(0);
            }
            return entidad;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Genera url para llamar reporte.
     *
     * @param reporte
     * @param parametros
     * @param idInstitucionEjercicioFiscal
     * @param usuario
     * @return
     * @throws DaoException
     */
    public String buscarURLReporte(final Reporte r, final Map<String, String> parametros,
            final String codigoInstitucion, final Integer ejercicioFiscal, final String usuario) throws DaoException {
        try {
            StringBuilder url = new StringBuilder();
            ParametroGlobal parametro = parametroGlobalDao.buscarPorNemonico(
                    ParametroGlobalEnum.URL_DE_REPORTES.getCodigo());
            url.append(parametro.getValorTexto());
            url.append("/");
            url.append(r.getServlet());
            url.append("?__report=");
            url.append(r.getRptDesing());
            url.append(".");
            url.append(r.getExtension());
            if (r.getFormato().equals("P")) {
                url.append("&__format=pdf");
            } else if (r.getFormato().equals("H")) {
                url.append("&__format=html");
            }
            // agregar las varibles de sistema
            url.append("&p_ef=");
            url.append(ejercicioFiscal);
            url.append("&p_ins=");
            url.append(URLEncoder.encode(codigoInstitucion, "UTF-8"));
            url.append("&p_usu=");
            url.append(URLEncoder.encode(usuario, "UTF-8"));

            // agregar variable privadas
            if (parametros != null) {
                Set<String> keys = parametros.keySet();
                for (String key : keys) {
                    if (parametros.get(key) != null) {
                        url.append("&");
                        url.append(key);
                        url.append("=");
                        url.append(URLEncoder.encode(parametros.get(key), "UTF-8"));
                    }
                }
            }

            return url.toString();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
