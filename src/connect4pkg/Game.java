package connect4pkg;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import net.miginfocom.swing.MigLayout;
//System.out;


public class Game extends JPanel{
	
	Player[] allPlayers = new Player[2];
	Player activePlayer, otherPlayer;
	int turn = 0, defaultOption = 0;
	public Column[] columns = new Column[7];
	public State state;
	public static int height = 640, width = 640, intelligence = 5;
	Random rand = new Random();
	public boolean painting = false;
	
	Game() {
		setPreferredSize(new Dimension(width, height));	
		setVisible(true);
	}
	
	public void addComponents() {
		setLayout(new MigLayout("gap 0, ins 0", "", ""));
			for (int x = 0; x < 7; x++) {
				columns[x] = new Column(this, x);
				add(columns[x]);
			}
		revalidate();
		reset();
	}
	
	public void reset() {
		turn = 0;
		
		String[] options = {"Friend", "Computer"};
		String message = "Who would you like to play against?";
		int response = JOptionPane.showOptionDialog(connect4pkg.Main.frame, message, "New Game Setup", JOptionPane.YES_NO_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, options[defaultOption]);	
		defaultOption = response;
		if (response == 1 && allPlayers[0] != null && allPlayers[0].type.equals("ai")) {
			allPlayers[1] = new Player("You", "human", Color.red);
			allPlayers[0] = new Player("AI", "ai", Color.cyan);				
		}
		else if (response == 1) {
			allPlayers[0] = new Player("You", "human", Color.red);
			allPlayers[1] = new Player("AI", "ai", Color.cyan);		
		}
		else if (response == 0) {
			allPlayers[0] = new Player("P1", "human", Color.red);
			allPlayers[1] = new Player("P2", "human", Color.cyan);				
		}
		else if (response == JOptionPane.CLOSED_OPTION) {
			System.exit(0);
		}
		for (Component comp : this.getComponents()) {
			((Column)comp).setFull(false);
		}
		activePlayer = allPlayers[0];
		otherPlayer = allPlayers[1];
		state = new State(activePlayer, otherPlayer);
		if (activePlayer.type.equals("ai")) {
			takeAITurn(intelligence);
		}
	}
	
	public void toggleActivePlayer() {
		state.otherPlayer = state.allPlayers[turn%2];
		turn++;
		state.activePlayer = state.allPlayers[turn%2];
		if (state.activePlayer.type.equals("ai")) {
			takeAITurn(intelligence);
		}
	}
	
	public int minimax(State state, int depth, int alpha, int beta, boolean foundWin, ArrayList<Integer> moves) {
		
		int result = state.getTilt();
		State losingState = null;
		
		if (depth == 0 || result != 0) {
			return result*(depth+1);
		}		
		
		for (int x = 0; x < 7; x++) {
			if (!state.isFull(x)) {
				State checking = new State(state.board, x, state.otherPlayer, state.activePlayer);
				moves.add(x);
				result = minimax(checking, depth - 1, beta*-1, alpha*-1, foundWin, moves);
				moves.remove(moves.size()-1);
				if (checking.getTilt() == 1) {
					foundWin = true;
				}
				if (result < 0) {
					losingState = checking;
					losingState.drawState();
					//System.out.println("turn: "+turn+" yields result "+result+" after "+movesToString(moves)+x);
				}
				if (result > beta) {
					return result*-1;
				}
				else if (result > alpha) {
					if (losingState != null) {
						checking.drawState();
						//System.out.println("turn: "+turn+" blocks with "+movesToString(moves)+x);
						losingState.drawState();		
						losingState = checking;
						//System.out.println("turn: "+turn+" with "+movesToString(moves)+" giving alpha "+result);
					}
					alpha = result;
				}
			}
		}
		return alpha*-1;
	}
	
	public String movesToString(ArrayList<Integer> moves) {
		String output = "";
		for (Integer i : moves) {
			//System.output = new String(//System.output+i.toString()+", ");
		}
		return output;
	}
	
	public void takeAITurn(int depth) {
		int choice = -1;
		if (turn > 1) {		
			int bestValue = -(depth+2);
			ArrayList<Integer> moves = new ArrayList<Integer>();
			ArrayList<Integer> bestMoves = new ArrayList<Integer>();	
			for (int x = 0; x < 7; x++) {
			if (!state.isFull(x)) {
				moves.add(x);
				int result = minimax(new State(state.board, x, state.activePlayer, state.otherPlayer), depth, -1*(depth+2), depth+2, false, moves);
				moves.remove(0);
				//System.out.println("turn: "+turn+" x: "+x+", result: "+result);
				if (result == bestValue) {
					bestMoves.add(x);
				}
				if (result > bestValue) {
					bestValue = result;
					bestMoves.clear();
					bestMoves.add(x);
					choice = x;
				}
			}
		}	
			if (bestMoves.size() > 1) {
				//System.out.println("turn: "+turn+" choosing between "+movesToString(bestMoves));
				int r = rand.nextInt(bestMoves.size());
				choice = bestMoves.get(r);	
			}
			//System.out.println("turn: "+turn+" chose "+choice+" with value "+bestValue);
		}
		else {
			choice = rand.nextInt(7);
		}
			if (choice != -1) {
				state.addToken(choice);
				if (state.isFull(choice)) {
				//	System.//System.out.println("making "+choice+" full");
					columns[choice].setFull(true);
				}
				checkWin();
			}
			else {
				//System.out.println("error: no choice made");
			}		
		}
	
	public void checkWin() {
		repaint();
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	if (Math.abs(state.getTilt()) == 1) {
					state.setGlowing();
					String message = "";
					if (state.activePlayer.type.equals("human")) {
						message = "You won!";
					}
					else {
						message = "You lost!";
					}
					JOptionPane.showMessageDialog(connect4pkg.Main.frame, message);
					reset();
				}
				else if (turn < 42 - 1) {
					toggleActivePlayer();
				}
				else {
					JOptionPane.showMessageDialog(connect4pkg.Main.frame, "Cat!");
					reset();
				}
		    }
		});
	}
	
	public void doAction(int x) {
		state.addToken(x);
		if (state.isFull(x)) {
			columns[x].setFull(true);
		}
		painting = true;
		checkWin();
	}
	
	public void render() {
		repaint();
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage(connect4pkg.Main.bg, 0, 0, null);
		if (state != null) {
			for (int x = 0; x < 7; x++) {
				for (int y = 0; y < 6; y++) {
					g.setColor(state.board[x][y].getColor());
					g.fillRect(x*100, (5-y)*100, 100, 100);
					if (state.board[x][y].glowing) {
						g.setColor(new Color(0x80ffffff, true));
						g.fillRect(x*100, (5-y)*100, 100, 100);
					}
				}
			}			
		}
	}
}

