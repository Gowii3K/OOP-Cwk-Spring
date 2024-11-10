package com.oop.cwk.Service;

import com.google.gson.Gson;
import com.oop.cwk.Model.Config;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

@Service
public class ConfigService {

    Config config=new Config();
    Gson gson = new Gson();

    public Config createNewConfig(Scanner scanner) {
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
        return config;
    }

    public Config loadConfig(Scanner scanner) {
        System.out.println(config);
        System.out.println("Enter name of the config file you want to load from");
        String loadName=scanner.next();
        try {
            FileReader fileReader = new FileReader(loadName + ".json");
            config=gson.fromJson(fileReader,Config.class);
        }
        catch (Exception ignored){
            System.out.println("Error writing config file");
        }
        return config;

    }






}
