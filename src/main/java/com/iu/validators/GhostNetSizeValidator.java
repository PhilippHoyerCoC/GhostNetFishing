package com.iu.validators;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

@FacesValidator("ghostNetSizeValidator")
public class GhostNetSizeValidator implements Validator<Integer> {

    @Override
    public void validate(FacesContext context, UIComponent component, Integer value) throws ValidatorException {
        if (value <= 0) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ghostnet size must be greater than 0", null));
        }
    }
}
