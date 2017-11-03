/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.enums.TipoIdentificacionEnum;
import ec.com.atikasoft.proteus.modelo.PersonalOtrasInstituciones;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.TreeNode;

/**
 *
 */
@ManagedBean(name = "personalOtrasInstitucionesHelper")
@SessionScoped
@Getter
@Setter
public class PersonalOtrasInstitucionesHelper extends CatalogoHelper {

    /**
     * Entidad Principal.
     */
    private PersonalOtrasInstituciones personalOtrasInstituciones;
    /**
     * Entidad de Transacción.
     */
    private PersonalOtrasInstituciones personalOtrasInstitucionesEditDelete;

    /**
     * Lista de PersonalOtrasInstituciones.
     */
    private List<PersonalOtrasInstituciones> listaPersonalOtrasInstituciones;

    /**
     * Lista de Unidades Organizacionales para selección
     */
    private List<UnidadOrganizacional> unidadesOrganizacionales;

    private TipoIdentificacionEnum[] tiposIdentificacion;

    private TreeNode rootUnidadOrganizacional;

    private TreeNode unidadSeleccionada;

    private Boolean busquedaActivos;

    /**
     * Archivo acción de personal registro
     */
    private StreamedContent archivoPdfAccionRegistro;

    /**
     * Archivo acción de personal terminacion
     */
    private StreamedContent archivoPdfAccionTerminacion;

    /**
     * Indicador de que el numero de identificacion existe en el sistema de personas.
     */
    private Boolean existeSistemaPersonas;

    /**
     * Constructor por defecto.
     */
    public PersonalOtrasInstitucionesHelper() {
        super();
        iniciador();
    }

    /**
     * Este método Inicializa las variables del Helper.
     */
    public final void iniciador() {
        personalOtrasInstituciones = new PersonalOtrasInstituciones();
        personalOtrasInstitucionesEditDelete = new PersonalOtrasInstituciones();
        listaPersonalOtrasInstituciones = new ArrayList<>();
        unidadesOrganizacionales = new ArrayList<>();
        tiposIdentificacion = TipoIdentificacionEnum.values();
        rootUnidadOrganizacional = new DefaultTreeNode();
        unidadSeleccionada = new DefaultTreeNode();
        setEsNuevo(Boolean.TRUE);
        existeSistemaPersonas = Boolean.FALSE;
    }

}
