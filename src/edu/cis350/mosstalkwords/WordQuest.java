package edu.cis350.mosstalkwords;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WordQuest extends SQLiteOpenHelper {
	
	  // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "mossWords";

    
    // Contacts table name
    // Table name according to user name
    private String tableName;
    
    public WordQuest(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
	 
    public void getTable(String uname) {
    	String userName = uname.toLowerCase();
    	tableName = userName + "_wordQuest";
    }
    
    public int getLevelsForMode(String mode)
    {
    	/* I need to start from level 1 and go till I find locked level */
    	int level = 1;
    	boolean lockedLevelFound = false;
    	SQLiteDatabase db = this.getReadableDatabase();
		
    	
    	do{
	    		String getProgress = "select count(*) from " + tableName + " where progress >= 80 AND level = "+ level;
	    		String getUnassisted = "select count(*) from " + tableName + " where unassistedGreaterThan24 >= 1 AND level = "+ level;
	    		
	    		
	        	Cursor cursor1 = db.rawQuery(getProgress, null);
	        	Cursor cursor2 = db.rawQuery(getUnassisted, null);
	    		/* Get 100 images of a level */
	    		if(cursor1 != null && cursor2 != null)
	        	{
	    			
	    			cursor1.moveToFirst();
	        		cursor2.moveToFirst();
	        		if(mode.equalsIgnoreCase("easy"))
	    	    	{
		        		if(Integer.parseInt(cursor1.getString(0)) >= 50 && Integer.parseInt(cursor2.getString(0)) >= 30)
		        			level++;
		        		else
			        	{
		        			lockedLevelFound = true;
			        	}
	    	    	}
	        		else if(mode.equalsIgnoreCase("medium"))
	        		{
	        			if(Integer.parseInt(cursor1.getString(0)) >= 60 && Integer.parseInt(cursor2.getString(0)) >= 40)
		        			level++;
		        		else
			        	{
		        			lockedLevelFound = true;
			        	}
	        		}
	        		else if(mode.equalsIgnoreCase("hard"))
	        		{
	        			if(Integer.parseInt(cursor1.getString(0)) >= 70 && Integer.parseInt(cursor2.getString(0)) >= 50)
		        			level++;
		        		else
			        	{
		        			lockedLevelFound = true;
			        	}
	        		}
	        		else if(mode.equalsIgnoreCase("harder"))
	        		{
	        			if(Integer.parseInt(cursor1.getString(0)) >= 80 && Integer.parseInt(cursor2.getString(0)) >= 60)
		        			level++;
		        		else
			        	{
		        			lockedLevelFound = true;
			        	}
	        		}
	        		else if(mode.equalsIgnoreCase("hardest"))
	        		{
	        			if(Integer.parseInt(cursor1.getString(0)) >= 90 && Integer.parseInt(cursor2.getString(0)) >= 70)
		        			level++;
		        		else
			        	{
		        			lockedLevelFound = true;
			        	}
	        		}
	        	}
	    }while(lockedLevelFound == false && level <= 4);
    	return level;
    }
    
    public List<ImageStatistics> getImagesForLevel(int level){
    	
    			SQLiteDatabase db = this.getReadableDatabase();
				String getItems = "select * from " + tableName + " where level = "+ level + " order by weight desc";
	    		List<ImageStatistics> returnList = new ArrayList<ImageStatistics>();
	        	Cursor cursor = db.rawQuery(getItems, null);
	        	int num = 1;
	        	
	    		if(cursor != null)
	        	{
	    			cursor.moveToFirst();
	    			do
	    			{
	    				ImageStatistics imageStat = new ImageStatistics();
	    				
	    				imageStat.setImageName(cursor.getString(1));
	    				imageStat.setCategory(null);
	    				imageStat.setWordHints(0);
	    				imageStat.setSoundHints(0);
	    				imageStat.setSolved(false);
	    				imageStat.setAttempts(0);
    					imageStat.setIsFavorite(false);
    					Calendar cal = Calendar.getInstance();
    					cal.setTime(new Date(cursor.getString(9)));
    					imageStat.setLastSeen(cal);   
    					imageStat.setSeenToday(checkIfSeenToday(cal));
	    				imageStat.setUrl(cursor.getString(11));
	    				
	    				returnList.add(imageStat);
	    				cursor.moveToNext();
	    				num++;
	    			}while(num <= (20 - level + 1));
	        	}
	    		
	    		for(int i = level-1; i > 0; i--)
	    		{	
	    			ImageStatistics imageStat = new ImageStatistics();
	    			getItems = "select * from " + tableName + " where level = "+ i + " order by weight desc";
		        	Cursor cursor2 = db.rawQuery(getItems, null);
		        	
		        	if(cursor2 != null){
		        		cursor2.moveToFirst();
		        		imageStat.setImageName(cursor2.getString(1));
	    				imageStat.setCategory(null);
	    				imageStat.setWordHints(0);
	    				imageStat.setSoundHints(0);
	    				imageStat.setSolved(false);
	    				imageStat.setAttempts(0);
    					imageStat.setIsFavorite(false);
    					Calendar cal = Calendar.getInstance();
    					cal.setTime(new Date(cursor2.getString(9)));
    					imageStat.setLastSeen(cal);   
    					imageStat.setSeenToday(checkIfSeenToday(cal));
	    				imageStat.setUrl(cursor2.getString(11));
	    				returnList.add(imageStat);
		        	}
	    		}
	    		
	    		return returnList;
    }
    
    public boolean checkIfSeenToday (Calendar lastSeen) {
		if(lastSeen != null)
		{
	    	double hours = (System.currentTimeMillis() - lastSeen.getTimeInMillis()) / 1000 / 60 / 60;			
			if(hours<=24)
				return true;
			else
				return false;
		}
		return false;
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
    

}
