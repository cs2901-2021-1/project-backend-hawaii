package controller;

import business.AuthorizationService;
import business.PredictionService;
import business.custom_exceptions.CustomNotFoundException;
import data.dtos.PredictionDTO;
import data.entities.Prediction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientId;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/viewers")
@CrossOrigin
public class ViewerController {

    @Autowired
    private PredictionService predictionService;
    
    @GetMapping("/google/callback")
     public void googleCallback(HttpServletResponse response) throws IOException {
         response.sendRedirect("https://cs.mrg.com.pe/app-sec02-group02/#/");
     }

    @GetMapping
    public List<Prediction> getAllPrediction() {
        return predictionService.getAllPredictions();
    }
    
    
}
