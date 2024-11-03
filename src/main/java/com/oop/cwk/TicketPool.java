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
    private final Condition pausedCondition = lock.newCondition();
    int totalTickets;
    int maximumTicketCapacity;
    ConcurrentLinkedQueue<Integer> availableTickets = new ConcurrentLinkedQueue<>();
    int currentTicket = 1;
    public ConcurrentLinkedQueue<Integer> getAvailableTickets() {return availableTickets;}
    public int getMaximumTicketCapacity() {return maximumTicketCapacity;}
    public  int getTotalTickets() {return totalTickets;}
    public  void setTotalTickets(int totalTickets) {this.totalTickets = totalTickets;}
    private volatile boolean isPaused = false;
    private final Lock pauseLock = new ReentrantLock();



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
        // Ensure the lock is released

    }


    public  void addTickets(int TicketsPerRelease, int releaseInterval,int vendorId ,Vendor vendor) throws InterruptedException {
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
                    int remainingCapacity = maximumTicketCapacity - availableTickets.size();
                    int ticketBatch;

                    // find the least amount between tickets per release, remaining ticket slots, and total tickets
                    if (TicketsPerRelease <= remainingCapacity && TicketsPerRelease <= totalTickets) {
                        ticketBatch = TicketsPerRelease;

                    } else ticketBatch = Math.min(totalTickets, remainingCapacity);
                    System.out.println(remainingCapacity);
                    System.out.println("ticket batch is" + ticketBatch);
                    for (int i = 0; i < ticketBatch; i++) {
                        System.out.println(("Ticket No " + currentTicket + "Added by " + vendorId));
                        availableTickets.offer(currentTicket);
                        vendor.TicketSold(currentTicket);
                        currentTicket++;
                        totalTickets--;
                        System.out.println("tickets are for event " + availableTickets);
                    }
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

        Integer purchasedTicket;
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

                } else {
                    purchasedTicket = availableTickets.poll();
                    customer.ticketBought(purchasedTicket);
                    System.out.println("Ticket No " + purchasedTicket + "of event  " + "bought by " + customerId);
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
