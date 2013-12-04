package edu.cis350.mosstalkwords;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class EndSetReturnActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    Context mContext = this; //this.getApplicationContext();
		   
	    
	    
	   // LayoutInflater inflater = this.getLayoutInflater();//(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    //final View layout = inflater.inflate(R.layout.dialog_enter_name_and_email, null); 
	    
	    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
	    builder.setCancelable(false);
		builder.setTitle("Play Again?")//hackish extra spaces to center the title since not an option
			//.setView(layout)
		
			.setNeutralButton(R.string.restart, new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int id)
					{
						//restart set
						Intent activityMain = new Intent(EndSetReturnActivity.this, MainActivity.class);
						//activityMain.putExtra("startCategory", EndSetReturnActivity.this.getIntent().getStringExtra("categoryName"));
						Set s = EndSetReturnActivity.this.getIntent().getExtras().getParcelable("currentSet");
						s.resetImagesStatistics();
						activityMain.putExtra("currentSet", s);
						populateIntentModeData(activityMain);
						startActivity(activityMain);
						finish();
				}
				})
				.setPositiveButton(R.string.nextSet, new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int id)
					{
						//next set
						
						
						Intent activityMain = new Intent(EndSetReturnActivity.this, MainActivity.class);

						//menu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
						//need to make main activity exit.

						
						
						//fix this, need to close main activity.
						freeCacheMem();
										
						/*
						if(EndSetReturnActivity.this.getIntent().getStringExtra("categoryName") != null)
						{
							activityMain.putExtra("startCategory", EndSetReturnActivity.this.getIntent().getStringExtra("categoryName"));
						}
						else
						{
							activityMain.putExtra("startFavourites", true);
						}
						//activityMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
				
						startActivity(activityMain);
				*/		
						populateIntentModeData(activityMain);
						startActivity(activityMain);
						
						finish();
					}
				})
				.setNegativeButton(R.string.menu, new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int id)
					{
						//go to main menu
						Intent menu = new Intent(EndSetReturnActivity.this, WelcomeActivity.class);		
//						menu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

						//need to make main activity exit.

						
						
						//fix this, main activity needs to close.
						
						
						menu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						
						freeCacheMem();
						
						startActivity(menu);
						finish();
					}
				});
				
		
		AlertDialog alert= builder.create();//create the AlertDialog object and return it
		//alert.setContentView(R.layout.dialog_endset);
		alert.show();
	}
	
	private void freeCacheMem()
	{
		Set s = EndSetReturnActivity.this.getIntent().getExtras().getParcelable("currentSet");

		String [] temp = s.getWords();
		String[] del = new String[temp.length];
		
		for(int z = 0; z < del.length; z++)
			del[z] = temp[z];
		
		ImageCache.getInstance().clearCache(del);
	}
	private void populateIntentModeData(Intent i)
	{
		i.putExtra("mode", this.getIntent().getIntExtra("mode", -1));
		i.putExtra("wordQuestLevel", this.getIntent().getIntExtra("wordQuestLevel",-1));
		i.putExtra("categoryName", this.getIntent().getStringExtra("categoryName"));
	}
}
