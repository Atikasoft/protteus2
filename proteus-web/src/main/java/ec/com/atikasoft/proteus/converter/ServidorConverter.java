/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.converter;

import ec.com.atikasoft.proteus.modelo.Servidor;
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
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@FacesConverter("servidorConverter")
public class ServidorConverter implements Converter {
    
    public static final String CLAVE_SESSION = "ServidorConverter";

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value.trim().equals("")) {
            return null;
        } else {
            try {
                Long id = Long.valueOf(value);
                Servidor s = buscar(id);
                if (s == null) {
                    return new Servidor(id);
                } else {
                    if (s.getId().equals(0L) ) {
                        return null;
                    }
                } 
                return s;
//                return s == null ? new Servidor(id) : s;
            } catch (NumberFormatException exception) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en conversión ",
                        "Servidor"));
            }
        }

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            return String.valueOf(((Servidor) value).getId());
        }

    }
    
    /**
     * Intenta encontrar el Servidor en la session
     * @param id
     * @return 
     */
    private Servidor buscar(final Long id){
        List<Servidor> l = (List<Servidor>) Utilitarios.obtenerAtributoSession(CLAVE_SESSION);
        if(l != null){
            for(Servidor s: l){
                if(s.getId().equals(id)){
                    return s;
                }
            }
        }
        return null;
    }
}
