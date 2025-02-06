package com.portal.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.portal.dto.StudentDto;

public interface StudentService {

	public StudentDto createStudentRecord(StudentDto studentData);

	public Page<StudentDto> getAllStudentRecords(int page, int limit);
	
	public Page<StudentDto> getStudentsByName(String studentName, int page, int limit);
	
	public StudentDto updateStudentRecord(StudentDto studentData);
	
	public StudentDto getStudentById(Long id);
	
	public Boolean deleteStudentRecord(Long studentId);
	
}
