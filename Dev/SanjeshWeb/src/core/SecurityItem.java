package core;

import java.util.Arrays;

public class SecurityItem {
	
	private String key;
	private String title;
	private SecurityItemList children = new SecurityItemList(this);
	
	SecurityItemList parentList;
	
	public SecurityItem(){
	}
	
	public SecurityItem(String key, String title){
		this();
		this.key = key;
		this.title = title;
	}
	
	public SecurityItem(String key, String title, SecurityItem ... children){
		this(key, title);
		this.children.addAll(Arrays.asList(children));
	}
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public SecurityItemList getChildren(){
		return children;
	}
	
	public String getFullKey(){
		String fullKey = this.key;
		SecurityItem si = this;
		while( si.parentList != null ){
			si = si.parentList.parentItem;
			fullKey = si.key + "." + fullKey;
		}
		return fullKey;
	}
	
	public SecurityItem findRoot(){
		SecurityItem si = this;
		while( si.parentList != null ){
			si = si.parentList.parentItem;
		}
		return si;
	}
}
