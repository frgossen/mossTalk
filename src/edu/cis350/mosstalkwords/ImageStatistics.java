package edu.cis350.mosstalkwords;

import java.util.Calendar;

public class ImageStatistics {

	private String imageName;
	private String url;
	private String category;
	private Boolean isFavorite;
	private int wordHints;
	private int soundHints;
	private int attempts;
	private boolean isSolved;
	private boolean isSeenToday;
	private Calendar lastSeen;
	
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Boolean getIsFavorite() {
		return isFavorite;
	}
	public void setIsFavorite(Boolean isFavorite) {
		this.isFavorite = isFavorite;
	}
	public int getWordHints() {
		return wordHints;
	}
	public void setWordHints(int wordHints) {
		this.wordHints = wordHints;
	}
	public int getSoundHints() {
		return soundHints;
	}
	public void setSoundHints(int soundHints) {
		this.soundHints = soundHints;
	}
	public int getAttempts() {
		return attempts;
	}
	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}
	public boolean isSolved() {
		return isSolved;
	}
	public void setSolved(boolean isSolved) {
		this.isSolved = isSolved;
	}
	public boolean isSeenToday() {
		return isSeenToday;
	}
	public void setSeenToday(boolean isSeenToday) {
		this.isSeenToday = isSeenToday;
	}
	public Calendar getLastSeen() {
		return lastSeen;
	}
	public void setLastSeen(Calendar lastSeen) {
		this.lastSeen = lastSeen;
	}
}
