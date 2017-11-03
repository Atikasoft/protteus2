/*
 *  MetadataTablaControlador.java
 *  SMP V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of SMP
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with SMP.
 *
 *  SMP
 *  Quito - Ecuador
 *
 *  03/10/2013
 *
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.CargaMasivaAtributoServidorHelper;
import ec.com.atikasoft.proteus.enums.TipoDocumentoEnum;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.ServidorServicio;
import ec.com.atikasoft.proteus.util.UtilArchivos;
import ec.com.atikasoft.proteus.vo.CargaMasivaServidorVO;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 * Carga Masiva de Atributos del Servidor
 *
 * @author Maikel Pérez Martínez <maikel.perez@markasoft.ec>
 */
@ManagedBean(name = "cargaMasivaAtributoServidorBean")
@ViewScoped
public class CargaMasivaAtributoServidorControlador extends CatalogoControlador {

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/carga_masiva_servidor/carga_masiva_servidor.jsf";

    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;

    @EJB
    private ServidorServicio servidorServicio;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{cargaMasivaAtributoServidorHelper}")
    private CargaMasivaAtributoServidorHelper cargaMasivaAtributoServidorHelper;

    /**
     * Valiable temporal para asignar consecutivos a los registros cargados
     */
    private Long id;

    /**
     * Constructor por defecto.
     */
    public CargaMasivaAtributoServidorControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        cargaMasivaAtributoServidorHelper.setNombreArchivo("");
        cargaMasivaAtributoServidorHelper.setArchivo(null);
        cargaMasivaAtributoServidorHelper.setArchivoCargado(null);
        cargaMasivaAtributoServidorHelper.setContent(null);
        cargaMasivaAtributoServidorHelper.setTipoCarga(CargaMasivaAtributoServidorHelper.FONDO_RESERVA);
        cargaMasivaAtributoServidorHelper.setSoloLectura(false);
        inicializarTiposCarga();
        inicializarTipoDocumentosDisponibles();
        inicializarResumen();
        inicializarTrazas();
        inicializarData();
        seleccionarTipoCarga();
        inicializarTiposErrores();
    }

    /**
     *
     */
    private void inicializarTiposCarga() {
        cargaMasivaAtributoServidorHelper.setTipoCarga(null);
        iniciarCombos(cargaMasivaAtributoServidorHelper.getTiposCarga());
        cargaMasivaAtributoServidorHelper.getTiposCarga().add(new SelectItem(CargaMasivaAtributoServidorHelper.FONDO_RESERVA, CargaMasivaAtributoServidorHelper.ETIQUETA_FONDO_RESERVA));
        cargaMasivaAtributoServidorHelper.getTiposCarga().add(new SelectItem(CargaMasivaAtributoServidorHelper.DECIMO_TERCERO, CargaMasivaAtributoServidorHelper.ETIQUETA_DECIMO_TERCERO));
        cargaMasivaAtributoServidorHelper.getTiposCarga().add(new SelectItem(CargaMasivaAtributoServidorHelper.DECIMO_CUARTO, CargaMasivaAtributoServidorHelper.ETIQUETA_DECIMO_CUARTO));
        cargaMasivaAtributoServidorHelper.getTiposCarga().add(new SelectItem(CargaMasivaAtributoServidorHelper.TOMA_TRANSPORTE, CargaMasivaAtributoServidorHelper.ETIQUETA_TOMA_TRANSPORTE));
    }

    private void inicializarTipoDocumentosDisponibles() {
        StringBuilder tb = new StringBuilder();
        TipoDocumentoEnum[] valores = TipoDocumentoEnum.values();
        int pos = 0;
        for (TipoDocumentoEnum td : valores) {
            if (pos++ > 0) {
                tb.append("|");
            }
            tb.append(td.getNemonico());
        }
        cargaMasivaAtributoServidorHelper.setTipoDocumentosDisponibles(tb.toString());
    }

    private void inicializarResumen() {
        cargaMasivaAtributoServidorHelper.setFallos(0);
        cargaMasivaAtributoServidorHelper.setRealizados(0);
        cargaMasivaAtributoServidorHelper.setTotalServidores(0);
    }

    private void inicializarTrazas() {
        cargaMasivaAtributoServidorHelper.getTrazas().clear();
    }

    private void inicializarData() {
        cargaMasivaAtributoServidorHelper.getDatos().clear();
        cargaMasivaAtributoServidorHelper.setRegistrosCargados(false);
    }

    public void seleccionarTipoCarga() {
        if (cargaMasivaAtributoServidorHelper.getTipoCarga() != null) {
            if (cargaMasivaAtributoServidorHelper.getTipoCarga().equals(CargaMasivaAtributoServidorHelper.FONDO_RESERVA)) {
                cargaMasivaAtributoServidorHelper.setNombreTipoCarga(CargaMasivaAtributoServidorHelper.ETIQUETA_FONDO_RESERVA);
            } else if (cargaMasivaAtributoServidorHelper.getTipoCarga().equals(CargaMasivaAtributoServidorHelper.TOMA_TRANSPORTE)) {
                cargaMasivaAtributoServidorHelper.setNombreTipoCarga(CargaMasivaAtributoServidorHelper.ETIQUETA_TOMA_TRANSPORTE);
            } else if (cargaMasivaAtributoServidorHelper.getTipoCarga().equals(CargaMasivaAtributoServidorHelper.DECIMO_TERCERO)) {
                cargaMasivaAtributoServidorHelper.setNombreTipoCarga(CargaMasivaAtributoServidorHelper.ETIQUETA_DECIMO_TERCERO);
            } else if (cargaMasivaAtributoServidorHelper.getTipoCarga().equals(CargaMasivaAtributoServidorHelper.DECIMO_CUARTO)) {
                cargaMasivaAtributoServidorHelper.setNombreTipoCarga(CargaMasivaAtributoServidorHelper.ETIQUETA_DECIMO_CUARTO);
            } else {
                cargaMasivaAtributoServidorHelper.setNombreTipoCarga("XXXXXXXXXXXXXXX");
            }
        }
    }

    private void inicializarTiposErrores() {
        cargaMasivaAtributoServidorHelper.getTiposError().clear();
        cargaMasivaAtributoServidorHelper.getTiposError().add(new SelectItem("", obtenerProperties().
                getString("ec.gob.mrl.smp.genericos.combo.consulta")));
        cargaMasivaAtributoServidorHelper.getTiposError().add(new SelectItem(CargaMasivaAtributoServidorHelper.TE_FORMATO,
                getBundle("ec.gob.mrl.smp.administracion.cargaMasivaServidor.tipoError.formato", null)));
        cargaMasivaAtributoServidorHelper.getTiposError().add(new SelectItem(CargaMasivaAtributoServidorHelper.TE_DATOS,
                getBundle("ec.gob.mrl.smp.administracion.cargaMasivaServidor.tipoError.datos", null)));
        cargaMasivaAtributoServidorHelper.getTiposError().add(new SelectItem(CargaMasivaAtributoServidorHelper.TE_CONSULTA,
                getBundle("ec.gob.mrl.smp.administracion.cargaMasivaServidor.tipoError.consulta", null)));
    }

    @Override
    public String guardar() {
        boolean error = false;
        if (!cargaMasivaAtributoServidorHelper.getRegistrosCargados()) {
            Integer tipo = cargaMasivaAtributoServidorHelper.getTipoCarga();
            File archivo = cargaMasivaAtributoServidorHelper.getArchivo();
            if (tipo == null) {
                mostrarMensajeEnPantalla("ec.gob.mrl.smp.administracion.cargaMasivaServidor.requerido.tipo", FacesMessage.SEVERITY_ERROR);
                error = true;
            }
            if (archivo == null) {
                mostrarMensajeEnPantalla("ec.gob.mrl.smp.administracion.cargaMasivaServidor.requerido.archivo", FacesMessage.SEVERITY_ERROR);
                error = true;
            }

            if (!error) {
                cargaMasivaAtributoServidorHelper.getDatos().clear();
                cargaMasivaAtributoServidorHelper.getTrazas().clear();
                parsear(archivo);
                Integer atendidos = cargaMasivaAtributoServidorHelper.getDatos().size();
                if (atendidos > 0) {
                    try {
                        List<CargaMasivaServidorVO> documentosIncorrectos
                                = servidorServicio.validarDocumentosExistentes(cargaMasivaAtributoServidorHelper.getDatos());
                        for (CargaMasivaServidorVO par : documentosIncorrectos) {
                            addError(getBundle("ec.gob.mrl.smp.administracion.cargaMasivaServidor.error.documentos",
                                    new Object[]{par.getTipoDocumento(), par.getDocumento()}), par.getLinea(), CargaMasivaAtributoServidorHelper.TE_CONSULTA);
                        }
                        cargaMasivaAtributoServidorHelper.getDatos().removeAll(documentosIncorrectos);

                        atendidos -= documentosIncorrectos.size();
                    } catch (ServicioException ex) {
                        Logger.getLogger(CargaMasivaAtributoServidorControlador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                Integer total = cargaMasivaAtributoServidorHelper.getTotalServidores();
                Integer fallidos = total - atendidos;
                cargaMasivaAtributoServidorHelper.setFallos(fallidos);
                cargaMasivaAtributoServidorHelper.setRealizados(atendidos);
                cargaMasivaAtributoServidorHelper.setRegistrosCargados(true);
            }
        }
        if (!error) {
            if (!cargaMasivaAtributoServidorHelper.getSoloLectura()) {
                try {
                    String atributo = null;
                    Integer att = cargaMasivaAtributoServidorHelper.getTipoCarga();
                    if (att == 1) {
                        atributo = "RFR";
                    }
                    if (att == 2) {
                        atributo = "TT";
                    }
                    if (att == 3) {
                        atributo = "DT";
                    }
                    if (att == 4) {
                        atributo = "DC";
                    }

                    if (atributo != null) {
                        servidorServicio.actualizarAtributoServidor(atributo, cargaMasivaAtributoServidorHelper.getDatos());
                        mostrarMensajeEnPantalla("ec.gob.mrl.smp.administracion.cargaMasivaServidor.satisfactorio", FacesMessage.SEVERITY_INFO);
                        cargaMasivaAtributoServidorHelper.setRegistrosCargados(false);
                    }
                } catch (ServicioException ex) {
                    Logger.getLogger(CargaMasivaAtributoServidorControlador.class.getName()).log(Level.SEVERE, null, ex);
                    mostrarMensajeEnPantalla("ec.gob.mrl.smp.administracion.cargaMasivaServidor.error", FacesMessage.SEVERITY_ERROR);
                }
            } else {
                mostrarMensajeEnPantalla("ec.gob.mrl.smp.administracion.cargaMasivaServidor.info.soloLectura", FacesMessage.SEVERITY_WARN);
            }
        }
        return null;
    }

    private void parsear(final File archivo) {
        try {
            FileInputStream fis = new FileInputStream(archivo);
            InputStreamReader isr = new InputStreamReader(fis, "utf8");
            BufferedReader br = new BufferedReader(isr);
            String linea;
            int num = 1;
            id = 1L;
            cargaMasivaAtributoServidorHelper.setTotalServidores(0);
            while ((linea = br.readLine()) != null) {
                List<CargaMasivaServidorVO> data = parsearLinea(linea, num++);
                if (!data.isEmpty()) {
                    cargaMasivaAtributoServidorHelper.getDatos().addAll(data);
                }
            }

            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensajeEnPantalla("ec.gob.mrl.smp.administracion.cargaMasivaServidor.error.lecturaFichero", FacesMessage.SEVERITY_ERROR);
        }
    }

    private List<CargaMasivaServidorVO> parsearLinea(final String linea, int num) {
        List<CargaMasivaServidorVO> data = new ArrayList<CargaMasivaServidorVO>();
        Integer total = 0;
        if (linea != null) {
            String[] dataLinea = linea.split(";");
            int length = dataLinea.length;
            for (int csvCount = 0; csvCount < length; csvCount += 3) {
                String tipoDocumento = dataLinea[csvCount];
                if (csvCount + 2 < length) {
                    String documento = dataLinea[csvCount + 1];
                    String valor = dataLinea[csvCount + 2];
                    CargaMasivaServidorVO d = parsearData(tipoDocumento, documento, valor, num);
                    if (d != null) {
                        total++;
                        data.add(d);
                    }
                } else {
                    addError(getBundle("ec.gob.mrl.smp.administracion.cargaMasivaServidor.error.formatoCVS", new Object[]{
                        cargaMasivaAtributoServidorHelper.getTipoDocumentosDisponibles(), "0|1"
                    }), num, CargaMasivaAtributoServidorHelper.TE_FORMATO);
                }
            }
        }
        cargaMasivaAtributoServidorHelper.setTotalServidores(cargaMasivaAtributoServidorHelper.getTotalServidores() + total);
        return data;
    }

    private CargaMasivaServidorVO parsearData(final String tipoDocumento, final String documento, final String valor, int linea) {
        boolean error = false;
        if (!validarTipoDocumento(tipoDocumento)) {
            addError(getBundle("ec.gob.mrl.smp.administracion.cargaMasivaServidor.error.tipoDocumento",
                    new Object[]{cargaMasivaAtributoServidorHelper.getTipoDocumentosDisponibles(), tipoDocumento}), linea,
                    CargaMasivaAtributoServidorHelper.TE_DATOS);
            error = true;
        }
        Integer nValor = null;
        try {
            nValor = Integer.parseInt(valor);
        } catch (Exception e) {
        }
        if (nValor == null) {
            addError(getBundle("ec.gob.mrl.smp.administracion.cargaMasivaServidor.error.valor",
                    new Object[]{valor}), linea, CargaMasivaAtributoServidorHelper.TE_DATOS);
            error = true;
        }

        if (error) {
            return null;
        }

        CargaMasivaServidorVO cmsvo = new CargaMasivaServidorVO();
        cmsvo.setId(id++);
        cmsvo.setTipoDocumento(tipoDocumento);
        cmsvo.setDocumento(documento);
        cmsvo.setValor(nValor != null && nValor == 1);
        cmsvo.setLinea(linea);
        cmsvo.setRechazado(error);
        return cmsvo;
    }

    private boolean validarTipoDocumento(final String tipoDocumento) {
        return TipoDocumentoEnum.obtenerNombre(tipoDocumento) != null;
    }

    private void addError(final String error, int linea, int tipo) {
        cargaMasivaAtributoServidorHelper.getTrazas().add(new String[]{error, linea + "", tipo + "", getTipoErrorMsg(tipo)});
    }

    private String getTipoErrorMsg(Integer tipo) {
        if (tipo.equals(CargaMasivaAtributoServidorHelper.TE_FORMATO)) {
            return getBundle("ec.gob.mrl.smp.administracion.cargaMasivaServidor.tipoError.formato", null);
        }
        if (tipo.equals(CargaMasivaAtributoServidorHelper.TE_DATOS)) {
            return getBundle("ec.gob.mrl.smp.administracion.cargaMasivaServidor.tipoError.datos", null);
        }
        if (tipo.equals(CargaMasivaAtributoServidorHelper.TE_CONSULTA)) {
            return getBundle("ec.gob.mrl.smp.administracion.cargaMasivaServidor.tipoError.consulta", null);
        }
        return "";
    }

    public void cargarArchivo(final FileUploadEvent event) {
        try {
            inicializarResumen();
            inicializarTrazas();
            inicializarData();
            cargaMasivaAtributoServidorHelper.setRegistrosCargados(false);
            cargaMasivaAtributoServidorHelper.setSoloLectura(false);

            cargaMasivaAtributoServidorHelper.setArchivo(null);
            cargaMasivaAtributoServidorHelper.setArchivoCargado(null);
            cargaMasivaAtributoServidorHelper.setNombreArchivo("");

            UploadedFile file = event.getFile();
            cargaMasivaAtributoServidorHelper.setArchivoCargado(file);
            InputStream in = file.getInputstream();
            String nombreArchivo = file.getFileName();
            int indice = nombreArchivo.lastIndexOf(".");
            //String nombreArchivoSinExt = nombreArchivo.substring(0, indice);
            //String extencionSinPunto = nombreArchivo.substring(indice + 1);
            //InputStream stream = new ByteArrayInputStream(file.getContents());
            String extencion = nombreArchivo.substring(indice);
            File tempFile = UtilArchivos.getFileFromBytes(in, nombreArchivo, extencion);
            if (tempFile != null) {
                cargaMasivaAtributoServidorHelper.setArchivo(tempFile);
                cargaMasivaAtributoServidorHelper.setNombreArchivo(nombreArchivo);
                FacesMessage msg = new FacesMessage("El archivo se subió correctamente.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (Exception e) {
            Logger.getLogger(ServidorControlador.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public void setCargaMasivaAtributoServidorHelper(CargaMasivaAtributoServidorHelper cargaMasivaAtributoServidorHelper) {
        this.cargaMasivaAtributoServidorHelper = cargaMasivaAtributoServidorHelper;
    }

    public CargaMasivaAtributoServidorHelper getCargaMasivaAtributoServidorHelper() {
        return cargaMasivaAtributoServidorHelper;
    }

    @Override
    public String iniciarEdicion() {
        return null;
    }

    @Override
    public String iniciarNuevo() {
        return null;
    }

    @Override
    public String borrar() {
        return null;
    }

    @Override
    public String buscar() {
        return null;
    }
}
