/*
 *  ProcedimientosSprynImpl.java
 *  ESIPREN V 2.0 $Revision 1.0 $
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
 *  Aug 23, 2012
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.servicio;

import ec.com.atikasoft.proteus.dao.AsistenciaDao;
import ec.com.atikasoft.proteus.dao.ServidorDao;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.Asistencia;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.servicio.base.BaseServicio;
import ec.com.atikasoft.proteus.util.UtilFechas;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
@Stateless
@LocalBean
public class ProcedimientosServicio extends BaseServicio {

    /**
     * Logger de la clase.
     */
    private static final Logger LOG = Logger.getLogger(ProcedimientosServicio.class.getName());

    /**
     * Datasource de mf.
     */
    @Resource(name = "jdbc/relojasistencia")
    private DataSource dataSource;

    /**
     * DAO de Servidor.
     */
    @EJB
    private ServidorDao servidorDao;

    /**
     * DAO de Asistencia.
     */
    @EJB
    private AsistenciaDao asistenciaDao;

    /**
     * Constructor sin argumentos.
     */
    public ProcedimientosServicio() {
        super();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public long cargarAsistenciasMDMQ(final Date fechaInicio, final Date fechaFin) throws ServicioException {
        Connection conn = null;
        ResultSet rs = null;
        CallableStatement cst = null;
        Long contador = 0l;
        LOG.log(Level.INFO, "********* cargando asistencia desde {0} hasta:{1}", new Object[]{
            UtilFechas.formatearLargo(fechaInicio), UtilFechas.formatearLargo(fechaFin)});
        try {
            conn = dataSource.getConnection();
            String sp = "{ call RelojAsistencia.ZKS_NOVEDADES_GENERAL_V2_STP(?,?,?,?,?,?,?,?)}";
            cst = conn.prepareCall(sp);
            cst.setString(1, null);
            cst.setDate(2, new java.sql.Date(fechaInicio.getTime()));
            cst.setDate(3, new java.sql.Date(fechaFin.getTime()));
            cst.setString(4, null);
            cst.setString(5, null);
            cst.setString(6, null);
            cst.setString(7, null);
            cst.setString(8, "ZKSRptMarcaciones");
//            System.out.println("fin de seteo de parametros....");
            cst.execute();
//            System.out.println("ejecutando stp....");
            rs = cst.getResultSet();
//            System.out.println("ejecutando stp*** ...." + rs.getType());
            int i = 0;
            while (rs.next()) {
//                System.out.println(i++);
                Asistencia a = new Asistencia();
                a.setCodigoEmpleado(Long.valueOf(rs.getString("COD_EMP")));
                a.setFecha(UtilFechas.convertirFechaStringEnDate(rs.getString("FECHA"), UtilFechas.FORMATO_FECHA_1));
                a.setTimbre1(rs.getString("M1"));
                a.setTimbre2(rs.getString("M2"));
                a.setTimbre3(rs.getString("M3"));
                a.setTimbre4(rs.getString("M4"));
                a.setTimbre5(rs.getString("M5"));
                a.setTimbre6(rs.getString("M6"));
                a.setTimbre7(rs.getString("M7"));
                a.setTimbre8(rs.getString("M8"));
                a.setTimbre9(rs.getString("M9"));
                a.setTimbre10(rs.getString("M10"));

                guardarAsistencia(a);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ServicioException("Error al poblar asistencia desde stp mdmq", ex);
        } finally {
            if (cst != null) {
                try {
                    cst.close();
                } catch (SQLException ex) {
                    LOG.log(Level.INFO, "{0}{1}", new Object[]{ex.getMessage(), ex});
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    LOG.log(Level.INFO, "{0}{1}", new Object[]{ex.getMessage(), ex});
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    LOG.log(Level.INFO, "{0}{1}", new Object[]{ex.getMessage(), ex});
                }
            }
        }
        return contador;
    }

    /**
     *
     * @param a
     * @return
     */
    public long guardarAsistencia(Asistencia a) {
        Long contador = 0l;
        a.setFechaCreacion(new Date());
        a.setUsuarioCreacion("Automático");
        a.setVigente(Boolean.TRUE);

        try {
            Servidor s = servidorDao.buscarPorCodigo(a.getCodigoEmpleado());
            a.setServidor(s);
            List<Asistencia> lista = asistenciaDao.buscarPorFechaYEmpleado(a.getCodigoEmpleado(), a.getFecha());

            if (lista.isEmpty() && a.getServidor() != null) {
                asistenciaDao.crear(a);
                contador++;
            }
        } catch (DaoException ex) {
            LOG.log(Level.INFO, "No se puede guardar para el codigo {0}", a.getCodigoEmpleado() + "/" + ex.getMessage());
        }
        return contador;
    }
}
