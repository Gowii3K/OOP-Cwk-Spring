package com.oop.cwk.Model;

import java.util.ArrayList;
import java.util.List;

public  class Vendor implements Runnable{

    private final int vendorId;//unique id for each vendor
    private final int releaseInterval;//get from config
    private final TicketPool ticketPool;
    private final List<Integer> soldTickets=new ArrayList<>();


    public int getVendorId() {
        return vendorId;
    }
    public List<Integer> getSoldTickets() {return soldTickets;}

    public Vendor( int releaseInterval, TicketPool ticketPool, int vendorId){

        this.releaseInterval=releaseInterval;
        this.ticketPool=ticketPool;
        this.vendorId=vendorId;

    }

    public void TicketSold(Integer ticketId){
        soldTickets.add(ticketId);
    }

    @Override
    public void run() {
        while (true){

                try {
                    ticketPool.addTickets(releaseInterval,this.vendorId,this);
                    if (ticketPool.getTotalTickets() == 0) {
                        System.out.println("All tickets added, Vendor " + vendorId + "Finished execution.. Terminating ");
                        break;
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Restore interrupted status
                    break;
                }
            }
    }


}