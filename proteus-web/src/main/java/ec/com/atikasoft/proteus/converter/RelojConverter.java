/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.converter;

import ec.com.atikasoft.proteus.modelo.Reloj;
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
@FacesConverter("relojConverter")
public class RelojConverter implements Converter {
    
    public static final String CLAVE_SESSION = "RelojConverter";

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value.trim().equals("")) {
            return null;
        } else {
            try {
                Long id = Long.valueOf(value);
                Reloj r = buscar(id);
                if (r == null) {
                    return new Reloj(id);
                } else {
                    if (r.getId().equals(0L) ) {
                        return null;
                    }
                } 
                return r;
            } catch (NumberFormatException ex) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en conversión ",
                        "Reloj"));
            }
        }

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            return String.valueOf(((Reloj) value).getId());
        }

    }
    
    /**
     * Intenta encontrar el horario en la session
     * @param id
     * @return 
     */
    private Reloj buscar(final Long id){
        List<Reloj> l = (List<Reloj>) Utilitarios.obtenerAtributoSession(CLAVE_SESSION);
        if(l != null){
            for(Reloj h: l){
                if(h.getId().equals(id)){
                    return h;
                }
            }
        }
        return null;
    }
}
