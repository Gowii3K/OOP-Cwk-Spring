package com.oop.cwk.Model;

/**
 * Represents a ticket that will be added to the ticketPool
 */
public class Ticket {

    //unique identifier for each ticket
    private int id;

    public Ticket(int id) {
        this.id = id;
    }

    //getter for ticketId
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
