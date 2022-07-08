package com.students.system.services;

import com.students.system.entities.Teacher;
import com.students.system.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Component
public class TeacherService {


    private TeacherRepository teacherRepository;
    @Autowired
    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public Teacher save(Teacher t) {
        return teacherRepository.save(t);

    }

    public Teacher getTeacher(Long teacherId) {
        return teacherRepository.findById(teacherId).orElseThrow(()->new EntityNotFoundException("No such element!"));
    }

    public List<Teacher> getAll() {
        // return teacherRepository.findAllByIdOrderById();
        return teacherRepository.findAll();
    }
}