package mochi.tool.mongodb.operators;

public class QueryOperators {

	/**
	 * 用来查找已经存在的键所对应的所有键值对。
	 */
	public static final String $EXIST = "$exists";
	
	/**
	 * 匹配键值不等于指定值的文档。
	 */
	public static final String $NE = "$ne";
	
	/**
	 * 匹配键不存在或者键值不等于指定数组的任意值的文档。
	 */
	public static final String $NIN = "$nin";
	
}
