package com.example.TailorMe.API.Models;


import com.example.TailorMe.API.enumTypes.Roles;

public class userDTO {

    private Long userId;

    private Roles userRole;

    private String firstName;

    private String lastName;
    private String email;
    private String jwt;

    public userDTO(user loginUser,String jwtToken) {
        this.userId = loginUser.getUserId();
        this.userRole = loginUser.getUserRole();
        this.firstName = loginUser.getFirstName();
        this.lastName = loginUser.getLastName();
        this.email = loginUser.getEmail();
        this.jwt = jwtToken;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Roles getUserRole() {
        return userRole;
    }

    public void setUserRole(Roles userRole) {
        this.userRole = userRole;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
