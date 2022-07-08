package com.students.system.controllers;

import com.students.system.entities.Course;
import com.students.system.entities.Grade;
import com.students.system.entities.Student;
import com.students.system.exceptions.CannotCreateEntityException;
import com.students.system.exceptions.NoGradesException;
import com.students.system.exceptions.StudentNotFoundException;
import com.students.system.exceptions.StudentNotInCourseException;
import com.students.system.services.CourseService;
import com.students.system.services.GradeService;
import com.students.system.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentRestController {

    StudentService studentService;

    CourseService courseService;

    GradeService gradeService;
    @Autowired
    public StudentRestController(StudentService studentService, CourseService courseService, GradeService gradeService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.gradeService = gradeService;
    }

    @PostMapping("/add")
    public ResponseEntity addStudent(@RequestBody Student student){
        try {
            studentService.save(student);
            return new ResponseEntity("student added to DB", HttpStatus.CREATED);
        }catch (CannotCreateEntityException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{studentId}/course/{courseId}/grade/{grade}")
    public ResponseEntity addGradeToStudent(@PathVariable Long studentId,@PathVariable Long courseId, @PathVariable double grade) {
        try {
            Student student = studentService.getStudent(studentId);
            Optional<Course> course = courseService.getCourseIfStudentIsEnrolled(courseId, student);
            Grade grade1 = new Grade(student, course.get(), grade);
            student.getGrades().add(grade1);
            gradeService.save(grade1);
            studentService.save(student);
            return ResponseEntity.ok(grade + " was added to student with id: " + studentId + " in course with id " + courseId);
        } catch (EntityNotFoundException | StudentNotInCourseException | CannotCreateEntityException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        }


    @GetMapping("/{studentId}/average")
    public ResponseEntity getAverageOfStudent(@PathVariable Long studentId){
        try {
            String average = String.format("%.2f", gradeService.getGradeOfStudent(studentId).stream().mapToDouble(d -> d).average().orElse(0.0));
            return new ResponseEntity("Average is: "+ average, HttpStatus.OK);
        }catch (EntityNotFoundException | NoGradesException e ){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/getall")
    public ResponseEntity getAllStudents(){
        try{
            return ResponseEntity.ok(studentService.getAll());
        }catch(StudentNotFoundException e ){
            System.out.println(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getStudent(@PathVariable Long id){
        try{
            return new ResponseEntity(studentService.getStudent(id), HttpStatus.OK);
        }catch(EntityNotFoundException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

}
