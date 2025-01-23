package com.edj.teamoop.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false)
    private LocalDate createdAt;

    @Column(nullable = true)
    private LocalDate updatedAt;

    @PrePersist
    protected void onCreate() {
       if (this.createdAt == null) {
         this.createdAt = LocalDate.now();
       }
    }
}
