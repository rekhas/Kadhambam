package com.tamil.kadhambam;

import com.tamil.TString;

public class TWord {
	TString word;
	String hint;
	
	public TWord(TString word, String hint) {
		this.word = word;
		this.hint = hint;
	}

	public TString getWord() {
		return word;
	}

	public String getHint() {
		return hint;
	}
	
}
