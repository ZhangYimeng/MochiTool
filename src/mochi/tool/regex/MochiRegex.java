package mochi.tool.regex;

import java.util.regex.Pattern;

public class MochiRegex {

	private String text;
	private Pattern pattern;
	
	public MochiRegex(String text, String pattern) {
		this.text = text;
		this.pattern = Pattern.compile(pattern);
	}
	
}
