package com.students.system.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="teacher_name")
    String name;
    String degree;
    @OneToMany(mappedBy = "teacher" , fetch = FetchType.EAGER)
    @JsonIgnore
    Set<Course> coursesSet=new HashSet<>();
    public Teacher(String name) {
        this.name = name;
    }

    public Teacher() {

    }
    @Override
    public String toString(){
        return this.name+" ";
    }


    public Teacher(String name, String degree, Set<Course> coursesSet) {
        this.name = name;
        this.degree = degree;
        this.coursesSet = coursesSet;
    }

    public Teacher(String name, String degree) {
        this.name = name;
        this.degree = degree;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public Set<Course> getCoursesSet() {
        return coursesSet;
    }

    public void setCoursesSet(Set<Course> coursesSet) {
        this.coursesSet = coursesSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return name.equals(teacher.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
