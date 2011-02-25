package com.tamil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TString {
	private List<TChar> tscChars = new ArrayList<TChar>();
	private static Set<Character> prefix = new HashSet<Character>();
	private static Set<Character> suffix = new HashSet<Character>();

	static {
		prefix.add('¦');
		prefix.add('§');
		prefix.add('¨');

		suffix.add('¡');
		suffix.add('¢');
		suffix.add('£');
		suffix.add('¤');
		suffix.add('¥');
		suffix.add('ª');
	}

	public TString(String tamilStr) {
		char tempChar = ' ';
		for (int i = 0; i < tamilStr.length(); i++) {
			char currentChar = tamilStr.charAt(i);
			if (prefix.contains(currentChar)) {
				if (tempChar != ' ')
					tscChars.add(new TChar(new String(new char[] { tempChar })));
				tempChar = currentChar;
			} else if (suffix.contains(currentChar)) {
				TChar tChar = new TChar(new String(new char[] { tempChar,
						currentChar }));
				tscChars.add(tChar);
				tempChar = ' ';
			} else {
				if (tempChar != ' ' && prefix.contains(tempChar)) {
					tscChars.add(new TChar(new String(new char[] { tempChar,
							currentChar })));
					tempChar = ' ';
				} else if (tempChar != ' ') {
					tscChars.add(new TChar(new String(new char[] { tempChar })));
					tempChar = currentChar;
				} else
					tempChar = currentChar;
			}
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
