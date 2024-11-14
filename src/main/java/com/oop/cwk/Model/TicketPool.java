package com.oop.cwk.Model;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

@Component
public class TicketPool {

    static Logger logger=Logger.getLogger(TicketPool.class.getName());

    //Fields
    private int totalTickets;
    private int maximumTicketCapacity;
    private final ConcurrentLinkedQueue<Ticket> availableTickets = new ConcurrentLinkedQueue<>();
    private int currentTicket = 1;

    //Getters and Setters
    public ConcurrentLinkedQueue<Ticket> getAvailableTickets() {
        return availableTickets;
    }
    public int getMaximumTicketCapacity() {
        return maximumTicketCapacity;
    }
    public void setMaximumTicketCapacity(int maximumTicketCapacity) {
        this.maximumTicketCapacity = maximumTicketCapacity;
    }
    public  int getTotalTickets() {
        return totalTickets;
    }
    public  void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getCurrentTicket() {
        return currentTicket;
    }

    public void setCurrentTicket(int currentTicket) {
        this.currentTicket = currentTicket;
    }

    public void incrementCurrentTicket() {
        currentTicket++;
    }
    public void decrementTotalTickets() {
        totalTickets--;
    }
    public TicketPool() {
        System.out.println("Initializing TicketPool");
    }
}
