package com.zakafikry.school.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Teachers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String birthDate;
    private String hiredDate;
    private String name;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Users user;
}
