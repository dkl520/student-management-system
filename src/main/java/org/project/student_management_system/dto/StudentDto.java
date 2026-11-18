package org.project.student_management_system.dto;

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
    private String firstName;
    private String lastName;
    private String email;
    private String major;
}
