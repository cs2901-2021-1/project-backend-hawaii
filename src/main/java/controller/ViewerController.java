package controller;

import business.PredictionService;
import data.dtos.PredictionDTO;
import data.entities.Prediction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
