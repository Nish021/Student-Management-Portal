package com.portal.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
@Table(name = "student")
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "student_id")
	private Long id;
	
	@Column(name = "student_name")
	@NotEmpty(message = "Student name is required")
	@Pattern(regexp = "^[a-zA-Z ]+$")
	private String name;
	
	@Column(name = "student_age")
	@NotEmpty(message = "Student age is required")
	@Pattern(regexp = "^[0-9]+$")
	private String age;
	
	@Column(name = "student_class")
	@NotEmpty(message = "Student class is required")
	private String className;
	
	@Column(name = "student_phonenumber")
	@NotNull(message = "Student phone number is required")
	@Min(value = 1000000000L, message = "Phone number must be exactly 10 digits.")
    @Max(value = 9999999999L, message = "Phone number must be exactly 10 digits.")
	private Long phoneNumber;
}
