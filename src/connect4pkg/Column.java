package connect4pkg;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

import pkg.Position;

public class Column extends JComponent implements MouseListener{
	int x;
	Game parent;
	boolean full = false;
	
	public Column(Column c) {
		this(c.parent, c.x);
		this.full = c.full;
	}
	
	public Column(Game parent, int x) {
		setPreferredSize(new Dimension(100, 600));
		setBackground(Color.red);
		this.parent = parent;
		this.x = x;
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
	
	public void setFull(boolean full) {
		this.full = full;
	}
	
	public boolean isFull() {
		return full;
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
			parent.doAction(this.x);
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	

}
