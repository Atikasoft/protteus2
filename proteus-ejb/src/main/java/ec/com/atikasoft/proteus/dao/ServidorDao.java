/**
 * ServidorDao.java Proteus V 2.0 $Revision 1.0 $
 *
 * Todos los derechos reservados. All rights reserved. This software is the confidential and proprietary information of
 * Proteus ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with Proteus.
 *
 * Proteus Quito - Ecuador
 *
 * 22/10/2012
 *
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.vo.CargaMasivaServidorVO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Patricio Recalde <patricio.recalde@atikasoft.com.ec>
 * @author Mauro Vincent <mauro.vincent@atikasoft.com.ec>
 */
@LocalBean
@Stateless
public class ServidorDao extends GenericDAO<Servidor, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(ServidorDao.class.getName());

    /**
     * Constructor sin argumentos.
     */
    public ServidorDao() {
        super(Servidor.class);
    }

    /**
     * Este metodo procesa la busqueda de todo por nombre.
     *
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @return List
     * @throws DaoException DaoException
     */
    public Servidor buscar(final String tipoIdentificacion, final String numeroIdentificacion) throws DaoException {
        try {
            Servidor entidad = null;
            List<Servidor> lista = buscarPorConsultaNombrada(Servidor.BUSCAR_POR_IDENTIFICACION, tipoIdentificacion,
                    numeroIdentificacion);
            if (!lista.isEmpty()) {
                entidad = lista.get(0);
            }
            return entidad;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
    /**
     * Este metodo procesa la busqueda de todo por nombre.
     *
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @return List
     * @throws DaoException DaoException
     */
    public Servidor buscarPorIdentificacionExternoInternoConPuesto(final String tipoIdentificacion, 
            final String numeroIdentificacion) throws DaoException {
        try {
            Servidor entidad = null;
            List<Servidor> lista = buscarPorConsultaNombrada(
                    Servidor.BUSCAR_POR_IDENTIFICACION_SERVIDOR_EXTERNO_O_INTERNO_CON_PUESTO, tipoIdentificacion,
                    numeroIdentificacion);
            if (!lista.isEmpty()) {
                entidad = lista.get(0);
            }
            return entidad;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Este metodo procesa la busqueda de todo por nombre.
     *
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @return List
     * @throws DaoException DaoException
     */
    public Servidor buscarExterno(
            final String tipoIdentificacion, final String numeroIdentificacion) throws DaoException {
        try {
            Servidor entidad = null;
            List<Servidor> lista = buscarPorConsultaNombrada(
                    Servidor.BUSCAR_EXTERNO_POR_IDENTIFICACION, tipoIdentificacion, numeroIdentificacion);
            if (!lista.isEmpty()) {
                entidad = lista.get(0);
            }
            return entidad;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Buscar por documentos y que este activo en distributivo
     *
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param idInstitucionEjercicioFiscal
     * @return
     * @throws DaoException
     */
    public Servidor buscar(
            final String tipoIdentificacion, final String numeroIdentificacion,
            final Long idInstitucionEjercicioFiscal) throws DaoException {
        try {
            Servidor entidad = null;
            List<Servidor> lista = buscarPorConsultaNombrada(
                    Servidor.BUSCAR_POR_IDENTIFICACION_DISTRIBUTIVO,
                    tipoIdentificacion, numeroIdentificacion, idInstitucionEjercicioFiscal);
            if (!lista.isEmpty()) {
                entidad = lista.get(0);
            }
            return entidad;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    public Servidor buscarPorTipoIdentificacion(final String tipoIdentificacion) throws
            DaoException {
        try {

            Servidor entidad = null;
            List<Servidor> lista = buscarPorConsultaNombrada(
                    Servidor.BUSCAR_POR_NUMERO_IDENTIFICACION, tipoIdentificacion);
            if (!lista.isEmpty()) {
                entidad = lista.get(0);
            }
            return entidad;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Este metodo procesa la busqueda de todo por appellidosNombres.
     *
     * @param appellidosNombres String
     * @return List
     * @throws DaoException DaoException
     */
    public List<Servidor> buscarPorNombre(final String appellidosNombres) throws
            DaoException {
        try {
            List<Servidor> lista = buscarPorConsultaNombrada(Servidor.BUSCAR_POR_APELLIDOS_NOMBRES_SERVIDOR,
                    UtilCadena.concatenar("%", appellidosNombres.toUpperCase(), "%"));
            return lista;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Este metodo busca servidores con puestos asignados filtrando por nombre.
     *
     * @param nombre String
     * @return List
     * @throws DaoException DaoException
     */
    public List<Servidor> buscarPorNombreConPuesto(final String nombre) throws
            DaoException {
        try {
            List<Servidor> lista = buscarPorConsultaNombrada(
                    Servidor.BUSCAR_POR_NOMBRE_SERVIDOR_CON_PUESTO, UtilCadena.concatenar("%", nombre.toUpperCase(), "%"));
            return lista;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Este metodo procesa la busqueda de todo por nombre y número de identificacion.
     *
     * @param nombre String
     * @param numeroIdentificacion
     * @return List
     * @throws DaoException DaoException
     */
    public List<Servidor> buscarPorNombreYIdentificacion(
            final String nombre, final String numeroIdentificacion) throws DaoException {
        try {
            List<Servidor> lista = buscarPorConsultaNombrada(
                    Servidor.BUSCAR_POR_NOMBRE_Y_IDENTIFICACION_SERVIDOR,
                    UtilCadena.concatenar("%", nombre.toUpperCase(), "%"), numeroIdentificacion);
            return lista;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Busca servidores por nombnre y que existan en el distributivo
     *
     * @param nombre
     * @param idInstitucionEjercicioFiscal
     * @return servidores
     * @throws DaoException
     */
    public List<Servidor> buscarPorNombreDistributivo(
            final String nombre, final Long idInstitucionEjercicioFiscal) throws DaoException {
        try {
            List<Servidor> lista = buscarPorConsultaNombrada(
                    Servidor.BUSCAR_POR_NOMBRE_SERVIDOR_DISTRIBUTIVO,
                    UtilCadena.concatenar("%", nombre.toUpperCase(), "%"),
                    Boolean.TRUE,
                    idInstitucionEjercicioFiscal,
                    Boolean.TRUE);

            return lista;

        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Este metodo busca N servidores por nombre, si idInstitucionEjercicioFiscal no es null se verifican que existan en
     * distributivo
     *
     * @param nombre
     * @param idInstitucionEjercicioFiscal
     * @param n
     * @return
     * @throws DaoException
     */
    public List<Servidor> buscarPorNombreDistributivoLimite(
            final String nombre, final Long idInstitucionEjercicioFiscal, final Integer n) throws DaoException {
        try {
            String qName = idInstitucionEjercicioFiscal == null ? Servidor.BUSCAR_POR_APELLIDOS_NOMBRES_SERVIDOR
                    : Servidor.BUSCAR_POR_NOMBRE_SERVIDOR_DISTRIBUTIVO;
            Query query = getEntityManager().createNamedQuery(qName);
            query.setParameter(1, UtilCadena.concatenar("%", nombre.toUpperCase(), "%"));
            if (idInstitucionEjercicioFiscal != null) {
                query.setParameter(2, Boolean.TRUE);
                query.setParameter(3, idInstitucionEjercicioFiscal);
                query.setParameter(4, Boolean.TRUE);
            }
            query.setMaxResults(n);
            List<Servidor> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Busca servidores por nombnre y que existan en el distributivo
     *
     * @param nombre
     * @param idInstitucionEjercicioFiscal
     * @param codigoUnidadOrganizacional
     * @return servidores
     * @throws DaoException
     */
    public List<Servidor> buscarPorNombreDistributivo(final String nombre, final Long idInstitucionEjercicioFiscal,
            String codigoUnidadOrganizacional) throws DaoException {
        try {
            List<Servidor> lista = buscarPorConsultaNombrada(
                    Servidor.BUSCAR_POR_NOMBRE_SERVIDOR_DISTRIBUTIVO_UNIDAD_ORGANIZACIONAL,
                    UtilCadena.concatenar("%", nombre.toUpperCase(), "%"),
                    Boolean.TRUE, idInstitucionEjercicioFiscal, Boolean.TRUE,
                    codigoUnidadOrganizacional == null ? "%" : codigoUnidadOrganizacional);
            System.out.println(codigoUnidadOrganizacional + ":" + lista.size());
            return lista;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Busca servidores por nombre y numero de identificacion y que existan en el distributivo
     *
     * @param nombre
     * @param numeroIdentificacion
     * @param idInstitucionEjercicioFiscal
     * @return
     * @throws DaoException
     */
    public List<Servidor> buscarPorNombreEIndentificacionDistributivo(
            final String nombre, final String numeroIdentificacion,
            final Long idInstitucionEjercicioFiscal) throws DaoException {
        try {
            List<Servidor> lista = buscarPorConsultaNombrada(
                    Servidor.BUSCAR_POR_NOMBRE_E_IDENTIFICACION_SERVIDOR_DISTRIBUTIVO,
                    UtilCadena.concatenar("%", nombre.toUpperCase(), "%"),
                    UtilCadena.concatenar("%", numeroIdentificacion.toUpperCase(), "%"),
                    Boolean.TRUE,
                    idInstitucionEjercicioFiscal,
                    Boolean.TRUE);

            return lista;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Este metodo procesa la busqueda de servidor por codigo de empleado
     *
     * @param codEmpleado String
     * @return Servidor
     * @throws DaoException DaoException
     */
    public Servidor buscarPorCodigo(final Long codEmpleado) throws
            DaoException {
        try {
            List<Servidor> lista = buscarPorConsultaNombrada(
                    Servidor.BUSCAR_POR_CODIGO, codEmpleado);
            if (!lista.isEmpty()) {
                return lista.get(0);
            }
            return null;
        } catch (DaoException e) {
            throw new DaoException(e);
        }
    }
    
    /**
     * Recupera los servidores que tienen un horario determinado dado su id
     * @param horarioId id del horario
     * @param institucionEjercicioFiscalId Id del institucion ejercicio fiscal
     * @return
     * @throws DaoException 
     */
    public List<Servidor> buscarActivosPorHorarioId(
            final Long horarioId, final Long institucionEjercicioFiscalId) throws DaoException {
        List<Servidor> result = new ArrayList<>();
        try {
            result = buscarPorConsultaNombrada(
                    Servidor.BUSCAR_ACTIVOS_POR_HORARIO_ID, horarioId, institucionEjercicioFiscalId);
        } catch (DaoException e) {
            throw new DaoException(e);
        }
        return result;
    }

    /**
     * Busca el servidor por su numero de identificacion y clave
     *
     * @param username
     * @param clave
     * @return
     * @throws DaoException
     */
    public List<Servidor> buscarServidorPorUsernameClave(final String username, final String clave) throws
            DaoException {
        try {
            return buscarPorConsultaNombrada(Servidor.BUSCAR_POR_USERNAME_CLAVE, username, clave);
        } catch (DaoException e) {
            throw new DaoException("Error al buscar servidor por el username y la clave.", e);
        }
    }

    /**
     * Busca el servidor por su numero de identificacion y clave
     *
     * @param username
     * @param clave
     * @return
     * @throws DaoException
     */
    public List<Servidor> buscarExternoServidorPorUsernameClave(final String username, final String clave) throws
            DaoException {
        try {
            return buscarPorConsultaNombrada(Servidor.BUSCAR_EXTERNO_POR_USERNAME_CLAVE, username, clave);
        } catch (DaoException e) {
            throw new DaoException("Error al buscar servidor por el username y la clave.", e);
        }
    }

    /**
     * Verifica que las parejas de tipos de documentos y documentos pasados por parametros existan. Devuelve los pares
     * que NO existen.
     *
     * @param documentos
     * @return
     * @throws DaoException
     */
    public List<CargaMasivaServidorVO> chequearDocumentosExistentes(final List<CargaMasivaServidorVO> documentos) throws
            DaoException {
        List<CargaMasivaServidorVO> resultados = new ArrayList<>();
        if (documentos != null) {
            StringBuilder valuesPares = new StringBuilder();
            int r = 0;
            for (int i = 0; i < documentos.size(); i++) {
                CargaMasivaServidorVO par = documentos.get(i);
                if (!par.getRechazado()) {
                    if (r++ > 0) {
                        valuesPares.append(" , ");
                    }
                    valuesPares.append("('")
                            .append(par.getId()).append("','")
                            .append(par.getTipoDocumento()).append("','")
                            .append(par.getDocumento()).append("','")
                            .append(par.getValor()).append("','")
                            .append(par.getLinea()).append("','")
                            .append(0)
                            .append("')");
                }
            }

            StringBuilder stQuery = new StringBuilder("Select c1.id,c1.td,c1.d,c1.v,c1.l,1 from ( ");
            stQuery.append(" select id,td,d,v,l,r, CASE WHEN EXISTS(SELECT * FROM sch_proteus.servidor s ");
            stQuery.append(" WHERE  s.numero_identificacion = E.d and s.tipo_identificacion = E.td and s.vigente = 1) THEN 1 ");
            stQuery.append(" ELSE 0 END AS [Status] FROM (VALUES ");
            stQuery.append(valuesPares.toString());
            stQuery.append(" ) E(id,td,d,v,l,r) ) as c1 where c1.Status = 0 ");

            Query query = getEntityManager().createNativeQuery(stQuery.toString());
            List<Object[]> l = query.getResultList();
            if (l != null) {
                for (Object[] d : l) {
                    CargaMasivaServidorVO registro = new CargaMasivaServidorVO();
                    registro.setId(Long.parseLong(d[0].toString()));
                    registro.setTipoDocumento(d[1].toString());
                    registro.setDocumento(d[2].toString());
                    registro.setValor(Boolean.parseBoolean(d[3].toString()));
                    registro.setLinea(Integer.parseInt(d[4].toString()));
                    registro.setRechazado(true);
                    resultados.add(registro);
                }
            }
        }
        return resultados;
    }

    /**
     * Actualiza los atributos de servidor dados por carga masiva los codigos que se esperan son: En el caso de
     * recibeFondoReserva el codigo es RFR En el caso de tomaTransporte el codigo es TT
     *
     * @param codigoAtributo
     * @param servidores
     * @throws DaoException
     */
    public void actualizarAtributo(final String codigoAtributo, final List<CargaMasivaServidorVO> servidores) throws
            DaoException {

        String campo = null;
        boolean niega = false;
        if (codigoAtributo.equals("RFR")) {
            campo = "recibeFondoReserva";
            niega = true;
        }
        if (codigoAtributo.equals("TT")) {
            campo = "tomaTransporte";
        }
        if (codigoAtributo.equals("DT")) {
            campo = "mensualizaDecimoTercero";
            niega = true;
        }
        if (codigoAtributo.equals("DC")) {
            campo = "mensualizaDecimoCuarto";
            niega = true;
        }

        if (campo != null) {
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE Servidor SET ");
            sql.append(campo).append("=:dato ");
            sql.append("WHERE tipoIdentificacion=:tipoIdentificacion ");
            sql.append("AND numeroIdentificacion=:numeroIdentificacion");

            Query query = getEntityManager().createQuery(sql.toString());
            for (CargaMasivaServidorVO servidor : servidores) {
                query.setParameter("tipoIdentificacion", servidor.getTipoDocumento());
                query.setParameter("numeroIdentificacion", servidor.getDocumento());
                query.setParameter("dato", niega ? !servidor.getValor() : servidor.getValor());
                query.executeUpdate();
            }
        }
    }

    /**
     * Este metodo busca servidores con puestos asignados filtrando por nombre y unidades organizacionales
     *
     * @param nombre
     * @param codigosUnidadesOrg
     * @return
     * @throws DaoException
     */
    public List<Servidor> buscarServidorConPuestoPorNombreYUnidadOrganizacional(
            final String nombre, final String codigosUnidadesOrg) throws DaoException {
        try {

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT s.tipo_identificacion,");
            sql.append("       s.numero_identificacion,");
            sql.append("       s.apellidos_nombres,");
            sql.append("       s.fecha_ingreso_institucion,");
            sql.append("       s.jornada, ");
            sql.append("       s.id ");
            sql.append("FROM sch_proteus.servidor s");
            sql.append("     inner join sch_proteus.distributivo_detalles dd on dd.servidor_id= s.id ");
            sql.append("     inner join sch_proteus.distributivo d on d.id= dd.distributivo_id ");
            sql.append("     inner join sch_proteus.unidades_organizacionales uo on d.unidad_organizacional_id = uo.id ");
            sql.append("WHERE s.tipo='I' AND s.vigente='true' AND  s.apellidos_nombres like '%")
                    .append(nombre.toUpperCase()).append("%' ");
            sql.append("     AND uo.codigo IN (");
            sql.append("                        SELECT codigo FROM sch_proteus.unidades_organizacionales");
            sql.append("                                      WHERE codigo IN (").append(codigosUnidadesOrg).append(")) ");
            sql.append("ORDER BY s.apellidos_nombres ");

            Query query = getEntityManager().createNativeQuery(sql.toString());
            List<Object[]> lista = query.getResultList();
            List<Servidor> registros = new ArrayList<>();

            for (Object[] o : lista) {
                Servidor s = new Servidor();
                s.setTipoIdentificacion(o[0] == null ? null : o[0].toString());
                s.setNumeroIdentificacion(o[1] == null ? null : o[1].toString());
                s.setApellidosNombres(o[2] == null ? null : o[2].toString());
                s.setFechaIngresoInstitucion(o[3] == null ? null : (Date) o[3]);
                s.setJornada(o[4] == null ? 1 : new Integer(o[4].toString()));
                s.setId(o[5] == null ? 0 : new Long(o[5].toString()));
                registros.add(s);
            }
            return registros;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
