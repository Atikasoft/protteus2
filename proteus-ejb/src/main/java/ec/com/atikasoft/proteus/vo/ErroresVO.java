
package ec.com.atikasoft.proteus.vo;

import java.io.Serializable;

/**
 *
 * @author Mauro Vincent <mauro.vincent@atikasoft.com.ec>
 */

public class ErroresVO implements Serializable {
    /**
     * Variable seccion error.
     */
    private String seccion;
    /**
     * variable campo error.
     */
    private String campo;
    /**
     * variable menaje error.
     */
    private String mensajeError;
    /**
     * variable de referencia apra navegación.
     */
    private String referencia;
    /**
     * constructor.
     */
    public ErroresVO() {
        super();
    }

    /**
     * @return the seccion
     */
    public String getSeccion() {
        return seccion;
    }

    /**
     * @param seccion the seccion to set
     */
    public void setSeccion(final String seccion) {
        this.seccion = seccion;
    }

    /**
     * @return the campo
     */
    public String getCampo() {
        return campo;
    }

    /**
     * @param campo the campo to set
     */
    public void setCampo(final String campo) {
        this.campo = campo;
    }

    /**
     * @return the mensajeError
     */
    public String getMensajeError() {
        return mensajeError;
    }

    /**
     * @param mensajeError the mensajeError to set
     */
    public void setMensajeError(final String mensajeError) {
        this.mensajeError = mensajeError;
    }

    /**
     * @return the referencia
     */
    public String getReferencia() {
        return referencia;
    }

    /**
     * @param referencia the referencia to set
     */
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }
}
