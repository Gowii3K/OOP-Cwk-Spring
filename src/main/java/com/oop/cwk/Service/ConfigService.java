package com.oop.cwk.Service;

import com.google.gson.Gson;
import com.oop.cwk.Model.Config;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * Service for creating and loading configuration files
 */
@Service
public class ConfigService {

    Config config= null;
    //gson to convert object to json file
    Gson gson = new Gson();


    /**
     * Create a new configuration with inputs provided by the user
     * @param scanner=take user input
     * @return configuration file with properties defined by the user
     */
    public Config createNewConfig(Scanner scanner) {
        int totalTickets;

        do {
            System.out.println("Enter Total Tickets (Minimum 1)");

            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a number");
                scanner.nextLine();
            }
            totalTickets = scanner.nextInt();
            scanner.nextLine();
        }while (totalTickets<=0);


        int ticketReleaseRate;
        do {
            System.out.println("Enter Ticket Release Rate (Minimum 1)");
            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a number");
                scanner.nextLine();
            }
            ticketReleaseRate = scanner.nextInt();
        }while (ticketReleaseRate <=0);
        int customerRetrievalRate;
        do {
            System.out.println("Enter Customer Retrieval Rate (Minimum 1)");
            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a number");
                scanner.nextLine();
            }
            customerRetrievalRate = scanner.nextInt();
            scanner.nextLine();
        }while (customerRetrievalRate <=0);
        int maxTicketCapacity;
        do {
            System.out.println("Enter Max Ticket Capacity (Minimum 1)");
            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a number");
                scanner.nextLine();
            }
            maxTicketCapacity = scanner.nextInt();
            scanner.nextLine();
        }while (maxTicketCapacity <=0);

        int numVendors;

        do {
            System.out.println("Enter Number of Vendors (Minimum 1)");
            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a number");
                scanner.nextLine();
            }
            numVendors = scanner.nextInt();
            scanner.nextLine();
        }while (numVendors <=0);

        int numCustomers;

        do {
            System.out.println("Enter Number of Customers (Minimum 1)");
            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a number");
                scanner.nextLine();
            }
            numCustomers = scanner.nextInt();
            scanner.nextLine();
        }while (numCustomers <=0);

        config=new Config(totalTickets,ticketReleaseRate,customerRetrievalRate,maxTicketCapacity,numVendors,numCustomers);


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
        return config;
    }

    /**
     * Load an existing configuration
     * @param scanner=take in name of the configuration file
     * @return configuration file specified by the user
     */
    public Config loadConfig(Scanner scanner) {

        try {
            while (true) {
                System.out.println("Enter name of the config file you want to load from");
                String loadName=scanner.next();
                File f = new File(loadName + ".json");
                if (f.exists()) {
                    System.out.println("file exists");
                    FileReader fileReader = new FileReader(loadName + ".json");
                    config = gson.fromJson(fileReader, Config.class);
                    break;
                }
                else {
                    System.out.println("File not found");
                }
            }

        }
        catch (Exception ignored){
            System.out.println("Error writing config file");
        }
        return config;

    }






}
