package edu.cis350.mosstalkwords;


import java.io.Serializable;
import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
public class WelcomeActivity extends Activity {
	
	private Button word_quest;
	private Button category_list;
	private Button favorite;
	private SharedPreferences userSettings;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);
		userSettings = getSharedPreferences("UserPreferences", MODE_PRIVATE);
		TextView v = (TextView) findViewById(R.id.txtScore);
		v.setText("Score = " + userSettings.getInt("totalScore", 0));
	}
	
	public void resetUserData(View v){
		/*
		new AlertDialog.Builder(this)
		.setTitle("Reset user data")
		.setMessage("Are you sure you want to reset all user data? This cannot be undone.")
        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	performUserReset();
            	new AlerDialog.Builder(this)
            	.setTitle("Successfully reset")
            	.setMessage("All user data has been successfully reset. ")
            	.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            		
            	}).show();
            }
        })
        .setNegativeButton("No", null).show();	
        */
	}
	
	public void openWordQuest(View v){
		
	}
	
	public void openFavourites(View v){
		
	}
	
	public void openCategories(View v){
		Intent wordQuestEntry = new Intent(this, CategoryList.class);	
		startActivity(wordQuestEntry);				
	}
	
	private void performUserReset(){
		SharedPreferences.Editor editor = userSettings.edit();
		editor.putString("userName", "");
		editor.putString("userEmail", "");
		editor.commit();
	}
}
