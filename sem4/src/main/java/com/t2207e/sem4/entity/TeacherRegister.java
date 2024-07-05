package com.t2207e.sem4.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "teacher_registers")
@Getter
@Setter
@NoArgsConstructor

public class TeacherRegister {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "bankNumber cannot null")
    private String bankNumber="Not Bank Number";
    @NotNull(message = "bankName cannot null")
    private String bankName="BANK";

    private String git;

    private String website;
    @Column(columnDefinition = "TEXT")
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date createAt = new Date(System.currentTimeMillis());

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date updateAt = new Date(System.currentTimeMillis());

    private Integer status=2;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
