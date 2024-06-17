package com.t2207e.sem4.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleId;

    @NotNull(message = "roleName cannot be null")
    private String roleName;

    @NotNull(message = "status cannot be null")
    private int status;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date updateAt = new Date(System.currentTimeMillis());

    private String description;

    @OneToMany(mappedBy = "role")
    private List<UserRole> userRoles;
}
