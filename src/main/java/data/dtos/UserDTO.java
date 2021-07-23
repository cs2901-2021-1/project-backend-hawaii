package data.dtos;

import java.sql.Date;
import java.time.LocalDate;

public class UserDTO {
    private String email;
    private Date dateInsert;

    public UserDTO(String email, Date dateInsert) {
        this.email = email;
        this.dateInsert = dateInsert;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateInsert() {
        return dateInsert;
    }

    public void setDateInsert(Date dateInsert) {
        this.dateInsert = dateInsert;
    }
}
