package com.oop.cwk.Model;
import java.util.ArrayList;
import java.util.List;

public class Customer implements Runnable{


    private final int customerId;
    private final int retrievalInterval;//get from config
    private final TicketPool ticketPool;
    private final List<Integer> boughtTickets= new ArrayList<>();

    public int getCustomerId() {
        return customerId;
    }
    public List<Integer> getBoughtTickets() {
        return boughtTickets;
    }

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
                    if (ticketPool.getTotalTickets() == 0 && ticketPool.getAvailableTickets().isEmpty()) {
                        System.out.println("All tickets bought,Customer " +customerId +" Finished execution.. Terminating ");
                        break;
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Restore interrupted status
                    break;

                }
        }
    }
}