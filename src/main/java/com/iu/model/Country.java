package com.iu.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Country {

    private String name;
    private String dialCode;
    private String code;

    public Country() {
    }

    public Country(String name, String dialCode, String code) {
        this.name = name;
        this.dialCode = dialCode;
        this.code = code;
    }

}
