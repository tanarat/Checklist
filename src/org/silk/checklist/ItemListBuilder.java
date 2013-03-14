package org.silk.checklist;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public class ItemListBuilder {
	
	private List<ListItem> itemList = new ArrayList<ListItem>();
	private List<ListItem> itemArrayList = new ArrayList<ListItem>();
	
	public void setList(List<ListItem> itemList){
		this.itemList = itemList;
	}
	public void add(ListItem item){
		itemList.add(item);
	}
	public void add(ListItem item, Long parentId){
		ListItem parent = search(parentId, this.itemList);
		if(parent != null){
			parent.getSubItems().add(item);
		}
	}
	public boolean alreadyAdd(Long itemId) {
		// TODO Auto-generated method stub
		return getItem(itemId) != null;
	}
	public int getItemIndex(long itemId) {
		// TODO Auto-generated method stub
		for (int i = 0; i < itemArrayList.size() ; i++) {
			if(itemArrayList.get(i).getId().longValue() == itemId)
				return i;
		}
		return 0;
	}
	public ListItem getItem(Long itemId){
		return search(itemId,itemList);
	}
	public void toggleExpanded(Long itemId){
		ListItem itemFound = search(itemId,itemList);
		if(itemFound != null){
			itemFound.setExpanded(!itemFound.isExpanded());
		}
	}
	public void expand(Long itemId){
		ListItem itemFound = search(itemId,itemList);
		if(itemFound != null){
//			System.out.println("expand item : " + itemFound.getName());
			itemFound.setExpanded(true);
		}
		else
			System.out.println("not found item id " + itemId);
		
	}
	public void collapse(Long itemId){
		ListItem itemFound = search(itemId,itemList);
		if(itemFound != null){
//			System.out.println("collapse item : " + itemFound.getName());
			itemFound.setExpanded(false);
		}
		else
			System.out.println("not found item id " + itemId);
	}
	private ListItem search(Long searchId,List<ListItem> searchList){
//		boolean found = false;
		ListItem itemFound = null;
		for (int i = 0; i < searchList.size() && itemFound == null; i++) {
			ListItem item = searchList.get(i);
			if(item.getId().longValue() == searchId.longValue()){
				itemFound = item;
//				found = true;	
			}else{
				itemFound = search(searchId,item.getSubItems());
			}
		}
		return itemFound;
	}
	
	public List<ListItem> getItemArrayList(){
		itemArrayList.clear();
		for (int i = 0; i < itemList.size(); i++) {
			ListItem item = itemList.get(i);
			addToItemArrayList(item);
		}
		return itemArrayList;
	}

	private void addToItemArrayList(ListItem item){
		//add itself
		itemArrayList.add(item);
		//if expanded, add sub
		if(item.isExpanded()){
			List<ListItem> subs = item.getSubItems();
			for (int i = 0; i < subs.size(); i++) {
				ListItem subItem = subs.get(i);
				addToItemArrayList(subItem);
			}
		}
		
	}
	public void clear() {
		// TODO Auto-generated method stub
		itemList.clear();
		itemArrayList.clear();
	}
	
	
	
}
