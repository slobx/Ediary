package com.iktpreobuka.final_project.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileHandlerImpl implements FileHandler{

	static final String UPLOAD_FOLDER = "C:\\Users\\slobx\\Desktop\\eDiary\\logs";

	@Override
	public boolean singleFileUpload(MultipartFile file) throws IOException {
		
		if(file.isEmpty())
		{		
			
			return false;
		}
		
		try {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOAD_FOLDER + file.getOriginalFilename());
			Files.write(path, bytes);
			
		} catch (Exception e) {
			throw e;
		}
		file.getContentType();
		return true;
		
		
	}
	
}
