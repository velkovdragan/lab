package mk.ukim.finki.wp.lab.web.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.service.WorkshopService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet(name = "WorkshopListServlet", urlPatterns = "")
public class WorkshopListServlet extends HttpServlet {

    private final WorkshopService workshopService;
    private final SpringTemplateEngine Springengine;

    public WorkshopListServlet(WorkshopService workshopService, SpringTemplateEngine Springengine) {
        this.workshopService = workshopService;
        this.Springengine = Springengine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html; charset=UTF-8");

        WebContext context = new WebContext(
                JakartaServletWebApplication.buildApplication(getServletContext())
                        .buildExchange(req, resp));

        context.setVariable("errorMessage", req.getParameter("errorMessage"));

        String text = req.getParameter("text");
        String priceStr = req.getParameter("maxPrice");

        if (text != null && text.isBlank()) text = null;

        Double maxPrice = null;
        if (priceStr != null && !priceStr.isBlank()) {
            maxPrice = Double.parseDouble(priceStr);
        }

        if (text == null && maxPrice == null) {
            context.setVariable("workshops", workshopService.listAll());
        } else {
            context.setVariable("workshops", workshopService.search(text, maxPrice));
        }

        Springengine.process("listWorkshops.html", context, resp.getWriter());
    }
}