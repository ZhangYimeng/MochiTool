package mochi.tool.mongodb.cache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Matryoshka {

	private List<Field> fields;
	
	public Matryoshka() {
		fields = new ArrayList<Field>();
	}
	
	public Matryoshka(Field... fields) {
		this.fields = Arrays.asList(fields);
	}
	
	public Matryoshka(List<Field> fields) {
		this.fields = fields;
	}
	
	public void addField(Field field) {
		fields.add(field);
	}
	
	public Field getFieldByIndex(int index) {
		return fields.get(index);
	}
	
	public int getFieldsNumber() {
		return fields.size();
	}
	
	public Iterator<Field> getFieldsIterator() {
		return fields.iterator();
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < fields.size(); i++) {
			sb.append(fields.get(i) + "\n");
		}
		return sb.toString();
	}
	
}
