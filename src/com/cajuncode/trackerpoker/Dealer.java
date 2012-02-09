package com.cajuncode.trackerpoker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.HttpEntity;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import com.cajuncode.restcllient.RestClient;
import com.cajuncode.restcllient.RestResponseListener;
import com.cajuncode.trackerpoker.utils.StringTemplate;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;


public class Dealer {
	private static Dealer instance;
	
	public static Dealer getInstance(Context context){
		if (instance == null){
			instance = new Dealer(context);
		}
		return instance;
	}
	public static int MSG_DEALER_RESPONSE_RESULT = 1000;
	
	private static SharedPreferences prefs;
	private static Context context;
	private static final String PREFERENCE_KEY = "TrackerPokerPreferences";
	private static final String EMAIL_KEY = "TrackerPokerAuthEmail";
	private static final String TOKEN_KEY = "TrackerPokerAuthToken";
	private static final String SCHEME = "http"; 
//	private static final String HOST = "10.0.2.2";
//	private static final int PORT = 3000; // to Not include port set to -1
	private static final String HOST = "tracker-poker.herokuapp.com"; // 
	private static final int PORT = -1; // 3000; // to Not include port set to -1
	private static final String LOGIN_PATH = "/token/fetch";
	private static final String ROOM_PATH = "/room/<room_id>/join"; // "/room/:id/join"
	private static final String STORY_PATH = "/room/<room_id>/active_story"; // "/room/:id/active_story"
	private static final String VOTE_PATH = "/room/<room_id>/story/<id>/vote"; // "/room/:room_id/story/:id/vote"

	
	private String vote;
	private String room;
	private String token;
	private String email;
	private String story;
	
	private Dealer(Context context){
		this.context = context;
		prefs = context.getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE);
		
		RestClient client = new RestClient(SCHEME, HOST, PORT);
	} 
	
	public void authenicate(String username, String password){
		boolean result = false;
		RestClient client = RestClient.sharedClient();
		StringTemplate url = new StringTemplate(LOGIN_PATH);
		//url.add("base", BASE_URL);
		System.out.println(url.render());
		
	    setEmail(username);
    
        // Add your data
        Map<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("password", password);
        client.post(url.render(), params, authResponseListener);

		
	}
	private RestResponseListener authResponseListener = new RestResponseListener() {
		
		public void processResponse(HttpResponse response) {
			// TODO Auto-generated method stub
			System.out.println(response.getStatusLine());
	        if( response.getStatusLine().getStatusCode() == 200){
	        	try {
			        HttpEntity entity = response.getEntity();
			        BufferedReader in
		        		= new BufferedReader(new InputStreamReader(entity.getContent()));
		        
					setToken(in.readLine());
					Dealer.this.toast_result("Login Successful");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		  
	        }else
	        	Dealer.this.toast_result("Login Failed");
		}
	}; 
	
	public boolean submitVote(){
		boolean result = false;
		Map<String, String> qparams = new HashMap<String, String>();
		qparams.put("auth_token", this.getToken());
		result = retrieve_story(qparams);
		if(result && !this.story.equals("-1"))
			post_vote(qparams);
		if( this.story.equals("-1")){
			result = false;
			toast_result("Voting is closed");
		}
		return result;
	}
	private void post_vote(Map<String, String> params) {
		
		StringTemplate url = new StringTemplate(VOTE_PATH);
		//url.add("base", BASE_URL);
		url.add("room_id", this.room);
		url.add("id", this.story);
		System.out.println(url.render());
		params.put("score", this.vote);
		RestClient client = RestClient.sharedClient();
		client.post(url.render(), params, storyResponseListener);

	}

	private boolean retrieve_story(Map<String, String> params) {
		boolean result = false;
		StringTemplate url = new StringTemplate(STORY_PATH);
		//url.add("base", BASE_URL);
		url.add("room_id", this.room);
		System.out.println(url.render());
		RestClient client = RestClient.sharedClient();
		
		HttpResponse response = client.get(url.render(), params);
		if( response.getStatusLine().getStatusCode() == 200){
			HttpEntity entity = response.getEntity();
			try{
	        BufferedReader in
	        	= new BufferedReader(new InputStreamReader(entity.getContent()));
	        setStory(in.readLine());
			result = true;
			}catch (IOException e){}
		}
		
		return result;
		
	}
	
	private RestResponseListener storyResponseListener = new RestResponseListener() {

		public void processResponse(HttpResponse response) {
			// Execute HTTP Post Request
	        System.out.println(response.getStatusLine());
	        if( response.getStatusLine().getStatusCode() == 200){
		        
	        	Dealer.this.toast_result(
						"Vote: " + getVote() + " to Story: "+ getStory());
	        }else
	        	Dealer.this.toast_result("Could not place vote for story "+ getStory()); 
			
		}
		
	};
	
	public void joinRoom(){
		boolean result = false;
		StringTemplate url = new StringTemplate(ROOM_PATH);
		RestClient client = RestClient.sharedClient();
		url.add("room_id", this.room);
		Map<String, String> qparams = new HashMap<String, String>();
		qparams.put("auth_token", this.getToken());
		client.get(url.render(), qparams, joinResponseListener);
	}
	
	private RestResponseListener joinResponseListener = new RestResponseListener() {

		public void processResponse(HttpResponse response) {
			// TODO Auto-generated method stub
			if( response.getStatusLine().getStatusCode() == 200){
				//result = true;
				 Dealer.this.toast_result("Room Joined");
			}
		}
		
	};
	
	private void toast_result(String message){
		Toast.makeText(context, message, 1500).show();
	}
	
	private void setPref(String name, String value){
		Editor mEditor = prefs.edit(); 
		mEditor.putString(name,value); 
		mEditor.commit();
	}
	public String getVote() {
		return vote;
	}

	public void setVote(String vote) {
		this.vote = vote;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getToken() {
		if (token == null)
			token = prefs.getString(TOKEN_KEY, null);
		return token;
	}

	public void setToken(String token) {
		if(!token.equals(this.token)){
			setPref(TOKEN_KEY, token);
			this.token = token;
			System.out.println("Token: " + token);
		}
	}
	
	public void setEmail(String email) {
		if(!email.equals(this.email)){
			setPref(EMAIL_KEY, email);
			this.email = email;
			System.out.println("Email: " + email);
		}
	}
	public String getEmail(){
		if(this.email == null)
			this.email = prefs.getString(EMAIL_KEY, null);
		return email;
	}
	
	
	
	public String getStory() {
		return story;
	}

	public void setStory(String story) {
		this.story = story;
	}

	public boolean isAuthenicated(){
		return token != null;
	}
	public boolean isInRoom(){
		return room != null;
	}
}
