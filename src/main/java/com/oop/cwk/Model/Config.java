package com.oop.cwk.Model;


/**
 * Represents the Configuration values that the ticketBooking system should operate in
 */
public class Config {
     //total tickets available for the event
     private int totalTickets;
     //time which a vendor must wait before attempting to release another ticket
     private int ticketReleaseRate;
     //time which a customer must wait before attempting to purchase another ticket
     private int customerRetrievalRate;
     //the maximum amount of tickets that can be on sale at a given time
     private int maxTicketCapacity;
     //number of vendors for the event
     private final int numVendors;
     //number of customers for the event
     private final int numCustomers;

     public Config(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity, int numVendors, int numCustomers) {
          this.totalTickets = totalTickets;
          this.ticketReleaseRate = ticketReleaseRate;
          this.customerRetrievalRate = customerRetrievalRate;
          this.maxTicketCapacity = maxTicketCapacity;
          this.numVendors = numVendors;
          this.numCustomers = numCustomers;
     }

     //getters and setters
     public int getTotalTickets() {
          return totalTickets;
     }

     public void setTotalTickets(int totalTickets) {
          this.totalTickets = totalTickets;
     }

     public int getTicketReleaseRate() {
          return ticketReleaseRate;
     }

     public void setTicketReleaseRate(int ticketReleaseRate) {
          this.ticketReleaseRate = ticketReleaseRate;
     }

     public int getCustomerRetrievalRate() {
          return customerRetrievalRate;
     }

     public void setCustomerRetrievalRate(int customerRetrievalRate) {
          this.customerRetrievalRate = customerRetrievalRate;
     }

     public int getNumVendors() {
          return numVendors;
     }

     public int getNumCustomers() {
          return numCustomers;
     }

     public int getMaxTicketCapacity() {
          return maxTicketCapacity;
     }

     public void setMaxTicketCapacity(int maxTicketCapacity) {
          this.maxTicketCapacity = maxTicketCapacity;
     }

     @Override
     public String toString() {
          return "Config{" +
                  "totalTickets=" + totalTickets +
                  ", ticketReleaseRate=" + ticketReleaseRate +
                  ", customerRetrievalRate=" + customerRetrievalRate +
                  ", maxTicketCapacity=" + maxTicketCapacity +
                  '}';
     }
}
