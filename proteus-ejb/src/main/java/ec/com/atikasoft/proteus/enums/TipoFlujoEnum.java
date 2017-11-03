/*
 *  EstadosTramiteEnum.java
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
 *  05/11/2012
 *
 */
package ec.com.atikasoft.proteus.enums;

/**
 * Definicion de los tipos de flujos.
 *
 * @author Patricio Recalde <patricio.recalde@atikasoft.com.ec>
 */
public enum TipoFlujoEnum {

    /**
     * Flujo regular con solicitud.
     */
    FLUJO_CORTO("FCO", "FLUJO CORTO", false, ProcesoEnum.MOVIMIENTO_PERSONAL_CORTO.getCodigo()),
    /**
     * Flujo corto sin solicitud.
     */
    FLUJO_DIRECTO("FDI", "FLUJO DIRECTO", false, ProcesoEnum.MOVIMIENTO_PERSONAL_DIRECTO.getCodigo());

    /**
     * Columna del campo ordenamiento.
     */
    private final String codigo;

    /**
     * Descripcion del campo ordenamiento.
     */
    private final String descripcion;

    /**
     * Indica si correponde con solicitud o no.
     */
    private final Boolean conSolicitud;

    /**
     * Codigo del proceso en gestor.
     */
    private final String codigoProceso;

    /**
     * Constructor requerido.
     *
     * @param codigo String
     * @param descripcion String
     */
    private TipoFlujoEnum(final String codigo, final String descripcion, final Boolean conSolicitud, final String codigoProceso) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.conSolicitud = conSolicitud;
        this.codigoProceso = codigoProceso;
    }

    /**
     * 
     * @param codigo
     * @return 
     */
    public static TipoFlujoEnum buscar(String codigo) {
        for (TipoFlujoEnum en : TipoFlujoEnum.values()) {
            if (en.getCodigo().equals(codigo)) {
                return en;
            }
        }
        return null;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @return the conSolicitud
     */
    public Boolean getConSolicitud() {
        return conSolicitud;
    }

    /**
     * @return the codigoProceso
     */
    public String getCodigoProceso() {
        return codigoProceso;
    }
}
