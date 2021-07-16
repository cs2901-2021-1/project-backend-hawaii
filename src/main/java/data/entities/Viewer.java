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

    @Column(name = "fullName", nullable = false)
    private String fullName;

    public Viewer(String email, String fullName) {
        this.email = email;
        this.fullName = fullName;
    }

    public Viewer() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
