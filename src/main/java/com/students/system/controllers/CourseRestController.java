package com.students.system.controllers;

import com.students.system.entities.Course;
import com.students.system.entities.Student;
import com.students.system.exceptions.CannotCreateEntityException;
import com.students.system.exceptions.EmptyCourseException;
import com.students.system.exceptions.NoGradesException;
import com.students.system.services.CourseService;
import com.students.system.services.GradeService;
import com.students.system.services.StudentService;
import com.students.system.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/course")
public class CourseRestController {

    CourseService courseService;
    TeacherService teacherService;

    StudentService studentService;

    GradeService gradeService;
    @Autowired
    public CourseRestController(CourseService courseService, TeacherService teacherService, StudentService studentService, GradeService gradeService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
        this.studentService = studentService;
        this.gradeService = gradeService;
    }

    @PostMapping("/add")
    public ResponseEntity addCourse(@RequestBody Course course){
        try{
            return new ResponseEntity(courseService.save(course), HttpStatus.OK);
        }catch (CannotCreateEntityException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{courseId}/teacher/{teacherId}/add")
    public ResponseEntity addTeacherToCourse(@PathVariable Long courseId, @PathVariable Long teacherId){
        try {
            Course course = courseService.getCourse(courseId);
            course.setTeacher(teacherService.getTeacher(teacherId));
            return new ResponseEntity<>(courseService.save(course), HttpStatus.OK);
        }catch (EntityNotFoundException | CannotCreateEntityException e ){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/{courseId}/student/{studentId}/add")
    public ResponseEntity addStudentToCourse(@PathVariable Long courseId,@PathVariable Long studentId){
        try {
            Course course = courseService.getCourse(courseId);
            Student student = studentService.getStudent(studentId);
            student.getCourses().add(course);
            course.getStudentsInCourse().add(student);
            studentService.save(student);
            courseService.save(course);
            return new ResponseEntity(HttpStatus.OK);
        }catch (EntityNotFoundException | CannotCreateEntityException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

    }
    @GetMapping("/showall")
    public ResponseEntity getAllStudentsAndCourses() {
        try{
            return ResponseEntity.ok(courseService.getAllCoursesAndStudents());
        }catch(EmptyCourseException e){
            System.out.println(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/showall/full")
    public ResponseEntity getAllCoursesAndTeachers() {
        try {
            List<String> result = new ArrayList<>();
            courseService.getAll().forEach(course -> result.add(course.toStringCourseWithTeacherAndStudents()));
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch(EmptyCourseException e){
            System.out.println(e.getMessage());

            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/{courseId}/average")
    public ResponseEntity getAverageForCourse(@PathVariable Long courseId){
        try {
            String average = String.format("%.2f", gradeService.getGrades(courseId).stream().mapToDouble(d -> d).average().orElse(0.0));
            return new ResponseEntity("Average is: "+ average, HttpStatus.OK);
        }catch (NoGradesException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }

    }


}
