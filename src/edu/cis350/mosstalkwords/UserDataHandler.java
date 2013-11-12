package edu.cis350.mosstalkwords;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;


public class UserDataHandler {
	String userName;
	public UserDataHandler(String name)
	{
		userName = name;
	}
	public List<Image> getFavoriteStimulus(Context context)
	{
		List<UserStimuli> usList = new ArrayList<UserStimuli>();
		DatabaseHandler dbHandler = new DatabaseHandler(context);
		List<Image> imgList = new ArrayList<Image>();

		dbHandler.getTable(userName);

		usList = dbHandler.getFavoriteStimuli();


		Collections.shuffle(usList);
		System.out.println(usList.size());
		System.out.println(usList.get(0).getImageName());
		/*if(usList.size() < 20)
	                {
	                        return usList;
	                }
	                //else
	                //{

	                if(usList.size()>20) {        
	                //	List<UserStimuli> usListFinal = new ArrayList<UserStimuli>();*/


		for(int i=0;i < 20;i++)
		{
			Image img = new Image(usList.get(i).getImageName(), usList.get(i).getCategory(), 
					"0", "0", "0", usList.get(i).getUrl());
			imgList.add(img);
			//imgList.add(setCategory(usList.get(i).getCategory());
			//imgList.get(i).setWord(usList.get(i).getImageName());
		//	imgList.get(i).setUrl(usList.get(i).getUrl());
		//	imgList.get(i).setFrequency(0);
		//	imgList.get(i).setLength(0);
		//	imgList.get(i).setImageability(0);
			
			if(i+1 == 20 || i+1 == usList.size())
				break;
		}

		return imgList;

	}
}
