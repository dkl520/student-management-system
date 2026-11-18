package org.project.student_management_system.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.project.student_management_system.dto.StudentDto;
import org.project.student_management_system.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    private StudentDto studentDto;

    @BeforeEach
    void setUp() {
        studentDto = new StudentDto(1L, "San", "Zhang", "zhangsan@example.com", "Computer Science");
    }

    @Test
    void testCreateStudent() {
        when(studentService.createStudent(any(StudentDto.class))).thenReturn(studentDto);

        ResponseEntity<StudentDto> response = studentController.createStudent(studentDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("San", response.getBody().getFirstName());
        verify(studentService, times(1)).createStudent(any(StudentDto.class));
    }

    @Test
    void testGetAllStudents() {
        when(studentService.getAllStudents()).thenReturn(List.of(studentDto));

        ResponseEntity<List<StudentDto>> response = studentController.getAllStudents();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(studentService, times(1)).getAllStudents();
    }

    @Test
    void testGetStudentById() {
        when(studentService.getStudentById(1L)).thenReturn(studentDto);

        ResponseEntity<StudentDto> response = studentController.getStudentById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        verify(studentService, times(1)).getStudentById(1L);
    }

    @Test
    void testUpdateStudent() {
        when(studentService.updateStudent(eq(1L), any(StudentDto.class))).thenReturn(studentDto);

        ResponseEntity<StudentDto> response = studentController.updateStudent(1L, studentDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        verify(studentService, times(1)).updateStudent(eq(1L), any(StudentDto.class));
    }

    @Test
    void testDeleteStudent() {
        doNothing().when(studentService).deleteStudent(1L);

        ResponseEntity<Void> response = studentController.deleteStudent(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(studentService, times(1)).deleteStudent(1L);
    }
}
