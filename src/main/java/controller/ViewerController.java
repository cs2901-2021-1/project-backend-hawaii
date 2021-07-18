package controller;

import business.AuthorizationService;
import business.PredictionService;
import business.custom_exceptions.CustomNotFoundException;
import data.dtos.PredictionDTO;
import data.entities.Prediction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientId;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Logger;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Collections;

@RestController
@RequestMapping("/viewers")
@CrossOrigin
public class ViewerController {

    @Autowired
    private PredictionService predictionService;
    @Autowired
    private AuthorizationService authorizationService ;
    
    @GetMapping("/auth")
     public void authenticate(HttpServletResponse response) throws IOException {
         response.sendRedirect("https://cs.mrg.com.pe/app-sec02-group02/#/proyecciones");
     }
    /*
    @GetMapping
    public ResponseEntity<?> getAllPrediction(Principal principal) {
        if (principal == null ) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "unauthorized"));
        }
        return ResponseEntity.ok(predictionService.getAllPredictions());
    }
    */


    @GetMapping
    public ResponseEntity<?> getAllPrediction(@AuthenticationPrincipal OAuth2User user) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method getAllPredictions = PredictionService.class.getMethod("getAllPredictions");
        return authorizationService.executeIfViewer(user, predictionService, getAllPredictions);
    }

}
