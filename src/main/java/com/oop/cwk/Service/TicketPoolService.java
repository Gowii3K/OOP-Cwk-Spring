package com.oop.cwk.Service;

import com.oop.cwk.Model.Ticket;
import com.oop.cwk.Model.TicketPool;
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


    public void addTicket() {
        lock.lock();
        try {
            while (ticketPool.getAvailableTickets().size()>= ticketPool.getMaximumTicketCapacity()){
                notFull.await();
            }
            Ticket ticket=new Ticket(4);
            ticketPool.getAvailableTickets().offer(ticket);
            System.out.println(Thread.currentThread().getName()+" : Ticket Added");
            notEmpty.signalAll();
        }
        catch (InterruptedException e) {
            //handle this
                throw new RuntimeException(e);
            }
        finally {
            lock.unlock();
        }
    }


    public void removeTicket()  {
        lock.lock();
        try {
            while (ticketPool.getAvailableTickets().isEmpty()){
                notEmpty.await();
            }
            ticketPool.getAvailableTickets().poll();
            System.out.println(Thread.currentThread().getName()+": Ticket Removed");
            notFull.signalAll();


        }
        catch (Exception e){
            //handle this
        }
        finally {
            lock.unlock();
        }
    }
}



