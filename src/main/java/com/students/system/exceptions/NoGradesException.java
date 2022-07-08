package com.students.system.exceptions;

public class NoGradesException extends Exception {
    public NoGradesException(String no_grades_in_course) {
        super(no_grades_in_course);
    }
}
