package com.portal.exception;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {

	private int errorStatus;
	private List<String> errorMessage;
	
}
