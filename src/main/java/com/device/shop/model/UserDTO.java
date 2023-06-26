package com.device.shop.model;

import com.device.shop.entity.ERole;
import com.device.shop.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String name;
    private String surname;
    private String phone;
    private String email;
    private String password;
    private Set<ERole> roles;

}
