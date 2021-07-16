package controller;


import business.AuthorizationService;
import business.PredictionService;
import data.dtos.ViewerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/ti")
@CrossOrigin
public class TIController {

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private PredictionService predictionService;

    @PostMapping
    public void addViewer(@RequestBody ViewerDTO viewerDTO){
        authorizationService.addViewer(viewerDTO);
    }


    @GetMapping("/courses")
    public void setCourses() throws SQLException {
        predictionService.setCourses();
    }


}
