package com.iu.converters;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;

@FacesConverter("ghostNetSizeConverter")
public class GhostNetSizeConverter implements Converter<Object> {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            throw new ConverterException(
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ghostnet size must only conatin numbers", null), e);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value.toString();
    }
}
