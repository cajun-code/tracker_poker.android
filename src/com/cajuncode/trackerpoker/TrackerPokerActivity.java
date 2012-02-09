package com.cajuncode.trackerpoker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class TrackerPokerActivity extends Activity {
	private Dealer dealer;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dealer = ((TrackerPokerApp)getApplication()).getDealer();
        setContentView(R.layout.main);
        
        Button b  = (Button)findViewById(R.id.button1);
        b.setOnClickListener(showCardButtonListener);
        b  = (Button)findViewById(R.id.button2);
        b.setOnClickListener(showCardButtonListener);
        b  = (Button)findViewById(R.id.button3);
        b.setOnClickListener(showCardButtonListener);
        b  = (Button)findViewById(R.id.button4);
        b.setOnClickListener(showCardButtonListener);
        b  = (Button)findViewById(R.id.button5);
        b.setOnClickListener(showCardButtonListener);
        b  = (Button)findViewById(R.id.button6);
        b.setOnClickListener(showCardButtonListener);
        b  = (Button)findViewById(R.id.button7);
        b.setOnClickListener(showCardButtonListener);
        b  = (Button)findViewById(R.id.button8);
        b.setOnClickListener(showCardButtonListener);
        b  = (Button)findViewById(R.id.button9);
        b.setOnClickListener(showCardButtonListener);
        b  = (Button)findViewById(R.id.Button01);
        b.setOnClickListener(showCardButtonListener);
        b  = (Button)findViewById(R.id.Button02);
        b.setOnClickListener(showCardButtonListener);
        b  = (Button)findViewById(R.id.Button03);
        b.setOnClickListener(showCardButtonListener);
        b  = (Button)findViewById(R.id.Button06);
        b.setOnClickListener(showCardButtonListener);
        
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		MenuInflater inf = getMenuInflater();
		inf.inflate(R.menu.tracker_poker_menu, menu);
		return true;
	}
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		super.onOptionsItemSelected(item);
		switch (item.getItemId()){
		case R.id.join_room:
			Intent room_activity = new Intent(TrackerPokerActivity.this, RoomActivity.class);
			startActivity(room_activity);
			break;
		case R.id.login:
			Intent login_activity = new Intent(TrackerPokerActivity.this, LoginActivity.class);
			startActivity(login_activity);
			break;
		}
		return true;
	}

	protected OnClickListener showCardButtonListener = new OnClickListener(){

		public void onClick(View v) {
			// TODO Auto-generated method stub
			Button b = (Button)v;
			dealer.setVote(b.getText().toString());
			dealer.submitVote();
				
			Intent addNewContact =
					new Intent(TrackerPokerActivity.this, CardActivity.class);
					startActivity(addNewContact); 
		}
		private static final String TAG = "TrackerPoker";
		public Handler _handler = new Handler() {
			
			@Override
			public void handleMessage(Message msg) {
				Log.d(TAG, String.format("Handler.handleMessage(): msg=%s", msg));
	            // This is where main activity thread receives messages
	            // Put here your handling of incoming messages posted by other threads
				super.handleMessage(msg);
			}

		};
	};
}