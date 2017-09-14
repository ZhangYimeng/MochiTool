package mochi.tool.mongodb.cache;

public class Field {

	private String name;
	
	public Field(String name) {
		this.name = name;
	}
	
	public String getFieldName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
