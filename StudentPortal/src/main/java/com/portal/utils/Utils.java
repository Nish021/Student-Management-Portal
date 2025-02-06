package com.portal.utils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.portal.dto.StudentDto;
import com.portal.model.Student;

@Component
public class Utils {
	
	@Autowired
	ModelMapper modelMapper;

	public StudentDto convertEntityToDto(Student student) {
		return modelMapper.map(student, StudentDto.class);
	}

	public Student convertDtoToEntity (StudentDto studentDto) {
		return modelMapper.map(studentDto, Student.class);
	}
	
}
