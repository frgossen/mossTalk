package edu.cis350.mosstalkwords.test;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Parcel;

import edu.cis350.mosstalkwords.Image;
import edu.cis350.mosstalkwords.ImageStatistics;
import edu.cis350.mosstalkwords.Set;
import edu.cis350.mosstalkwords.SetStatistics;

import junit.framework.TestCase;

public class SetTest extends TestCase {

	private Image img1, img2, img3;
	private ArrayList<Image> sampleImages;
	private Set s;
	
	public void setUp(){
		sampleImages = new ArrayList<Image>();
		img1 = new ImageStatistics();
		img1.set"Elephant", "Living", "20", "30", "40", "http://www.google.com/elephant.png");
		img2 = new Image("House", "NonLiving", "21", "31", "41", "http://www.google.com/house.png");
		img3 = new Image("Car", "NonLiving", "22", "32", "42", "http://www.google.com/car.png");
		sampleImages.add(img1);
		sampleImages.add(img2);
		sampleImages.add(img3);
		s = new Set(sampleImages);
	}
	
	public void testWriteToParcel() {
		// Write to Parcel 
		Bundle b = new Bundle();
		b.putParcelable("tagForSet", s);
		Parcel p = Parcel.obtain();
		b.writeToParcel(p, 0);
		// Read from Parcel 
		p.setDataPosition(0);
	    Bundle b2 = p.readBundle();
	    b2.setClassLoader(Set.class.getClassLoader());
	    Set s2 = b2.getParcelable("tagForSet");
	    
	    // Compare original and recreated
 		assertEquals(s, s2);
	}

	public void testGetWords() {
		String[] words = s.getWords();
		assertEquals(words.length, 3);
		assertEquals(words[0], "Elephant");
		assertEquals(words[1], "House");
		assertEquals(words[2], "Car");
	}

	public void testGet() {
		assertEquals(s.get(0), img1);
		assertEquals(s.get(1), img2);
		assertEquals(s.get(2), img3);
	}

	public void testGetWord() {
		assertEquals(s.getWord(0), "Elephant");
		assertEquals(s.getWord(1), "House");
		assertEquals(s.getWord(2), "Car");
	}

	public void testGetSize() {
		assertEquals(s.getSize(), 3);
	}
	
	public void testEquals(){
		Set s2 = new Set(sampleImages);
		assertEquals(s, s2);
	}
}
