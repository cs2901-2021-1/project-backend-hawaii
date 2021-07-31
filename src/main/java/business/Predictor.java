package business;

import java.util.HashMap;
import java.util.Map;

public class Predictor {
    public static Map<String, Integer> predict(Map<String, Integer> matriculadosAnteriorMap, Map<String, Integer> aptosAnteriorMap, Map<String, Integer>aptosActualMap){
        Map<String, Integer> predictions= new HashMap<>();
        for(var item : matriculadosAnteriorMap.entrySet()){
            var codCurso = item.getKey();
            if(aptosActualMap.containsKey(codCurso)&& aptosAnteriorMap.containsKey(codCurso)) {
                predictions.put(codCurso, 1 + (item.getValue() * aptosActualMap.get(codCurso)) / aptosAnteriorMap.get(codCurso));
            }
            else{
                predictions.put(codCurso, 0);
            }
        }
        return predictions;
    }
}
