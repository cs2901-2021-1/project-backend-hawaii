package controller;
import business.AuthorizationService;
import business.PredictionService;
import business.custom_exceptions.ConflictException;
import business.custom_exceptions.NotFoundException;
import business.custom_exceptions.UnauthorizedException;
import data.dtos.ViewerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

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
    public List<ViewerDTO> getViewers(@AuthenticationPrincipal OAuth2User user) throws UnauthorizedException{
        authorizationService.authorizeTI(user);
        return authorizationService.getViewers();
    }

    @PostMapping("/add")
    public void addViewer(@RequestBody ViewerDTO viewerDTO, @AuthenticationPrincipal OAuth2User user) throws UnauthorizedException, ConflictException {
        authorizationService.authorizeTI(user);
        authorizationService.addViewer(viewerDTO);
    }

    @PostMapping("/del")
    public void deleteViewer(@RequestBody ViewerDTO viewerDTO, @AuthenticationPrincipal OAuth2User user) throws UnauthorizedException, NotFoundException {
        authorizationService.authorizeTI(user);
        authorizationService.deleteViewer(viewerDTO);
    }

    @GetMapping("/update")
    public void setCourses(@AuthenticationPrincipal OAuth2User user) throws UnauthorizedException{
        authorizationService.authorizeTI(user);
        //hola predictionService. setCourses()
    }

}
