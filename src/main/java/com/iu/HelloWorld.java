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

    public void writeMessage() {
        System.out.println("Hallo Welt from HelloWorld.java");
    }

    public void setGhostNet() {
        ghostNetDAO.setGhostNet();
        System.out.println("Set Ghostnet from HelloWorld Class");
    }

    public void getGhostNetStatus() {
        GhostNetDAO ghostNetDAO = new GhostNetDAO();
        //return ghostNetDAO.getGhostNetStatus(1);
        System.out.println(ghostNetDAO.getGhostNetStatus(1));
    }
}
