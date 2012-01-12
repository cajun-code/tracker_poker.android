package com.cajuncode.trackerpoker;

public class Dealer {
	private static Dealer instance;
	
	public static Dealer getInstance(){
		if (instance == null){
			instance = new Dealer();
		}
		return instance;
	}
	
	private Dealer(){}
	
	private String vote;
	private String room;
	private String token;
	
	public boolean authenicate(String username, String password){
		boolean result = false;
		
		return result;
	}
	public void submitVote(){
		
		
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
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	public boolean isAuthenicated(){
		return token != null;
	}
	public boolean isInRoom(){
		return room != null;
	}
}
