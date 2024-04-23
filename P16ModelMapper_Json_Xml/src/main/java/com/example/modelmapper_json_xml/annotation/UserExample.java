package com.example.modelmapper_json_xml.annotation;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

@Entity
@Table(name = "users")
public class UserExample {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Password(minLength = 6,
            maxLength = 50,
            requireLowercase = true,
            requireUppercase = true,
            requireDigit = true,
            requireSpecialSymbol = true,
            message = "Invalid password"
    )

    private String password;

    @Column(nullable = false)
    @Email(message = "Invalid Email")
    private String email;
}

