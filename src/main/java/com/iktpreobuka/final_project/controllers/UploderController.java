package com.iktpreobuka.final_project.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.iktpreobuka.final_project.services.FileHandler;


@Controller
@RequestMapping(path = "/")
public class UploderController {

	@Autowired
	private FileHandler fileHandler;
	

	
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "upload";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/uploadStatus")
	public String uploadStatus() {
		return "uploadStatus";
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.GET, value = "/upload")
	public boolean singleFileUpload(@RequestParam ("file") MultipartFile file) {
		
				
		@SuppressWarnings("null")
		boolean result = (Boolean) null;
		try {
			result = fileHandler.singleFileUpload(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
