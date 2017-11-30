package mochi.tool.regex;

public class Value_Posision {

	private String value;
	private long startIndex;
	private long endIndex;
	
	public Value_Posision(String value, long startIndex, long endIndex) {
		this.setValue(value);
		this.setStartIndex(startIndex);
		this.setEndIndex(endIndex);
	}

	public long getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(long endIndex) {
		this.endIndex = endIndex;
	}

	public long getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(long startIndex) {
		this.startIndex = startIndex;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
