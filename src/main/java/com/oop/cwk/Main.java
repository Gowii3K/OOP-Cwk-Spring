package com.oop.cwk;
import com.oop.cwk.Model.Config;
import com.oop.cwk.Model.Customer;
import com.oop.cwk.Model.TicketPool;
import com.oop.cwk.Model.Vendor;
import com.oop.cwk.Service.ConfigService;
import com.oop.cwk.Service.TicketPoolService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

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

    private static Config config;
    public static Config getConfig() {
        return config;
    }

    private static  Thread [] vendorThreads;
    private static  Thread [] customerThreads;



    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context=SpringApplication.run(Main.class, args);

        TicketPool ticketPool=context.getBean(TicketPool.class);
        TicketPoolService ticketPoolService=context.getBean(TicketPoolService.class);
        ConfigService configService=context.getBean(ConfigService.class);
        Scanner scanner=new Scanner(System.in);

        int option;

        System.out.println("Welcome to the program ");
        System.out.println("1. Create New Config File");
        System.out.println("2. Load Existing Config File");
        do{
            System.out.println("Enter option: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid choice");
                scanner.nextLine();
            }
            option = scanner.nextInt();

        }while (option<1 || option>2);




        switch (option){
            case 1:
                scanner.nextLine();
                config=configService.createNewConfig(scanner);
                break;
            case 2:
                scanner.nextLine();
                config=configService.loadConfig(scanner);
                break;
        }

        vendorThreads=new Thread[config.getNumVendors()];
        customerThreads=new Thread[config.getNumCustomers()];

        System.out.println("Configuration Set: "+config);
        System.out.println("Press enter key to start");
        scanner.nextLine();
        scanner.nextLine();


        startTicketPool(config,ticketPool,ticketPoolService);

    }

    /**
     * Initializes the ticketPool with configuration values provided by the user
     * @param config=details to configure the ticketPool
     * @param ticketPool=details to configure the ticketPool
     * @param ticketPoolService=common ticketPool for vendors and consumers to operate on
     * @throws InterruptedException==service class to handle business logic of ticketPool
     */
    public static void startTicketPool(Config config,TicketPool ticketPool,TicketPoolService ticketPoolService) throws InterruptedException {

        //initialize values from
        ticketPool.setTotalTickets(config.getTotalTickets());
        ticketPool.setMaximumTicketCapacity(config.getMaxTicketCapacity());
        int ticketReleaseRate = config.getTicketReleaseRate();
        int customerRetrievalRate = config.getCustomerRetrievalRate();
        int numVendors=config.getNumVendors();
        int numCustomers=config.getNumCustomers();

        //create vendor threads
        for (int i = 0; i < numVendors; i++) {

            vendors.add(new Vendor(ticketReleaseRate, ticketPool, i + 1,ticketPoolService)); //
            vendorThreads[i] = new Thread(vendors.get(i));
            vendorThreads[i].start();
        }
        //create customer threads
        for (int i = 0; i < numCustomers; i++) {

            customers.add(new Customer(i+1,customerRetrievalRate,ticketPoolService,ticketPool));
            customerThreads[i]=new Thread(customers.get(i));
            customerThreads[i].start();
        }

        //join threads
        for (int i = 0; i < numVendors; i++) {
            vendorThreads[i].join();
        }
        for (int j = 0; j < numCustomers; j++) {
            customerThreads[j].join();
        }

        System.out.println("Finished Execution.. All Tickets Listed and Sold");

    }

    /**
     * Resets the ticketPool to the configuration chosen by the user at the start
     * @param config=details to configure the ticketPool
     * @param ticketPool=common ticketPool for vendors and consumers to operate on
     * @param ticketPoolService=service class to handle business logic of ticketPool
     */
    public static void restartTicketPool(Config config,TicketPool ticketPool,TicketPoolService ticketPoolService) {
        ticketPool.getAvailableTickets().clear();
        ticketPool.setTotalTickets(config.getTotalTickets());
        ticketPool.setMaximumTicketCapacity(config.getMaxTicketCapacity());
        ticketPool.setCurrentTicket(1);
        ticketPoolService.getLogs().clear();

        //reset every vendor
        for (Vendor vendor : vendors) {
            vendor.resetVendor();
        }
        //reset every customer
        for (Customer customer : customers) {
            customer.resetCustomer();
        }


        //create new thread if existing threads are terminated
        for (int i = 0; i < config.getNumVendors(); i++) {
            if(!vendorThreads[i].isAlive()) {
                vendorThreads[i] = new Thread(vendors.get(i));
                vendorThreads[i].start();
            }

        }
        for (int i = 0; i < config.getNumCustomers(); i++) {
            if(!customerThreads[i].isAlive()) {
                customerThreads[i] = new Thread(customers.get(i));
                customerThreads[i].start();
            }
        }




    }
}
