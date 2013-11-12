package edu.cis350.mosstalkwords;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class SetStatistics implements Parcelable {
	
	public final static int DEFAULT_SET_SIZE = 20;
	public final static int NUM_STARS = 5;
	
	private int[] attempts;
	private int[] wordHints;
	private int[] soundHints;
	private boolean[] solved;
	
	// BEGINNNING --- IMPLEMENT PARCELABLE INTERFACE
	public int describeContents() {
        return 0;
    }
    public void writeToParcel(Parcel out, int flags) {
        out.writeIntArray(attempts);
        out.writeIntArray(wordHints);
        out.writeIntArray(soundHints);
        out.writeBooleanArray(solved);
    }
    public static final Parcelable.Creator<SetStatistics> CREATOR = new Parcelable.Creator<SetStatistics>() {
        public SetStatistics createFromParcel(Parcel in) {
            return new SetStatistics(in);
        }

        public SetStatistics[] newArray(int size) {
            return new SetStatistics[size];
        }
    };
    private SetStatistics(Parcel in) {
    	attempts = in.createIntArray();
    	wordHints = in.createIntArray();
    	soundHints = in.createIntArray();
    	solved = in.createBooleanArray();
    }
    
    
	// END --- IMPLEMENT PARCELABLE INTERFACE
	
	
	
	
	
	
	/*
	public int getSetNumber() {
		return SET_NUMBER;
	}
	*/

	//score, speed, efficiency, streaks
	//int longestStreak=0;
	//public static final int NUMSTARS = 3; 
	//private int totalScore=0;
	//private int currentStreak;
	//int maxAttemptsAllowed=3;//number of attempts considered for efficiency calculations, anything over this is considered 0 efficiency
	//public HashMap<String, Integer> starsForSets;
	//public HashMap<String, int []> stimulusSetScores;
	
	//private int[] imageSetScore;
	
//	private int currentStreak = 0;
//	private int longestStreak = 0;
//	private int totalSetScore = 0;
//	private int currentScore = 0;
//	private int completeness = 0;
	
	//public HashMap<String, Integer> longestStreakForSets;
	//public HashMap<String, int [][]> stimulusSetEfficiencies;
	//public HashMap<String, Integer> percentEfficiencyForSets;
	//public HashMap<String, Integer> bestStreaksForSets;
	//public HashMap<String, Integer> bestCompletenessForSets;//can't make BestReport object or serializable passing into intent breaks
	//public HashMap<String, Integer> bestScoresForSets;
	//public String name;
	//public String email;
	
	public SetStatistics() {
		this(DEFAULT_SET_SIZE);
	}

	public SetStatistics(int size) {
		//totalScore = 0;
		//currentStreak = 0;
		//name = null;
		//email=null;
		reset(size);
	}
	
	/*
	public void initSet(String setName, int setSize) {
		int[] scores = new int[setSize];
		int[][] eff = new int[setSize][2];
		stimulusSetScores.put(setName,scores);
		stimulusSetEfficiencies.put(setName,eff);
	}*/
	
	public void reset(){
		reset(DEFAULT_SET_SIZE);
	}
	
	public void reset(int size){
		//imageSetScore = new int[SET_SIZE];
		attempts = new int[size];
		wordHints = new int[size];
		soundHints = new int[size];
		solved = new boolean[size];
	}
	
	public int getSize(){
		return attempts.length;
	}
	/*
	public void setImageScore(int imageIdx, int score) {
		imageSetScore[imageIdx] = score;
		//totalSetScore += score;
		//currentScore += score;
		//stimulusSetScores.get(setName)[imageIdx] = score;
		//this.totalScore += score;
		
	}
	*/
	/*
	public void updateImageUserPerformance(int imageIdx, int wordHintUsed, int syllableHintUsed, int attempts) {
		imageSetAttempts[imageIdx] = attempts;
		imageSetHintsWord[imageIdx] = wordHintUsed;
		imageSetHintsSyllable[imageIdx] = syllableHintUsed;
		
//		System.out.printf("updating efficiency hints: %d attempts %d \n", hints, attempts);
	}
	*/
	/*
	public int getTotalScore() {
		return this.totalSetScore;
	}
	*/
	
	public int getAttempts(int imageIdx){
		return attempts[imageIdx];
	}
	
	public int getWordHints(int imageIdx){
		return wordHints[imageIdx];
	}
	
	public int getSoundHints(int imageIdx){
		return soundHints[imageIdx];
	}
	
	public boolean isSolved(int imageIdx) {
		return solved[imageIdx];
	}
	
	public int getScore(int imageIdx){
		return getScore(imageIdx, false);
	}
	
	public int getScore(int imageIdx, boolean ignoreSolvedStatus){
		if (!ignoreSolvedStatus && !isSolved(imageIdx))
			return 0;
		int score = 100;
		if (getWordHints(imageIdx) > 0)
			score -= 75;
		else if (getSoundHints(imageIdx) > 0) 
			score -= 50;
		return score;
	}
	
	public int getTotalScore() {
		return getTotalScore(getSize());
	}
	
	public int getTotalScore(int firstNImages) {
		int sum = 0;
		firstNImages = Math.min(firstNImages, getSize());
		for(int i=0; i<firstNImages; i++)
			sum += getScore(i);
		return sum;
	}
	/*
	public void increaseStreak() {
		currentStreak++;
	}
	*/
	/*
	public void setTotalScore(int totscore){
		totalSetScore=totscore;
	}
	
	public void setCurrentScore(int cScore)
	{
		currentScore=cScore;
	}
	*/
	/*
	public void endedSet() {
		//this.getStarScore();
		this.calculateAverageEfficiencyPercent();//calculate the percentage for this set
		this.streakEnded();
//		updateBestReport(getLongestStreak(setName),getAverageEfficiencyPercent(setName),getStarScore(setName),setName);
		//nextSet();
	}
	 */
	
	public int getLongestStreak(){
		int currentStreak = 0;
		int longestStreak = 0;
		int length = getSize();

		for (int i=0; i<length; i++) {
			if (!isSolved(i)) {
				if (currentStreak > longestStreak) {
					longestStreak = currentStreak;
				}
				currentStreak = 0;
			}
			else
			{
				currentStreak++;
			}
		}
		return longestStreak;
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
	
	public String generateSetReport(String[] images, String userName)
	{
		String fullReport = "";
		fullReport += ("User: " + userName + "\n");
		//fullReport+=("CurrentSet: "+currentSet.getName()+"\n");
		String efficiencyPercent = "" + getCompleteness();
		fullReport += ("Completeness: " + efficiencyPercent+"%\n");
		//String longestStreak=Integer.valueOf(getLongestStreak(currentSet.getName())).toString();
		fullReport += ("Longest Streak: " + getLongestStreak() + "\n");
		fullReport += ("\nImage By Image Statistics:\n\n");
		for(int i=0; i<images.length; i++) 
			fullReport += generateImageReport(images[i], i);
		
		return fullReport;
	}
	
	private String generateImageReport(String imageName, int index)
	{
		String imageReport = "";
		imageReport += imageName + ":\n";
		imageReport += "Score: " + getScore(index) + " ";
		imageReport += "Word Hint Used: " + wordHints[index] + " ";
		imageReport += "Syllable Hint Used: " + soundHints[index] + " ";
		imageReport += "Attempts: " + attempts[index];
		imageReport += "\n";
		
		/*
		imageReport+="Score: "+Integer.valueOf(stimulusSetScores.get(currentSet.getName())[index]).toString()+"\n";
		imageReport+="Number Of Hints Used: "+Integer.valueOf((stimulusSetEfficiencies.get(currentSet.getName()))[index][0]).toString()+"\n";
		imageReport+="Number of Attempts: "+Integer.valueOf((stimulusSetEfficiencies.get(currentSet.getName()))[index][1])+"\n";
		imageReport+="\n";
		 */
		
		return imageReport;
	}
	
	/*
	public void streakEnded()
	{
		if (longestStreak < currentStreak)
			longestStreak = currentStreak;
		
		//reset streak counter
		currentStreak=0;
		
	}
	*/
	
	
	public int getStarScore(){
		double completeness = getCompleteness();
		double rangePerStar = 1.0 / (NUM_STARS + 1);
		int starScore = (int) (completeness / rangePerStar);
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
	
	/*
	public int getLongestStreak()
	{
		return longestStreak;
	}
	
	public void resetLongestStreak()
	{
		longestStreak = 0;
	}
	*/
	public double getCompleteness(){
		int score = getTotalScore();
		int maxScore = getSize() * 100;
		double completeness = 1.0 * score / maxScore;
		return completeness;
	}

	public double getCompletenessPercent(){
		return Math.round(getCompleteness() * 1000) / 10;
	}

	public void incWordHint(int imageIdx){
		wordHints[imageIdx]++;
	}

	public void incAttempts(int imageIdx){
		attempts[imageIdx]++;
	}

	public void incSoundHint(int imageIdx){
		soundHints[imageIdx]++;
	}
	
	public void setSolved(int imageIdx, boolean isSolved) {
		solved[imageIdx] = isSolved;
	}
	
	public int[] getScores(){
		int[] scores = new int[getSize()];
		for(int i=0; i<scores.length; i++)
			scores[i] = getScore(i);
		return scores;
	}
	
	public SetStatistics deepCopy()
	{
		SetStatistics s = new SetStatistics();
		s.attempts = this.attempts;
		s.wordHints = this.wordHints;
		s.soundHints = this.soundHints;
		s.solved = this.solved;
		return s;
	}
}
