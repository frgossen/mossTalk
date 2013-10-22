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
public class WelcomeActivity extends Activity implements OnClickListener {
	
	private Button word_quest;
	private Button category_list;
	private Button favorite;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);
		Intent i=getIntent();
		
		word_quest = (Button)findViewById(R.id.btnWordQuest);
		word_quest.setOnClickListener(this);
		
		category_list = (Button)findViewById(R.id.btnCategories);
		category_list.setOnClickListener(this);
		
		favorite = (Button)findViewById(R.id.btnFavourites);
		favorite.setOnClickListener(this);
	}
	
	public void onClick(View view) {
		Intent i = getIntent();
		if (view.getId() == word_quest.getId())
		{	 
			Intent wordQuestEntry = new Intent(this, DifficultLevel.class);	
			startActivity(wordQuestEntry);				
		}
		else if (view.getId() == category_list.getId()) {
			Intent categoryListEntry = new Intent(this, CategoryList.class);	
			startActivity(categoryListEntry);
		} 
		else if (view.getId() == favorite.getId()) {
			Intent favoriteEntry = new Intent(this, MainActivity.class);	
			startActivity(favoriteEntry);
		}
	}
	
}
