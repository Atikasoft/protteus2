/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.servicio;

import ec.com.atikasoft.proteus.dao.ArchivoDao;
import ec.com.atikasoft.proteus.dao.InstitucionDao;
import ec.com.atikasoft.proteus.dao.ParametroGlobalDao;
import ec.com.atikasoft.proteus.dao.ParametroInstitucionalDao;
import ec.com.atikasoft.proteus.dao.PersonalOtrasInstitucionesDao;
import ec.com.atikasoft.proteus.dao.ServidorDao;
import ec.com.atikasoft.proteus.enums.ParametroGlobalEnum;
import ec.com.atikasoft.proteus.enums.ParametroInstitucionCatalogoEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.Archivo;
import ec.com.atikasoft.proteus.modelo.Institucion;
import ec.com.atikasoft.proteus.modelo.ParametroGlobal;
import ec.com.atikasoft.proteus.modelo.ParametroInstitucional;
import ec.com.atikasoft.proteus.modelo.PersonalOtrasInstituciones;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.pdf.GenerarAccionPersonalOtraInstitucionRegistro;
import ec.com.atikasoft.proteus.pdf.GenerarAccionPersonalOtraInstitucionTerminacion;
import ec.com.atikasoft.proteus.pdf.MarcaAgua;
import ec.com.atikasoft.proteus.servicio.base.BaseServicio;
import ec.com.atikasoft.proteus.util.UtilArchivos;
import ec.com.atikasoft.proteus.vo.UsuarioVO;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author atikasoft
 */
@Stateless
@LocalBean
public class PersonalOtrasInstitucionesServicio extends BaseServicio {

    /**
     * Logger de clase.
     */
    private Logger LOG = Logger.getLogger(PersonalOtrasInstitucionesServicio.class.getCanonicalName());

    /**
     * DAO de Distributivo
     */
    @EJB
    private PersonalOtrasInstitucionesDao personalOtrasInstitucionesDao;

    /**
     * servidor dao
     */
    @EJB
    private ServidorDao servidorDao;

    /**
     * archivo dao
     */
    @EJB
    private ArchivoDao archivoDao;

    /**
     * parametro institucional dao
     */
    @EJB
    private ParametroInstitucionalDao parametroInstitucionalDao;

    /**
     * institucion dao
     */
    @EJB
    private InstitucionDao institucionDao;

    /**
     *
     */
    @EJB
    private ParametroGlobalDao parametroGlobalDao;

