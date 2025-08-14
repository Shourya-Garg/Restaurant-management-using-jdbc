package com.zeta_training.restaurant_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Data
public class LoginDTO {
    private String email;
    private String password;
}
