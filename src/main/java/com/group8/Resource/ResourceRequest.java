package com.group8.Resource;

import com.group8.appuser.AppUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class ResourceRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "app_user_id")
    private AppUser appUser;

    @Column(nullable = false)
    private String resourceType;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDate requestDate;

    @Column(nullable = false)
    private String status = "Pending";
}
