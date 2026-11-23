package org.project.student_management_system.service;

import org.project.student_management_system.dto.StudentDto;
import org.project.student_management_system.entity.Student;
import org.project.student_management_system.exception.StudentNotFoundException;
import org.project.student_management_system.mapper.StudentMapper;
import org.project.student_management_system.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
 * 事务说明:
 * isolation（隔离级别）—— 控制并发事务之间能"看到"彼此多少未提交/已提交的数据
 *   - DEFAULT          : 使用数据库默认级别（MySQL InnoDB 默认是 REPEATABLE_READ）
 *   - READ_UNCOMMITTED : 可读到别人未提交的数据（脏读）
 *   - READ_COMMITTED   : 只能读到已提交的数据，避免脏读
 *   - REPEATABLE_READ  : 同一事务内多次读取同一行结果一致，避免不可重复读
 *   - SERIALIZABLE     : 完全串行化，最安全但并发性能最差
 *
 * propagation（传播行为）—— 当一个事务方法被另一个方法调用时，事务如何"传递"
 *   - REQUIRED     : 有事务就加入，没有就新建（默认，最常用）
 *   - REQUIRES_NEW : 总是新建事务，挂起外层事务
 *   - SUPPORTS     : 有事务就加入，没有就以非事务方式执行
 *   - MANDATORY    : 必须在已有事务中调用，否则抛异常
 *   - NOT_SUPPORTED: 以非事务方式执行，挂起外层事务
 *   - NEVER        : 不能在事务中调用，否则抛异常
 *   - NESTED       : 在外层事务中创建保存点（savepoint），可单独回滚

 */
@Service
public class StudentService {

    private final StudentRepository sr;

    public StudentService(StudentRepository sr) {
        this.sr = sr;
    }

    // 写操作：加入或新建事务；READ_COMMITTED 保证不会基于其他事务未提交的数据写入
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public StudentDto createStudent(StudentDto studentDto) {
        Student student = StudentMapper.mapToStudent(studentDto);
        Student savedStudent = sr.save(student);
        return StudentMapper.mapToStudentDto(savedStudent);
    }

    // 读操作：readOnly = true 让 Hibernate 跳过脏检查（dirty checking），提升查询性能
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, readOnly = true)
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
