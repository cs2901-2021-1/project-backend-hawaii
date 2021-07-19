package business;

import data.entities.Prediction;
import data.repositories.PredictionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.sql.*;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class PredictionService {

    @Autowired
    private PredictionRepository predictionRepository;

    @Autowired
    private Environment environment;


    public List<Prediction> getAllPredictions() {
        return predictionRepository.findAll();
    }

    public void setCourses() throws SQLException {
        executeQuery("SELECT IDACTIVIDAD, DESCRIPCIONLARGA FROM CONFIGURACION.CON_ACTIVIDAD INNER JOIN CONFIGURACION.CON_CURSO ON CONFIGURACION.CON_ACTIVIDAD.CODACTIVIDAD = CONFIGURACION.CON_CURSO.CODCURSO WHERE CONFIGURACION.CON_ACTIVIDAD.isdeleted = 'N' &");
    }

    private void executeQuery(String sql) throws SQLException {
        try (var connection = DriverManager.getConnection(Objects.requireNonNull(environment.getProperty("oracle.datasource.url")),
                environment.getProperty("oracle.datasource.username"),
                environment.getProperty("oracle.datasource.password")); var statement = connection.createStatement()) {
            var resultSet = statement.executeQuery(sql);
            var count = 0;
            while (resultSet.next() && count < 1) {
                var prediction = new Prediction(resultSet);
                predictionRepository.save(prediction);
                count++;
            }
        }
    }

}
