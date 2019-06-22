package com.liseh.ms.model.dto;

import lombok.Data;

@Data
public class RegistrationDto {
    private String email;
    private String name;
    private String phoneNumber;
    private String password;
}
