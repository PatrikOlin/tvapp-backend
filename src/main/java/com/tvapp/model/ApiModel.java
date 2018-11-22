package com.tvapp.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "api")
public class ApiModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(length = 50)
    private String name;

    @Column(length = 50)
    private String token;

    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Column(name = "user_name", length = 50)
    private String userName;

    @Column(length = 50)
    private String password;

    public ApiModel() {
    }

    public ApiModel(String name, String token) {
        this.name = name;
        this.token = token;
    }

    public ApiModel(String name, String token, String userName, String password) {
        this.name = name;
        this.token = token;
        this.userName = userName;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
