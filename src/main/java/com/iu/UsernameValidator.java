package com.iu;

import jakarta.enterprise.inject.spi.CDI;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

@FacesValidator("usernameValidator")
public class UsernameValidator implements Validator<String> {
    
    private UserDAO userDAO;

    public UsernameValidator() {
        this.userDAO = CDI.current().select(UserDAO.class).get();
    }
    
    @Override
    public void validate(FacesContext context, UIComponent component, String username) throws ValidatorException {
        if (userDAO.getUserByUsername(username) != null) {
            throw new ValidatorException(new FacesMessage("Username already exists"));
        }
    }
}
