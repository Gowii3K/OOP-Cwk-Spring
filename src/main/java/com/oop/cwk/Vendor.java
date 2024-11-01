package com.oop.cwk;

public  class Vendor implements Runnable{

    int vendorId;//unique id for each vendor
    int TicketsPerRelease;// varies from vendor import random
    int releaseInterval;//get from config

    TicketPool ticketPool;

    public int getVendorId() {
        return vendorId;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
    }

    public int getTicketsPerRelease() {
        return TicketsPerRelease;
    }

    public void setTicketsPerRelease(int ticketsPerRelease) {
        TicketsPerRelease = ticketsPerRelease;
    }

    public int getReleaseInterval() {
        return releaseInterval;
    }

    public void setReleaseInterval(int releaseInterval) {
        this.releaseInterval = releaseInterval;
    }

    public TicketPool getTicketPool() {
        return ticketPool;
    }

    public void setTicketPool(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    public Vendor(int TicketsPerRelease, int releaseInterval, TicketPool ticketPool, int vendorId){
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

    }
}