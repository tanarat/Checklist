package org.silk.checklist;
import java.util.ArrayList;
import java.util.List;


public class ListItem {
	private Long id;
	private String name;
	private List<ListItem> subItems = new ArrayList<ListItem>();
	private boolean expanded;
	
	
	public ListItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ListItem(Long id, String name, List<ListItem> subItems, boolean expanded) {
		super();
		this.id = id;
		this.name = name;
		if(subItems != null)
			this.subItems = subItems;
		this.expanded = expanded;
	}
	public ListItem(Long itemId, String name) {
		// TODO Auto-generated constructor stub
		this.id = itemId;
		this.name = name;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<ListItem> getSubItems() {
		return subItems;
	}
	public void setSubItems(List<ListItem> subItems) {
		this.subItems = subItems;
	}
	public boolean isExpanded() {
		return expanded;
	}
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public boolean isExpandable() {
		return subItems.size() != 0;
	}

	
	
}
