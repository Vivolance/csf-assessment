package ibf2022.batch2.csf.backend.repositories;


import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

import ibf2022.batch2.csf.backend.models.Bundle;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.amazonaws.services.s3.model.CannedAccessControlList;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;

import org.springframework.stereotype.Repository;

import java.io.ByteArrayOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Repository
public class ImageRepository {
	@Autowired
	private AmazonS3 s3;

	//TODO: Task 3
	// You are free to change the parameter and the return type
	// Do not change the method's name
	public Bundle upload(String name, String title, String comments, MultipartFile archive) throws IOException {
		/**
		 * Not used, but possibly later, if S3 bucket does not automatically unzip the file and upload each of them accordingly
		 */
		Map<String, String> userData = new HashMap<>();
		Bundle bundle = new Bundle();
		bundle.setName(name);
		bundle.setComments(name);
		bundle.setTitle(title);
		bundle.setDate(LocalDate.now());
		bundle.setBundleId(UUID.randomUUID().toString().substring(0, 8));

		ArrayList<String> imageUrls = new ArrayList<String>();

		userData.put("name", name);
		userData.put("title", title);
		userData.put("comments", comments);

		try (ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(archive.getBytes()))) {
			ZipEntry zipEntry = zis.getNextEntry();
			while (zipEntry != null) {
				if (!zipEntry.isDirectory()) {
					String fileName = zipEntry.getName();
					String contentType = getContentType(fileName);

					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					byte[] buffer = new byte[1024];
					int len;
					while ((len = zis.read(buffer)) > 0) {
						baos.write(buffer, 0, len);
					}

					byte[] bytes = baos.toByteArray();
					InputStream inputStream = new ByteArrayInputStream(bytes);

					ObjectMetadata metadata = new ObjectMetadata();
					metadata.setContentType(contentType);
					metadata.setContentLength(bytes.length);
					metadata.setUserMetadata(userData);

					PutObjectRequest putReq = new PutObjectRequest("my-bucket-two", fileName, inputStream, metadata);
					putReq = putReq.withCannedAcl(CannedAccessControlList.PublicRead);
					PutObjectResult putObjectResult = s3.putObject(putReq);

					imageUrls.add(s3.getUrl("my-bucket-two", fileName).toString());
				}
				zipEntry = zis.getNextEntry();
			}
		}
		bundle.setUrls(imageUrls);
		return bundle;
	}

	private String getContentType(String fileName) {
		String extension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
		switch (extension) {
			case "png":
				return "image/png";
			case "jpg":
			case "jpeg":
				return "image/jpeg";
			case "gif":
				return "image/gif";
			default:
				return "application/octet-stream";
		}
	}
}