package com.iu.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class PhoneNumber {
    private String countryCode;
    private String phone;

    @Override
    public String toString() {
        return String.format("PhoneNumber{countryCode=%s, phone=%s}", countryCode, phone);
    }
}
