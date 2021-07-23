package data.dtos;

import java.time.LocalDate;
import java.util.Calendar;

public class UserDTO {
    private String email;
    private LocalDate dateInsert;

    public UserDTO(String email, LocalDate dateInsert) {
        this.email = email;
        this.dateInsert = dateInsert;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateInsert() {
        return dateInsert;
    }

    public void setDateInsert(LocalDate dateInsert) {
        this.dateInsert = dateInsert;
    }
}
