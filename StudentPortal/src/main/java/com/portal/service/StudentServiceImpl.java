package com.portal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.portal.dto.StudentDto;
import com.portal.exception.StudentNotFoundException;
import com.portal.model.Student;
import com.portal.repository.StudentRepository;
import com.portal.utils.Utils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService{
	
	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	Utils util;
	
	@Autowired
	RedisTemplate<String, Object> redisTemplate;
	
	private static final String KEY = "STUDENT";
	
	@Override
	public StudentDto createStudentRecord(StudentDto studentData) {
		 log.info("Creating student record with data: {}", studentData);
		 Student student = util.convertDtoToEntity(studentData);
		 Student savedStudent = studentRepository.save(student);
		 redisTemplate.opsForHash().put(KEY, savedStudent.getId(), util.serializeStudent(savedStudent));
		 log.info("Student record created successfully: {}", savedStudent);
		 return util.convertEntityToDto(savedStudent);
	}

	@Override
	public Page<StudentDto> getAllStudentRecords(int page, int limit) {
		log.info("Fetching all student records with page: {} and limit: {}", page, limit);
		Pageable pageable = PageRequest.of(page, limit);
		Page<Student> studentRecords = studentRepository.findAll(pageable);
		List<StudentDto> records = studentRecords.getContent().stream()
				.map(data -> util.convertEntityToDto(data)).toList();
		log.info("Fetched {} student records", studentRecords.getTotalElements());
		return new PageImpl<>(records, pageable, studentRecords.getTotalElements());
	}
	

	@Override
	public Page<StudentDto> getStudentsByName(String studentName, int page, int limit) {
		log.info("Fetching students with name: {} at page: {} and limit: {}", studentName, page, limit);
		Pageable pageable = PageRequest.of(page, limit);
		Page<Student> studentRecords = studentRepository.getStudentByName(studentName, pageable);
		if(!studentRecords.hasContent()) {
			log.error("No students found with name: {}", studentName);
			throw new StudentNotFoundException(studentName + " is not present in database");
		}
		List<StudentDto> records = studentRecords.stream()
				.map(data -> util.convertEntityToDto(data)).toList();
		log.info("Fetched {} students with name: {}", studentRecords.getTotalElements(), studentName);
		return new PageImpl<>(records, pageable, studentRecords.getTotalElements());
	}

	@Override
	public StudentDto updateStudentRecord(StudentDto studentData) {
		log.info("Updating student record with ID: {} and data: {}", studentData.getId(), studentData);
		Object obj = redisTemplate.opsForHash().get(KEY, studentData.getId());
		Student student;
		if(obj != null) {
			student = util.deserializeStudent((String) obj);
			log.info("Found student in cache with ID: {}", studentData.getId());
		}else {
			student = studentRepository.getStudentById(studentData.getId());
			if(student == null) {
				log.error("Student with ID: {} not found in database", studentData.getId());
				throw new StudentNotFoundException(studentData.getName() + " is not present in database");
			}
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
		redisTemplate.opsForHash().put(KEY, student.getId(), util.serializeStudent(student));
		log.info("Student record updated successfully with ID: {} and cached", student.getId());
		return util.convertEntityToDto(student);
	}
	
	@Override
	public StudentDto getStudentById(Long id) {
		log.info("Fetching student record by ID: {}", id);
		Object obj = redisTemplate.opsForHash().get(KEY, id);
		Student student;
		if (obj != null) {
			student = util.deserializeStudent((String) obj);
			log.info("Student found in cache with ID: {}", id);
		}else {
				student = studentRepository.getStudentById(id);
				if(student == null) {
					log.error("Student with ID: {} not found in database", id);
					throw new StudentNotFoundException("Student is not present in database");
				}
				redisTemplate.opsForHash().put(KEY, id, util.serializeStudent(student));
				log.info("Student found in database with ID: {} and cached", id);
		}
		return util.convertEntityToDto(student);
	}

	@Override
	public Boolean deleteStudentRecord(Long studentId) {
		log.info("Request to delete student record with ID: {}", studentId);
		Object obj = redisTemplate.opsForHash().get(KEY, studentId);
		Student student;
		if (obj != null) {
		       student =  util.deserializeStudent((String) obj);
		       log.info("Student found in cache with ID: {}", studentId);
		}else {
			student = studentRepository.getStudentById(studentId);
			if(student == null) {
				log.error("Student with ID: {} not found in database", studentId);
				throw new StudentNotFoundException("Student is not present in database");
			}
		}
		studentRepository.deleteById(studentId);
	    redisTemplate.opsForHash().delete(KEY, studentId);
	    log.info("Student record with ID: {} deleted successfully", studentId);
		return true;
	}


}
