package com.openmtr.api.services;

import java.sql.DriverManager;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class Reading {

	public static final String DATAURL = "jdbc:sqlserver://openmeter-data.database.windows.net:1433;database=Openmtr;user=openmtr-admin@openmeter-data;password=OMeter@@99;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30";
	// public static final String URL =
	// "jdbc:sqlserver://openmeter-data.database.windows.net:1433";
	// public static final STring database = "Openmtr";
	// public static final String USER = "openmtr-admin@openmeter-data" ;
	// public static final String PASSWORD = "OMeter@@99";
	// encrypt=true;
	// trustServerCertificate=false;
	// hostNameInCertificate=*.database.windows.net;
	// loginTimeout=30;

	/*
	 * database layout: int (auto increment) nchar (255) url NOT NULL date
	 * yyyy-mm-dd uploadDate NOT NULL nchar (10) buildVersion NOT NULL nchar (10)
	 * read NOT NULL nchar (25) readMethod NOT NULL nchar (25) meterType
	 * totalProcessingTime nchar (15) NOT NULL nchar (50) errorCode bit success NOT
	 * NULL (DEFAULT 0) createdBy Nchar(30) NOT NULL createdDate Date NOT NULL
	 * updatedBy nchar(30) NULL updatedDate Date NULL
	 */

	private String url;
	private String date;
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
	 * @params url, date, buildVersion, read, readMethod totalProcessingTime,
	 * success boolean, createdBy, createdDate (yyyy-mm-dd) renders meterType,
	 * errorCode, updatedBy, and updatedDate null this will be the most commonly
	 * used constructor
	 */
	public Reading(String url, String date, String buildVersion, String read, String readMethod,
			String totalProcessingTime, boolean success, String createdBy, String createdDate) {
		this.url = url;
		this.date = date;
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
	public Reading(String url, String date, String buildVersion, String read, String readMethod,
			String totalProcessingTime, boolean success, String createdBy, String createdDate, String meterType) {
		this.url = url;
		this.date = date;
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
	public Reading(String url, String date, String buildVersion, String read, String readMethod,
			String totalProcessingTime, boolean success, String createdBy, String createdDate, String meterType,
			String errorCode) {
		this.url = url;
		this.date = date;
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

	// connect to the azure sql databasef
	// if connection doesn't work, thrown an error
	public static Connection getConnection() {
		try {

			return DriverManager.getConnection(DATAURL);
		} catch (SQLException ex) {
			throw new RuntimeException("Something went wrong connecting to the database.", ex);
		}
	}
}
