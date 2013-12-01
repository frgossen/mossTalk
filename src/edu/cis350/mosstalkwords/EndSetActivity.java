package edu.cis350.mosstalkwords;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;

public class EndSetActivity extends UserActivity {
	GridView gridView;
	ImageAdapter adapter; 
	Context mContext = this;
	ImageManager im;
	
	private int mode;
	private String categoryName; 
	private boolean isFavourites;
	private Set currentSet;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
 		super.onCreate(savedInstanceState);
		setContentView(R.layout.end_dialog);//.favorite_gridview);

		im = new ImageManager(this.getUserName(), this.getApplicationContext());
		
		gridView = (GridView) this.findViewById(R.id.gridview);
		
	    //list of string names, results
	    Intent i = getIntent();
	    categoryName = i.getStringExtra("categoryName"); 
	    isFavourites = i.getBooleanExtra("startFavourites", false);
		//currentSetStatistics = (SetStatistics) i.getParcelableExtra("currentSetStatistics");
		
	    
		
		currentSet = (Set)i.getParcelableExtra("currentSet");
		
		mode = i.getIntExtra("mode", -1);
	    
	    //int[] results = currentSetStatistics.getImageSetScore();
	    //Context ctx, String[] res, String[] imn
	    
		String[] imageWords = new String[currentSet.getSize()];
		
		for (int j = 0; j < currentSet.getSize(); j++) {
			imageWords[j] = currentSet.get(j).getImageName();
		}
		
	    adapter = new ImageAdapter(this, currentSet, getUserName());
		gridView.setAdapter(adapter);
		
		RatingBar score = (RatingBar) this.findViewById(R.id.scoreBar);
	    int starScore = currentSet.getStarScore();
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
	    String completenessPercent = "" + currentSet.getCompletenessPercent();
	    completeness.setText(completeness.getText() + completenessPercent+"%");
	    
	    TextView streak=(TextView) this.findViewById(R.id.streak);
	    String longestStreak= Integer.valueOf(currentSet.getLongestStreak()).toString();
	    streak.setText(streak.getText()+longestStreak);
	    
	    TextView setScoreNum = (TextView) this.findViewById(R.id.endScore);
	    setScoreNum.setText(setScoreNum.getText().toString() + currentSet.getTotalScore()); 

