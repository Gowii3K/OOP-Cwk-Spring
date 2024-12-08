package com.oop.cwk.Model;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

/**
 * Represents the pool in which tickets are put up for sale.
 */
@Component
public class TicketPool {


    //total number of tickets available for the event
    private int totalTickets;
    //maximum number of tickets that can be on sale at a given time
    private int maximumTicketCapacity;
    //data structure to hold the tickets
    private final ConcurrentLinkedQueue<Ticket> availableTickets = new ConcurrentLinkedQueue<>();
    //track number of sold tickets
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


    /**
     * increase current tickets by one
     */
    public void incrementCurrentTicket() {
        currentTicket++;
    }

    /**
     * decrease total tickets by one
     */
    public void decrementTotalTickets() {
        totalTickets--;
    }
    public TicketPool() {
        System.out.println("Initializing TicketPool");
    }
}
