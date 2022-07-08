package com.students.system.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="student_name")
    private String name;
    private int age;

    @OneToMany(mappedBy = "studentGradeId" , fetch = FetchType.EAGER)
    private List<Grade> grades=new ArrayList<>();
    @JsonIgnore
    @ManyToMany(mappedBy = "studentsInCourse" , fetch = FetchType.EAGER)
    private Set<Course> courses=new HashSet<>();

    public Student() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }


    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString(){
        return this.name + " ";
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return name.equals(student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
    public List<Double>getGradeValues(){
        List<Double>gradeRes =new ArrayList<>();
        grades.forEach(grade -> gradeRes.add(grade.getGrade()));
        return gradeRes;
    }
}
