package org.project.student_management_system.repository;

import org.project.student_management_system.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;


//Spring Data JPA 最神奇、最强大的特性之一（通常被称为“根据方法名推导查询”，Query Method Derivation）。
//
//不用写 SQL 语句，也不用写具体实现，Spring 就能帮你把数据库查询跑通。下面我带你拆解它是怎么做到的：
public interface StudentRepository extends JpaRepository<Student, Long> {

}

