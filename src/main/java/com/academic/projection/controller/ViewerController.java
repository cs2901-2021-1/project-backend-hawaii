package com.academic.projection.controller;

import com.academic.projection.data.entities.Prediction;
import com.academic.projection.service.PredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/viewers")
@CrossOrigin
public class ViewerController {
    @Autowired
    private PredictionService predictionService;

    @GetMapping("/view")
    @ResponseBody
    public List<Prediction> getAllPrediction(){
        return predictionService.getAllPredictions();
    }
}
