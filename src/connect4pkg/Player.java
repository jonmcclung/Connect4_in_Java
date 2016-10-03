package connect4pkg;

import java.awt.Color;
import java.util.ArrayList;

public class Player {
	
	public String type, name;
	public Color color;
	
	Player(String name, String type, Color color) {
		this.name = name;
		this.type = type;
		this.color = color;
	}
	
	Player(Player player) {
		this(player.name, player.type, player.color);
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
