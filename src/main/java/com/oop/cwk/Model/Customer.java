package com.oop.cwk.Model;
import com.oop.cwk.Service.TicketPoolService;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a customer that purchases ticket from the ticketPool
 */
public class Customer implements Runnable{


    //unique id for each customer
    private final int customerId;
    //time interval that a customer should wait before attempting to purchase another ticket
    private final int retrievalInterval;
    //service to handle ticketPool business logic
    private final TicketPoolService ticketPoolService;
    //common ticketPool shared among vendors and customers
    private final TicketPool ticketPool;
    //track the tickets purchased by the customer
    private final List<Integer> boughtTickets= new ArrayList<>();

    public Customer(int customerId, int retrievalInterval, TicketPoolService ticketPoolService, TicketPool ticketPool) {
        this.customerId = customerId;
        this.retrievalInterval = retrievalInterval;
        this.ticketPoolService = ticketPoolService;
        this.ticketPool = ticketPool;
    }

    //getter for customerId
    public int getCustomerId() {
        return customerId;
    }
    //getter for BoughtTickets
    public List<Integer> getBoughtTickets() {
        return boughtTickets;
    }


    /**
     * Adds id of ticket that was purchased by the customer  to the boughtTickets array
     * @param ticketId=id of ticket that the customer purchased
     */
    public void ticketBought(Integer ticketId){
        boughtTickets.add(ticketId);
    }

    /**
     * resets customer objects to initial state
     */
    public void resetCustomer(){
        boughtTickets.clear();
    }


    @Override
    public void run() {
        while (true) {
            ticketPoolService.removeTicket(this);
            try {
                Thread.sleep(retrievalInterval* 1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (ticketPool.getTotalTickets() == 0 && ticketPool.getAvailableTickets().isEmpty()) {
                System.out.println("All tickets bought,Customer " +customerId +" Finished execution.. Terminating ");
                break;
            }
        }
    }
}