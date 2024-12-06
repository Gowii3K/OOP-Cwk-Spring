package com.oop.cwk.Controller;

import com.oop.cwk.*;
import com.oop.cwk.DTO.TicketDTO;
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
public class VendorController {

    TicketPool ticketPool;
    TicketPoolService ticketPoolService;

    @Autowired
    public VendorController(TicketPool ticketPool, TicketPoolService ticketPoolService) {
        this.ticketPool = ticketPool;
        this.ticketPoolService = ticketPoolService;
    }

    @GetMapping("/vendors")
    public TicketPool getVendors() {
        return ticketPool;

    }

    @GetMapping("/customers")
    public TicketDTO getCustomers() {
        List<Customer> customers= Main.getCustomers();
        List<Vendor> vendors=Main.getVendors();
        return new TicketDTO(ticketPool,customers,vendors,ticketPoolService);
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
        ticketPoolService.stopTicketPool();
        return "All threads are paused.";
    }

    @GetMapping("/resume")
    public String resumeThreads() {
        if(TicketPoolService.getIsStopped()){

        }
        else {
            ticketPoolService.stopTicketPool();
        }
        System.out.println("Restarted");
        Main.restartTicketPool(Main.getConfig(),ticketPool,ticketPoolService);
        ticketPoolService.resume();
        return "All threads are resumed.";
    }

}
