package edu.cis350.mosstalkwords;


import java.io.Serializable;
import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
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
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog.setTitle("Reset User Data");
		alertDialog
		.setMessage("Are you sure you want to reset all user data? This cannot be undone.")
        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	performUserReset();
            	AlertDialog.Builder showDialog1 = new AlertDialog.Builder(WelcomeActivity.this);
            	showDialog1
            	.setTitle("Successfully reset user data")
            	.setMessage("You have successfully reset all user data! You are required to create a new user")
            	
            	.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            		public void onClick(DialogInterface dialog, int id) {
            			dialog.cancel();
            			Intent gotoBaseMenu = new Intent(WelcomeActivity.this, BaseActivity.class);
            			startActivity(gotoBaseMenu);
            		}
            	})
            	.show();
            	
            }
        })
        .setNegativeButton("No", null).show();	 
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
		editor.putString("name", null);
		editor.putString("email", null);
		editor.putInt("userInitScore", 0);
		editor.commit();
	}
}
