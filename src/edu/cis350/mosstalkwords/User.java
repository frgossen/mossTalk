package edu.cis350.mosstalkwords;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import android.content.Intent;
import android.util.Log;

public class User implements Serializable {
	final int SET_NUMBER = 20;
	
	public int getSetNumber() {
		return SET_NUMBER;
	}

	//score, speed, efficiency, streaks
	//int longestStreak=0;
	public static final int NUMSTARS = 3; 
	//private int totalScore=0;
	//private int currentStreak;
	//int maxAttemptsAllowed=3;//number of attempts considered for efficiency calculations, anything over this is considered 0 efficiency
	//public HashMap<String, Integer> starsForSets;
	//public HashMap<String, int []> stimulusSetScores;
	
	private int[] imageSetScore = new int[SET_NUMBER];
	private int[] imageSetAttempts = new int[SET_NUMBER];
	private int[] imageSetHintsWord = new int[SET_NUMBER];
	private int[] imageSetHintsSyllable = new int[SET_NUMBER];
	
	private int currentStreak = 0;
	private int longestStreak = 0;
	private int totalSetScore = 0;
	private int currentScore = 0;
	private int completeness = 0;
	//public HashMap<String, Integer> longestStreakForSets;
	//public HashMap<String, int [][]> stimulusSetEfficiencies;
	//public HashMap<String, Integer> percentEfficiencyForSets;
	//public HashMap<String, Integer> bestStreaksForSets;
	//public HashMap<String, Integer> bestCompletenessForSets;//can't make BestReport object or serializable passing into intent breaks
	//public HashMap<String, Integer> bestScoresForSets;
	public String name;
	public String email;
	
	public User() {
		//totalScore = 0;
		//currentStreak = 0;
		name = null;
		email=null;
		
	}
	
	/*
	public void initSet(String setName, int setSize) {
		int[] scores = new int[setSize];
		int[][] eff = new int[setSize][2];
		stimulusSetScores.put(setName,scores);
		stimulusSetEfficiencies.put(setName,eff);
	}*/
	
	
	public void updateImageScore(int imageIdx, int score) {	
		imageSetScore[imageIdx] = score;
		totalSetScore += score;
		currentScore += score;
		//stimulusSetScores.get(setName)[imageIdx] = score;
		//this.totalScore += score;
		
	}
	
	public void updateImageEfficiency(int imageIdx, int wordHintUsed,
			int syllableHintUsed, int attempts) {
		imageSetAttempts[imageIdx] = attempts;
		imageSetHintsWord[imageIdx] = wordHintUsed;
		imageSetHintsSyllable[imageIdx] = syllableHintUsed;
		
//		System.out.printf("updating efficiency hints: %d attempts %d \n", hints, attempts);
	}
	
	public int getTotalScore() {
		return this.totalSetScore;
	}
	public String getTotalScoreString()
	{
		return Integer.toString(totalSetScore);
	}
	public void increaseStreak() {
		currentStreak++;
		
	}
	public void setTotalScore(int totscore)
	{
		totalSetScore=totscore;
	}
	
	public void setCurrentScore(int cScore)
	{
		currentScore=cScore;
	}
	
	public void endedSet() {
		this.getStarScore();
		this.calculateAverageEfficiencyPercent();//calculate the percentage for this set
		this.streakEnded();
//		updateBestReport(getLongestStreak(setName),getAverageEfficiencyPercent(setName),getStarScore(setName),setName);
		//nextSet();
	}

	/*
	public boolean hasPlayedSet(String setName) {
		return this.stimulusSetScores.containsKey(setName);
	}
	*/
	
	/*
	public HashSet<String> getSetScoresInMapOfStrings()
	{
		HashSet<String> scoreStrings=new HashSet<String>();
		for(int i:starsForSets.values())
		{
			scoreStrings.add(Integer.toString(i));
		}
		return scoreStrings;
	}
	
	*/
	
