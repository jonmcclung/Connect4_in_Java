package pkg;

import java.util.ArrayList;

public class State{
	
	public int tilt = 0;
	Player activePlayer, otherPlayer;
	Player[] allPlayers = new Player[2];
	
	public State(Position p, Player activePlayer, Player otherPlayer) {
		
		this.activePlayer = new Player(activePlayer);
		this.activePlayer.marks.add(new Mark(p, this.activePlayer));
		this.otherPlayer = new Player(otherPlayer);
		allPlayers[0] = this.activePlayer;
		allPlayers[1] = this.otherPlayer;
		setTilt();
	}

	public State(Player activePlayer, Player otherPlayer) {
		
		this.activePlayer = new Player(activePlayer);
		this.otherPlayer = new Player(otherPlayer);
		allPlayers[0] = this.activePlayer;
		allPlayers[1] = this.otherPlayer;
		setTilt();
	}
	
	public int getTilt() {
		return tilt;
	}
	
	public void drawState() {
		String[][] map = new String[3][3];
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				map[x][y] = " ";
				for (Player player : allPlayers) {
					for (Mark m : player.marks) {
						if (m.p.equals(new Position(x, y))) {
							map[x][y] = new String(m.owner.letter);
						}
					}
				}
			}
			//System.out.println(map[0][y]+" "+map[1][y]+" "+map[2][y]);
		}
		//System.out.println("\n");
	}
	
	public void setTilt() {

		tilt = 0;
		for (int i = 0; i < 8; i++) {
			tilt += getLineTilt(i);
		}
	}
	
	public ArrayList<Position> getPossibleMoves() {
		ArrayList<Position> possibleMoves = new ArrayList<Position>();
		possibleMoves.clear();
		//System.out.println("tilt "+getTilt());
		if (Math.abs(getTilt()) != 1000) {
			for (int x = 0; x < 3; x++) {
				for (int y = 0; y < 3; y++) {
					Position p = new Position(x, y);
					possibleMoves.add(p);
					for (Player player : allPlayers) {
						for (Mark m : player.marks) {
							if (p.equals(m.p)) {
								possibleMoves.remove(p);
								//System.out.println(p.print()+" is already occupied.");

							}
						}
					}
				}	
			}
			/*System.out.println("possible moves: ");
			for (Position p : possibleMoves) {
				System.out.println(p.print());
			}*/
		}
		
		return possibleMoves;
	}
	
	public int getLineTilt(int id) {
		int lineTilt = 0;
		if (id < 8 && id > -1) {
			Position[] checkingPosition = new Position[3];
			Mark[] checking = new Mark[3];
			switch(id) {
			case 0:
			case 1:
			case 2: {
				for (int i = 0; i < 3; i++) {
					checkingPosition[i] = new Position(i, id);
				}
				break;
			}
			case 3:
			case 4:
			case 5: {
				for (int i = 0; i < 3; i++) {
					checkingPosition[i] = new Position(id-3, i);
				}
				break;
			}
			case 6: {
				for (int i = 0; i < 3; i++) {
					checkingPosition[i] = new Position(i, i);
				}
				break;
			}
			case 7: {
				for (int i = 0; i < 3; i++) {
					checkingPosition[i] = new Position(i, 2-i);
				}
				break;
			}
			}
			int markNum = 0;
			for (Player player : allPlayers) {
				for (Mark m : player.marks) {
					for (Position p : checkingPosition) {
						if (p.equals(m.p)) {
							checking[markNum] = m;
							markNum++;
						}
					}
				}
			}
			if (markNum == 3) {
				if (checking[0].owner == checking[1].owner && checking[1].owner == checking[2].owner) {
					if (checking[0].owner.type.equals(activePlayer.type)) {
						lineTilt += 1000;			
						//System.out.println(lineTilt+" "+id);
					}
					else if (checking[0].owner.type.equals(otherPlayer.type)) {
						lineTilt -= 1000;
						//System.out.println(lineTilt+" "+id);
					}
				}
			}
		}
		return lineTilt;
	}
}
