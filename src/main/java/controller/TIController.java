package controller;


import business.AuthorizationService;
import business.PredictionService;
import data.dtos.ViewerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.sql.SQLException;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/ti")
@CrossOrigin
public class TIController {
    
    @Autowired
    private PredictionService predictionService;
    @Autowired
    private AuthorizationService authorizationService;

    
    @GetMapping("/auth")
     public void authenticate(HttpServletResponse response) throws IOException {
         response.sendRedirect("https://cs.mrg.com.pe/app-sec02-group02/#/panel");
     }

    @PostMapping
    public void addViewer(@AuthenticationPrincipal OAuth2User user, @RequestBody ViewerDTO viewerDTO){
        authorizationService.addViewer(viewerDTO);
    }


    @GetMapping("/courses")
    public void setCourses() throws SQLException {
        predictionService.setCourses();
    }


}
