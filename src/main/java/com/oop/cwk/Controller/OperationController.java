package com.oop.cwk.Controller;

import com.oop.cwk.Main;
import com.oop.cwk.Model.TicketPool;
import com.oop.cwk.Service.TicketPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class OperationController {
    TicketPool ticketPool;
    TicketPoolService ticketPoolService;

    @Autowired
    public OperationController(TicketPool ticketPool, TicketPoolService ticketPoolService) {
        this.ticketPool = ticketPool;
        this.ticketPoolService = ticketPoolService;
    }

    @PostMapping("/stop")
    public String stopThreads() {
        if(TicketPoolService.getIsStopped()){
            ticketPoolService.resume();
        }
        else {
            ticketPoolService.stopTicketPool();

        }
        return "All threads are paused.";
    }

    @PostMapping("/restart")
    public String restartThreads() {
        if(!TicketPoolService.getIsStopped()){
            ticketPoolService.stopTicketPool();
        }
        System.out.println("Restarted");
        Main.restartTicketPool(Main.getConfig(),ticketPool,ticketPoolService);
        ticketPoolService.resume();
        return "All threads are resumed.";
    }

}
