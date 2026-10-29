package mk.ukim.finki.wp.lab.web.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.lab.model.Registration;
import mk.ukim.finki.wp.lab.service.RegistrationService;
import mk.ukim.finki.wp.lab.service.WorkshopService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "RegistrationServlet", urlPatterns = "/registration")
public class RegistrationServlet extends HttpServlet {
    private final RegistrationService registrationService ;
    private final SpringTemplateEngine Springengine;

    public RegistrationServlet(RegistrationService registrationService, SpringTemplateEngine Springengine) {
        this.registrationService = registrationService;
        this.Springengine = Springengine;
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String workShop=req.getParameter("workshopTitle");
        String numSeats=req.getParameter("numSeats");

        String name=req.getParameter("participantName");
        String mail=req.getParameter("participantEmail");

        try {
            int numberOfSeats = Integer.parseInt(numSeats);

            Registration registration = registrationService.register(
                    workShop,
                    name,
                    mail,
                    numberOfSeats
            );

            WebContext context = new WebContext(
                    JakartaServletWebApplication
                            .buildApplication(getServletContext())
                            .buildExchange(req, res)
            );

            context.setVariable("registration", registration);
            context.setVariable("clientIp", req.getRemoteAddr());
            context.setVariable("browser", req.getHeader("User-Agent"));

            res.setContentType("text/html;charset=UTF-8");

            Springengine.process(
                    "registrationConfirmation.html",
                    context,
                    res.getWriter()
            );

        } catch (IllegalArgumentException e) {

            String message = URLEncoder.encode(
                    e.getMessage() != null ? e.getMessage() : "Invalid registration data",
                    StandardCharsets.UTF_8
            );

            res.sendRedirect("/?errorMessage=" + message);
        }
    }
}
