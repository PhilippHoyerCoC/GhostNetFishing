package com.iu;

import java.util.Locale;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;

@FacesConverter("ghostNetCoordinatesConverter")
public class GhostNetCoordinatesConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            double coordinate = Double.valueOf(value);
            String componentId = component.getId();
            if ("latitude".equals(componentId) && (coordinate < -90 || coordinate > 90)) {
                throw new ConverterException(
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Latitude must be between -90 and 90", null));
            } else if ("longitude".equals(componentId) && (coordinate < -180 || coordinate > 180)) {
                throw new ConverterException(
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Longitude must be between -180 and 180", null));
            }
            return coordinate;
        } catch (NumberFormatException e) {
            throw new ConverterException(
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Coordinates must only conatin numbers", null), e);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return String.format(Locale.US, "%.6f", value);
    }
}
