package org.project.student_management_system.mapper;

import org.junit.jupiter.api.Test;
import org.project.student_management_system.dto.StudentDto;
import org.project.student_management_system.entity.Student;

import static org.junit.jupiter.api.Assertions.*;

class StudentMapperTest {

    @Test
    void testMapToStudentDto() {
        Student student = new Student(1L, "San", "Zhang", "zhangsan@example.com", "Computer Science");
        StudentDto dto = StudentMapper.mapToStudentDto(student);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("San", dto.getFirstName());
        assertEquals("Zhang", dto.getLastName());
        assertEquals("zhangsan@example.com", dto.getEmail());
        assertEquals("Computer Science", dto.getMajor());
    }

    @Test
    void testMapToStudentDtoNull() {
        assertNull(StudentMapper.mapToStudentDto(null));
    }

    @Test
    void testMapToStudent() {
        StudentDto dto = new StudentDto(1L, "San", "Zhang", "zhangsan@example.com", "Computer Science");
        Student student = StudentMapper.mapToStudent(dto);

        assertNotNull(student);
        assertEquals(1L, student.getId());
        assertEquals("San", student.getFirstName());
        assertEquals("Zhang", student.getLastName());
        assertEquals("zhangsan@example.com", student.getEmail());
        assertEquals("Computer Science", student.getMajor());
    }

    @Test
    void testMapToStudentNull() {
        assertNull(StudentMapper.mapToStudent(null));
    }
}
