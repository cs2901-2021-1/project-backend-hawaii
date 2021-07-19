package business;

import data.entities.Prediction;
import data.repositories.PredictionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.sql.*;
import java.util.List;

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
        ResultSet resultSet = executeQuery("SELECT IDACTIVIDAD, DESCRIPCIONLARGA FROM CONFIGURACION.CON_ACTIVIDAD INNER JOIN CONFIGURACION.CON_CURSO ON CONFIGURACION.CON_ACTIVIDAD.CODACTIVIDAD = CONFIGURACION.CON_CURSO.CODCURSO WHERE CONFIGURACION.CON_ACTIVIDAD.isdeleted = 'N' &");
    }

    private ResultSet executeQuery(String sql) throws SQLException {

        Connection connection = DriverManager.getConnection(
                environment.getProperty("oracle.datasource.url"),
                environment.getProperty("oracle.datasource.username"),
                environment.getProperty("oracle.datasource.password"));
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        int count = 50;
        while(resultSet.next() && count<100){
            Prediction prediction = new Prediction(resultSet);
            predictionRepository.save(prediction);
            count++;
        }

        connection.close();
        return resultSet;
    }

}
