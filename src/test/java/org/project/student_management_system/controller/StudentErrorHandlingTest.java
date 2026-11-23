package org.project.student_management_system.controller;

import org.junit.jupiter.api.Test;
import org.project.student_management_system.repository.StudentRepository;
import org.project.student_management_system.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
@Import(StudentService.class)
class StudentErrorHandlingTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentRepository repository;

    @Test
    void missingStudentReturns404ForReadUpdateAndDelete() throws Exception {
        when(repository.findById(42L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/students/42"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Student not found with ID: 42"));
        mockMvc.perform(put("/api/v1/students/42")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"A\",\"lastName\":\"B\",\"email\":\"a@example.com\",\"major\":\"CS\"}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Student not found with ID: 42"));
        mockMvc.perform(delete("/api/v1/students/42"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Student not found with ID: 42"));

        verify(repository, never()).save(any());
        verify(repository, never()).delete(any());
    }

    @Test
    void invalidIdAndMalformedJsonReturn400() throws Exception {
        mockMvc.perform(get("/api/v1/students/not-a-number"))
                .andExpect(status().isBadRequest());
        mockMvc.perform(post("/api/v1/students/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{"))
                .andExpect(status().isBadRequest());
        verifyNoInteractions(repository);
    }

    @Test
    void emptyStudentListReturns200() throws Exception {
        when(repository.findAll()).thenReturn(List.of());
        mockMvc.perform(get("/api/v1/students"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}