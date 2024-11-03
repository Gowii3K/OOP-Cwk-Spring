package com.oop.cwk;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class VendorController {
    @GetMapping("/vendors")
    public TicketPool getVendors() {
        return Main.getTicketPool();

    }

    @GetMapping("/customers")
    public TicketDTO getCustomers() {
        TicketPool ticketPool = Main.getTicketPool();
        List<Customer> customers=Main.getCustomers();
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
        TicketPool ticketPool = Main.getTicketPool();
        ticketPool.pause();
        return "All threads are paused.";
    }

    @GetMapping("/resume")
    public String resumeThreads() {
        TicketPool ticketPool = Main.getTicketPool();
        ticketPool.resume();
        return "All threads are resumed.";
    }

}
