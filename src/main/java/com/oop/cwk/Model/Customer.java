package com.oop.cwk.Model;
import com.oop.cwk.Service.TicketPoolService;

import java.util.ArrayList;
import java.util.List;

public class Customer implements Runnable{


    private final int customerId;
    private final int retrievalInterval;//get from config
    private final TicketPoolService ticketPoolService;
    private final TicketPool ticketPool;
    private final List<Integer> boughtTickets= new ArrayList<>();

    public Customer(int customerId, int retrievalInterval, TicketPoolService ticketPoolService, TicketPool ticketPool) {
        this.customerId = customerId;
        this.retrievalInterval = retrievalInterval;
        this.ticketPoolService = ticketPoolService;
        this.ticketPool = ticketPool;
    }

    public int getCustomerId() {
        return customerId;
    }
    public List<Integer> getBoughtTickets() {
        return boughtTickets;
    }


    public void ticketBought(Integer ticketId){
        boughtTickets.add(ticketId);
    }
    public void resetCustomer(){
        boughtTickets.clear();
    }


    @Override
    public void run() {
        while (true) {
            ticketPoolService.removeTicket(customerId,this);
            try {
                Thread.sleep(retrievalInterval);
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