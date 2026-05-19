package com.manojs.hospitalmanagement.user.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpResponseDto {

    private Long id;
    private String fullName;
    private String email;

}