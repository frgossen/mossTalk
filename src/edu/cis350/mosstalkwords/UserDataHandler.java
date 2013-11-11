package edu.cis350.mosstalkwords;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;


public class UserDataHandler {
        
        public List<UserStimuli> getFavoriteStimulus(Context context)
        {
                List<UserStimuli> usList = new ArrayList<UserStimuli>();
                DatabaseHandler dbHandler = new DatabaseHandler(context);
                
                usList = dbHandler.getFavoriteStimuli();
                
                
                Collections.shuffle(usList);
                
                if(usList.size() < 20)
                {
                        return usList;
                }
                else
                {
                        List<UserStimuli> usListFinal = new ArrayList<UserStimuli>();
                        for(int i=0;i < 20;i++)
                        {
                                usListFinal.add(usList.get(i));
                        }
                        return usListFinal;
                }
                
        }
}