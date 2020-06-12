package org.tutorial.model;

public class Message {
    private Long id;
    private String message;

    public Message() {
    }

    public Message(String mesaage) {
        this.message = mesaage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String mesaage) {
        this.message = mesaage;
    }
}

