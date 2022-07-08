package com.students.system.services;

import com.students.system.entities.Grade;
import com.students.system.exceptions.NoGradesException;
import com.students.system.repositories.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GradeService {

    private GradeRepository gradeRepository;
    @Autowired
    public GradeService(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }



    public Grade save(Grade g){
        return gradeRepository.save(g);
    }

    public List<Double> getGrades(Long courseId) throws NoGradesException {
        List<Double> grades = gradeRepository.getGradesByCourse(courseId);
        if(grades.size()>0)
            return gradeRepository.getGradesByCourse(courseId);
        throw new NoGradesException("No grades in course");
    }

    public List<Double> getGradeOfStudent(Long studentId) throws NoGradesException {
        List<Double> grades = gradeRepository.getGradesByStudent(studentId);
        if(grades.size()>0)
            return gradeRepository.getGradesByStudent(studentId);
        throw new NoGradesException("Student has no grades");
    }

}
