package com.example.Army.hibernateConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SessionFactory {


    @Bean
    public org.hibernate.SessionFactory findSessionFactory(){
        return new HibernateConfiguration().getSessionFactory();
    }


}
