package com.oop.cwk;

import com.google.gson.Gson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class Main {



    static TicketPool ticketPool = new TicketPool();


    private static List<Vendor> vendors = new ArrayList<>();

    public static TicketPool getTicketPool() {
        return ticketPool;
    }

    public static void setTicketPool(TicketPool ticketPool) {
        Main.ticketPool = ticketPool;
    }

    public static List<Vendor> getVendors() {
        return vendors;
    }
    private static List<Customer> customers= new ArrayList<>();
    public static List<Customer> getCustomers() {
        return customers;
    }

    public static void setVendors(List<Vendor> vendors) {
        Main.vendors = vendors;
    }

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Main.class, args);





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
        int numCustomers=7;




        //user cant input more max capacity than total tickets
        //vendor threads cant release more at a time than maximum ticket capacity

        Vendor[] vendorObjects = new Vendor[numVendors];

        Customer[]customerObjects = new Customer[numCustomers];
        Thread [] vendorThreads=new Thread[numVendors];
        Thread [] customerThreads=new Thread[numCustomers];


        ticketPool.totalTickets=config.totalTickets;
        ticketPool.maximumTicketCapacity=config.maxTicketCapacity;



        for (int i = 0; i < numVendors; i++) {

            int ticketReleaseRate = config.ticketReleaseRate;

            vendorObjects[i] = new Vendor(6, ticketReleaseRate, ticketPool, i + 1); // Example: different TicketsPerRelease
            vendors.add(vendorObjects[i]);
            vendorThreads[i] = new Thread(vendorObjects[i]);
            vendorThreads[i].start();
        }

        for (int i = 0; i < numCustomers; i++) {

            int customerRetrievalRate = config.customerRetrievalRate;

            customerObjects[i] = new Customer(customerRetrievalRate, ticketPool, i + 1); // Example: different TicketsPerRelease
            customers.add(customerObjects[i]);
            customerThreads[i]=new Thread(customerObjects[i]);
            customerThreads[i].start();
        }






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
            System.out.println(customerObjects[j].customerId);
        }


        // Create and start customer threads



    }
}
