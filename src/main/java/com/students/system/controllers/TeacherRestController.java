package com.students.system.controllers;

import com.students.system.entities.Teacher;
import com.students.system.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/teacher")
public class TeacherRestController {
    TeacherService teacherService;
    @Autowired
    public TeacherRestController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping("/add")
    public ResponseEntity<Teacher>addTeacher(@RequestBody Teacher teacher){
        return ResponseEntity.ok(teacherService.save(teacher));
    }
    @GetMapping("/get/all")
    public ResponseEntity<List<Teacher>>getAllTeachers(){
        return ResponseEntity.ok(teacherService.getAll());
    }

}
