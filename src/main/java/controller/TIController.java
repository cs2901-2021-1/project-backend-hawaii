package controller;
import business.AuthorizationService;
import business.PredictionService;
import business.custom_exceptions.ConflictException;
import business.custom_exceptions.NotFoundException;
import business.custom_exceptions.UnauthorizedException;
import data.dtos.UserDTO;
import data.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static config.GlobalConstants.TYPE_ADMIN;

@RestController
@RequestMapping("/ti")
@CrossOrigin
public class TIController {

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private PredictionService predictionService;

    @GetMapping("/auth")
    public void authenticate(HttpServletResponse response) throws IOException {
        response.sendRedirect("https://cs.mrg.com.pe/app-sec02-group02/#/panel");
    }

    @GetMapping
    public List<User> getViewers(@AuthenticationPrincipal OAuth2User user) throws UnauthorizedException{
        authorizationService.authorize(user,TYPE_ADMIN);
        return authorizationService.getViewers();
    }

    @PostMapping("/add")
    public User addViewer(@RequestBody UserDTO userDTO, @AuthenticationPrincipal OAuth2User user) throws UnauthorizedException, ConflictException {
        authorizationService.authorize(user,TYPE_ADMIN);
        return authorizationService.addViewer(userDTO);
    }

    @PostMapping("/del")
    public void deleteViewer(@RequestBody UserDTO userDTO, @AuthenticationPrincipal OAuth2User user) throws UnauthorizedException, NotFoundException {
        authorizationService.authorize(user,TYPE_ADMIN);
        authorizationService.deleteViewer(userDTO);
    }

    @GetMapping("/update")
    public void setCourses(@AuthenticationPrincipal OAuth2User user) throws UnauthorizedException{
        authorizationService.authorize(user,TYPE_ADMIN);
        //hola predictionService. setCourses()
    }

}
