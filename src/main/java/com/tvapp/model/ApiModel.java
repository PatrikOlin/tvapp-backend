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

    @Column(length = 50, name = "api_key")
    private String apiKey;

    @Column(length = 50, name = "user_key")
    private String userKey;

    public ApiModel() {
    }

    public ApiModel(String name, String token, String userName, String password, String apiKey, String userKey) {
        this.name = name;
        this.token = token;
        this.userName = userName;
        this.password = password;
        this.apiKey = apiKey;
        this.userKey = userKey;
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

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }
}
