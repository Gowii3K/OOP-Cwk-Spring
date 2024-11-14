package com.oop.cwk.Model;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

@Component
public class TicketPool {

    static Logger logger=Logger.getLogger(TicketPool.class.getName());

    //locks and conditions
    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();
    private final Condition pausedCondition = lock.newCondition();

    //Fields
    private int totalTickets;
    private int maximumTicketCapacity;
    private final ConcurrentLinkedQueue<Ticket> availableTickets = new ConcurrentLinkedQueue<>();
    private int currentTicket = 1;

    //Getters and Setters
    public ConcurrentLinkedQueue<Ticket> getAvailableTickets() {
        return availableTickets;
    }
    public int getMaximumTicketCapacity() {
        return maximumTicketCapacity;
    }
    public void setMaximumTicketCapacity(int maximumTicketCapacity) {
        this.maximumTicketCapacity = maximumTicketCapacity;
    }
    public  int getTotalTickets() {
        return totalTickets;
    }
    public  void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }
    private volatile boolean isPaused = false;


    public TicketPool() {
        System.out.println("Initializing TicketPool");
    }


    public void pause() {
        isPaused = true;
        System.out.println("TicketPool paused.");
    }

    public void resume() {
            lock.lock(); // Acquire the lock
            try {
                isPaused = false; // Update the paused state
                pausedCondition.signalAll(); // Notify any waiting threads
                System.out.println("TicketPool resumed.");
            } finally {
                lock.unlock(); // Ensure the lock is released
            }
        }

    private void checkPaused() throws InterruptedException {

            while (isPaused) {
                pausedCondition.await(); // Wait until signaled to continue
            }
    }

    public  void addTickets( int releaseInterval,int vendorId ,Vendor vendor) throws InterruptedException {
        lock.lock();
        try {
            while (true) {
                checkPaused();
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
                else {
                        System.out.println(("Ticket No " + currentTicket + "Added by " + vendorId));
                        Ticket ticket=new Ticket(currentTicket);
                        availableTickets.offer(ticket);
                        vendor.TicketSold(currentTicket);
                        currentTicket++;
                        totalTickets--;
                        System.out.println("Available current tickets are " + availableTickets);
                        //notify waiting threads
                        Thread.sleep(releaseInterval);
                        notEmpty.signalAll();
                        break;
                }
            }
        }
        finally {
            lock.unlock();
        }

    }

    public  void removeTicket(int retrievalInterval,int customerId, Customer customer) throws InterruptedException {

        Ticket purchasedTicket;
        lock.lock();
        try {
            while (true) {
                checkPaused();
                //total tickets out and no more on sale
                if (availableTickets.isEmpty() && totalTickets == 0) {
                    System.out.println("All tickets sold out ");
                    return;
                }
                //nothing on sale but it will be put up eventually
                else if (totalTickets > 0 && availableTickets.isEmpty()) {
                    System.out.println("wait for vendors to add pls");
                    notEmpty.await();

                //if neither of the above conditions are satisfied continue with removing a ticket
                } else {
                    purchasedTicket = availableTickets.poll();
                    customer.ticketBought(purchasedTicket.getId());
                    System.out.println("Ticket No " + purchasedTicket.getId() + "bought by " + customerId);
                    Thread.sleep(retrievalInterval);
                    notFull.signalAll();
                    break;
                }
            }
        }
        finally {
            lock.unlock();
        }

    }
}
