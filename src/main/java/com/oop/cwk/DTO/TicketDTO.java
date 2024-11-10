package com.oop.cwk.DTO;

import com.oop.cwk.Model.Customer;
import com.oop.cwk.Model.TicketPool;
import com.oop.cwk.Model.Vendor;

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
