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
    private static final int numVendors=10;
    private static final int numCustomers=10;
    private static final Thread [] vendorThreads=new Thread[numVendors];
    private static final Thread [] customerThreads=new Thread[numCustomers];


    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context=SpringApplication.run(Main.class, args);
        TicketPool ticketPool=context.getBean(TicketPool.class);
        TicketPoolService ticketPoolService=context.getBean(TicketPoolService.class);

        // initialize TicketPool with values from config
        ConfigService configService=context.getBean(ConfigService.class);
        Scanner scanner=new Scanner(System.in);

        int option;


        System.out.println("Welcome to the program ");
        System.out.println("1. Create New Config File");
        System.out.println("2. Load Existing Config File");
        do{
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
                config=configService.loadConfig(scanner);
                break;
        }

        startTicketPool(config,ticketPool,ticketPoolService);

    }

    public static void startTicketPool(Config config,TicketPool ticketPool,TicketPoolService ticketPoolService) throws InterruptedException {
        ticketPool.setTotalTickets(config.getTotalTickets());
        ticketPool.setMaximumTicketCapacity(config.getMaxTicketCapacity());


        int ticketReleaseRate = config.getTicketReleaseRate();
        int customerRetrievalRate = config.getCustomerRetrievalRate();

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
        for(int i=0;i<numVendors;i++) {
            System.out.println(vendors.get(i));
        }
        for(int j=0;j<numCustomers;j++){
            System.out.println(customers.get(j));
            System.out.println(customers.get(j).getCustomerId());
        }

    }

    public static void restartTicketPool(Config config,TicketPool ticketPool,TicketPoolService ticketPoolService) {
        ticketPool.getAvailableTickets().clear();
        ticketPool.setTotalTickets(config.getTotalTickets());
        ticketPool.setMaximumTicketCapacity(config.getMaxTicketCapacity());
        ticketPool.setCurrentTicket(1);
        ticketPoolService.getLogs().clear();

        for (Vendor vendor : vendors) {
            vendor.resetVendor();
        }
        for (Customer customer : customers) {
            customer.resetCustomer();
        }


        for (int i = 0; i < numVendors; i++) {
            vendorThreads[i] = new Thread(vendors.get(i));
            vendorThreads[i].start();
        }

        // Create new threads for customers
        for (int i = 0; i < numCustomers; i++) {
            customerThreads[i] = new Thread(customers.get(i));
            customerThreads[i].start();
        }



    }
}
