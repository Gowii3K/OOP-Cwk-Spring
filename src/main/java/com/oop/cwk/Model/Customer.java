package com.oop.cwk.Model;
import com.oop.cwk.Service.TicketPoolService;

import java.util.ArrayList;
import java.util.List;

public class Customer implements Runnable{


    private final int purchaseQuantity;
    private final int retrievalInterval;//get from config
    private final TicketPoolService ticketPoolService;
    private final TicketPool ticketPool;


    public Customer(int retrievalInterval, TicketPoolService ticketPoolService, TicketPool ticketPool,int purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
        this.retrievalInterval = retrievalInterval;
        this.ticketPoolService = ticketPoolService;
        this.ticketPool = ticketPool;
    }



    @Override
    public void run() {
        for(int i=0;i<purchaseQuantity;i++){
            ticketPoolService.removeTicket();
            try {
                Thread.sleep(retrievalInterval);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        }
    }
