package com.tvapp.rest;


import org.springframework.security.crypto.bcrypt.BCrypt;
import javax.persistence.*;

@Entity
@Table(name = "user")
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private int id;

    @Column(name= "user_password")
    private String password;

    @Column(name = "user_email")
    private String email;


    public UserDetails() {

    }

    public UserDetails(String email, String password) {
        this.setEmail(email);
        this.setPassword(password);
    }

    public UserDetails(int id, String email, String password) {
        this.setId(id);
        this.setEmail(email);
        this.setPassword(password);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
