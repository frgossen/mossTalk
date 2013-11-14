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
    
 	
	public SetStatistics() {
		this(DEFAULT_SET_SIZE);
	}

	public SetStatistics(int size) {
		reset(size);
	}
	
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
		if (longestStreak < currentStreak) {
			longestStreak = currentStreak;
		}
		return longestStreak;
	}
	
	
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
		
		
		return imageReport;
	}
	
	
	
	public int getStarScore(){
		double completeness = getCompleteness();
		double rangePerStar = 1.0 / (NUM_STARS + 1);
		int starScore = (int) (completeness / rangePerStar);
		return starScore;
	}
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
