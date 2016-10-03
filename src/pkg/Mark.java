package pkg;

public class Mark {

	Position p;
	Player owner;
	Mark(Mark m) {
		this.p = new Position(m.p);
		this.owner = m.owner;
	}
	
	Mark(Position p, Player owner) {
		this.p = new Position(p);
		this.owner = owner;
	}
}
