package mochi.tool.mongodb.cache;

public class DistinctResult<T> {

	private T value;
	
	public DistinctResult(T next) {
		value = next;
	}
	
	public T getValue() {
		return value;
	}
	
	public String toString() {
		return value.toString();
	}

}
