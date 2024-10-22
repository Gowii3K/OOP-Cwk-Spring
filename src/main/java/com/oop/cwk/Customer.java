package com.oop.cwk;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

public class Customer implements Runnable{

    Logger logger=Logger.getLogger(Customer.class.getName());

    int customerId;
    int retrievalInterval;//get from config

    int eventId;

    String name;

    TicketPool ticketPool;

    public Customer(int retrievalInterval,TicketPool ticketPool,int customerId,int eventId){
        this.retrievalInterval=retrievalInterval;
        this.ticketPool=ticketPool;
        this.customerId=customerId;
        this.eventId=eventId;
    }
    @Override
    public void run() {

        //create logger


        while (true) {

                try {
                    ticketPool.removeTicket(retrievalInterval,customerId,eventId);
                    //logger.info("Customer " + customerId + " purchased a ticket.");
                    if (ticketPool.getTotalTickets() == 0 && ticketPool.ticketsOnSale==0) {
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