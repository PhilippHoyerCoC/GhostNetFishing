package com.iu.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Coordinates {
    private double latitude;
    private double longitude;

    @Override
    public String toString() {
        return String.format("Coordinates{latitude=%s, longitude=%s}", latitude, longitude);
    }
}
