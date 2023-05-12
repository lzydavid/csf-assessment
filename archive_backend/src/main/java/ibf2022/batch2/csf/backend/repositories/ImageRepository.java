package ibf2022.batch2.csf.backend.repositories;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Repository
public class ImageRepository {

	@Autowired
    private AmazonS3 s3Client;

    @Value("${do.storage.bucketname}")
    private String bucketName;

    public List<String> upload(MultipartFile file) throws IOException{

        List<String> urls = new ArrayList<>();

        ZipInputStream zis = new ZipInputStream(file.getInputStream());

        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
            String fileName = zipEntry.getName();
            String contentType="";
            
            if(fileName.endsWith(".jpg")){
                contentType = "jpg";
            }
            if(fileName.endsWith(".png")){
                contentType = "png";
            }else{
                contentType = "gif";
            }

            String imageSize = zipEntry.getSize()+"";

            byte[] data = new byte[(int) zipEntry.getSize()];
            int bytesRead = 0;
            while (bytesRead < data.length) {
                int count = zis.read(data, bytesRead, (data.length - bytesRead));
                if (count == -1) {
                    break;
                }
                bytesRead += count;
            }
            Map<String, String> userData = new HashMap<>();
            userData.put("content-type",contentType);
            userData.put("image-size",imageSize);

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setUserMetadata(userData);
            metadata.setContentLength(zipEntry.getSize());

            String key = UUID.randomUUID().toString().substring(0, 8);

            String url = "%s.%s".formatted(key, contentType);

            PutObjectRequest putRequest = 
                new PutObjectRequest(bucketName, url, new ByteArrayInputStream(data), metadata);
                
            putRequest.withCannedAcl(CannedAccessControlList.PublicRead);
            s3Client.putObject(putRequest);

            urls.add(url);

            zipEntry = zis.getNextEntry();
        }    
        return urls;
    }
}
