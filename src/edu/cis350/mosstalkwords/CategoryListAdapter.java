package edu.cis350.mosstalkwords;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


class ClickListenerCategory implements OnClickListener
{
	private final int position;

	public ClickListenerCategory (int position)
	{
		this.position = position;
	}

	public void onClick(View v)
 	{
		// Preform a function based on the position
		// someFunction(this.position)
 	}
}



public class CategoryListAdapter extends BaseAdapter {
	
	private Context mContext;
	private ArrayList<String> categoryList;
	
	// Context
    // Data list
    // The keys used to retrieve the data
	public CategoryListAdapter (Context c, ArrayList<String> categoryList) {
		mContext = c;
		this.categoryList = categoryList;
	}
	
	// Total number of things contained within the adapter
	public int getCount() {
		return categoryList.size();
	}
	
	// Require for structure, not really used in my code.
	public Object getItem(int position) {
		return null;
	}
	
	public long getItemId(int position) {
		return position;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
	    View v = convertView;

	    TextView nameView;

	    // First let's verify the convertView is not null
	    if (convertView == null) {
	        // This a new view we inflate the new layout
	        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        v = inflater.inflate(R.layout.row_image, null);
	        // Now we can fill the layout with the right values
	        TextView tv = (TextView) v.findViewById(R.id.name);

	        nameView = tv;
	        v.setTag(nameView);
	    }
	    else 
	        nameView = (TextView) v.getTag();

	    System.out.println("Position ["+position+"]");
	    String p = categoryList.get(position);
	    nameView.setText(p);
	    return v;
	}
}
