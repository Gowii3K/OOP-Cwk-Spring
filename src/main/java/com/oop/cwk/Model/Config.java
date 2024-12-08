package com.oop.cwk.Model;


/**
 * Represents the Configuration values that the ticketBooking system should operate in
 */
public class Config {
     private int totalTickets;
     private int ticketReleaseRate;
     private int customerRetrievalRate;
     private int maxTicketCapacity;
     private final int numVendors;
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
