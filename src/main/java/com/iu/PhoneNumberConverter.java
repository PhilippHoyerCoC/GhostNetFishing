package com.iu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;

@FacesConverter("phoneNumberConverter")
public class PhoneNumberConverter implements Converter<Object> {

    private static final Logger logger = LogManager.getLogger(PhoneNumberConverter.class);

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        // Convert the string value to a PhoneNumber object
        try {
            String phoneNumber = value;
            String componentId = component.getId();
            if (componentId.equals("phone") && !phoneNumber.matches("[0-9 ]+")) {
                logger.error("Phone number must contain only numbers but was: {}", phoneNumber);
                throw new ConverterException(
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Phone number must contain only numbers but was: " + phoneNumber, null)
                );
            }
            phoneNumber = phoneNumber.replace(" ", "");
            return phoneNumber;
        } catch (Exception e) {
            throw new ConverterException(
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid phone number", null), e
            );
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        // Convert the PhoneNumber object to a string
        return value.toString();
    }
}
