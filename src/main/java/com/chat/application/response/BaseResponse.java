package com.chat.application.response;

import java.util.Map;

/**
 * the default payload for sending responses of all http requests
 * @since 1.0
 * @author JOSWAL
 */
public class BaseResponse {

	private int statusCode;

	private String description;

	private Object data;

	private Object errors;

	private Map<String, Object> additionalData;

	public BaseResponse() {
	}

	public BaseResponse(int statusCode, String description) {
		super();
		this.statusCode = statusCode;
		this.description = description;
	}

	public BaseResponse(int statusCode) {
		super();
		this.statusCode = statusCode;
	}

	public BaseResponse(int statusCode, String description, Object data, Map<String, Object> additionalData) {
		this.statusCode = statusCode;
		this.description = description;
		this.data = data;
        this.additionalData = additionalData;
    }

	/**
	 * get the data for the response payload
	 * @return the data or null value if no data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * set the description of the result of current http request
	 * @return the description of the result of current request
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * get the errors that occurred during the current request
	 * @return the list of errors that occurred or null if no error
	 */
	public Object getErrors() {
		return errors;
	}

	/**
	 * get the value of the HTTP status code for the response payload
	 * @return statusCode the HTTP status code
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * set the data for the response payload
	 * @param data the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * set the description of the result of current http request
	 * @param description the description of the result of current http request
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * set the errors that occurred during the current request
	 * @param errors the errors that occurred during the current request
	 */
	public void setErrors(Object errors) {
		this.errors = errors;
	}

	/**
	 * set the value of the HTTP status code for the response payload
	 * @param statusCode the value of the HTTP status code for the response payload
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public Map<String, Object> getAdditionalData() {
		return additionalData;
	}

	public void setAdditionalData(Map<String, Object> additionalData) {
		this.additionalData = additionalData;
	}
}