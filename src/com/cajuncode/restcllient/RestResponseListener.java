package com.cajuncode.restcllient;

import org.apache.http.HttpResponse;

public interface RestResponseListener {
	public void processResponse(HttpResponse response);
}
