package com.students.system.services;

import com.students.system.entities.Course;
import com.students.system.entities.Student;
import com.students.system.exceptions.CannotCreateEntityException;
import com.students.system.exceptions.EmptyCourseException;
import com.students.system.exceptions.StudentNotInCourseException;
import com.students.system.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Component
public class CourseService {

    private CourseRepository courseRepository;
    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course save(Course c) throws CannotCreateEntityException {
        if (c != null)
            return courseRepository.save(c);
        throw new CannotCreateEntityException("Cannot add course to database!");
    }
    public Course getCourse(Long courseId) {
        return courseRepository.findById(courseId).orElseThrow( () ->new EntityNotFoundException("No such element"));
    }

    public List<Course> getAll() throws EmptyCourseException {

        List<Course> courseList = courseRepository.findAll();
        if(courseList.size()>0)
            return courseList;
        else
            throw new EmptyCourseException("No courses found!");
    }

    public Optional<Course> getCourseIfStudentIsEnrolled(Long courseId, Student student) throws StudentNotInCourseException {
        Optional<Course> course =courseRepository.findById(courseId);
        if (course.get().getStudentsInCourse().contains(student))
            return course;
        throw new StudentNotInCourseException("Student is not enrolled in course!");
    }


    public Map<String, Set<Student>> getAllCoursesAndStudents() throws EmptyCourseException {
        Map<String, Set<Student>> coursesAndStudents = new HashMap<>();
        List<Course> courseList =getAll();
        if(courseList.size() >0) {
            courseList.forEach(course -> coursesAndStudents.put(course.getName(), course.getStudentsInCourse()));
            return coursesAndStudents;
        }
        else{
            throw new EmptyCourseException("No courses found!");
        }
    }



}
