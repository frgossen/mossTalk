package edu.cis350.mosstalkwords;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Set implements Parcelable {
	
	private ArrayList<ImageStatistics> images;

	// BEGINNNING --- IMPLEMENT PARCELABLE INTERFACE
	public int describeContents() {
        return 0;
    }
    public void writeToParcel(Parcel out, int flags) {
    	out.writeTypedList(images);
    }
    public static final Parcelable.Creator<Set> CREATOR = new Parcelable.Creator<Set>() {
        public Set createFromParcel(Parcel in) {
            return new Set(in);
        }

        public Set[] newArray(int size) {
            return new Set[size];
        }
    };
    private Set(Parcel in) {
    	images = new ArrayList<ImageStatistics>();
        in.readTypedList(images, ImageStatistics.CREATOR);
    }
	// END --- IMPLEMENT PARCELABLE INTERFACE
	
	public Set(List<ImageStatistics> imageList){
		images = new ArrayList<ImageStatistics>(imageList);
	}
	
	public String[] getWords(){
		String[] words = new String[images.size()];
		for(int i=0; i<words.length; i++)
			words[i] = images.get(i).getImageName();
		return words;
	}
	
	public ImageStatistics get(int i){
		return images.get(i);
	}

	public String getWord(int i){
		return get(i).getImageName();
	}
	
	public int getSize(){
		return images.size();
	}

	public int getTotalScore() {
		return getTotalScore(getSize());
	}

	public int getScore(int imageIdx){
		return getScore(imageIdx, false);
	}
	
	public void incWordHint(int imageIdx){
		ImageStatistics is = get(imageIdx);
		is.setWordHints(is.getWordHints()+1);
	}

	public void setSolved(int imageIdx, boolean solved){
		ImageStatistics is = get(imageIdx);
		is.setSolved(solved);
	}

	public void incAttempts(int imageIdx){
		ImageStatistics is = get(imageIdx);
		is.setAttempts(is.getAttempts()+1);
	}

	public void incSoundHint(int imageIdx){
		ImageStatistics is = get(imageIdx);
		is.setSoundHints(is.getSoundHints()+1);
	}
		
	public int getScore(int imageIdx, boolean ignoreSolvedStatus){
		if (!ignoreSolvedStatus && !get(imageIdx).isSolved())
			return 0;
		int score = 100;
		if (get(imageIdx).getWordHints() > 0)
			score -= 75;
		else if (get(imageIdx).getSoundHints() > 0) 
			score -= 50;
		return score;
	}
	
	public int getTotalScore(int firstNImages) {
		int sum = 0;
		firstNImages = Math.min(firstNImages, getSize());
		for(int i=0; i<firstNImages; i++)
			sum += getScore(i);
		return sum;
	}

	public boolean equals(Object obj){
		if(obj instanceof Set) {
			Set other = (Set) obj;
			return images.equals(other.images);
		}
		else
			return false;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(ImageStatistics img : images)
			sb.append(img).append(", ");
		sb.append("]");
		return sb.toString();
	}
	
}
