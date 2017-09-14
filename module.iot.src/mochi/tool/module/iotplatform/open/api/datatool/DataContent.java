package mochi.tool.module.iotplatform.open.api.datatool;

public class DataContent {
	
	public String sourceTag;
	public short valueType;
	public byte[] value;
	
	public DataContent(String sourceTag, short valueType, byte[] value) {
		this.sourceTag = sourceTag;
		this.valueType = valueType;
		this.value = value;
	}
	
}
