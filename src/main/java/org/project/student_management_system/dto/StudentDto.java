package org.project.student_management_system.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tools.jackson.databind.annotation.JsonSerialize;
import tools.jackson.databind.ser.std.ToStringSerializer;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @NotBlank(message = "name cannot be blank")
    private String firstName;
    @NotBlank(message = "name cannot be blank")
    private String lastName;
    @NotBlank(message = "name cannot be blank")
    @Email(message = "email is not right")
    private String email;
    @NotBlank(message = "major cannot be blank")
    private String major;
}
