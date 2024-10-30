package com.oop.cwk;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;


public class TicketPool {

    static Logger logger=Logger.getLogger(TicketPool.class.getName());

    int test;
     int ticketsOnSale;// make this thread safe
     int totalTickets;
     int maximumTicketCapacity;
     List<Integer> availableTickets = Collections.synchronizedList(new ArrayList<>());
     int currentTicket = 1;

    static {
        try {
            // Set up the logger to log to a file
            FileHandler fileHandler = new FileHandler("TicketPooltest2.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false);
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize logger", e);
        }
    }

    public TicketPool() {
        System.out.println("Initializing TicketPool");
    }

    public  int getTotalTickets() {
        return totalTickets;
    }

    public int getCurrentTicket() {
        return currentTicket;
    }

    public void setCurrentTicket(int currentTicket) {
        this.currentTicket = currentTicket;
    }

    public int getTicketsOnSale() {
        return ticketsOnSale;
    }

    public void setTicketsOnSale(int ticketsOnSale) {
        this.ticketsOnSale = ticketsOnSale;
    }

    public int getTest() {
        return test;
    }

    public void setTest(int test) {
        this.test = test;
    }

    public List<Integer> getAvailableTickets() {
        return availableTickets;
    }

    public void setAvailableTickets(List<Integer> availableTickets) {
        this.availableTickets = availableTickets;
    }

    public  void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getMaximumTicketCapacity() {
        return this.maximumTicketCapacity;
    }

    public  void setMaximumTicketCapacity(int maximumTicketCapacity) {
        this.maximumTicketCapacity = maximumTicketCapacity;
    }

    public synchronized void addTickets(int TicketsPerRelease, int releaseInterval,int vendorId) throws InterruptedException {
        ticketsOnSale = availableTickets.size();

        //checks max capacity and notifies waiting threads,so they can terminate
        if(totalTickets==0){

            System.out.println("All tickets added");
            notifyAll();
            return;

        }

        //checks if it should wait for customers to buy
        else if (ticketsOnSale==maximumTicketCapacity) {
            System.out.println("wait for cust to add");
            wait();

        }
        System.out.println("check");
        // if neither of the above condition is satisfied proceed with adding tickets

        int remainingCapacity=maximumTicketCapacity-ticketsOnSale;
        int ticketBatch = 0;
        // find the least amount between tickets per release, remaining ticket slots, and total tickets
        if(TicketsPerRelease<=remainingCapacity && TicketsPerRelease<=totalTickets){
            ticketBatch=TicketsPerRelease;
            System.out.println("tpr");
        } else if (totalTickets>remainingCapacity) {
            ticketBatch=remainingCapacity;
            System.out.println("rem cap");
        }
        else{
            ticketBatch=totalTickets;
            System.out.println("tt");
        }

        System.out.println("ticket batch is" + ticketBatch);
        for(int i=0;i<ticketBatch;i++){

            System.out.println(("Ticket No "+currentTicket+"Added by "+vendorId));
            logger.info("Ticket No "+currentTicket+"Added by "+vendorId);
            availableTickets.add(currentTicket);
            currentTicket++;
            totalTickets--;
            ticketsOnSale++;
            System.out.println("tickets are for event "+vendorId+availableTickets);


        }
        Thread.sleep(releaseInterval);
        //notify waiting threads
        notifyAll();




    }


    public synchronized Integer removeTicket(int retrievalInterval,int customerId,int eventId) throws InterruptedException {
        //total tickets out and no more on sale
        if (ticketsOnSale == 0 && totalTickets == 0) {
            System.out.println("All tickets sold out ");
            notifyAll();
            return null;
        }
        //nothing on sale but it will be put up eventually
        else if (totalTickets > 0 && ticketsOnSale == 0) {
            System.out.println("wait for vendors to add pls");
            wait();
            return null;

        } else {
            int purchasedTicket = availableTickets.removeFirst();

            System.out.println("Purchase ticket is " + purchasedTicket);
            System.out.println("Ticket No "+purchasedTicket+"of event  "+eventId+"bought by "+customerId);
            logger.info("Ticket No "+purchasedTicket+"of event  "+eventId+"bought by "+customerId);
            ticketsOnSale--;
            Thread.sleep(retrievalInterval);
            notifyAll();
            return purchasedTicket;

        }



    }
}