    /**
     * Busca personal de otras instituciones por numero y tipo de identificación
     *
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @return
     * @throws ServicioException
     */
    public PersonalOtrasInstituciones buscarPersonalOtrasInstituciones(final String tipoIdentificacion,
            final String numeroIdentificacion) throws ServicioException {
        try {
            return personalOtrasInstitucionesDao.buscar(tipoIdentificacion, numeroIdentificacion);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Busca personal vigente de otras instituciones por numero y tipo de identificación
     *
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @return PersonalOtrasInstituciones
     * @throws ServicioException
     */
    public PersonalOtrasInstituciones buscarPersonalOtrasInstitucionesVigente(final String tipoIdentificacion,
            final String numeroIdentificacion) throws ServicioException {
        try {
            return personalOtrasInstitucionesDao.buscarVigentesPorIdentificacion(tipoIdentificacion, numeroIdentificacion);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este metodo busca personal de otras instituciones filtrando por nombre.
     *
     * @param nombre
     * @return
     * @throws ServicioException
     */
    public List<PersonalOtrasInstituciones> buscarPersonalOtrasInstitucionesPorNombre(final String nombre) throws
            ServicioException {
        try {
            return personalOtrasInstitucionesDao.buscarPorNombre(nombre);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este metodo busca personal de otras instituciones filtrando las vigentes por nombre.
     *
     * @param nombre
     * @return
     * @throws ServicioException
     */
    public List<PersonalOtrasInstituciones> buscarPersonalOtrasInstitucionesVigentesPorNombre(final String nombre) throws
            ServicioException {
        try {
            return personalOtrasInstitucionesDao.buscarVigentesPorNombre(nombre);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este metodo busca personal de otras instituciones con el estado especificado.
     *
     * @param vigente
     * @return
     * @throws ServicioException
     */
    public List<PersonalOtrasInstituciones> buscarPersonalOtrasInstitucionesPorEstado(final Boolean vigente) throws
            ServicioException {
        try {
            return personalOtrasInstitucionesDao.buscarPorEstado(vigente);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este metodo procesa la creacion de una persona de otra institución.
     *
     * @param p PersonalOtrasInstituciones que se desea guardar
     * @throws ServicioException ServicioException
     */
    public void guardarPersonalOtrasInstituciones(final PersonalOtrasInstituciones p) throws ServicioException {
        try {
            p.setApellidosNombres(p.getApellidosNombres().toUpperCase());
            personalOtrasInstitucionesDao.crear(p);
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método transacciona la eliminacion de persona de otra institución.
     *
     * @param p
     * @throws ServicioException ServicioException
     */
    public void eliminarPersonalOtrasInstituciones(final PersonalOtrasInstituciones p) throws ServicioException {
        try {
            p.setVigente(Boolean.FALSE);
            personalOtrasInstitucionesDao.actualizar(p);
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
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
    public Servidor buscarServidor(final String tipoDocumento, final String numeroDocumento) throws ServicioException {
        try {
            return servidorDao.buscarPorIdentificacionExternoInternoConPuesto(tipoDocumento, numeroDocumento);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Genera pdf de Accion de Personal para Registro de Personal Otra Intitucion
     *
     * @param personal
     * @param usuario
     * @throws ServicioException
     */
    public void generarPdfAccionPersonalOtrasInstitucionesRegistro(
            final PersonalOtrasInstituciones personal,
            UsuarioVO usuario) throws ServicioException {

        try {
            String numeroAccion = generarNumeroAccionPersonalOtrasInstitucionesRegistro(
                    usuario.getInstitucion().getId());

            ParametroInstitucional pi = parametroInstitucionalDao
                    .buscarPorNemonico(ParametroInstitucionCatalogoEnum.LOGO_DE_INSTITUCION.getCodigo());

            ParametroInstitucional piResolucion
                    = parametroInstitucionalDao.buscarPorNemonico(
                            ParametroInstitucionCatalogoEnum.DESCRIPCION_DE_RESOLUCION_PARA_ACCION_DE_PERSONAL_REGISTRO_PERSONAL_OTRAS_INTITUCIONES.
                            getCodigo());

            //-----------generando y persistiendo archivo pdf------------------
            GenerarAccionPersonalOtraInstitucionRegistro gap = new GenerarAccionPersonalOtraInstitucionRegistro(
                    personal, usuario.getServidor().getApellidosNombres(), numeroAccion,
                    pi.getArchivo().getArchivo(), piResolucion.getValorTexto());

            File pdf = gap.generar();

            ParametroGlobal repos = parametroGlobalDao.buscarPorNemonico(ParametroGlobalEnum.REPOS.getCodigo());
            File marcaAgua = new File(repos.getValorTexto() + File.separator + "reportes" + File.separator + "img"
                    + File.separator + "marca_agua.png");
            File pdfMarcaAgua = MarcaAgua.procesar(pdf, marcaAgua);

            Archivo a = new Archivo();
            a.setUsuarioCreacion(usuario.getUsuario());
            a.setFechaCreacion(new Date());
            a.setVigente(Boolean.TRUE);
            a.setDescripcion("ARCHIVO DE ACCIÓN DE PERSONAL POR REGISTRO DE COMISIÓN DE SERVICIOS "
                    + "CON REMUNERACIÓN DE OTRA INSTITUCIÓN");
            a.setNombre(pdfMarcaAgua.getName());
            a.setPalabrasClave("personal otras instituciones");
            a.setArchivo(UtilArchivos.getBytesFromFile(pdfMarcaAgua));
            archivoDao.crear(a);
            archivoDao.flush();

            //--------------------ASIGNAR ARCHIVO ACCION DE INGRESO ---------------------------
            personal.setNumeroArchivoAccionIngreso(numeroAccion);
            personal.setArchivoAccionIngreso(a);
            personalOtrasInstitucionesDao.actualizar(personal);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServicioException("Error generando archivo acción de registro de personal de otras instituciones.", e);
        }
    }

    /**
     * Genera pdf de Accion de Personal para Terminacion de Personal Otra Intitucion
     *
     * @param personal
     * @param usuario
     * @throws ServicioException
     */
    public void generarPdfAccionPersonalOtrasInstitucionesTerminacion(
            final PersonalOtrasInstituciones personal,
            UsuarioVO usuario) throws ServicioException {

        try {
            String numeroAccion = generarNumeroAccionPersonalOtrasInstitucionesTerminacion(
                    usuario.getInstitucion().getId());

            ParametroInstitucional pi = parametroInstitucionalDao
                    .buscarPorNemonico(ParametroInstitucionCatalogoEnum.LOGO_DE_INSTITUCION.getCodigo());

            ParametroInstitucional piResolucion = parametroInstitucionalDao.buscarPorNemonico(
                    ParametroInstitucionCatalogoEnum.DESCRIPCION_DE_RESOLUCION_PARA_ACCION_DE_PERSONAL_TERMINACION_PERSONAL_OTRAS_INTITUCIONES.
                    getCodigo());

            //-------------generando y persistiendo archivo pdf---------------------
            GenerarAccionPersonalOtraInstitucionTerminacion gap = new GenerarAccionPersonalOtraInstitucionTerminacion(
                    personal, usuario.getServidor().getApellidosNombres(), numeroAccion,
                    pi.getArchivo().getArchivo(), piResolucion.getValorTexto());

            File pdf = gap.generar();

            ParametroGlobal repos = parametroGlobalDao.buscarPorNemonico(ParametroGlobalEnum.REPOS.getCodigo());
            File marcaAgua = new File(repos.getValorTexto() + File.separator + "reportes" + File.separator + "img"
                    + File.separator + "marca_agua.png");
            File pdfMarcaAgua = MarcaAgua.procesar(pdf, marcaAgua);

            Archivo a = new Archivo();
            a.setUsuarioCreacion(usuario.getUsuario());
            a.setFechaCreacion(new Date());
            a.setVigente(Boolean.TRUE);
            a.setDescripcion("ARCHIVO DE ACCIÓN DE PERSONAL POR TERMINACIÓN DE COMISIÓN DE SERVICIOS "
                    + "CON REMUNERACIÓN DE OTRA INSTITUCIÓN");
            a.setNombre(pdfMarcaAgua.getName());
            a.setPalabrasClave("personal otras instituciones");
            a.setArchivo(UtilArchivos.getBytesFromFile(pdfMarcaAgua));
            archivoDao.crear(a);
            archivoDao.flush();

            //-----------------------ASIGNAR ARCHIVO ACCION DE TERMINACION -----------------------------
            personal.setNumeroArchivoAccionSalida(numeroAccion);
            personal.setArchivoAccionSalida(a);
            personalOtrasInstitucionesDao.actualizar(personal);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServicioException("Error generando archivo acción de terminación de personal de otras instituciones.", e);
        }
    }

    /**
     * metodo para obtener el numero accion de registro de personal de otras instituciones
     *
     * @param institucionId
     * @return
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public String generarNumeroAccionPersonalOtrasInstitucionesRegistro(final Long institucionId) throws ServicioException {
        try {
            Institucion institucion = institucionDao.buscarPorId(institucionId);
            institucionDao.lock(institucion);
            institucion.setContadorAccionPersonalOtrasInstitucionesRegistro(
                    institucion.getContadorAccionPersonalOtrasInstitucionesRegistro() + 1);
            institucionDao.actualizar(institucion);
            institucionDao.flush();
            return institucion.getContadorAccionPersonalOtrasInstitucionesRegistro().toString();

        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * metodo para obtener el numero accion de terminación de personal de otras instituciones
     *
     * @param institucionId
     * @return
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public String generarNumeroAccionPersonalOtrasInstitucionesTerminacion(final Long institucionId) throws ServicioException {
        try {
            Institucion institucion = institucionDao.buscarPorId(institucionId);
            institucionDao.lock(institucion);
            institucion.setContadorAccionPersonalOtrasInstitucionesTerminacion(
                    institucion.getContadorAccionPersonalOtrasInstitucionesTerminacion() + 1);
            institucionDao.actualizar(institucion);
            institucionDao.flush();
            return institucion.getContadorAccionPersonalOtrasInstitucionesTerminacion().toString();

        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }
}
