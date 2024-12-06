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

@RestController
@CrossOrigin
public class TicketPoolController {

    TicketPool ticketPool;
    TicketPoolService ticketPoolService;

    @Autowired
    public TicketPoolController(TicketPool ticketPool, TicketPoolService ticketPoolService) {
        this.ticketPool = ticketPool;
        this.ticketPoolService = ticketPoolService;
    }

    @GetMapping("/ticketPool")
    public TicketDTO getData() {
        List<Customer> customers= Main.getCustomers();
        List<Vendor> vendors=Main.getVendors();
        return new TicketDTO(ticketPool,customers,vendors,ticketPoolService);
    }
}