	public String generateSetReport(ArrayList<String> images)
	{
		String fullReport="";
		fullReport+=("User: "+name+"\n");
		//fullReport+=("CurrentSet: "+currentSet.getName()+"\n");
		String efficiencyPercent=Integer.valueOf((int)calculateAverageEfficiencyPercent()).toString();
		fullReport+=("Completeness: "+efficiencyPercent+"%\n");
		//String longestStreak=Integer.valueOf(getLongestStreak(currentSet.getName())).toString();
		fullReport+=("Longest Streak: "+longestStreak+"\n");
		fullReport+=("\nImage By Image Statistics:\n\n");
		int index=0;
		for(String image:images)
		{
			fullReport+=generateImageStatistics(image,index);
			index++;
		}
		
		return fullReport;
	}
	
	public String generateImageStatistics(String imgName, int index)
	{
		String imageReport="";
		imageReport+=imgName+":\n";
		imageReport+="Score: " + imageSetScore[index] + " ";
		imageReport+="Word Hint Used: " + this.imageSetHintsWord[index] + " ";
		imageReport+="Syllable Hint Used: " + this.imageSetHintsSyllable[index] + " ";
		imageReport+="Attempts: " + this.imageSetAttempts[index];
		imageReport+="\n";
	/*
		imageReport+="Score: "+Integer.valueOf(stimulusSetScores.get(currentSet.getName())[index]).toString()+"\n";
		imageReport+="Number Of Hints Used: "+Integer.valueOf((stimulusSetEfficiencies.get(currentSet.getName()))[index][0]).toString()+"\n";
		imageReport+="Number of Attempts: "+Integer.valueOf((stimulusSetEfficiencies.get(currentSet.getName()))[index][1])+"\n";
		imageReport+="\n";
	*/
		
		return imageReport;
	}
	
	
	public void streakEnded()
	{
		if (longestStreak < currentStreak)
			longestStreak = currentStreak;
		
		//reset streak counter
		currentStreak=0;
		
	}
	
	
	
	public int getStarScore()
	{
		//return starsForSets.get(stimulusSetName);
		
		int starScore = 1;
		int maxScore = 100 * SET_NUMBER;

		int interval = maxScore/NUMSTARS;

		if(currentScore >= 2 * interval)
		{
			starScore = 3;
		}
		
		else if(currentScore >=  interval)
		{
			starScore = 2;
		}
		else
		{
			starScore = 1;
		}
		return starScore;
	}
	/*
	public boolean containsBestStarScore(String stimulusSetName)
	{
		return bestScoresForSets.containsKey(stimulusSetName);
	}
	public void calculateStarScore(String stimulusSetName)
	{
		int temp[]=stimulusSetScores.get(stimulusSetName);
		int totalScore= temp.length*300;
		int interval = totalScore/NUMSTARS;
		int setScore=0;
		for(int i: temp)
		{
			setScore+=i;
		}
		if(setScore>2*interval) {
			starsForSets.put(stimulusSetName, Integer.valueOf(3));
		}
		else if(setScore>1*interval) {
			starsForSets.put(stimulusSetName, Integer.valueOf(2));
		} else {
			starsForSets.put(stimulusSetName, Integer.valueOf(1));
		}
	}
	*/
	/*
	public void updateBestReport(int streak, int complete, int score, String setName)
	{
		if(bestScoresForSets.containsKey(setName))
		{
			if(bestScoresForSets.get(setName)<score)
				bestScoresForSets.put(setName,score);
			if(bestStreaksForSets.get(setName)<streak)
				bestStreaksForSets.put(setName,streak);
			if(bestCompletenessForSets.get(setName)<complete)
				bestCompletenessForSets.put(setName, complete);
		}
		else
		{
			bestScoresForSets.put(setName,score);
			bestStreaksForSets.put(setName,streak);
			bestCompletenessForSets.put(setName, complete);
		}
	}
	*/
	
	
	public int getAverageEfficiencyPercent()
	{
		return (int)calculateAverageEfficiencyPercent();
	}
	
	public int getLongestStreak()
	{
		return longestStreak;
	}
	
	public void resetLongestStreak()
	{
		longestStreak = 0;
	}
	
	public double calculateAverageEfficiencyPercent()
	{
		double completeness = 0;
		double maxScorePerImg = 100.00/SET_NUMBER;
		for ( int i = 0; i < SET_NUMBER; i++)
		{
			if(imageSetScore[i] != 0)
			{
				completeness += Math.max(maxScorePerImg - (imageSetHintsWord[i] + imageSetHintsSyllable[i] + imageSetAttempts[i]), 2);
			}
		}
		
		return completeness;
		
		
	}

}
