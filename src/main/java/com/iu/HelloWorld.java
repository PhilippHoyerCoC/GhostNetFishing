package com.iu;

import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
public class HelloWorld {

    @Inject
    GhostNetDAO ghostNetDAO;

    public String getMessage() {
        return "Hello World return!";
    }

}
