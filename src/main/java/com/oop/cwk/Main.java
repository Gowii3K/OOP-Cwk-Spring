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

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context=SpringApplication.run(Main.class, args);
        TicketPool ticketPool=context.getBean(TicketPool.class);
        TicketPoolService ticketPoolService=context.getBean(TicketPoolService.class);

        // initialize TicketPool with values from config
        ConfigService configService=context.getBean(ConfigService.class);
        Scanner scanner=new Scanner(System.in);
        System.out.println("Welcome to the program Please select an Option");
        System.out.println("1. Create New Config File");
        System.out.println("2. Load Existing Config File");

        int option=scanner.nextInt();

        switch (option){
            case 1:
                config=configService.createNewConfig(scanner);
                break;
            case 2:
                config=configService.loadConfig(scanner);
                break;
        }

        startTicketPool(config,ticketPool,ticketPoolService);

    }

    public static void startTicketPool(Config config,TicketPool ticketPool,TicketPoolService ticketPoolService) throws InterruptedException {
        ticketPool.setMaximumTicketCapacity(config.getMaxTicketCapacity());

        int numVendors=10;
        int numCustomers=10;
        Thread [] vendorThreads=new Thread[numVendors];
        Thread [] customerThreads=new Thread[numCustomers];
        int ticketReleaseRate = config.getTicketReleaseRate();
        int customerRetrievalRate = config.getCustomerRetrievalRate();
        int totalTickets=config.getTotalTickets();


        //create vendor threads
        for (int i = 0; i < numVendors; i++) {

            vendors.add(new Vendor(ticketReleaseRate, ticketPool,ticketPoolService,totalTickets)); //
            vendorThreads[i] = new Thread(vendors.get(i));
            vendorThreads[i].setName("Vendor"+(i+1));
            vendorThreads[i].start();
        }
        //create customer threads
        for (int i = 0; i < numCustomers; i++) {

            customers.add(new Customer(customerRetrievalRate,ticketPoolService,ticketPool,10));
            customerThreads[i]=new Thread(customers.get(i));
            customerThreads[i].setName("Customer"+(i+1));
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
            System.out.println(vendors.get(i));
        }
        for(int j=0;j<numCustomers;j++){
            System.out.println(customers.get(j));
        }

    }

    public static void restartTicketPool(Config config,TicketPool ticketPool,TicketPoolService ticketPoolService) {
        ticketPool.getAvailableTickets().clear();
        ticketPool.setMaximumTicketCapacity(config.getMaxTicketCapacity());
        ticketPoolService.getLogs().clear();


    }
}
