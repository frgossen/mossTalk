package edu.cis350.mosstalkwords;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter {

	private Context context; // context for inflater	
	private int[] results; // scores (will be used to mark them correct/incorrect
	private String[] imageNames; // list of names of the images
	private boolean[] checked;
	public boolean[] getChecked() {
		return checked;
	}


	private View gridView;
//	private ImageCache imCache; // image cache.
	
	public ImageAdapter(Context ctx, int[] res, String[] imn) {
		super();
		context = ctx;
		results = res;
		imageNames = imn;
		checked = new boolean[res.length];
		System.out.println(res.length +"--" + imn.length);
		System.out.println(results[0] +"--" + imageNames[0]);
	}

	public int getCount() {
		return imageNames.length;
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return imageNames[position];
	}
	
	public boolean getCheckBox(int position)
	{
		
		return true;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
		
	
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		System.out.println("Here -1");
		
		
		System.out.println("Here 0");
		if(convertView==null){
			LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			gridView = li.inflate(R.layout.favorite_image, null);
			
			System.out.println("Here 1");
			
			// set value into textview
			TextView textView = (TextView) gridView.findViewById(R.id.item_image_label);
			System.out.println("p:"+position);
			textView.setText("" + results[position]);
			
			if(results[position] == 100)
			{
				System.out.println("HERE!");
				gridView.findViewById(R.id.fav_item).setBackgroundResource(0x01060014);	
			}
			else if(results[position] == 0)
			{
				System.out.println("HERE1!");
				gridView.findViewById(R.id.fav_item).setBackgroundResource(0x01060016);	
			}
			else
			{
				gridView.findViewById(R.id.fav_item).setBackgroundColor(0xFFFFFF00);	
			}
			
			//set rowview color
			//View v = gridView.findViewById(R.id.favoriteRow);
			
			System.out.println("Here 2");
			// set image based on selected text
			ImageView imageView = (ImageView) gridView.findViewById(R.id.item_image);
			

			CheckBox c = (CheckBox) gridView.findViewById(R.id.item_checkbox);
			
			final int a = position;
			
			c.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
					if(buttonView.isChecked())
					{
						checked[a] = true;
						
					}
					else
					{
						checked[a] = false;
						
					}
					System.out.println(a + " "+ checked[a]);
					
				}
			});
			
			
			Bitmap im = ImageCache.getInstance().getBitmapFromCache(imageNames[position]);

			System.out.println("Here 3");
			Drawable drawableBitmap = new BitmapDrawable(context.getResources(), im);
			imageView.setImageDrawable(drawableBitmap);
			

		}
		else
		{
			gridView = convertView;
		}
		return gridView;

	}


}
