package com.academic.projection.repository;

import com.academic.projection.data.entities.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface PredictionRepository extends JpaRepository<Prediction, String> {


}
