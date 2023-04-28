package com.epf.rentmanager.servlet.user;

import com.epf.rentmanager.AppConfiguration;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(name = "UserDetailsServlet", value = "/users/details")
public class UserDetailsServlet extends HttpServlet {


    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private VehicleService vehicleService;


    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");

        try{
            // RECUPERATION DU CLIENT
            Client client = clientService.findById(Long.parseLong(id));

            // RECUPERATION DES RESERVATION ASSOCIE AU CLIENT
            List<Reservation> reservationList = reservationService.findAll().stream().filter(r -> r.getClientId() == Long.parseLong(id)).collect(Collectors.toList());

            // RECUPERATION DES VEHICULES ASSOCIE AU RESERVATION DU CLIENT
            Map<Long, Vehicle> vehicleMap = vehicleService.findAll().stream().collect(Collectors.toMap(Vehicle::getId, v -> v));
            Map<Long, Vehicle> vehicules = reservationList.stream().map(r -> vehicleMap.get(r.getVehicleId())).collect(Collectors.toMap(Vehicle::getId, v -> v, (v1, v2) -> v2));

            request.setAttribute("user", client);
            request.setAttribute("reservations", reservationList);
            request.setAttribute("vehicles", vehicules);

            this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/details.jsp").forward(request, response);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }
}
