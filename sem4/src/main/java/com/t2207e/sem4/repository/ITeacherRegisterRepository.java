package com.t2207e.sem4.repository;

import com.t2207e.sem4.entity.TeacherRegister;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ITeacherRegisterRepository extends JpaRepository<TeacherRegister,Integer> {
    Optional<TeacherRegister> getTeacherRegisterByUser_UserId(int userId);
}
