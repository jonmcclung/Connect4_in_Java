package connect4pkg;

import java.util.ArrayList;


public class State {

	public int tilt = -3;
	Player activePlayer, otherPlayer;
	Player[] allPlayers = new Player[2];
	Tile[][] board = new Tile[7][6];
	
	
	public State(Player activePlayer, Player otherPlayer) {
		for (int y = 0; y < 6; y++) {
			for (int x = 0; x < 7; x++) {
				board[x][y] = new Tile(x, y);
			}
		}
		
		this.activePlayer = activePlayer;
		this.otherPlayer = otherPlayer;
		allPlayers[0] = activePlayer;
		allPlayers[1] = otherPlayer;
		
	}
	
	public State(Tile[][] board, int x, Player activePlayer, Player otherPlayer) {
		this(board, activePlayer, otherPlayer);
		addToken(x);
	}

	public State(Tile[][] board, Player activePlayer, Player otherPlayer) {
		
		for (int y = 0; y < 6; y++) {
			for (int x = 0; x < 7; x++) {
				this.board[x][y] = new Tile(board[x][y]);
			}
		}

		this.activePlayer = activePlayer;
		this.otherPlayer = otherPlayer;
		allPlayers[0] = activePlayer;
		allPlayers[1] = otherPlayer;
		tilt = -3;
	}
	
	public void drawState() {
			/*for (int y = 5; y > -1; y--) {
				System.out.println(
			 board[0][y].getString()+" "+board[1][y].getString()+" "
			+board[2][y].getString()+" "+board[3][y].getString()+" "
			+board[4][y].getString()+" "+board[5][y].getString()+" "
			+board[6][y].getString());		
			}*/
			//System.out.println("tilt: "+tilt+" just moved: "+activePlayer.name);
	}

	public void addToken(int x) {
		for (int y = 0; y < 6; y++) {
			if (board[x][y].isEmpty()) {
				board[x][y].setOwner(activePlayer);
				break;
			}
		}
		setTilt();
	}
	
	public boolean isFull(int x) {
		return (board[x][5].owner != null);
	}
	
	public int getTilt() {
		//System.out.println("tilt: "+tilt);
		if (tilt != -3) {
			return tilt;			
		}
		else {
			setTilt();
			return tilt;
		}
	}
	
	public void setTilt() {

		tilt = 0;
		int counter = 0, resetCounter = 0, x = 0, y = 0;
		Tile last = null;
		boolean reset = true;
		int[][] diagonal = {{0, 0, 0, 1, 2, 3}, {2, 1, 0, 0, 0, 0}};
		for (int checkingType = 0; checkingType < 4; checkingType++) {
			reset = true;
			x = 0; y = 0; resetCounter = 0;
			iLoop:
			for (int i = 0; i < 42; i++) {
				if (!reset || checkingType > 1) {
					switch(checkingType) {
					case 0: {
						y++;
						if (y == 6) {
							x++;
							y = 0;
							reset = true;
						}
						break;
					}
					case 1: {
						x++;
						if (x == 7) {
							y++;
							x = 0;
							reset = true;
						}
						break;
					}
					case 2: {
						if (!reset) {
							x++;
							y++;							
						}
						if (y == 6 || x == 7 || reset) {
							if (resetCounter == 6) {
								break iLoop;
							}
							x = diagonal[0][resetCounter];
							y = diagonal[1][resetCounter];
							resetCounter++;
							reset = true;
						}
						break;
					}
					case 3: {
						if (!reset) {
							x++;
							y--;							
						}
						if (y == -1 || x == 7 || reset) {
							if (resetCounter == 6) {
								break iLoop;
							}
							x = diagonal[0][resetCounter];
							y = 5 - diagonal[1][resetCounter];
							resetCounter++;
							reset = true;
						}
						break;
					}
					}					
				}
				if (reset) {
					for (Tile[] T : board) {
						for (Tile t : T) {
							t.checking = false;
						}
					}
					last = board[x][y];
					counter = 0;
					reset = false;
					continue;
				}
				if (last.matches(board[x][y])) {
					counter++;
					last.checking = true; board[x][y].checking = true;
					//System.out.println(last.toString()+" matches "+board[x][y].toString());
					if (counter == 3) {
						tilt = ((board[x][y].owner == activePlayer) ? 1 : -1);
						return;
						}
					}
					else {
						counter = 0;
					}
					last = board[x][y];
				}
			}	
		}
	
	public void setGlowing() {
		setTilt();

		for (Tile[] T : board) {
			for (Tile t : T) {
				if (t.checking) {
					t.glowing = true;
				}
			}
		}
	}
		
		
		
	}
