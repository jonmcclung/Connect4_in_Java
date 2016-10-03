package pkg;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main extends Canvas implements Runnable{
	
	public static Font font;
	public static Color light = new Color(0xebebe1), lighter = new Color(0xeb, 0xeb, 0xe1, 0x80), 
			dark = new Color(0xa4, 0x99, 0x9a, 0x80), darker = Color.decode("0xa4999a"), darkest = Color.decode("0x585252"),
			transparent = new Color(0,0,0,0);
	public static boolean running;
	public static final int FPS = 60;
	public static int time = 0, frames = 0;
	public static JFrame frame;
	public static String title = "Tic Tac Toe";
	public static BufferedImage bg = null;
	public static Game game;

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

		try {
			bg = ImageIO.read(this.getClass().getResourceAsStream("connect4pkg/res/bg.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream("connect4pkg/res/Xiomara-Script.ttf"));
		     GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream("connect4pkg/res/Xiomara-Script.ttf")));
		} catch (IOException|FontFormatException e) {
		     System.out.println("failure");
		}


		frame = new JFrame(title);
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		game = new Game();
		frame.add(game, BorderLayout.CENTER);
		frame.setLocation(xCenter-(game.width/2), yCenter-(game.height/2)-24);
		frame.pack();
		frame.setVisible(true);
		game.addComponents();
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new Main().start();
	}
	
	@Override
	public void run() {
		long lastUpdate = System.nanoTime();
		double nsPerFrame = (1000000000/FPS);
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
				game.render();
			}			
		}
	}	
}
