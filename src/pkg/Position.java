package pkg;

import java.io.Serializable;

public class Position implements Serializable{

	public int x;
	public int y;
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Position(Position p) {
		x = p.x;
		y = p.y;
	}

	public boolean equals(Position p) {
		if (p.x != x || p.y != y) {
			return false;
		}
		return true;
	}
	
	public int distance(Position p) {
		return (Math.abs(p.x-x)+Math.abs(p.y-y));
	}
	
	public Position difference(Position p) {
		return new Position(x-p.x, y-p.y);
	}
	
	public boolean equals(int x, int y) {
		if (this.x != x || this.y != y) {
			return false;
		}
		return true;
	}
	
	public void set(Position p) {
		x = p.x;
		y = p.y;
	}
	
	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public String print() {
		return new String(x+", "+y);
	}
}
