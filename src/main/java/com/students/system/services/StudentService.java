package com.students.system.services;

import com.students.system.entities.Student;
import com.students.system.exceptions.CannotCreateEntityException;
import com.students.system.exceptions.StudentNotFoundException;
import com.students.system.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Component
public class StudentService {

    private StudentRepository studentRepository;
    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student save(Student s) throws CannotCreateEntityException {
        if (s != null)
            return studentRepository.save(s);
        throw new CannotCreateEntityException("Can not save student in database!");
    }

    public List<Student> getAll() throws StudentNotFoundException {
        // return studentRepository.findAllByIdOrderById();
        List<Student>studentList= studentRepository.findAll();
        if(studentList.size()>0)
            return studentList;
        else
            throw new StudentNotFoundException("No Students found");
    }
    public Student getStudent(Long studentId){
        return studentRepository.findById(studentId).orElseThrow(()->new EntityNotFoundException("No such element!"));
    }
}