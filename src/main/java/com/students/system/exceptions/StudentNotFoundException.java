package com.students.system.exceptions;

public class StudentNotFoundException extends Exception {
    public StudentNotFoundException(String no_students_found) {
        super(no_students_found);
    }
}
