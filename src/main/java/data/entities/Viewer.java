package data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "viewer")
public class Viewer {
    @Id
    @Column(name = "email",updatable = false)
    private String email;


    public Viewer(String email) {
        this.email = email;
    }

    public Viewer() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
