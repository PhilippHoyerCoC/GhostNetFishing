package com.iu;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter(value="ghostNetStatusConverter")
public class GhostNetStatusConverter implements Converter {
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return GhostNetStatusEnum.valueOf(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";
        }
        return ((GhostNetStatusEnum) value).name();
    }

//    @Override
//    public Object getAsObject(FacesContext context, UIComponent component, String value) {
//        return GhostNetStatusEnum.valueOf(value);
//    }
//
//    @Override
//    public String getAsString(FacesContext context, UIComponent component, Object value) {
//        return ((GhostNetStatusEnum) value).name();
//    }
}
