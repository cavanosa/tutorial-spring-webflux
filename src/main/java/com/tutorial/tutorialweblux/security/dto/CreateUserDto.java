package com.tutorial.tutorialweblux.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateUserDto {
    private String username;
    private String email;
    private String password;
}
