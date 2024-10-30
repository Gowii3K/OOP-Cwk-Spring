package com.oop.cwk;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Customer implements Runnable{

    Logger logger=Logger.getLogger(Customer.class.getName());

    int customerId;

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public List<Integer> getBoughtTickets() {
        return boughtTickets;
    }

    public void setBoughtTickets(List<Integer> boughtTickets) {
        this.boughtTickets = boughtTickets;
    }



    List<Integer> boughtTickets= new ArrayList<>();
    int retrievalInterval;//get from config

    int eventId;

    String name;


    TicketPool ticketPool;

    public Customer(int retrievalInterval,TicketPool ticketPool,int customerId){
        this.retrievalInterval=retrievalInterval;
        this.ticketPool=ticketPool;
        this.customerId=customerId;
    }
    @Override
    public void run() {

        //create logger


        while (true) {

                try {

                    Integer ticket=ticketPool.removeTicket(retrievalInterval,customerId);
                    if (ticket!=null){
                        boughtTickets.add(ticket);
                    }
                    //logger.info("Customer " + customerId + " purchased a ticket.");
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
//inject ticketppol