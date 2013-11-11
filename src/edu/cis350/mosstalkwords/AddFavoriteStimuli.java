package edu.cis350.mosstalkwords;

import java.util.Calendar;

public class AddFavoriteStimuli {
	private DatabaseHandler db;
	
	public AddFavoriteStimuli (DatabaseHandler db) {
		this.db = db;
	}
	
	public void addFavoriteStimuli (String userName,
									String imageName,
									String categoryName,
									int totalAttemptsNum,
									int correctAttempsNum,
									int soundHintsNum,
									int wordHintsNum,
									int difficultLevel,
									String imageURL) {
		db.getTable(userName);
		
		UserStimuli stimuli = new UserStimuli();
		stimuli.setImageName(imageName);
		stimuli.setCategory(categoryName);
		stimuli.setIsFavorite(1);
		stimuli.setAttempts(totalAttemptsNum);
		stimuli.setCorrectAttempts(correctAttempsNum);
		stimuli.setSoundHints(soundHintsNum);
		stimuli.setPlaywordHints(wordHintsNum);
		if (soundHintsNum + wordHintsNum == 0) {
			stimuli.setNoHint(1);
		} else {
			stimuli.setNoHint(0);
		}
		Calendar cd = Calendar.getInstance();
		stimuli.setLastSeen(cd);
		stimuli.setDifficulty(difficultLevel);
		stimuli.setUrl(imageURL);
		
		//stimuli is for this session
		
		db.setStimuli(stimuli);
	}
	
}
