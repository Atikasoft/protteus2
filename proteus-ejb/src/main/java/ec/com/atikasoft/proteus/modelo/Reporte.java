/*
 *  Reporte.java
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
 *  Dec 27, 2011
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.io.Serializable;
import javax.persistence.*;

/**
 * Define los reportes birt.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
@Entity
@Table(name = "reportes", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = Reporte.BUSCAR_POR_REPORTE, query = "SELECT o FROM Reporte o WHERE o.rptDesing=?1"),
    @NamedQuery(name = Reporte.BUSCAR_VIGENTES,
    query = "SELECT o FROM Reporte o WHERE o.vigente=true ORDER BY o.nombre")
})
public class Reporte extends EntidadBasica implements Serializable {

    /**
     * Version de la clase.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Busca por reporte.
     */
    public static final String BUSCAR_POR_REPORTE = "Reporte.buscarPorReporte";

    /**
     * Buscar vigentes.
     */
    public static final String BUSCAR_VIGENTES = "Reporte.buscarVigentes";

    /**
     * Identificador unico.
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Descripcion.
     */
    @Column(name = "descripcion", nullable = false, length = 200)
    private String descripcion;

    /**
     * Nombre.
     */
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    /**
     * Servlet.
     */
    @Column(name = "servlet", nullable = false, length = 100)
    private String servlet;

    /**
     * Formato. PDF = P HTML = H NINGUNO = N
     */
    @Column(name = "formato", nullable = false, length = 1)
    private String formato;

    /**
     * Nombre del reporte.
     */
    @Column(name = "rptdesing", length = 100)
    private String rptDesing;

    /**
     * Extension del reporte.
     */
    @Column(name = "extension", length = 125)
    private String extension;

    /**
     * Constructor sin argumentos.
     */
    public Reporte() {
        super();
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return the rptDesing
     */
    public String getRptDesing() {
        return rptDesing;
    }

    /**
     * @return the servlet
     */
    public String getServlet() {
        return servlet;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(final String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @param id the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    /**
     * @param rptDesing the rptDesing to set
     */
    public void setRptDesing(final String rptDesing) {
        this.rptDesing = rptDesing;
    }

    /**
     * @param servlet the servlet to set
     */
    public void setServlet(final String servlet) {
        this.servlet = servlet;
    }

    /**
     * @return the formato
     */
    public String getFormato() {
        return formato;
    }

    /**
     * @param formato the formato to set
     */
    public void setFormato(final String formato) {
        this.formato = formato;
    }

    /**
     * @return the extension
     */
    public String getExtension() {
        return extension;
    }

    /**
     * @param extension the extension to set
     */
    public void setExtension(final String extension) {
        this.extension = extension;
    }
}
