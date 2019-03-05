package com.openmtr.api.services;

import java.sql.DriverManager;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class Reading {

	public static final String DATAURL = System.getenv("JDBC-SQL-Azure-OpenMTR");
	

	/*
	 * database layout: int Id(auto increment), nchar (255) url NOT NULL,
	 * yyyy-mm-dd uploadDate NOT NULL nchar (10) buildVersion NOT NULL nchar (10)
	 * read NOT NULL nchar (25) readMethod NOT NULL nchar (25) meterType
	 * totalProcessingTime nchar (15) NOT NULL nchar (50) errorCode bit success NOT
	 * NULL (DEFAULT 0) createdBy Nchar(30) NOT NULL createdDate Date NOT NULL
	 * updatedBy nchar(30) NULL updatedDate Date NULL
	 */

	private String url;
	private String uploadDate;
	private String buildVersion;
	private String read;
	private String readMethod;
	private String meterType;
	private String totalProcessingTime;
	private String errorCode;
	private boolean success;
	private String createdBy;
	private String createdDate;
	private String updatedBy;
	private String updatedDate;

	/*
	 * default/no argument constructor
	 */
	public Reading() {
	}

	/*
	 * constructor with all values required
	 * 
	 * @params url, uploadDate, buildVersion, read, readMethod totalProcessingTime,
	 * success boolean, createdBy, createdDate (yyyy-mm-dd) renders meterType,
	 * errorCode, updatedBy, and updatedDate null this will be the most commonly
	 * used constructor
	 */
	public Reading(String url, String uploadDate, String buildVersion, String read, String readMethod,
			String totalProcessingTime, boolean success, String createdBy, String createdDate) {
		this.url = url;
		this.uploadDate = uploadDate;
		this.buildVersion = buildVersion;
		this.read = read;
		this.readMethod = readMethod;
		this.totalProcessingTime = totalProcessingTime;
		this.success = success;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.meterType = null;
		this.errorCode = null;
		this.updatedBy = null;
		this.updatedDate = null;
	}

	/*
	 * constructor including a meter type
	 * 
	 * @params url, date, buildVersion, read, readMethod totalProcessingTime,
	 * success boolean, createdBy, createdDate (yyyy-mm-dd) renders errorCode,
	 * updatedBy, and updatedDate null
	 */
	public Reading(String url, String uploadDate, String buildVersion, String read, String readMethod,
			String totalProcessingTime, boolean success, String createdBy, String createdDate, String meterType) {
		this.url = url;
		this.uploadDate = uploadDate;
		this.buildVersion = buildVersion;
		this.read = read;
		this.readMethod = readMethod;
		this.totalProcessingTime = totalProcessingTime;
		this.success = success;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.meterType = meterType;
		this.errorCode = null;
		this.updatedBy = null;
		this.updatedDate = null;
	}

	/*
	 * constructor including a meterType and errorCode
	 * 
	 * @params url, date, buildVersion, read, readMethod totalProcessingTime,
	 * success boolean, createdBy, createdDate (yyyy-mm-dd), meterType, errorCode
	 * renders updatedBy, and updatedDate null
	 */
	public Reading(String url, String uploadDate, String buildVersion, String read, String readMethod,
			String totalProcessingTime, boolean success, String createdBy, String createdDate, String meterType,
			String errorCode) {
		this.url = url;
		this.uploadDate = uploadDate;
		this.buildVersion = buildVersion;
		this.read = read;
		this.readMethod = readMethod;
		this.totalProcessingTime = totalProcessingTime;
		this.success = success;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.meterType = meterType;
		this.errorCode = errorCode;
		this.updatedBy = null;
		this.updatedDate = null;
	}
	
	// default update constructor
	public Reading(String updatedBy, String updatedDate) {
		this.updatedBy = updatedBy;
		this.updatedDate = updatedDate;
		this.url = null;
		this.createdDate = null;
		this.meterType = null;
		this.errorCode = null;
		this.buildVersion = null;
		this.read = null;
		this.readMethod = null;
		this.totalProcessingTime = null;
		this.createdBy = null;
	}
		

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getBuildVersion() {
		return buildVersion;
	}

	public void setBuildVersion(String buildVersion) {
		this.buildVersion = buildVersion;
	}

	public String getRead() {
		return read;
	}

	public void setRead(String read) {
		this.read = read;
	}

	public String getReadMethod() {
		return readMethod;
	}

	public void setReadMethod(String readMethod) {
		this.readMethod = readMethod;
	}

	public String getTotalProcessingTime() {
		return totalProcessingTime;
	}

	public void setTotalProcessingTime(String totalProcessingTime) {
		this.totalProcessingTime = totalProcessingTime;
	}

	public boolean getsuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getMeterType() {
		return this.meterType;
	}

	public void setMeterType(String meterType) {
		this.meterType = meterType;
	}

	public String getErrorCode() {
		return this.errorCode;
	}
	
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	

	// connect to the azure sql database
	// if connection doesn't work, thrown an error
	public static Connection getConnection() {
		try {
			if (DATAURL != null) {
				return DriverManager.getConnection(DATAURL);
			}
			else throw new RuntimeException("No connection string was provided.");
		} catch (SQLException ex) {
			throw new RuntimeException("Something went wrong connecting to the database.", ex);
		}
	}
	
	public boolean insertReading(Connection azureData) {
		String columnValues = "(";
		String dataValues = "(";
		if (this.updatedBy != null) {
			columnValues = columnValues + " updatedBy,";
			dataValues = dataValues + " " + this.updatedBy + ",";
		}
		else {
			columnValues = columnValues + "";
			dataValues = dataValues + "";
		}
		if (this.updatedDate != null) {
			columnValues = columnValues + " updatedDate,";
			dataValues = dataValues + " " + this.updatedDate + ",";
		}
		else {
			columnValues = columnValues + "";
			dataValues = dataValues + "";
		}
		if (url != null) {
			columnValues = columnValues + " url,";
			dataValues = dataValues + " " + this.url + ",";
		}
		else {
			columnValues = columnValues + "";
			dataValues = dataValues + "";
		}
		if (meterType != null) {
			columnValues = columnValues + " meterType,";
			dataValues = dataValues + " " + this.meterType + ",";
		}
		else {
			columnValues = columnValues + "";
			dataValues = dataValues + "";
		}
		if (this.errorCode != null) {
			columnValues = columnValues + " errorCode,";
			dataValues = dataValues + " " + this.errorCode + ",";
		}
		else {
			columnValues = columnValues + "";
			dataValues = dataValues + "";
		}
		if (this.buildVersion != null) {
			columnValues = columnValues + " buildVersion,";
			dataValues = dataValues + " " + this.buildVersion + ",";
		}
		else {
			columnValues = columnValues + "";
			dataValues = dataValues + "";
		}
		
		if (read != null) {
			columnValues = columnValues + " read,";
			dataValues = dataValues + " " + this.read + ",";
		}
		
		else {
			columnValues = columnValues + "";
			dataValues = dataValues + "";
		}
		
		if (this.readMethod != null) {
			columnValues = columnValues + " readMethod,";
			dataValues = dataValues + " " + this.readMethod + ",";
		}
		
		else {
			columnValues = columnValues + "";
			dataValues = dataValues + "";
		}
		
		if (this.totalProcessingTime != null) {
			columnValues = columnValues + " totalProcessingTime,";
			dataValues = dataValues + " " + this.totalProcessingTime + ",";
		}
		
		else {
			columnValues = columnValues + "";
			dataValues = dataValues + "";
		}
		
		if (this.createdBy != null) {
			columnValues = columnValues + " createdBy,";
			dataValues = dataValues + " " + this.createdBy + ",";
		}
		
		else {
			columnValues = columnValues + "";
			dataValues = dataValues + "";
		}
		
		// take the last comment off the two strings so we get a good insert
		columnValues = peskyComma(columnValues);
		dataValues = peskyComma(columnValues);
		
		// add the strings together in the insertion string
		
		//String insertion = "INSERT INTO reading(
		
		try {
		
		}
	}
	
	// gets rid of that pesky comma on the end of an aggregate value or data string.
	public String peskyComma(String commaString) {
		if (commaString.charAt(commaString.length() - 1) == ',') {
			commaString = commaString.substring(0, commaString.length() - 1);
			return commaString;
		}
		else {
			return commaString;
		}
	}
	
	
	
	
	// method to create insert statement
}
