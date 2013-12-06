package edu.cis350.mosstalkwords;

/*
 * Category is used to score the name and its icon corresponding with the layout
 */
public class Category {

	private String name;
	private int icon;

	public Category(int icon, String name) {
		this.icon = icon;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getIcon() {
		return icon;
	}
}
