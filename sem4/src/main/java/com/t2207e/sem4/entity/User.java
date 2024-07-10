package com.t2207e.sem4.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    @NotNull(message = "Username cannot be null")
    private String username;
    @NotNull(message = "Password cannot be null")
    @Size(min = 6, message = "Min length of Password is 6")
    private String password;
    @NotNull(message = "Fullname cannot be null")
    private String fullname;
    @Past(message = "Birthday must be in the past")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Birthday cannot be null")
    private Date birthday;
    @NotNull(message = "Address cannot be null")
    private String address;
    @NotNull(message = "Phone Number cannot be null")
    @Pattern(regexp = "0[0-9]{9}", message = "phoneNumber must only have numeric characters and must have 10 characters")
    private String phoneNumber;
    @NotNull(message = "Email cannot be null")
    @Email
    private String email;
    @NotNull(message = "Image cannot be null")

    private String image = "/home/images/NoImage.png";
    @NotNull(message = "Gender cannot be null")
    private Integer gender;
    @NotNull(message = "Status cannot be null")
    private Integer status = 0;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @NotNull(message = "createAt cannot be null")
    private Date createAt = new Date(System.currentTimeMillis());
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @NotNull(message = "updateAt cannot be null")
    private Date updateAt = new Date(System.currentTimeMillis());

    private String description = "User";

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @OneToMany(mappedBy = "user")
    private List<UserRole> userRoles;

}
