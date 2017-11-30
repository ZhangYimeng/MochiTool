package mochi.tool.regex;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MochiRegex {

	private String text;
	private Pattern pattern;
	private String patternString;
	private Matcher matcher;
	
	public MochiRegex(String text, String pattern) {
		this.text = text;
		patternString = pattern;
		this.pattern = Pattern.compile(pattern);
		matcher = this.pattern.matcher(text);
	}
	
	public List<String> findAllMatchesValueInTheText() {
		LinkedList<String> list = new LinkedList<String>();
		while(matcher.find()) {
			list.add(matcher.group());
		}
		return list;
	}
	
	public List<Value_Posision> findAllMatchesValue_PosisionInTheText() {
		LinkedList<Value_Posision> list = new LinkedList<Value_Posision>();
		while(matcher.find()) {
			Value_Posision vp = new Value_Posision(matcher.group(), matcher.start(), matcher.end());
			list.add(vp);
		}
		return list;
	}
	
	public boolean isTheTextMatchThisPattern() {
		return text.matches(patternString);
	}
	
	public static void main(String[] args) {
		String text = "qwe";
		String pattern = "\\D";
		MochiRegex mr = new MochiRegex(text, pattern);
		System.out.println(mr.isTheTextMatchThisPattern());
		System.out.println(mr.findAllMatchesValueInTheText());
	}
	
}
