package com.oop.cwk.Model;

import com.oop.cwk.Service.TicketPoolService;

import java.util.ArrayList;
import java.util.List;

public  class Vendor implements Runnable{

    private final int vendorId;//unique id for each vendor
    private final int releaseInterval;//get from config
    private final TicketPoolService ticketPoolService;
    private final TicketPool ticketPool;
    private final List<Integer> soldTickets=new ArrayList<>();


    public int getVendorId() {
        return vendorId;
    }
    public List<Integer> getSoldTickets() {return soldTickets;}

    public Vendor(int releaseInterval, TicketPool ticketPool, int vendorId, TicketPoolService ticketPoolService){

        this.releaseInterval=releaseInterval;
        this.ticketPool=ticketPool;
        this.vendorId=vendorId;

        this.ticketPoolService = ticketPoolService;
    }

    public void TicketSold(Integer ticketId){
        soldTickets.add(ticketId);
    }

    @Override
    public void run() {
        while (true){

            ticketPoolService.addTicket(releaseInterval,this.vendorId,this);
            if (ticketPool.getTotalTickets() == 0) {
                System.out.println("All tickets added, Vendor " + vendorId + "Finished execution.. Terminating ");
                break;
            }
        }
    }


}