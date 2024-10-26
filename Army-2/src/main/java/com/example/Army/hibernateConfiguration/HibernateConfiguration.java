package com.example.Army.hibernateConfiguration;

import com.example.Army.SecuritySystem.Member.Member;
import com.example.Army.SecuritySystem.Token.ConfirmationToken;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateConfiguration {
    @Bean
    public SessionFactory getSessionFactory(){
        org.hibernate.cfg.Configuration config = new org.hibernate.cfg.Configuration();
        config.configure();
        config.addAnnotatedClass(Member.class);
        config.addAnnotatedClass(ConfirmationToken.class);
        StandardServiceRegistryBuilder sb = new StandardServiceRegistryBuilder();
        StandardServiceRegistry sr = sb.applySettings(config.getProperties()).build();
        SessionFactory sf = config.buildSessionFactory(sr);
        return sf;
    }
}
