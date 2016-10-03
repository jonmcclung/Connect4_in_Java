package connect4pkg;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends Canvas implements Runnable{
	
	public static Font font;
	public static Color light = new Color(0xebebe1), lighter = new Color(0xeb, 0xeb, 0xe1, 0x80), 
			dark = new Color(0xa4, 0x99, 0x9a, 0x80), darker = Color.decode("0xa4999a"), darkest = Color.decode("0x585252"),
			transparent = new Color(0,0,0,0);
	public static boolean running;
	public static final int FPS = 60, width = 700, height = 600;
	public static int time = 0, spriteWidth = 96, frames = 0;
	public static JFrame frame;
	public static JPanel panel;
	public static String title = "Connect 4";
	public static Player player;
	public static Player enemy;
	public static Game game;
	public static BufferedImage spriteSheet = null, bg = null;

	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static final int xCenter = (int)screenSize.getWidth()/2;
	static final int yCenter = (int)screenSize.getHeight()/2;
	
	public void updateTime() {
		time++;
	}
	
	public synchronized void start() {
		new Thread(this).start();
		running = true;
	}
	
	public synchronized void stop() {
		running = false;
	}
	
	

	Main() {
		/*try {
			spriteSheet = ImageIO.read(this.getClass().getResourceAsStream("connect4pkg.res/spriteSheet.png"));
		} catch(IOException e) {
			e.printStackTrace();
			System.out.println("failed to load spriteSheet");
		}*/
		try {
			bg = ImageIO.read(this.getClass().getResourceAsStream("res/menubg.png"));
		} catch (IOException e) {
			System.out.println("failed to load bg");
			e.printStackTrace();
		}
		/*try {
			font = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream("connect4pkg.res/Lakmus.ttf"));
		     GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream("connect4pkg.res/Lakmus.ttf")));
		} catch (IOException|FontFormatException e) {
		     System.out.println("failure to load font");
		}*/
		
		enemy = new Player("AI", "ai", Color.cyan);
		player = new Player("You", "human", Color.red);
		
		Game game = new connect4pkg.Game();
		
		frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		game.setPreferredSize(new Dimension(width, height));
	//	game.setBackground(dark);
		frame.add(game);
		
		frame.setLocation(xCenter-(width/2), yCenter-(height/2)-24);
		frame.pack();
		frame.setVisible(true);
		game.addComponents();
	}
	
	public static void main(String[] args) {
		
		new Main().start();
	}
	
	@Override
	public void run() {
		long lastUpdate = System.nanoTime();
		double nsPerFrame = (1d/FPS)*1000000000;
		double delta = 0.0;
		
		while (running) {

			long now = System.nanoTime();
			delta += (now-lastUpdate)/nsPerFrame;
			lastUpdate = now;
			
			boolean update = false;
			
			if (delta >= 1) {
				delta -= 1;
				frames++;
				update = true;
			}
			
			if (frames >= FPS) {
				updateTime();
				frames -= FPS;
			}
			
			if (update) {
				//game.render();
			}			
		}
	}
}
