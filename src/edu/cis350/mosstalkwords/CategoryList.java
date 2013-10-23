package edu.cis350.mosstalkwords;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;



public class CategoryList extends Activity {

	// The data to show
	ArrayList<String> categoryList = new ArrayList<String>();
	
	private void initList() {
	    // We populate the category list
		categoryList.add("Living");
		categoryList.add("NonLiving");
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.category_list);

	    initList();

	    // We get the ListView component from the layout
	    ListView lv = (ListView) findViewById(R.id.category_list);
	    
	    // This is a simple adapter that accepts as parameter
	    // Context
	    // Data list
	    // The row layout that is used during the row creation
	    // The View id used to show the data. The key number and the view id must match
	    lv.setAdapter(new CategoryListAdapter(this, categoryList));
	    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

	        public void onItemClick(AdapterView<?> parent, final View view,
	            int position, long id) {
	          final String item = (String) parent.getItemAtPosition(position);
	          System.out.println(""+position);
	          Intent i = getIntent();
	          i.putExtra("indexOfSetsArray", position);
	  		  setResult(RESULT_OK, i);
	  		  finish();
	        }

	      });
	    }
	}
		
