/*
 *  AnticipoAprobacionHelper.java
 *  PROTEUS V 2.0 $Revision 1.0 $
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
 *  05/12/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.vo.CargaMasivaServidorVO;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 * Carga Masiva de Atributos del Servidor
 *
 * @author Maikel Pérez Martínez <maikel.perez@markasoft.ec>
 */
@ManagedBean(name = "cargaMasivaAtributoServidorHelper")
@SessionScoped
public class CargaMasivaAtributoServidorHelper extends CatalogoHelper {

    public static final Integer FONDO_RESERVA = 1;
    public static final Integer TOMA_TRANSPORTE = 2;
    public static final Integer DECIMO_TERCERO = 3;
    public static final Integer DECIMO_CUARTO = 4;

    public static final String ETIQUETA_FONDO_RESERVA = "Acumula Fondo Reserva IESS (1=Si,0=No)";
    public static final String ETIQUETA_TOMA_TRANSPORTE = "Toma Transporte (1=Si,0=No)";
    public static final String ETIQUETA_DECIMO_TERCERO = "Acumula Décimo Tercero (1=Si,0=No)";
    public static final String ETIQUETA_DECIMO_CUARTO = "Acumula Décimo Cuarto (1=Si,0=No)";

    public static final Integer TE_FORMATO = 1;
    public static final Integer TE_DATOS = 2;
    public static final Integer TE_CONSULTA = 3;

    /**
     * Nombre del tipo de carga
     */
    private String nombreTipoCarga;
    /**
     * Uno de los valores de FONDO_RESERVA o TOMA_TRANSPORTE
     */
    private Integer tipoCarga;

    /**
     * valores de FONDO_RESERVA o TOMA_TRANSPORTE
     */
    private List<SelectItem> tiposCarga;

    /**
     * Para manejar descarga del archivo a leer
     */
    private StreamedContent content;

    /**
     * Total de registros de servidores atendidos
     */
    private Integer totalServidores;

    /**
     * Total de registros de servidores que fallaron
     */
    private Integer fallos;

    /**
     * Total de registros satisfactorios
     */
    private Integer realizados;

    /**
     * Detalles de alertas pos 0: Detalle del error pos 1: Linea del error pos
     * 2: Tipo del error (TE_FORMATO,TE_DATOS,TE_CONSUTLA) pos 3: Mensaje del
     * tipo de error
     */
    private List<String[]> trazas;

    /**
     * Manejo de upload
     */
    private UploadedFile archivoCargado;

    /**
     * Fichero cargado
     */
    private File archivo;

    /**
     * Nombre del fichero cargado
     */
    private String nombreArchivo;

    /**
     * Almacenar la informacion cargada del archivo
     */
    private List<CargaMasivaServidorVO> datos;

    /**
     * String en el formato TD1|TD2
     */
    private String tipoDocumentosDisponibles;

    /**
     * Registros cargados por vista previa o por carga
     */
    private boolean registrosCargados;

    /**
     * Para definir si se hara una carga o un analisis sin escritura
     */
    private boolean soloLectura;

    /**
     * Filtro para la tabla de errores
     */
    private List<SelectItem> tiposError;

    /**
     * Constructor por defecto.
     */
    public CargaMasivaAtributoServidorHelper() {
        super();
        iniciador();
    }

    /**
     * Método para iniciar las variables del AnticipoHelper.
     */
    public final void iniciador() {
        trazas = new ArrayList<String[]>();
        tiposCarga = new ArrayList<SelectItem>();
        datos = new ArrayList<CargaMasivaServidorVO>();
        tiposError = new ArrayList<SelectItem>();
    }

    public Integer getTipoCarga() {
        return tipoCarga;
    }

    public void setTipoCarga(Integer tipoCarga) {
        this.tipoCarga = tipoCarga;
    }

    public StreamedContent getContent() {
        return content;
    }

    public void setContent(StreamedContent content) {
        this.content = content;
    }

    public Integer getTotalServidores() {
        return totalServidores;
    }

    public void setTotalServidores(Integer totalServidores) {
        this.totalServidores = totalServidores;
    }

    public Integer getFallos() {
        return fallos;
    }

    public void setFallos(Integer fallos) {
        this.fallos = fallos;
    }

    public Integer getRealizados() {
        return realizados;
    }

    public void setRealizados(Integer realizados) {
        this.realizados = realizados;
    }

    public List<String[]> getTrazas() {
        return trazas;
    }

    public void setTrazas(List<String[]> trazas) {
        this.trazas = trazas;
    }

    public UploadedFile getArchivoCargado() {
        return archivoCargado;
    }

    public void setArchivoCargado(UploadedFile archivoCargado) {
        this.archivoCargado = archivoCargado;
    }

    public File getArchivo() {
        return archivo;
    }

    public void setArchivo(File archivo) {
        this.archivo = archivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public List<SelectItem> getTiposCarga() {
        return tiposCarga;
    }

    public void setTiposCarga(List<SelectItem> tiposCarga) {
        this.tiposCarga = tiposCarga;
    }

    public List<CargaMasivaServidorVO> getDatos() {
        return datos;
    }

    public void setDatos(List<CargaMasivaServidorVO> datos) {
        this.datos = datos;
    }

    public String getTipoDocumentosDisponibles() {
        return tipoDocumentosDisponibles;
    }

    public void setTipoDocumentosDisponibles(String tipoDocumentosDisponibles) {
        this.tipoDocumentosDisponibles = tipoDocumentosDisponibles;
    }

    public boolean getRegistrosCargados() {
        return registrosCargados;
    }

    public void setRegistrosCargados(boolean registrosCargados) {
        this.registrosCargados = registrosCargados;
    }

    public boolean getSoloLectura() {
        return soloLectura;
    }

    public void setSoloLectura(boolean soloLectura) {
        this.soloLectura = soloLectura;
    }

    public String getNombreTipoCarga() {
        return nombreTipoCarga;
    }

    public void setNombreTipoCarga(String nombreTipoCarga) {
        this.nombreTipoCarga = nombreTipoCarga;
    }

    public List<SelectItem> getTiposError() {
        return tiposError;
    }

    public void setTiposError(List<SelectItem> tiposError) {
        this.tiposError = tiposError;
    }
}
