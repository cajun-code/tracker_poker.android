package com.cajuncode.trackerpoker;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RoomActivity extends Activity {
	private Dealer dealer;
	private EditText room_no;
	private RoomActivity self ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dealer = ((TrackerPokerApp)getApplication()).getDealer();
		setContentView(R.layout.room);
		self = this;
		room_no = (EditText)findViewById(R.id.room_no);
		Button button = (Button)findViewById(R.id.scan);
		button.setOnClickListener(scanButtonListener);
		button = (Button)findViewById(R.id.go_to_room);
		button.setOnClickListener(roomButtonListener);
		button = (Button)findViewById(R.id.cancel_room);
		button.setOnClickListener(cancelButtonListener);
		
	}
	protected OnClickListener scanButtonListener = new OnClickListener(){

		public void onClick(View v) {
			IntentIntegrator integrator = new IntentIntegrator(self);
			 integrator.initiateScan();
		}
	};
	protected OnClickListener cancelButtonListener = new OnClickListener(){

		public void onClick(View v) {
			closePage();
		}
	};
	protected OnClickListener roomButtonListener = new OnClickListener(){

		public void onClick(View v) {
			dealer.setRoom(room_no.getText().toString());
			dealer.joinRoom();
				
			closePage();
		}
	};
	protected void closePage(){
		this.finish();
	}
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		 IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		 if (scanResult != null) {
			 room_no.setText(scanResult.getContents());
		 }
	}
	
}
