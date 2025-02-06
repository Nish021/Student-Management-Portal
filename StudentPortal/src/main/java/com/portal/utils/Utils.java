package com.portal.utils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
	
	public String serializeStudent(Student student) {
	    try {
	        ObjectMapper objectMapper = new ObjectMapper();
	        return objectMapper.writeValueAsString(student);
	    } catch (JsonProcessingException e) {
	        throw new RuntimeException("Failed to serialize student object", e);
	    }
	}

	public Student deserializeStudent(String studentJson) {
	    try {
	        ObjectMapper objectMapper = new ObjectMapper();
	        return objectMapper.readValue(studentJson, Student.class);
	    } catch (JsonProcessingException e) {
	        throw new RuntimeException("Failed to deserialize student object", e);
	    }
	}
	
}
