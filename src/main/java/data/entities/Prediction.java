package data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import static config.GlobalConstants.CODE_CURSO_LENGTH;


@Entity
@Table(name = "courses")
public class Prediction {
    @Id
    @Column(name = "codecourse", length = CODE_CURSO_LENGTH)
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "nstudent")
    private int nStudent;

    @Column(name = "error")
    private double error;

    public Prediction() {
    }

    public Prediction(String code, String name, int nStudent, double error) {
        this.code = code;
        this.name = name;
        this.nStudent = nStudent;
        this.error = error;
    }

    /*
    wow public Prediction(ResultSet resultSet)  SQLException
        wow this.code = resultSet.getString("IDACTIVIDAD")
        wow this.name = resultSet.getString("DESCRIPCIONLARGA")
        wow this.nStudent = 0
        wow this.error = 0
    */

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getnStudent() {
        return nStudent;
    }

    public void setnStudent(int nStudent) {
        this.nStudent = nStudent;
    }

    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error = error;
    }
}
