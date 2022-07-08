package com.students.system.repositories;

import com.students.system.entities.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface GradeRepository extends JpaRepository<Grade,Long> {


    @Query(value="SELECT grade FROM grade WHERE course_id=?1",nativeQuery = true)
    List<Double> getGradesByCourse(Long courseId);
    @Query(value="SELECT grade FROM grade WHERE student_id=?1", nativeQuery = true)
    List<Double>getGradesByStudent(Long studentId);

}

