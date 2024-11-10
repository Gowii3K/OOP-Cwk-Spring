package com.oop.cwk;

import com.google.gson.Gson;

import com.oop.cwk.Model.Config;
import com.oop.cwk.Model.Customer;
import com.oop.cwk.Model.TicketPool;
import com.oop.cwk.Model.Vendor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class Main {

    private static final List<Vendor> vendors = new ArrayList<>();
    private static final List<Customer> customers= new ArrayList<>();
    public static List<Vendor> getVendors() {
        return vendors;
    }
    public static List<Customer> getCustomers() {
        return customers;
    }


    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context=SpringApplication.run(Main.class, args);
        TicketPool ticketPool=context.getBean(TicketPool.class);


        // initialize TicketPool with values from config
        Gson gson = new Gson();
        Config config=context.getBean(Config.class);

        System.out.println("Welcome to the program Please select an Option");
        System.out.println("1. Create New Config File");
        System.out.println("2. Load Existing Config File");
        Scanner scanner=new Scanner(System.in);
        int option=scanner.nextInt();
        switch (option){
            case 1:
                System.out.println("Enter Total Tickets");
                config.setTotalTickets(scanner.nextInt());
                System.out.println("Enter Max Ticket Capacity");
                config.setMaxTicketCapacity(scanner.nextInt());
                System.out.println("Enter Ticket adding rate");
                config.setTicketReleaseRate(scanner.nextInt());
                System.out.println("Enter Ticket selling rate");
                config.setCustomerRetrievalRate(scanner.nextInt());
                String myJson=gson.toJson(config);
                System.out.println(myJson);
                System.out.println("what do u want to name the config file");
                String name=scanner.next();
                try {
                    FileWriter writer= new FileWriter(name+".json");
                    gson.toJson(config,writer);
                    writer.close();
                }
                catch (Exception e){
                    System.out.println("Error writing config file");
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
                    System.out.println("Error writing config file");
                }
                break;
        }

        int numVendors=3;
        int numCustomers=5;
        Vendor[] vendorObjects = new Vendor[numVendors];
        Customer[]customerObjects = new Customer[numCustomers];
        Thread [] vendorThreads=new Thread[numVendors];
        Thread [] customerThreads=new Thread[numCustomers];


        ticketPool.setTotalTickets(config.getTotalTickets());
        ticketPool.setMaximumTicketCapacity(config.getMaxTicketCapacity());


        //create vendor threads
        for (int i = 0; i < numVendors; i++) {
            int ticketReleaseRate = config.getTicketReleaseRate();
            vendorObjects[i] = new Vendor(ticketReleaseRate, ticketPool, i + 1); // Example: different TicketsPerRelease
            vendors.add(vendorObjects[i]);
            vendorThreads[i] = new Thread(vendorObjects[i]);
            vendorThreads[i].start();
        }
        //create customer threads
        for (int i = 0; i < numCustomers; i++) {
            int customerRetrievalRate = config.getCustomerRetrievalRate();
            customerObjects[i] = new Customer(customerRetrievalRate, ticketPool, i + 1); // Example: different TicketsPerRelease
            customers.add(customerObjects[i]);
            customerThreads[i]=new Thread(customerObjects[i]);
            customerThreads[i].start();
        }
        //join threads
        for (int i = 0; i < numVendors; i++) {
            vendorThreads[i].join();
        }
        for (int j = 0; j < numCustomers; j++) {
                customerThreads[j].join();
        }
        for(int i=0;i<numVendors;i++) {
            System.out.println(vendorObjects[i]);
        }
        for(int j=0;j<numCustomers;j++){
            System.out.println(customerObjects[j]);
            System.out.println(customerObjects[j].getCustomerId());
        }
    }
}
