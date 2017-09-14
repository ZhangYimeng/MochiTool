package mochi.tool.mongodb.cache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Filter {

	private List<Object> filter;
	
	public Filter() {
		filter = new ArrayList<Object>();
	}
	
	public Filter(List<Object> filter) {
		this.filter = filter;
	}
	
	public Filter(Object... filedValue) {
		filter = Arrays.asList(filedValue);
	}
	
	public void addFilterFieldValue(Object filedValue) {
		filter.add(filedValue);
	}
	
	public Object getFieldValueByIndex(int index) {
		return filter.get(index);
	}
	
	public int getFilterFieldValueNumber() {
		return filter.size();
	}
	
	public Iterator<Object> getFilterFieldValueIterator() {
		return filter.iterator();
	}
	
}
