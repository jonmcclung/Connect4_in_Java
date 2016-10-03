package connect4pkg;

import java.awt.Color;

public class Tile {
	
	public Player owner = null;
	public int x, y;
	public boolean checking, glowing;
	public Tile(Tile tile) {
		this(tile.owner, tile.x, tile.y);
	}
	
	public Tile(Player owner, int x, int y) {
		this(x, y);
		this.owner = owner;
	}
	
	public Tile(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public boolean matches(Tile t) {
		return (t.owner != null && owner != null && t.owner == owner);
	}
	
	public void setOwner(Player owner) {
		this.owner = owner;
	}
	
	public boolean isEmpty() {
		return (owner == null);
	}
	
	public Color getColor() {
		if (owner == null) {
			return Main.transparent;
		}
		return owner.color;
	}
	
	public String getString() {
		if (owner == null)
			return ".";
		switch(owner.name) {
		case "AI":
			return "A";
		case "You":
			return "P";		
		case "P1":
			return "1";		
		case "P2":
			return "2";
		}
		return "O";
	}
	
	@Override
	public String toString() {
		return ("("+x+", "+y+")");
	}

}
