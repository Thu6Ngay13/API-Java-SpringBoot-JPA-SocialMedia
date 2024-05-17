package SocialMedia.Services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Collections;

import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;

@Service
public class StoreFilesToDriverImpl implements IStoreFilesToDriver{
	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String SERVICE_ACOUNT_KEY_PATH = getPathToGoodleCredentials();

    private Drive createDriveService() throws GeneralSecurityException, IOException {
		GoogleCredential credential = 
        		GoogleCredential
        			.fromStream(new FileInputStream(SERVICE_ACOUNT_KEY_PATH))
        			.createScoped(Collections.singleton(DriveScopes.DRIVE));

        return new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                credential)
                .build();
    }
    
    private static String getPathToGoodleCredentials() {
        String currentDirectory = System.getProperty("user.dir");
        Path filePath = Paths.get(currentDirectory, "socialmedia-423001-b14e55ecb6f2.json");
        return filePath.toString();
    }

    @Override
    public String uploadImageToDrive(File file) throws GeneralSecurityException, IOException {

        try{
            String folderId = "1AytG6IsOPcxfncy9VmzDBkFiqTeqnDI5";
            Drive drive = createDriveService();
            
            com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
            fileMetaData.setName(file.getName());
            fileMetaData.setParents(Collections.singletonList(folderId));
            
            FileContent mediaContent = new FileContent("image/jpeg", file);
            com.google.api.services.drive.model.File uploadedFile = drive.files().create(fileMetaData, mediaContent).setFields("id").execute();
            
            String imageUrl = "https://drive.google.com/uc?export=view&id=" + uploadedFile.getId();
            return imageUrl;
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }

    }
}
