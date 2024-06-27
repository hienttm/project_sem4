package com.t2207e.sem4.service;

import com.t2207e.sem4.entity.TeacherRegister;
import com.t2207e.sem4.repository.ITeacherRegisterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class TeacherRegisterService implements ITeacherRegisterService{
    private final ITeacherRegisterRepository teacherRegisterReponsitory;

    public TeacherRegisterService(ITeacherRegisterRepository teacherRegisterReponsitory) {
        this.teacherRegisterReponsitory = teacherRegisterReponsitory;
    }

    @Override
    public List<TeacherRegister> listTeacherRegisters() {
        return teacherRegisterReponsitory.findAll();
    }

    @Override
    public Optional<TeacherRegister> getTeacherRegisterById(Integer id) {
        return teacherRegisterReponsitory.findById(id);
    }

    @Override
    public void save(TeacherRegister teacherRegister) {
        teacherRegisterReponsitory.save(teacherRegister);
    }

    @Override
    public Optional<TeacherRegister> getTeacherRegisterByUser_UserId(int id) {
        return teacherRegisterReponsitory.getTeacherRegisterByUser_UserId(id);
    }
}
