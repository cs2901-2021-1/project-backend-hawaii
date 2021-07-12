package business;

import business.custom_exceptions.CustomNotFoundException;
import business.custom_exceptions.TooLongException;
import data.dtos.PredictionDTO;
import data.entities.Prediction;
import data.repositories.PredictionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Optional;


import javax.transaction.Transactional;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PredictionService {

    @Autowired
    private PredictionRepository predictionRepository;

    @Autowired
    private Environment environment;


    public List<Prediction> getAllPredictions() {
        try {
            ResultSet resultSet = executeQuery("SELECT IDACTIVIDAD, DESCRIPCIONLARGA FROM CONFIGURACION.CON_ACTIVIDAD INNER JOIN CONFIGURACION.CON_CURSO ON CONFIGURACION.CON_ACTIVIDAD.CODACTIVIDAD = CONFIGURACION.CON_CURSO.CODCURSO WHERE CONFIGURACION.CON_ACTIVIDAD.isdeleted = 'N' AND configuracion.con_actividad.codareafuncional=81");
            return predictionRepository.findAll();
        } catch (SQLException throwables) {
            return predictionRepository.findAll();
        }

    }

    public ResultSet executeQuery(String sql) throws SQLException {

        Connection connection = DriverManager.getConnection(
                environment.getProperty("oracle.datasource.url"),
                environment.getProperty("oracle.datasource.username"),
                environment.getProperty("oracle.datasource.password"));
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        int count =0;
        while(resultSet.next()&& count<20){
            Prediction prediction = new Prediction(resultSet);
            predictionRepository.save(prediction);
            count++;
        }

        connection.close();
        return resultSet;
    }
}
