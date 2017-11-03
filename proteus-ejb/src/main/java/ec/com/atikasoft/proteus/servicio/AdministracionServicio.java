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

import ec.com.atikasoft.proteus.dao.ArchivoDao;
import ec.com.atikasoft.proteus.dao.*;
import ec.com.atikasoft.proteus.enums.CamposConfiguracionEnum;
import ec.com.atikasoft.proteus.enums.EstadoDatoAdiconalServidorEnum;
import ec.com.atikasoft.proteus.enums.FuncionesDesconcentradosEnum;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.*;
import ec.com.atikasoft.proteus.modelo.distributivo.ServidorCapacitacion;
import ec.com.atikasoft.proteus.modelo.distributivo.ServidorEvaluacion;
import ec.com.atikasoft.proteus.modelo.distributivo.ServidorExperiencia;
import ec.com.atikasoft.proteus.modelo.distributivo.ServidorInformacionMedica;
import ec.com.atikasoft.proteus.modelo.distributivo.ServidorInstruccion;
import ec.com.atikasoft.proteus.modelo.distributivo.ServidorParienteInstitucion;
import ec.com.atikasoft.proteus.modelo.nomina.CotizacionIessEspecial;
import ec.com.atikasoft.proteus.servicio.base.BaseServicio;
import ec.com.atikasoft.proteus.util.UtilArchivos;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.util.UtilMatematicas;
import ec.com.atikasoft.proteus.vo.EstadoAdministracionPuestoRegimenLaboralVO;
import ec.com.atikasoft.proteus.vo.InstitucionVO;
import ec.com.atikasoft.proteus.vo.TipoMovimientoAlertaVO;
import ec.com.atikasoft.proteus.vo.TipoMovimientoReglaVO;
import ec.com.atikasoft.proteus.vo.TipoMovimientoRequisitoVO;
import ec.com.atikasoft.proteus.vo.UsuarioVO;
import ec.com.atikasoft.proteus.vo.ValidacionTipoMovimientoRequisitoVO;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@Stateless
@LocalBean
@TransactionAttribute
public class AdministracionServicio extends BaseServicio {

    /**
     * DAO de Parametro Global.
     */
    @EJB
    private ParametroGlobalDao parametroGlobalDao;

    /**
     * DAO de Documento Habilitante.
     */
    @EJB
    private DocumentoHabilitanteDao documentoHabilitanteDao;

    /**
     * DAO de Ejercicio fiscal.
     */
    @EJB
    private EjercicioFiscalDao ejercicioFiscalDao;

    /**
     * DAO de Tipo de Movimiento.
     */
    @EJB
    private TipoMovimientoDao tipoMovimientoDao;

    /**
     * DAO de Tercero.
     */
    @EJB
    private TerceroDao terceroDao;

    /**
     * DAO de Tercero.
     */
    @EJB
    private TerceroNominaDetalleDao terceroNominaDetalleDao;

    /**
     * Dao de Grupo.
     */
    @EJB
    private GrupoDao grupoDao;

    /**
     * Dao de Clase.
     */
    @EJB
    private ClaseDao claseDao;

    /**
     * Dao de Requisito.
     */
    @EJB
    private RequisitoDao requisitoDao;

    /**
     * Dao de Alerta.
     */
    @EJB
    private AlertaDao alertaDao;

    /**
     * Dao de Regla.
     */
    @EJB
    private ReglaDao reglaDao;

    /**
     * Dao de TipoMovimientoRequisito.
     */
    @EJB
    private TipoMovimientoRequisitoDao tipoMovimientoRequisitoDao;

    /**
     * DAO de InstitucionEjercicioFiscal.
     */
    @EJB
    private InstitucionEjercicioFiscalDao institucionEjercicioFiscalDao;

    /**
     * Dao de validacion.
     */
    @EJB
    private ValidacionDao validacionDao;

    /**
     * Dao de movimiento.
     */
    @EJB
    private MovimientoDao movimientoDao;

    /**
     * Dao de ParametroInstitucionalCatalogoDao.
     */
    @EJB
    private ParametroInstitucionalCatalogoDao parametroInstitucionalCatalogoDao;

    /**
     * Dao de ParametroInstitucionalDao.
     */
    @EJB
    private ParametroInstitucionalDao parametroInstitucionalDao;

    /**
     * Dao de Archivo.
     */
    @EJB
    private ArchivoDao archivoDao;

    /**
     * Dao de Justificacion.
     */
    @EJB
    private JustificacionDao justificacionDao;

    /**
     * Dao de licencia Horario.
     */
    @EJB
    private LicenciaHorarioDao licenciaHorarioDao;

    /**
     * Dai de tipo movimiento precedencia.
     */
    @EJB
    private TipoMovimientoPrecedenciaDao tipoMovimientoPrecedenciaDao;

    /**
     * Dao de validacion bitacora.
     */
    @EJB
    private ValidacionBitacoraDao validacionBitacoraDao;

    /**
     * Dao de auxiliar del tramite.
     */
    @EJB
    private TramiteAuxiliarDao tramiteAuxiliarDao;

    /**
     * Dao de Tipo Movimient oDescentralizado.
     */
    @EJB
    private TipoMovimientoDescentralizadoDao tipoMovimientoDescentralizadoDao;

    /**
     * Dao de TipoMovimientoAlerta.
     */
    @EJB
    private TipoMovimientoAlertaDao tipoMovimientoAlertaDao;

    /**
     * Dao de TipoMovimientoRegla.
     */
    @EJB
    private TipoMovimientoReglaDao tipoMovimientoReglaDao;

    /**
     * Dao de Tramite.
     */
    @EJB
    private TramiteDao tramiteDao;

    /**
     * Dao de TipoMovimientoDelegado.
     */
    @EJB
    private TipoMovimientoDelegadoDao tipoMovimientoDelegadoDao;

    /**
     * DAO de Parametro Global.
     */
    @EJB
    private ModalidadLaboralDao modalidadLaboralDao;

    /**
     * DAO de Parametro Global.
     */
    @EJB
    private ModalidadNivelOcupacionalDao modalidadNivelOcupacionalDao;

    /**
     * Dao de denominacion Puesto Dao.
     */
    @EJB
    private DenominacionPuestoDao denominacionPuestoDao;

    /**
     * Dao de ubicacion geografica Dao.
     */
    @EJB
    private UbicacionGeograficaDao ubicacionGeograficaDao;

    /**
     * Servicio de gestion.
     */
    @EJB
    private TareaServicio tareaServicio;

    /**
     * Dao de Banco.
     */
    @EJB
    private BancoDao bancoDao;

    /**
     * Dao de TipoAnticipo.
     */
    @EJB
    private TipoAnticipoDao tipoAnticipoDao;

    /**
     * Dao de Rubro.
     */
    @EJB
    private RubroDao rubroDao;

    /**
     * Dao de Rubro-TipoNomina.
     */
    @EJB
    private RubroTipoNominaDao rubroTipoNominaDao;

    /**
     * Dao de Rubro-PeriodoNomina.
     */
    @EJB
    private RubroPeriodoNominaDao rubroPeriodoNominaDao;

    /**
     * Dao de Rubro.
     */
    @EJB
    private CodigoContableDao codigoContableDao;

    /**
     * Dao de Rubro.
     */
    @EJB
    private ClasificadorGastoDao clasificadorGastoDao;

    /**
     * Dao de Unidad organizacional.
     */
    @EJB
    private UnidadOrganizacionalDao unidadOrganizacionalDao;

    /**
     * Dao de Unidad presupuestaria.
     */
    @EJB
    private UnidadPresupuestariaDao unidadPresupuestariaDao;

    /**
     * DAO de Documento Habilitante.
     */
    @EJB
    private EstadoPuestoDao estadoPuestoDao;

    /**
     * DAO de Documento Habilitante.
     */
    @EJB
    private EstadoPersonalDao estadoPersonalDao;

    /**
     * DAO de Estado Administracion Puesto
     */
    @EJB
    private EstadoAdministracionPuestoDao estadoAdministracionPuestoDao;

    /**
     * DAO de Estado Administracion Puesto Regimen Laboral
     */
    @EJB
    private EstadoAdministracionPuestoRegimenLaboralDao estadoAdministracionPuestoRegimenLaboralDao;

    /**
     * DAO de Parametro Global.
     */
    @EJB
    private DatoAdicionalDao datoAdicionalDao;

    /**
     * Servicio Regimen
     */
    @EJB
    private RegimenLaboralServicio regimenLaboralServicio;

    /**
     * CatalogoDao
     */
    @EJB
    private CatalogoDao catalogoDao;

    /**
     * CatalogoDao
     */
    @EJB
    private TipoCatalogoDao tipoCatalogoDao;

    /**
     * Dao de Constantes.
     */
    @EJB
    private ConstanteDao constanteDao;

    /**
     * Dao de periodo nomina.
     */
    @EJB
    private PeriodoNominaDao periodoNominaDao;

    /**
     * Dao de metadataTabla.
     */
    @EJB
    private MetadataTablaDao metadataTablaDao;

    /**
     * Dao de camposAcceso.
     */
    @EJB
    private CampoAccesoDao campoAccesoDao;

    /**
     * Dao de metadataColumna.
     */
    @EJB
    private MetadataColumnaDao metadataColumnaDao;

    /**
     * Dao de metadataColumna.
     */
    @EJB
    private TipoNominaEstadoPuestoDao tipoNominaEstadoPuestoDao;

    /**
     * Dao de metadataColumna.
     */
    @EJB
    private TipoNominaEstadoPersonalDao tipoNominaEstadoPersonalDao;

    /**
     * Dao de metadataColumna.
     */
    @EJB
    private TipoNominaDao tipoNominaDao;

    /**
     * Dao de Variable.
     */
    @EJB
    private VariableDao variablePDao;

    /**
     * Dao de VariableCondicionDao.
     */
    @EJB
    private VariableCondicionDao variableCondicionDao;

    /**
     * Dao de Formula.
     */
    @EJB
    private FormulaDao formulaDao;

    /**
     * Dao de BeneficiarioInstitucion.
     */
    @EJB
    private BeneficiarioInstitucionalDao beneficiarioInstitucionalDao;

    /**
     * Dao de BeneficiarioEspecialDao.
     */
    @EJB
    private BeneficiarioEspecialDao beneficiarioEspecialDao;

    /**
     * Dao de UnidadAprobacionDao.
     */
    @EJB
    private UnidadAprobacionDao unidadAprobacionDao;

    /**
     * Dao de DistributivoDetalle.
     */
    @EJB
    private DistributivoDetalleDao distributivoDetalleDao;

    /**
     * dao CotizacionIessDao.
     */
    @EJB
    private CotizacionIessDao cotizacionIessDao;

    /**
     * dao CotizacionIessEspecialDao.
     */
    @EJB
    private CotizacionIessEspecialDao cotizacionIessEspecialDao;

    /**
     * ServidorInstitucionDao
     */
    @EJB
    private ServidorInstitucionDao servidorInstitucionDao;

    /**
     * servidor dao
     */
    @EJB
    private ServidorDao servidorDao;

    /**
     * Dao de cuentaBancariaDao.
     */
    @EJB
    private CuentaBancariaDao cuentaBancariaDao;

    /**
     * Dao de cuentaBancariaDao.
     */
    @EJB
    private PortalRhhDao portalRhhDao;

    /**
     * Dao de cuentaBancariaDao.
     */
    @EJB
    private NominaDao nominaDao;

    /**
     * Dao de NovedadDao.
     */
    @EJB
    private NovedadDao novedadDao;

    /**
     * Dao de NovedadDao.
     */
    @EJB
    private NovedadDetalleDao novedadDetalleDao;

    /**
     * Dao de institucion.
     */
    @EJB
    private ServidorCapacitacionDao servidorCapacitacionDao;
    /**
     * Servicio de reclutamiento.
     */
    @EJB
    private ReclutamientoServicio reclutamientoServicio;

    /**
     * Dao de serviEvaluacionDao.
     */
    @EJB
    private servidorEvaluacionDao serviEvaluacionDao;

    /**
     * Dao de serviEvaluacionDao.
     */
    @EJB
    private ServidorExperienciaDao servidorExperienciaDao;

    /**
     * Dao de servidorInstruccionDao.
     */
    @EJB
    private ServidorInstruccionDao servidorInstruccionDao;

    /**
     * Dao de servidorParienteInstitucionDao.
     */
    @EJB
    private ServidorParienteInstitucionDao servidorParienteInstitucionDao;

    /**
     * Dao de servidorInformacionMedicaDao.
     */
    @EJB
    private ServidorInformacionMedicaDao servidorInformacionMedicaDao;

    /**
     * Dao de feriados.
     */
    @EJB
    private FeriadoDao feriadoDao;

    /**
     * Dao de sectores.
     */
    @EJB
    private SectorDao sectorDao;

    /**
     * Dap de partidas.
     */
    @EJB
    private PartidaDao partidaDao;

    /**
     *
     */
    @EJB
    private DesconcentradoServicio desconcentradoServicio;

    /**
     *
     */
    @EJB
    private ImpuestoRentaDao impuestoRentaDao;

    @EJB
    private RolDao rolDao;

    @EJB
    private FichaPersonalServicio fichaPersonalServicio;

    /**
     * Dao de institucion
     */
    @EJB
    private InstitucionDao institucionDao;

    /**
     * Dao de relojUnidadOrganizacional
     */
    @EJB
    private RelojUnidadOrganizacionaljDao relojUnidadOrgDao;

