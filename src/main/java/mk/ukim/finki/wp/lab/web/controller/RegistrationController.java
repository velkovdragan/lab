package mk.ukim.finki.wp.lab.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.wp.lab.model.Registration;
import mk.ukim.finki.wp.lab.service.RegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/registration")
    public String register(@RequestParam(required = false) String workshopTitle,
                           @RequestParam String participantName,
                           @RequestParam String participantEmail,
                           @RequestParam Integer numSeats,
                           @RequestHeader("User-Agent") String userAgent,
                           HttpServletRequest request,
                           Model model) {
        try {
            Registration registration = registrationService.register(
                    workshopTitle, participantName, participantEmail, numSeats);

            model.addAttribute("registration", registration);
            model.addAttribute("clientIp", request.getRemoteAddr());
            model.addAttribute("browser", userAgent);

            return "registrationConfirmation";
        } catch (IllegalArgumentException e) {
            String message = e.getMessage() != null ? e.getMessage() : "Invalid registration data";
            return "redirect:/workshops?errorMessage=" + URLEncoder.encode(message, StandardCharsets.UTF_8);
        }
    }
}
