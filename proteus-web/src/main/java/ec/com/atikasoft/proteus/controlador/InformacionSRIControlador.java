/*
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
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import ec.com.atikasoft.proteus.controlador.helper.SRIHelper;
import ec.com.atikasoft.proteus.dao.InstitucionEjercicioFiscalDao;
import ec.com.atikasoft.proteus.enums.MesesEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.modelo.InstitucionEjercicioFiscal;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.ImpuestoRentaServicio;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean(name = "informacionSRIBean")
@ViewScoped
public class InformacionSRIControlador extends BaseControlador {

    /**
     * Logger de clases.
     */
    private Logger LOG = Logger.getLogger(InformacionSRIControlador.class.getCanonicalName());

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/consultas/sri/informacion_sri.jsf";

    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;

    /**
     * Servicio de impuesto a la renta.
     */
    @EJB
    private ImpuestoRentaServicio sriServicio;

    /**
     * DAO de Ejercicio fiscal.
     */
    @EJB
    private InstitucionEjercicioFiscalDao institucionEjercicioFiscalDao;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{sriHelper}")
    private SRIHelper sriHelper;

    /**
     * Constructor por defecto.
     */
    public InformacionSRIControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        iniciarComboEjercicio();
        iniciarComboMeses();
    }

    private void iniciarComboEjercicio() {
        try {
            sriHelper.getInstitucionesEsjerciciosFiscales().clear();

            sriHelper.getInstitucionesEsjerciciosFiscales().addAll(institucionEjercicioFiscalDao.buscarPorInstitucion(
                    obtenerUsuarioConectado().getInstitucion().getId()));

            sriHelper.getListaPeriodoFiscal().clear();

            for (InstitucionEjercicioFiscal ef : sriHelper.getInstitucionesEsjerciciosFiscales()) {
                if (ef.getEjercicioFiscal().getVigente()) {
                    sriHelper.getListaPeriodoFiscal().add(new SelectItem(ef.getId(),
                            ef.getEjercicioFiscal().getNombre()));
                }
            }
        } catch (DaoException ex) {
            Logger.getLogger(InformacionSRIControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Busca la institucion ejercicio fiscal en la lista cargada en el helper
     *
     * @return
     */
    private InstitucionEjercicioFiscal buscarInstitucionEjercicioFiscal(Long institucionEjercicioId) {
        for (InstitucionEjercicioFiscal ie : sriHelper.getInstitucionesEsjerciciosFiscales()) {
            if (ie.getId().equals(institucionEjercicioId)) {
                return ie;
            }
        }
        return null;
    }

    /**
     * llena combo de meses.
     */
    private void iniciarComboMeses() {
        sriHelper.getOpcionesMeses().clear();
        iniciarCombosTodos(sriHelper.getOpcionesMeses());
        for (MesesEnum me : MesesEnum.values()) {
            sriHelper.getOpcionesMeses().add(new SelectItem(me.getNumero(),
                    me.getNombre()));
        }

    }

    /**
     * Genera excel Bases Imponibles.
     *
     * @return
     */
    public StreamedContent generarExcelBasesImponibles() {

        InstitucionEjercicioFiscal institucionEjercicio = buscarInstitucionEjercicioFiscal(
                sriHelper.getPeriodoFiscalBaseId());
        
        
        String nombre = "BASES_IMPONIBLES_" + institucionEjercicio.getEjercicioFiscal().getNombre()
                + (sriHelper.getMesBaseSeleccionado() == null ? "_" : ("_" + MesesEnum.obtenerNombre(
                                sriHelper.getMesBaseSeleccionado()) + "_"))
                        + (new SimpleDateFormat("yyyyMMddHHmm").format(new Date())) + ".xls";
       
        try {
            InputStream stream = sriServicio.generarExcelBasesImponibles(institucionEjercicio.
                    getInstitucion().getRuc(), institucionEjercicio.getEjercicioFiscal().getId(),
                    sriHelper.getMesBaseSeleccionado());
            StreamedContent sc = new DefaultStreamedContent(
                    stream, "application/vnd.ms-excel", nombre);

            //sriHelper.setMesSeleccionado(null);
            //sriHelper.setPeriodoFiscalId(sriHelper.getInstitucionesEsjerciciosFiscales().get(0).getId());
            return sc;
        } catch (Exception e) {
            e.getMessage();
        }

        return null;

    }

    /**
     * Genera XML RDEP.
     *
     * @return
     */
    public StreamedContent generarXMLRDEP() {
        InstitucionEjercicioFiscal institucionEjercicio = buscarInstitucionEjercicioFiscal(
                sriHelper.getPeriodoFiscalRDEPId());
        
        
        String nombre = "RDEP_" + institucionEjercicio.getEjercicioFiscal().getNombre()
                + (sriHelper.getMesRDEPSeleccionado()== null ? "_" : ("_" + MesesEnum.obtenerNombre(
                                sriHelper.getMesRDEPSeleccionado()) + "_"))
                        + (new SimpleDateFormat("yyyyMMddHHmm").format(new Date())) + ".xml";
       
        try {
            
            InputStream stream = sriServicio.obtenerRegistrosRDEP(institucionEjercicio.
                    getInstitucion().getRuc(), institucionEjercicio.getEjercicioFiscal().getId(),
                    sriHelper.getMesRDEPSeleccionado(), institucionEjercicio.getEjercicioFiscal().getNombre());
            StreamedContent sc = new DefaultStreamedContent(
                    stream, "application/xml", nombre);

            return sc;
        } catch (Exception e) {
            e.getMessage();
        }

        return null;
    }

    public void generarExcelFormulario104() {

    }

    public SRIHelper getSriHelper() {
        return sriHelper;
    }

    public void setSriHelper(SRIHelper sriHelper) {
        this.sriHelper = sriHelper;
    }

}