    /**
     * Este metodo busca una institucion segun el codigo.
     *
     * @param codigo String
     * @param ejercicioFiscal
     * @return InstitucionEjercicioFiscal
     * @throws ServicioException ServicioException
     */
    public InstitucionEjercicioFiscal buscarInstitucionPorCodigoYEjercicioFiscal(
            final String codigo, final Long ejercicioFiscal) throws ServicioException {
        try {
            return institucionEjercicioFiscalDao.buscarPorCodigoYEjercicioFiscal(codigo, ejercicioFiscal);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo procesa la creacion de un parametro Global.
     *
     * @param p ParametroGlobal
     * @throws ServicioException ServicioException
     */
    public void guardarParametroGlobal(final ParametroGlobal p) throws ServicioException {
        try {
            p.setNemonico(p.getNemonico().toUpperCase());
            p.setNombre(p.getNombre().toUpperCase());
            parametroGlobalDao.crear(p);
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo procesa la actualizacion de un parametro Global.
     *
     * @param p ParametroGlobal
     * @throws ServicioException ServicioException
     */
    public void actulizarParametroGlobal(final ParametroGlobal p) throws ServicioException {
        try {
            p.setNemonico(p.getNemonico().toUpperCase());
            p.setNombre(p.getNombre().toUpperCase());
            parametroGlobalDao.actualizar(p);

        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método transacciona la eliminacion de un parametro global.
     *
     * @param p ParametroGlobal
     * @throws ServicioException ServicioException
     */
    public void eliminarParametroGlobal(final ParametroGlobal p) throws ServicioException {
        try {
            parametroGlobalDao.eliminar(p);
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método crea un documento Habilitante.
     *
     * @param documentoHabilitante DocumentoHabilitante
     * @throws ServicioException ServicioException
     */
    public void guardarDocumentoHabilitante(final DocumentoHabilitante documentoHabilitante) throws ServicioException {
        try {
            if (documentoHabilitante.getNumeroAutomatico() == null) {
                documentoHabilitante.setNumeroAutomatico(Boolean.FALSE);
            }
            documentoHabilitante.setNemonico(documentoHabilitante.getNemonico().toUpperCase());
            documentoHabilitante.setNombre(documentoHabilitante.getNombre().toUpperCase());
            documentoHabilitante.setContador(0l);
            documentoHabilitanteDao.crear(documentoHabilitante);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método actualiza un registro de Documento Habilitante.
     *
     * @param d DocumentoHabilitante
     * @throws ServicioException ServicioException
     */
    public void actualizarDocumentoHabilitante(final DocumentoHabilitante d) throws ServicioException {
        try {
            d.setNemonico(d.getNemonico().toUpperCase());
            d.setNombre(d.getNombre().toUpperCase());
            documentoHabilitanteDao.actualizar(d);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Esté método transacciona la eliminación de un documento habilitante.
     *
     * @param d DocuementoHabilitante
     * @throws ServicioException ServicioException
     */
    public void eliminarDocumentoHabilitante(final DocumentoHabilitante d) throws ServicioException {
        try {
            documentoHabilitanteDao.eliminar(d);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo transacciona la busqueda de parametro por nombre.
     *
     * @param nombre String
     * @return List
     * @throws ServicioException ServicioException
     */
    public List<ParametroGlobal> listarTodosParametroGlobalPorNombre(final String nombre) throws ServicioException {
        try {
            List<ParametroGlobal> parametros;
            if (nombre == null || nombre.isEmpty()) {
                parametros = parametroGlobalDao.buscarTodosPorNombre("");
            } else {
                parametros = parametroGlobalDao.buscarTodosPorNombre(nombre);
            }
            return parametros;
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            throw new ServicioException(e);
        }

    }

    /**
     * Este metodo transacciona la busqueda de documento habilitante por nombre.
     *
     * @param nombre String
     * @return List
     * @throws ServicioException ServicioException
     */
    public List<DocumentoHabilitante> listarTodosDocumentoHabilitantePorNombre(final String nombre)
            throws ServicioException {
        try {
            List<DocumentoHabilitante> lista;
            if (nombre == null || nombre.isEmpty()) {
                lista = documentoHabilitanteDao.buscarTodos();
            } else {
                lista = documentoHabilitanteDao.buscarTodosPorNombre(nombre.toUpperCase());
            }
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este metodo procesa la creacion de un ejercicio fiscal.
     *
     * @param ef
     * @throws DaoException
     */
    public void guardarEjercicioFiscal(final EjercicioFiscal ef) throws ServicioException {
        try {
            ef.setNombre(ef.getNombre().toUpperCase());
            ejercicioFiscalDao.crear(ef);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método transacciona la eliminacion de un ejercicio fiscal.
     *
     * @param ef
     * @throws DaoException
     */
    public void eliminarEjercicioFiscal(final EjercicioFiscal ef) throws ServicioException {
        try {
            ejercicioFiscalDao.eliminar(ef);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }

    }

    /**
     * Este metodo procesa la actualizacion de un ejercicio fiscal.
     *
     * @param ef
     * @throws DaoException
     */
    public void actualizarEjercicioFiscal(final EjercicioFiscal ef) throws ServicioException {
        try {
            ejercicioFiscalDao.actualizar(ef);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo transacciona la busqueda de parametro por nombre.
     *
     * @param nombre
     * @return
     * @throws DaoException
     */
    public List<EjercicioFiscal> listarTodosEjercicioFiscalPorNombre(final String nombre) throws ServicioException {

        try {
            List<EjercicioFiscal> lista;
            if (nombre == null || nombre.isEmpty()) {
                lista = ejercicioFiscalDao.buscarTodos();
            } else {
                lista = ejercicioFiscalDao.buscarTodosPorNombre(nombre.toUpperCase());
            }
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);

        }

    }

    /**
     * Este metodo transacciona la busqueda del ejercicio fiscal por su id
     *
     * @param id
     * @return
     * @throws ServicioException
     */
    public EjercicioFiscal obtenerEjercicioFiscalPorId(final Long id) throws ServicioException {

        try {
            return ejercicioFiscalDao.buscarPorId(id);

        } catch (Exception e) {
            throw new ServicioException(e);

        }

    }

    /**
     * Este metodo transacciona la busqueda de registros vigentes.
     *
     * @return
     * @throws ServicioException
     */
    public List<EjercicioFiscal> listarTodosEjercicioFiscalVigentes() throws ServicioException {

        try {
            return ejercicioFiscalDao.buscarTodos();
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este metodo procesa la creacion de un tipo de movimiento.
     *
     * @param tm
     * @throws DaoException
     */
    public void guardarTipoMovimiento(final TipoMovimiento tm) throws DaoException {
        try {
            tm.setNemonico(tm.getNemonico().toUpperCase());
            tm.setNombre(tm.getNombre().toUpperCase());
            tm.setFechaInicio(CamposConfiguracionEnum.SOLO_LECTURA.getCodigo());
            tm.setFechaFin(CamposConfiguracionEnum.SOLO_LECTURA.getCodigo());
            if (tm.getRenovacionComisionServicio() == null) {
                tm.setRenovacionComisionServicio(Boolean.FALSE);
            }
            tipoMovimientoDao.crear(tm);
        } catch (DaoException ex) {
            throw new DaoException(ex);
        }
    }

    /**
     * Este método transacciona la eliminacion de un tipo de movimiento.
     *
     * @param tm
     * @throws DaoException
     */
    public void eliminarTipoMovimiento(final TipoMovimiento tm) throws DaoException {
        try {
            tipoMovimientoDao.eliminar(tm);
        } catch (DaoException ex) {
            throw new DaoException(ex);
        }

    }

    /**
     * Este metodo procesa la actualizacion de un tipo de movimiento.
     *
     * @param tm
     * @throws DaoException
     */
    public void actualizarTipoMovimiento(final TipoMovimiento tm) throws DaoException {
        try {
            tm.setNemonico(tm.getNemonico().toUpperCase());
            tm.setNombre(tm.getNombre().toUpperCase());
            tm.setFechaInicio(CamposConfiguracionEnum.SOLO_LECTURA.getCodigo());
            tm.setFechaFin(CamposConfiguracionEnum.SOLO_LECTURA.getCodigo());
            tipoMovimientoDao.actualizar(tm);
        } catch (DaoException ex) {
            throw new DaoException(ex);
        }
    }

    /**
     * Este metodo transacciona la busqueda de parametro por nombre.
     *
     * @param nombre nombre a buscar
     * @return lista
     * @throws DaoException En caso de error
     */
    public List<TipoMovimiento> listarTodosTipoMovimientoPorNombre(final String nombre) throws ServicioException {
        try {
            List<TipoMovimiento> lista;
            if (nombre == null || nombre.isEmpty()) {
                lista = tipoMovimientoDao.buscarTodosActivos(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
            } else {
                lista = tipoMovimientoDao.buscarTodosPorNombre(nombre.toUpperCase());
            }
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método permite crear un grupo.
     *
     * @param grupo Grupo
     * @throws ServicioException
     */
    public void guardarGrupo(final Grupo grupo) throws ServicioException {
        try {
            grupo.setNemonico(grupo.getNemonico().toUpperCase());
            grupo.setNombre(grupo.getNombre().toUpperCase());
            grupoDao.crear(grupo);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método permite actualizar un grupo.
     *
     * @param grupo Grupo
     * @throws ServicioException
     */
    public void actualizarGrupo(final Grupo grupo) throws ServicioException {
        try {
            grupo.setNemonico(grupo.getNemonico().toUpperCase());
            grupo.setNombre(grupo.getNombre().toUpperCase());
            grupoDao.actualizar(grupo);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método procesa la eliminbación de un grupo.
     *
     * @param grupo Grupo
     * @throws ServicioException
     */
    public void eliminarGrupo(final Grupo grupo) throws ServicioException {
        try {
            grupoDao.eliminar(grupo);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método permite listar los grupos.
     *
     * @param nombre String
     * @return
     * @throws ServicioException
     */
    public List<Grupo> listarTodosGrupoPorNombre(final String nombre) throws ServicioException {
        try {
            List<Grupo> lista;
            if (nombre == null || nombre.isEmpty()) {
                lista = grupoDao.buscarTodosVigente();
            } else {
                lista = grupoDao.buscarTodosPorNombre(nombre.toUpperCase());
            }
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Este método permite listar los grupos por nemónico.
     *
     * @param nemonico
     * @return
     * @throws ServicioException
     */
    public List<Grupo> listarTodosGrupoPorNemonico(final String nemonico) throws ServicioException {
        try {
            List<Grupo> listaGruponemonico;
            listaGruponemonico = grupoDao.buscarTodosPorNemonico(nemonico.toUpperCase());
            return listaGruponemonico;
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Este método permite crear un requisito.
     *
     * @param requisito Requisito
     * @throws ServicioException
     */
    public void guardarRequisito(final Requisito requisito) throws ServicioException {
        try {
            requisito.setNemonico(requisito.getNemonico().toUpperCase());
            requisito.setNombre(requisito.getNombre().toUpperCase());
            requisitoDao.crear(requisito);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método permite actualizar un requisito.
     *
     * @param requisito Requisito
     * @throws ServicioException
     */
    public void actualizarRequisito(final Requisito requisito) throws ServicioException {
        try {
            requisito.setNemonico(requisito.getNemonico().toUpperCase());
            requisito.setNombre(requisito.getNombre().toUpperCase());
            requisitoDao.actualizar(requisito);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método procesa la eliminbación de un requisito.
     *
     * @param requisito Requisito
     * @throws ServicioException
     */
    public void eliminarRequisito(final Requisito requisito) throws ServicioException {
        try {
            requisitoDao.eliminar(requisito);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método permite listar los requisitos.
     *
     * @param nombre String
     * @return
     * @throws ServicioException
     */
    public List<Requisito> listarTodosRequisitoPorNombre(final String nombre) throws ServicioException {
        try {
            List<Requisito> lista;
            if (nombre == null || nombre.isEmpty()) {
                lista = requisitoDao.buscarTodos();
            } else {
                lista = requisitoDao.buscarTodosPorNombre(nombre.toUpperCase());
            }
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Este método permite listar los requisitos por Nemónico.
     *
     * @param nemonico String
     * @return
     * @throws ServicioException
     */
    public List<Requisito> listarTodosRequisitoPorNemonico(final String nemonico) throws ServicioException {
        try {
            List<Requisito> listaRequisitoNemonico;
            listaRequisitoNemonico = requisitoDao.buscarTodosPorNemonico(nemonico.toUpperCase());
            return listaRequisitoNemonico;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar los grupos.
     *
     * @return List<Grupo>
     */
    public List<Grupo> listarTodosGrupo() throws ServicioException {
        try {
            List<Grupo> listaGrupo = grupoDao.buscarTodosVigente();
            return listaGrupo;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar los grupos vigentes.
     *
     * @return List<Grupo>
     */
    public List<Grupo> listarTodosVigente() throws ServicioException {
        try {
            List<Grupo> listaGrupo = grupoDao.buscarTodosVigente();
            return listaGrupo;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar los grupos.
     *
     * @return List<Grupo>
     */
    public List<Clase> listarClasePorGrupoId(final Long grupoId) throws ServicioException {
        try {
            List<Clase> listaClase = (List<Clase>) claseDao.buscarClasePorGrupoId(grupoId);
            return listaClase;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este metodo transacciona la busqueda de parametro por nemonico.
     *
     * @param nemonico String
     * @return List
     * @throws ServicioException ServicioException
     */
    public List<Clase> listarClasePorNemonico(final String nemonico) throws ServicioException {
        try {
            List<Clase> listaReglasNemonico;
            listaReglasNemonico = claseDao.buscarPorNemonico(nemonico.toUpperCase());
            return listaReglasNemonico;
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Buscar clase por id
     *
     * @param idClase
     * @return
     * @throws ServicioException
     */
    public Clase buscarClasePorId(final Long idClase) throws ServicioException {
        try {
            return claseDao.buscarPorId(idClase);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar los grupos.
     *
     * @return List<Requisito>
     */
    public List<Requisito> listarTodosRequisito() throws ServicioException {
        try {
            List<Requisito> listaRequisitos = requisitoDao.buscarTodos();
            return listaRequisitos;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar los requisitos por id del grupo.
     *
     * @return List<Requisito>
     */
    public List<Requisito> listarRequisitoPorGrupoId(final Long grupoId) throws ServicioException {
        try {
            List<Requisito> listaRequisito = requisitoDao.buscarPorGrupoId(grupoId);
            return listaRequisito;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * este metodo guarda un nuevo registro de la reglas
     *
     * @param re
     * @throws ServicioException
     */
    public void guardarRegla(final Regla re) throws ServicioException {
        try {

            re.setNemonico(re.getNemonico().toUpperCase());
            re.setNombre(re.getNombre().toUpperCase());
            reglaDao.crear(re);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar todas las alertas.
     *
     * @return List<Alerta>
     */
    public List<Alerta> listarTodosAlertas() throws ServicioException {
        try {
            List<Alerta> listaAlerta = alertaDao.buscarTodos();
            return listaAlerta;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este metodo procesa la actualización de una regla.
     *
     * @param Regla
     * @throws ServicioException
     * @param cl
     */
    public void actualizarRegla(final Regla re) throws ServicioException {
        try {
            re.setNemonico(re.getNemonico().toUpperCase());
            re.setNombre(re.getNombre().toUpperCase());
            reglaDao.actualizar(re);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo procesa la eliminación de una regla.
     *
     * @param re
     * @throws ServicioException
     */
    public void eliminarRegla(final Regla re) throws ServicioException {
        try {
            reglaDao.eliminar(re);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar todas las reglas.
     *
     * @return List<Regla>
     */
    public List<Regla> listarTodosReglas() throws ServicioException {
        try {
            return reglaDao.buscarTodos();
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param nombre
     * @return
     * @throws ServicioException
     */
    public List<Regla> listarTodasReglaPorNombre(final String nombre) throws ServicioException {
        try {
            List<Regla> lista;
            if (nombre == null || nombre.isEmpty()) {
                lista = reglaDao.buscarTodos();
            } else {
                lista = reglaDao.buscarTodosPorNombre(nombre.toUpperCase());
            }
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    public List<Clase> listarTodosClase() throws ServicioException {
        try {
            return claseDao.buscarTodos();
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param cl
     * @throws ServicioException
     */
    public void guardarClase(final Clase cl) throws ServicioException {
        try {
            cl.setNemonico(cl.getNemonico().toUpperCase());
            cl.setNombre(cl.getNombre().toUpperCase());
            claseDao.crear(cl);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo procesa la actualización de una clase.
     *
     * @param Clase
     * @throws ServicioException
     * @param cl
     */
    public void actualizarClase(final Clase cl) throws ServicioException {
        try {
            cl.setNemonico(cl.getNemonico().toUpperCase());
            cl.setNombre(cl.getNombre().toUpperCase());
            claseDao.actualizar(cl);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo procesa la eliminación de una clase.
     *
     * @param cl
     * @throws ServicioException
     */
    public void eliminarClase(final Clase cl) throws ServicioException {
        try {
            claseDao.eliminar(cl);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     *
     * @param nombre
     * @return
     * @throws ServicioException
     */
    public List<Clase> listarTodosClasePorNombre(final String nombre) throws ServicioException {
        try {
            List<Clase> lista;
            if (nombre == null || nombre.isEmpty()) {
                lista = claseDao.buscarTodos();
            } else {
                lista = claseDao.buscarTodosPorNombre(nombre.toUpperCase());
            }
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * método para guardar el tipo de movimiento delegado.
     *
     * @param tmd TipoMovimientoDelegado
     * @throws ServicioException
     */
    public void guardarTipoMovimientoDelegado(final TipoMovimientoDelegado tmd) throws ServicioException {
        try {
            tipoMovimientoDelegadoDao.crear(tmd);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * método para guardar el tipo de movimiento delegado.
     *
     * @param tmd TipoMovimientoDelegado
     * @throws ServicioException
     */
    public String guardarTipoMovimientoDelegado(final Long tipoMovimientoId, final Long institucionId,
            final List<TipoMovimientoDelegado> tmds, final String usuario)
            throws ServicioException {
        try {
            InstitucionEjercicioFiscal ief = institucionEjercicioFiscalDao.buscarPorId(institucionId);
            StringBuilder buff = new StringBuilder();
            // eliminar los anteriores.
            List<TipoMovimientoDelegado> anteriores = tipoMovimientoDelegadoDao.buscarPorTipoMovimientoEInstitucion(
                    tipoMovimientoId, ief.getInstitucion().getId());
            for (TipoMovimientoDelegado anterior : anteriores) {
                Long tareas = tareaServicio.contar(anterior.getDelegadoCedula(), ief.getInstitucion().getCodigo(),
                        Boolean.TRUE);
                if (tareas.compareTo(0l) == 1) {
                    buff.append("El aprobador ").append(anterior.getDelegadoNombre()).append(" tiene ").append(tareas).
                            append(" pendientes de atender,reasigne.");
                } else {
                    anterior.setVigente(Boolean.FALSE);
                    anterior.setFechaActualizacion(new Date());
                    anterior.setUsuarioActualizacion(usuario);
                    tipoMovimientoDelegadoDao.actualizar(anterior);
                }
            }
            // registrar los nuevos.
            for (TipoMovimientoDelegado tmd : tmds) {
                guardarTipoMovimientoDelegado(tmd);
            }
            return buff.toString();
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * método para actualizar el tipo de movimiento delegado.
     *
     * @param tmd TipoMovimientoDelegado
     * @throws ServicioException
     */
    public void actualizarTipoMovimientoDelegado(final TipoMovimientoDelegado tmd) throws ServicioException {
        try {
            tipoMovimientoDelegadoDao.actualizar(tmd);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * método para elimina el tipo de movimiento delegado.
     *
     * @param tmd TipoMovimientoDelegado
     * @throws ServicioException
     */
    public void eliminarTipoMovimientoDelegado(final TipoMovimientoDelegado tmd) throws ServicioException {
        try {
            tipoMovimientoDelegadoDao.eliminar(tmd);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * método que busca el tipo de movimiento delegado por id.
     *
     * @param id Long
     * @return TipoMovimientoDelegado
     * @throws ServicioException
     */
    public TipoMovimientoDelegado buscarTipoMovimientoDelegadoPorId(final Long id) throws ServicioException {
        try {
            return tipoMovimientoDelegadoDao.buscarTipoMovimientoDelegadoporId(id).get(0);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método crea una alerta.
     *
     * @param documentoHabilitante DocumentoHabilitante
     * @throws ServicioException ServicioException
     */
    public void guardarAlerta(final Alerta a) throws ServicioException {
        try {
            a.setNemonico(a.getNemonico().toUpperCase());
            a.setNombre(a.getNombre().toUpperCase());
            a.setTipoPeriodo(a.getTipoPeriodo().toUpperCase());
            alertaDao.crear(a);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método actualiza un registro de una Alerta.
     *
     * @param a Alerta
     * @throws ServicioException ServicioException
     */
    public void actualizarAlerta(final Alerta a) throws ServicioException {
        try {
            a.setNemonico(a.getNemonico().toUpperCase());
            a.setNombre(a.getNombre().toUpperCase());
            a.setTipoPeriodo(a.getTipoPeriodo().toUpperCase());
            alertaDao.actualizar(a);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Esté método transacciona la eliminación de una alerta.
     *
     * @param a Alerta
     * @throws ServicioException ServicioException
     */
    public void eliminarAlerta(final Alerta a) throws ServicioException {
        try {
            alertaDao.eliminar(a);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo transacciona la busqueda de parametro por nombre.
     *
     * @param nombre String
     * @return List
     * @throws ServicioException ServicioException
     */
    public List<Alerta> listarTodosAlertaPorNombre(final String nombre) throws ServicioException {
        try {
            List<Alerta> listaalertas;
            if (nombre == null || nombre.isEmpty()) {
                listaalertas = alertaDao.buscarTodos();
            } else {
                listaalertas = alertaDao.buscarTodosPorNombre(nombre.toUpperCase());
            }
            return listaalertas;
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Este metodo transacciona la busqueda de parametro por nemonico.
     *
     * @param nemonico String
     * @return List
     * @throws ServicioException ServicioException
     */
    public List<Alerta> listarTodosAlertaPorNemonico(final String nemonico) throws ServicioException {
        try {
            List<Alerta> listaalertasnemonico;
            listaalertasnemonico = alertaDao.buscarTodosPorNemonico(nemonico.toUpperCase());
            return listaalertasnemonico;
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Este metodo transacciona la busqueda de parametro por nemonico.
     *
     * @param nemonico String
     * @return List
     * @throws ServicioException ServicioException
     */
    public List<DocumentoHabilitante> listarTodosDocumentoHabilitantePorNemonico(final String nemonico) throws
            ServicioException {
        try {
            List<DocumentoHabilitante> listadocumentoHabilitante;
            listadocumentoHabilitante = documentoHabilitanteDao.buscarTodosPorNemonico(nemonico.toUpperCase());
            return listadocumentoHabilitante;
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Este metodo busca los tipos movimientos por Id.
     *
     * @param id Long
     * @return TipoMovimiento
     * @throws ServicioException ServicioException
     */
    public TipoMovimiento buscarTipoMovimientoPorId(final Long id) throws ServicioException {
        try {
            TipoMovimiento tipoMovimiento = new TipoMovimiento();
            if (id != null) {
                tipoMovimiento = tipoMovimientoDao.buscarPorId(id);
            }
            return tipoMovimiento;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método busca los Tipos Movimientos activos y segun el id de clase.
     *
     * @param id Long
     * @return List
     * @throws ServicioException ServicioException
     */
    public List<TipoMovimiento> listarTipoMovimientoActivosPorClase(final Long id) throws ServicioException {
        try {
            return tipoMovimientoDao.buscarActivosPorClase(id);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Lista todos los tipos de movimientos que pertenecen a clases registradas
     * para este grupo
     *
     * @param idGrupo
     * @return
     * @throws ServicioException
     */
    public List<TipoMovimiento> listarTipoMovimientoActivosPorGrupo(final Long idGrupo) throws ServicioException {
        try {
            return tipoMovimientoDao.buscarPorIdGrupo(idGrupo);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método busca los Tipos Movimientos activos y segun el id de clase
     * sin el id del tipo de movimiento padre.
     *
     * @param idClase Long
     * @param idTipoMovimiento Long
     * @return List
     * @throws ServicioException ServicioException
     */
    public List<TipoMovimiento> listarTipoMovimientoActivosPorClaseSinPadre(final Long idClase,
            final Long idTipoMovimiento)
            throws ServicioException {
        try {
            return tipoMovimientoDao.buscarActivosPorClaseSinPadre(idClase, idTipoMovimiento);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método busca los Tipos Movimientos activos y segun el id de clase.
     *
     * @param id Long
     * @return List
     * @throws ServicioException ServicioException
     */
    public List<TipoMovimiento> listarTipoMovimientoDesconcentradoPorClase(final Long id) throws ServicioException {
        try {
            return tipoMovimientoDao.buscarPorTipoGestionDesconcentrado(id);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método busca los Tipos Movimientos activos y segun el id de clase.
     *
     * @param id Long
     * @return List
     * @throws ServicioException ServicioException
     */
    public List<TipoMovimiento> listarTipoMovimientoCentralizadosPorClase(final Long id) throws ServicioException {
        try {
            return tipoMovimientoDao.buscarPorTipoGestionCentralizado(id);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método crea un requisito por tipo de movimiento.
     *
     * @param tmr TipoMovimeintoRequisito
     * @throws ServicioException ServicioException
     */
    public void guardarTipoMovimientoRequisito(final TipoMovimientoRequisito tmr) throws ServicioException {
        try {
            tipoMovimientoRequisitoDao.crear(tmr);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método actualiza un requisito por tipo de movimiento.
     *
     * @param tmr TipoMovimeintoRequisito
     * @throws ServicioException ServicioException
     */
    public void actualizarTipoMovimientoRequisito(final TipoMovimientoRequisito tmr) throws ServicioException {
        try {
            tipoMovimientoRequisitoDao.actualizar(tmr);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Esté método transacciona la eliminación de una alerta.
     *
     * @param tmr TipoMovimeintoRequisito
     * @throws ServicioException ServicioException
     */
    public void eliminarTipoMovimientoRequisito(final TipoMovimientoRequisito tmr) throws ServicioException {
        try {
            tipoMovimientoRequisitoDao.eliminar(tmr);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Metodo que lista los tipos de movimientos requisitos por el tipo de
     * movimiento id.
     *
     * @param tipoMovimientoId id del tipo de movimiento
     * @return Lista de tipos de movimientos requisitos
     * @throws ServicioException En caso de error
     */
    public List<TipoMovimientoRequisito> listarTipoMovimientoRequisitoPorTipoMovimientoId(final Long tipoMovimientoId)
            throws ServicioException {
        try {
            List<TipoMovimientoRequisito> listaTipoMovimientoRequisito = tipoMovimientoRequisitoDao.
                    buscarPorTipoMovimientoId(tipoMovimientoId);
            return listaTipoMovimientoRequisito;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que lista los tipos de movimientos requisitos por el tipo de
     * movimiento id que se muestran al servidor público.
     *
     * @param tipoMovimientoId id del tipo de movimiento
     * @return Lista de tipos de movimientos requisitos
     * @throws ServicioException En caso de error
     */
    public List<TipoMovimientoRequisito> listarTipoMovimientoRequisitoPorTipoMovimientoIdServidorPublico(
            final Long tipoMovimientoId)
            throws ServicioException {
        try {
            List<TipoMovimientoRequisito> listaTipoMovimientoRequisito = tipoMovimientoRequisitoDao.
                    buscarPorTipoMovimientoIdServidorPublico(tipoMovimientoId);
            return listaTipoMovimientoRequisito;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este metodo busca el ejercicio fiscal activo.
     *
     * @return EjercicioFiscal
     * @throws ServicioException ServicioException
     */
    public EjercicioFiscal buscarEjercicioFiscalActivo() throws ServicioException {
        try {
            return ejercicioFiscalDao.buscarActivo();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            throw new ServicioException(e);
        }
    }

    /**
     * Busca el registro InstitucionEjercicioFiscal que contiene el ejercicio
     * fiscal fiscal que va a continuacion del actual. Util para la
     * planificacion de vacaciones
     *
     * @param institucionId
     * @return
     * @throws ServicioException
     */
    public InstitucionEjercicioFiscal buscarInstitucionEjercFiscalConProximoEjercFiscalPorInstitucion(
            Long institucionId) throws ServicioException {
        try {
            List<InstitucionEjercicioFiscal> listaInstitucionesEjerciciosFiscales
                    = listarTodosInstitucionesEjercicioFiscalPorInstitucion(institucionId);
            for (InstitucionEjercicioFiscal ief : listaInstitucionesEjerciciosFiscales) {
                if (ief.getEjercicioFiscal().getEsProximoEjercicio() != null
                        && ief.getEjercicioFiscal().getEsProximoEjercicio()) {
                    return ief;
                }
            }

        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            throw new ServicioException(e);
        }
        return null;
    }

    /**
     * Buscar por institucion ejercicio fiscal.
     *
     * @param institucionEjercicioFiscalId Identificador unico de la
     * institucion.
     * @return InstitucionEjercicioFiscal
     * @throws ServicioException Error en capa de servicio.
     */
    public InstitucionEjercicioFiscal buscarInstitucionEjercicioFiscal(final Long institucionEjercicioFiscalId) throws ServicioException {
        try {
            return institucionEjercicioFiscalDao.buscarPorId(institucionEjercicioFiscalId);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Buscar por institucion.
     *
     * @param institucionId Identificador unico de la institucion.
     * @return InstitucionEjercicioFiscal
     * @throws ServicioException Error en capa de servicio.
     */
    public Institucion buscarInstitucion(final Long institucionId) throws ServicioException {
        try {
            return institucionDao.buscarPorId(institucionId);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar todas las alertas vigentes.
     *
     * @return List<Alerta>
     */
    public List<Alerta> listarAlertasVigentes() throws ServicioException {
        try {
            List<Alerta> listaAlerta = alertaDao.buscarVigente();
            return listaAlerta;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar todas las alertas por tipo de movimiento
     * mediante el id del tipo de movimiento y en estado vigentes.
     *
     * @param tipoMovimientoId id del tipo de movimiento
     * @return List<TipoMovimientoAlerta>
     * @throws ServicioException en caso de error
     */
    public List<TipoMovimientoAlerta> listarTipoMovimientoAlertaPorTipoMovimientoId(final Long tipoMovimientoId)
            throws ServicioException {
        try {
            List<TipoMovimientoAlerta> listaTipoMovimientoAlerta = tipoMovimientoAlertaDao.buscarPorTipoMovimientoId(
                    tipoMovimientoId);
            return listaTipoMovimientoAlerta;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar todas las reglas vigentes.
     *
     * @return List<Regla>
     */
    public List<Regla> listarReglasVigentes() throws ServicioException {
        try {
            List<Regla> listaReglas = reglaDao.buscarVigentes();
            return listaReglas;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar todas las instituciones vigentes.
     *
     * @return List<Institucion>
     */
    public List<InstitucionEjercicioFiscal> listarInstitucionesVigentes() throws ServicioException {
        try {
            List<InstitucionEjercicioFiscal> listaInstitucionProteus = institucionEjercicioFiscalDao.buscarVigentes();
            return listaInstitucionProteus;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar todas las InstitucionesEjercicioFiscal
     * vigentes y no vigentes.
     *
     * @param idInstitucion Long
     * @return List<InstitucionEjercicioFiscal>
     */
    public List<InstitucionEjercicioFiscal> listarTodosInstitucionesEjercicioFiscalPorInstitucion(
            final Long idInstitucion)
            throws ServicioException {
        try {
            List<InstitucionEjercicioFiscal> listaInstitucionProteus = institucionEjercicioFiscalDao.
                    buscarTodosPorInstitucion(
                            idInstitucion);
            return listaInstitucionProteus;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar todas las InstitucionesEjercicioFiscal
     * vigentes y no vigentes.
     *
     * @param idInstitucion Long
     * @return List<InstitucionEjercicioFiscal>
     */
    public List<InstitucionEjercicioFiscal> listarTodosVigentesInstitucionesEjercicioFiscalPorInstitucion(
            final Long idInstitucion)
            throws ServicioException {
        try {
            List<InstitucionEjercicioFiscal> listaInstitucionProteus
                    = institucionEjercicioFiscalDao.buscarPorInstitucion(idInstitucion);
            return listaInstitucionProteus;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este metodo transacciona la busqueda de parametro por nemonico.
     *
     * @param nemonico String
     * @return List
     * @throws ServicioException ServicioException
     */
    public List<Regla> listarTodosReglaPorNemonico(final String nemonico) throws ServicioException {
        try {
            List<Regla> listaReglasNemonico;
            listaReglasNemonico = reglaDao.buscarTodosPorNemonico(nemonico.toUpperCase());
            return listaReglasNemonico;
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Metodo que busca las regas tipo movimiento por el id de tipo de
     * movimiento.
     *
     * @param tipoMovimientoId Id del tipo de movimiento
     * @return lista de reglas
     * @throws ServicioException En caso de error
     */
    public List<TipoMovimientoRegla> listarTipoMovimientoReglaPorTipoMovimientoId(final Long tipoMovimientoId)
            throws ServicioException {
        try {
            List<TipoMovimientoRegla> listaTipoMovimientoRegla = tipoMovimientoReglaDao.buscarPorTipoMovimiento(
                    tipoMovimientoId);
            return listaTipoMovimientoRegla;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de quitar la vigencia a los requisitos por tipo de
     * movimiento.
     *
     * @param usuario usuario logeado
     * @param tm tipo de movimiento
     * @throws ServicioException en caso de error
     */
    public void quitarVigenciaTipoMovimientoRequisito(final String usuario, final TipoMovimiento tm)
            throws ServicioException {
        try {
            List<TipoMovimientoRequisito> listaEliminar = listarTipoMovimientoRequisitoPorTipoMovimientoId(tm.getId());
            if (listaEliminar.size() > 0) {
                for (TipoMovimientoRequisito tmr : listaEliminar) {
                    tmr.setVigente(Boolean.FALSE);
                    tmr.setFechaActualizacion(new Date());
                    tmr.setUsuarioActualizacion(usuario);
                    tipoMovimientoRequisitoDao.actualizar(tmr);
                }
            }
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Metodo que se encarga de guardar los tipos de movimintos requisitos en la
     * base de datos.
     *
     * @param usuario Usuario Logeado
     * @param tm Tipo de movimiento
     * @param lista lista a guardar
     * @throws ServicioException En caso de error
     */
    public void guardarTipoMovimientoRequisito(final String usuario, final Long tipoMovimientoId,
            final List<TipoMovimientoRequisitoVO> lista) throws ServicioException {
        try {
            TipoMovimiento tipoMovimiento = tipoMovimientoDao.buscarPorId(tipoMovimientoId);
            for (TipoMovimientoRequisitoVO tmrvo : lista) {
                if (tmrvo.getTipoMovimientoRequisitoId() == null) {
                    if (tmrvo.getSeleccionar()) {
                        TipoMovimientoRequisito tmr = new TipoMovimientoRequisito();
                        tmr.setPresentaServidorPublico(tmrvo.getPresentaServidorPublico());
                        tmr.setSubirArchivoObligatorio(tmrvo.getSubirArchivoObligatorio());
                        tmr.setObligatorio(tmrvo.getObligatorio());
                        tmr.setRequisito(tmrvo.getRequisito());
                        tmr.setTipoMovimiento(tipoMovimiento);
                        tmr.setUsuarioCreacion(usuario);
                        tmr.setFechaCreacion(new Date());
                        tmr.setVigente(Boolean.TRUE);
                        tipoMovimientoRequisitoDao.crear(tmr);
                    }
                } else {
                    TipoMovimientoRequisito tmrequisito = tipoMovimientoRequisitoDao.buscarPorId(tmrvo.
                            getTipoMovimientoRequisitoId());
                    if (tmrvo.getSeleccionar()) {
                        tmrequisito.setPresentaServidorPublico(tmrvo.getPresentaServidorPublico());
                        tmrequisito.setSubirArchivoObligatorio(tmrvo.getSubirArchivoObligatorio());
                        tmrequisito.setObligatorio(tmrvo.getObligatorio());
                    } else {
                        tmrequisito.setVigente(Boolean.FALSE);
                    }
                    tmrequisito.setUsuarioActualizacion(usuario);
                    tmrequisito.setFechaActualizacion(new Date());
                    tipoMovimientoRequisitoDao.actualizar(tmrequisito);
                }
            }
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Guarda los tipos de movimientos precedencia.
     *
     * @param usuario String
     * @param tm TipoMovimiento
     * @param lista List<TipoMovimientoPrecedencia>
     * @throws ServicioException en caso de error
     */
    public void guardarTipoMovimientoPrecedencia(final String usuario, final TipoMovimiento tm,
            final List<TipoMovimientoPrecedencia> lista) throws ServicioException {
        try {
            quitarVigenciaTipoMovimientoPrecedencia(tm, usuario);
            for (TipoMovimientoPrecedencia tmp : lista) {
                TipoMovimientoPrecedencia tmprecedencia = tipoMovimientoPrecedenciaDao.
                        buscarPorTipoMovimientoYDependiente(
                                tm.getId(), tmp.getTipoMovimientoDependiente().getId());
                if (tmprecedencia != null) {
                    tmprecedencia.setOrdinal(tmp.getOrdinal());
                    tmprecedencia.setUsuarioActualizacion(usuario);
                    tmprecedencia.setFechaActualizacion(new Date());
                    tipoMovimientoPrecedenciaDao.actualizar(tmprecedencia);
                } else {
                    TipoMovimientoPrecedencia tmpNew = new TipoMovimientoPrecedencia();
                    tmpNew.setOrdinal(tmp.getOrdinal());
                    tmpNew.setTipoMovimiento(tm);
                    tmpNew.setTipoMovimientoDependiente(tmp.getTipoMovimientoDependiente());
                    tmpNew.setUsuarioCreacion(usuario);
                    tmpNew.setFechaCreacion(new Date());
                    tmpNew.setVigente(Boolean.TRUE);
                    tipoMovimientoPrecedenciaDao.crear(tmpNew);
                }
            }

        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Metodo que quita la vigencia de una lista de TipoMovimientoPrecedencia.
     *
     * @param tm Tipo Movimiento
     * @param usuario Usuario
     * @throws ServicioException En caso de error
     */
    public void quitarVigenciaTipoMovimientoPrecedencia(final TipoMovimiento tm, final String usuario)
            throws ServicioException {
        try {
            List<TipoMovimientoPrecedencia> listaPrecedencia = tipoMovimientoPrecedenciaDao.buscarPorTipoMovimiento(tm.
                    getId());
            if (listaPrecedencia.size() > 0) {
                for (TipoMovimientoPrecedencia tmpBorrar : listaPrecedencia) {
                    tmpBorrar.setVigente(Boolean.FALSE);
                    tmpBorrar.setFechaActualizacion(new Date());
                    tmpBorrar.setUsuarioActualizacion(usuario);
                }
            }
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Metodo que se encarga de quitar la vigencia a los reglas por tipo de
     * movimiento.
     *
     * @param usuario usuario logeado
     * @param tm tipo de movimiento
     * @throws ServicioException en caso de error
     */
    public void quitarVigenciaTipoMovimientoRegla(final String usuario, final TipoMovimiento tm)
            throws ServicioException {
        try {
            List<TipoMovimientoRegla> listaEliminar = listarTipoMovimientoReglaPorTipoMovimientoId(tm.getId());
            if (listaEliminar.size() > 0) {
                for (TipoMovimientoRegla tmr : listaEliminar) {
                    tmr.setVigente(Boolean.FALSE);
                    tmr.setFechaActualizacion(new Date());
                    tmr.setUsuarioActualizacion(usuario);
                    tipoMovimientoReglaDao.actualizar(tmr);
                }
            }
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Metodo que se encarga de guardar los tipos de movimintos reglas en la
     * base de datos.
     *
     * @param usuario Usuario Logeado
     * @param tm Tipo de movimiento
     * @param lista lista a guardar
     * @throws ServicioException En caso de error
     */
    public void guardarTipoMovimientoRegla(final String usuario, final TipoMovimiento tm,
            final List<TipoMovimientoReglaVO> lista) throws ServicioException {
        try {
            quitarVigenciaTipoMovimientoRegla(usuario, tm);
            for (TipoMovimientoReglaVO tmrvo : lista) {
                TipoMovimientoRegla tmr = new TipoMovimientoRegla();
                tmr.setObligatorio(tmrvo.getObligatorio());
                tmr.setJustificable(tmrvo.getJustificable());
                tmr.setRegla(tmrvo.getRegla());
                tmr.setTipoMovimiento(tm);
                tmr.setUsuarioCreacion(usuario);
                tmr.setFechaCreacion(new Date());
                tmr.setVigente(Boolean.TRUE);
                tipoMovimientoReglaDao.crear(tmr);
            }
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Metodo que se encarga de guardar las Instituciones en la base de datos.
     *
     * @param usuario Usuario Logeado
     * @param idEjercicioFiscal ejercicio fiscal
     * @param lista lista a guardar
     * @throws ServicioException En caso de error
     */
    public void guardarInstitucion(final String usuario, final Long idEjercicioFiscal,
            final List<InstitucionVO> lista) throws ServicioException {
        try {
//            for (InstitucionVO insvo : lista) {
//                InstitucionEjercicioFiscal ief = new InstitucionEjercicioFiscal();
//                ief.setCodigo(UtilCadena.concatenar(insvo.getInstitucionSiith().
//                        getCodigoCatastro(), ".", insvo.getInstitucionSiith().getId()));
//                ief.setInstitucionCoreId(insvo.getInstitucionSiith().getId());
//                ief.setEjercicioFiscal(new EjercicioFiscal(idEjercicioFiscal));
//                ief.setContadorActoAdministrativo(0L);
//                ief.setContadorRegistro(0L);
//                ief.setContadorTramite(0L);
//                ief.setRuc(insvo.getInstitucionSiith().getRuc());
//                ief.setUsuarioCreacion(usuario);
//                ief.setFechaCreacion(new Date());
//                ief.setVigente(Boolean.TRUE);
//                ief.setNombre(insvo.getInstitucionSiith().getNombre());
//
//                institucionEjercicioFiscalDao.crear(ief);
//        }
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Metodo que se encarga de quitar la vigencia a los alertas por tipo de
     * movimiento.
     *
     * @param usuario usuario logeado
     * @param tm tipo de movimiento
     * @throws ServicioException en caso de error
     */
    public void quitarVigenciaTipoMovimientoAlerta(final String usuario, final TipoMovimiento tm)
            throws ServicioException {
        try {
            List<TipoMovimientoAlerta> listaEliminar = listarTipoMovimientoAlertaPorTipoMovimientoId(tm.getId());
            if (listaEliminar.size() > 0) {
                for (TipoMovimientoAlerta tmr : listaEliminar) {
                    tmr.setVigente(Boolean.FALSE);
                    tmr.setFechaActualizacion(new Date());
                    tmr.setUsuarioActualizacion(usuario);
                    tipoMovimientoAlertaDao.actualizar(tmr);
                }
            }
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Metodo que se encarga de guardar los tipos de movimintos alertas en la
     * base de datos.
     *
     * @param usuario Usuario Logeado
     * @param tm Tipo de movimiento
     * @param lista lista a guardar
     * @throws ServicioException En caso de error
     */
    public Boolean guardarTipoMovimientoAlerta(final String usuario, final TipoMovimiento tm,
            final List<TipoMovimientoAlertaVO> lista) throws ServicioException {
        Boolean resultado = Boolean.FALSE;
        try {
            quitarVigenciaTipoMovimientoAlerta(usuario, tm);
            for (TipoMovimientoAlertaVO tmrvo : lista) {
                TipoMovimientoAlerta tmr = new TipoMovimientoAlerta();
                tmr.setMensaje(tmrvo.getMensaje());
                tmr.setAlerta(tmrvo.getAlerta());
                tmr.setTipoMovimiento(tm);
                tmr.setUsuarioCreacion(usuario);
                tmr.setFechaCreacion(new Date());
                tmr.setVigente(Boolean.TRUE);
                if (!tmr.getMensaje().isEmpty()) {
                    tipoMovimientoAlertaDao.crear(tmr);
                    resultado = Boolean.TRUE;
                } else {
                    resultado = Boolean.FALSE;
                }
            }
            return resultado;
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Metodo que se encarga de listar los tramites segun el estado y el
     * usuario.
     *
     * @param estado estado a buscar
     * @param usuario usuario
     * @param uo
     * @return List<Tramite> lista de tramites
     * @throws ServicioException en caso de error
     */
    public List<Tramite> buscarTramitesPorEstadoYUsuario(final String estado, final String usuario)
            throws ServicioException {
        try {
            List<Tramite> listaTramites = tramiteDao.buscarPorEstadoYUsuario(estado, usuario);
            return listaTramites;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de listar los tramites segun los parametros
     * enviados como filtros.
     *
     * @param codigoFase codigoFase a buscar
     * @param usuario usuario
     * @param codigoInstitucion codigo de la institucion logeado
     * @param ejercicioFiscal ejercicio fiscal de la institucion logeado
     * @param token campo abierto de busqueda
     * @param ordenar campo para ordenar
     * @param tipo tipo de ordenamiento
     * @return List<Tramite> lista de tramites
     * @throws ServicioException en caso de error
     */
    public List<Tramite> listarTramitePorFiltros(final String codigoFase, final String usuario,
            final String codigoInstitucion, final Integer ejercicioFiscal, final String token,
            final String ordenar, final String tipo) throws ServicioException {
        try {
            List<Tramite> listaTramites = tramiteDao.buscar(codigoFase, usuario, codigoInstitucion,
                    ejercicioFiscal, token, ordenar, tipo);
            return listaTramites;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de listar los tramites segun los estados
     * especificados.
     *
     * @return List<Tramite> lista de tramites
     * @throws ServicioException en caso de error
     */
    public List<Tramite> listarTramitePorEstados(final String[] estados) throws ServicioException {
        try {
            List<Tramite> listaTramites = tramiteDao.buscarPorEstados(estados);
            return listaTramites;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo lara buscar todos los movimientos degun los filtros.
     *
     * @param usuario.
     * @param codigoInstitucion.
     * @param ejercicioFiscal.
     * @param fechaVigenciaDesde..
     * @param fechaVigenciaHasta.
     * @return
     * @throws ServicioException
     */
    public List<Movimiento> listarMovimientoPorFiltros(final String usuario,
            final String codigoInstitucion, final Date fechaVigenciaDesde,
            Date fechaVigenciaHasta, Long tipoMovimientoId, String codigoFase,
            Date fechaEstadoDesde, Date fechaEstadoHasta) throws ServicioException {
        try {
            return movimientoDao.buscar(usuario, codigoInstitucion,
                    fechaVigenciaDesde, fechaVigenciaHasta, tipoMovimientoId, codigoFase,
                    fechaEstadoDesde, fechaEstadoHasta);
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Metodo que se encarga de guardar la validacion iterando una lista
     * recibida como parametro.
     *
     * @param lista Lista a iterar y guardar
     * @param usuario Usuario logeado en el sistema
     * @param tipoMovimientoRequisito tipo de movimiento requisito
     * @param movimiento Movimiento al que pertenece la validacion
     * @throws ServicioException En caso de error
     */
    public void guardarValidacion(final List<ValidacionTipoMovimientoRequisitoVO> lista,
            final TipoMovimientoRequisito tipoMovimientoRequisito, final Movimiento movimiento,
            final String usuario) throws ServicioException {
        try {
            for (ValidacionTipoMovimientoRequisitoVO vtmrvo : lista) {

                Validacion val = validacionDao.buscarPorTipoMovimientoRequisitoIdYMovimientoId(
                        vtmrvo.getTipoMovimientoRequisito().getId(), movimiento.getId());
                if (val != null) {
                    Validacion validacion = vtmrvo.getValidacion();
                    validacion.setArchivo(vtmrvo.getArchivo());
                    validacion.setTipoMovimientoRequisito(vtmrvo.getTipoMovimientoRequisito());
                    validacion.setMovimiento(movimiento);
                    validacion.setSustentoLegal(vtmrvo.getTipoMovimientoRequisito().getTipoMovimiento().getSustentoLegal());
                    validacion.setFechaActualizacion(new Date());
                    validacion.setUsuarioActualizacion(usuario);
                    validacion.setVigente(Boolean.TRUE);
                    if (validacion.getArchivo() != null) {
                        guardarArchivo(validacion.getArchivo(), usuario);
                    } else {
                        validacion.setArchivo(val.getArchivo());
                    }
                    validacionDao.actualizar(validacion);
                    guardarValidacionBitacora(validacion, usuario);
                } else {
                    Validacion validacion = vtmrvo.getValidacion();
                    validacion.setArchivo(vtmrvo.getArchivo());
                    validacion.setTipoMovimientoRequisito(vtmrvo.getTipoMovimientoRequisito());
                    validacion.setMovimiento(movimiento);
                    validacion.setSustentoLegal(vtmrvo.getTipoMovimientoRequisito().getTipoMovimiento().getSustentoLegal());
                    validacion.setFechaCreacion(new Date());
                    validacion.setUsuarioCreacion(usuario);
                    validacion.setVigente(Boolean.TRUE);
                    if (validacion.getArchivo() != null) {
                        guardarArchivo(validacion.getArchivo(), usuario);
                    } else {
                        validacion.setArchivo(null);
                    }
                    if (validacion.getObservacion() != null) {
                        validacion.setObservacion(new String(validacion.getObservacion().getBytes("ISO-8859-1")));
                    }
                    if (validacion.getNumeroDocumento() != null) {
                        validacion.setNumeroDocumento(new String(validacion.getNumeroDocumento().getBytes("ISO-8859-1")));
                    }
                    validacionDao.crear(validacion);
                    guardarValidacionBitacora(validacion, usuario);
                }
            }
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Metodo que setea el archivo como null.
     *
     * @param vtmrvo ValidacionTipoMovimientoRequisitoVO
     * @param movimiento Movimiento
     * @throws ServicioException En caso de error
     */
    public void eliminarArchivoValidacion(final ValidacionTipoMovimientoRequisitoVO vtmrvo,
            final Movimiento movimiento, final String usuario) throws ServicioException {
        try {
            Validacion val = validacionDao.buscarPorTipoMovimientoRequisitoIdYMovimientoId(
                    vtmrvo.getTipoMovimientoRequisito().getId(), movimiento.getId());
            if (val != null) {
                val.setArchivo(null);
                validacionDao.actualizar(val);
                guardarValidacionBitacora(val, usuario);
            }
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Metodo que se encarga de guardar la validacion bitacora a partir de una
     * validacion.
     *
     * @param validacion Validacion
     * @param usuario String
     * @throws ServicioException en caso de error
     */
    public void guardarValidacionBitacora(final Validacion validacion, final String usuario) throws ServicioException {
        try {
            ValidacionBitacora validacionBitacora = new ValidacionBitacora();
            validacionBitacora.setCumple(validacion.getCumple());
            validacionBitacora.setNumeroDocumento(validacion.getNumeroDocumento());
            validacionBitacora.setFechaDocumento(validacion.getFechaDocumento());
            validacionBitacora.setObservacion(validacion.getObservacion());
            validacionBitacora.setCalificacion(validacion.getCalificacion());
            validacionBitacora.setFechaCreacion(new Date());
            validacionBitacora.setFechaActualizacion(null);
            validacionBitacora.setUsuarioCreacion(usuario);
            validacionBitacora.setUsuarioActualizacion(null);
            validacionBitacora.setVigente(Boolean.TRUE);
            validacionBitacora.setTipoMovimientoRequisito(validacion.getTipoMovimientoRequisito());
            validacionBitacora.setMovimiento(validacion.getMovimiento());
            validacionBitacora.setSustentoLegal(validacion.getSustentoLegal());
            validacionBitacora.setArchivo(validacion.getArchivo());
            validacionBitacoraDao.crear(validacionBitacora);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Metodo que se encarga de guardar un archivo.
     *
     * @param archivo Archivo a guardar
     * @throws ServicioException en caso de error
     */
    public void guardarArchivo(final Archivo archivo, final String usuario) throws ServicioException {
        try {
            Archivo arch = archivo;
            arch.setFechaCreacion(new Date());
            arch.setUsuarioCreacion(usuario);
            arch.setVigente(Boolean.TRUE);
            archivoDao.crear(arch);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método actualiza una validacion.
     *
     * @param archivo Archivo en tipo byte
     * @param nombreArchivo Nombre del archivo a guardar
     * @param tipoArchivo Tipo del Archivo
     * @param tamanoArchivo Tamano del Archivo
     * @param usuario Usuario a guardar
     * @param validacion Entidad a guardar
     * @throws ServicioException ServicioException
     */
    public void actualizarValidacion(final byte[] archivo, final String nombreArchivo,
            final String tipoArchivo, final Long tamanoArchivo, final String usuario,
            final Validacion validacion) throws ServicioException {
        try {
            if (tipoArchivo.equals("application/pdf")) {
                Archivo arch = crearArchivo(archivo, nombreArchivo, tipoArchivo, tamanoArchivo, usuario);
                validacion.setArchivo(arch);
            }
            validacionDao.actualizar(validacion);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Crea un archivo.
     *
     * @return Archivo Archivo a guardar
     * @throws DaoException En caso de error
     */
    protected Archivo crearArchivo(final byte[] archivo, final String nombreArchivo,
            final String tipoArchivo, final Long tamanoArchivo, final String usuario)
            throws DaoException, IOException {
        Archivo a = new Archivo();
        a.setArchivo(archivo);
        a.setDescripcion(UtilCadena.concatenar(nombreArchivo, tipoArchivo, tamanoArchivo.toString()));
        a.setFechaCreacion(new Date());
        a.setNombre(nombreArchivo);
        a.setPalabrasClave(nombreArchivo);
        a.setUsuarioCreacion(usuario);
        a.setVigente(Boolean.TRUE);
        return archivoDao.crear(a);
    }

    public Archivo crearArchivo1(final byte[] archivo, final String nombreArchivo,
            final String tipoArchivo, final Long tamanoArchivo, final String usuario)
            throws DaoException, IOException {
        Archivo a = new Archivo();
        a.setArchivo(archivo);
        a.setDescripcion(UtilCadena.concatenar(nombreArchivo, tipoArchivo, tamanoArchivo.toString()));
        a.setFechaCreacion(new Date());
        a.setNombre(nombreArchivo);
        a.setPalabrasClave(nombreArchivo);
        a.setUsuarioCreacion(usuario);
        a.setVigente(Boolean.TRUE);
        return archivoDao.crear(a);
    }

    public Movimiento buscarMovimiento(final Long idMovimiento) throws ServicioException {
        try {
            return movimientoDao.buscarPorId(idMovimiento);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar una lista de tipos de movimientos por el
     * nemonico.
     */
    public List<TipoMovimiento> listarTodosTipoMovimientoPorNemonico(final String nemonico) throws ServicioException {
        try {
            List<TipoMovimiento> lista;
            lista = tipoMovimientoDao.buscarTodosPorNemonico(nemonico.toUpperCase());
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de listar los documentos habilitantes vigentes.
     */
    public List<DocumentoHabilitante> listarDocumentoHabilitanteVigentes() throws ServicioException {
        try {
            List<DocumentoHabilitante> lista = documentoHabilitanteDao.buscarActivos();
            return lista;
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método busca un parametro blobal, segun su nemonico.
     *
     * @param nemonico String
     * @return ParametroGlobal
     * @throws ServicioException ServicioException
     */
    public ParametroGlobal buscarParametroGlobalPorNemonico(final String nemonico) throws ServicioException {
        try {
            return parametroGlobalDao.buscarPorNemonico(nemonico);
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            throw new ServicioException(e);
        }
    }

    /**
     * Este método busca un Requisito, segun su nemonico.
     *
     * @param nemonico String
     * @return Requisito
     * @throws ServicioException ServicioException
     */
    public Requisito buscarRequisitoPorNemonico(final String nemonico) throws ServicioException {
        try {
            return requisitoDao.buscarPorNemonico(nemonico);
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            throw new ServicioException(e);
        }
    }

    /**
     * Este metodo transacciona la busqueda de parametro por nombre.
     *
     * @param nombre String
     * @return List
     * @throws ServicioException ServicioException
     */
    public List<InstitucionEjercicioFiscal> listarTodosInstitucionPorNombre(final String nombre) throws
            ServicioException {
        try {
            List<InstitucionEjercicioFiscal> instituciones = new ArrayList<InstitucionEjercicioFiscal>();
            instituciones.clear();
//            if (nombre == null || nombre.isEmpty()) {
//                instituciones = institucionDao.buscarTodos();
//            } else {
            instituciones = institucionEjercicioFiscalDao.buscarTodosPorNombre(nombre.toUpperCase());
//            }
            return instituciones;
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            throw new ServicioException(e);
        }

    }

    /**
     * Este metodo procesa la creacion de una institucion.
     *
     * @param p ParametroGlobal
     */
    public void guardarInstituciones(final InstitucionEjercicioFiscal ins) throws ServicioException {
        try {
            institucionEjercicioFiscalDao.crear(ins);
            institucionEjercicioFiscalDao.flush();
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo procesa la actualizacion de una institucion.
     *
     * @param ef
     * @throws DaoException
     */
    public void actualizarInstitucion(final InstitucionEjercicioFiscal ins) throws ServicioException {
        try {
            institucionEjercicioFiscalDao.actualizar(ins);
            institucionEjercicioFiscalDao.flush();
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar las validaciones por el id de tipo de
     * movimiento.
     *
     * @param tipoMovimientoId id del tipo de movimiento
     * @return List<Validacion> lista de validacion
     * @throws ServicioException en caso de error
     */
    public List<Validacion> listarValidacionPorTipoMovimientoId(final Long tipoMovimientoId) throws ServicioException {
        try {
            List<Validacion> lista = validacionDao.buscarPorTipoMovimientoId(tipoMovimientoId);
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar las validaciones por el id de movimiento.
     *
     * @param tipoMovimientoId id del tipo de movimiento
     * @return List<Validacion> lista de validacion
     * @throws ServicioException en caso de error
     */
    public List<Validacion> listarValidacionPorMovimientoId(final Long movimientoId) throws ServicioException {
        try {
            List<Validacion> lista = validacionDao.buscarPorMovimientoId(movimientoId);
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este metodo busca los movimientos por Id.
     *
     * @param id Long
     * @return Movimiento
     * @throws ServicioException ServicioException
     */
    public Movimiento buscarMovimientoPorId(final Long id) throws ServicioException {
        try {
            Movimiento movimiento = new Movimiento();
            if (id != null) {
                movimiento = movimientoDao.buscarPorId(id);
            }
            return movimiento;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este metodo transacciona la busqueda de parametro por nemonico.
     *
     * @param nemonico String
     * @return List
     * @throws ServicioException ServicioException
     */
    public List<ParametroInstitucionalCatalogo> listarTodosParametroInstitucionalCatalogoPorNemonico(
            final String nemonico) throws ServicioException {
        try {
            List<ParametroInstitucionalCatalogo> listaParametroInstitucionalCatalogoNemonico;
            listaParametroInstitucionalCatalogoNemonico
                    = parametroInstitucionalCatalogoDao.buscarTodosPorNemonico(nemonico.toUpperCase());
            return listaParametroInstitucionalCatalogoNemonico;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo para guardar los ParametroInstitucionalCatalogo.
     *
     * @param pi
     * @throws ServicioException
     */
    public void guardarParametroInstitucionalCatalogo(final ParametroInstitucionalCatalogo pi) throws ServicioException {
        try {
            pi.setNemonico(pi.getNemonico().toUpperCase());
            pi.setNombre(pi.getNombre().toUpperCase());
            parametroInstitucionalCatalogoDao.crear(pi);
            //parametroInstitucionalDao.crear(pr);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo procesa la actualización de una
     * ParametroInstitucionalCatalogo.
     *
     * @param ParametroInstitucionalCatalogo
     * @throws ServicioException
     * @param pi
     */
    public void actualizarParametroInstitucionalCatalogo(final ParametroInstitucionalCatalogo pi) throws
            ServicioException {
        try {
            pi.setNemonico(pi.getNemonico().toUpperCase());
            pi.setNombre(pi.getNombre().toUpperCase());
            parametroInstitucionalCatalogoDao.actualizar(pi);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo procesa la eliminación de un ParametroInstitucionalCatalogo.
     *
     * @param pi
     * @throws ServicioException
     */
    public void eliminarParametroInstitucionalCatalogo(final ParametroInstitucionalCatalogo pi) throws ServicioException {
        try {
            parametroInstitucionalCatalogoDao.eliminar(pi);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     *
     * @param nombre
     * @return
     * @throws ServicioException
     */
    public List<ParametroInstitucionalCatalogo> listarTodosParametroInstitucionalCatalogoPorNombre(final String nombre)
            throws ServicioException {
        try {
            List<ParametroInstitucionalCatalogo> lista;
            if (nombre == null || nombre.isEmpty()) {
                lista = parametroInstitucionalCatalogoDao.buscarTodos();
            } else {
                lista = parametroInstitucionalCatalogoDao.buscarTodosPorNombre(nombre.toUpperCase());
            }
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar los parametros Institucionales Catalogos.
     *
     * @return List<ParametroInstitucionalCatalogo>
     */
    public List<ParametroInstitucionalCatalogo> listarTodosParametroInstitucionalCatalogo() throws ServicioException {
        try {
            List<ParametroInstitucionalCatalogo> listaParametroInstitucionalCatalogo
                    = parametroInstitucionalCatalogoDao.buscarTodos();
            return listaParametroInstitucionalCatalogo;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Recupera todos los tipo de movimientos activo.
     */
    public List<TipoMovimiento> listarTiposMovimientos(final Boolean conRequisitos, final Boolean conAlertas,
            final Boolean conReglas) throws ServicioException {
        try {
            return tipoMovimientoDao.buscarTodosActivos(conRequisitos, conAlertas, conReglas);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Recupera todos los tipo de movimientos activo.
     */
    public List<TipoMovimiento> listarTiposMovimientosActivos() throws ServicioException {
        try {
            return tipoMovimientoDao.buscarTodosActivos();
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * metodo para guardar un parametro institucional con un archivo.
     *
     * @param pi
     * @param archivo
     * @throws ServicioException
     */
    public void guardarParametroInstitucional(final ParametroInstitucional pi, final Archivo archivo) throws
            ServicioException {
        try {
            Archivo crear = archivoDao.crear(archivo);
            pi.setArchivo(crear);
            parametroInstitucionalDao.crear(pi);
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    /**
     * metodo para actualizar un parametro institucional con el archivo.
     *
     * @param pi
     * @param archivo
     * @throws ServicioException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void actualizarParametroInstitucional(final ParametroInstitucional pi, final Archivo archivo) throws
            ServicioException {
        try {
            Archivo crear = archivoDao.crear(archivo);
            pi.setArchivo(crear);
            parametroInstitucionalDao.actualizar(pi);
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo procesa la actualizacion de un parametro Institucional .
     *
     * @param p ParametroGlobal
     */
    public void actualizarParametroInstitucional(final ParametroInstitucional p) throws ServicioException {
        try {
            parametroInstitucionalDao.actualizar(p);

        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método transacciona la eliminacion de un parametro Institucional.
     *
     * @param p ParametroGlobal
     * @throws ServicioException ServicioException
     */
    public void eliminarParametroInstitucional(final ParametroInstitucional p) throws ServicioException {
        try {
            parametroInstitucionalDao.eliminar(p);
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo busca los movimientos por Id.
     *
     * @param id Long
     * @return Movimiento
     * @throws ServicioException ServicioException
     */
    public ParametroInstitucional buscarParametroInstitucionalPorId(final Long id) throws ServicioException {
        try {
            ParametroInstitucional parametro = new ParametroInstitucional();
            if (id != null) {
                parametro = parametroInstitucionalDao.buscarPorId(id);
            }
            return parametro;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Buscar por institucion.
     *
     * @param institucionId Identificador unico de la institucion.
     * @return InstitucionEjercicioFiscal
     * @throws ServicioException Error en capa de servicio.
     */
    public ParametroInstitucional buscarParametroInstitucional(final Long parametroInstitucionalId) throws
            ServicioException {
        try {
            return parametroInstitucionalDao.buscarPorId(parametroInstitucionalId);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar todos parametros institucionales..
     *
     * @return List<Alerta>
     */
    public List<ParametroInstitucional> listarTodosParametroInstitucional(final Long institucionId) throws
            ServicioException {
        try {
            return parametroInstitucionalDao.buscarPorInstitucionId(institucionId);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Buscar por institucional catalogo.
     *
     * @param institucionId Identificador unico de la institucion.
     * @return InstitucionEjercicioFiscal
     * @throws ServicioException Error en capa de servicio.
     */
    public ParametroInstitucionalCatalogo buscarParametroInstitucionalCatalogo(
            final Long parametroInstitucionalCatalogoId) throws ServicioException {
        try {
            return parametroInstitucionalCatalogoDao.buscarPorId(parametroInstitucionalCatalogoId);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param institucionId
     * @return
     * @throws ServicioException
     */
    public List<ParametroInstitucionalCatalogo> buscarCatalogosSinParametrosInstitucionales(final Long institucionId)
            throws ServicioException {
        try {
            return parametroInstitucionalCatalogoDao.buscarSinParametrosInstitucionales(institucionId);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Recupera el parametro institucional.
     *
     * @param institucionId Identificador de la institucion.
     * @param nemonico Nemonico del catalogo.
     * @return Datos de parametro institucional.
     * @throws ServicioException Error de ejecucion.
     */
    public ParametroInstitucional buscarParametroIntitucional(final Long institucionId, final String nemonico) throws
            ServicioException {
        try {
            return parametroInstitucionalDao.buscarPorInstitucionYNemonico(institucionId, nemonico);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo para guardar la Justificacion.
     *
     * @param
     * @throws ServicioException
     */
    public void guardarJustificacion(final byte[] archivo, final String nombreArchivo,
            final String tipoArchivo, final Long tamanoArchivo, final String usuario,
            final Justificacion justificacion) throws ServicioException {
        try {
            Archivo arch = crearArchivo(archivo, nombreArchivo, tipoArchivo, tamanoArchivo, usuario);
            justificacion.setArchivo(arch);
            justificacion.setUsuarioCreacion(usuario);
            justificacion.setFechaCreacion(new Date());
            justificacion.setVigente(Boolean.TRUE);
            justificacionDao.crear(justificacion);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Metodo para guardar la Justificacion.
     *
     * @param
     * @throws ServicioException
     */
    public void actualizarJustificacion(final byte[] archivo, final String nombreArchivo,
            final String tipoArchivo, final Long tamanoArchivo, final String usuario,
            final Justificacion justificacion) throws ServicioException {
        try {
            if (archivo != null) {
                Archivo arch = crearArchivo(archivo, nombreArchivo, tipoArchivo, tamanoArchivo, usuario);
                justificacion.setArchivo(arch);
            }
            justificacion.setUsuarioActualizacion(usuario);
            justificacion.setFechaActualizacion(new Date());
            justificacionDao.actualizar(justificacion);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    public Justificacion buscarJustificacionPorMovimeintoYTipoMovimientoRegla(
            final Long movimientoId, final Long tipoMovimientoReglaId) throws ServicioException {
        try {
            Justificacion justificacion = new Justificacion();
            if (movimientoId != null && tipoMovimientoReglaId != null) {
                justificacion = justificacionDao.buscarPorMovimientoYTipoMovimientoRegla(movimientoId,
                        tipoMovimientoReglaId);
            }
            return justificacion;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo para caragar un archivo y guardar un parametro institucional.
     *
     * @param archivo
     * @param nombreArchivo
     * @param tipoArchivo
     * @param tamanoArchivo
     * @param usuario
     * @param parametroInstitucional
     * @throws ServicioException
     */
    public void guardarParametroInstitucional(final byte[] archivo, final String nombreArchivo,
            final String tipoArchivo, final Long tamanoArchivo, final String usuario,
            final ParametroInstitucional parametroInstitucional) throws ServicioException {
        try {
            if (tipoArchivo.equals("image/jpeg") || tipoArchivo.equals("image/png") || tipoArchivo.equals("image/gif")) {
                Archivo arch = crearArchivo(archivo, nombreArchivo, tipoArchivo, tamanoArchivo, usuario);
                parametroInstitucional.setArchivo(arch);
            } else {
                parametroInstitucional.setArchivo(null);
            }
            parametroInstitucionalDao.crear(parametroInstitucional);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * metodo para actualizar un parametro institucional con un archivo cargado.
     *
     * @param archivo
     * @param nombreArchivo
     * @param tipoArchivo
     * @param tamanoArchivo
     * @param usuario
     * @param parametroInstitucional
     * @throws ServicioException
     */
    public void actualizarParametroInstitucional(final byte[] archivo, final String nombreArchivo,
            final String tipoArchivo, final Long tamanoArchivo, final String usuario,
            final ParametroInstitucional parametroInstitucional) throws ServicioException {
        try {
            if (archivo != null) {
                if (tipoArchivo.equals("image/jpeg") || tipoArchivo.equals("image/png") || tipoArchivo.equals(
                        "image/gif")) {
                    Archivo arch = crearArchivo(archivo, nombreArchivo, tipoArchivo, tamanoArchivo, usuario);
                    parametroInstitucional.setArchivo(arch);
                } else {
                    parametroInstitucional.setArchivo(null);
                }
            }
            parametroInstitucionalDao.actualizar(parametroInstitucional);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    public ParametroInstitucional buscarParametroInstitucionalPorInstitucionYparametroInstitucionalCatalogo(
            final Long institucionId, final Long parametroInstitucionalCatalogoId) throws ServicioException {
        try {
            ParametroInstitucional parametroInstitucional = new ParametroInstitucional();
            if (institucionId != null && parametroInstitucionalCatalogoId != null) {
                parametroInstitucional = parametroInstitucionalDao.
                        buscarParametroInstitucionalPorInstitucionYparametroInstitucionalCatalogo(institucionId,
                                parametroInstitucionalCatalogoId);
            }
            return parametroInstitucional;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo para controlar los contraint de las tablas.
     *
     * @param idTablaPadreNombre
     * @param nombreTablaPadre
     * @param nombreTablaHija
     * @param id
     * @return
     * @throws ServicioException
     */
    public Boolean tieneRestricciones(final String idTablaPadreNombre, final String nombreTablaPadre,
            final String nombreTablaHija, final Long id) throws ServicioException {
        try {
            StringBuilder sql = new StringBuilder("SELECT th.id FROM ");
            sql.append(nombreTablaPadre);
            sql.append(" tp,");
            sql.append(nombreTablaHija);
            sql.append(" th WHERE th.vigente=true AND tp.id=th.");
            sql.append(idTablaPadreNombre);
            sql.append(" AND tp.id = ");
            sql.append(id);
            //parametroGlobalDao se lo usa solo para llamar al metodo getEntityManager.
            Query query = parametroGlobalDao.getEntityManager().createQuery(sql.toString());
            Boolean resultado = Boolean.FALSE;
            if (!(query.getResultList().isEmpty())) {
                resultado = Boolean.TRUE;
            }
            return resultado;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Recupera todos los parametros globales.
     *
     * @return
     * @throws ServicioException
     */
    public List<ParametroGlobal> buscarParametrosGlobales() throws ServicioException {
        try {
            return parametroGlobalDao.buscarTodos();
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar los tipos de movimientos.
     *
     * @return List<TipoMovimiento>
     */
    public List<TipoMovimiento> listarTiposMovimientos() throws ServicioException {
        try {
            List<TipoMovimiento> listaTipoMovimiento = tipoMovimientoDao.buscarTodosVigente();
            return listaTipoMovimiento;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar los tipos de movimientos.
     *
     * @return List<TipoMovimiento>
     */
    public List<TipoMovimiento> listarTiposMovimientosOrdenados() throws ServicioException {
        try {
            List<TipoMovimiento> listaTipoMovimiento = tipoMovimientoDao.buscarTodosVigenteOrdenados();
            return listaTipoMovimiento;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Método para buscar los delegados de un tipo de movimiento.
     *
     * @param tm TipoMovimiento
     * @return List
     * @throws ServicioException
     */
    public List<TipoMovimientoDelegado> listarTiposMovimientosDelegados(final TipoMovimiento tm,
            final Long institucionCoreId) throws ServicioException {
        try {
            return tipoMovimientoDelegadoDao.buscarPorTipoMovimientoEInstitucion(tm.getId(), institucionCoreId);
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Busca un delegado existente para el tipo de movimiento.
     *
     * @param tm TipoMovimiento
     * @param cedulaDelegado String
     * @return List
     * @throws ServicioException
     */
    public List<TipoMovimientoDelegado> listarTipoMovimientoDelegado(final TipoMovimiento tm,
            final String cedulaDelegado) throws ServicioException {
        try {
            List<TipoMovimientoDelegado> listaTipoMovimientoDelegado = tipoMovimientoDelegadoDao.
                    buscarTipoMovimientoDelegado(tm, cedulaDelegado);
            return listaTipoMovimientoDelegado;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Recupera un grupo dado su id.
     *
     * @param grupoId
     * @return
     * @throws ServicioException
     */
    public Grupo buscarGrupo(final Long grupoId) throws ServicioException {
        try {
            return grupoDao.buscarPorId(grupoId);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    public List<LicenciaHorario> listarLicenciaHorarioPorLicencia(final Long licenciaId) throws ServicioException {
        try {
            return licenciaHorarioDao.buscarPorLicencia(licenciaId);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este metodo crear Licencia Horarios.
     *
     * @param lista List
     * @param licencia Licencia
     * @throws ServicioException captura de errores
     */
    public void crearLicenciaHorario(final List<LicenciaHorario> lista, final Licencia licencia) throws
            ServicioException {
        try {
            licenciaHorarioDao.crear(lista, licencia);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de listar los tipos de movimientos precedencia por
     * el id de tipo de movimiento.F
     */
    public List<TipoMovimientoPrecedencia> listarTipoMovimientoPrecedenciaPorTipoMovimientoId(
            final Long idTipoMovimiento) throws ServicioException {
        try {
            return tipoMovimientoPrecedenciaDao.buscarPorTipoMovimiento(idTipoMovimiento);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Busca auxiliares por tramite.
     *
     * @param tramiteId
     * @return
     * @throws ServicioException
     */
    public TramiteAuxiliar buscarTramiteAuxiliarPorTramite(final Long tramiteId) throws ServicioException {
        try {
            return tramiteAuxiliarDao.buscarPorTramite(tramiteId);
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Este metodo lista las Unidades Organizacionales desconcentradas.
     *
     * @return Lista de instituciones
     */
    public List<TipoMovimientoDescentralizado> buscarTodosTipoMovimientoDescentralizado(final Long tipoMovimientoId,
            final Long institucionId)
            throws ServicioException {
        try {
            return tipoMovimientoDescentralizadoDao.buscarActivos(tipoMovimientoId, institucionId);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Recupera lista de movimientos descentralizados por Unidad Organizacional
     *
     * @param idUnidadOrganizacional
     * @return
     * @throws ServicioException error a nivel de servicio
     */
    public List<TipoMovimientoDescentralizado> buscarTipoMovimientoDescentralizadoPorUnidadOrganizacional(
            final Long idUnidadOrganizacional)
            throws ServicioException {
        try {
            return tipoMovimientoDescentralizadoDao.buscarPorUnidadOrganizacional(idUnidadOrganizacional);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este metodo guarda una lista de instituciones para descentralizarse.
     *
     * @param instituciones final
     * @throws DaoException captura de errores.
     */
    public void guardarTiposMovimientosDescentralizados(
            final List<TipoMovimientoDescentralizado> instituciones) throws DaoException {
        try {
            tipoMovimientoDescentralizadoDao.guardarTiposMovimientosDescentralizados(instituciones);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Este metodo actualiza un TipoMovimientoDescentralizado.
     *
     * @param tipoMovimientoDescentralizado TipoMovimientoDescentralizado
     * @throws DaoException DaoException captura de errores.
     */
    public void actualizarTipoMovimientoDescentralizado(
            final TipoMovimientoDescentralizado tipoMovimientoDescentralizado) throws DaoException {
        try {
            tipoMovimientoDescentralizadoDao.actualizar(tipoMovimientoDescentralizado);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar los tipos de movimientos para la
     * solicitud.
     *
     * @return List<TipoMovimiento>
     */
    public List<TipoMovimiento> listarTiposMovimientosConSolicitud(final String nemonico) throws ServicioException {
        try {
            return tipoMovimientoDao.buscarConSolicitud(nemonico);
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Recupera tipos de movimientos dado su modalidad laboral.
     */
    public List<TipoMovimiento> listarTiposDeMovimientosPorModalidadLaboralConsolicitud(final String nemonico,
            final Long modalidadLaboralId) throws ServicioException {
        try {
            return tipoMovimientoDao.buscarPorModalidadLaboralConSolicitud(nemonico, modalidadLaboralId);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Recupera tipos de movimientos dado el nemonico del grupo.
     *
     * @param nemonicoGrupo
     * @return
     * @throws ServicioException
     */
    public List<TipoMovimiento> buscarTiposMovimientosPorGrupo(final String nemonicoGrupo) throws ServicioException {
        try {
            return tipoMovimientoDao.buscarPorGrupo(nemonicoGrupo);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Permite el registro de una modalidad laboral
     *
     * @param modalidadLaboral modalidad laboral a registrar
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void guardarModalidadLaboral(final ModalidadLaboral modalidadLaboral) throws ServicioException {
        try {
            modalidadLaboral.setCodigo(modalidadLaboral.getCodigo().toUpperCase());
            modalidadLaboral.setNombre(modalidadLaboral.getNombre().toUpperCase());
            modalidadLaboralDao.crear(modalidadLaboral);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Permite el registro de la modalidad laboral - nivel ocupacional
     *
     * @param modalidadNivelLaboral registro a guardar
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void guardarModalidadNivelOcupacional(final ModalidadNivelOcupacional modalidadNivelLaboral) throws
            ServicioException {
        try {
            modalidadNivelLaboral.setIdModalidadLaboral(modalidadNivelLaboral.getModalidadLaboral().getId());
            modalidadNivelLaboral.setIdNivelOcupacional(modalidadNivelLaboral.getNivelOcupacional().getId());
            modalidadNivelOcupacionalDao.crear(modalidadNivelLaboral);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método procesa la eliminación de un registro de modalidad laboral -
     * nivel ocupacional >>>>>>> .r8279
     *
     * @param modalidadLaboral modalidad laboral a eliminar: Cambiar la vigencia
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void eliminarModalidadNivelOcupacional(final ModalidadNivelOcupacional modalidadNivelLaboral) throws
            ServicioException {
        try {
            modalidadNivelLaboral.setVigente(Boolean.FALSE);
            modalidadNivelOcupacionalDao.actualizar(modalidadNivelLaboral);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    public List<NivelOcupacional> listarNivelOcupacionalDeModalidad(final Long id) throws ServicioException {
        try {
            List<ModalidadNivelOcupacional> listaModalidadNivelOcupacional;
            List<NivelOcupacional> listaNiveles;

            listaModalidadNivelOcupacional = modalidadNivelOcupacionalDao.buscarVigentePorModalidad(id);

            listaNiveles = regimenLaboralServicio.listarTodosNivelOcupacionalVigentes();
            for (NivelOcupacional nivel : listaNiveles) {
                nivel.setSeleccionado(Boolean.FALSE);
                for (ModalidadNivelOcupacional reg : listaModalidadNivelOcupacional) {
                    if (nivel.getId().equals(reg.getIdNivelOcupacional())) {
                        nivel.setSeleccionado(Boolean.TRUE);
                    }
                }
            }
            return listaNiveles;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método permite actualizar una modalidad laboral
     *
     * @param modalidadLaboral modalidad laboral que se va a actualizar
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void actualizarModalidadLaboral(final ModalidadLaboral modalidadLaboral) throws ServicioException {
        try {
            modalidadLaboral.setCodigo(modalidadLaboral.getCodigo().toUpperCase());
            modalidadLaboral.setNombre(modalidadLaboral.getNombre().toUpperCase());
            modalidadLaboralDao.actualizar(modalidadLaboral);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método procesa la eliminación de un registro de la modalidad
     * laboral.
     *
     * @param modalidadLaboral modalidad laboral a eliminar: Cambiar la vigencia
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void eliminarModalidadLaboral(final ModalidadLaboral modalidadLaboral) throws ServicioException {
        try {
            modalidadLaboral.setVigente(false);
            modalidadLaboralDao.actualizar(modalidadLaboral);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método permite listar las modalidades laborales.
     *
     * @param nombre String del nombre de la modalidad para la busqueda
     * @return lista de Modalidad Laboral recuperadas
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<ModalidadLaboral> listarTodosModalidadLaboralPorNombre(final String nombre) throws ServicioException {
        try {
            List<ModalidadLaboral> lista;
            if (nombre == null || nombre.isEmpty()) {
                lista = modalidadLaboralDao.buscarVigente();
            } else {
                lista = modalidadLaboralDao.buscarTodosPorNombre(nombre.toUpperCase());
            }
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Este método permite listar los registros de modalidad laboral vigentes
     *
     * @return lista de Modalidad Laboral recuperadas
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<ModalidadLaboral> listarTodosModalidadLaboralVigentes() throws ServicioException {
        try {
            return modalidadLaboralDao.buscarVigente();

        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método permite listar los registros de modalidad laboral por codigo.
     *
     * @param codigo String del codigo de la modalidad para la busqueda
     * @return lista de Modalidad Laboral recuperadas
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<ModalidadLaboral> listarTodosModalidadLaboralPorCodigo(final String codigo) throws ServicioException {
        try {
            List<ModalidadLaboral> listaModalidadLabCodigo;
            listaModalidadLabCodigo = modalidadLaboralDao.buscarTodosPorCodigo(codigo.toUpperCase());
            return listaModalidadLabCodigo;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Busca las modalidades sin certificación presupuestaria en la unidad
     * presupuestaria especificada.
     *
     * @param idUnidadPresupuestaria id de la unidad presupuestaria
     * @return lista de Modalidad Laboral recuperadas
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<ModalidadLaboral> listarModalidadesSinCertificacionPorUnidadPresupuestaria(
            final Long idUnidadPresupuestaria) throws ServicioException {
        try {
            return modalidadLaboralDao.buscarModalidadesSinCertificacionPorUnidadPresupuestaria(idUnidadPresupuestaria);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param nombre
     * @return
     * @throws ServicioException
     */
    public List<DenominacionPuesto> listarTodosDenominacionPuestoPorNombre(final String nombre) throws ServicioException {
        try {
            List<DenominacionPuesto> lista;
            if (nombre == null || nombre.isEmpty()) {
                lista = denominacionPuestoDao.buscarVigente();
            } else {
                lista = denominacionPuestoDao.buscarTodosPorNombre(nombre.toUpperCase());
            }
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Lista denominaciones de puesto vigentes
     *
     * @return
     * @throws ServicioException
     */
    public List<DenominacionPuesto> listarTodosDenominacionPuestoVigentes() throws ServicioException {
        try {
            return denominacionPuestoDao.buscarVigente();
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * metodo para guardar la denominacion puesto.
     *
     * @param dp
     * @throws ServicioException
     */
    public void guardarDenominacionPuesto(final DenominacionPuesto dp) throws ServicioException {
        try {
            dp.setCodigo(dp.getCodigo().toUpperCase());
            dp.setNombre(dp.getNombre().toUpperCase());
            denominacionPuestoDao.crear(dp);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo procesa la actualización de una denominacion puesto.
     *
     * @param Clase
     * @throws ServicioException
     * @param cl
     */
    public void actualizarDenominacionPuesto(final DenominacionPuesto dp) throws ServicioException {
        try {

            dp.setCodigo(dp.getCodigo().toUpperCase());
            dp.setNombre(dp.getNombre().toUpperCase());
            denominacionPuestoDao.actualizar(dp);
            System.out.println("estado del vigenteeee***************" + dp.getVigente());
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo transacciona la busqueda de parametro por nemonico.
     *
     * @param nemonico String
     * @return List
     * @throws ServicioException ServicioException
     */
    public List<DenominacionPuesto> listarDenominacionPuestoPorNemonico(final String nemonico) throws ServicioException {
        try {
            List<DenominacionPuesto> listaReglasNemonico;
            listaReglasNemonico = denominacionPuestoDao.buscarPorNemonico(nemonico.toUpperCase());
            return listaReglasNemonico;
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Este metodo procesa la eliminación de una denominacion puesto.
     *
     * @param dp
     * @throws ServicioException
     */
    public void eliminarDenominacionPuesto(final DenominacionPuesto dp) throws ServicioException {
        try {
            dp.setVigente(Boolean.FALSE);
            denominacionPuestoDao.actualizar(dp);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Buscar Denominacion Puesto por ID
     *
     * @param id
     * @return
     * @throws ServicioException
     */
    public DenominacionPuesto buscarDenominacionPuesto(final Long id) throws ServicioException {
        try {
            return denominacionPuestoDao.buscarPorId(id);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar las ubicaciones geograficas.
     *
     * @return List<UbicacionGeografica>
     */
    public List<UbicacionGeografica> listarTodosubicacionGeografica() throws ServicioException {
        try {
            UbicacionGeografica ubicacionGeografica = new UbicacionGeografica();
            ubicacionGeografica.setCodigo("UBICACIÓN GEOGRÁFICA");
            ubicacionGeografica.setNombre("UBICACIÓN GEOGRÁFICA");
            List<UbicacionGeografica> listaUbicacion = ubicacionGeograficaDao.buscarActivosOrdenados();
            listaUbicacion.add(0, ubicacionGeografica);
            return listaUbicacion;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo para crear una ubicacion geografoca
     *
     * @param ug
     * @throws ServicioException
     */
    public void guardarUbicacionGeografica(final UbicacionGeografica ug) throws ServicioException {
        try {
            ug.setNombre(ug.getNombre().toUpperCase());
            ubicacionGeograficaDao.crear(ug);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Metodo para crear una ubicacion geografoca
     *
     * @param ug
     * @throws ServicioException
     */
    public void actualizarUbicacionGeografica(final UbicacionGeografica ug) throws ServicioException {
        try {
            //ug.setId(null);
            ug.setNombre(ug.getNombre().toUpperCase());
            ubicacionGeograficaDao.actualizar(ug);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo procesa la eliminación de ubicacion geografica.
     *
     * @param ug
     * @throws ServicioException
     */
    public void eliminarUbicacionGeografica(final UbicacionGeografica ug) throws ServicioException {
        try {
            ug.setVigente(Boolean.FALSE);
            ubicacionGeograficaDao.actualizar(ug);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar las ubicaciones geograficas.
     *
     * @return List<UbicacionGeografica>
     */
    public List<UbicacionGeografica> listarTodosIdUbicacionGeografica(final Long idUbicacionGeografica) throws
            ServicioException {
        try {
            List<UbicacionGeografica> listaUbicacion = ubicacionGeograficaDao.buscarPorPadre(idUbicacionGeografica);
            return listaUbicacion;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar las ubicaciones geograficas.
     *
     * @return List<UbicacionGeografica>
     */
    public List<UbicacionGeografica> listarTodosPaisesUbicacionGeografica() throws
            ServicioException {
        try {
            List<UbicacionGeografica> listaUbicacion = ubicacionGeograficaDao.buscarPaises();
            return listaUbicacion;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar las ubicaciones geograficas.
     *
     * @return List<UbicacionGeografica>
     */
    public UbicacionGeografica buscarUbicacionGeograficaId(final Long id) throws
            ServicioException {
        try {
            UbicacionGeografica ubicacionGeografica = new UbicacionGeografica();
            if (id != null) {
                ubicacionGeografica = ubicacionGeograficaDao.buscarPorId(id);
            }
            return ubicacionGeografica;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * metodo para guardar un banco
     *
     * @param banco registro a guardar
     * @throws ServicioException excepcion a nivel de negocio
     */
    public void guardarBanco(final Banco banco) throws ServicioException {
        try {
            banco.setCodigo(banco.getCodigo().toUpperCase());
            banco.setNombre(banco.getNombre().toUpperCase());
            banco.setVigente(Boolean.TRUE);
            bancoDao.crear(banco);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo procesa la actualización de un banco
     *
     * @param banco Instancia de banco
     * @throws ServicioException excepciones a nivel de negocio
     */
    public void actualizarBanco(final Banco banco) throws ServicioException {
        try {
            banco.setCodigo(banco.getCodigo().toUpperCase());
            banco.setNombre(banco.getNombre().toUpperCase());
            bancoDao.actualizar(banco);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo procesa la eliminación lógica de un banco
     *
     * @param banco registro a eliminar
     * @throws ServicioException excepcion a nivel de negocio
     */
    public void eliminarBanco(final Banco banco) throws ServicioException {
        try {
            banco.setVigente(Boolean.FALSE);
            bancoDao.actualizar(banco);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar todas los bancos vigentes
     *
     * @return List<Banco> listado obtenido
     */
    public List<Banco> listarTodosBancosVigentes() throws ServicioException {
        try {
            return bancoDao.buscarVigente();
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param nombre campo a buscar
     * @return lista obtenida
     * @throws ServicioException excepcion a nivel de negocio
     */
    public List<Banco> listarTodasBancosPorNombre(final String nombre) throws ServicioException {
        try {
            List<Banco> lista;
            if (nombre == null || nombre.isEmpty()) {
                lista = bancoDao.buscarVigente();
            } else {
                lista = bancoDao.buscarTodosPorNombre(nombre.toUpperCase());
            }
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Este método permite listar los registros de bancos por codigo.
     *
     * @param codigo String del banco del banco para la busqueda
     * @return lista debancos recuperados
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<Banco> listarTodosBancoPorCodigo(final String codigo) throws ServicioException {
        try {
            List<Banco> listaBanco;
            listaBanco = bancoDao.buscarTodosPorCodigo(codigo.toUpperCase());
            return listaBanco;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * metodo para guardar un tipoAnticipo
     *
     * @param tipoAnticipo registro a guardar
     * @throws ServicioException excepcion a nivel de negocio
     */
    public void guardarTipoAnticipo(final TipoAnticipo tipoAnticipo) throws ServicioException {
        try {
            tipoAnticipo.setCodigo(tipoAnticipo.getCodigo().toUpperCase());
            tipoAnticipo.setNombre(tipoAnticipo.getNombre().toUpperCase());
            tipoAnticipo.setVigente(Boolean.TRUE);
            tipoAnticipoDao.crear(tipoAnticipo);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo procesa la actualización de un tipoAnticipo
     *
     * @param tipoAnticipo Instancia de tipoAnticipo
     * @throws ServicioException excepciones a nivel de negocio
     */
    public void actualizarTipoAnticipo(final TipoAnticipo tipoAnticipo) throws ServicioException {
        try {
            tipoAnticipo.setCodigo(tipoAnticipo.getCodigo().toUpperCase());
            tipoAnticipo.setNombre(tipoAnticipo.getNombre().toUpperCase());
            tipoAnticipoDao.actualizar(tipoAnticipo);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo procesa la eliminación lógica de un tipoAnticipo
     *
     * @param tipoAnticipo registro a eliminar
     * @throws ServicioException excepcion a nivel de negocio
     */
    public void eliminarTipoAnticipo(final TipoAnticipo tipoAnticipo) throws ServicioException {
        try {
            tipoAnticipo.setVigente(Boolean.FALSE);
            tipoAnticipoDao.actualizar(tipoAnticipo);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar todas los tipoAnticipos vigentes
     *
     * @return List<TipoAnticipo> listado obtenido
     */
    public List<TipoAnticipo> listarTodosTipoAnticiposVigentes() throws ServicioException {
        try {
            return tipoAnticipoDao.buscarVigente();
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param nombre campo a buscar
     * @return lista obtenida
     * @throws ServicioException excepcion a nivel de negocio
     */
    public List<TipoAnticipo> listarTodasTipoAnticiposPorNombre(final String nombre) throws ServicioException {
        try {
            List<TipoAnticipo> lista;
            if (nombre == null || nombre.isEmpty()) {
                lista = tipoAnticipoDao.buscarVigente();
            } else {
                lista = tipoAnticipoDao.buscarTodosPorNombre(nombre.toUpperCase());
            }
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Este método permite listar los registros de tipoAnticipos por codigo.
     *
     * @param codigo String del tipoAnticipo del tipoAnticipo para la busqueda
     * @return lista detipoAnticipos recuperados
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<TipoAnticipo> listarTodosTipoAnticipoPorCodigo(final String codigo) throws ServicioException {
        try {
            List<TipoAnticipo> listaTipoAnticipo;
            listaTipoAnticipo = tipoAnticipoDao.buscarTodosPorCodigo(codigo.toUpperCase());
            return listaTipoAnticipo;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * *******************************************************
     */
    /**
     * metodo para guardar un variableP
     *
     * @param variableP registro a guardar
     * @throws ServicioException excepcion a nivel de negocio
     */
    public void guardarVariableP(final Variable variableP) throws ServicioException {
        try {
            variableP.setCodigo(variableP.getCodigo().toUpperCase());
            variableP.setNombre(variableP.getNombre().toUpperCase());
            variableP.setValidado(Boolean.FALSE);
            variablePDao.crear(variableP);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo procesa la actualización de un variableP
     *
     * @param variableP Instancia de variableP
     * @throws ServicioException excepciones a nivel de negocio
     */
    public void actualizarVariableP(final Variable variableP) throws ServicioException {
        try {
            variableP.setCodigo(variableP.getCodigo().toUpperCase());
            variableP.setNombre(variableP.getNombre().toUpperCase());
            variablePDao.actualizar(variableP);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo procesa la eliminación lógica de un variableP
     *
     * @param variableP registro a eliminar
     * @throws ServicioException excepcion a nivel de negocio
     */
    public void eliminarVariableP(final Variable variableP) throws ServicioException {
        try {
            variableP.setVigente(Boolean.FALSE);
            variablePDao.actualizar(variableP);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar todas los Variable vigentes
     *
     * @return List<VariableP> listado obtenido
     */
    public List<Variable> listarTodosVariablePVigentes() throws ServicioException {
        try {
            return variablePDao.buscarVigente();
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param nombre campo a buscar
     * @return lista obtenida
     * @throws ServicioException excepcion a nivel de negocio
     */
    public List<Variable> listarTodasVariablePPorNombre(final String nombre) throws ServicioException {
        try {
            List<Variable> lista;
            if (nombre == null || nombre.isEmpty()) {
                lista = variablePDao.buscarVigente();
            } else {
                lista = variablePDao.buscarTodosPorNombre(nombre.toUpperCase());
            }
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Este método permite listar los registros de Variable por codigo.
     *
     * @param codigo String del Variable del Variable para la busqueda
     * @return lista Variable recuperados
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<Variable> listarTodosVariablePPorCodigo(final String codigo) throws ServicioException {
        try {
            List<Variable> lista;
            lista = variablePDao.buscarTodosPorCodigo(codigo.toUpperCase());
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * metodo para guardar un VariableCondicion
     *
     * @param VariableCondicion registro a guardar
     * @throws ServicioException excepcion a nivel de negocio
     */
    public void guardarVariableCondicion(final VariableCondicion variableCondicion) throws ServicioException {
        try {
            variableCondicion.setVigente(Boolean.TRUE);
            variableCondicionDao.crear(variableCondicion);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo procesa la actualización de un variableCondicion
     *
     * @param variableCondicion Instancia de variableP
     * @throws ServicioException excepciones a nivel de negocio
     */
    public void actualizarVariableCondicion(final VariableCondicion variableCondicion) throws ServicioException {
        try {
            variableCondicionDao.actualizar(variableCondicion);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo procesa la eliminación lógica de un variableP
     *
     * @param variableCondicion registro a eliminar
     * @throws ServicioException excepcion a nivel de negocio
     */
    public void eliminarVariableCondicion(final VariableCondicion variableCondicion) throws ServicioException {
        try {
            variableCondicion.setVigente(Boolean.FALSE);
            variableCondicionDao.actualizar(variableCondicion);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar todas los Variable vigentes
     *
     * @return List<VariableCondicion> listado obtenido
     */
    public List<VariableCondicion> listarTodosVariableCondicionVigentes() throws ServicioException {
        try {
            return variableCondicionDao.buscarVigente();
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * metodo para guardar un Formula.
     *
     * @param formula registro a guardar
     * @throws ServicioException excepcion a nivel de negocio
     */
    public void guardarFormula(final Formula formula) throws ServicioException {
        try {
            formula.setCodigo(formula.getCodigo().toUpperCase());
            formula.setNombre(formula.getNombre().toUpperCase());
            formulaDao.crear(formula);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Permite obtener el listado de formulas en las cuales se utiliza el codigo
     * de la formula que se envia como parametro
     *
     * @param codigoFormula formula a buscar
     * @return lista de formulas
     */
    public List obtenerUsosEnFormula(String codigoFormula) throws ServicioException {
        try {
            return formulaDao.buscarTodosPorCodigoEnFormula(codigoFormula.toUpperCase());
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este metodo procesa la actualización de un Formula
     *
     * @param formula Instancia de formula
     * @throws ServicioException excepciones a nivel de negocio
     */
    public void actualizarFormula(final Formula formula) throws ServicioException {
        try {
            formula.setCodigo(formula.getCodigo().toUpperCase());
            formula.setNombre(formula.getNombre().toUpperCase());
            formulaDao.actualizar(formula);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo procesa la eliminación lógica de un formula
     *
     * @param formula registro a eliminar
     * @throws ServicioException excepcion a nivel de negocio
     */
    public void eliminarFormula(final Formula formula) throws ServicioException {
        try {
            formula.setVigente(Boolean.FALSE);
            formulaDao.actualizar(formula);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar todas los Variable vigentes
     *
     * @return List<Formula> listado obtenido
     */
    public List<Formula> listarTodosFormulaVigentes() throws ServicioException {
        try {
            return formulaDao.buscarVigente();
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param nombre campo a buscar
     * @return lista obtenida
     * @throws ServicioException excepcion a nivel de negocio
     */
    public List<Formula> listarTodasFormulaPorNombre(final String nombre) throws ServicioException {
        try {
            List<Formula> lista;
            if (nombre == null || nombre.isEmpty()) {
                lista = formulaDao.buscarVigente();
            } else {
                lista = formulaDao.buscarTodosPorNombre(nombre.toUpperCase());
            }
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Este método permite listar los registros de formula por codigo.
     *
     * @param codigo String de codigo formula para la busqueda
     * @return lista formula recuperados
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<Formula> listarTodosFormulaPorCodigo(final String codigo) throws ServicioException {
        try {
            List<Formula> lista;
            lista = formulaDao.buscarTodosPorCodigo(codigo.toUpperCase());
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * metodo para guardar un Rubro.
     *
     * @param rubro registro a guardar
     * @throws ServicioException excepcion a nivel de negocio
     */
    public void guardarRubro(final Rubro rubro) throws ServicioException {
        try {
            rubro.setCodigo(rubro.getCodigo().toUpperCase());
            rubro.setNombre(rubro.getNombre().toUpperCase());
            rubroDao.crear(rubro);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo procesa la actualización de un rubro
     *
     * @param rubro Instancia de rubro
     * @throws ServicioException excepciones a nivel de negocio
     */
    public void actualizarRubro(final Rubro rubro) throws ServicioException {
        try {
            rubro.setCodigo(rubro.getCodigo().toUpperCase());
            rubro.setNombre(rubro.getNombre().toUpperCase());
            rubroDao.actualizar(rubro);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo procesa la eliminación lógica de un rubro
     *
     * @param rubro registro a eliminar
     * @throws ServicioException excepcion a nivel de negocio
     */
    public void eliminarRubro(final Rubro rubro) throws ServicioException {
        try {
            rubro.setVigente(Boolean.FALSE);
            rubroDao.actualizar(rubro);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar todas los Rubros vigentes
     *
     * @return List<Rubro> listado obtenido
     */
    public List<Rubro> listarTodosRubrosVigentes() throws ServicioException {
        try {
            return rubroDao.buscarVigente();
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param nombre campo a buscar
     * @return lista obtenida
     * @throws ServicioException excepcion a nivel de negocio
     */
    public List<Rubro> listarTodosRubrosPorNombre(final String nombre) throws ServicioException {
        try {
            List<Rubro> lista;
            if (nombre == null || nombre.isEmpty()) {
                lista = rubroDao.buscarVigente();
            } else {
                lista = rubroDao.buscarTodosPorNombre(nombre.toUpperCase());
            }
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método permite listar los registros de rubros por codigo.
     *
     * @param codigo String de codigo rubro para la busqueda
     * @return lista rubros recuperados
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<Rubro> listarTodosRubroPorCodigo(final String codigo) throws ServicioException {
        try {
            List<Rubro> lista;
            lista = rubroDao.buscarTodosPorCodigo(codigo.toUpperCase());
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método permite listar los registros de rubros por tipo.
     *
     * @param tipo String de tipo: <I>ngreso de Servidores,Ingresos de
     * <A>nticipos, <D>escuentos,
     * <P>
     * Aporte institucional, <R>ecuperacion Anticipos
     * @return lista rubros recuperados
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<Rubro> listarTodosRubroPorTipo(final String tipo) throws ServicioException {
        try {
            List<Rubro> lista;
            lista = rubroDao.buscarPorTipo(tipo.toUpperCase());
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método permite listar los registros de rubros por tipo de
     * Beneficiario.
     *
     * @param tipoBeneficiario String de tipoBeneficiario E-Especial y N-Normal
     * @return lista rubros recuperados
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<Rubro> listarTodosRubroPorTipoBeneficiario(final String tipoBeneficiario) throws ServicioException {
        try {
            List<Rubro> lista;
            lista = rubroDao.buscarPorTipoBeneficiario(tipoBeneficiario.toUpperCase());
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método permite listar rubro por id
     *
     * @param id Id del rubro
     * @return rubros recuperados
     * @throws ServicioException excepcion a nivel de servicio
     */
    public Rubro listarRubroPorId(final Long id) throws ServicioException {
        try {
            return rubroDao.buscarPorId(id);

        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar todas los CodigosContables vigentes
     *
     * @return List<CodigoContable> listado obtenido
     */
    public List<CodigoContable> listarTodosCodigosContablesVigentes() throws ServicioException {
        try {
            return codigoContableDao.buscarVigentes();
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar todas los ClasificadorGastos vigentes
     *
     * @return List<ClasificadorGasto> listado obtenido
     */
    public List<ClasificadorGasto> listarTodosClasificadorGastoVigentes() throws ServicioException {
        try {
            return clasificadorGastoDao.buscarVigentes();
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este metodo procesa la eliminación lógica de un RubroTipoNomina
     *
     * @param r registro a eliminar
     * @throws ServicioException excepcion a nivel de negocio
     */
    public void eliminarRubroTipoNomina(final RubroTipoNomina r) throws ServicioException {
        try {
            r.setVigente(Boolean.FALSE);
            rubroTipoNominaDao.actualizar(r);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo procesa la eliminación lógica de un RubroPeriodoNomina
     *
     * @param r registro a eliminar
     * @throws ServicioException excepcion a nivel de negocio
     */
    public void eliminarRubroPeriodoNomina(final RubroPeriodoNomina r) throws ServicioException {
        try {
            r.setVigente(Boolean.FALSE);
            rubroPeriodoNominaDao.actualizar(r);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar todas los RubroPeriodoNomina vigentes
     *
     * @return List<RubroPeriodoNomina> listado obtenido
     */
    public List<RubroPeriodoNomina> listarTodosRubroPeriodoNominaVigentesPorRubro(final Long idRubro) throws
            ServicioException {
        try {
            return rubroPeriodoNominaDao.buscarVigentesPorRubro(idRubro);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar todas los RubroPeriodoNomina vigentes
     *
     * @return List<RubroTipoNomina> listado obtenido
     */
    public List<RubroTipoNomina> listarTodosRubroTipoNominaVigentesPorRubro(final Long idRubro) throws ServicioException {
        try {
            return rubroTipoNominaDao.buscarVigentesPorRubro(idRubro);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /*
     * Obtiene los registros de Tipo Nomina que estan en la tabla Rubro Tipo Nomina @param id del Rubro @return lista de
     * Tipos de Nomina con su campo seleccionado ya seteado @throws ServicioException
     */
    public List<TipoNomina> listarTipoNominaDeRubro(final Long id) throws ServicioException {
        try {
            List<RubroTipoNomina> listaRubroTN = listarTodosRubroTipoNominaVigentesPorRubro(id);;
            List<TipoNomina> lista = listarTodosTipoNominaVigentes();

            for (TipoNomina nivel : lista) {
                nivel.setSeleccionado(Boolean.FALSE);
                for (RubroTipoNomina reg : listaRubroTN) {
                    if (nivel.getId().equals(reg.getIdTipoNomina())) {
                        nivel.setSeleccionado(Boolean.TRUE);
                    }
                }
            }
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * metodo para guardar un RubroTipoNomina.
     *
     * @param r registro a guardar
     * @throws ServicioException excepcion a nivel de negocio
     */
    public void guardarRubroTipoNomina(final RubroTipoNomina r) throws ServicioException {
        try {
            rubroTipoNominaDao.crear(r);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * metodo para guardar un RubroPeriodoNomina.
     *
     * @param r registro a guardar
     * @throws ServicioException excepcion a nivel de negocio
     */
    public void guardarRubroPeriodoNomina(final RubroPeriodoNomina r) throws ServicioException {
        try {
            rubroPeriodoNominaDao.crear(r);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Obtiene los registros de Periodo Nomina que estan en la tabla Rubro
     * Periodo Nomina
     *
     * @param id del Rubro
     * @return lista de Periodo de Nomina con su campo seleccionado ya seteado
     * @throws ServicioException
     */
    public List<PeriodoNomina> listarPeriodoNominaDeRubro(final Long id) throws ServicioException {
        try {
            List<RubroPeriodoNomina> listaRubroPN = listarTodosRubroPeriodoNominaVigentesPorRubro(id);;
            List<PeriodoNomina> lista = listarTodosPeriodoNominaVigente();

            for (PeriodoNomina nivel : lista) {
                nivel.setSeleccionado(Boolean.FALSE);
                for (RubroPeriodoNomina reg : listaRubroPN) {
                    if (nivel.getId().equals(reg.getIdPeriodoNomina())) {
                        nivel.setSeleccionado(Boolean.TRUE);
                    }
                }
            }
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * metodo para guardar un BeneficiarioInstitucional.
     *
     * @param bi registro a guardar
     * @throws ServicioException excepcion a nivel de negocio
     */
    public void guardarBeneficiarioInstitucional(final BeneficiarioInstitucional bi) throws ServicioException {
        try {
            if (bi.getNombreBeneficiario() != null) {
                bi.setNombreBeneficiario(bi.getNombreBeneficiario().toUpperCase());
            }
            if (bi.getNumeroIdentificacion() != null) {
                bi.setNumeroIdentificacion(bi.getNumeroIdentificacion().toUpperCase());
            }
            beneficiarioInstitucionalDao.crear(bi);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo procesa la actualización de un BeneficiarioInstitucional
     *
     * @param bi Instancia de BeneficiarioInstitucional
     * @throws ServicioException excepciones a nivel de negocio
     */
    public void actualizarBeneficiarioInstitucional(BeneficiarioInstitucional bi) throws ServicioException {
        try {
            if (bi.getNombreBeneficiario() != null) {
                bi.setNombreBeneficiario(bi.getNombreBeneficiario().toUpperCase());
            }
            if (bi.getNumeroIdentificacion() != null) {
                bi.setNumeroIdentificacion(bi.getNumeroIdentificacion().toUpperCase());
            }
            beneficiarioInstitucionalDao.actualizar(bi);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo procesa la eliminación lógica de un BeneficiarioInstitucional
     *
     * @param bi registro a eliminar
     * @throws ServicioException excepcion a nivel de negocio
     */
    public void eliminarBeneficiarioInstitucional(final BeneficiarioInstitucional bi) throws ServicioException {
        try {
            bi.setVigente(Boolean.FALSE);
            beneficiarioInstitucionalDao.actualizar(bi);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar todas los BeneficiarioInstitucional
     * vigentes
     *
     * @return List<BeneficiarioInstitucional> listado obtenido
     */
    public List<BeneficiarioInstitucional> listarTodosBeneficiarioInstitucionalVigentes() throws ServicioException {
        try {
            return beneficiarioInstitucionalDao.buscarVigente();
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar todas los BeneficiarioInstitucional
     * vigentes por numero de identificacion
     *
     * @return List<BeneficiarioInstitucional> listado obtenido
     */
    public List<BeneficiarioInstitucional> listarTodosBeneficiarioInstitucionalPorNumeroIdentificacion(
            final String numeroIdentificacion) throws ServicioException {
        try {
            return beneficiarioInstitucionalDao.buscarTodosPorNumeroIdentificacion(numeroIdentificacion);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar todas los BeneficiarioEspecial vigentes
     * por numero de identificacion
     *
     * @return List<BeneficiarioEspecial> listado obtenido
     */
    public List<BeneficiarioEspecial> listarTodosBeneficiarioEspecialPorNumeroIdentificacion(
            final String numeroIdentificacion) throws ServicioException {
        try {
            return beneficiarioEspecialDao.buscarPorNumeroIdentificacion(numeroIdentificacion);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * metodo para guardar un BeneficiarioEspecial.
     *
     * @param bi registro a guardar
     * @throws ServicioException excepcion a nivel de negocio
     */
    public void guardarBeneficiarioEspecial(final BeneficiarioEspecial bi) throws ServicioException {
        try {
            if (bi.getNombreBeneficiario() != null) {
                bi.setNombreBeneficiario(bi.getNombreBeneficiario().toUpperCase());
            }
            if (bi.getNumeroIdentificacion() != null) {
                bi.setNumeroIdentificacion(bi.getNumeroIdentificacion().toUpperCase());
            }
            beneficiarioEspecialDao.crear(bi);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo procesa la actualización de un BeneficiarioEspecial
     *
     * @param bi Instancia de BeneficiarioEspecial
     * @throws ServicioException excepciones a nivel de negocio
     */
    public void actualizarBeneficiarioEspecial(BeneficiarioEspecial bi) throws ServicioException {
        try {
            if (bi.getNombreBeneficiario() != null) {
                bi.setNombreBeneficiario(bi.getNombreBeneficiario().toUpperCase());
            }
            if (bi.getNumeroIdentificacion() != null) {
                bi.setNumeroIdentificacion(bi.getNumeroIdentificacion().toUpperCase());
            }
            beneficiarioEspecialDao.actualizar(bi);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo procesa la eliminación lógica de un BeneficiarioEspecial
     *
     * @param bi registro a eliminar
     * @throws ServicioException excepcion a nivel de negocio
     */
    public void eliminarBeneficiarioEspecial(final BeneficiarioEspecial bi) throws ServicioException {
        try {
            bi.setVigente(Boolean.FALSE);
            beneficiarioEspecialDao.actualizar(bi);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar todas los BeneficiarioEspecial vigentes
     *
     * @return List<BeneficiarioEspecial> listado obtenido
     */
    public List<BeneficiarioEspecial> listarTodosBeneficiarioEspecialVigentes() throws ServicioException {
        try {
            return beneficiarioEspecialDao.buscarVigente();
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar el BeneficiarioEspecial por su Id
     *
     * @param id de beneficiarioInsitucion
     * @return registro obtenido
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public List<BeneficiarioEspecial> listarBeneficiarioEspecialPorIdBeneficiarioInstitucional(final Long id) throws
            ServicioException {
        try {
            return beneficiarioEspecialDao.buscarPorIdBeneficiarioInstitucion(id);
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar el BeneficiarioEspecial por su Id
     *
     * @param numeroIdentificacion
     * @param idRubro de rubro
     * @return registro obtenido
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public List<BeneficiarioEspecial> listarBeneficiarioEspecialPorNumeroIdentificacionYRubro(
            final String numeroIdentificacion, final Long idRubro) throws
            ServicioException {
        try {
            return beneficiarioEspecialDao.buscarPorNumeroIdentificacionYRubro(numeroIdentificacion, idRubro);
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar el BeneficiarioInstitucional por numero
     * de identificacion y rubro id
     *
     * @param numeroIdentificacion de numeroIdentificacion
     * @param idRubro de rubro
     * @return registro obtenido
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public List<BeneficiarioInstitucional> listarBeneficiarioInstitucionalPorNumeroIdentificacionYRubro(
            final String numeroIdentificacion, final Long idRubro) throws
            ServicioException {
        try {
            return beneficiarioInstitucionalDao.buscarPorNumeroIdentificacionYRubro(numeroIdentificacion, idRubro);
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar el BeneficiarioInstitucional por servidor
     * id y rubro id
     *
     * @param idServidor de numeroIdentificacion
     * @param idRubro de rubro
     * @return registro obtenido
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public List<BeneficiarioInstitucional> listarBeneficiarioInstitucionalPorServidorYRubro(
            final Long idServidor, final Long idRubro) throws
            ServicioException {
        try {
            return beneficiarioInstitucionalDao.buscarPorServidorYRubro(idServidor, idRubro);
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Buscar los registros de Detalles de Distributivos por el servidor
     *
     * @return lista obtenida
     * @throws ServicioException excepcion a nivel de negocio
     */
    public List<DistributivoDetalle> listarTodosDistributivoDetalleVigentes() throws ServicioException {
        try {
//                      return distributivoDetalleDao.buscarVigente();
            return null;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Buscar los registros de Detalles de Distributivos por identificacion del
     * servidor
     *
     * @param numeroDocumento campo a buscar
     * @return lista obtenida
     * @throws ServicioException excepcion a nivel de negocio
     */
    public List<DistributivoDetalle> listarTodosDistributivoDetallePorServidor(final Long idServidor)
            throws ServicioException {
        try {
            return distributivoDetalleDao.buscarPorServidor(idServidor);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * metodo para guardar un UnidadAprobacion.
     *
     * @param unidadAprobacion registro a guardar
     * @throws ServicioException excepcion a nivel de negocio
     */
    public void guardarUnidadAprobacion(final UnidadAprobacion unidadAprobacion) throws ServicioException {
        try {
            unidadAprobacionDao.crear(unidadAprobacion);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo procesa la actualizacion de una unidadAprobacion
     *
     * @param unidadAprobacion registro a eliminar
     * @throws ServicioException excepcion a nivel de negocio
     */
    public void actualizarUnidadAprobacion(final UnidadAprobacion unidadAprobacion) throws ServicioException {
        try {
            unidadAprobacionDao.actualizar(unidadAprobacion);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo procesa la eliminación lógica de una unidadAprobacion
     *
     * @param unidadAprobacion registro a eliminar
     * @throws ServicioException excepcion a nivel de negocio
     */
    public void eliminarUnidadAprobacion(final UnidadAprobacion unidadAprobacion) throws ServicioException {
        try {
            unidadAprobacion.setVigente(Boolean.FALSE);
            unidadAprobacionDao.actualizar(unidadAprobacion);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar todas las UnidadAprobacion vigentes
     *
     * @return List<UnidadAprobacion> listado obtenido
     */
    public List<UnidadAprobacion> listarTodosUnidadAprobacionVigentes() throws ServicioException {
        try {
            return unidadAprobacionDao.buscarVigente();
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Buscar los registrosde Unidades Aprobacion por Aprobador
     *
     * @param idAprobador campo a buscar
     * @return lista obtenida
     * @throws ServicioException excepcion a nivel de negocio
     */
    public List<UnidadAprobacion> listarTodosUnidadAprobacionPorAprobador(final Long idAprobador) throws
            ServicioException {
        try {
            return unidadAprobacionDao.buscarTodosPorAprobador(idAprobador);

        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Buscar los registros de Unidades Aprobacion por Unidad Organizacional
     *
     * @param idUnidad campo a buscar
     * @return lista obtenida
     * @throws ServicioException excepcion a nivel de negocio
     */
    public List<UnidadAprobacion> listarTodosUnidadAprobacionPorUnidad(final Long idUnidad) throws ServicioException {
        try {
            return unidadAprobacionDao.buscarTodosPorUnidadOrganizacional(idUnidad);

        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Buscar los registrosde Unidades Organizacionales por Id de Unidad
     * Organizacional y de su aprobador
     *
     * @param idAprobador identificador de la Unidad Aprobadora
     * @param idUnidad identificador de la Unidad Organizacional
     * @return lista obtenida
     * @throws ServicioException excepcion a nivel de negocio
     */
    public List<UnidadAprobacion> listarTodosUnidadAprobacionPorSiExiste(final Long idAprobador, final Long idUnidad)
            throws ServicioException {
        try {
            return unidadAprobacionDao.buscarTodosPorSiExiste(idAprobador, idUnidad);

        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * **************************************************
     */
    /**
     * Metodo que se encarga de buscar las unidad organizacional.
     *
     * @return List<UnidadOrganizacional>
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public List<UnidadOrganizacional> listarTodosUnidadOrganizacional() throws ServicioException {
        try {
            return unidadOrganizacionalDao.buscarActivosOrdenados();
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar las unidad organizacional.
     *
     * @param id
     * @return List<UnidadOrganizacional>
     */
    public List<UnidadOrganizacional> listarTodosUnidadOrganizacionalId(final Long id) throws ServicioException {
        try {
            return unidadOrganizacionalDao.buscarPorIdUbicacion(id);
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Recuepera las Undiades Organizacionales que tienen asigando un reloj
     * determinado
     *
     * @param relojId
     * @return
     * @throws ServicioException
     */
    public List<UnidadOrganizacional> listarUnidadesOrganizacionalesPorRelojId(
            final Long relojId) throws ServicioException {
        try {
            return unidadOrganizacionalDao.buscarPorRelojId(relojId);
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar las unidad organizacional.
     *
     * @param nivel
     * @param id
     * @return List<UnidadOrganizacional>
     * @throws ServicioException
     */
    public List<UnidadOrganizacional> listarPorNivel(final Long nivel, final Long id) throws ServicioException {
        try {
            return unidadOrganizacionalDao.buscarPorNivel(nivel, id);
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo para crear una unidad organizacional.
     *
     * @param ug
     * @throws ServicioException
     */
    public void guardarUnidadOrganizacional(final UnidadOrganizacional ug) throws ServicioException {
        try {
            ug.setNombre(ug.getNombre().toUpperCase());
            if (ug.getIdUnidadOrganizacional() != null) {
                UnidadOrganizacional uo = unidadOrganizacionalDao.buscarPorId(ug.getIdUnidadOrganizacional());
                ug.setUnidadOrganizacional(uo);
                List<String> n = new ArrayList<>();
                rutaUnidadOrganizacion(ug, n);
                Collections.reverse(n);
                StringBuilder ruta = new StringBuilder();
                for (String l : n) {
                    ruta.append(l).append(" / ");
                }
                ug.setRuta(ruta.toString());
            }
            unidadOrganizacionalDao.crear(ug);
            guardarRelojesUnidadesOrganizacionales(ug);

        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Metodo para actualizar una unidad organizacional.
     *
     * @param ug
     * @throws ServicioException
     */
    public void actualizarUnidadOrganizacional(final UnidadOrganizacional ug) throws ServicioException {
        try {
            ug.setNombre(ug.getNombre().toUpperCase());
            if (ug.getIdUnidadOrganizacional() != null) {
                UnidadOrganizacional uo = unidadOrganizacionalDao.buscarPorId(ug.getIdUnidadOrganizacional());
                ug.setUnidadOrganizacional(uo);
                List<String> n = new ArrayList<>();
                rutaUnidadOrganizacion(ug, n);
                Collections.reverse(n);
                StringBuilder ruta = new StringBuilder();
                for (String l : n) {
                    ruta.append(l).append(" / ");
                }
                ug.setRuta(ruta.toString());
            }

            unidadOrganizacionalDao.actualizar(ug);
            guardarRelojesUnidadesOrganizacionales(ug);

        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     *
     * @param uo
     * @param n
     */
    private void rutaUnidadOrganizacion(UnidadOrganizacional uo, List<String> n) {
        if (uo.getIdUnidadOrganizacional() != null) {
            n.add(uo.getNombre());
            rutaUnidadOrganizacion(uo.getUnidadOrganizacional(), n);
        }
    }

    /**
     * Guarda en base de datos los registros de RelojUnidadOrganizacional
     * asociados a la Undiad Organizacional
     */
    private void guardarRelojesUnidadesOrganizacionales(UnidadOrganizacional unidad) throws DaoException {
        try {
            List<RelojUnidadOrganizacional> registrosActuales = relojUnidadOrgDao
                    .buscarVigentesPorUnidadOrganizacionalId(unidad.getId());
            Date fechaCreacionActualizacion = unidad.getFechaActualizacion() != null
                    ? unidad.getFechaActualizacion() : unidad.getFechaCreacion();
            String usuarioCreacionActualizacion = unidad.getUsuarioActualizacion() != null
                    ? unidad.getUsuarioActualizacion() : unidad.getUsuarioCreacion();

            for (RelojUnidadOrganizacional ruo : registrosActuales) {
                ruo.setFechaActualizacion(fechaCreacionActualizacion);
                ruo.setUsuarioActualizacion(usuarioCreacionActualizacion);
                ruo.setVigente(Boolean.FALSE);
                relojUnidadOrgDao.actualizar(ruo);
            }

            for (RelojUnidadOrganizacional ruo : unidad.getRelojesUnidadesOrganizacionales()) {
                ruo.setReloj(ruo.getReloj());
                ruo.setUnidadOrganizacional(unidad);
                if (ruo.getId() == null) {
                    relojUnidadOrgDao.crear(ruo);
                } else {
                    relojUnidadOrgDao.actualizar(ruo);
                }
            }

        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * metodo para guardar un UnidadPresupuestaria
     *
     * @param unidad registro a guardar
     * @throws ServicioException excepcion a nivel de negocio
     */
    public void guardarUnidadPresupuestaria(final UnidadPresupuestaria unidad) throws ServicioException {
        try {
            unidad.setNombre(unidad.getNombre().toUpperCase());
            unidad.setSociedad(unidad.getSociedad().toUpperCase());
            unidad.setCentroCosto(unidad.getCentroCosto().toUpperCase());
            unidad.setCentroGestor(unidad.getCentroGestor().toUpperCase());
            unidad.setProyecto(unidad.getProyecto().toUpperCase());
            unidadPresupuestariaDao.crear(unidad);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo procesa la actualización de un UnidadPresupuestaria
     *
     * @param unidad Instancia de UnidadPresupuestaria
     * @throws ServicioException excepciones a nivel de negocio
     */
    public void actualizarUnidadPresupuestaria(final UnidadPresupuestaria unidad) throws ServicioException {
        try {
            unidad.setNombre(unidad.getNombre().toUpperCase());
            unidad.setSociedad(unidad.getSociedad().toUpperCase());
            unidad.setCentroCosto(unidad.getCentroCosto().toUpperCase());
            unidad.setCentroGestor(unidad.getCentroGestor().toUpperCase());
            unidad.setProyecto(unidad.getProyecto().toUpperCase());
            unidadPresupuestariaDao.actualizar(unidad);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo procesa la eliminación lógica de un UnidadPresupuestaria
     *
     * @param unidad registro a eliminar
     * @throws ServicioException excepcion a nivel de negocio
     */
    public void eliminarUnidadPresupuestaria(final UnidadPresupuestaria unidad) throws ServicioException {
        try {
            unidad.setVigente(Boolean.FALSE);
            unidadPresupuestariaDao.actualizar(unidad);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar todas los UnidadPresupuestaria vigentes
     * por codigo
     *
     * @param codigo
     * @return List<UnidadPresupuestaria> listado obtenido
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public List<UnidadPresupuestaria> listarTodosUnidadesPresupuestariasPorCodigo(final String codigo)
            throws ServicioException {
        try {
            return unidadPresupuestariaDao.buscarPorCodigo(codigo);
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar todas los UnidadPresupuestaria vigentes
     * por codigo y sector
     *
     * @param codigo
     * @return List<UnidadPresupuestaria> listado obtenido
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public List<UnidadPresupuestaria> listarTodosUnidadesPresupuestariasPorCodigoYSector(final String codigo, final Sector sector)
            throws ServicioException {
        try {
            return unidadPresupuestariaDao.buscarPorCodigoYSector(codigo, sector.getId());
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar todas los UnidadPresupuestaria vigentes
     *
     * @return List<UnidadPresupuestaria> listado obtenido
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public List<UnidadPresupuestaria> listarTodosUnidadesPresupuestarias()
            throws ServicioException {
        try {
            return unidadPresupuestariaDao.buscarVigentes();
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param nombre
     * @return
     * @throws ServicioException
     */
    public List<EstadoPuesto> listarTodosEstadoPuestoPorNombre(final String nombre) throws ServicioException {
        try {
            List<EstadoPuesto> lista;
            if (nombre == null || nombre.isEmpty()) {
                lista = estadoPuestoDao.buscarVigente();
            } else {
                lista = estadoPuestoDao.buscarTodosPorNombre(nombre.toUpperCase());
            }
            return lista;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Este metodo procesa la eliminación de ubicacion geografica.
     *
     * @param uo
     * @throws ServicioException
     */
    public void eliminarUnidadOrganizacional(final UnidadOrganizacional uo) throws ServicioException {
        try {
            uo.setVigente(Boolean.FALSE);
            unidadOrganizacionalDao.actualizar(uo);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     *
     * @param nombre
     * @return
     * @throws ServicioException
     */
    public List<UnidadOrganizacional> listarTodosUnidadOrganizacionalPorNombre(final String nombre) throws
            ServicioException {
        try {
            List<UnidadOrganizacional> lista;
            if (nombre == null || nombre.isEmpty()) {
                lista = unidadOrganizacionalDao.buscarVigentes();
            } else {
                lista = unidadOrganizacionalDao.buscarTodosPorNombre(nombre.toUpperCase());
            }
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Obtiene todos los registros vigentes
     *
     * @return
     * @throws ServicioException
     */
    public List<UnidadOrganizacional> listarTodosUnidadOrganizacionalVigentes() throws ServicioException {
        try {
            return unidadOrganizacionalDao.buscarVigentes();

        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Este metodo transacciona la busqueda de parametro por nemonico.
     *
     * @param nemonico String
     * @return List
     * @throws ServicioException ServicioException
     */
    public List<EstadoPuesto> listarEstadoPuestoPorNemonico(final String codigo) throws ServicioException {
        try {
            List<EstadoPuesto> listaReglasNemonico;
            listaReglasNemonico = estadoPuestoDao.buscarPorNemonico(codigo.toUpperCase());
            return listaReglasNemonico;
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     *
     * @param cl
     * @throws ServicioException
     */
    public void guardarEstadopuesto(final EstadoPuesto cl) throws ServicioException {
        try {
            cl.setCodigo(cl.getCodigo().toUpperCase());
            cl.setNombre(cl.getNombre().toUpperCase());
            if (cl.isPredeterminado()) {
                estadoPuestoDao.removerPredeterminado();
            }
            estadoPuestoDao.crear(cl);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     *
     * @param cl
     * @throws ServicioException
     */
    public void actualizarEstadoPuesto(final EstadoPuesto cl) throws ServicioException {
        try {
            cl.setCodigo(cl.getCodigo().toUpperCase());
            cl.setNombre(cl.getNombre().toUpperCase());
            if (cl.isPredeterminado()) {
                estadoPuestoDao.removerPredeterminado();
            }
            estadoPuestoDao.actualizar(cl);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo procesa la eliminación lógica de un estado puesto.
     *
     * @param ep registro a eliminar
     * @throws ServicioException excepcion a nivel de negocio
     */
    public void eliminarEstadoPuesto(final EstadoPuesto ep) throws ServicioException {
        try {
            ep.setVigente(Boolean.FALSE);
            estadoPuestoDao.actualizar(ep);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo transacciona la busqueda de parametro por nemonico.
     *
     * @param nemonico String
     * @return List
     * @throws ServicioException ServicioException
     */
    public List<EstadoPersonal> listarEstadoPersonalPorNemonico(final String codigo) throws ServicioException {
        try {
            List<EstadoPersonal> listaReglasNemonico;
            listaReglasNemonico = estadoPersonalDao.buscarPorNemonico(codigo.toUpperCase());
            return listaReglasNemonico;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param nombre
     * @return
     * @throws ServicioException
     */
    public List<EstadoPersonal> listarTodosEstadoPersonalPorNombre(final String nombre) throws ServicioException {
        try {
            List<EstadoPersonal> lista;
            if (nombre == null || nombre.isEmpty()) {
                lista = estadoPersonalDao.buscarVigente();
            } else {
                lista = estadoPersonalDao.buscarTodosPorNombre(nombre.toUpperCase());
            }
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Este metodo procesa la eliminación lógica de un estado puesto.
     *
     * @param ep registro a eliminar
     * @throws ServicioException excepcion a nivel de negocio
     */
    public void eliminarEstadoPersonal(final EstadoPersonal ep) throws ServicioException {
        try {
            ep.setVigente(Boolean.FALSE);
            estadoPersonalDao.actualizar(ep);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     *
     * @param cl
     * @throws ServicioException
     */
    public void guardarEstadoPersonal(final EstadoPersonal cl) throws ServicioException {
        try {
            cl.setCodigo(cl.getCodigo().toUpperCase());
            cl.setNombre(cl.getNombre().toUpperCase());
            estadoPersonalDao.crear(cl);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     *
     * @param cl
     * @throws ServicioException
     */
    public void actualizarEstadoPersonal(final EstadoPersonal cl) throws ServicioException {
        try {
            cl.setCodigo(cl.getCodigo().toUpperCase());
            cl.setNombre(cl.getNombre().toUpperCase());
            // cl.setVigente(Boolean.FALSE);
            estadoPersonalDao.actualizar(cl);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo lista los estados administracion de puestos por código.
     *
     * @param codigo String
     * @return List
     * @throws ServicioException ServicioException
     */
    public List<EstadoAdministracionPuesto> listarEstadoAdministracionPuestoPorCodigo(final String codigo) throws ServicioException {
        try {
            return estadoAdministracionPuestoDao.buscarTodosPorCodigo(codigo.toUpperCase());
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este metodo lista todos los estados administracion de puestos.
     *
     * @return List
     * @throws ServicioException ServicioException
     */
    public List<EstadoAdministracionPuesto> listarTodosEstadoAdministracionPuesto() throws ServicioException {
        try {
            return estadoAdministracionPuestoDao.buscarVigente();
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este metodo busca un estado administracion de puestos por código.
     *
     * @param codigo String
     * @return List
     * @throws ServicioException ServicioException
     */
    public EstadoAdministracionPuesto buscarEstadoAdministracionPuestoPorCodigo(final String codigo) throws ServicioException {
        try {
            return estadoAdministracionPuestoDao.buscarPorCodigo(codigo.toUpperCase());
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Busca estados administración de puestos por nombre
     *
     * @param nombre
     * @return
     * @throws ServicioException
     */
    public List<EstadoAdministracionPuesto> listarTodosEstadoAdministracionPuestoPorNombre(final String nombre) throws ServicioException {
        try {
            List<EstadoAdministracionPuesto> lista;
            if (nombre == null || nombre.isEmpty()) {
                lista = estadoAdministracionPuestoDao.buscarVigente();
            } else {
                lista = estadoAdministracionPuestoDao.buscarTodosPorNombre(nombre.toUpperCase());
            }
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Eliminación lógica de un estado administración de puesto.
     *
     * @param estado registro a eliminar
     * @throws ServicioException excepcion a nivel de negocio
     */
    public void eliminarEstadoAdministracionPuesto(final EstadoAdministracionPuesto estado) throws ServicioException {
        try {
            estado.setVigente(Boolean.FALSE);
            estadoAdministracionPuestoDao.actualizar(estado);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Crea un nuevo estado administración de puesto
     *
     * @param cl
     * @throws ServicioException
     */
    public void guardarEstadoAdministracionPuesto(final EstadoAdministracionPuesto cl) throws ServicioException {
        try {
            cl.setCodigo(cl.getCodigo().toUpperCase());
            cl.setNombre(cl.getNombre().toUpperCase());
            estadoAdministracionPuestoDao.crear(cl);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Actualiza un estado administración de puesto existente
     *
     * @param cl
     * @throws ServicioException
     */
    public void actualizarEstadoAdministracionPuesto(final EstadoAdministracionPuesto cl) throws ServicioException {
        try {
            cl.setCodigo(cl.getCodigo().toUpperCase());
            cl.setNombre(cl.getNombre().toUpperCase());
            // cl.setVigente(Boolean.FALSE);
            estadoAdministracionPuestoDao.actualizar(cl);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Buscar un DatoAdicional por id
     *
     * @param id
     * @return
     * @throws ServicioException
     */
    public DatoAdicional buscarDatoAdicional(final Long id) throws ServicioException {
        try {
            return datoAdicionalDao.buscarPorId(id);
        } catch (DaoException ex) {
            Logger.getLogger(AdministracionServicio.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    /**
     * Permite almacenar un dato adicional
     *
     * @param datoAdicional registro a guardar
     * @throws ServicioException
     */
    public void guardarDatoAdicional(final DatoAdicional datoAdicional) throws ServicioException {
        try {
            datoAdicional.setCodigo(datoAdicional.getCodigo().toUpperCase());
            datoAdicional.setNombre(datoAdicional.getNombre().toUpperCase());
            datoAdicionalDao.crear(datoAdicional);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Permite actualizar un dato adicional
     *
     * @param datoAdicional registro a actualizar
     * @throws ServicioException
     */
    public void actualizarDatoAdicional(final DatoAdicional datoAdicional) throws ServicioException {
        try {
            datoAdicional.setCodigo(datoAdicional.getCodigo().toUpperCase());
            datoAdicional.setNombre(datoAdicional.getNombre().toUpperCase());
            datoAdicionalDao.actualizar(datoAdicional);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo procesa la eliminación lógica de un estado puesto.
     *
     * @param datoAdicional registro a eliminar
     * @throws ServicioException excepcion a nivel de negocio
     */
    public void eliminarDatoAdicional(final DatoAdicional datoAdicional) throws ServicioException {
        try {
            datoAdicional.setVigente(Boolean.FALSE);
            datoAdicionalDao.actualizar(datoAdicional);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Enlista los datos adicionales vigentes
     *
     * @return lista de registros obtenidos
     * @throws ServicioException excepción a nivel de negocio
     */
    public List<DatoAdicional> listarTodosDatosAdicionalesVigentes() throws ServicioException {
        try {
            List<DatoAdicional> lista;
            lista = datoAdicionalDao.buscarVigente();

            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Enlista los datos adicionales vigentes por el tipo de nomina id
     *
     * @param idTipoNomina
     * @return lista de registros obtenidos
     * @throws ServicioException excepción a nivel de negocio
     */
    public List<DatoAdicional> listarTodosDatosAdicionalesPorTipoNomina(final Long idTipoNomina) throws ServicioException {
        try {
            List<DatoAdicional> lista;
            lista = datoAdicionalDao.buscarPorTipoNomina(idTipoNomina);

            return lista;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }

    }

    /**
     *
     * @param nombre campo a buscar
     * @return lista obtenida
     * @throws ServicioException excepcion a nivel de negocio
     */
    public List<DatoAdicional> listarTodasDatoAdicionalPorNombre(final String nombre) throws ServicioException {
        try {
            List<DatoAdicional> lista;
            if (nombre == null || nombre.isEmpty()) {
                lista = datoAdicionalDao.buscarVigente();
            } else {
                lista = datoAdicionalDao.buscarTodosPorNombre(nombre.toUpperCase());
            }
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Este método permite listar los registros de bancos por codigo.
     *
     * @param codigo String del dato adicional para la busqueda
     * @return lista de registros recuperados
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<DatoAdicional> listarTodosDatoAdicionalPorCodigo(final String codigo) throws ServicioException {
        try {
            List<DatoAdicional> listaBanco;
            listaBanco = datoAdicionalDao.buscarTodosPorCodigo(codigo.toUpperCase());
            return listaBanco;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param nombre
     * @return
     * @throws ServicioException
     */
    public List<TipoCatalogo> listarTodosTipoCatalogo() throws ServicioException {
        try {
            List<TipoCatalogo> lista;
            lista = tipoCatalogoDao.buscarVigentes();
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método permite listar los registros de bancos por codigo.
     *
     * @param codigo String del dato adicional para la busqueda
     * @return lista de registros recuperados
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<Catalogo> listarTodosPorTipoCatalogoId(final Long id) throws ServicioException {
        try {
            List<Catalogo> lista;
            lista = catalogoDao.buscarPorTipoCatalogoId(id);
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este metodo transacciona la busqueda de parametro por nemonico.
     *
     * @param nemonico String
     * @return List
     * @throws ServicioException ServicioException
     */
    public List<Catalogo> listarCatalogoPorNemonico(final String codigo) throws ServicioException {
        try {
            List<Catalogo> listaReglasNemonico;
            listaReglasNemonico = catalogoDao.buscarPorNemonico(codigo.toUpperCase());
            return listaReglasNemonico;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param cl
     * @throws ServicioException
     */
    public void guardarCatalogo(final Catalogo cl) throws ServicioException {
        try {
            cl.setCodigo(cl.getCodigo().toUpperCase());
            cl.setNombre(cl.getNombre().toUpperCase());
            catalogoDao.crear(cl);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     *
     * @param cl
     * @throws ServicioException
     */
    public void actualizarCatalogo(final Catalogo cl) throws ServicioException {
        try {
            cl.setCodigo(cl.getCodigo().toUpperCase());
            cl.setNombre(cl.getNombre().toUpperCase());
            catalogoDao.actualizar(cl);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo procesa la eliminación lógica de un catalogo.
     *
     * @param ep registro a eliminar
     * @throws ServicioException excepcion a nivel de negocio
     */
    public void eliminarCatalogo(final Catalogo ep) throws ServicioException {
        try {
            ep.setVigente(Boolean.FALSE);
            catalogoDao.actualizar(ep);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * metodo para guardar una constante
     *
     * @param constante registro a guardar
     * @throws ServicioException excepcion a nivel de negocio
     */
    public void guardarConstante(final Constante constante) throws ServicioException {
        try {
            constante.setCodigo(constante.getCodigo().toUpperCase());
            constante.setNombre(constante.getNombre().toUpperCase());
            constanteDao.crear(constante);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo procesa la actualización de una constante
     *
     * @param constante Instancia de constante
     * @throws ServicioException excepciones a nivel de negocio
     */
    public void actualizarConstante(final Constante constante) throws ServicioException {
        try {
            constante.setCodigo(constante.getCodigo().toUpperCase());
            constante.setNombre(constante.getNombre().toUpperCase());
            constanteDao.actualizar(constante);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo procesa la eliminación lógica de una constante
     *
     * @param constante registro a eliminar
     * @throws ServicioException excepcion a nivel de negocio
     */
    public void eliminarConstante(final Constante constante) throws ServicioException {
        try {
            constante.setVigente(Boolean.FALSE);
            constanteDao.actualizar(constante);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar todas las constantes vigentes
     *
     * @return List<Constante> listado obtenido
     */
    public List<Constante> listarTodosConstantesVigentes() throws ServicioException {
        try {
            return constanteDao.buscarVigente();
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param nombre campo a buscar
     * @return lista obtenida
     * @throws ServicioException excepcion a nivel de negocio
     */
    public List<Constante> listarTodasConstantePorNombre(final String nombre) throws ServicioException {
        try {
            List<Constante> lista;
            if (nombre == null || nombre.isEmpty()) {
                lista = constanteDao.buscarVigente();
            } else {
                lista = constanteDao.buscarTodosPorNombre(nombre.toUpperCase());
            }
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Este método permite listar los registros de constantes por codigo.
     *
     * @param codigo String del banco de la constante para la busqueda
     * @return lista de constantes recuperadas
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<Constante> listarTodosConstantePorCodigo(final String codigo) throws ServicioException {
        try {
            List<Constante> listaConstante;
            listaConstante = constanteDao.buscarTodosPorCodigo(codigo.toUpperCase());
            return listaConstante;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param nombre
     * @return
     * @throws ServicioException
     */
    public List<CotizacionIess> listarTodosCotizacionIessPorNombre(final String nombre) throws ServicioException {
        try {
            List<CotizacionIess> lista;
            if (nombre == null || nombre.isEmpty()) {
                lista = cotizacionIessDao.buscarVigente();
            } else {
                lista = cotizacionIessDao.buscarTodosPorNombre(nombre.toUpperCase());
            }
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }
    
    /**
     * Lista las CotizacionIessEspecial vigentes 
     * @return
     * @throws ServicioException 
     */
    public List<CotizacionIessEspecial> listarTodosCotizacionIessEspeciales() throws ServicioException {
        try {
            return cotizacionIessEspecialDao.buscarVigentes();
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }
    
    /**
     * Verifica si existe el nombre de la CotizacionIessEspecial en base de datos
     * ingnorando el id de edicion
     * @param nombre
     * @param idIgnorar
     * @return
     * @throws ServicioException 
     */
    public boolean existeNombreDeCotizacionIessEspeciales(final String nombre, final Long idIgnorar) throws ServicioException {
        try {
            return cotizacionIessEspecialDao.existeNombreEnUso(nombre, idIgnorar);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param cl
     * @throws ServicioException
     */
    public void guardarCotizacionIess(final CotizacionIess cl) throws ServicioException {
        try {
            cl.setPorcentajeAdicionalAporteIndividual(BigDecimal.ZERO);
            cl.setPorcentajeAdicionalAportePatronal(BigDecimal.ZERO);
            cl.setPorcentajeIece(BigDecimal.ZERO);
            cl.setPorcentajeSecap(BigDecimal.ZERO);
            cotizacionIessDao.crear(cl);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }
    
    /**
     * Guarda una nueva CotizacionIessEspecial
     * @param cl
     * @throws ServicioException 
     */
    public void guardarCotizacionIessEspecial(final CotizacionIessEspecial cl) throws ServicioException {
        try {
            cotizacionIessEspecialDao.crear(cl);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     *
     * @param cl
     * @throws ServicioException
     */
    public void actualizarCotizacionIess(final CotizacionIess cl) throws ServicioException {
        try {
            cotizacionIessDao.actualizar(cl);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }
    
    /**
     * Actualiza una CotizacionIessEspecial
     * @param cl
     * @throws ServicioException
     */
    public void actualizarCotizacionIessEspecial(final CotizacionIessEspecial cl) throws ServicioException {
        try {
            cotizacionIessEspecialDao.actualizar(cl);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo procesa la eliminación lógica de un catalogo.
     *
     * @param ep registro a eliminar
     * @throws ServicioException excepcion a nivel de negocio
     */
    public void eliminarCotizacionIess(final CotizacionIess ep) throws ServicioException {
        try {
            ep.setVigente(Boolean.FALSE);
            cotizacionIessDao.actualizar(ep);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }
    
    /**
     * Elimina una CotizacionIessEspecial
     * @param ep
     * @throws ServicioException 
     */
    public void eliminarCotizacionIessEspecial(final CotizacionIessEspecial ep) throws ServicioException {
        try {
            ep.setVigente(Boolean.FALSE);
            cotizacionIessEspecialDao.actualizar(ep);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }
    

    /**
     * Este metodo transacciona la busqueda de parametro por id de nivel
     * ocupacional.
     *
     * @param nivelOcupacionalId String
     * @return List
     * @throws ServicioException ServicioException
     */
    public List<CotizacionIess> listarCotizacionPorIdNivelOcupacional(final Long nivelOcupacionalId) throws
            ServicioException {
        try {
            List<CotizacionIess> listaReglasNemonico;
            listaReglasNemonico = cotizacionIessDao.buscarPorNemonico(nivelOcupacionalId);
            return listaReglasNemonico;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @return lista obtenida
     * @throws ServicioException excepcion a nivel de negocio
     */
    public List<PeriodoNomina> listarTodosPeriodoNominaVigente() throws ServicioException {
        try {
            List<PeriodoNomina> lista;
            lista = periodoNominaDao.buscarVigente();
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    //AA Periodos nomina por ejercicio fiscal
    public List<PeriodoNomina> buscarPeriodoNominaPorEjercicio() throws ServicioException {
        try {
            return periodoNominaDao.buscarPorEjercicio(ejercicioFiscalDao.buscarActivo().getId());
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este metodo transacciona la busqueda de parametro por nemonico.
     *
     * @param nemonico String
     * @return List
     * @throws ServicioException ServicioException
     */
    public List<UnidadOrganizacional> listarUnidadOrganizacionalPorNemonico(final String codigo) throws
            ServicioException {
        try {
            List<UnidadOrganizacional> listaReglasNemonico;
            listaReglasNemonico = unidadOrganizacionalDao.buscarPorNemonico(codigo.toUpperCase());
            return listaReglasNemonico;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Lista todas las Unidades organizacionales vigentes.
     *
     * @return List
     * @throws ServicioException ServicioException
     */
    public List<UnidadOrganizacional> listarUnidadOrganizacionalVigente() throws
            ServicioException {
        try {
            return unidadOrganizacionalDao.buscarVigentes();
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param nombre campo a buscar
     * @return lista obtenida
     * @throws ServicioException excepcion a nivel de negocio
     */
    public List<MetadataTabla> listarMetadataTablasPorNombre(final String nombre) throws ServicioException {
        try {
            List<MetadataTabla> lista;
            if (nombre == null || nombre.isEmpty()) {
                lista = metadataTablaDao.buscarTodosVigente();
            } else {
                lista = metadataTablaDao.buscarTodosPorNombre(nombre.toUpperCase());
            }
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Metodo que se encarga de buscar todas las MetadataColumna vigentes
     *
     * @return List<MetadataColumna> listado obtenido
     */
    public List<MetadataColumna> listarMetadataColumnasVigentes() throws ServicioException {
        try {
            return metadataColumnaDao.buscarTodosVigente();
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param nombre campo a buscar
     * @return lista obtenida
     * @throws ServicioException excepcion a nivel de negocio
     */
    public List<MetadataColumna> listarMetadataColumnasPorNombre(final String nombre) throws ServicioException {
        try {
            List<MetadataColumna> lista;
            if (nombre == null || nombre.isEmpty()) {
                lista = metadataColumnaDao.buscarTodosVigente();
            } else {
                lista = metadataColumnaDao.buscarTodosPorNombre(nombre.toUpperCase());
            }
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Este método permite listar los registros de metadata columna por el id de
     * la metadata tabla a la que pertenecen.
     *
     * @param idPadre Id de la metadataTabla
     * @return lista de constantes recuperadas
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<MetadataColumna> listarMetadataColumnasPorIdMetadataTabla(final Long idPadre) throws ServicioException {
        try {
            List<MetadataColumna> listaMetadataColumna;
            listaMetadataColumna = metadataColumnaDao.buscarTodosPorMetadataTabla(idPadre);
            return listaMetadataColumna;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este metodo transacciona la busqueda de parametro por nemonico.
     *
     * @param nemonico String
     * @return List
     * @throws ServicioException ServicioException
     */
    public List<UbicacionGeografica> listarUbicacionGeograficaPorNemonico(final String codigo) throws ServicioException {
        try {
            List<UbicacionGeografica> listaReglasNemonico;
            listaReglasNemonico = ubicacionGeograficaDao.buscarPorNemonico(codigo.toUpperCase());
            return listaReglasNemonico;
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Metodo que se encarga de buscar los campos de acceso vigentes
     *
     * @return List<CampoAcceso> listado obtenido
     */
    public List<CampoAcceso> listarCampoAccesoVigentes() throws ServicioException {
        try {
            return campoAccesoDao.buscarCampoAccesoVigente();
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar los campos de acceso vigentes de un tipo
     * especifico
     *
     * @return List<CampoAcceso> listado obtenido
     */
    public List<CampoAcceso> listarCampoAccesoVigentesPorTipo(final String tipo) throws ServicioException {
        try {
            return campoAccesoDao.buscarCampoAccesoVigentePorTipo(tipo);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param id campo a buscar
     * @return lista obtenida
     * @throws ServicioException excepcion a nivel de negocio
     */
    public List<CampoAcceso> listarCampoAccesoPorId(final Long id) throws ServicioException {
        try {
            List<CampoAcceso> listaCampoAcceso;
            listaCampoAcceso = campoAccesoDao.buscarCampoAccesoPorId(id);
            return listaCampoAcceso;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param idTabla campo a buscar
     * @return lista obtenida
     * @throws ServicioException excepcion a nivel de negocio
     */
    public List<CampoAcceso> listarCampoAccesoPorTabla(final Long idTabla) throws ServicioException {
        try {
            List<CampoAcceso> listaCampoAcceso;
            listaCampoAcceso = campoAccesoDao.buscarCampoAccesoPorTabla(idTabla);
            return listaCampoAcceso;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param idColumna campo a buscar
     * @return lista obtenida
     * @throws ServicioException excepcion a nivel de negocio
     */
    public List<CampoAcceso> listarCampoAccesoPorColumna(final Long idColumna) throws ServicioException {
        try {
            List<CampoAcceso> listaCampoAcceso;
            listaCampoAcceso = campoAccesoDao.buscarCampoAccesoPorColumna(idColumna);
            return listaCampoAcceso;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param nombre campo a buscar
     * @param exacto indica si se debe buscar el nombre exacto o que contenga.
     * Si es true busca el exacto
     * @return lista obtenida
     * @throws ServicioException excepcion a nivel de negocio
     */
    public List<CampoAcceso> listarCampoAccesoPorNombre(final String nombre, final boolean exacto) throws
            ServicioException {
        try {
            List<CampoAcceso> lista;
            if (nombre == null || nombre.isEmpty()) {
                lista = campoAccesoDao.buscarCampoAccesoVigente();
            } else {
                lista = campoAccesoDao.buscarTodosPorNombre(nombre.toUpperCase(), exacto);
            }
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * metodo para guardar una constante
     *
     * @param campoAcceso registro a guardar
     * @throws ServicioException excepcion a nivel de negocio
     */
    public void guardarCampoAcceso(final CampoAcceso campoAcceso) throws ServicioException {
        try {
            campoAcceso.setNombre(campoAcceso.getNombre().toUpperCase());
            campoAccesoDao.crear(campoAcceso);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo procesa la actualización de un campoAcceso
     *
     * @param campoAcceso Instancia de constante
     * @throws ServicioException excepciones a nivel de negocio
     */
    public void actualizarCampoAcceso(final CampoAcceso campoAcceso) throws ServicioException {
        try {
            campoAcceso.setNombre(campoAcceso.getNombre().toUpperCase());
            campoAccesoDao.actualizar(campoAcceso);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo procesa la eliminación lógica de una constante
     *
     * @param campoAcceso registro a eliminar
     * @throws ServicioException excepcion a nivel de negocio
     */
    public void eliminarCampoAcceso(final CampoAcceso campoAcceso) throws ServicioException {
        try {
            campoAcceso.setVigente(Boolean.FALSE);
            campoAccesoDao.actualizar(campoAcceso);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     *
     * @param cl
     * @throws ServicioException
     */
    public void actualizarTipoNomina(final TipoNomina cl) throws ServicioException {
        try {
            cl.setCodigo(cl.getCodigo().toUpperCase());
            cl.setNombre(cl.getNombre().toUpperCase());
            cl.setDescripcion(cl.getDescripcion().toUpperCase());
            tipoNominaDao.actualizar(cl);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     *
     * @param cl
     * @throws ServicioException
     */
    public void guardarTipoNomina(final TipoNomina cl) throws ServicioException {
        try {
            cl.setCodigo(cl.getCodigo().toUpperCase());
            cl.setNombre(cl.getNombre().toUpperCase());
            cl.setDescripcion(cl.getDescripcion().toUpperCase());
            tipoNominaDao.crear(cl);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo transacciona la busqueda de parametro por nemonico.
     *
     * @param nemonico String
     * @return List
     * @throws ServicioException ServicioException
     */
    public List<TipoNomina> listarTipoNominaPorNemonico(final String codigo) throws ServicioException {
        try {
            List<TipoNomina> listaReglasNemonico;
            listaReglasNemonico = tipoNominaDao.buscarPorNemonico(codigo.toUpperCase());
            return listaReglasNemonico;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método procesa la eliminación de un registro de EstadoPuesto
     * -TipoNominaEstadoPuesto
     *
     * @param TipoNominaEstadoPuesto modalidad laboral a eliminar: Cambiar la
     * vigencia
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void eliminarTipoNominaEstadoPuesto(final TipoNominaEstadoPuesto tipoNominaEstadoPuesto) throws
            ServicioException {
        try {
            tipoNominaEstadoPuesto.setVigente(Boolean.FALSE);
            tipoNominaEstadoPuestoDao.actualizar(tipoNominaEstadoPuesto);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * metodo para cargar la lista de estados puestos por Nómina.
     *
     * @param id
     * @return
     * @throws ServicioException
     */
    public List<EstadoPuesto> listarEstadoPuestoDeNomina(final Long id) throws ServicioException {
        try {
            List<EstadoPuesto> listaEstadoPuestos;
            List<TipoNominaEstadoPuesto> listaNominaEstadoPuestos;
            listaNominaEstadoPuestos = tipoNominaEstadoPuestoDao.buscarVigentePorEtadoPuesto(id);
            listaEstadoPuestos = estadoPuestoDao.buscarVigente();
            for (EstadoPuesto estado : listaEstadoPuestos) {
                estado.setSeleccionado(Boolean.FALSE);
                for (TipoNominaEstadoPuesto reg : listaNominaEstadoPuestos) {
                    if (estado.getId().equals(reg.getEstadoPuestoId())) {
                        estado.setSeleccionado(Boolean.TRUE);
                    }
                }
            }
            return listaEstadoPuestos;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    public List<EstadoPersonal> listarEstadoPersonalDeNomina(final Long id) throws ServicioException {
        try {
            List<EstadoPersonal> listaEstadoPersonal;
            List<TipoNominaEstadoPersonal> listaNominaEstadoPersonal;
            listaNominaEstadoPersonal = tipoNominaEstadoPersonalDao.buscarVigentePorEtadoPersonal(id);
            listaEstadoPersonal = estadoPersonalDao.buscarVigente();
            for (EstadoPersonal estado : listaEstadoPersonal) {
                estado.setSeleccionado(Boolean.FALSE);
                for (TipoNominaEstadoPersonal reg : listaNominaEstadoPersonal) {
                    if (estado.getId().equals(reg.getEstadoPersonalId())) {
                        estado.setSeleccionado(Boolean.TRUE);
                    }
                }
            }
            return listaEstadoPersonal;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método procesa la eliminación de un registro de EstadoPuesto
     * -TipoNominaEstadoPuesto
     *
     * @param TipoNominaEstadoPuesto modalidad laboral a eliminar: Cambiar la
     * vigencia
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void eliminarTipoNominaEstadoPersonal(final TipoNominaEstadoPersonal tipoNominaEstadoPersonal) throws
            ServicioException {
        try {
            tipoNominaEstadoPersonal.setVigente(Boolean.FALSE);
            tipoNominaEstadoPersonalDao.actualizar(tipoNominaEstadoPersonal);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Permite el registro de la modalidad laboral - nivel ocupacional
     *
     * @param modalidadNivelLaboral registro a guardar
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void guardarEstadoPersonalNomina(final TipoNominaEstadoPersonal tnep) throws
            ServicioException {
        try {
            tipoNominaEstadoPersonalDao.crear(tnep);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Permite el registro de la modalidad laboral - nivel ocupacional
     *
     * @param modalidadNivelLaboral registro a guardar
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void guardarEstadoPuestoNomina(final TipoNominaEstadoPuesto tnep) throws
            ServicioException {
        try {
            tipoNominaEstadoPuestoDao.crear(tnep);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     *
     * @param nombre
     * @return
     * @throws ServicioException
     */
    public List<TipoNomina> listarTodosTipoNominaPorNombre(final String nombre) throws ServicioException {
        try {
            List<TipoNomina> lista;
            if (nombre == null || nombre.isEmpty()) {
                lista = tipoNominaDao.buscarVigente();
            } else {
                lista = tipoNominaDao.buscarTodosPorNombre(nombre.toUpperCase());
            }
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Obtiene listado de los Tipos de Nómina VIgentes
     *
     * @return
     * @throws ServicioException
     */
    public List<TipoNomina> listarTodosTipoNominaVigentes() throws ServicioException {
        try {

            return tipoNominaDao.buscarVigente();

        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Este metodo procesa la eliminación lógica de un estado puesto.
     *
     * @param ep registro a eliminar
     * @throws ServicioException excepcion a nivel de negocio
     */
    public void eliminarTipoNomina(final TipoNomina ep) throws ServicioException {
        try {
            ep.setVigente(Boolean.FALSE);
            tipoNominaDao.actualizar(ep);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo busca los movimientos por Id.
     *
     * @param id Long
     * @return Movimiento
     * @throws ServicioException ServicioException
     */
    public Servidor buscarServidorPorTipoDocumento(final String tipoDocumento) throws ServicioException {
        try {
            Servidor servidor = new Servidor();
            if (tipoDocumento != null) {
                servidor = servidorDao.buscarPorTipoIdentificacion(UtilCadena.concatenar("%", tipoDocumento, "%"));
            }
            return servidor;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este metodo busca los servidores por Id.
     *
     * @param idServidor Long
     * @return Movimiento
     * @throws ServicioException ServicioException
     */
    public Servidor buscarServidorPorId(final Long idServidor) throws ServicioException {
        try {
            Servidor servidor = new Servidor();
            servidor = servidorDao.buscarPorId(idServidor);
            return servidor;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este metodo busca los movimientos por Id.
     *
     * @param tipoDocumento
     * @param numeroDocumento
     * @return Movimiento
     * @throws ServicioException ServicioException
     */
    public Servidor buscarServidor(final String tipoDocumento, final String numeroDocumento) throws ServicioException {
        try {
            return servidorDao.buscar(tipoDocumento, numeroDocumento);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Buscar por tipoDocumento y documento y activo en distributivo
     *
     * @param tipoDocumento
     * @param numeroDocumento
     * @return
     * @throws ServicioException
     */
    public Servidor buscarServidor(final String tipoDocumento, final String numeroDocumento, final Long idInstitucionEjercicioFiscal) throws ServicioException {
        try {
            return servidorDao.buscar(tipoDocumento, numeroDocumento, idInstitucionEjercicioFiscal);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este metodo busca los ServidorInstitucion vigentes
     *
     * @return Lista de ServidorInstitucion
     * @throws ServicioException ServicioException
     */
    public List<ServidorInstitucion> buscarServidorInstitucionVigente() throws
            ServicioException {
        try {
            return servidorInstitucionDao.buscarVigente();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            throw new ServicioException(e);
        }
    }

    /**
     * Este metodo busca los ServidorInstitucion vigentes por su institucion
     *
     * @param idInstitucion id de la institucion
     * @return Lista de ServidorInstitucion
     * @throws ServicioException ServicioException
     */
    public List<ServidorInstitucion> buscarServidorInstitucionVigentePorInstitucion(final Long idInstitucion) throws
            ServicioException {
        try {
            return servidorInstitucionDao.buscarVigentePorInstitucion(idInstitucion);
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            throw new ServicioException(e);
        }
    }

    /**
     * Este metodo busca los ServidorInstitucion vigentes por el numero de
     * identificacion del funcionario
     *
     * @param numDoc identificacion del servidor
     * @return Lista de ServidorInstitucion
     * @throws ServicioException ServicioException
     */
    public List<ServidorInstitucion> buscarServidorInstitucionVigentePorDocumentoServidor(final String numDoc) throws
            ServicioException {
        try {
            return servidorInstitucionDao.buscarVigentePorPorDocumentoServidor(numDoc);
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param cl
     * @throws ServicioException
     */
//    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void guardarServidorArchivo(final Servidor cl, final String usuario) throws ServicioException {
        try {
            if (cl.getArchivoId() == null) {
                Archivo crear = archivoDao.crear(cl.getArchivo());
                archivoDao.flush();
                cl.setArchivoId(crear.getId());
                cl.setArchivo(crear);
                servidorDao.actualizar(cl);
            } else {
                archivoDao.actualizar(cl.getArchivo());
                archivoDao.flush();
            }

        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     *
     * @param nombre campo a buscar
     * @return lista obtenida
     * @throws ServicioException excepcion a nivel de negocio
     */
    public List<CuentaBancaria> listarTodasCuentaBancariaVigentesPorServidor(Long servidorId) throws ServicioException {
        try {
            List<CuentaBancaria> lista;
            lista = cuentaBancariaDao.buscarVigentePorServidor(servidorId);
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     *
     * @param beneficiarioId campo a buscar
     * @return lista obtenida
     * @throws ServicioException excepcion a nivel de negocio
     */
    public List<CuentaBancaria> listarTodasCuentaBancariaVigentesPorBeneficiarioInstitucional(final Long beneficiarioId)
            throws ServicioException {
        try {
            List<CuentaBancaria> lista;
            lista = cuentaBancariaDao.buscarVigentePorBeneficiarioInstitucional(beneficiarioId);
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param beneficiarioId campo a buscar
     * @return lista obtenida
     * @throws ServicioException excepcion a nivel de negocio
     */
    public List<CuentaBancaria> listarTodasCuentaBancariaVigentesPorBeneficiarioEspecial(final Long beneficiarioId)
            throws ServicioException {
        try {
            List<CuentaBancaria> lista;
            lista = cuentaBancariaDao.buscarVigentePorBeneficiarioEspecial(beneficiarioId);
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Permite el registro de la modalidad laboral - nivel ocupacional
     *
     * @param cu
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void guardarCuentaBancaria(final CuentaBancaria cu) throws
            ServicioException {
        try {
            cuentaBancariaDao.crear(cu);
            List<CuentaBancaria> listaCuentaB = cuentaBancariaDao.buscarVigentePorServidor(cu.getServidorId());
            for (CuentaBancaria cuenta : listaCuentaB) {
                if (!cuenta.getId().equals(cu.getId())) {
                    cuenta.setPagoNomina(Boolean.FALSE);
                }
            }
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Permite el registro de la modalidad laboral - nivel ocupacional
     *
     * @param cu
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void actualizarCuentaBancaria(final CuentaBancaria cu) throws
            ServicioException {
        try {
            if (cu.getPagoNomina()) {
                List<CuentaBancaria> listaCuentaB = cuentaBancariaDao.buscarVigentePorServidor(cu.getServidorId());
                for (CuentaBancaria cuenta : listaCuentaB) {
                    if (!cuenta.getId().equals(cu.getId())) {
                        cuenta.setPagoNomina(Boolean.FALSE);
                        cuentaBancariaDao.actualizar(cuenta);
                    }
                }
            }
            cuentaBancariaDao.actualizar(cu);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método permite listar los registros de bancos por codigo.
     *
     * @param numeroCuenta
     * @return lista debancos recuperados
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<CuentaBancaria> listarTodosCuentaBancariaPorCodigo(final String numeroCuenta) throws ServicioException {
        try {
            List<CuentaBancaria> listaCuenta;
            listaCuenta = cuentaBancariaDao.buscarTodosPorCodigo(numeroCuenta);
            return listaCuenta;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param nombre campo a buscar
     * @return lista obtenida
     * @throws ServicioException excepcion a nivel de negocio
     */
    public List<Banco> listarTodasBancosVigente() throws ServicioException {
        try {
            List<Banco> lista;
            lista = bancoDao.buscarVigente();
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Este metodo buscarArchivoPorId .
     *
     * @param id Long
     * @return Archivo
     * @throws ServicioException ServicioException
     */
    public Archivo buscarArchivoPorId(final Long id) throws ServicioException {
        try {
            Archivo archivo = new Archivo();
            if (id != null) {
                archivo = archivoDao.buscarPorId(id);
            }
            return archivo;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @return lista obtenida
     * @throws ServicioException excepcion a nivel de negocio
     */
    public List<PortalRhh> listarTodasPortalRhhVigente() throws ServicioException {
        try {
            List<PortalRhh> lista;
            lista = portalRhhDao.buscarVigente();
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Crea un nuevo portal rrhh
     *
     * @param p
     * @throws ServicioException
     */
    public void guardarPortalRrhh(final PortalRhh p) throws ServicioException {
        try {
            p.setCodigo(p.getCodigo().toUpperCase());
            portalRhhDao.crear(p);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Actualiza un portal rrhh existente
     *
     * @param p
     * @throws ServicioException
     */
    public void actualizarPortalRrhh(final PortalRhh p) throws ServicioException {
        try {
            p.setCodigo(p.getCodigo().toUpperCase());
            portalRhhDao.actualizar(p);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Enlista los Nominas vigentes
     *
     * @return lista de registros obtenidos
     * @throws ServicioException excepción a nivel de negocio
     */
    public List<Nomina> listarTodosNominasPorIdPeriodoYEstado(long periodoNominaId,
            long institucionEjercicioFiscalId, String estado) throws ServicioException {
        try {
            List<Nomina> lista = nominaDao.buscarVigentePorPeriodoNominaIdEstado(periodoNominaId,
                    institucionEjercicioFiscalId, estado);
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Enlista los Nominas vigentes
     *
     * @return lista de registros obtenidos
     * @throws ServicioException excepción a nivel de negocio
     */
    public Nomina listarTodosNominasPorId(final Long idNomina) throws ServicioException {
        try {
            return nominaDao.buscarPorId(idNomina);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Enlista los Nominas vigentes
     *
     * @return lista de registros obtenidos
     * @throws ServicioException excepción a nivel de negocio
     */
    public List<Nomina> listarTodosNominasPorIdPeriodoYVigentes(long periodoNominaId,
            long institucionEjercicioFiscalId) throws ServicioException {
        try {
            List<Nomina> lista = nominaDao.buscarVigentePorPeriodoNominaId(periodoNominaId,
                    institucionEjercicioFiscalId);
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param idNomina
     * @return
     * @throws ServicioException
     */
    public Nomina listarPorNomina(final Long idNomina)
            throws ServicioException {
        try {
            return nominaDao.buscarPorId(idNomina);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este metodo busca los tipos movimientos por Id.
     *
     * @param id Long
     * @return TipoMovimiento
     * @throws ServicioException ServicioException
     */
    public PeriodoNomina buscarPeriodoNominaPorId(final Long id) throws ServicioException {
        try {
            PeriodoNomina periodoNomina = new PeriodoNomina();
            if (id != null) {
                periodoNomina = periodoNominaDao.buscarPorId(id);
            }
            return periodoNomina;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este metodo busca los movimientos por Id.
     *
     * @param id Long
     * @return Movimiento
     * @throws ServicioException ServicioException
     */
    public List<Servidor> buscarServidorPorNombre(final String nombre) throws ServicioException {
        try {
            List<Servidor> servidor = new ArrayList<Servidor>();
            if (nombre != null) {
                servidor = servidorDao.buscarPorNombre(nombre.toUpperCase());
            }
            return servidor;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este metodo busca servidores por nombre, siempre y cuando existan en
     * distributivo
     *
     * @param nombre
     * @param idInstitucionEjercicioFiscal
     * @return servidores
     * @throws ServicioException ServicioException
     */
    public List<Servidor> buscarServidorPorNombreDistributivo(final String nombre,
            final Long idInstitucionEjercicioFiscal) throws ServicioException {
        try {
            List<Servidor> servidor = new ArrayList<Servidor>();
            if (nombre != null) {
                servidor = servidorDao.buscarPorNombreDistributivo(nombre.toUpperCase(), idInstitucionEjercicioFiscal);
            }
            return servidor;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este metodo busca N servidores por nombre, si
     * idInstitucionEjercicioFiscal no es null se verifican que existan en
     * distributivo
     *
     * @param nombre
     * @param idInstitucionEjercicioFiscal
     * @param n
     * @return
     * @throws ServicioException
     */
    public List<Servidor> buscarServidorPorNombreDistributivoLimite(final String nombre,
            final Long idInstitucionEjercicioFiscal, final Integer n) throws ServicioException {
        try {
            List<Servidor> servidor = new ArrayList<Servidor>();
            if (nombre != null) {
                servidor = servidorDao.buscarPorNombreDistributivoLimite(nombre.toUpperCase(), idInstitucionEjercicioFiscal, n);
            }
            return servidor;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este metodo busca servidores por nombre y numero de indentificacion,
     * siempre y cuando existan en distributivo
     *
     * @param nombre
     * @param identificacion
     * @param idInstitucionEjercicioFiscal
     * @return
     * @throws ServicioException
     */
    public List<Servidor> buscarServidorPorNombreYNumeroIdentificacionDistributivo(
            final String nombre, final String identificacion,
            final Long idInstitucionEjercicioFiscal) throws ServicioException {
        try {
            List<Servidor> servidor = new ArrayList<Servidor>();
            if (nombre != null) {
                servidor = servidorDao.buscarPorNombreEIndentificacionDistributivo(
                        nombre.toUpperCase(), identificacion, idInstitucionEjercicioFiscal);
            }
            return servidor;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este metodo busca servidores por nombre, siempre y cuando existan en
     * distributivo
     *
     * @param nombre
     * @param idInstitucionEjercicioFiscal
     * @param codigoUnidadOrganizacional
     * @return servidores
     * @throws ServicioException ServicioException
     */
    public List<Servidor> buscarServidorPorNombreDistributivo(final String nombre,
            final UsuarioVO usuarioVO, Boolean esRRHH) throws ServicioException {
        try {
            List<Servidor> servidor = new ArrayList<>();
            if (nombre != null) {
                if (esRRHH) {
                    servidor = servidorDao.buscarPorNombreDistributivo(nombre.toUpperCase(),
                            usuarioVO.getInstitucionEjercicioFiscal().getId(), null);
                } else {
                    List<UnidadOrganizacional> unidadesAcceso
                            = desconcentradoServicio.buscarUnidadesDeAcceso(usuarioVO.getServidor().getId(),
                                    FuncionesDesconcentradosEnum.NOVEDADES.getCodigo());
                    for (UnidadOrganizacional uo : unidadesAcceso) {

                        servidor.addAll(servidorDao.buscarPorNombreDistributivo(nombre.toUpperCase(),
                                usuarioVO.getInstitucionEjercicioFiscal().getId(), uo.getCodigo()));
                    }
                }
            }
            return servidor;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param nombre
     * @param idInstitucionEjercicioFiscal
     * @param uo
     * @param servidores
     * @throws DaoException
     */
    public void buscarServidorPorNombreDistributivo(
            final String nombre, final Long idInstitucionEjercicioFiscal,
            UnidadOrganizacional uo, List<Servidor> servidores) throws DaoException {
        servidores.addAll(
                servidorDao.buscarPorNombreDistributivo(nombre.toUpperCase(),
                        idInstitucionEjercicioFiscal, uo.getCodigo()));
        for (UnidadOrganizacional hijo : uo.getListaUnidadesOrganizacionales()) {
            buscarServidorPorNombreDistributivo(nombre, idInstitucionEjercicioFiscal, hijo, servidores);
        }
    }

    /**
     *
     * @param nv
     * @throws ServicioException
     */
    public void guardarNovedad(final Novedad nv) throws ServicioException {
        try {
            novedadDao.crear(nv);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     *
     * @param cl
     * @throws ServicioException
     */
    public void actualizarNovedad(final Novedad nv) throws ServicioException {
        try {
            novedadDao.actualizar(nv);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Actualiza el estado de una novedad y sus detalles
     *
     * @param nv
     * @param detalles
     * @param eliminados
     * @throws ServicioException
     */
    public void actualizarNovedad(final Novedad nv, final List<NovedadDetalle> detalles, final List<NovedadDetalle> eliminados, final UsuarioVO usuarioVO) throws ServicioException {
        try {
            novedadDao.actualizar(nv);
            for (NovedadDetalle nd : detalles) {
                nd.setValorDescontado(BigDecimal.ZERO);
                nd.setNovedadId(nv.getId());
                if (nd.getId() == null) {
                    iniciarDatosEntidad(nd, Boolean.TRUE, usuarioVO);
                    guardarNovedadDetalle(nd);
                } else {
                    iniciarDatosEntidad(nd, Boolean.FALSE, usuarioVO);
                    actualizarNovedadDetalle(nd);
                }
            }
            for (NovedadDetalle nd : eliminados) {
                nd.setValorDescontado(BigDecimal.ZERO);
                nd.setNovedadId(nv.getId());
                iniciarDatosEntidad(nd, Boolean.FALSE, usuarioVO);
                nd.setVigente(Boolean.FALSE);
                actualizarNovedadDetalle(nd);
            }
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * metodo para obtener el numero de tramite por institucion ejercicio
     * fiscal.
     *
     * @param institucion
     * @return
     * @throws DaoException
     */
//    public String generarNumeroNovedad(final InstitucionEjercicioFiscal institucion, Long InstitucionEjercicioFiscalId)
//            throws ServicioException {
//        
//    }
    /**
     * metodo para obtener el numero de tramite por institucion ejercicio
     * fiscal.
     *
     * @param institucion
     * @param InstitucionEjercicioFiscalId
     * @return
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public String generarNumeroTramite(final InstitucionEjercicioFiscal institucion,
            final Long InstitucionEjercicioFiscalId) throws ServicioException {
        try {
            InstitucionEjercicioFiscal ins = institucionEjercicioFiscalDao.buscarPorCodigoYEjercicioFiscal(
                    institucion.getInstitucion().getCodigo(), InstitucionEjercicioFiscalId);
            institucionEjercicioFiscalDao.lock(ins);
            ins.setContadorTramite(ins.getContadorTramite() + 1);
            institucionEjercicioFiscalDao.actualizar(ins);
            return UtilMatematicas.rellenarConCerosIzq(ins.getContadorTramite(), 10);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * metodo para obtener el numero de puesto por institucion ejercicio.
     *
     * @param institucionId
     * @return
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public String generarNumeroPuesto(final Long institucionId) throws ServicioException {
        try {
            Institucion institucion = institucionDao.buscarPorId(institucionId);
            institucionDao.lock(institucion);
            institucion.setContadorPuestos(institucion.getContadorPuestos() + 1);
            institucionDao.actualizar(institucion);
            institucionDao.flush();
            return UtilMatematicas.rellenarConCerosIzq(institucion.getContadorPuestos(), 10);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * metodo para obtener el numero de partida individual por distributivo.
     *
     * @param institucionId
     * @return
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public String generarNumeroPartidaIndividual(final Long institucionId) throws ServicioException {
        try {
            Institucion institucion = institucionDao.buscarPorId(institucionId);
            institucionDao.lock(institucion);
            institucion.setContadorPartidaIndividual(institucion.getContadorPartidaIndividual() + 1);
            institucionDao.actualizar(institucion);
            institucionDao.flush();
            return institucion.getContadorPartidaIndividual().toString();

        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * metodo para obtener el numero accion de personal de vacaciones
     *
     * @param institucionId
     * @return
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public String generarNumeroAccionPersonalVacaciones(final Long institucionId) throws ServicioException {
        try {
            Institucion institucion = institucionDao.buscarPorId(institucionId);
            institucionDao.lock(institucion);
            institucion.setContadorAccionPersonalVacaciones(institucion.getContadorAccionPersonalVacaciones() + 1);
            institucionDao.actualizar(institucion);
            institucionDao.flush();
            return institucion.getContadorAccionPersonalVacaciones().toString();

        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * metodo para obtener el numero accion de personal de vacaciones
     *
     * @param institucionId
     * @return
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public String generarNumeroAccionPersonalLiquidacionVacaciones(final Long institucionId) throws ServicioException {
        try {
            Institucion institucion = institucionDao.buscarPorId(institucionId);
            institucionDao.lock(institucion);
            institucion.setContadorAccionPersonalLiquidacionVacaciones(
                    institucion.getContadorAccionPersonalLiquidacionVacaciones() + 1);
            institucionDao.actualizar(institucion);
            institucionDao.flush();
            return institucion.getContadorAccionPersonalLiquidacionVacaciones().toString();

        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     *
     * @param cl
     * @throws ServicioException
     */
    public void guardarNovedadDetalle(final NovedadDetalle nv) throws ServicioException {
        try {
            novedadDetalleDao.crear(nv);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     *
     * @param cl
     * @throws ServicioException
     */
    public void actualizarNovedadDetalle(final NovedadDetalle nv) throws ServicioException {
        try {
            novedadDetalleDao.actualizar(nv);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Enlista los Novedades por parametros vigentes
     *
     * @return lista de registros obtenidos
     * @throws ServicioException excepción a nivel de negocio
     */
    public List<Novedad> listarTodosNovedadesPorPeriodoIdYTipoNominaId(long periodoNominaId,
            long tipoNominaId) throws ServicioException {
        try {
            List<Novedad> lista = novedadDao.buscarNovedadesPorPeriodoIdYTipoNominaId(periodoNominaId,
                    tipoNominaId);
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Enlista los Novedades por parametros vigentes por el id de la nomina
     *
     * @param nominaId
     * @return lista de registros obtenidos
     * @throws ServicioException excepción a nivel de negocio
     */
    public List<Novedad> listarTodosNovedadesPorNominaId(final long nominaId) throws ServicioException {
        try {
            List<Novedad> lista = novedadDao.buscarNovedadesNominaId(nominaId);
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * lista las novedades detalles por el id de la novedad.
     *
     * @param NominaId
     * @return
     * @throws ServicioException
     */
    public List<NovedadDetalle> listarTodosNovedadDetalleNominaId(
            long NominaId) throws ServicioException {
        try {
            List<NovedadDetalle> lista = novedadDetalleDao.buscarNovedadesDetallesPorNominaId(
                    NominaId);
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * lista las novedades detalles por el id de la novedad.
     *
     * @param NominaId
     * @return
     * @throws ServicioException
     */
    public List<NovedadDetalle> listarTodosNovedadDetalleNovedadId(
            long novedadId) throws ServicioException {
        try {
            List<NovedadDetalle> lista = novedadDetalleDao.buscarNovedadesDetallesPorNovedadId(
                    novedadId);
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Recupera la lista de unidades organizacionales.
     *
     * @return
     * @throws ServicioException
     */
    public List<UnidadOrganizacional> listarUnidadesOrganizacionales() throws ServicioException {
        try {
            return unidadOrganizacionalDao.buscarVigentes();
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * eliminar la novedad detalle.
     *
     * @param ep
     * @throws ServicioException
     */
    public void eliminarNovedadDetalle(final NovedadDetalle ep) throws ServicioException {
        try {
            ep.setVigente(Boolean.FALSE);
            novedadDetalleDao.actualizar(ep);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * eliminar la novedad.
     *
     * @param novedad
     * @throws ServicioException
     */
    public void eliminarNovedad(final Novedad novedad) throws ServicioException {
        try {
            novedad.setVigente(Boolean.FALSE);
            novedadDao.actualizar(novedad);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * guardar servidor.
     *
     * @param sr
     * @throws ServicioException
     */
    public void guardarServidor(final Servidor sr) throws ServicioException {
        try {
//            sr.setApellidosNombres(sr.getApellidosNombres());
            servidorDao.actualizar(sr);
            if (sr.getCuentasBancarias() != null) {
                actualizarCuentasBancarias(sr);
            }
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * guardar servidor.
     *
     * @param sr
     * @throws ServicioException
     */
    public void guardarServidorCapacitacion(final ServidorCapacitacion sr) throws ServicioException {
        try {
            servidorCapacitacionDao.crear(sr);

        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * guardar servidor.
     *
     * @param sr
     * @throws ServicioException
     */
    public void guardarServidorEvaluacion(final ServidorEvaluacion sr) throws ServicioException {
        try {
            serviEvaluacionDao.crear(sr);

        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * guardar servidor.
     *
     * @param sr
     * @throws ServicioException
     */
    public void guardarServidorExperiencia(final ServidorExperiencia sr) throws ServicioException {
        try {
            servidorExperienciaDao.crear(sr);

        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * guardar servidor.
     *
     * @param sr
     * @throws ServicioException
     */
    public void guardarServidorInstitucion(final ServidorInstitucion sr) throws ServicioException {
        try {
            servidorInstitucionDao.crear(sr);

        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * guardar servidor.
     *
     * @param sr
     * @throws ServicioException
     */
    public void guardarServidorInstruccion(final ServidorInstruccion sr) throws ServicioException {
        try {
            servidorInstruccionDao.crear(sr);

        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * guardar servidor.
     *
     * @param sr
     * @throws ServicioException
     */
    public void guardarServidorParientesInstitucion(final ServidorParienteInstitucion sr) throws ServicioException {
        try {
            servidorParienteInstitucionDao.crear(sr);

        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * guardar servidor.
     *
     * @param sr
     * @throws ServicioException
     */
    public void guardarServidorInformacionMedica(final ServidorInformacionMedica sr) throws ServicioException {
        try {
            servidorInformacionMedicaDao.crear(sr);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * guardar la actualizacion del personal.
     *
     * @param servidor
     * @param si
     * @param institucionId
     * @param archivo
     * @param servidorInstruccions
     * @param servidorParienteInstitucions
     * @param servidorCargaFamiliars
     * @param servidorExperiencias
     * @param servidorEvaluacions
     * @param servidorCapacitacions
     * @throws ServicioException
     */
    public void guardarActualizacionFichaPersonal(Servidor servidor, ServidorInstitucion si, Long institucionId,
            Archivo archivo,
            List<ServidorInstruccion> servidorInstruccions,
            List<ServidorParienteInstitucion> servidorParienteInstitucions,
            List<ServidorCargaFamiliar> servidorCargaFamiliars,
            List<ServidorExperiencia> servidorExperiencias,
            List<ServidorEvaluacion> servidorEvaluacions,
            List<ServidorCapacitacion> servidorCapacitacions) throws
            ServicioException {
        try {
            if (servidor != null) {
                Servidor servidorAnterior = servidorDao.buscarPorId(servidor.getId());
                if (servidor.getMensualizaDecimoCuarto().compareTo(servidorAnterior.getMensualizaDecimoCuarto()) != 0) {
                    servidor.setUsuarioMensualizaDecimoCuarto(servidor.getUsuarioActualizacion());
                    servidor.setFechaMensualizaDecimoCuarto(new Date());
                }
                if (servidor.getMensualizaDecimoTercero().compareTo(servidorAnterior.getMensualizaDecimoTercero()) != 0) {
                    servidor.setUsuarioMensualizaDecimoTercero(servidor.getUsuarioActualizacion());
                    servidor.setFechaMensualizaDecimoTercero(new Date());
                }
                if (servidor.getRecibeFondoReserva().compareTo(servidorAnterior.getRecibeFondoReserva()) != 0) {
                    servidor.setUsuarioRecibeFondoReserva(servidor.getUsuarioActualizacion());
                    servidor.setFechaRecibeFondoReserva(new Date());
                }
                if (servidor.getNombres() != null) {
                    servidor.setNombres(servidor.getNombres().toUpperCase());
                }
                if (servidor.getApellidos() != null) {
                    servidor.setApellidos(servidor.getApellidos().toUpperCase());
                }
                guardarServidor(servidor);
                if (servidorInstruccions != null) {
                    for (ServidorInstruccion sin : servidorInstruccions) {
                        fichaPersonalServicio.actualizarServidorInstruccion(sin);
                    }
                }
                if (servidorParienteInstitucions != null) {
                    for (ServidorParienteInstitucion spi : servidorParienteInstitucions) {
                        fichaPersonalServicio.actualizarServidorParientes(spi);
                    }
                }
                if (servidorCargaFamiliars != null) {
                    for (ServidorCargaFamiliar scf : servidorCargaFamiliars) {
                        fichaPersonalServicio.actualizarServidorCargaFamiliar(scf);
                    }
                }
                if (servidorExperiencias != null) {
                    for (ServidorExperiencia se : servidorExperiencias) {
                        fichaPersonalServicio.actualizarServidorExperiencia(se);
                    }
                }
                if (servidorEvaluacions != null) {
                    for (ServidorEvaluacion see : servidorEvaluacions) {
                        fichaPersonalServicio.actualizarServidorEvaluacion(see);
                    }
                }
                if (servidorCapacitacions != null) {
                    for (ServidorCapacitacion sca : servidorCapacitacions) {
                        fichaPersonalServicio.actualizarServidorCapacitacion(sca);
                    }
                }
            }

        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo para guardar el archivo de la foto
     *
     * @param servidor
     * @param archivoFoto
     * @throws ServicioException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void guardarArchivoPersonal(Servidor servidor, final File archivoFoto) throws ServicioException {
        try {

            //guarda archivo
            if (archivoFoto != null) {
                Archivo archivo = new Archivo();
                archivo.setArchivo(UtilArchivos.getBytesFromFile(archivoFoto));
                archivo.setDescripcion(UtilCadena.concatenar("FOTO SERVIDOR: ", servidor.getApellidosNombres()));
                archivo.setNombre(UtilCadena.concatenar("FOTO_SERVIDOR_", servidor.getApellidosNombres(), ".jpg"));
                archivo.setVigente(Boolean.TRUE);
                archivo.setFechaCreacion(new Date());
                archivo.setUsuarioCreacion(servidor.getNumeroIdentificacion());
                Archivo crear = archivoDao.crear(archivo);
                archivoDao.flush();
                servidor.setArchivoFotoId(crear.getId());
                servidor.setArchivoFoto(crear);
                servidorDao.actualizar(servidor);
            } else {
                servidor.setArchivoFotoId(null);
            }

        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo para guardar el archivo de la foto
     *
     * @param tercero
     * @param archivoFoto
     * @throws ServicioException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void guardarArchivoTerceros(Tercero tercero, final File archivoFoto) throws ServicioException {
        try {

            //guarda archivo
            if (archivoFoto != null) {
                Archivo archivo = new Archivo();
                archivo.setArchivo(UtilArchivos.getBytesFromFile(archivoFoto));
                archivo.setDescripcion(UtilCadena.concatenar("JUSTIFICACIÓN DE PAGO A TERCERO: ", tercero.getNombres()));
                archivo.setNombre(tercero.getArchivo().getNombre());
                archivo.setVigente(Boolean.TRUE);
                archivo.setFechaCreacion(new Date());
                if (tercero.getUsuarioActualizacion() != null) {
                    archivo.setUsuarioCreacion(tercero.getUsuarioActualizacion());
                } else {
                    archivo.setUsuarioCreacion(tercero.getUsuarioCreacion());
                }
                Archivo crear = archivoDao.crear(archivo);
                archivoDao.flush();
                tercero.setArchivo(crear);
                terceroDao.actualizar(tercero);
            } else {
                tercero.setArchivo(null);
            }

        } catch (IOException e) {
            throw new ServicioException(e);
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo para guardar el archivo de la foto
     *
     * @param servidor
     * @param archivoFoto
     * @throws ServicioException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void guardarArchivoFirmas(Servidor servidor, final File archivoFirma) throws ServicioException {
        try {

            //guarda archivo
            if (archivoFirma != null) {
                Archivo archivo = new Archivo();
                archivo.setArchivo(UtilArchivos.getBytesFromFile(archivoFirma));
                archivo.setDescripcion(UtilCadena.concatenar("FIRMA: ", servidor.getApellidosNombres()));
                archivo.setVigente(Boolean.TRUE);
                archivo.setFechaCreacion(new Date());
                archivo.setUsuarioCreacion(servidor.getNumeroIdentificacion());
                Archivo crear = archivoDao.crear(archivo);
                archivoDao.flush();
                servidor.setArchivoId(crear.getId());
                servidor.setArchivo(crear);
                servidorDao.actualizar(servidor);
            } else {
                servidor.setArchivoId(null);
            }

        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * guardar servidor.
     *
     * @param sr
     * @throws ServicioException
     */
    public List<Catalogo> listarTodosPorTipoCatalogoCodigo(final String codigo) throws ServicioException {
        try {
            List<Catalogo> lista;
            lista = catalogoDao.buscarPorTipoCatalogoCodigo(codigo);
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param id
     * @return
     * @throws ServicioException
     */
    public ModalidadLaboral buscarModalidadLaboral(final Long id) throws ServicioException {
        try {
            return modalidadLaboralDao.buscarPorId(id);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param id
     * @return
     * @throws ServicioException
     */
    public UnidadOrganizacional buscarUnidadOrganizacional(final Long id) throws ServicioException {
        try {
            return unidadOrganizacionalDao.buscarPorId(id);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param id
     * @return
     * @throws ServicioException
     */
    public EstadoPuesto buscarEstadoPuesto(final Long id) throws ServicioException {
        try {
            return estadoPuestoDao.buscarPorId(id);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @return @throws ServicioExceptionf
     */
    public EstadoPuesto buscarEstadoPuestoPredeterminado() throws ServicioException {
        try {
            return estadoPuestoDao.buscarPredeterminado();
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param id
     * @return
     * @throws ServicioException
     */
    public EstadoPersonal buscarEstadoPersonal(final Long id) throws ServicioException {
        try {
            return estadoPersonalDao.buscarPorId(id);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este metodo busca los movimientos por Id.
     *
     * @param nombre
     * @param numeroIdentificacion
     * @return
     * @throws ServicioException
     */
    public List<Servidor> buscarServidorPorNombreYIdentificacion(final String nombre, final String numeroIdentificacion)
            throws ServicioException {
        try {
            List<Servidor> servidor = new ArrayList<Servidor>();
            servidor = servidorDao.buscarPorNombreYIdentificacion(nombre.toUpperCase(), numeroIdentificacion);
            return servidor;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @return @throws ServicioException
     */
    public List<Feriado> buscarFeriados() throws ServicioException {
        try {
            return feriadoDao.buscarVigente();
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param catalogoId
     * @return
     * @throws DaoException
     */
    public Catalogo buscarCatalogoPorId(final Long catalogoId) throws DaoException {
        try {
            Catalogo entidad = catalogoDao.buscarPorId(catalogoId);
            return entidad;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     *
     * @param catalogoCod
     * @param tipoCatalogoCod
     * @return
     * @throws DaoException
     */
    public Catalogo buscarCatalogoPorCodigoYTipoCatalogo(
            final String catalogoCod,
            final String tipoCatalogoCod) throws DaoException {
        try {
            return catalogoDao.buscar(catalogoCod, tipoCatalogoCod);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Permite buscar los sectores vigentes
     *
     * @return
     * @throws ServicioException
     */
    public List<Sector> listarTodosSectores() throws ServicioException {
        try {
            return sectorDao.buscarVigentes();
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método permite crear un grupo.
     *
     * @param t Tercero
     * @throws ServicioException
     */
    public void guardarTercero(final Tercero t) throws ServicioException {
        try {
            t.setNombres(t.getNombres().toUpperCase());
            terceroDao.crear(t);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método permite actualizar un grupo.
     *
     * @param t tercero
     * @throws ServicioException
     */
    public void actualizarTercero(final Tercero t) throws ServicioException {
        try {
            t.setNombres(t.getNombres().toUpperCase());
            terceroDao.actualizar(t);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método procesa la eliminación de un tercero.
     *
     * @param t Tercero
     * @throws ServicioException
     */
    public void eliminarTercero(final Tercero t) throws ServicioException {
        try {
            t.setVigente(Boolean.FALSE);
            terceroDao.actualizar(t);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método permite listar los grupos.
     *
     * @param tipoIdentificacion String
     * @param numeroIdentificacion String
     * @return
     * @throws ServicioException
     */
    public List<Tercero> listarTodosTerceroPorIdentificacion(final String tipoIdentificacion,
            final String numeroIdentificacion)
            throws ServicioException {
        try {
            List<Tercero> lista;

            lista = terceroDao.buscarVigentePorIdentificacion(tipoIdentificacion, numeroIdentificacion);

            return lista;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Este método permite listar los grupos.
     *
     * @param estado String
     * @param idInstitucionEjercicio String
     * @return
     * @throws ServicioException
     */
    public List<Tercero> listarTodosTerceroPorEstado(final Long ejercicioFiscalId, final String estado)
            throws ServicioException {
        try {
            List<Tercero> lista;

            lista = terceroDao.buscarPorEstado(ejercicioFiscalId, estado);

            return lista;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Este método permite listar todos los terceros vigentes.
     *
     * @return
     * @throws ServicioException
     */
    public List<Tercero> listarTodosTerceros() throws ServicioException {
        try {
            List<Tercero> lista;
            lista = terceroDao.buscarVigente();
            return lista;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Este método permite listar todos los terceros vigentes.
     *
     * @param idTercero
     * @return
     * @throws ServicioException
     */
    public List<TerceroNominaDetalle> listarTodosTercerosEnNominaPorTercero(final Long idTercero) throws
            ServicioException {
        try {
            return terceroNominaDetalleDao.buscarPorTercero(idTercero);
        } catch (DaoException e) {
            throw new ServicioException(e);
        }

    }

    /**
     * obtiene una unidad organizacional por id
     *
     * @param id
     * @return
     * @throws ServicioException
     */
    public UnidadOrganizacional obtenerUnidadOrganizacionalPorId(final Long id) throws ServicioException {
        try {
            return unidadOrganizacionalDao.buscarPorId(id);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     *
     * @return @throws ServicioException
     */
    public List<Partida> buscarPartidasVigentes() throws ServicioException {
        try {
            return partidaDao.buscarVigentes();
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }

    }

    /**
     * Este método permite crear partida.
     *
     * @param partida
     * @throws ServicioException
     */
    public void guardarPartida(final Partida partida) throws ServicioException {
        try {
            partida.setDescripcion(""/*partida.getDescripcion().toUpperCase()*/);
            partida.setCodigo(partida.getCodigo().toUpperCase());
            partida.setNombre(partida.getNombre().toUpperCase());
            partidaDao.crear(partida);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método permite actualizar una partida.
     *
     * @param partida
     * @throws ServicioException
     */
    public void actualizarPartida(final Partida partida) throws ServicioException {
        try {
            partidaDao.actualizar(partida);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Método para eliminación de partida.
     *
     * @param partida
     * @throws ServicioException
     */
    public void eliminarPartida(final Partida partida) throws ServicioException {
        try {
            partida.setVigente(Boolean.FALSE);
            partidaDao.actualizar(partida);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método permite listar partidas por código.
     *
     * @param codigo String
     * @return
     * @throws ServicioException
     */
    public List<Partida> listarPartidasPorCodigo(final String codigo) throws ServicioException {
        try {
            List<Partida> lista;
            lista = partidaDao.buscarPorCodigo(codigo.toUpperCase());
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Este método permite buscar partidas por código o nombre.
     *
     * @param nombre
     * @param codigo
     * @return List<Partida> lista
     * @throws ServicioException
     */
    public List<Partida> listarPartidasPorCodigoONombre(final String codigo, final String nombre)
            throws ServicioException {
        try {
            List<Partida> lista;
            lista = partidaDao.buscarPorConsultaNombrada(Partida.BUSCAR_POR_CODIGO_Y_NOMBRE,
                    codigo.toUpperCase(), nombre.toUpperCase());
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Este método permite buscar partidas por nombre.
     *
     * @param nombre
     * @return List<Partida> lista
     * @throws ServicioException
     */
    public List<Partida> listarPartidasPorNombre(final String nombre, Long id)
            throws ServicioException {
        try {
            List<Partida> lista;
            lista = partidaDao.buscarPorConsultaNombrada(Partida.BUSCAR_POR_NOMBRE, nombre.toUpperCase(), id);
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     *
     * @return @throws ServicioException
     */
    public List<CodigoContable> buscarCodigosContablesVigentes() throws ServicioException {
        try {
            return codigoContableDao.buscarVigentes();
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }

    }

    /**
     * Este método permite crear codigo contable.
     *
     * @param codigoContable
     * @throws ServicioException
     */
    public void guardarCodigoContable(final CodigoContable codigoContable) throws ServicioException {
        try {
            //codigoContable.setCodigo(codigoContable.getCodigo().toUpperCase());
            codigoContable.setNombre(codigoContable.getNombre().toUpperCase());
            codigoContableDao.crear(codigoContable);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método permite actualizar un codigo contable.
     *
     * @param codigoContable
     * @throws ServicioException
     */
    public void actualizarCodigoContable(final CodigoContable codigoContable) throws ServicioException {
        try {
            codigoContableDao.actualizar(codigoContable);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Método para eliminación de codigo contable.
     *
     * @param codigoContable
     * @throws ServicioException
     */
    public void eliminarCodigoContable(final CodigoContable codigoContable) throws ServicioException {
        try {
            codigoContable.setVigente(Boolean.FALSE);
            codigoContableDao.actualizar(codigoContable);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método permite listar codigos contables (busca por código).
     *
     * @param codigo
     * @return
     * @throws ServicioException
     */
    public List<CodigoContable> listarCodigosContablesPorCodigo(final String codigo) throws ServicioException {
        try {
            List<CodigoContable> lista;
            lista = codigoContableDao.buscarPorConsultaNombrada(CodigoContable.BUSCAR_POR_CODIGO, codigo.toUpperCase());
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Este método permite busca codigos contables por nombre.
     *
     * @param nombre
     * @return
     * @throws ServicioException
     */
    public List<CodigoContable> listarCodigosContablesPorNombre(final String nombre) throws ServicioException {
        try {
            List<CodigoContable> lista;
            lista = codigoContableDao.buscarPorConsultaNombrada(CodigoContable.BUSCAR_POR_NOMBRE, nombre.toUpperCase());
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Este método permite buscar codigos contables por código o nombre.
     *
     * @param nombre
     * @param codigo
     * @return List<CodigoContable> lista
     * @throws ServicioException
     */
    public List<CodigoContable> listarCodigosContablesPorCodigoONombre(final String codigo, final String nombre)
            throws ServicioException {
        try {
            List<CodigoContable> lista;
            lista = codigoContableDao.buscarPorConsultaNombrada(CodigoContable.BUSCAR_POR_CODIGO_Y_NOMBRE,
                    codigo.toUpperCase(), nombre.toUpperCase());
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Permite copiar la información del reclutamiento a las tablas de detalles
     * del servidor, para ello si existiera información en el servidor se
     * inabilita e incorpora la nueva información. llenar servidor_capacitacion
     * con reclutamientos_capacitacion llenar servidor_instrucion con
     * reclutamientos_instrucciones llenar servidor_experiencia con
     * reclutamientos_trayectoria_laboral
     *
     * @param s servidor
     * @param r reclutamiento
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public void copiarDetallesReclutamientoAlServidor(final Servidor s, final Reclutamiento r) throws ServicioException {
        try {
            s.setCatalogoGeneroId(r.getCatalogoGeneroId());
            s.setCatalogoNacionalidadId(r.getCatalogoNacionalidadId());
            s.setTelefono(r.getTelefono());
            s.setCelular(r.getCelular());
            s.setMail(r.getMail());
            s.setHoraEntrada(r.getHoraEntrada());
            s.setJornada(r.getJornada());
            s.setBarrioDomicilio(r.getCallePrincipal());
            s.setCalleDomicilio(r.getCalleSecundaria() + " " + r.getNumeroDomicilio() + " " + r.getReferenciaDomicilio());
            servidorDao.actualizar(s);

            List<ServidorCapacitacion> capacitacionServidor = servidorCapacitacionDao.buscarPorServidorId(s.getId());
            List<ServidorInstruccion> instruccionServidor = servidorInstruccionDao.buscarPorServidorId(s.getId());
            List<ServidorExperiencia> experienciaServidor = servidorExperienciaDao.buscarPorServidorId(s.getId());
            List<ReclutamientoCapacitacion> capacitacionesReclut
                    = reclutamientoServicio.listarTodosReclutamientoCapacitacionPorIdReclutado(r.getId());
            List<ReclutamientoInstruccion> instruccionReclut
                    = reclutamientoServicio.listarTodosReclutamientoInstruccionPorIdReclutado(r.getId());
            List<ReclutamientoTrayectoriaLaboral> trayectoriaReclut
                    = reclutamientoServicio.listarTodosReclutamientoTrayectoriaPorIdReclutado(r.getId());

            if (!capacitacionServidor.isEmpty()) {
                for (ServidorCapacitacion sc : capacitacionServidor) {
                    sc.setUsuarioActualizacion(r.getUsuarioActualizacion());
                    sc.setFechaActualizacion(new Date());
                    sc.setVigente(Boolean.FALSE);
                    servidorCapacitacionDao.actualizar(sc);
                }
            }
            if (!instruccionServidor.isEmpty()) {
                for (ServidorInstruccion si : instruccionServidor) {
                    si.setUsuarioActualizacion(r.getUsuarioActualizacion());
                    si.setFechaActualizacion(new Date());
                    si.setVigente(Boolean.FALSE);
                    servidorInstruccionDao.actualizar(si);
                }
            }

            if (!experienciaServidor.isEmpty()) {
                for (ServidorExperiencia se : experienciaServidor) {
                    se.setUsuarioActualizacion(r.getUsuarioActualizacion());
                    se.setFechaActualizacion(new Date());
                    se.setVigente(Boolean.FALSE);
                    servidorExperienciaDao.actualizar(se);
                }
            }
            if (!capacitacionesReclut.isEmpty()) {
                for (ReclutamientoCapacitacion cr : capacitacionesReclut) {
                    ServidorCapacitacion sc = new ServidorCapacitacion();
                    sc.setUsuarioCreacion(r.getUsuarioActualizacion());
                    sc.setFechaCreacion(new Date());
                    sc.setVigente(Boolean.TRUE);
                    sc.setCapacitador(cr.getNombreInstitucion());
                    sc.setEvento(cr.getNombreEvento());
                    sc.setDuracionHoras(Integer.parseInt(cr.getDuracionHoras().toString()));
                    sc.setServidor(s);
                    sc.setServidorId(s.getId());
                    sc.setTipoDiploma(cr.getTipoDiploma());
                    sc.setEstado(EstadoDatoAdiconalServidorEnum.APROBADO.getCodigo());
                    servidorCapacitacionDao.crear(sc);
                }
            }

            if (!instruccionReclut.isEmpty()) {
                for (ReclutamientoInstruccion ri : instruccionReclut) {
                    ServidorInstruccion si = new ServidorInstruccion();
                    si.setUsuarioCreacion(r.getUsuarioActualizacion());
                    si.setFechaCreacion(new Date());
                    si.setVigente(Boolean.TRUE);
                    si.setCatalogoNivelInstruccionId(ri.getCatalogoNivelInstruccion() != null
                            ? ri.getCatalogoNivelInstruccion().getId() : null);
                    si.setServidor(s);
                    si.setServidorId(s.getId());
                    si.setEspecializacion(ri.getEspecializacion());
                    si.setTitulo(ri.getTituloObtenido());
                    si.setUnidadEducativa(ri.getNombreInstitucion());
                    si.setEstado(EstadoDatoAdiconalServidorEnum.APROBADO.getCodigo());
                    servidorInstruccionDao.crear(si);
                }
            }

            if (!trayectoriaReclut.isEmpty()) {
                for (ReclutamientoTrayectoriaLaboral rt : trayectoriaReclut) {
                    ServidorExperiencia se = new ServidorExperiencia();
                    se.setUsuarioCreacion(r.getUsuarioActualizacion());
                    se.setFechaCreacion(new Date());
                    se.setVigente(Boolean.TRUE);
                    se.setServidor(s);
                    se.setServidorId(s.getId());
                    se.setDenominacionPuesto(rt.getDenominacionPuesto());
                    se.setEmpresa(rt.getOrganizacionEmpresa());
                    se.setFechaDesde(rt.getFechaInicio());
                    se.setFechaHasta(rt.getFechaFin());
                    se.setRazonSalida(rt.getRazonSalida());
                    se.setActividades(rt.getResponsabilidades());
                    servidorExperienciaDao.crear(se);
                    se.setEstado(EstadoDatoAdiconalServidorEnum.APROBADO.getCodigo());
                }
            }
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Lista de ImpuestoRenta segun ejercicio fiscal
     *
     * @param idEjercicioFiscal
     * @return
     * @throws ServicioException
     */
    public List<ImpuestoRenta> listarImpuestosRenta(final Long idEjercicioFiscal) throws ServicioException {
        List<ImpuestoRenta> lista = null;
        try {
            lista = impuestoRentaDao.listarPorEjercicioFiscal(idEjercicioFiscal);
        } catch (DaoException ex) {
            Logger.getLogger(AdministracionServicio.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
        return lista;
    }

    /**
     * Persiste una nueva entidad ImpuestoRenta en la base de datos
     *
     * @param impuestoRenta
     * @throws ServicioException
     */
    public void guardarImpuestoRenta(final ImpuestoRenta impuestoRenta) throws ServicioException {
        try {
            impuestoRentaDao.crear(impuestoRenta);
        } catch (DaoException ex) {
            Logger.getLogger(AdministracionServicio.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    /**
     * Actualiza el estado de una entidad ImpuestoRenta
     *
     * @param impuestoRenta
     * @throws ServicioException
     */
    public void actualizarImpuestoRenta(final ImpuestoRenta impuestoRenta) throws ServicioException {
        try {
            impuestoRentaDao.actualizar(impuestoRenta);
        } catch (DaoException ex) {
            Logger.getLogger(AdministracionServicio.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    /**
     * Elimina de forma logica un ImpuestoRenta
     *
     * @param impuestoRenta
     * @throws ServicioException
     */
    public void eliminarImpuestoRenta(final ImpuestoRenta impuestoRenta) throws ServicioException {
        try {
            impuestoRenta.setVigente(Boolean.FALSE);
            impuestoRentaDao.actualizar(impuestoRenta);
        } catch (DaoException ex) {
            Logger.getLogger(AdministracionServicio.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    /**
     * Busca una CuentaBancaria dado el Banco, el Tipo de Cuenta, y el Numero de
     * Cuenta
     *
     * @param bancoId
     * @param tipoCuenta
     * @param numeroCuenta
     * @return
     */
    public CuentaBancaria buscarCuentaPorBancoTipoYNumero(
            final Long bancoId, final String tipoCuenta, final String numeroCuenta) throws ServicioException {
        CuentaBancaria cuentaBancaria = null;
        try {
            cuentaBancaria = cuentaBancariaDao.buscarPorBancoTipoyNumero(bancoId, tipoCuenta, numeroCuenta);
        } catch (DaoException ex) {
            Logger.getLogger(AdministracionServicio.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
        return cuentaBancaria;
    }

    public void actualizarCuentasBancarias(final Servidor servidor) throws ServicioException {
        for (CuentaBancaria cb : servidor.getCuentasBancarias()) {
            try {
                if (cb.getId() == null) {
                    if (cb.getPagoNomina() == null) {
                        cb.setPagoNomina(Boolean.FALSE);
                    }
                    System.out.println(cb.getBanco());
                    System.out.println(cb.getBancoId());
                    cuentaBancariaDao.crear(cb);
                } else {
                    cuentaBancariaDao.actualizar(cb);
                }
            } catch (DaoException ex) {
                Logger.getLogger(AdministracionServicio.class.getName()).log(Level.SEVERE, null, ex);
                throw new ServicioException(ex);
            }
        }
    }

    public Banco buscarBanco(final Long id) throws ServicioException {
        try {
            return bancoDao.buscarPorId(id);
        } catch (DaoException ex) {
            Logger.getLogger(AdministracionServicio.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    /**
     *
     * @param usuarioVO
     * @return
     * @throws ServicioException
     */
//    public Boolean esUnidadOrganizacionDeRecursosHumanos(final UsuarioVO usuarioVO) throws ServicioException {
//        try {
//            Boolean resultado = Boolean.FALSE;
//            ParametroInstitucional pi = parametroInstitucionalDao.buscarPorInstitucionYNemonico(
//                    usuarioVO.getInstitucion().getId(), ParametroInstitucionCatalogoEnum.CODIGO_DIRECCION_RRHH.getCodigo());
//            List<UnidadOrganizacional> uos = unidadOrganizacionalDao.buscarPorNemonico(
//                    usuarioVO.getDistributivoDetalle().getDistributivo().getUnidadOrganizacional().getCodigo());
//            if (!uos.isEmpty()) {
//                UnidadOrganizacional uoTemporal = uos.get(0);
//                while (uoTemporal != null) {
//                    if (uoTemporal.getCodigo().trim().equals(pi.getValorTexto().trim())) {
//                        resultado = Boolean.TRUE;
//                        break;
//                    }
//                    uoTemporal = uoTemporal.getUnidadOrganizacional();
//                }
//            }
//            return resultado;
//        } catch (Exception e) {
//            throw new ServicioException(e);
//        }
//    }
    /**
     * Retorna el primer rol con el codigo especificado o null
     *
     * @param codigo
     * @return
     * @throws ServicioException
     */
    public Rol buscarRolPorCodigo(final String codigo) throws ServicioException {
        try {
            return rolDao.buscarPorCodigo(codigo);
        } catch (DaoException ex) {
            Logger.getLogger(AdministracionServicio.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo busca estados administracion de puestos - regimen laboral por
     * id del estado administracion de puestos.
     *
     * @param estadoAdministracionPuestoId
     * @return List
     * @throws ServicioException ServicioException
     */
    public List<EstadoAdministracionPuestoRegimenLaboral> buscarEstadoAdministracionPuestoRegimenLaboralPorEstadoId(
            final Long estadoAdministracionPuestoId) throws ServicioException {
        try {
            return estadoAdministracionPuestoRegimenLaboralDao
                    .buscarPorEstadoAdministracionPuestoId(estadoAdministracionPuestoId);
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este metodo busca estados administracion de puestos - regimen laboral
     * vigentes
     *
     * @return List
     * @throws ServicioException ServicioException
     */
    public List<EstadoAdministracionPuestoRegimenLaboral> buscarEstadoAdministracionPuestoRegimenLaboralVigentes()
            throws ServicioException {
        try {
            return estadoAdministracionPuestoRegimenLaboralDao.buscarTodosVigentes();
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este metodo busca todos los estados administracion de puestos - regimen
     * laboral por regimen laboral id.
     *
     * @param regimenLaboralId
     * @return List
     * @throws ServicioException ServicioException
     */
    public List<EstadoAdministracionPuestoRegimenLaboral> buscarEstadoAdministracionPuestoRegimenLaboralPorRegimenId(
            final Long regimenLaboralId) throws ServicioException {
        try {
            return estadoAdministracionPuestoRegimenLaboralDao.buscarPorRegimenLaboralId(regimenLaboralId);
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de guardar los estados administracion puesto -
     * regimen laboral en la base de datos.
     *
     * @param usuario Usuario Logeado
     * @param regimenLaboralId id del regimen laboral editado
     * @param lista lista a guardar
     * @throws ServicioException En caso de error
     */
    public void guardarEstadoAdministracionPuestoRegimenLaboral(final String usuario, final Long regimenLaboralId,
            final List<EstadoAdministracionPuestoRegimenLaboralVO> lista) throws ServicioException {
        try {
            RegimenLaboral regimen = regimenLaboralServicio.buscarRegimenLaboralPorId(regimenLaboralId);

            for (EstadoAdministracionPuestoRegimenLaboralVO ervo : lista) {

                EstadoAdministracionPuestoRegimenLaboral earl = estadoAdministracionPuestoRegimenLaboralDao
                        .buscarPorEstadoAdministracionPuestoIdYRegimenLaboralId(ervo.
                                getEstadoAdministracionPuesto().getId(), regimenLaboralId);

                if (earl != null) {
                    earl.setVigente(Boolean.FALSE);
                    estadoAdministracionPuestoRegimenLaboralDao.actualizar(earl);
                }

                if (ervo.isSeleccionado()) {
                    EstadoAdministracionPuestoRegimenLaboral er = new EstadoAdministracionPuestoRegimenLaboral();
                    er.setEstadoAdministracionPuesto(ervo.getEstadoAdministracionPuesto());
                    er.setRegimenLaboral(regimen);
                    er.setUsuarioCreacion(usuario);
                    er.setFechaCreacion(new Date());
                    er.setVigente(Boolean.TRUE);
                    estadoAdministracionPuestoRegimenLaboralDao.crear(er);
                }

            }
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

}
