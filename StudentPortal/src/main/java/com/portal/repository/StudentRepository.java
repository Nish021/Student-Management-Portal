package com.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portal.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
	
	List<Student> getStudentByName(String studentName);
	Student getStudentById(Long studentId);

}
