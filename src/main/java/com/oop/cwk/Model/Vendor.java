package com.oop.cwk.Model;

import com.oop.cwk.Service.TicketPoolService;

import java.util.ArrayList;
import java.util.List;

public  class Vendor implements Runnable{

    private final int totalTickets;

    private final int releaseInterval;//get from config
    private final TicketPoolService ticketPoolService;
    private final TicketPool ticketPool;



    public Vendor(int releaseInterval, TicketPool ticketPool, TicketPoolService ticketPoolService, int totalTickets){

        this.releaseInterval=releaseInterval;
        this.ticketPool=ticketPool;

        this.ticketPoolService = ticketPoolService;
        this.totalTickets = totalTickets;
    }



    @Override
    public void run() {
        for(int i=0;i<totalTickets;i++){
            ticketPoolService.addTicket();
            try {
                Thread.sleep(releaseInterval);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }


}