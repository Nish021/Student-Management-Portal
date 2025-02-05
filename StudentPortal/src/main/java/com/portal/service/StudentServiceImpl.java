package com.portal.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portal.dto.StudentDto;
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
		 Student savedStudent = studentRepository.save(student); // Save entity
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
	public List<StudentDto> getStudentsByName(String studentName) {
		List<Student> studentRecords = studentRepository.getStudentByName(studentName);
		List<StudentDto> records = studentRecords.stream()
				.map(data -> convertEntityToDto(data)).toList();
		return records;
	}

	@Override
	public StudentDto updateStudentRecord(StudentDto studentData) {
		Student student = studentRepository.getStudentById(studentData.getId());
		if(student != null) {
			student.setName(studentData.getName());
			student.setAge(studentData.getAge());
			student.setClassName(studentData.getClassName());
			student.setPhoneNumber(studentData.getPhoneNumber());
			studentRepository.save(student);
		}
		return convertEntityToDto(student);
	}

	@Override
	public Boolean deleteStudentRecord(Long studentId) {
		if(studentRepository.existsById(studentId)) {
			studentRepository.deleteById(studentId);
			return true;
		}
		return false;
	}
	
	public StudentDto convertEntityToDto(Student student) {
		return modelMapper.map(student, StudentDto.class);
	}

	public Student convertDtoToEntity (StudentDto studentDto) {
		return modelMapper.map(studentDto, Student.class);
	}

}
