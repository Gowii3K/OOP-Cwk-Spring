package com.oop.cwk;

import java.util.List;

public class TicketDTO {

    TicketPool ticketPool;
    List<Customer> customers;
    List<Vendor> vendors;

    public TicketPool getTicketPool() {
        return ticketPool;
    }



    public List<Customer> getCustomers() {
        return customers;
    }



    public List<Vendor> getVendors() {
        return vendors;
    }



    public TicketDTO(TicketPool ticketPool, List<Customer> customers, List<Vendor> vendors) {
        this.ticketPool = ticketPool;
        this.customers = customers;
        this.vendors = vendors;
    }

}
