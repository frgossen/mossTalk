package edu.cis350.mosstalkwords;

import java.util.ArrayList;

public class CategoryMappings {


	private ArrayList<String> categoryList = new ArrayList<String>();
	
	public CategoryMappings()
	{
		categoryList.add("Living");
		categoryList.add("NonLiving");
	}
	
	public void addCategory(String s)
	{
		categoryList.add(s);
	}
	
	public String getCategory(int position)
	{
		return categoryList.get(position);
	}
	
	public ArrayList<String> getCategoryList()
	{
		return categoryList;
	}
	
	
	


}
