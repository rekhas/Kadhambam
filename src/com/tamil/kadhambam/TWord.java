package com.tamil.kadhambam;

import java.io.Serializable;

import com.tamil.TString;

public class TWord implements Serializable {
	TString word;
	String hint;
	
	public TWord() {
		
	}

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
