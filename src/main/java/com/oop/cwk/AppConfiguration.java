package com.oop.cwk;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfiguration {

    @Bean
    @Scope("prototype")
    public TicketPool ticketPool(){
        return new TicketPool();
    }

    
}
