package SocialMedia.Services;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

public interface IStoreFilesToDriver {
	String uploadImageToDrive(File file) throws GeneralSecurityException, IOException;
}
