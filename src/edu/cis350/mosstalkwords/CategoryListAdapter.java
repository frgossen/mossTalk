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
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/*
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
*/


public class CategoryListAdapter extends ArrayAdapter<Category> {
	
	private Context context;
	private int layoutResourceId;
	private ArrayList<Category> categoryList;
	
	public CategoryListAdapter (Context context, int layoutResourceId, ArrayList<Category> categoryList) {
		super(context, layoutResourceId, categoryList);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.categoryList = categoryList;
    }
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        CategoryHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new CategoryHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.icon);
            holder.txtTitle = (TextView)row.findViewById(R.id.name);
            
            row.setTag(holder);
        }
        else
        {
            holder = (CategoryHolder)row.getTag();
        }
        
        Category category = categoryList.get(position);
        holder.txtTitle.setText(category.getName());
        holder.imgIcon.setImageResource(category.getIcon());
        
        return row;
    }
	
	static class CategoryHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
    }
}
