package com.group8.appuser;

import jakarta.persistence.Entity;
import lombok.*;

@NoArgsConstructor
@Entity
public class Employee extends AppUser {
    public Employee(String firstName, String lastName, String email, String password) {
        super(firstName,lastName,email,password);
    }
}