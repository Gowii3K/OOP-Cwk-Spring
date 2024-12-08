package com.oop.cwk.Controller;

import com.oop.cwk.DTO.TicketDTO;
import com.oop.cwk.Main;
import com.oop.cwk.Model.Customer;
import com.oop.cwk.Model.TicketPool;
import com.oop.cwk.Model.Vendor;
import com.oop.cwk.Service.TicketPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 * Controller responsible for handling requests related to getting information
 * about the ticketPool, customers and vendors
 */
@RestController
@CrossOrigin
public class TicketPoolController {

    TicketPool ticketPool;
    TicketPoolService ticketPoolService;

    /**
     *Initializes the controller by autowiring ticketPool Bean and ticketPoolServiceBean
     * @param ticketPool= ticketPool of the event
     * @param ticketPoolService= service class that handles business logic of the ticketPool
     */
    @Autowired
    public TicketPoolController(TicketPool ticketPool, TicketPoolService ticketPoolService) {
        this.ticketPool = ticketPool;
        this.ticketPoolService = ticketPoolService;
    }

    /**
     * API endpoint to retrieve information about ticketPool and list of vendors and customers
     * @return A Data Transfer Object containing details about the ticketPool and list of customers and vendors
     *         and tickets bought and sold by them
     */
    @GetMapping("/ticketPool")
    public TicketDTO getData() {
        List<Customer> customers= Main.getCustomers();
        List<Vendor> vendors=Main.getVendors();
        return new TicketDTO(ticketPool,customers,vendors,ticketPoolService);
    }
}
