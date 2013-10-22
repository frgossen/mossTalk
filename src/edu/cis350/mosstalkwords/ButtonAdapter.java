package edu.cis350.mosstalkwords;
import android.content.Context;

import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

class ClickListenerDifficult implements OnClickListener
{
	private final int position;

	public ClickListenerDifficult(int position)
	{
		this.position = position;
	}

	public void onClick(View v)
 	{
		// Preform a function based on the position
		// someFunction(this.position)
 	}
}

public class ButtonAdapter extends BaseAdapter {
	private Context mContext;
	public String[] filesnames = {
		"Easiest",
		"Medium",
		"Harder",
		"Hardest"
	};

	// Gets the context so it can be used later
	public ButtonAdapter(Context c) {
		mContext = c;
	}

	// Total number of things contained within the adapter
	public int getCount() {
		return filesnames.length;
 	}

	// Require for structure, not really used in my code.
	public Object getItem(int position) {
		return null;
	}

	// Require for structure, not really used in my code. Can
	// be used to get the id of an item in the adapter for 
	// manual control. 
	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Button btn;
		if (convertView == null) {  
			// if it's not recycled, initialize some attributes
			btn = new Button(mContext);
			btn.setLayoutParams(new GridView.LayoutParams(250, 150));
			btn.setPadding(40, 40, 40, 40);
		} 
		else {
			btn = (Button) convertView;
		}
  
  		btn.setText(filesnames[position]); 
  		// 	filenames is an array of strings
  		btn.setTextColor(Color.WHITE);
  		btn.setBackgroundResource(R.drawable.button_blue);
  		btn.setId(position);

  		// Set the onclicklistener so that pressing the button fires an event
  		// We will need to implement this onclicklistner.
  		btn.setOnClickListener(new ClickListenerDifficult(position));
  		
  		return btn;
	}
	
	
}
