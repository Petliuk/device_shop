package com.device.shop.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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



}
