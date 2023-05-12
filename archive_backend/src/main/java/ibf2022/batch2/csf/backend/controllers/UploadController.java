package ibf2022.batch2.csf.backend.controllers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;


import ibf2022.batch2.csf.backend.models.Bundle;
import ibf2022.batch2.csf.backend.models.BundleId;
import ibf2022.batch2.csf.backend.models.Upload;
import ibf2022.batch2.csf.backend.repositories.ArchiveRepository;
import ibf2022.batch2.csf.backend.repositories.ImageRepository;

@Controller
@RequestMapping
public class UploadController {

	@Autowired
	private ImageRepository imgRepo;
	@Autowired
	private ArchiveRepository archRepo;

	// TODO: Task 2, Task 3, Task 4
	
	@PostMapping(path = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> upload(@RequestPart MultipartFile file,@RequestPart String name,@RequestPart String title,@RequestPart String comment) throws IOException {


		List<String> urls = imgRepo.upload(file);

		Optional<BundleId> opt= archRepo.recordBundle(name, title, comment, urls);
		if(opt.isEmpty()){
			return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body("error");
		}
		BundleId id = opt.get();

		return ResponseEntity.status(HttpStatus.SC_CREATED).body(id.toJSON().toString());
	}

	// TODO: Task 5

	@GetMapping(path = "/bundle/{bundleId}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getImages(@PathVariable String bundleId ) {
		System.out.println(bundleId);

		Upload u = archRepo.getBundleByBundleId(bundleId);

		return ResponseEntity.ok().body(u.toJSON().toString());
	}
	

	// TODO: Task 6
	@GetMapping(path = "/bundle",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getBundles() {

		List<Bundle> bundles = archRepo.getBundles();

		return ResponseEntity.ok().body(Bundle.toJSONArr(bundles).toString());
	}

}
