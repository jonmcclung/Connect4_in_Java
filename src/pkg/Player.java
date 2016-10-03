package pkg;

import java.awt.Color;
import java.util.ArrayList;

public class Player {

	public ArrayList<Mark> marks = new ArrayList<Mark>();
	public String type;
	public String letter;
	public Color color;
	
	public Player(Player p) {
		marks.clear();
		this.type = new String(p.type);
		this.letter = p.letter;
		this.color = p.color;
		for (Mark m : p.marks) {
			marks.add(new Mark(m));
			marks.get(marks.size()-1).owner = this;
		}
	}
	
	public Player(String type, String col) {
		this.type = type;
		if (col.equals("blue")) {
			letter = "x";
			color = Color.cyan;
		}
		else {
			letter = "o";
			color = Color.decode("0xff80ff");
		}
	}
}
