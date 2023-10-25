package techadmire.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

@Entity
@Table(name = "users")
public class User{

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    public String username;

    @Column(nullable = false)
    public String firstname;

    @Column(nullable = false)
    public String lastname;

    @JsonIgnore
    @Column(nullable = false)
    public String password;

    @JsonIgnore
    @Column
    private String salt;

    public User() {}

    public User(String username, String firstname, String lastname, String password) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.salt = UUID.randomUUID().toString();
        this.password = new BCryptPasswordEncoder().encode(password + this.salt);
    }



    @JsonIgnore
    public String getSalt() {
        return salt;
    }

    @JsonProperty
    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getUsername() {
        return firstname;
    }

    public void setUsername(String firstname) {
        this.firstname = firstname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

}