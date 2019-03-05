package com.openmtr.api.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.QueryParam;

public class GetRequest extends ApiRequest {
	
	@QueryParam("url")
	private String url = "";

	@Override
	@QueryParam("email")
	public void setEmailAddress(String email) {
		this.email = email;
		
	}

	@Override
	@QueryParam("numberOfDials")
	public void setDialsOnMeter(String dialsOnMeter) {
		this.dialsOnMeter = dialsOnMeter;
		
	}

	@Override
	protected boolean savedImage() {
		try {
			this.downloadImage(this.url);
			this.extractByteArray();
			return true;
		} catch (FileNotFoundException ex) {
			this.setErrorMsg(ex.getMessage());
			return false;
		}
		
	}
	
	public boolean validateRequest() {
		if(!this.isValidEmail()) {
			this.setErrorMsg("Email address is invalid");
		}
		else if (!this.isValidDialsOnMeter()) {
			this.setErrorMsg("The dials on meter is invalid");
		}
		else if (!this.isValidUrl()) {
			this.setErrorMsg("The URL address is invalid");
		}
		else if(!this.savedImage()) {
			this.setErrorMsg("Could not download the image.");
		}
		return this.error;
	}
	
	
	protected void createImageFileName(String imageExtension) {
		Date date = new Date();
		this.image = new File(this.getImageFolderLocation() + date.getTime() + imageExtension);
	}
	

	
	private void downloadImage(String url) throws FileNotFoundException{
		try {
			InputStream in = new URL(url).openStream();
			this.createImageFileName(this.determineFileType(in));
			Files.copy(in, Paths.get(this.getImageFile().getPath()), StandardCopyOption.REPLACE_EXISTING);
		} catch(Exception ex) {
			throw new FileNotFoundException("Could not download image from URL: " + url);
		}
		
	}

	private boolean isValidUrl() {
		Pattern reg = Pattern.compile("^((http[s]?|ftp):\\/)?\\/?([^:\\/\\s]+)((\\/\\w+)*\\/)([\\w\\-\\.]+[^#?\\s]+)(.*)?(#[\\w\\-]+)?$");
		Matcher m = reg.matcher(this.url);
		return m.find();
	}
	

}
