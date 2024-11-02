package com.oop.cwk;

import java.util.ArrayList;
import java.util.List;

public  class Vendor implements Runnable{

    private int vendorId;//unique id for each vendor
    private int TicketsPerRelease;// varies from vendor import random
    private int releaseInterval;//get from config
    private TicketPool ticketPool;
    private List<Integer> soldTickets=new ArrayList<>();


    public int getVendorId() {
        return vendorId;
    }
    public List<Integer> getSoldTickets() {return soldTickets;}

    public Vendor(int TicketsPerRelease, int releaseInterval, TicketPool ticketPool, int vendorId){
        this.TicketsPerRelease=TicketsPerRelease;
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
                    ticketPool.addTickets(TicketsPerRelease,releaseInterval,this.vendorId,this);
                    System.out.println("done by vendor "+vendorId);
                    if (ticketPool.totalTickets == 0) {
                        System.out.println("All tickets added, vendor " + vendorId + " is done.");
                        break;
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Restore interrupted status
                    break;
                }
            }
        System.out.println("got out vendor" +vendorId);

    }


}