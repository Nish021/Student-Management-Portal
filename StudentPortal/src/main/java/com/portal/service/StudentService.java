package com.portal.service;

import java.util.List;

import com.portal.dto.StudentDto;

public interface StudentService {

	public StudentDto createStudentRecord(StudentDto studentData);

	public List<StudentDto> getAllStudentRecords();
	
	public List<StudentDto> getStudentsByName(String studentName);
	
	public StudentDto updateStudentRecord(StudentDto studentData);
	
	public Boolean deleteStudentRecord(Long studentId);
	
}
