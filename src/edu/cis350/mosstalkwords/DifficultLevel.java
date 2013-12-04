package edu.cis350.mosstalkwords;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;



public class DifficultLevel extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.difficult_level);
	    GridView gridview = (GridView) findViewById(R.id.difficultGridview);
		gridview.setAdapter(new ButtonAdapter(this));
	}
		
}

		
		
		
		