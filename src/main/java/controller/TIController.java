package controller;


import business.AuthorizationService;
import business.PredictionService;
import business.custom_exceptions.ConflictException;
import business.custom_exceptions.NotFoundException;
import business.custom_exceptions.UnauthorizedException;
import data.dtos.ViewerDTO;
import data.entities.Viewer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

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

    @PostMapping("/add")
    public Viewer addViewer(@RequestBody ViewerDTO viewerDTO, @AuthenticationPrincipal OAuth2User user) throws UnauthorizedException, ConflictException {
        authorizationService.authorizeTI(user);
        return authorizationService.addViewer(viewerDTO);
    }

    @PostMapping("/delete")
    public void deleteViewer(@RequestBody ViewerDTO viewerDTO, @AuthenticationPrincipal OAuth2User user) throws UnauthorizedException, NotFoundException {
        authorizationService.authorizeTI(user);
        authorizationService.deleteViewer(viewerDTO);
    }

    @GetMapping("/courses")
    public void setCourses(@AuthenticationPrincipal OAuth2User user) throws SQLException , UnauthorizedException{
        authorizationService.authorizeTI(user);
        predictionService.setCourses();
    }



}
