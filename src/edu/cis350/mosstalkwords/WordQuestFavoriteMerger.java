package edu.cis350.mosstalkwords;

import java.util.ArrayList;

public class WordQuestFavoriteMerger {
	ImageManager imageManager;
	public WordQuestFavoriteMerger(ImageManager im)
	{
		imageManager = im;
	}
	
	public void mergeWQandFavorites(Set s)
	{
		for(int i = 0; i < s.getImages().size(); i++)
		{
			UserStimuli userStimuli = imageManager.dbHandler.getUserStimuli(s.get(i).getImageName());
			if(userStimuli != null)
			{
				s.get(i).setIsFavorite(userStimuli.getIsFavorite()==0?false:true);
			}
		}
	}

}
