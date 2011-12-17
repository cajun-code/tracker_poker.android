package com.cajuncode.trackerpoker;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Button;
public class CardActivity extends Activity {
	private Dealer dealer = Dealer.getInstance();
	private TextView display ;
	private Button closeCard;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.card);
		
		display = (TextView) findViewById(R.id.card_display);
		display.setText(dealer.getVote());
		
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
