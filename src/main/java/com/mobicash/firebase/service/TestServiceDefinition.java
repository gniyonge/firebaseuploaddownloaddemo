package com.mobicash.firebase.service;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface TestServiceDefinition {

	String uploadFile(File file, String fileName) throws IOException;

	File convertToFile(MultipartFile multipartFile, String fileName) throws IOException;

	String getExtension(String fileName);

	Object upload(MultipartFile multipartFile);

	Object download(String fileName) throws IOException;
}
