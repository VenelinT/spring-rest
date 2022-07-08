package com.students.system.services;

import com.students.system.entities.Teacher;
import com.students.system.repositories.TeacherRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TeacherServiceTest {

    TeacherRepository teacherRepository= mock(TeacherRepository.class);
    TeacherService teacherService = new TeacherService(teacherRepository);
    Teacher teacher= new Teacher("Dimitar", "PHD");



    @Test
    void save() {
        when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher);
        assertSame(teacher, teacherService.save(teacher));
    }

    @Test
    void getTeacher() {
        when(teacherRepository.findById(anyLong())).thenReturn(Optional.ofNullable(teacher));
        when(teacherRepository.findAll()).thenReturn(List.of(teacher));
        assertSame(teacher,teacherService.getTeacher(1L));
    }
    void getTeacherWithInvalidIdShouldThrowException(){
        when(teacherRepository.findById(anyLong())).thenReturn(Optional.ofNullable(teacher));
        when(teacherRepository.findAll()).thenReturn(List.of(teacher));
        assertThrows(RuntimeException.class, () -> teacherService.getTeacher(200000L));
    }

    @Test
    void getAll() {
        when(teacherRepository.findAll()).thenReturn(List.of(teacher,teacher));
        assertTrue(2==teacherService.getAll().size());
    }
}