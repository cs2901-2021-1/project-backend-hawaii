package data.dtos;

public class ViewerDTO {
    private String email;

    public ViewerDTO(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
