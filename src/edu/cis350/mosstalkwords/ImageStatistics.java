package edu.cis350.mosstalkwords;

import java.util.Calendar;
import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

public class ImageStatistics implements Parcelable {

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

	// BEGINNNING --- IMPLEMENT PARCELABLE INTERFACE
	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel out, int flags) {
		out.writeString(imageName);
		out.writeString(url);
		out.writeString(category);
		out.writeInt(isFavorite ? 1 : 0);
		out.writeInt(wordHints);
		out.writeInt(soundHints);
		out.writeInt(attempts);
		out.writeInt(isSolved ? 1 : 0);
		out.writeInt(isSeenToday ? 1 : 0);
		out.writeLong(lastSeen.getTimeInMillis());
	}

	public static final Parcelable.Creator<ImageStatistics> CREATOR = new Parcelable.Creator<ImageStatistics>() {
		public ImageStatistics createFromParcel(Parcel in) {
			return new ImageStatistics(in);
		}

		public ImageStatistics[] newArray(int size) {
			return new ImageStatistics[size];
		}
	};

	private ImageStatistics(Parcel in) {
		imageName = in.readString();
		url = in.readString();
		category = in.readString();
		isFavorite = in.readInt() == 1;
		wordHints = in.readInt();
		soundHints = in.readInt();
		attempts = in.readInt();
		isSolved = in.readInt() == 1;
		isSeenToday = in.readInt() == 1;
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(in.readLong()));
		lastSeen = c;

	}
	// END --- IMPLEMENT PARCELABLE INTERFACE
	
	public ImageStatistics(){}

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
