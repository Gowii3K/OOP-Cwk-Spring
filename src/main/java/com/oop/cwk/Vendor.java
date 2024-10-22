package com.oop.cwk;

public  class Vendor implements Runnable{

    int vendorId;//unique id for each vendor
    int TicketsPerRelease;// varies from vendor import random
    int releaseInterval;//get from config

    TicketPool ticketPool;


    public Vendor(int TicketsPerRelease,int releaseInterval, TicketPool ticketPool,int vendorId){
        this.TicketsPerRelease=TicketsPerRelease;
        this.releaseInterval=releaseInterval;
        this.ticketPool=ticketPool;
        this.vendorId=vendorId;

    }

    @Override
    public void run() {
        while (true){


                try {
                    ticketPool.addTickets(TicketsPerRelease,releaseInterval,this.vendorId);
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

//inject ticketpool






    }


}