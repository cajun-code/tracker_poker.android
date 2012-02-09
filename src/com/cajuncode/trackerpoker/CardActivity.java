package com.cajuncode.trackerpoker;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Button;
public class CardActivity extends Activity {
	private static final String TAG = "Tracker Poker Card Activity";
	private Dealer dealer;
	private ImageView display ;
	private Button closeCard;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		dealer = ((TrackerPokerApp)getApplication()).getDealer();
		setContentView(R.layout.card);
		
		display = (ImageView) findViewById(R.id.card_image);
		AssetManager assets = getAssets(); 
		InputStream stream; 
		try
		{
			stream = assets.open( dealer.getVote().toLowerCase() + ".png");
			Drawable card = Drawable.createFromStream(stream, dealer.getVote());
			
			display.setImageDrawable(card);
			display.setOnClickListener(backButtonListener);
		}
		catch (IOException e) {
			Log.e(TAG, "Error loading " + dealer.getVote(), e);
		} 
		
		closeCard = (Button)findViewById(R.id.card_close);
		closeCard.setOnClickListener(backButtonListener);
	}
	protected OnClickListener backButtonListener = new OnClickListener(){

		public void onClick(View v) {
			// TODO Auto-generated method stub
			closeCard();
		}
		
	};
	protected void closeCard(){
		this.finish();
	}
	
}
