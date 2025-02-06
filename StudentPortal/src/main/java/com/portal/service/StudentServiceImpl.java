package com.portal.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.portal.dto.StudentDto;
import com.portal.exception.StudentNotFoundException;
import com.portal.model.Student;
import com.portal.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService{
	
	@Autowired
	public StudentRepository studentRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public StudentDto createStudentRecord(StudentDto studentData) {
		 Student student = convertDtoToEntity(studentData);
		 Student savedStudent = studentRepository.save(student);
		 return convertEntityToDto(savedStudent);
	}

	@Override
	public List<StudentDto> getAllStudentRecords() {
		List<Student> studentRecords = studentRepository.findAll();
		List<StudentDto> records = studentRecords.stream()
				.map(data -> convertEntityToDto(data)).toList();
		return records;
	}
	

	@Override
	@Cacheable(value = "students", key = "studentName")
	public List<StudentDto> getStudentsByName(String studentName) {
		List<Student> studentRecords = studentRepository.getStudentByName(studentName);
		if(studentRecords.size() == 0) {
			throw new StudentNotFoundException(studentName + " is not present in database");
		}
		List<StudentDto> records = studentRecords.stream()
				.map(data -> convertEntityToDto(data)).toList();
		return records;
	}

	@Override
	@CachePut(value = "students", key = "#studentData.id")
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
		return convertEntityToDto(student);
	}

	@Override
	@CacheEvict(value = "students", key = "#studentId")
	public Boolean deleteStudentRecord(Long studentId) {
		Student student = studentRepository.getStudentById(studentId);
		if(student == null) {
			throw new StudentNotFoundException("Student is not present in database");
		}
		studentRepository.deleteById(studentId);
		return true;
	}
	
	public StudentDto convertEntityToDto(Student student) {
		return modelMapper.map(student, StudentDto.class);
	}

	public Student convertDtoToEntity (StudentDto studentDto) {
		return modelMapper.map(studentDto, Student.class);
	}

}
