/**
 * EmailValidador.java
 * EDUCA V 1.0 $Revision 1.0 $
 *
 * Todos los derechos reservados. All rights reserved.
 * This software is the confidential and proprietary information of MarkaSoft
 * ("Confidential Information"). You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the license agreement you entered into
 * with MarkaSoft.
 *
 * MarkaSoft
 * Quito - Ecuador
 * http://www.markasoft.ec/
 *
 * 09/04/2013
 *
 */
package ec.com.atikasoft.proteus.validador;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Validador de E-mail.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
public class EmailValidador implements Validator {

    /**
     * Exp de email.
     */
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\."
            + "[_A-Za-z0-9-]+)*@[_A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*"
            + "(\\.[A-Za-z]{2,})$";

    /**
     * Pattern.
     */
    private Pattern pattern;

    /**
     * Match.
     */
    private Matcher matcher;

    /**
     * Constructor.
     */
    public EmailValidador() {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    @Override
    public void validate(final FacesContext context, final UIComponent component,
            final Object value) throws ValidatorException {

        matcher = pattern.matcher(value.toString());
        if (!matcher.matches()) {

            FacesMessage msg =
                    new FacesMessage("E-mail invalido.",
                    "El formato del e-mail no es correcto.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);

        }

    }
}
