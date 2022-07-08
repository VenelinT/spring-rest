package com.students.system.services;

import com.students.system.entities.Course;
import com.students.system.entities.Grade;
import com.students.system.entities.Student;
import com.students.system.exceptions.NoGradesException;
import com.students.system.repositories.GradeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GradeServiceTest {
    GradeRepository gradeRepository= mock(GradeRepository.class);
    GradeService gradeService= new GradeService(gradeRepository);
    Grade mockGrade;
    Student student = new Student("Venelin", 22);
    Course course= new Course("Math",23);

    @BeforeEach
    void setUp(){
        mockGrade = new Grade(student,course,5.2);

    }
    @Test
    void save() {
        when(gradeRepository.save(any(Grade.class))).thenReturn(mockGrade);
        assertSame(mockGrade,gradeService.save(mockGrade));


    }

    @Test
    void getGradesWithCorrectParams() throws NoGradesException {
        when(gradeRepository.getGradesByCourse(anyLong())).thenReturn(List.of(5.2,4.8,3.3,2.2,6.0));
        assertIterableEquals(List.of(5.2,4.8,3.3,2.2,6.0), gradeService.getGrades(1L));
    }

    @Test
    void getGradeOfStudentWithCorrectParams() throws NoGradesException {
        when(gradeRepository.getGradesByStudent(anyLong())).thenReturn(List.of(5.2,4.8,3.3,2.2,6.0));
        assertIterableEquals(List.of(5.2,4.8,3.3,2.2,6.0),gradeService.getGradeOfStudent(1L));
    }

    @Test
    void getGradeOfStudentWithIncorrectParamsShouldThrowException(){
        when(gradeRepository.getGradesByStudent(anyLong())).thenReturn(List.of());
        assertThrows(NoGradesException.class, () ->gradeService.getGradeOfStudent(1L));
    }

    @Test
    void getGradeOfCourseWithInCorrectParamsShouldThrowException(){
        when(gradeRepository.getGradesByCourse(anyLong())).thenReturn(List.of());
        assertThrows(NoGradesException.class, ()->gradeService.getGrades(1L)); 
    }


}