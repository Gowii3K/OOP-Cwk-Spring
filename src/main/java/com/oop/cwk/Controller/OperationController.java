package com.oop.cwk.Controller;

import com.oop.cwk.Main;
import com.oop.cwk.Model.TicketPool;
import com.oop.cwk.Service.TicketPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller responsible for operations that manipulate the state of the ticketPool
 */
@RestController
@CrossOrigin
public class OperationController {
    TicketPool ticketPool;
    TicketPoolService ticketPoolService;

    /**
     * Initializes the controller by autowiring ticketPool Bean and ticketPoolServiceBean
     * @param ticketPool=ticketPool of the event
     * @param ticketPoolService=service class that handles business logic of the ticketPool
     */
    @Autowired
    public OperationController(TicketPool ticketPool, TicketPoolService ticketPoolService) {
        this.ticketPool = ticketPool;
        this.ticketPoolService = ticketPoolService;
    }

    /**
     * API endpoint for stopping and resuming All threads in the system.
     * @return
     */
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

    /**
     * API endpoint for restarting the ticketPool with the initial values given by the user
     * @return
     */
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
