package com.tutorial.tutorialweblux.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginDto {
    private String username;
    private String password;
}
