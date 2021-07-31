package business;

import data.entities.Prediction;
import data.repositories.PredictionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.sql.*;
import java.util.*;

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



    @Async
    @Transactional
    public void updatePredictions() throws SQLException {
        try (var connection = DriverManager.getConnection(Objects.requireNonNull(environment.getProperty("oracle.datasource.url")),environment.getProperty("oracle.datasource.username"),environment.getProperty("oracle.datasource.password"));var statement = connection.createStatement()){
            var periodos = statement.executeQuery("SELECT DISTINCT PER.DESCRIPCIONLARGA FROM PROGRAMACION.PRO_PERIODO  PER WHERE PER.ISDELETED = 'N' AND PER.CODPROGRAMA=1 AND PER.DESCRIPCIONLARGA<>'NI-2016-0' ORDER BY PER.DESCRIPCIONLARGA DESC;");
            List<String> periodosList = new ArrayList<>();
            while (periodos.next()) {
                periodosList.add(periodos.getString("DESCRIPCIONLARGA"));
            }
            var exceptionPeriodo = periodosList.indexOf("2021-0");
            periodosList.set(exceptionPeriodo,"2021 - 1");
            periodosList.set(exceptionPeriodo+1,"2021-0");
            var periodoActual = periodosList.get(1);
            var periodoAnterior = periodosList.get(4);

            Map<String, Integer> matriculadosMap = new HashMap<>();
            var matriculados = statement.executeQuery("SELECT ACT.IDACTIVIDAD AS \"CODCURSO\",\n" +
                    "        COUNT( GA.IDALUMNO) AS \"CODCANTIDAD\"\n" +
                    "FROM ACADEMICO.ACA_ALUMNO_MATRICULA_CURSO AMC\n" +
                    "INNER JOIN ACADEMICO.ACA_ALUMNO_MATRICULA AMT ON AMT.CODALUMNOMATRICULA = AMC.CODALUMNOMATRICULA AND AMT.ISDELETED = 'N'\n" +
                    "INNER JOIN PROGRAMACION.PRO_CURSO_SECCION CSEC ON CSEC.CODCURSOSECCION = AMC.CODCURSOSECCION AND CSEC.ISDELETED = 'N'\n" +
                    "INNER JOIN ACADEMICO.ACA_ALUMNO_MALLA AM ON AM.CODALUMNOMALLA = AMT.CODALUMNOMALLA AND AM.ISDELETED = 'N'\n" +
                    "INNER JOIN CONFIGURACION.CON_MALLA MA ON MA.CODMALLA = AM.CODMALLA  AND MA.ISDELETED = 'N' --AND (MA.CODPRODUCTO = :IDACTIVIDAD OR :IDACTIVIDAD = 0) \n" +
                    "INNER JOIN CONFIGURACION.CON_CURSO_VERSION CV ON CV.CODCURSOVERSION = AMC.CODCURSOVERSION AND CV.ISDELETED = 'N' --AND (CV.CODCURSO = :CODACTIVIDAD  or :CODACTIVIDAD  = 0)\n" +
                    "INNER JOIN CONFIGURACION.CON_PRODUCTO PROD ON PROD.CODPRODUCTO = MA.CODPRODUCTO AND PROD.ISDELETED = 'N' AND PROD.CODPROGRAMA=1\n" +
                    "INNER JOIN PROGRAMACION.PRO_PERIODORANGO PR ON PR.CODPERIODORANGO = AMT.CODPERIODORANGO AND PR.ISDELETED = 'N'\n" +
                    "INNER JOIN PROGRAMACION.PRO_PERIODO PER ON PER.CODPERIODO = PR.CODPERIODO AND PER.ISDELETED = 'N'\n" +
                    "INNER JOIN GENERAL.GEN_MAESTRO_TABLAS_DETALLE MTD_SEC ON MTD_SEC.CODMAESTROTABLASDETALLE = CSEC.CODSECCIONMTD AND MTD_SEC.ISDELETED = 'N'\n" +
                    "INNER JOIN CONFIGURACION.CON_ACTIVIDAD ACT ON ACT.CODACTIVIDAD = CV.CODCURSO AND ACT.ISDELETED = 'N' \n" +
                    "INNER JOIN GENERAL.GEN_AREA_FUNCIONAL AF ON AF.CODAREAFUNCIONAL = ACT.CODAREAFUNCIONAL AND AF.ISDELETED='N' --AND (AF.CODAREAFUNCIONAL = :CODAREAFUNCIONAL OR :CODAREAFUNCIONAL = 0)\n" +
                    "INNER JOIN GENERAL.GEN_ALUMNO GA ON GA.CODALUMNO = AM.CODALUMNO AND GA.ISDELETED = 'N'\n" +
                    "INNER JOIN GENERAL.GEN_PERSONA PERS ON PERS.CODPERSONA = GA.CODALUMNO AND PERS.ISDELETED = 'N'\n" +
                    "WHERE AMC.ISDELETED = 'N' AND per.descripcionlarga="+ periodoAnterior+
                    "\nGROUP BY PER.DESCRIPCIONLARGA, ACT.IDACTIVIDAD ;");

            while(matriculados.next()){
                matriculadosMap.put(matriculados.getString("CODCURSO"),matriculados.getInt("CODCANTIDAD"));
            }

            Map<String, Integer> aptosAnteriorMap = new HashMap<>();
            var aptosAnterior = statement.executeQuery("SELECT ca.idactividad AS \"CURSO\", count(ca.idactividad) AS \"CANTIDAD\"\n" +
                    "FROM ACADEMICO.aca_alumno_malla am     \n" +
                    "INNER JOIN GENERAL.gen_alumno ga ON am.codalumno=ga.codalumno AND ga.isdeleted='N'\n" +
                    "INNER JOIN ACADEMICO.aca_alumno_matricula amat ON amat.codalumnomalla=am.codalumnomalla AND amat.isdeleted='N' \n" +
                    "INNER JOIN ACADEMICO.aca_alumno_matricula_demanda amd ON amd.codalumnomatricula = amat.codalumnomatricula AND amd.isdeleted='N'\n" +
                    "INNER JOIN CONFIGURACION.con_actividad ca ON amd.codcurso=ca.codactividad AND ca.isdeleted='N'\n" +
                    "INNER JOIN PROGRAMACION.pro_periodo pr ON amat.codperiodorango=pr.codperiodo AND pr.isdeleted='N'\n" +
                    "WHERE am.isdeleted='N' AND am.estado='A' AND  pr.descripcionlarga=" + periodoAnterior+
                    "\nGROUP BY  ca.idactividad , ca.descripcionlarga, pr.descripcionlarga\n" +
                    ";");
            while(aptosAnterior.next()){
                aptosAnteriorMap.put(aptosAnterior.getString("CURSO"),aptosAnterior.getInt("CANTIDAD"));
            }

            Map<String, Integer> aptosActualMap = new HashMap<>();
            var aptosActual = statement.executeQuery("SELECT ca.idactividad AS \"CURSO\", count(ca.idactividad) AS \"CANTIDAD\"\n" +
                    "FROM ACADEMICO.aca_alumno_malla am     \n" +
                    "INNER JOIN GENERAL.gen_alumno ga ON am.codalumno=ga.codalumno AND ga.isdeleted='N'\n" +
                    "INNER JOIN ACADEMICO.aca_alumno_matricula amat ON amat.codalumnomalla=am.codalumnomalla AND amat.isdeleted='N' \n" +
                    "INNER JOIN ACADEMICO.aca_alumno_matricula_demanda amd ON amd.codalumnomatricula = amat.codalumnomatricula AND amd.isdeleted='N'\n" +
                    "INNER JOIN CONFIGURACION.con_actividad ca ON amd.codcurso=ca.codactividad AND ca.isdeleted='N'\n" +
                    "INNER JOIN PROGRAMACION.pro_periodo pr ON amat.codperiodorango=pr.codperiodo AND pr.isdeleted='N'\n" +
                    "WHERE am.isdeleted='N' AND am.estado='A' AND  pr.descripcionlarga=" + periodoActual+
                    "\nGROUP BY  ca.idactividad , ca.descripcionlarga, pr.descripcionlarga\n" +
                    ";");
            while(aptosActual.next()){
                aptosActualMap.put(aptosActual.getString("CURSO"),aptosActual.getInt("CANTIDAD"));
            }

            Map<String, String> cursosMap = new HashMap<>();
            var cursos = statement.executeQuery("\n" +
                    "SELECT ca.idactividad, ca.descripcionlarga\n" +
                    "FROM  CONFIGURACION.CON_ACTIVIDAD ca\n" +
                    "WHERE ca.isdeleted='N'");
            while(cursos.next()){
                cursosMap.put(cursos.getString(1), cursos.getString(2));
            }

            var predictions = Predictor.predict(matriculadosMap,aptosAnteriorMap,aptosActualMap);
            for(var item:predictions.entrySet()){
                predictionRepository.save(new Prediction(item.getKey(), cursosMap.get(item.getKey()),item.getValue(),0));
            }
        }
    }
}
