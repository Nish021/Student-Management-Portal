package com.portal.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.portal.dto.StudentDto;
import com.portal.exception.StudentNotFoundException;
import com.portal.model.Student;
import com.portal.repository.StudentRepository;
import com.portal.utils.Utils;

@Service
public class StudentServiceImpl implements StudentService{
	
	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	Utils util;
	
	@Override
	public StudentDto createStudentRecord(StudentDto studentData) {
		 Student student = util.convertDtoToEntity(studentData);
		 Student savedStudent = studentRepository.save(student);
		 return util.convertEntityToDto(savedStudent);
	}

	@Override
	public Page<StudentDto> getAllStudentRecords(int page, int limit) {
		Pageable pageable = PageRequest.of(page, limit);
		Page<Student> studentRecords = studentRepository.findAll(pageable);
		List<StudentDto> records = studentRecords.getContent().stream()
				.map(data -> util.convertEntityToDto(data)).toList();
		return new PageImpl<>(records, pageable, studentRecords.getTotalElements());
	}
	

	@Override
//	@Cacheable(value = "students", key = "studentName")
	public Page<StudentDto> getStudentsByName(String studentName, int page, int limit) {
		Pageable pageable = PageRequest.of(page, limit);
		Page<Student> studentRecords = studentRepository.getStudentByName(studentName, pageable);
		if(!studentRecords.hasContent()) {
			throw new StudentNotFoundException(studentName + " is not present in database");
		}
		List<StudentDto> records = studentRecords.stream()
				.map(data -> util.convertEntityToDto(data)).toList();
		return new PageImpl<>(records, pageable, studentRecords.getTotalElements());
	}

	@Override
//	@CachePut(value = "students", key = "#studentData.id")
	public StudentDto updateStudentRecord(StudentDto studentData) {
		Student student = studentRepository.getStudentById(studentData.getId());
		if(student == null) {
			throw new StudentNotFoundException(studentData.getName() + " is not present in database");
		}
		if (studentData.getName() != null) {
	        student.setName(studentData.getName());
	    }
	    if (studentData.getAge() != null) {
	        student.setAge(studentData.getAge());
	    }
	    if (studentData.getClassName() != null) {
	        student.setClassName(studentData.getClassName());
	    }
	    if (studentData.getPhoneNumber() != null) {
	        student.setPhoneNumber(studentData.getPhoneNumber());
	    }
		studentRepository.save(student);
		return util.convertEntityToDto(student);
	}
	
	@Override
	public StudentDto getStudentById(Long id) {
		Student student = studentRepository.getStudentById(id);
		if(student == null) {
			throw new StudentNotFoundException("Student is not present in database");
		}
		return util.convertEntityToDto(student);
	}

	@Override
//	@CacheEvict(value = "students", key = "#studentId")
	public Boolean deleteStudentRecord(Long studentId) {
		Student student = studentRepository.getStudentById(studentId);
		if(student == null) {
			throw new StudentNotFoundException("Student is not present in database");
		}
		studentRepository.deleteById(studentId);
		return true;
	}


}
