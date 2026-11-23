package org.project.student_management_system.service;

import org.junit.jupiter.api.Test;
import org.project.student_management_system.dto.StudentDto;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionAttribute;

import static org.junit.jupiter.api.Assertions.*;

// 用 Spring 自己解析 @Transactional 的方式，检查每个方法的隔离级别、传播行为和只读设置
class StudentServiceTransactionTest {

    private final AnnotationTransactionAttributeSource source = new AnnotationTransactionAttributeSource();

    private TransactionAttribute attributeOf(String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
        TransactionAttribute attr = source.getTransactionAttribute(
                StudentService.class.getMethod(methodName, parameterTypes), StudentService.class);
        assertNotNull(attr, methodName + " 应该有 @Transactional");
        return attr;
    }

    @Test
    void createStudentIsReadCommittedRequired() throws Exception {
        TransactionAttribute attr = attributeOf("createStudent", StudentDto.class);
        assertEquals(TransactionDefinition.ISOLATION_READ_COMMITTED, attr.getIsolationLevel());
        assertEquals(TransactionDefinition.PROPAGATION_REQUIRED, attr.getPropagationBehavior());
        assertFalse(attr.isReadOnly());
    }

    @Test
    void readMethodsAreReadOnly() throws Exception {
        for (TransactionAttribute attr : new TransactionAttribute[]{
                attributeOf("getAllStudents"),
                attributeOf("getStudentById", Long.class)}) {
            assertEquals(TransactionDefinition.ISOLATION_READ_COMMITTED, attr.getIsolationLevel());
            assertEquals(TransactionDefinition.PROPAGATION_REQUIRED, attr.getPropagationBehavior());
            assertTrue(attr.isReadOnly());
        }
    }

    @Test
    void updateAndDeleteAreRepeatableRead() throws Exception {
        for (TransactionAttribute attr : new TransactionAttribute[]{
                attributeOf("updateStudent", Long.class, StudentDto.class),
                attributeOf("deleteStudent", Long.class)}) {
            assertEquals(TransactionDefinition.ISOLATION_REPEATABLE_READ, attr.getIsolationLevel());
            assertEquals(TransactionDefinition.PROPAGATION_REQUIRED, attr.getPropagationBehavior());
            assertFalse(attr.isReadOnly());
        }
    }

    @Test
    void runtimeExceptionTriggersRollback() throws Exception {
        assertTrue(attributeOf("deleteStudent", Long.class).rollbackOn(new RuntimeException()));
    }
}
