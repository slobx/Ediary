package com.iktpreobuka.final_project.controllers.util;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.final_project.util.View;


public class RESTError {

	@JsonView(View.Public.class)
	private int code;
	@JsonView(View.Public.class)
	private String message;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public RESTError(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public RESTError() {
		super();
	}
}
