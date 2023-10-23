package com.iu;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Named
@ApplicationScoped
public class Anschrift {
    private String name;
    private String strasse;
    private Integer hausnummer;
    private String plz;
    private String ort;

    public Anschrift() {}

    public Anschrift(String name, String strasse, Integer hausnummer, String plz, String ort) {
        this.name = name;
        this.strasse = strasse;
        this.hausnummer = hausnummer;
        this.plz = plz;
        this.ort = ort;
    }
}
