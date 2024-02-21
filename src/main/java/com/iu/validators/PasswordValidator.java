package com.iu.validators;

import java.util.regex.Pattern;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

@FacesValidator("passwordValidator")
public class PasswordValidator implements Validator<String>{

    private static final Pattern DIGIT_PATTERN = Pattern.compile(".*\\d.*");
    private static final Pattern LOWERCASE_PATTERN = Pattern.compile(".*[a-z].*");
    private static final Pattern UPPERCASE_PATTERN = Pattern.compile(".*[A-Z].*");
    private static final Pattern SPECIAL_CHARACTER_PATTERN = Pattern.compile(".*[!@#&()–[{}]:;',?/*~$^+=<>].*");

    @Override
    public void validate(FacesContext context, UIComponent component, String value) throws ValidatorException {
        StringBuilder errorMessage = new StringBuilder("Password must meet the following criteria:\n");

        if (value == null || value.length() < 8 || value.length() > 20) {
            errorMessage.append("- Password must be between 8 and 20 characters long\n");
        }
        if (!DIGIT_PATTERN.matcher(value).matches()) {
            errorMessage.append("- Password must contain at least one digit [0-9]\n");
        }
        if (!LOWERCASE_PATTERN.matcher(value).matches()) {
            errorMessage.append("- Password must contain at least one lowercase Latin character [a-z]\n");
        }
        if (!UPPERCASE_PATTERN.matcher(value).matches()) {
            errorMessage.append("- Password must contain at least one uppercase Latin character [A-Z]\n");
        }
        if (!SPECIAL_CHARACTER_PATTERN.matcher(value).matches()) {
            errorMessage.append("- Password must contain at least one special character like ! @ # & ( )\n");
        }

        if (errorMessage.length() > "Password must meet the following criteria:\n".length()) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage.toString(), null));
        }
    }
}

