package edu.cis350.mosstalkwords;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.os.Bundle;
import android.os.Parcelable;

public class EndSetActivity extends Activity {

	GridView gridView;
	ImageAdapter adapter; 
	Context mContext = this;
	DatabaseHandler db;
	AddFavoriteStimuli addFavoriteStimuli;
	
	private int mode;
	private String categoryName; 
	private SetStatistics currentSetStatistics;
	private ArrayList<Image> currentSet;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
 		super.onCreate(savedInstanceState);
		setContentView(R.layout.end_dialog);//.favorite_gridview);

		db = new DatabaseHandler(this);
		addFavoriteStimuli = new AddFavoriteStimuli(db);
		
	    //LayoutInflater inflater = this.getLayoutInflater();//(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    //View layout = inflater.inflate(R.layout.dialog_endset, null);
	    //View layout = inflater.inflate(R.layout.end_dialog, null);
	    
	//	gridView = (GridView) findViewById(R.id.favorite_grid);
		gridView = (GridView) this.findViewById(R.id.gridview);
		
	    //list of string names, results
	    Intent i = getIntent();
	    categoryName = i.getStringExtra("categoryName"); 
		currentSetStatistics = (SetStatistics) i.getParcelableExtra("currentSetStatistics");
		
		
		currentSet = i.<Image>getParcelableArrayListExtra("currentSet");
		System.out.println("size is here " + currentSet.size());
		
		
	//	currentSet = (Set) i.getParcelableExtra("currentSet");
		mode = i.getIntExtra("mode", -1);
	    
	    //int[] results = currentSetStatistics.getImageSetScore();
	    //Context ctx, String[] res, String[] imn
	    
		String[] imageWords = new String[currentSet.size()];
		
		for (int j = 0; j < currentSet.size(); j++) {
			imageWords[j] = currentSet.get(j).getWord();
		}
		
	    adapter = new ImageAdapter(this, currentSetStatistics.getScores(), imageWords);
		gridView.setAdapter(adapter);
		/*
		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				System.out.println(getResources().getColor(android.R.color.holo_green_light));
				v.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
				
				ImageView iv = (ImageView)v.findViewById(R.id.item_image);
				
				TextView tv = (TextView)v.findViewById(R.id.item_image_label);
				tv.setText("ASDASd");
				CheckBox cb = (CheckBox)v.findViewById(R.id.item_checkbox);
				System.out.println(cb.isChecked() + " " + position);
			}
		});
		
		
		//System.out.println(((CheckBox)gridView.findViewById(R.id.item_checkbox)).isChecked());
		
		for(int i = 0; i < 20; i++)
		{
			View v = (View)gridView.getAdapter().getView(0, null, null);
			CheckBox ba = (CheckBox)v.findViewById(R.id.item_checkbox);
			System.out.println(ba.isChecked());
			
		}
		 */
		
		RatingBar score = (RatingBar) this.findViewById(R.id.scoreBar);
	    int starScore = currentSetStatistics.getStarScore();
	    //System.out.println("scNull:"+score);
	    score.setNumStars(SetStatistics.NUM_STARS);
	    score.setRating(starScore);
	    
	    TextView message=(TextView) this.findViewById(R.id.Message);
	    String msg;
	    switch (starScore) {
	    	case 1: msg = getString(R.string.one_star); break;
	    	case 2: msg = getString(R.string.two_star); break;
	    	case 3: msg = getString(R.string.three_star); break;
	    	case 4: msg = "Four stars. Good job, but don't get cocky."; break;
	    	case 5: msg = "Five stars. If the picture was you, the word would be \"awesome\""; break;
	    	default: msg = "";
	    }
	    message.setText(msg);
	    
	    TextView completeness=(TextView) this.findViewById(R.id.Completeness);
	    String completenessPercent = "" + currentSetStatistics.getCompleteness();
	    completeness.setText(completeness.getText() + completenessPercent+"%");
	    
	    TextView streak=(TextView) this.findViewById(R.id.streak);
	    String longestStreak= new Integer(currentSetStatistics.getLongestStreak()).toString();
	    streak.setText(streak.getText()+longestStreak);
	    
	    TextView setScoreNum = (TextView) this.findViewById(R.id.endScore);
	    setScoreNum.setText(setScoreNum.getText().toString() + this.getIntent().getExtras().getInt("setScore")); 

	    TextView totalScoreNum = (TextView) this.findViewById(R.id.endScoreTotal);
	    totalScoreNum.setText(totalScoreNum.getText().toString() + currentSetStatistics.getTotalScore()); 

	    /*
			AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
			builder.setCancelable(false);
			builder.setTitle("Congratulations!")//hackish extra spaces to center the title since not an option
				.setView(layout)
				.setPositiveButton(R.string.send, new DialogInterface.OnClickListener()
				{
					boolean checked[] = adapter.getChecked();

					public void onClick(DialogInterface dialog, int id)
					{
						//adapter checkbox
						for(int i = 0; i < 20; i++)
						{
							System.out.println(adapter.getItem(i));
						}
						//restart set
						Intent i=getIntent();
						i.putExtra("Send", true);
						i.putExtra("No", false);
						setResult(RESULT_OK, i);
						finish();
					}
				})
				.setNegativeButton(R.string.no, new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int id)
					{
						boolean checked[] = adapter.getChecked();

						//adapter checkbox
						for(int i = 0; i < checked.length; i++)
						{
							
						}
	
						//go to main menu
						Intent i=getIntent();
						i.putExtra("Send", false);
						i.putExtra("No", true);
						setResult(RESULT_OK, i);
						finish();
					}
				});
					
			
				AlertDialog alert= builder.create();//create the AlertDialog object and return it
				//alert.setContentView(R.gridView.dialog_endset);
				alert.show();
*/		
	}
	
	public void writeToDB()
	{
		boolean checked[] = adapter.getChecked();

		//adapter checkbox
		for(int i = 0; i < checked.length; i++)
		{
			//addFavoriteStimuli.addFavoriteStimuli(userName, imageName, categoryName, totalAttemptsNum, correctAttempsNum,
			//		soundHintsNum, wordHintsNum, difficultLevel, imageURL)
		}
	}
	
	public void send(View v) {
		//update database
		
		//generate report
		
		//send report
		
		//select choice, replay | main | next Set
	}
	
	public void doNotSend(View v) {
		//update database

		//select choice, replay | main | next Set
	}

}
