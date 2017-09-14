package mochi.tool.mongodb.cache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import mochi.tool.mongodb.cache.foundation.exception.ValueNullException;

public class Value {

	private List<String> keyFields;
	private Object value;
	
	public Value() {
		keyFields = new ArrayList<String>();
	}
	
	public Value(String... keyFields) {
		this.keyFields = Arrays.asList(keyFields);
	}
	
	public Value(List<String> keyFields) {
		this.keyFields = keyFields;
	}
	
	public Value(Object value, String... keyFields) {
		this.keyFields = Arrays.asList(keyFields);
		this.value = value;
	}
	
	public Value(List<String> keyFields, Object value) {
		this.keyFields = keyFields;
		this.value = value;
	}
	
	public void addKeyField(String keyField) {
		keyFields.add(keyField);
	}
	
	public String getKeyFieldByIndex(int index) {
		return keyFields.get(index);
	}
	
	public int getKeyFieldNumber() {
		return keyFields.size();
	}
	
	public Iterator<String> getKeyFieldsIterator() {
		return keyFields.iterator();
	}
	
	public Object getValue() throws ValueNullException {
		if(value != null) {
			return value;
		} else {
			throw new ValueNullException();
		}
	}
	
	public void setValue(Object value) {
		this.value = value;
	}
	
}
