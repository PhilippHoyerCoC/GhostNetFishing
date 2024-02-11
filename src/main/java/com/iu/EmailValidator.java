package com.iu;

import java.util.regex.Pattern;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

@FacesValidator("emailValidator")
public class EmailValidator implements Validator<String>{
    
    //RFC 5322 Official Standard Email Regular Expression
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");

    @Override
    public void validate(FacesContext context, UIComponent component, String value) throws ValidatorException {
        if (value == null || !EMAIL_PATTERN.matcher(value).matches()) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid email format", null));
        }
    }
}
