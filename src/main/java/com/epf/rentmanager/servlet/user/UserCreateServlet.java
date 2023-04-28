package com.epf.rentmanager.servlet.user;

import com.epf.rentmanager.AppConfiguration;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

import static java.time.LocalDate.parse;

@WebServlet(name = "UserCreateServlet", value = "/users/edit")
public class UserCreateServlet extends HttpServlet {


    @Autowired
    ClientService clientService;


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
                Client client = clientService.findById(Long.parseLong(id));
                request.setAttribute("first_name", client.getFirstName());
                request.setAttribute("last_name", client.getLastName());
                request.setAttribute("email", client.getEmail());
                request.setAttribute("birthday", client.getBirthDate());
            }
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }

        request.getRequestDispatcher("/WEB-INF/views/users/edit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String lastName = request.getParameter("last_name");
        String firstName = request.getParameter("first_name");
        String email = request.getParameter("email");
        LocalDate birthDate = LocalDate.parse(request.getParameter("birthdate"));

        Client client = new Client(firstName,lastName,birthDate,email);

        try {
            if(id.equals("")){
                clientService.create(client);
            }else{
                clientService.update(Long.parseLong(id), lastName, firstName, email, birthDate);
            }
        } catch (ServiceException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        response.sendRedirect("/rentmanager/users");

    }
}
