package edu.cis350.mosstalkwords.test;

import edu.cis350.mosstalkwords.SetStatistics;
import android.os.Bundle;
import android.os.Parcel;
import android.test.AndroidTestCase;

public class SetStatisticsTest extends AndroidTestCase {
	private SetStatistics statistics;

	public void setUp() {
		statistics = new SetStatistics(5);	
		statistics.incAttempts(2);
		statistics.incSoundHint(2);
		statistics.incWordHint(2);
		statistics.incSoundHint(3);
		statistics.setSolved(2, true);
		statistics.setSolved(3, true);
		statistics.setSolved(4, true);
	}
	
	public void testWriteToParcel() {
		// Write to Parcel 
		Bundle b = new Bundle();
		b.putParcelable("tagForSetStatistics", statistics);
		Parcel p = Parcel.obtain();
		b.writeToParcel(p, 0);
		// Read from Parcel 
		p.setDataPosition(0);
		Bundle b2 = p.readBundle();
		b2.setClassLoader(SetStatistics.class.getClassLoader());
		SetStatistics statistics2 = b2.getParcelable("tagForSetStatistics");
			    
		// Compare statistics1 and statistics2
		assertEquals(statistics2.getAttempts(2), 1);
		assertEquals(statistics2.getSoundHints(2), 1);
		assertEquals(statistics2.getWordHints(2), 1);
		assertEquals(statistics2.getSoundHints(3), 1);
		assertFalse(statistics2.isSolved(1));
		assertTrue(statistics2.isSolved(2));
		assertTrue(statistics2.isSolved(3));
		assertTrue(statistics2.isSolved(4));
	}
	
	
	public void testReset() {
		statistics.reset();
		assertTrue(statistics.getAttempts(2) == 0);
	}

	public void testGetSize() {
		assertTrue(statistics.getSize() == 5);
	}

	public void testGetAttempts() {
		statistics.incAttempts(2);
		assertTrue(statistics.getAttempts(2) == 2 && statistics.getAttempts(3) == 0);
	}

	public void testGetWordHints() {
		statistics.incWordHint(2);
		assertTrue(statistics.getWordHints(2) == 2 && statistics.getWordHints(3) == 0);
	}

	public void testGetSoundHints() {
		statistics.incSoundHint(2);
		assertTrue(statistics.getSoundHints(2) == 2 && statistics.getSoundHints(3) == 1);
	}

	public void testSolved() {
		assertTrue(statistics.isSolved(2) && statistics.isSolved(3) 
				&& statistics.isSolved(4) && (!statistics.isSolved(0)));
	}

	public void testGetScoreIntBoolean() {
		assertTrue(statistics.getScore(3, true) == 50);
		assertTrue(statistics.getScore(0, true) == 100);
		assertTrue(statistics.getScore(0, false) == 0);
	}
	
	public void testGetTotalScore() {
		assertTrue(statistics.getTotalScore(4) == 75);
		assertTrue(statistics.getTotalScore() == 175);
	}

	public void testGetLongestStreak() {
		statistics.reset();
		statistics.setSolved(0, true);
		statistics.setSolved(2, true);
		statistics.setSolved(3, true);
		statistics.setSolved(4, true);
		System.out.println(statistics.getLongestStreak());
		assertTrue(statistics.getLongestStreak() == 3);
	}

	public void testGetCompleteness() {
		assertTrue(statistics.getCompleteness() == 0.35);
		assertTrue(statistics.getCompletenessPercent() == 35);
		assertTrue(statistics.getStarScore() == 2);
	}

	public void testIncWordHint() {
		assertTrue(statistics.getWordHints(2) == 1);
	}

	public void testIncAttempts() {
		assertTrue(statistics.getAttempts(2) == 1);
	}

	public void testIncSoundHint() {
		assertTrue(statistics.getSoundHints(3) == 1);
	}

}


