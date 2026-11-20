package org.project.student_management_system.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.project.student_management_system.dto.StudentDto;
import org.project.student_management_system.entity.Student;
import org.project.student_management_system.exception.StudentNotFoundException;
import org.project.student_management_system.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private Student student;
    private StudentDto studentDto;

    @BeforeEach
    void setUp() {
        student = new Student(1L, "San", "Zhang", "zhangsan@example.com", "Computer Science");
        studentDto = new StudentDto(1L, "San", "Zhang", "zhangsan@example.com", "Computer Science");
    }

    @Test
    void testCreateStudent() {
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        StudentDto result = studentService.createStudent(studentDto);

        assertNotNull(result);
        assertEquals(studentDto.getEmail(), result.getEmail());
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void testGetAllStudents() {
        when(studentRepository.findAll()).thenReturn(List.of(student));

        List<StudentDto> results = studentService.getAllStudents();

        assertEquals(1, results.size());
        assertEquals("San", results.get(0).getFirstName());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void testGetStudentByIdSuccess() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        StudentDto result = studentService.getStudentById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("zhangsan@example.com", result.getEmail());
    }

    @Test
    void testGetStudentByIdNotFound() {
        when(studentRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> studentService.getStudentById(2L));
    }

    @Test
    void testUpdateStudent() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        StudentDto updateDto = new StudentDto(1L, "Si", "Li", "lisi@example.com", "Mathematics");
        StudentDto result = studentService.updateStudent(1L, updateDto);

        assertNotNull(result);
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void testDeleteStudent() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        doNothing().when(studentRepository).delete(student);

        studentService.deleteStudent(1L);

        verify(studentRepository, times(1)).delete(student);
    }
}
