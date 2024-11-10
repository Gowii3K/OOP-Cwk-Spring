package com.oop.cwk.Model;

import org.springframework.stereotype.Component;

@Component
public class Config {
     private int totalTickets=100;
     private int ticketReleaseRate=1000;
     private int customerRetrievalRate=1000;
     private int maxTicketCapacity=20;

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
}
