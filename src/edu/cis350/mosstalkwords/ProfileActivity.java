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
public class ProfileActivity extends Activity implements OnClickListener{

	Button mainMenu;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.profile);
	    //Intent i=getIntent();
	    mainMenu = (Button)findViewById(R.id.profileMenuId);
	    mainMenu.setOnClickListener(this);
	    populateFields();
	}
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == mainMenu.getId())
		{
			finish();
		}
	}
	
	public void populateFields()
	{
		TextView name = (TextView)findViewById(R.id.profileName);
	    TextView score = (TextView)findViewById(R.id.profileScore);
	    
		SharedPreferences userSettings=getSharedPreferences("userpreferences", MODE_PRIVATE);
		 SharedPreferences.Editor editor = userSettings.edit();
		 
	    name.setText(name.getText().toString() + userSettings.getString("name", "no name"));
	    score.setText(score.getText().toString() + "Score from DB");

	}
}
