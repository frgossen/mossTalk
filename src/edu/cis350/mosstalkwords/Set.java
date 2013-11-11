package edu.cis350.mosstalkwords;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Set implements Parcelable {
	
	private ArrayList<Image> images;

	// BEGINNNING --- IMPLEMENT PARCELABLE INTERFACE
	public int describeContents() {
        return 0;
    }
    public void writeToParcel(Parcel out, int flags) {
    	out.writeList(images);
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
    	images = new ArrayList<Image>();
    	in.readList(images, null);
    }
	// END --- IMPLEMENT PARCELABLE INTERFACE
	
	public Set(List<Image> imageList){
		images = new ArrayList<Image>(imageList);
	}
	
	public String[] getWords(){
		String[] words = new String[images.size()];
		for(int i=0; i<words.length; i++)
			words[i] = images.get(i).getWord();
		return words;
	}
	
	public Image get(int i){
		return images.get(i);
	}

	public String getWord(int i){
		return get(i).getWord();
	}
	
	public int getSize(){
		return images.size();
	}
}
