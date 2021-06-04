package com.mobicash.firebase.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.mobicash.firebase.responder.ResponseFormatter;

@Service
public class TestService implements TestServiceDefinition {

	private String TEMP_URL = "";
	private String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/manassetest-73543/o/%s?alt=media";
	private String BACKET_NAME = "manassetest-73543.appspot.com";

	public String uploadFile(File file, String fileName) throws IOException {
		BlobId blobId = BlobId.of(BACKET_NAME, fileName);
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
		Credentials credentials = GoogleCredentials.fromStream(
				new FileInputStream("src/main/resources/manassetest-73543-firebase-adminsdk-41xwv-9e0d0ef077.json"));
		Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
		storage.create(blobInfo, Files.readAllBytes(file.toPath()));
		return String.format(DOWNLOAD_URL, URLEncoder.encode(fileName, "UTF-8"));
	}

	public File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
		File tempFile = new File(fileName);
		try (FileOutputStream fos = new FileOutputStream(tempFile)) {
			fos.write(multipartFile.getBytes());
			fos.close();
		}
		return tempFile;
	}

	public String getExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}

	public Object upload(MultipartFile multipartFile) {

		try {
			String fileName = multipartFile.getOriginalFilename();
			fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));

			File file = this.convertToFile(multipartFile, fileName);
			TEMP_URL = this.uploadFile(file, fileName);
			file.delete();
			return new ResponseFormatter("Successfully Uploaded !", TEMP_URL);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseFormatter("500", e, "Unsuccessfully Uploaded!");
		}

	}

	public Object download(String fileName) throws IOException {
		String destFileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));

		String destFilePath = "C:\\uploads\\" + destFileName;

		Credentials credentials = GoogleCredentials
				.fromStream(new FileInputStream("path of JSON with genarated private key"));
		Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
		Blob blob = storage.get(BlobId.of(BACKET_NAME, fileName));
		blob.downloadTo(Paths.get(destFilePath));
		return new ResponseFormatter("200", "Successfully Downloaded!");
	}
}
