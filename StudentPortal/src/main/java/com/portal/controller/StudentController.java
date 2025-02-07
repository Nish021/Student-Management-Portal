package com.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.portal.dto.StudentDto;
import com.portal.service.StudentService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class StudentController {

	@Autowired
	public StudentService studentService;
	
	@PostMapping("/createStudentRecord")
	public ResponseEntity<StudentDto> createStudentRecord(@Valid @RequestBody StudentDto studentData){
		StudentDto response = studentService.createStudentRecord(studentData);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping("/getAllStudentRecords")
	public ResponseEntity<Page<StudentDto>> getAllStudentRecords(@RequestParam int page, @RequestParam int limit ){
		Page<StudentDto>  studentList = studentService.getAllStudentRecords(page, limit);
		return ResponseEntity.ok(studentList);
	}
	
	@GetMapping("/getStudentsByName")
	public ResponseEntity<Page<StudentDto>> getStudentsByName(@RequestParam String studentName, @RequestParam int page, @RequestParam int limit){
		Page<StudentDto> studentList = studentService.getStudentsByName(studentName, page, limit);
		return ResponseEntity.ok(studentList);
	}
	
	@PatchMapping("/updateStudentRecord/{id}")
	public ResponseEntity<StudentDto> updateStudentRecord(@PathVariable Long id, @RequestBody StudentDto studentData){
		studentData.setId(id);
		StudentDto newStudentData = studentService.updateStudentRecord(studentData);
		return ResponseEntity.ok(newStudentData);
	}
	
	@GetMapping("/getStudentById/{id}")
	public ResponseEntity<StudentDto> getStudentById(@PathVariable Long id){
		StudentDto studentData = studentService.getStudentById(id);
		return ResponseEntity.ok(studentData);
	}
	
	@DeleteMapping("/deleteStudentRecord/{studentId}")
	public ResponseEntity<String> deleteStudentRecord(@PathVariable Long studentId) throws Exception{
		Boolean isDeleted;
		isDeleted = studentService.deleteStudentRecord(studentId);
		if(!isDeleted) {
			log.error("Error while deleting student with student ID : {}", studentId);
		}
		return ResponseEntity.ok("Student data is deleted successfully!");
	}
}
