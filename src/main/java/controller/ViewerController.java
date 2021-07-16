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

@RestController
@RequestMapping("/viewers")
@CrossOrigin
public class ViewerController {

    @Autowired
    private PredictionService predictionService;

    @GetMapping
    public List<Prediction> getAllPrediction(){
        return predictionService.getAllPredictions();
    }





}
