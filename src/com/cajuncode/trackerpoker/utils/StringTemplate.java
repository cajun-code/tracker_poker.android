package com.cajuncode.trackerpoker.utils;

import java.util.HashMap;
import java.util.Map;

public class StringTemplate {
	private Map<String, String> params;
	private StringBuffer template;
	public StringTemplate(CharSequence template){
		this.template = new StringBuffer(template);
		params = new HashMap<String, String>();
	}
	public void add(String key, String value){
		params.put(key, value);
	}
	public String render(){
		for(String key: params.keySet()){
			String tag_key = "<"+ key + ">";
			System.out.println("Finding Tag" + tag_key);
			int start = template.indexOf(tag_key);
			System.out.println("Starting index " + start);
			int end = start + tag_key.length();
			System.out.println("Ending index " + end);
			if(start > -1)
				template.replace(start, end, params.get(key));
		}
		return template.toString();
	}
	
}
