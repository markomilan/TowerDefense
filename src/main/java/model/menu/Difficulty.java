package model.menu;

public enum Difficulty {
	EASY (1),
	MEDIUM (2),
	HARD (3);
	
	private int diff;
	
	Difficulty(int diff){
		this.diff=diff;
	}

}