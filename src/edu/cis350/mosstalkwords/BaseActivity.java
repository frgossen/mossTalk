package edu.cis350.mosstalkwords;


import java.io.Serializable;
import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class BaseActivity extends Activity implements OnClickListener{

	Button login;

	EditText n;
	EditText e;
	//store name and email
	SharedPreferences userSettings;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.base);
	    Intent i=getIntent();
		login = (Button)findViewById(R.id.loginId);
		login.setOnClickListener(this);
		
		n = (EditText)findViewById(R.id.baseName);
		e = (EditText)findViewById(R.id.baseEmail);
		
		userSettings=getSharedPreferences("UserPreferences", MODE_PRIVATE);
		n.setText(userSettings.getString("name123", ""));
		e.setText(userSettings.getString("email123", ""));
	}
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent i = getIntent();
		if(v.getId() == login.getId())
		{
			 SharedPreferences.Editor editor = userSettings.edit();
			
			String name = n.getText().toString();
			String email = e.getText().toString();
			if(name != null && email != null && !name.equals("") && !email.equals(""))
			{
				setResult(RESULT_OK, i);
				
				 editor.putString("name123", name);
				 editor.putString("email123", email);
				 
				 editor.commit();
				 

				Intent userEntry = new Intent(this, MainActivity.class);
				startActivity(userEntry);
					
			}	
		}
	}
}
