package pkg;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

public class Tile extends JComponent implements MouseListener{
	Position p;
	Game parent;
	boolean full = false;
	
	public Tile(Tile t) {
		this.p = new Position(t.p);
		this.parent = t.parent;
		this.full = t.full;
	}
	
	public Tile(Game parent, Position p) {
		setPreferredSize(new Dimension(216, 216));
		this.parent = parent;
		this.p = p;
		addMouseListener(this);
	}
	
	public void setSelected(boolean isSelected) {
		if (isSelected) {
			setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		else {
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		setSelected(true);
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		setSelected(false);
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		if (!full && parent.activePlayer.type.equals("human")) {
			full = true;
			parent.doAction(this);
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	

}
