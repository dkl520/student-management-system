
package org.project.student_management_system.exception;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.RestControllerAdvice;

// 告诉 Spring：这是一个全局 Controller 异常处理类
// 可以统一处理多个 Controller 中抛出的异常
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StudentNotFoundException.class)
    

    public ResponseEntity<ErrorResponse> handleStudentNotFound(
            StudentNotFoundException exception) {

        // 创建一个 HTTP 响应：
        // status(HttpStatus.NOT_FOUND)：设置 HTTP 状态码为 404
        // body(...)：设置返回给前端的响应内容
        return ResponseEntity.status(HttpStatus.NOT_FOUND)

                .body(new ErrorResponse(
                        HttpStatus.NOT_FOUND.value(),
                        exception.getMessage()
                ));
    }

    public record ErrorResponse(int status, String message) {
    }
}
