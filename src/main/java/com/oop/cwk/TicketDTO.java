package com.oop.cwk;

import java.util.List;

public class TicketDTO {

    TicketPool ticketPool;
    List<Customer> customers;
    List<Vendor> vendors;

    public TicketDTO(TicketPool ticketPool, List<Customer> customers, List<Vendor> vendors) {
        this.ticketPool = ticketPool;
        this.customers = customers;
        this.vendors = vendors;
    }

}
