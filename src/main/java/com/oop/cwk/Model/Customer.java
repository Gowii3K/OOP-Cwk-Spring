package com.oop.cwk.Model;

import java.util.ArrayList;
import java.util.List;

public class Customer implements Runnable{


    private int customerId;
    private int retrievalInterval;//get from config
    private TicketPool ticketPool;
    private List<Integer> boughtTickets= new ArrayList<>();


    public int getCustomerId() {return customerId;}
    public void setCustomerId(int customerId) {this.customerId = customerId;}
    public List<Integer> getBoughtTickets() {return boughtTickets;}
    public void setBoughtTickets(List<Integer> boughtTickets) {this.boughtTickets = boughtTickets;}

    public Customer(int retrievalInterval,TicketPool ticketPool,int customerId){
        this.retrievalInterval=retrievalInterval;
        this.ticketPool=ticketPool;
        this.customerId=customerId;
    }

    public void ticketBought(Integer ticketId){
        boughtTickets.add(ticketId);
    }

    @Override
    public void run() {
        while (true) {
                try {

                    ticketPool.removeTicket(retrievalInterval,customerId,this);
                    if (ticketPool.getTotalTickets() == 0 && ticketPool.availableTickets.isEmpty()) {
                        System.out.println("All tickets added, customer " + customerId + " is done.");
                        break;
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Restore interrupted status
                    break;

                }
        }
        System.out.println("got out customer " +customerId);
    }
}