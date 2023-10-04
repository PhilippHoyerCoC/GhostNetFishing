package com.iu;

import jakarta.inject.Named;

@Named
public class HelloWorld {

    public HelloWorld() {
        System.out.println("HelloWorld started!");
    }

    public String getMessage() {
        return "Hello World return!";
    }
}
