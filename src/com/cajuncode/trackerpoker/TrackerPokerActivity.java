package com.cajuncode.trackerpoker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TrackerPokerActivity extends Activity {
	private Dealer dealer = Dealer.getInstance();
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
			
			break;
		}
		return true;
	}

	protected OnClickListener showCardButtonListener = new OnClickListener(){

		public void onClick(View v) {
			// TODO Auto-generated method stub
			Button b = (Button)v;
			dealer.setVote(b.getText().toString());
			Intent addNewContact =
					new Intent(TrackerPokerActivity.this, CardActivity.class);
					startActivity(addNewContact); 
		}
		
	};
}