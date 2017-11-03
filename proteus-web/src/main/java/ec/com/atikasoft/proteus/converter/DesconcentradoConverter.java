/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.converter;

import ec.com.atikasoft.proteus.modelo.Desconcentrado;
import ec.com.atikasoft.proteus.utilitarios.Utilitarios;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 * 
 * @author Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 */
@FacesConverter("desconcentradoConverter")
public class DesconcentradoConverter implements Converter {
    
    public static final String CLAVE_SESSION = "DesconcentradoConverter";

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value.trim().equals("")) {
            return null;
        } else {
            try {
                Long id = Long.valueOf(value);
                Desconcentrado d = buscar(id);
                if (d == null) {
                    return new Desconcentrado(id);
                } else {
                    if (d.getId().equals(0L) ) {
                        return null;
                    }
                } 
                return d;
            } catch (NumberFormatException ex) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en conversión ",
                        "Unidad Desconcetrada"));
            }
        }

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            return String.valueOf(((Desconcentrado) value).getId());
        }

    }
    
    /**
     * Intenta encontrar la Unidad Desconcentrada en la session
     * @param id
     * @return 
     */
    private Desconcentrado buscar(final Long id){
        List<Desconcentrado> l = (List<Desconcentrado>) Utilitarios.obtenerAtributoSession(CLAVE_SESSION);
        if(l != null){
            for(Desconcentrado d: l){
                if(d.getId().equals(id)){
                    return d;
                }
            }
        }
        return null;
    }
}
