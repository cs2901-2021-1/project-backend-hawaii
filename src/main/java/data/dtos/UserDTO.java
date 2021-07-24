package data.dtos;

import java.sql.Date;
import java.time.LocalDate;

public class UserDTO {
    private String email;

    public UserDTO(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
