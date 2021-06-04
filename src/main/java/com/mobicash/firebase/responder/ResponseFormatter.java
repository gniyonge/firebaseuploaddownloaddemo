package com.mobicash.firebase.responder;

public class ResponseFormatter {
	
private String title;
private Exception responseException;
private String responseDescription;



public ResponseFormatter(String title, String responseDescription) {
	super();
	this.title = title;
	this.responseDescription = responseDescription;
}


public ResponseFormatter(String title, Exception responseException, String responseDescription) {
	super();
	this.title = title;
	this.responseException = responseException;
	this.responseDescription = responseDescription;
}


public String getTitle() {
	return title;
}

public void setTitle(String title) {
	this.title = title;
}

public String getResponseDescription() {
	return responseDescription;
}

public void setResponseDescription(String responseDescription) {
	this.responseDescription = responseDescription;
}

public Exception getResponseException() {
	return responseException;
}

public void setResponseException(Exception responseException) {
	this.responseException = responseException;
}


}
