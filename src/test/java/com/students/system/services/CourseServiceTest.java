package com.students.system.services;


import com.students.system.entities.Course;
import com.students.system.entities.Student;
import com.students.system.entities.Teacher;
import com.students.system.exceptions.CannotCreateEntityException;
import com.students.system.exceptions.EmptyCourseException;
import com.students.system.exceptions.StudentNotInCourseException;
import com.students.system.repositories.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CourseServiceTest {

    CourseRepository courseRepository = mock(CourseRepository.class);
    Course mockCourse;
    CourseService courseService = new CourseService(courseRepository);


    @BeforeEach
    void setUp() {

        mockCourse = new Course();
        mockCourse.setName("Math");
        mockCourse.setTeacher(new Teacher("Dimitar", "PHD"));


    }


    @Test
    void save() throws CannotCreateEntityException {
        when(courseRepository.save(any(Course.class))).thenReturn(mockCourse);
        Course testCourse = courseService.save(mockCourse);
        assertEquals(mockCourse, testCourse);
        if(5==5)
            System.out.println("dasd");
    }

    @Test
    void getCourse() {
        when(courseRepository.findById(1L)).thenReturn(Optional.ofNullable(mockCourse));
        when(courseRepository.findAll()).thenReturn(List.of(mockCourse));
        assertSame(mockCourse, courseService.getCourse(1L));
    }

    @Test
    void getAll() throws EmptyCourseException {
        when(courseRepository.findAll()).thenReturn(List.of(mockCourse));
        assertIterableEquals(List.of(mockCourse), courseService.getAll());
    }

    @Test
    void getCourseIfStudentIsEnrolledWithCorrectParams() throws StudentNotInCourseException {
        Student student = new Student("Venelin", 21);
        mockCourse.getStudentsInCourse().add(student);
        when(courseRepository.findById(anyLong())).thenReturn(Optional.ofNullable(mockCourse));
        assertEquals(Optional.of(mockCourse),courseService.getCourseIfStudentIsEnrolled(1L,student));
    }

    @Test
    void getCourseIfStudentIsEnrolledWithIncorrectParamsShouldThrowException() {
        Student student = new Student("Venelin", 21);
        mockCourse.getStudentsInCourse().add(student);
        when(courseRepository.findById(anyLong())).thenReturn(Optional.ofNullable(mockCourse));
        assertThrows(StudentNotInCourseException.class,()->courseService.getCourseIfStudentIsEnrolled(1L,new Student()));

    }

    @Test
    void getAllWithEmptyListShouldThrowException(){
        when(courseRepository.findAll()).thenReturn(List.of());
        assertThrows(EmptyCourseException.class, ()->courseService.getAll());
    }

    @Test
    void getAllCoursesWithCorrectParams() throws EmptyCourseException {
        mockCourse.getStudentsInCourse().add(new Student("Venelin",23));
        when(courseRepository.findAll()).thenReturn(List.of(mockCourse));
        Map<String, Set<Student>> testMap = new HashMap<>();
        testMap.put(mockCourse.getName(),mockCourse.getStudentsInCourse());
        assertEquals(testMap,courseService.getAllCoursesAndStudents());
    }

    @Test
    void getAllCoursesWithIncorrectParamsShouldThrowException() {
        mockCourse.getStudentsInCourse().add(new Student("Venelin",23));
        when(courseRepository.findAll()).thenReturn(List.of());
        Map<String, Set<Student>> testMap = new HashMap<>();
        testMap.put(mockCourse.getName(),mockCourse.getStudentsInCourse());
        assertThrows(EmptyCourseException.class, ()->courseService.getAllCoursesAndStudents());
    }

}