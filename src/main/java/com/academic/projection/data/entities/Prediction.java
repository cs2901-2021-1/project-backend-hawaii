package com.academic.projection.data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "prediction")
public class Prediction {
    @Id
    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "nstudent")
    private int nStudent;

    @Column(name = "error")
    private double error;

    public Prediction() { }

    public Prediction(String code, String name, int nStudent, double error) {
        this.code = code;
        this.name = name;
        this.nStudent = nStudent;
        this.error = error;
    }
}
