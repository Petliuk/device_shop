package com.device.shop.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;


@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @ApiModelProperty(value = "User firstname",example = "firstname")
    private String name;
    @ApiModelProperty(value = "User lastname",example = "lastname")
    private String surname;
    @ApiModelProperty(value = "The user's phone number in the format +380 XX XXX XX XX , +380-XX-XXX-XX-XX", required = true, example = "+380 XX XXX XX XX , +380-XX-XXX-XX-XX")
    private String phone;

    @Column(unique = true)
    @ApiModelProperty(value = "User email", required = true, example = "user@gmail.com")
    private String email;
    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;

    public User() {
    }

    public User(String name, String surname, String phone, String email,  String password) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
