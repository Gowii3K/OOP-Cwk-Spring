package com.oop.cwk.Service;

import com.oop.cwk.Model.Customer;
import com.oop.cwk.Model.Ticket;
import com.oop.cwk.Model.TicketPool;
import com.oop.cwk.Model.Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class TicketPoolService {

    TicketPool ticketPool;
    @Autowired
    public TicketPoolService(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
        System.out.println("Ticket Pool Service Created");
    }


    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();


    public void addTicket(int releaseInterval, int vendorId , Vendor vendor){
        lock.lock();
        try {
            while (ticketPool.getTotalTickets()!=0) {
                if (ticketPool.getAvailableTickets().size() == ticketPool.getMaximumTicketCapacity()) {
                    notFull.await();
                } else {
                    Ticket ticket = new Ticket(ticketPool.getCurrentTicket());
                    ticketPool.getAvailableTickets().offer(ticket);
                    vendor.TicketSold(ticketPool.getCurrentTicket());
                    System.out.println("Ticket No "+ticket.getId()+ "Added By Vendor" + vendorId);
                    ticketPool.decrementTotalTickets();
                    ticketPool.incrementCurrentTicket();
                    Thread.sleep(releaseInterval);
                    notEmpty.signalAll();
                    return;
                }
            }
        }
        catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        finally {
            lock.unlock();
        }
    }


    public void removeTicket(int retrievalInterval,int customerId, Customer customer){
        lock.lock();
        try {
            while (ticketPool.getTotalTickets() != 0 || !ticketPool.getAvailableTickets().isEmpty()) {
                System.out.println("Customer "+customerId+" Aqquired Lock");
                if (ticketPool.getAvailableTickets().isEmpty() && ticketPool.getTotalTickets() !=0) {

                    notEmpty.await();

                } else {
                    Ticket ticket = ticketPool.getAvailableTickets().poll();
                    customer.ticketBought(ticket.getId());
                    System.out.println("Ticket No "+ticket.getId()+ "Purchased By Customer" + customerId);
                    Thread.sleep(retrievalInterval);
                    notFull.signalAll();
                    return;
                }
            }
        }
        catch (Exception e){

        }
        finally {
            lock.unlock();
        }
    }
}



