package com.zeta_training.restaurant_management_system.dto;

import com.zeta_training.restaurant_management_system.enumeration.Role;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Builder
public class RegisterDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
}
