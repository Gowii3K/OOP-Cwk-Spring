package com.oop.cwk.Service;

import com.oop.cwk.Model.Customer;
import com.oop.cwk.Model.Ticket;
import com.oop.cwk.Model.TicketPool;
import com.oop.cwk.Model.Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

@Service
public class TicketPoolService {

    TicketPool ticketPool;
    @Autowired
    public TicketPoolService(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
        System.out.println("Ticket Pool Service Created");
    }

    private static Logger logger = Logger.getLogger(TicketPoolService.class.getName());
    private  final List<String> logs = new ArrayList<>();


    public List<String> getLogs() {
        return logs;
    }

    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();
    private final Condition notStopped = lock.newCondition();
    private static volatile Boolean isStopped = false;

    public static Boolean getIsStopped() {
        return isStopped;
    }

    public void resume(){
        lock.lock();
        try {
            isStopped = false;
            notStopped.signalAll();
            System.out.println("resumed");
        }
        finally {
            lock.unlock();
        }
    }

    public void stopTicketPool() {
        System.out.println("Stopping Ticket Pool");
        isStopped = true;
    }

    public void checkStopped() throws InterruptedException {
        while (isStopped){
            notStopped.await();
            ticketPool.getAvailableTickets().clear();
        }
    }


    public void addTicket(int releaseInterval, int vendorId , Vendor vendor) {
        lock.lock();
        System.out.println("Vendor "+vendorId+" Acquired Lock");
        try {
            while (ticketPool.getTotalTickets()!=0) {
                checkStopped();
                if (ticketPool.getAvailableTickets().size() == ticketPool.getMaximumTicketCapacity()) {
                    notFull.await();
                } else {
                    Ticket ticket = new Ticket(ticketPool.getCurrentTicket());
                    ticketPool.getAvailableTickets().offer(ticket);
                    vendor.TicketSold(ticketPool.getCurrentTicket());
                    System.out.println("Ticket No "+ticket.getId()+ "Added By Vendor" + vendorId);
                    logger.info("Ticket No "+ticket.getId()+ "Added By Vendor" + vendorId);
                    logs.add("Ticket No "+ticket.getId()+ "Added By Vendor" + vendorId);
                    ticketPool.decrementTotalTickets();
                    ticketPool.incrementCurrentTicket();
                    notEmpty.signalAll();
                    return;
                }
            }
        }
        catch (InterruptedException e) {
            //handle this
                throw new RuntimeException(e);
            }
        finally {
            System.out.println("Vendor "+vendorId+" Released Lock");
            lock.unlock();
        }
    }


    public void removeTicket(int retrievalInterval,int customerId, Customer customer)  {
        lock.lock();
        System.out.println("Customer "+customerId+" Acquired Lock");
        try {
            while (ticketPool.getTotalTickets() != 0 || !ticketPool.getAvailableTickets().isEmpty()) {
                checkStopped();
                if (ticketPool.getAvailableTickets().isEmpty() && ticketPool.getTotalTickets() !=0) {
                    notEmpty.await();
                } else {
                    Ticket ticket = ticketPool.getAvailableTickets().poll();
                    customer.ticketBought(ticket.getId());
                    System.out.println("Ticket No "+ticket.getId()+ "Purchased By Customer" + customerId);
                    logger.info("Ticket No "+ticket.getId()+ "Purchased By Customer" + customerId);
                    logs.add("Ticket No "+ticket.getId()+ "Purchased By Customer" + customerId);
                    notFull.signalAll();
                    return;
                }
            }
        }
        catch (Exception e){
            //handle this
        }
        finally {
            System.out.println("Customer "+customerId+" Released Lock");
            lock.unlock();
        }
    }
}



