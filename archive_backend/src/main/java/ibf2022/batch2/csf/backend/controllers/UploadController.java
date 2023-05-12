package ibf2022.batch2.csf.backend.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ibf2022.batch2.csf.backend.models.Bundle;
import ibf2022.batch2.csf.backend.repositories.ArchiveRepository;
import ibf2022.batch2.csf.backend.repositories.ImageRepository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@CrossOrigin(origins="*")
public class UploadController {

	@Autowired
	private ImageRepository imageRepository;

	@Autowired
	private ArchiveRepository archiveRepository;

  	@PostMapping(path="/upload", consumes = "multipart/form-data")
  	public ResponseEntity<String> uploadFile(
    	@RequestParam("name") String name,
		@RequestParam("title") String title,
		@RequestParam("comments") String comments,
		@RequestParam("archive") MultipartFile archive
	) {
		// TODO: Complete the method implementation
		System.out.println("Received name: " + name);
		System.out.println("Received title: " + title);
		System.out.println("Received comments: " + comments);
		System.out.println("Received file: " + archive.getOriginalFilename());
		try {
			Bundle bundle = imageRepository.upload(name, title, comments, archive);
			Bundle savedBundle = archiveRepository.recordBundle(bundle);
			// Return 201 Created with the bundleId
			String jsonResponse = String.format("{\"bundleId\": \"%s\"}", bundle.getBundleId());
			return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(jsonResponse);
		} catch (Exception e) {
            e.printStackTrace();
            // Return 500 error ResponseEntity with the error message
            String jsonResponse = String.format("{\"error\": \"%s\"}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(jsonResponse);
		}
  	}

  // TODO: Task 5
  @GetMapping(path = "/bundle/{bundleId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> getBundleByBundleId(@PathVariable String bundleId) {
	Bundle bundle = archiveRepository.getBundleByBundleId(bundleId);
	if (bundle == null) {
	  return ResponseEntity
		  .status(HttpStatus.NOT_FOUND)
		  .body("Bundle not found for id: " + bundleId);
	}
	// Serialize the bundle object to JSON
	ObjectMapper objectMapper = new ObjectMapper();
	String json = "";
	try {
	  json = objectMapper.writeValueAsString(bundle);
	} catch (JsonProcessingException e) {
	  return ResponseEntity
		  .status(HttpStatus.INTERNAL_SERVER_ERROR)
		  .body("Error converting bundle to JSON: " + e.getMessage());
	}
	return ResponseEntity.ok(json);
  }

  	// TODO: Task 6
  	@GetMapping(path = "/bundles", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<Bundle>> getAllBundles() {
		ArrayList<Bundle> bundles = archiveRepository.getBundles();
		return ResponseEntity.ok(bundles);
	}
}