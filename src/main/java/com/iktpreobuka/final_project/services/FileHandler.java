package com.iktpreobuka.final_project.services;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface FileHandler {

	public boolean singleFileUpload(MultipartFile file) throws IOException;
}
