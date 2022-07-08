package com.students.system.services;

import com.students.system.entities.Student;
import com.students.system.exceptions.CannotCreateEntityException;
import com.students.system.exceptions.StudentNotFoundException;
import com.students.system.repositories.StudentRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StudentServiceTest {

    StudentRepository studentRepository= mock(StudentRepository.class);
    StudentService studentService= new StudentService(studentRepository);
    Student student = new Student("Venelin",20);
    @Test
    void save() throws CannotCreateEntityException {
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        assertSame(student,studentService.save(student));
    }

    @Test
    void getAll() throws StudentNotFoundException {
        when(studentRepository.findAll()).thenReturn(List.of(student));
        assertIterableEquals(List.of(student),studentService.getAll());
    }

    @Test
    void getStudent() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.ofNullable(student));
        when(studentRepository.findAll()).thenReturn(List.of(student));
        assertSame(student,studentService.getStudent(1L));
    }
}