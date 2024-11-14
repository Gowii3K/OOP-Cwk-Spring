package com.oop.cwk.Model;

public class Ticket {

    private int id;

    public Ticket(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Ticket "+ id ;
    }
}
