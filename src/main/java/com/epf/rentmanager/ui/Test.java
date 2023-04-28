package com.epf.rentmanager.ui;

import com.epf.rentmanager.AppConfiguration;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.model.Vehicle;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class Test {
    public static void main(String[] arg){
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
        VehicleService vehicleService = context.getBean(VehicleService.class);
        ClientService clientService = context.getBean(ClientService.class);

        try{
            List<Vehicle> clients = vehicleService.findAll();
            System.out.println(clients);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }


    }
}
