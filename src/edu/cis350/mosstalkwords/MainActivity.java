package edu.cis350.mosstalkwords;

import java.io.BufferedInputStream; 
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList; 

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ViewFlipper;
import android.widget.ViewSwitcher.ViewFactory;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewPropertyAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.widget.TextView;
import android.widget.ImageSwitcher;

public class MainActivity extends UserActivity implements ViewFactory, TextToSpeech.OnInitListener{

	private final int RESULT_SPEECH_REC = 984625;
	private final int IMAGE_CACHE_HEIGHT = 1280;
	private final int IMAGE_CACHE_WIDTH = 800;
	private final int MAX_RECOGNIZED_REPETITIONS = 1;
	private final String[] praisePhrases = new String[]{
			"Great job!", 
			"Well done!", 
			"Outstanding!", 
			"Very good!", 
			"Remarkable!", 
			"What a genius!"};
	private final int MODE_CATEGORY = 35675;
	private final int MODE_FAVOURITES = 62230;

	private TextToSpeech tts;
	private ImageCache imCache;
	private Images_SDB sdb;
	private UserDataHandler udh;
	private LoadSetAndImages backgroundTask;

	private int mode;
	private String categoryName; 
	private SetStatistics currentSetStatistics;
	private int imageIndex;
	private Set currentSet;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		ImageSwitcher imSwitcher = (ImageSwitcher) findViewById(R.id.imgSwitcher);
		imSwitcher.setFactory(this);
		ProgressBar pbar = (ProgressBar) findViewById(R.id.progBar);
//
		pbar.setMax(20 + 1);
		VerticalProgressBar imageScoreProgBar = (VerticalProgressBar) findViewById(R.id.imageScoreProgBar);
		imageScoreProgBar.setMax(100 + 5);
		
		sdb = new Images_SDB();
		udh  = new UserDataHandler(getUserName());
		backgroundTask = new LoadSetAndImages();
		tts = new TextToSpeech(this, this);
		imCache = ImageCache.getInstance();

