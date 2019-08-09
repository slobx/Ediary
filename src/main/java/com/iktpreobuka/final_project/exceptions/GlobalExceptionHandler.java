package com.iktpreobuka.final_project.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.multipart.MultipartException;

@ControllerAdvice
public class GlobalExceptionHandler {

public String handleError1(MultipartException e ) {
		
		
		return "redirect:/uploadStatus";
	}
}
