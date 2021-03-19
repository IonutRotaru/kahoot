package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

public class Config {
    @Bean
    RmiProxyFactoryBean rmiProxy() {
        RmiProxyFactoryBean bean = new RmiProxyFactoryBean();
        bean.setServiceInterface(UserService.class);
        bean.setServiceUrl("rmi://localhost:1099/userservice");

        return bean;
    }
}
