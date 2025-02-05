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
		List<StudentDto> studentList = null;
		studentList = studentService.getStudentsByName(studentName);
		return ResponseEntity.ok(studentList);
	}
	
	@PutMapping("/updateStudentRecord")
	public ResponseEntity<StudentDto> updateStudentRecord(@RequestBody StudentDto studentData){
		StudentDto newStudentData = null;
		newStudentData = studentService.updateStudentRecord(studentData);
		return ResponseEntity.ok(newStudentData);
	}
	
	@DeleteMapping("/deleteStudentRecord/{studentId}")
	public ResponseEntity<String> deleteStudentRecord(@PathVariable Long studentId){
		Boolean isDeleted;
		String response = "";
		isDeleted = studentService.deleteStudentRecord(studentId);
		if(isDeleted) {
			response = "Student record deleted succesfully";
		}else {
			response = "Not deleted";
		}
		return ResponseEntity.ok(response);
	}
}
