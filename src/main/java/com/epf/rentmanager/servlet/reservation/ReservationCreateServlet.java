package com.epf.rentmanager.servlet.reservation;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebServlet(name = "ReservationCreateServlet", value = "/rents/edit")
public class ReservationCreateServlet extends HttpServlet {


    @Autowired
    ReservationService reservationService;

    @Autowired
    ClientService clientService;

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
                Reservation reservation = reservationService.findById(Long.parseLong(id));
                request.setAttribute("client_id", reservation.getClientId());
                request.setAttribute("vehicle_id", reservation.getVehicleId());
                request.setAttribute("end", reservation.getEnd().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                request.setAttribute("start", reservation.getStart().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }
            request.setAttribute("clients", clientService.findAll());
            request.setAttribute("vehicles", vehicleService.findAll());

        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }

        request.getRequestDispatcher("/WEB-INF/views/rents/edit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String client_id = request.getParameter("client");
        String vehicule_id = request.getParameter("car");
        LocalDate start = LocalDate.parse(request.getParameter("begin"), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate end = LocalDate.parse(request.getParameter("end"), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        Reservation reservation = new Reservation(Long.parseLong(client_id), Long.parseLong(vehicule_id), start, end);

        try {
            if(id.equals("")){
                reservationService.create(reservation);
            }else{
                reservationService.update(Long.parseLong(id), Long.parseLong(client_id), Long.parseLong(vehicule_id), start, end);
            }
        } catch (ServiceException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        response.sendRedirect("/rentmanager/rents");
    }
}
