package edu.cis350.mosstalkwords;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;

public class EndSetActivity extends UserActivity {

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
		System.out.println("Send");
		//update database
		
		//create and send report
		Intent nameAndEmail = new Intent(this, NameAndEmailActivity.class);
		startActivityForResult(nameAndEmail,2);
		//wait
		
		//select choice, replay | main | next Set
	}
	
	public void doNotSend(View v) {
		System.out.println("No");
		//update database

		//select choice, replay | main | next Set
	displayOptions();
	}
	
	public File createReport() throws IOException
	{
		File path = Environment.getExternalStorageDirectory();
		File dir = new File(path.getAbsolutePath() + "/textfiles");
		dir.mkdirs();
		
		File reportFile = new File(dir,("Report.txt"));
		//OutputStreamWriter reportOut = new OutputStreamWriter(openFileOutput(currentSet.getName()+"Report.txt", this.MODE_PRIVATE));
		String[] imgNames = new String[SetStatistics.DEFAULT_SET_SIZE];
		int i = 0;
		
		String reportString = currentSetStatistics.generateSetReport(imgNames, getUserName());
		FileWriter report=new FileWriter(reportFile);
		report.write(reportString);
		//reportOut.write(reportString);
		//reportOut.close();
		report.close();
		//reportOut.println("User: "+currentUser.name);
		//reportOut.close();
		return reportFile;
	}

	
	public void sendReportViaEmail(File fileName)
	{
		Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, 
				new String[]{getEmail()});
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
	
	public void createAndSendReport()
	{

		File fileMade=new File("");
		try {
			fileMade = createReport();
		} catch (IOException e) {
			e.printStackTrace();
		}

		sendReportViaEmail(fileMade);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(requestCode == 1)
		{
			displayOptions();
		}
		else if(requestCode == 2)
		{
			if(data.getBooleanExtra("Cancel", true))
				createAndSendReport();
			else
				displayOptions();
		}
	}
	
	public void enterNameAndEmail()
	{
		Intent userEntry = new Intent(this, NameAndEmailActivity.class);
		userEntry.putExtra("User", currentSetStatistics);
		startActivityForResult(userEntry,2);
	}
	
	private void displayOptions()
	{
		Intent returnOptions = new Intent(this, EndSetReturnActivity.class);
		returnOptions.putExtra("currentSetStatistics", currentSetStatistics);
		returnOptions.putExtra("categoryName", categoryName);
		startActivity(returnOptions);
		finish();
	}

}
