package com.oop.cwk;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;


public class TicketPool {

    static Logger logger=Logger.getLogger(TicketPool.class.getName());
    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();
    int totalTickets;
    int maximumTicketCapacity;
    ConcurrentLinkedQueue<Integer> availableTickets = new ConcurrentLinkedQueue<>();
    int currentTicket = 1;

    static {
        try {
            // Set up the logger to log to a file
            FileHandler fileHandler = new FileHandler("TicketPooltest2.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false);
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize logger", e);
        }
    }

    public TicketPool() {
        System.out.println("Initializing TicketPool");
    }
    public  int getTotalTickets() {
        return totalTickets;
    }
    public  void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }



    public  void addTickets(int TicketsPerRelease, int releaseInterval,int vendorId) throws InterruptedException {
        lock.lock();
        try {
            //checks max capacity and notifies waiting threads,so they can terminate
            if (totalTickets == 0) {
                System.out.println("All tickets added");
                return;
            }
            //checks if it should wait for customers to buy
            else if (availableTickets.size() == maximumTicketCapacity) {
                System.out.println("wait for customers to buy");
                notFull.await();
            }
            // if neither of the above condition is satisfied proceed with adding tickets
            int remainingCapacity = maximumTicketCapacity - availableTickets.size();
            int ticketBatch;
            // find the least amount between tickets per release, remaining ticket slots, and total tickets
            if (TicketsPerRelease <= remainingCapacity && TicketsPerRelease <= totalTickets) {
                ticketBatch = TicketsPerRelease;

            } else ticketBatch = Math.min(totalTickets, remainingCapacity);
            System.out.println("ticket batch is" + ticketBatch);

            for (int i = 0; i < ticketBatch; i++) {
                System.out.println(("Ticket No " + currentTicket + "Added by " + vendorId));
                logger.info("Ticket No " + currentTicket + "Added by " + vendorId);
                availableTickets.offer(currentTicket);
                currentTicket++;
                totalTickets--;
                System.out.println("tickets are for event "+ availableTickets);
            }
            //notify waiting threads
            notEmpty.signalAll();
        }
        finally {
            lock.unlock();
        }
        Thread.sleep(releaseInterval);
    }


    public  Integer removeTicket(int retrievalInterval,int customerId) throws InterruptedException {
        //total tickets out and no more on sale
        Integer purchasedTicket;
        lock.lock();
        try {
            if (availableTickets.isEmpty() && totalTickets == 0) {
                System.out.println("All tickets sold out ");
                return null;
            }

            //nothing on sale but it will be put up eventually
            else if (totalTickets > 0 && availableTickets.isEmpty()) {
                System.out.println("wait for vendors to add pls");
                notEmpty.await();
                return null;

            } else {
                purchasedTicket = availableTickets.poll();
                System.out.println("Ticket No " + purchasedTicket + "of event  "  + "bought by " + customerId);
                logger.info("Ticket No " + purchasedTicket + "of event  "  + "bought by " + customerId);
                notFull.signalAll();
            }
        }
        finally {
            lock.unlock();
        }
        Thread.sleep(retrievalInterval);
        return purchasedTicket;
    }
}
