package com.oop.cwk;

import com.google.gson.Gson;

import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

@SpringBootApplication
public class Main {
    public static void main(String[] args) throws InterruptedException {





        // initialize TicketPool with values from config
        Gson gson = new Gson();
        Config config=new Config();

        System.out.println("Welcome to the program Please select an Option");
        System.out.println("1. Create New Config File");
        System.out.println("2. Load Existing Config File");
        Scanner scanner=new Scanner(System.in);
        int option=scanner.nextInt();
        switch (option){
            case 1:
                config=new Config();

                System.out.println("Enter Total Tickets");
                config.totalTickets=scanner.nextInt();
                System.out.println("Enter Max Ticket Capacity");
                config.maxTicketCapacity=scanner.nextInt();
                System.out.println("Enter Ticket adding rate");
                config.ticketReleaseRate= scanner.nextInt();
                System.out.println("Enter Ticket selling rate");
                config.customerRetrievalRate=scanner.nextInt();
                String myJson=gson.toJson(config);
                System.out.println(myJson);
                System.out.println("what do u want to name the config file");
                String name=scanner.next();                try {
                    FileWriter writer= new FileWriter(name+".json");
                    gson.toJson(config,writer);
                    writer.close();

                }
                catch (Exception e){

                }


                break;
            case 2:
                System.out.println("Enter name of the config file you want to load from");
                String loadName=scanner.next();
                try {


                    FileReader fileReader = new FileReader(loadName + ".json");
                    config=gson.fromJson(fileReader,Config.class);
                    System.out.println(config);

                }
                catch (Exception ignored){

                }
                break;
        }





        int numVendors=3;
        int numCustomers=5;




        //user cant input more max capacity than total tickets
        //vendor threads cant release more at a time than maximum ticket capacity

        Vendor[] vendorObjects = new Vendor[numVendors];
        TicketPool[] ticketPools = new TicketPool[numVendors];
        Customer[][]customerObjects = new Customer[numVendors][numCustomers];
        Thread [] vendorThreads=new Thread[numVendors];
        Thread [][] customerThreads=new Thread[numVendors][numCustomers];


        for (int i = 0; i < numVendors; i++) {
            ticketPools[i]=new TicketPool();
            ticketPools[i].setTotalTickets(config.totalTickets);
            ticketPools[i].setMaximumTicketCapacity(config.maxTicketCapacity);
            int ticketReleaseRate=config.ticketReleaseRate;
            int customerRetrievalRate=config.customerRetrievalRate;
            vendorObjects[i] = new Vendor(12, ticketReleaseRate, ticketPools[i],i+1); // Example: different TicketsPerRelease
            vendorThreads[i]= new Thread(vendorObjects[i]);
            vendorThreads[i].start();



            for (int j = 0; j < numCustomers; j++) {
                customerObjects[i][j] = new Customer(customerRetrievalRate, ticketPools[i],j+1,i+1);
                customerThreads[i][j] =new Thread(customerObjects[i][j]);
                customerThreads[i][j].start();

            }
        }

        for (int i = 0; i < numVendors; i++) {
            vendorThreads[i].join();

            for (int j = 0; j < numCustomers; j++) {
                customerThreads[i][j].join();

            }
        }


        for(int i=0;i<numVendors;i++){
            System.out.println(ticketPools[i]);
            System.out.println(ticketPools[i].getTotalTickets());
            System.out.println(ticketPools[i].getMaximumTicketCapacity());
            System.out.println(ticketPools[i].currentTicket);
            System.out.println(vendorObjects[i]);
            for(int j=0;j<numCustomers;j++){
                System.out.println(customerObjects[i][j]);
                System.out.println(customerObjects [i][j].customerId);
            }
        }

        // Create and start customer threads



    }
}
