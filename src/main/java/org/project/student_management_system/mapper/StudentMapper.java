package org.project.student_management_system.mapper;

import org.project.student_management_system.dto.StudentDto;
import org.project.student_management_system.entity.Student;

public class StudentMapper {

    public static StudentDto mapToStudentDto(Student student) {
        if (student == null) {
            return null;
        }
        return new StudentDto(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getMajor()
        );
    }

    public static Student mapToStudent(StudentDto studentDto) {
        if (studentDto == null) {
            return null;
        }
        Student student = new Student();
        student.setId(studentDto.getId());
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        student.setEmail(studentDto.getEmail());
        student.setMajor(studentDto.getMajor());
        return student;
    }
}
