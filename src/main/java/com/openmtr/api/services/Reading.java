package com.openmtr.api.services;

import java.sql.DriverManager;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

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
		return uploadDate;
	}

	public void setDate(String date) {
		this.uploadDate = date;
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
	
	// creates and excecutes an insert statement
	public boolean insertReading(Connection azureData) {
		String columnValues = "(";
		String dataValues = "(";
		
		columnValues = columnValues + insertValueWorker(this.updatedBy, "updatedBy");
		dataValues = dataValues + insertDataWorker(this.updatedBy);
		
		columnValues = columnValues + insertValueWorker(this.updatedDate, "updatedDate");
		dataValues = dataValues + insertDataWorker(this.updatedDate);
		
		columnValues = columnValues + insertValueWorker(this.url, "url");
		dataValues = dataValues + insertDataWorker(this.url);
		
		columnValues = columnValues + insertValueWorker(this.meterType, "meterType");
		dataValues = dataValues + insertDataWorker(this.meterType);
		
		columnValues = columnValues + insertValueWorker(this.errorCode, "errorCode");
		dataValues = dataValues + insertDataWorker(this.errorCode);
		
		columnValues = columnValues + insertValueWorker(this.buildVersion, "buildVersion");
		dataValues = dataValues + insertDataWorker(this.buildVersion);
		
		columnValues = columnValues + insertValueWorker(this.read, "read");
		dataValues = dataValues + insertDataWorker(this.read);
		
		columnValues = columnValues + insertValueWorker(this.readMethod, "readMethod");
		dataValues = dataValues + insertDataWorker(this.readMethod);
		
		columnValues = columnValues + insertValueWorker(this.totalProcessingTime, "totalProcessingTime");
		dataValues = dataValues + insertDataWorker(this.totalProcessingTime);
		
		columnValues = columnValues + insertValueWorker(this.createdBy, "createdBy");
		dataValues = dataValues + insertDataWorker(this.createdBy);
		
		// take the last comment off the two strings so we get a good insert
		columnValues = peskyComma(columnValues);
		dataValues = peskyComma(columnValues);
		
		// add the strings together in the insertion string
		
		String insertion = "INSERT INTO reading(" + columnValues + ") VALUES (" + dataValues + ")";
		// try to submit the insert
		try {
			Statement stmt = azureData.createStatement();
			ResultSet rs = stmt.executeQuery(insertion);
			String logText = "";
			ResultSetMetaData rsmd = rs.getMetaData();
			
			while (rs.next()){
				int i = 0;
					while (i < rsmd.getColumnCount()){
						logText = logText + (rs.getString(i) + ",");
					}
				logText = logText + "\n";
			}
		} catch(Exception e){
			System.out.print("Error message: " + e.getMessage());
		  }
		//return boolean based on if it worked or not.		
	}
	
	// write string from result to database
	public void writeInsertLog(String logText){
		BufferedWriter writer = null;
		try{
			writer = new BufferedWriter( new FileWriter( "InsertLog.txt"));
			writer.write (logText);
		}
		catch( IOException e){
		}
		finally {
			try{
				if (writer != null){
					writer.close( );
				}
			} catch (IOException e){
				System.out.print("IO Error : " + e.getMessage());
			  }
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
	
	
	public String insertValueWorker(String value, String name) {
		String columnValues = "";
		if (value != null) {
			columnValues = columnValues + " " + name + ",";
			return columnValues;
		}
		
		else {
			columnValues = columnValues + "";
			return columnValues;
		}
	}
	
	// inserts a value into a string if it's not null
	public String insertDataWorker(String value) {
		String dataValues = "";
		if (value != null) {
			dataValues = dataValues + " " + value + ",";
			return dataValues;
		}
		
		else {
			dataValues = dataValues + "";
			return dataValues;
		}
	}
	// method to create insert statement
}
