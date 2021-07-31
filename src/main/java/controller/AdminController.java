package controller;
import business.AuthorizationService;
import business.PredictionService;
import business.custom_exceptions.ConflictException;
import business.custom_exceptions.NotFoundException;
import business.custom_exceptions.UnauthorizedException;
import data.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static config.GlobalConstants.TYPE_ADMIN;

@RestController
@RequestMapping("/ti")
@CrossOrigin
public class AdminController {

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private PredictionService predictionService;

    @GetMapping("/auth")
    public void authenticate(HttpServletResponse response) throws IOException {
        response.sendRedirect("https://frontend-hawaii.vercel.app/#/panel");
    }

    @GetMapping
    public List<User> getViewers(@AuthenticationPrincipal OAuth2User user) throws UnauthorizedException{
        authorizationService.authorize(user,TYPE_ADMIN);
        return authorizationService.getViewers();
    }

    @GetMapping("/add")
    public User addViewer(@RequestParam String email, @AuthenticationPrincipal OAuth2User user) throws UnauthorizedException, ConflictException {
        authorizationService.authorize(user,TYPE_ADMIN);
        return authorizationService.addViewer(email);
    }

    @GetMapping("/del")
    public void deleteViewer(@RequestParam String email, @AuthenticationPrincipal OAuth2User user) throws UnauthorizedException, NotFoundException {
        authorizationService.authorize(user,TYPE_ADMIN);
        authorizationService.deleteViewer(email);
    }

    @GetMapping("/update")
    public void updatePredictions(@AuthenticationPrincipal OAuth2User user) throws UnauthorizedException, SQLException {
        authorizationService.authorize(user,TYPE_ADMIN);
        predictionService.updatePredictions();
    }

}
