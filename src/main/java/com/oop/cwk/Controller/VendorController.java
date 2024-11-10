package com.oop.cwk.Controller;

import com.oop.cwk.*;
import com.oop.cwk.DTO.TicketDTO;
import com.oop.cwk.Model.Customer;
import com.oop.cwk.Model.TicketPool;
import com.oop.cwk.Model.Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class VendorController {

    @Autowired
    TicketPool ticketPool;

    @GetMapping("/vendors")
    public TicketPool getVendors() {
        return ticketPool;

    }

    @GetMapping("/customers")
    public TicketDTO getCustomers() {
        List<Customer> customers= Main.getCustomers();
        List<Vendor> vendors=Main.getVendors();
        return new TicketDTO(ticketPool,customers,vendors);
    }

    @GetMapping("/test")
    public List<Vendor> getTestVendors() {
        return Main.getVendors();
    }

    @GetMapping("/test2")
    public List<Customer> getTestCustomers() {
        return Main.getCustomers();
    }
    @GetMapping("/pause")
    public String pauseThreads() {

        ticketPool.pause();
        return "All threads are paused.";
    }

    @GetMapping("/resume")
    public String resumeThreads() {
        ticketPool.resume();
        return "All threads are resumed.";
    }

}
