package com.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.portal.dto.StudentDto;
import com.portal.service.StudentService;

import jakarta.validation.Valid;

@RestController
public class StudentController {

	@Autowired
	public StudentService studentService;
	
	@PostMapping("/createStudentRecord")
	public ResponseEntity<StudentDto> createStudentRecord(@Valid @RequestBody StudentDto studentData){
		StudentDto response = null;
		response = studentService.createStudentRecord(studentData);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping("/getAllStudentRecords")
	public ResponseEntity<List<StudentDto>> getAllStudentRecords(){
		List<StudentDto> studentList = null;
		studentList = studentService.getAllStudentRecords();
		return ResponseEntity.ok(studentList);
	}
	
	@GetMapping("/getStudentsByName/{studentName}")
	public ResponseEntity<List<StudentDto>> getStudentsByName(@PathVariable String studentName){
		List<StudentDto> studentList = studentService.getStudentsByName(studentName);
		return ResponseEntity.ok(studentList);
	}
	
	@PutMapping("/updateStudentRecord/{id}")
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
			throw new Exception("Student data Not Deleted");
		}
		return ResponseEntity.ok("Student data is deleted successfully!");
	}
}
