package com.cohort22.data.models;


import com.cohort22.data.enums.Roles;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;

    private Roles role;

}
