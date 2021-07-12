package data.repositories;

import data.entities.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PredictionRepository extends JpaRepository<Prediction, String> {


}

