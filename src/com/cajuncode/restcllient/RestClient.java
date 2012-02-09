package com.cajuncode.restcllient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;

public class RestClient {
	private static RestClient instance;
	
	private String host;
	private int port;
	private String schema;
	public RestClient(String schema, String host, int port){
		this.host = host;
		this.port = port;
		this.schema = schema;
		
		if (instance == null)
			instance = this;
	}
	public static RestClient sharedClient(){
		return instance;
	}
	
	public HttpResponse get(final String path,final Map<String, String> queryParams) {
		HttpResponse response = null;
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		for (String key: queryParams.keySet()){
			qparams.add(new BasicNameValuePair(key, queryParams.get(key)));
		}
		URI uri;
		try {
			uri = URIUtils.createURI(this.schema, this.host, this.port, path, 
			    URLEncodedUtils.format(qparams, "UTF-8"), null);
			
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet get = new HttpGet(uri);
		
			response =  httpclient.execute(get);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	
	public void get(final String path, final Map<String, String> queryParams, final RestResponseListener listener){
		GetAsyncTask pat = new GetAsyncTask();
		pat.execute(new RCRequest(path, queryParams, listener));
	}
	
	public HttpResponse post(final String path, final Map<String, String>params){
		HttpResponse response = null;
		HttpClient httpclient = new DefaultHttpClient();
		try {
			URI uri = URIUtils.createURI(this.schema, this.host, this.port, path, 
				    null, null);
		    HttpPost httppost = new HttpPost(uri);
	    
	        // Add your data
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(params.size());
	        for(String key: params.keySet()){
	        	nameValuePairs.add(new BasicNameValuePair(key, params.get(key)));
	        }
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	        // Execute HTTP Post Request
	        response = httpclient.execute(httppost);
	        
	        
	    } catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    } catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	
	public void post(final String path, final Map<String, String>params, final RestResponseListener listener){
		PostAsyncTask pat = new PostAsyncTask();
		pat.execute(new RCRequest(path, params, listener));
		
	}
	private class RCRequest{
		public String path;
		public  Map<String, String>params;
		public  RestResponseListener listener;
		public RCRequest(String path, Map<String, String> params,
				RestResponseListener listener) {
			super();
			this.path = path;
			this.params = params;
			this.listener = listener;
		}
		
	}
	
	private class PostAsyncTask extends AsyncTask<RCRequest, Integer , HttpResponse>{
		private RCRequest request;
		@Override
		protected HttpResponse doInBackground(RCRequest... requests) {
			request = requests[0];
			return post(requests[0].path, requests[0].params);
			
		}
		@Override
		protected void onPostExecute(HttpResponse result) {
			request.listener.processResponse(result);
			super.onPostExecute(result);
		}
		
	}
	private class GetAsyncTask extends AsyncTask<RCRequest, Integer , HttpResponse>{
		private RCRequest request;
		@Override
		protected HttpResponse doInBackground(RCRequest... requests) {
			request = requests[0];
			return RestClient.this.get(requests[0].path, requests[0].params);
		}
		@Override
		protected void onPostExecute(HttpResponse result) {
			request.listener.processResponse(result);
			super.onPostExecute(result);
		}
	}
}
