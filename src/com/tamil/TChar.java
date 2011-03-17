package com.tamil;

public class TChar {
	private String tscChar;
	
	public TChar(String tamilChar){
		tscChar = tamilChar;
	}
	
	public String getChar(){
		return tscChar;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tscChar == null) ? 0 : tscChar.hashCode());
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
		TChar other = (TChar) obj;
		if (tscChar == null) {
			if (other.tscChar != null)
				return false;
		} else if (!tscChar.equals(other.tscChar))
			return false;
		return true;
	}
	
	
}
