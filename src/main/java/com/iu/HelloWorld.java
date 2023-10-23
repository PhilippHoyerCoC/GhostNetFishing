package com.iu;

import jakarta.inject.Named;

@Named
public class HelloWorld {

    private String ghostNetStatus;
    public HelloWorld() {}

    public String getMessage() {
        return "Hello World return!";
    }

    public void writeMessage() {
        System.out.println("Hallo Welt from HelloWorld.java");
    }

    public void setGhostNet(Long id) {
        GhostNetDAO ghostNetDAO = new GhostNetDAO();
        ghostNetDAO.setGhostNet(id);
        System.out.println("Set Ghostnet from HelloWorld Class");
    }

    public void getGhostNetStatus() {
        GhostNetDAO ghostNetDAO = new GhostNetDAO();
        //return ghostNetDAO.getGhostNetStatus(1);
        System.out.println(ghostNetDAO.getGhostNetStatus(1));
    }
}
