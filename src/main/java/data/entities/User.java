package data.entities;


import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

import static config.GlobalConstants.TYPE_USER_LENGTH;

@Entity
@Table(name = "users")
public class User{
    @Id
    @Column(name = "email",updatable = false)
    private String email;

    @Column(name = "type", length = TYPE_USER_LENGTH)
    private char type;

    @Column(name = "dateInsert")
    private Date dateInsert;

    public User(String email, char type) {
        this.dateInsert = new Date(LocalDate.now().getYear(),LocalDate.now().getMonthValue(),LocalDate.now().getMonthValue());
        this.type = type;
        this.email = email;
    }

    public User(String email, Date date){
        this.dateInsert = date;
        this.email = email;
    }

    public User() {}

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public Date getDateInsert() {
        return dateInsert;
    }

    public void setDateInsert(Date dateInsert) {
        this.dateInsert = dateInsert;
    }
}