package org.silk.checklist.activity;

import android.graphics.drawable.Drawable;
import android.net.Uri;

public class MyMenuItem {
	private long id;
	private String menuTitle;
	private int menuIconRes;
	private Class clazz;
	public MyMenuItem() {
		super();
	}
	public MyMenuItem(String menuTitle, int menuIconRes) {
		super();

		this.menuTitle = menuTitle;
		this.menuIconRes = menuIconRes;

	}
	public String getMenuTitle() {
		return menuTitle;
	}
	public void setMenuTitle(String menuTitle) {
		this.menuTitle = menuTitle;
	}
	public int getMenuIcon() {
		return menuIconRes;
	}
	public void setMenuIcon(int menuIconRes) {
		this.menuIconRes = menuIconRes;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Class getClazz() {
		return clazz;
	}
	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}
	
	
}
