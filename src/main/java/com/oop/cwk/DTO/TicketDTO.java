package com.oop.cwk.DTO;

import com.oop.cwk.Model.Customer;
import com.oop.cwk.Model.TicketPool;
import com.oop.cwk.Model.Vendor;
import com.oop.cwk.Service.TicketPoolService;

import java.util.List;

/**
 * Represents a Data Transfer Object to send data to the GUI
 */
public class TicketDTO {

    //ticketPool of the system
    TicketPool ticketPool;
    //Service layer of the system
    TicketPoolService ticketPoolService;
    //List of vendors
    List<Customer> customers;
    //List of Customers
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
    public TicketPoolService getTicketPoolService() {return ticketPoolService;}

    public TicketDTO(TicketPool ticketPool, List<Customer> customers, List<Vendor> vendors, TicketPoolService ticketPoolService) {
        this.ticketPool = ticketPool;
        this.customers = customers;
        this.vendors = vendors;
        this.ticketPoolService = ticketPoolService;
    }

}
