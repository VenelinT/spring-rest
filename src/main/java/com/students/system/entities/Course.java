package com.students.system.entities;

import javax.persistence.*;
import java.util.*;

@Entity
public class Course {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="course_name")
    private String name;
    @OneToMany(mappedBy = "courseGradeId" , fetch = FetchType.EAGER)
    private List<Grade> grades= new ArrayList<>();
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="teacher_id",referencedColumnName = "id")
    Teacher teacher;

    @ManyToMany(fetch = FetchType.EAGER)
    //@JoinTable(name = "student-course", joinColumns= @JoinColumn(name="course_id"), inverseJoinColumns = @JoinColumn(name="student_id"))
    private Set<Student> studentsInCourse=new HashSet<>();


    public Course(String name, int hours) {

    }

    public Course(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Course(Long courseId) {
    }

    public List<Grade> getGrades() {
        return grades;
    }
    public List<Double>getGradeValues(){
        List<Double>gradeRes =new ArrayList<>();
        grades.forEach(grade -> gradeRes.add(grade.getGrade()));
        return gradeRes;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    public Set<Student> getStudentsInCourse() {
        return studentsInCourse;
    }

    public void setStudentsInCourse(Set<Student> studentsInCourse) {
        this.studentsInCourse = studentsInCourse;
    }

    public Course() {
    }

    public Course(String name) {
        this.name = name;
    }

    public Course(String name, Teacher teacher) {
        this.name = name;
        this.teacher = teacher;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString(){
        return this.name;
    }

    public String toStringCourseWithTeacherAndStudents(){
        if(this.teacher==null)
            return this.name + " teacher: none "+ " students: "+this.getStudentsInCourse();
        return this.name + " teacher: "+this.teacher.getName()+ " students: "+this.getStudentsInCourse();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(id, course.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
