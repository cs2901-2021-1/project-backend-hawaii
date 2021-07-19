package data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "TI")
public class TI {
    @Id
    @Column(name = "email",updatable = false)
    private String email;

    public TI(String email) {
        this.email = email;
    }

    public TI() {}

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
