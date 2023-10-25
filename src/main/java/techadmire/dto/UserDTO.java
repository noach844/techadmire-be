package techadmire.dto;

public class UserDTO {
    private String username;
    private String firstname;
    private String lastname;
    private String password;

    public UserDTO() {
    }

    public UserDTO(String username, String firstname, String lastname, String password) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
