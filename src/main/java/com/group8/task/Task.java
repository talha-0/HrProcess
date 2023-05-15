package com.group8.task;

import com.group8.appuser.AppUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "app_user_id")
    private AppUser appUser;

    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String status = "Pending";

    @Column(nullable = false)
    private LocalDate dueDate;
}
