package com.t2207e.sem4.service;

import com.t2207e.sem4.entity.TeacherRegister;

import java.util.List;
import java.util.Optional;

public interface ITeacherRegisterService {
    List<TeacherRegister> listTeacherRegisters();
    Optional<TeacherRegister> getTeacherRegisterById(Integer id);
    void save(TeacherRegister teacherRegister);
    Optional<TeacherRegister> getTeacherRegisterByUser_UserId(int id);

}
