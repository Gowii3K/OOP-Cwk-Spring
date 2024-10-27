package com.oop.cwk;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfiguration {



    @Bean
    public Config config(){
        return new Config();
    }

    @Bean
    @Scope("prototype")
    public TicketPool ticketPool(){
        return new TicketPool();
    }

    /*@Bean
    @Scope("prototype")
    public Vendor vendor(int TicketsPerRelease,int releaseInterval, TicketPool ticketPool,Config config){


    }*/








}
