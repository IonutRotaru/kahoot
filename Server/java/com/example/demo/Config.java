package com.example.demo;

import com.example.demo.service.UserService;

import com.example.demo.service.UserServiceImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.remoting.rmi.RmiServiceExporter;
import org.springframework.remoting.support.RemoteExporter;

public class Config {

    @Bean
    RemoteExporter registerRMIExporter() {

        RmiServiceExporter exporter = new RmiServiceExporter();
        exporter.setServiceName("userservice");
        exporter.setServiceInterface(UserService.class);
        // exporter.setRegistryHost("localhost");
        exporter.setRegistryPort(1099);
        exporter.setService(new UserServiceImpl());

        return exporter;
    }
}
