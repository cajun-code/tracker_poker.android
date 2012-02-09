package com.cajuncode.trackerpoker;

import android.app.Application;

public class TrackerPokerApp extends Application {
	private Dealer dealer;
	@Override
	public void onCreate() {
		
		super.onCreate();
		dealer = Dealer.getInstance(this);
	}
	public Dealer getDealer() {
		return dealer;
	}
	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}
	
}
