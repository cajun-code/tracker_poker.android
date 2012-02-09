package com.cajuncode.trackerpoker;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	
	private Dealer dealer;
	EditText email;
	EditText password;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		dealer = ((TrackerPokerApp)getApplication()).getDealer();
		setContentView(R.layout.login);
		 email = (EditText)findViewById(R.id.email);
		 if (dealer.getEmail() != null)
			email.setText(dealer.getEmail());
		 password =(EditText)findViewById(R.id.password);
		 if (dealer.getToken() != null)
			password.setText(dealer.getToken());
		 Button login = (Button)findViewById(R.id.login_process);
		 Button cancel = (Button)findViewById(R.id.login_cancel);
		 login.setOnClickListener(loginButtonListener);
		 cancel.setOnClickListener(cancelButtonListener);
		 
	}
	protected OnClickListener loginButtonListener = new OnClickListener(){

		public void onClick(View v) {
			// TODO Auto-generated method stub
			dealer.authenicate(email.getText().toString(), password.getText().toString());
				
			closePage();
		}
		
	};
	protected OnClickListener cancelButtonListener = new OnClickListener(){

		public void onClick(View v) {
			// TODO Auto-generated method stub
			closePage();
		}
		
	};
	protected void closePage(){
		this.finish();
	}
}