	    TextView totalScoreNum = (TextView) this.findViewById(R.id.endScoreTotal);
	    totalScoreNum.setText(totalScoreNum.getText().toString() + getScore()); 
	}
	
	private void updateDB()
	{
		boolean checked[] = adapter.getChecked();

		//adapter checkbox
		for(int i = 0; i < checked.length; i++)
		{
			boolean solved = currentSet.get(i).isSolved();
			int correctAttempt = 0;
			if(solved)
			{
				correctAttempt = 1;
			}
			
			if(checked[i])
			{
								
				currentSet.get(i).setIsFavorite(true);
				/*
				modifyFavoriteStimuli.updateFavoriteStimuli(getUserName(), currentSet.get(i).getImageName(),
						currentSet.get(i).getCategory(), currentSet.get(i).getAttempts(),
						correctAttempt, currentSet.get(i).getSoundHints(), 
						currentSet.get(i).getWordHints(), 2, currentSet.get(i).getUrl(), 1);
						*/
			}
			else
			{
				currentSet.get(i).setIsFavorite(false);
				boolean[] origin = adapter.getOriginallyChecked();

				//if originally checked, but now it is unchecked, set to not favorite in DB	
				if(origin[i])
				{
					/*
					modifyFavoriteStimuli.updateFavoriteStimuli(getUserName(), currentSet.get(i).getImageName(),
							currentSet.get(i).getCategory(), currentSet.get(i).getAttempts(),
							correctAttempt, currentSet.get(i).getSoundHints(), 
							currentSet.get(i).getWordHints(), 2, currentSet.get(i).getUrl(), 0);
							*/
				}	
				//update if it is
				
				//skip otherwise
				
				
			}
		}
		im.setUserStimuli(currentSet.getImages());
	}
	
	public void send(View v) {
		System.out.println("Send");
		//update database
		updateDB();
		


		//create and send report
		Intent nameAndEmail = new Intent(this, NameAndEmailActivity.class);
		startActivityForResult(nameAndEmail, 2);
		//wait
		
		//select choice, replay | main | next Set
	}
	
	public void save(View v) {
		System.out.println("No");
		//update database
		updateDB();
		
		createSavedReport();
		
		//select choice, replay | main | next Set
		displayOptions();
	}
	
	public File createReport(String name) throws IOException
	{
		File path = Environment.getExternalStorageDirectory();
		File dir = new File(path.getAbsolutePath() + "/textfiles");
		dir.mkdirs();
		
		
		File reportFile = null;

		
		File[] files = dir.listFiles();

		
		if(files.length != 0)
		{
			reportFile = files[0];
		}
		else
		{
			reportFile = new File(dir, ("Report.txt"));
		}

		//OutputStreamWriter reportOut = new OutputStreamWriter(openFileOutput(currentSet.getName()+"Report.txt", this.MODE_PRIVATE));
		
		String[] imgNames = new String[currentSet.getSize()];
		
		for(int i = 0; i < currentSet.getSize(); i++)
		{
			imgNames[i] = currentSet.get(i).getImageName();
		}
		
		String reportString = currentSet.generateSetReport(imgNames, name);
		FileWriter report=new FileWriter(reportFile, true);
		report.write(reportString);
		report.close();
		return reportFile;
	}
	
	public void createSavedReport()
	{
		try
		{
			createReport(this.getUserName());
		}
		catch(IOException i)
		{
			i.printStackTrace();
		}
	}

	
	public void sendReportViaEmail(File fileName, String email)
	{
		Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		
		//this makes it not close... i don't know if this was intended.
		emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		//
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, 
				new String[]{email});
		String subject="Wordle "/*+currentSet.getName()*/ +" Report";
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
		String body= "Your report is attached below. Good Work!";
		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, body);

		//String rawFolderPath = "android.resource://" + getPackageName() 
		//                       + "/" + R.raw.shortcuts;

		// Here my file name is shortcuts.pdf which i have stored in /res/raw folder
		//Uri emailUri = Uri.parse(rawFolderPath );
		emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(fileName));
		emailIntent.setType("vnd.android.cursor.dir/vnd.google.note");
		System.out.println("Email Send");
		startActivityForResult(Intent.createChooser(emailIntent, "Send mail..."), 1);
	}
	
	public void createAndSendReport(String name, String email)
	{
		File fileMade=new File("");
		try {
			fileMade = createReport(name);
		} catch (IOException e) {
			e.printStackTrace();
		}

		sendReportViaEmail(fileMade, email);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(requestCode == 1)
		{
			//delete
			System.out.println("Back from Email");
			deleteFile();
			displayOptions();
		}
		else if(requestCode == 2)
		{
			boolean cancel = data.getBooleanExtra("Cancel", false);

			if(!cancel)
			{
				createAndSendReport(data.getStringExtra("Username"), data.getStringExtra("Email"));
			}
			else
			{
				createSavedReport();
				displayOptions();
			}
		}
	}
	
	public void enterNameAndEmail()
	{
		Intent userEntry = new Intent(this, NameAndEmailActivity.class);
		userEntry.putExtra("User", currentSet);
		startActivityForResult(userEntry,2);
	}
	
	private void displayOptions()
	{
		Intent returnOptions = new Intent(this, EndSetReturnActivity.class);
		returnOptions.putExtra("currentSet", currentSet);
		returnOptions.putExtra("categoryName", categoryName);
		returnOptions.putExtra("startFavourites", isFavourites);
		startActivity(returnOptions);
		finish();
	}
	
	public void deleteFile()
	{
		File path = Environment.getExternalStorageDirectory();
		File dir = new File(path.getAbsolutePath() + "/textfiles");
		if(dir.exists())
		{
			for(File f : dir.listFiles())
			{
				f.delete();
			}
		}
	}
	

}
