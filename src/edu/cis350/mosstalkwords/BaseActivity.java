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

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.base);
	    Intent i=getIntent();
		login = (Button)findViewById(R.id.loginId);
		login.setOnClickListener(this);
		
	}
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent i = getIntent();
		if(v.getId() == login.getId())
		{
			EditText n = (EditText)findViewById(R.id.baseName);
			EditText e = (EditText)findViewById(R.id.baseEmail);
			String name = n.getText().toString();
			String email = e.getText().toString();
			if(name != null && email != null && !name.equals("") && !email.equals(""))
			{
				System.out.println(name + " " + email);
				i.putExtra("name", name);
				i.putExtra("email", email);
				setResult(RESULT_OK, i);
				
				//store name and email
				SharedPreferences userSettings=getSharedPreferences("userpreferences", MODE_PRIVATE);
				 SharedPreferences.Editor editor = userSettings.edit();
				 editor.putString("name", name);
				 editor.putString("email", email);
				 editor.commit();
				 

				Intent userEntry = new Intent(this, MainActivity.class);
				startActivity(userEntry);
					
			}	
		}
	}
}
