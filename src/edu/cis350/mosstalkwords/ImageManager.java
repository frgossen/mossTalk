package edu.cis350.mosstalkwords;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import android.content.Context;

public class ImageManager {
	
	Images_SDB img_SDB;
	DatabaseHandler dbHandler;
	
	ImageManager(String userName,Context context) {
		dbHandler = new DatabaseHandler(context);
		dbHandler.getTable(userName);
	}

	public List<ImageStatistics> getImagesForCategory(String category){
		
		List<ImageStatistics> imageStatisticsList = new ArrayList<ImageStatistics>();
		
		img_SDB = new Images_SDB();
		List<Image> imageList = img_SDB.returnImages(category);
		
		for(int i = 0; i<imageList.size(); i++){
			ImageStatistics imageStatistics = new ImageStatistics();
			
			imageStatistics.setImageName(imageList.get(i).getWord());
			imageStatistics.setCategory(imageList.get(i).getCategory());
			imageStatistics.setUrl(imageList.get(i).getUrl());
			imageStatistics.setWordHints(0);
			imageStatistics.setSoundHints(0);
			imageStatistics.setSolved(false);
			imageStatistics.setAttempts(0);
			UserStimuli userStimuli = dbHandler.getUserStimuli(imageList.get(i).getWord());
			if(userStimuli == null)
			{
				imageStatistics.setIsFavorite(false);
				imageStatistics.setLastSeen(null);
				imageStatistics.setSeenToday(false);
			}
			else
			{
				imageStatistics.setIsFavorite(userStimuli.getIsFavorite()==0?false:true);
				imageStatistics.setLastSeen(userStimuli.getLastSeen());
				if(checkIfSeenToday(userStimuli.getLastSeen()))
					imageStatistics.setSeenToday(true);
				else
					imageStatistics.setSeenToday(false);
			}
			imageStatisticsList.add(imageStatistics);
		}
		
		return imageStatisticsList;
		
		
	}
	
public List<ImageStatistics> getImagesForFavorites(){
		
		List<ImageStatistics> imageStatisticsList = new ArrayList<ImageStatistics>();
		ImageStatistics imageStatistics = new ImageStatistics();
		
		
		/* Code to Fetch Favorite Images from database table and create a list of 20 favorite images */
		
		List<UserStimuli> usList = new ArrayList<UserStimuli>();
		//List<Image> imgList = new ArrayList<Image>();
		//DatabaseHandler dbHandler = new DatabaseHandler(context);
		

		//dbHandler.getTable(userName);

		usList = dbHandler.getFavoriteStimuli();

		Collections.shuffle(usList);
		//System.out.println(usList.size());
		if(usList.size() > 0)
		{
			for(int i=0;i < 20;i++)
			{
				imageStatistics.setImageName(usList.get(i).getImageName());
				imageStatistics.setUrl(usList.get(i).getUrl());
				imageStatistics.setCategory(usList.get(i).getCategory());
				imageStatistics.setIsFavorite(usList.get(i).getIsFavorite()==0?false:true);
				imageStatistics.setWordHints(0);
				imageStatistics.setSoundHints(0);
				imageStatistics.setSolved(false);
				imageStatistics.setAttempts(0);
				if(checkIfSeenToday(usList.get(i).getLastSeen()))
					imageStatistics.setSeenToday(true);
				else
					imageStatistics.setSeenToday(false);
				imageStatistics.setLastSeen(usList.get(i).getLastSeen());				
				
				imageStatisticsList.add(imageStatistics);
				
				if(i+1 == 20 || i+1 == usList.size())
					break;
			}
		}
		return imageStatisticsList;
	}

	public boolean checkIfSeenToday (Calendar lastSeen) {
		if(lastSeen == null)
		{
			return false;
		}
		System.out.println("l:"+lastSeen);
		double hours = (System.currentTimeMillis() - lastSeen.getTimeInMillis()) / 1000 / 60 / 60;			
		if(hours<=24)
			return true;
		else
			return false;
	}
	
	public void setUserStimuli(List<ImageStatistics> imageStatisticsList) {
		
		UserStimuli userStimuli = new UserStimuli();
		for(int i = 0; i<imageStatisticsList.size(); i++) {
			
			UserStimuli prevUserStimuli = dbHandler.getUserStimuli(imageStatisticsList.get(i).getImageName());
			
			userStimuli.setImageName(imageStatisticsList.get(i).getImageName());
			userStimuli.setCategory(imageStatisticsList.get(i).getCategory());
			userStimuli.setUrl(imageStatisticsList.get(i).getUrl());
			userStimuli.setIsFavorite(imageStatisticsList.get(i).getIsFavorite()==true?1:0);
			userStimuli.setLastSeen(imageStatisticsList.get(i).getLastSeen());

			userStimuli.setAttempts(prevUserStimuli.getAttempts()+1);
			userStimuli.setCorrectAttempts(prevUserStimuli.getCorrectAttempts()+(imageStatisticsList.get(i).isSolved()==true?1:0));
			userStimuli.setSoundHints(prevUserStimuli.getSoundHints()+(imageStatisticsList.get(i).getSoundHints()));
			userStimuli.setPlaywordHints(prevUserStimuli.getPlaywordHints()+(imageStatisticsList.get(i).getWordHints()));
			if(imageStatisticsList.get(i).getWordHints()==0 && imageStatisticsList.get(i).getSoundHints() == 0 && imageStatisticsList.get(i).isSolved() == true)
				userStimuli.setNoHint(prevUserStimuli.getNoHint()+1);
			else
				userStimuli.setNoHint(prevUserStimuli.getNoHint());
			
			dbHandler.setStimuli(userStimuli);

		}
	}
}
