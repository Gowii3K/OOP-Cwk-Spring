package com.oop.cwk.Model;



public class Config {
     private int totalTickets;
     private int ticketReleaseRate;
     private int customerRetrievalRate;
     private int maxTicketCapacity;

     public Config(){
          System.out.println("Config initialized");
     }

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
