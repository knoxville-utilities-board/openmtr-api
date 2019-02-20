package com.openmtr.api.services;


import java.time.Duration;
import java.time.Instant;

import javax.ws.rs.core.Response;


public class ReturnResponse {

    boolean error = false;
    String error_msg = "";
    int status_code = 400;
    String data = null;
    
    private Instant startProcessing;
	
	private Instant stopProcessing;
	
	private Duration totalProcessing;
	
    String totalProcessingTime = "";


    public ReturnResponse() {
    	//Start the timer
    	this.startProcessing();
    }

    /**
     * Will return a JSON response with the error message given
     * @param message The message to return
     * @param statusCode The Status code to return
     * @return Response
     */
    public Response error(String message, Integer statusCode ) {
        this.status_code = statusCode;
        this.error = true;
        this.error_msg = message;

        return this.error();

    }
    
    /**
     * Will return a error Response
     * @return Response
     */
    public Response error() {
    	//stop the timer
    	this.stopProcessing();
    	return Response
                .status(this.status_code)
                .entity("{" +
                        "\"error\" : \"" + this.error + "\", " +
                        "\"error_msg\" : \"" + this.error_msg + "\", " +
                        "\"processing_time\" : \"" + this.totalProcessingTime + "\"" +
                        "}"
                )
                .build();
    }
    
    public void setErrorMessage(String errorMsg) {
    	this.error_msg = errorMsg;
    	this.error = true;
    }
    
    public void setStatusCode(int statusCode) {
    	this.status_code = statusCode;
    }


    public void setData(String data) {
        this.data = data;
    }
    
    public void setTotalProcessingTime(String totalTime) {
    	this.totalProcessingTime = totalTime;
    }

    public String getData() {
        return this.data;
    }
    
    public Response success() {
    	//stop the timer
    	this.stopProcessing();
    	return Response
    			.ok()
    			.entity("{" +
                "\"error\" : \"" + this.error + "\", " +
                "\"error_msg\" : \"" + this.error_msg + "\", " +
                "\"data\" : \"" + this.data + "\", " +
                "\"processing_time\" : \"" + this.totalProcessingTime + "\"" +
                "}")
    			.build();
    }
    
    public String getStartTime() {
    	return this.startProcessing.toString();
    }
    
    public String getStopTime() {
    	return this.stopProcessing.toString();
    }
    
    public String getTotalProcessingTime() {
    	return this.totalProcessing.toString();
    }
    
    private void startProcessing() {
		this.startProcessing = Instant.now();
	}
	
	private void stopProcessing() {
		this.stopProcessing = Instant.now();
		this.totalProcessing = Duration.between(this.startProcessing, this.stopProcessing);
		
		
		this.setTotalProcessingTime("Total time was: " + this.totalProcessing.toString());
	}

}
