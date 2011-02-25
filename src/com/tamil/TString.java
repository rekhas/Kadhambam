package com.tamil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TString {
	private List<TChar> tscChars = new ArrayList<TChar>();

	public TString(String tamilStr) {
		String expr = "[¦§¨].[£¡¢¤¥ª]|[¦§¨].|.[£¡¢¤¥ª]|.";
		Pattern pattern = Pattern.compile(expr);
		Matcher matcher = pattern.matcher(tamilStr);
		while(matcher.find()){
			tscChars.add(new TChar(matcher.group()));
		}
	}

	public List<TChar> getChars() {
		return tscChars;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((tscChars == null) ? 0 : tscChars.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TString other = (TString) obj;
		if (tscChars == null) {
			if (other.tscChars != null)
				return false;
		} else if (!tscChars.equals(other.tscChars))
			return false;
		return true;
	}

}
