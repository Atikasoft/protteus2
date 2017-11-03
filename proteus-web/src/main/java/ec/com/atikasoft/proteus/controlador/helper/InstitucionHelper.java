/*
 *  InstitucionHelper.java
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
 *  21/11/2012
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.InstitucionEjercicioFiscal;
import ec.com.atikasoft.proteus.vo.InstitucionVO;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@ManagedBean(name = "institucionHelper")
@SessionScoped
public class InstitucionHelper extends CatalogoHelper {

    /**
     * variable de InstitucionVO
     */
    private InstitucionVO institucionVO;

    /**
     * variable de InstitucionEjercicioFiscal
     */
    private InstitucionEjercicioFiscal institucion;

    /**
     * Variable para modificar/eliminar una regla.
     */
    private InstitucionEjercicioFiscal institucionEditDelete;

    /**
     * Wraper de InstitucionEjercicioFiscal del siith core
     */
    private List<InstitucionVO> listaInstitucionVO;

    /**
     * Variable para listar las alertas.
     */
    private List<InstitucionEjercicioFiscal> listaInstitucion;

    private String nombre;
    /**
     * Variable para buscar las Instituciones del Proteus.
     */
    private String campoBusqueda;

    public InstitucionHelper() {
        super();
        init();
    }

    public final void init() {

        setInstitucionVO(new InstitucionVO());
        setListaInstitucionVO(new ArrayList<InstitucionVO>());
        setListaInstitucion(new ArrayList<InstitucionEjercicioFiscal>());
        institucion = new InstitucionEjercicioFiscal();
        nombre = "";
        campoBusqueda="";

    }

    /**
     * @return the institucionVO
     */
    public InstitucionVO getInstitucionVO() {
        return institucionVO;
    }

    /**
     * @param institucionVO the institucionVO to set
     */
    public void setInstitucionVO(InstitucionVO institucionVO) {
        this.institucionVO = institucionVO;
    }

    /**
     * @return the listaInstitucionVO
     */
    public List<InstitucionVO> getListaInstitucionVO() {
        return listaInstitucionVO;
    }

    /**
     * @param listaInstitucionVO the listaInstitucionVO to set
     */
    public void setListaInstitucionVO(List<InstitucionVO> listaInstitucionVO) {
        this.listaInstitucionVO = listaInstitucionVO;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the listaInstitucion
     */
    public List<InstitucionEjercicioFiscal> getListaInstitucion() {
        return listaInstitucion;
    }

    /**
     * @param listaInstitucion the listaInstitucion to set
     */
    public void setListaInstitucion(List<InstitucionEjercicioFiscal> listaInstitucion) {
        this.listaInstitucion = listaInstitucion;
    }

    /**
     * @return the institucion
     */
    public InstitucionEjercicioFiscal getInstitucion() {
        return institucion;
    }

    /**
     * @param institucion the institucion to set
     */
    public void setInstitucion(InstitucionEjercicioFiscal institucion) {
        this.institucion = institucion;
    }

    /**
     * @return the institucionEditDelete
     */
    public InstitucionEjercicioFiscal getInstitucionEditDelete() {
        return institucionEditDelete;
    }

    /**
     * @param institucionEditDelete the institucionEditDelete to set
     */
    public void setInstitucionEditDelete(InstitucionEjercicioFiscal institucionEditDelete) {
        this.institucionEditDelete = institucionEditDelete;
    }

    /**
     * @return the campoBusqueda
     */
    public String getCampoBusqueda() {
        return campoBusqueda;
    }

    /**
     * @param campoBusqueda the campoBusqueda to set
     */
    public void setCampoBusqueda(String campoBusqueda) {
        this.campoBusqueda = campoBusqueda;
    }
}
