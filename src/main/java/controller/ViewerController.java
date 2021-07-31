package controller;

import business.AuthorizationService;
import business.PredictionService;
import business.custom_exceptions.UnauthorizedException;
import data.entities.Prediction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import static config.GlobalConstants.TYPE_VIEWER;

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
         response.sendRedirect("https://frontend-hawaii.vercel.app/#/proyecciones");
     }

    @GetMapping
    public List<Prediction> getAllPrediction(@AuthenticationPrincipal OAuth2User user) throws UnauthorizedException {
        authorizationService.authorize(user, TYPE_VIEWER);
        return predictionService.getAllPredictions();
    }

}
