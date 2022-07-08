package com.students.system.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne()
    @JoinColumn(name="student_id", referencedColumnName = "id")
    private Student studentGradeId;
    @ManyToOne()
    @JoinColumn(name="course_id", referencedColumnName = "id")
    private Course courseGradeId;
    private Double grade;

    public Grade(Student student, Course course, Double grades) {
        this.studentGradeId = student;
        this.courseGradeId = course;
        this.grade = grades;
    }

    public Grade() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grade grade1 = (Grade) o;
        return studentGradeId.equals(grade1.studentGradeId) && courseGradeId.equals(grade1.courseGradeId) && grade.equals(grade1.grade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentGradeId, courseGradeId, grade);
    }

    public Double getGrade() {
        return grade;
    }
}
