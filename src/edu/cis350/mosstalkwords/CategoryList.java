package edu.cis350.mosstalkwords;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/*
 * CategoryList is to represent the category list module
 */
public class CategoryList extends Activity {

	// The data to show
	ArrayList<Category> categoryList;

	private void initList() {
		categoryList = new ArrayList<Category>();
		// We populate the category list
		categoryList.add(new Category(R.drawable.living, "Living"));
		categoryList.add(new Category(R.drawable.nonliving, "NonLiving"));
	}

	public void startMain(String categoryName) {
		Intent activityMain = new Intent(this, MainActivity.class);
		activityMain.putExtra("startCategory", categoryName);
		startActivity(activityMain);
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
		// The View id used to show the data. The key number and the view id
		// must match
		lv.setAdapter(new CategoryListAdapter(this, R.layout.row_image,
				categoryList));
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
				startMain(categoryList.get(position).getName());
				finish();
			}

		});
	}
}