		Intent i = getIntent();
		if(i.hasExtra("startCategory")){
			categoryName = getIntent().getExtras().getString("startCategory");
//
			currentSetStatistics = new SetStatistics(20);
			imageIndex = 0;
			currentSet = null;
			mode = MODE_CATEGORY;
			i.removeExtra("startCategory");
			backgroundTask.execute(true);
		}
		else if(i.hasExtra("startFavourites")){
			categoryName = null;
//
			currentSetStatistics = new SetStatistics(20);
			imageIndex = 0;
			currentSet = null;
			mode = MODE_FAVOURITES;
			i.removeExtra("startFavourites");
			backgroundTask.execute(true);
		}
		else{
			restoreState(savedInstanceState);
			backgroundTask.execute(false);
		}
	}
	
	public void onSaveInstanceState(Bundle bundle){
		super.onSaveInstanceState(bundle);
		bundle.putString("categoryName", categoryName); 
		bundle.putParcelable("currentSetStatistics", currentSetStatistics);
		bundle.putInt("imageIndex", imageIndex);
		bundle.putParcelable("currentSet", currentSet);
		bundle.putInt("mode", mode);
	}
	
	public void onRestoreInstanceState(Bundle bundle){
		super.onRestoreInstanceState(bundle);
		restoreState(bundle);
	}
	
	private void restoreState(Bundle bundle){
		if(bundle != null){
			categoryName = bundle.getString("categoryName"); 
			currentSetStatistics = bundle.getParcelable("currentSetStatistics");
			imageIndex = bundle.getInt("imageIndex");
			currentSet = bundle.getParcelable("currentSet");
			mode = bundle.getInt("mode");
		}
	}
	
	public void onResume(){
		super.onResume();
		updateLayoutInformation();
	}
	
	public void onDestroy(){
		super.onDestroy();
		tts.shutdown();
		backgroundTask.cancel(true);
	}
	
	/*
	public void enterNameAndEmail()
	{
		Intent userEntry = new Intent(this, NameAndEmailActivity.class);
		userEntry.putExtra("User", currentSetStatistics);
		startActivityForResult(userEntry,2);
	}
	
	private void openWelcomePage() {
		Intent welcome = new Intent(this, WelcomeActivity.class);
		welcome.putExtra("User", currentSetStatistics);
		startActivityForResult(welcome, 3);
		//startActivity(welcome);
	}

	private void returnFromSet()
	{
		Intent returnOptions = new Intent(this, EndSetReturnActivity.class);
		returnOptions.putExtra("User", currentSetStatistics);
		startActivityForResult(returnOptions,1);
	}
	*/

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	//required method for image switcher class
	public View makeView() {
		ImageView iv = new ImageView(this);
		iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
		iv.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		iv.setBackgroundColor(0xFFFFFFFF); //opaque white background
		return iv;
	}

		

	
	public void finishedSet(){
		imageIndex = 0;

		setScore(getScore()+currentSetStatistics.getTotalScore());
		
		SetStatistics s = currentSetStatistics.deepCopy();
		
		Intent gotoEndOfSet = new Intent(this, EndSetActivity.class);
		gotoEndOfSet.putExtra("categoryName", categoryName); 
		gotoEndOfSet.putExtra("currentSetStatistics", s);
		
		
		showCurrentImageFromCache();
		updateLayoutInformation();

		currentSetStatistics.reset();

		ArrayList<Image> currentSet1 = new ArrayList<Image>();
		for (int i = 0; i < currentSet.getSize(); i++) {
			currentSet1.add(currentSet.get(i));
		}
		gotoEndOfSet.putParcelableArrayListExtra("currentSet", currentSet1);
		//gotoEndOfSet.putExtra("currentSet", currentSet);
		gotoEndOfSet.putExtra("mode", mode);
		startActivity(gotoEndOfSet);		
		//finish();
	}

	private void nextImage() {
		imageIndex++;
		if(imageIndex == currentSetStatistics.getSize())
			finishedSet();
		else {
			showCurrentImageFromCache();
			updateLayoutInformation();
		}
	}
	
	private synchronized void showCurrentImageFromCache(){
		try{
			String word = currentSet.getWord(imageIndex);
			Bitmap im = imCache.getBitmapFromCache(word); 
			if(im != null){
				Drawable drawableBitmap = new BitmapDrawable(getResources(),im);
				ImageSwitcher imSwitcher = (ImageSwitcher) findViewById(R.id.imgSwitcher);
				imSwitcher.setImageDrawable(drawableBitmap);
			}
		}
		catch(Exception e){
			Log.d("ERROR", "Could not load image from cache. ");
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == RESULT_SPEECH_REC) {
			if(resultCode == RESULT_OK) {
				ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
				speechRecognitionResult(matches);
			}
			else {
				currentSetStatistics.incAttempts(imageIndex);
				updateLayoutInformation();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void onSoundHintButtonClick(View view) {
		currentSetStatistics.incSoundHint(imageIndex);
		
		String word = currentSet.get(imageIndex).getWord();
		/*
		String hint = word.substring(0, word.length()/2);
		speak(hint, 1);
		*/
		speakSound(word);
		updateLayoutInformation();
	}

	public void onWordHintButtonClick(View view) {
		String word = currentSet.get(imageIndex).getWord();
		speak(word, 1);
		currentSetStatistics.incWordHint(imageIndex);
		updateLayoutInformation();
	}

	public void onNextButtonClick(View view) {
		currentSetStatistics.setSolved(imageIndex, false);
		nextImage();
	}

	public void onSpeakButtonClick(View view) {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say what you see in the picture...");
		startActivityForResult(intent, RESULT_SPEECH_REC);
	}
	
	
	public void onFavouritesButtonClick(View view){
		/* *hide*
		Image currentImage = currentSet.get(imageIndex);
		currentImage.toggleFavourite();
		updateLayoutInformation();
		*/
	}

	public void speechRecognitionResult(ArrayList<String> matches){
		//if (matches.isEmpty()) { Log.d("voice rec","NO MATCHES");}
		boolean matchFound = false;
		String correctWord = currentSet.get(imageIndex).getWord();
		for (String s: matches) {
			if (s.toLowerCase().contains(correctWord.toLowerCase())) {
				updateLayoutInformation();
				praiseUser();
				currentSetStatistics.setSolved(imageIndex, true);
				nextImage();
				matchFound = true;
				break;
			}
		}
		if (!matchFound) {
			currentSetStatistics.incAttempts(imageIndex);
			updateLayoutInformation();

			String message = "Incorrect. You said:";
			int showN = Math.min(matches.size(), MAX_RECOGNIZED_REPETITIONS);
			for (int i=0; i<showN; i++) 
				message = message + "\n" + matches.get(i);

			new AlertDialog.Builder(this)
					.setMessage(message)
					.setTitle("Incorrect")
					.setCancelable(true)
					.setPositiveButton(android.R.string.ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int whichButton) {
									tts.stop();
								}
							}).show();

			String messageForTts = message
					.replaceFirst("\\n", "")
					.replaceAll("\\n", ",oar, "); //oar = or for tts apparently...
			speak(messageForTts, 1);
		}
	}
	
	private void praiseUser(){
		int n = (int)(Math.random() * praisePhrases.length);
		String phrase = praisePhrases[n];
		speak(phrase, 1);
		
		MediaPlayer mp = MediaPlayer.create(MainActivity.this,R.raw.ding);
		mp.start(); 
		
		ViewFlipper scoreFlipper = (ViewFlipper)findViewById(R.id.ViewFlipper);
		ViewPropertyAnimator animate = scoreFlipper.animate();
		animate.rotationBy(360);
	}
	
	private void updateLayoutInformation(){
		Log.d("SCORE_BOARD", "updateScoreBoard called");
		int attempts = currentSetStatistics.getAttempts(imageIndex);
		int soundHints = currentSetStatistics.getSoundHints(imageIndex);
		int wordHints = currentSetStatistics.getWordHints(imageIndex);
		int imageScore = currentSetStatistics.getScore(imageIndex, true);
		String scoreBoardMsg = "Attempts: " + attempts + 
				"\nSound hints: " + soundHints + 
				"\nWord hints: " + wordHints + 
				"\nImage Score: " + imageScore;/* + 
				"\nImage Index: " + imageIndex;*/
		TextView scoreBoard = (TextView) findViewById(R.id.scoBoStatistics);
		scoreBoard.setText(scoreBoardMsg);
		
		VerticalProgressBar imageScoreProgBar = (VerticalProgressBar) findViewById(R.id.imageScoreProgBar);
		imageScoreProgBar.setProgress(imageScore);
		
		int setScore = currentSetStatistics.getTotalScore(imageIndex);
		TextView txtScore = (TextView) findViewById(R.id.txtScore);
		txtScore.setText(setScore + " Points");
		
		ProgressBar pbar = (ProgressBar) findViewById(R.id.progBar);
		pbar.setProgress(1 + imageIndex);
		
		
		/* *hide*
		if(currentSet != null){
			ImageButton btn = (ImageButton) findViewById(R.id.btnFav);
			Image currentImage = currentSet.get(imageIndex);
			boolean isFav = currentImage.isFavourite();
			btn.setImageResource(isFav ? R.drawable.star_selected : R.drawable.star);
		}
		*/
		
	}
	
	private void speak(String words2say, float rate) {
		Log.d("SPEAK", words2say);
		tts.setSpeechRate(rate);
		tts.speak(words2say, TextToSpeech.QUEUE_FLUSH, null);
	}

	public void onInit(int status) {
		// speak("Welcome to Wordle!", 1);
	}

	class LoadSetAndImages extends AsyncTask<Boolean, Integer, Object> {
		protected Object doInBackground(Boolean[] params) {	
			try {
				/* Load a new set or only the images? When the Activity is recreated for some reason the set may be 
				 * intended to be the same. For instance when the Activity is rotated this is the case. 
				 */ 
				boolean loadNewSet = params[0].booleanValue();
				Log.d("ASYNC", "started, loadNewSet = " + loadNewSet);
				if(loadNewSet){
					if(mode == MODE_CATEGORY)
						currentSet = new Set(sdb.returnImages(categoryName));
					else if (mode == MODE_FAVOURITES)
						//currentSet = new Set(sdb.returnImages("Living"));
						currentSet = new Set(udh.getFavoriteStimulus(getApplicationContext()));
				}
				
				int size = currentSet.getSize();
				for(int i=0; i<size; i++) {
					// Skip passed images 
					while(i < imageIndex)
						i++;
					
					if(isCancelled())
						break;
					
					// Connect 
					URL aURL = new URL(currentSet.get(i).getUrl());				
					URLConnection conn = aURL.openConnection();
					conn.connect();

					if(isCancelled())
						break;

					BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());

					if(isCancelled())
						break;
					
					// Try to decode bitmap without running out of memory
					BitmapFactory.Options options = new BitmapFactory.Options();
					options.inJustDecodeBounds = true;
					Rect r = new Rect(-1,-1,-1,-1); 
					BitmapFactory.decodeStream(bis, r, options); 
					bis.close();

					// Calculate inSampleSize - need to figure out required dims
					options.inSampleSize = ImageCache.calculateInSampleSize(options, IMAGE_CACHE_WIDTH, IMAGE_CACHE_HEIGHT);

					if(isCancelled())
						break;
					
					// Decode bitmap with inSampleSize set
					options.inJustDecodeBounds = false;
					conn = aURL.openConnection(); //reopen connection
					conn.connect();
					bis = new BufferedInputStream(conn.getInputStream());
					Bitmap bitmap = BitmapFactory.decodeStream(bis, r, options); 			   

					if(isCancelled())
						break;
					
					imCache.addBitmapToCache( currentSet.get(i).getWord(), bitmap);

					publishProgress(i);
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
				Log.e("exception","malformedURL");
			} catch (IOException e) {
				e.printStackTrace();
				Log.e("exception","IOexception");
			} 
			return null;
		}

		protected void onProgressUpdate(Integer... progress) {
			int recentlyLoaded = progress[0];
			Log.d("ASYNC", "progress = " + recentlyLoaded);
			if (recentlyLoaded == imageIndex) 
				showCurrentImageFromCache();
		}	
	}
	
	public void speakSound(String s1)
    {
    	tts.setSpeechRate((float)(0.4));
    	tts.speak(s1, 0, null);
    	try {
			Thread.sleep(200+(100*(long)Math.floor((0.5*s1.length()))), 0);
    		tts.stop();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d("Error of Exception","Error");
		}
    }
}
