package com.openmtr.api.services;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

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
			return true;
		} catch (Exception ex) {
			this.setErrorMsg(ex.getMessage());
			return false;
		}
		
	}
	
	
	protected void createImageFileName(String imageExtension) {
		Date date = new Date();
		this.image = new File(this.getImageFolderLocation() + date.getTime() + imageExtension);
	}
	

	
	private void downloadImage(String url) throws Exception{
		try {
			InputStream in = new URL(url).openStream();
			this.createImageFileName(this.determineFileType(in));
			Files.copy(in, Paths.get(this.getImageFile().getPath()), StandardCopyOption.REPLACE_EXISTING);
		} catch(Exception ex) {
			throw new Exception("Could not download image from URL: " + url);
		}
		
	}

	

}
