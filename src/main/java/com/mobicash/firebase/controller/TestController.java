package com.mobicash.firebase.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mobicash.firebase.service.TestService;

@RestController
@RequestMapping("/demo")
public class TestController {
	public static final Logger logger = LoggerFactory.getLogger(RestController.class);

	@Autowired
	private TestService testService;

	@PostMapping("/upload/file")
	public Object upload(@RequestParam("file") MultipartFile multipartFile) {
		logger.info("CALLING UNIT-upload | File Name : {}",multipartFile.getOriginalFilename());
		return testService.upload(multipartFile);
	}

	@PostMapping("/download/file/{fileName}")
	public Object download(@PathVariable String fileName) throws IOException {
		logger.info("CALLING UNIT-download | File Name : {}", fileName);
		return testService.download(fileName);
	}
}
