package org.project.student_management_system.service;

import org.project.student_management_system.dto.StudentDto;
import org.project.student_management_system.entity.Student;
import org.project.student_management_system.exception.StudentNotFoundException;
import org.project.student_management_system.mapper.StudentMapper;
import org.project.student_management_system.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository sr;

    public StudentService(StudentRepository sr) {
        this.sr = sr;
    }

    public StudentDto createStudent(StudentDto studentDto) {
        Student student = StudentMapper.mapToStudent(studentDto);
        Student savedStudent = sr.save(student);
        return StudentMapper.mapToStudentDto(savedStudent);
    }

    public List<StudentDto> getAllStudents() {
        List<Student> students = sr.findAll();
        return students.stream()
                .map(StudentMapper::mapToStudentDto)
                .toList();
    }

    public StudentDto getStudentById(Long id) {
        Student student = sr.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException(id));
        return StudentMapper.mapToStudentDto(student);
    }

    public StudentDto updateStudent(Long id, StudentDto updatedStudentDto) {
        Student student = sr.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException(id));

        student.setFirstName(updatedStudentDto.getFirstName());
        student.setLastName(updatedStudentDto.getLastName());
        student.setEmail(updatedStudentDto.getEmail());
        student.setMajor(updatedStudentDto.getMajor());

        Student savedStudent = sr.save(student);
        return StudentMapper.mapToStudentDto(savedStudent);
    }

    public void deleteStudent(Long id) {
        Student existingStudent = sr.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException(id));
        sr.delete(existingStudent);
    }
}


