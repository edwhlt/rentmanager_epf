package com.epf.rentmanager.servlet.vehicles;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "VehicleCreateServlet", value = "/cars/edit")
public class VehicleCreateServlet extends HttpServlet {


    @Autowired
    VehicleService vehicleService;


    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        request.setAttribute("id", request.getParameter("id"));

        try{
            if(id != null){
                Vehicle vehicle = vehicleService.findById(Long.parseLong(id));
                request.setAttribute("modele", vehicle.getModele());
                request.setAttribute("manufacturer", vehicle.getConstructor());
                request.setAttribute("seats", vehicle.getSeats());
            }
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/edit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String modele = request.getParameter("modele");
        String constructor = request.getParameter("manufacturer");
        String seats = request.getParameter("seats");
        String id = request.getParameter("id");
        if(id.equals("")){
            System.out.println("creation");
        }else{
            System.out.println("modification");
        }
        try{
            if(id.equals("")){
                vehicleService.create(new Vehicle(modele, constructor, Integer.parseInt(seats)));
            }else{
                vehicleService.update(Long.parseLong(id), modele, constructor, Integer.parseInt(seats));
            }
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        response.sendRedirect("/rentmanager/cars");
    }
}
