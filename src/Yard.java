import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
//如果我继承JFrame，闪烁会很严重，但是继承Frame就不会
public class Yard extends Frame {
	PaintThread paintThread = new PaintThread();
	Image offScreenImage = null;
	Snake snake = new Snake(this);
	Egg egg = new Egg();
	
	private boolean overGame = false;
	private int score  = 0;
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}

	static final int ROWS = 30;
	static final int COLS = 40;
	static final int OFFX = 10;
	static final int OFFY = 10;
	static final int BARSIZE = 25;
	static final int BLOCK_SIZE = 15;
	
	public void launchFrame() {
		this.setTitle("贪吃蛇");
		this.setLocation(10, 10);
this.setSize(2*OFFX + COLS*BLOCK_SIZE, 2*OFFY + BARSIZE + ROWS*BLOCK_SIZE);
		this.setVisible(true);
		this.setResizable(false);
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
		//添加键盘监听器
		this.addKeyListener(new KeyMonitor());
		new Thread(paintThread).start();
	}
	//双缓冲
	/**/
	public void update(Graphics g) {
		if(offScreenImage == null) {
offScreenImage = this.createImage(2*OFFX + COLS*BLOCK_SIZE,
		2*OFFY + BARSIZE + ROWS*BLOCK_SIZE);
		}
		Graphics goff = offScreenImage.getGraphics();
		paint(goff);
		g.drawImage(offScreenImage, 0, 0, this);
	}
	
	public void paint(Graphics g) {
		Color co = g.getColor();
		g.setColor(Color.GRAY);
g.fillRect(0, 0, 2*OFFX+COLS*BLOCK_SIZE, 2*OFFY+BARSIZE+ROWS*BLOCK_SIZE);
		/*
		g.setColor(Color.DARK_GRAY);
		for(int i=0 ; i<=COLS ; ++i){
g.drawLine(OFFX + i*BLOCK_SIZE, BARSIZE + OFFX,
		   OFFX + i*BLOCK_SIZE, BARSIZE + OFFX + ROWS*BLOCK_SIZE);
		}
		for(int i=0 ; i<=ROWS ; ++i){
g.drawLine(OFFY,                   BARSIZE + OFFY + i*BLOCK_SIZE, 
		   OFFY + COLS*BLOCK_SIZE, BARSIZE + OFFY + i*BLOCK_SIZE);
		}
		*/
		g.setColor(Color.YELLOW);
		g.drawString("score:"+score, 10, 60);
		if(overGame) {
			g.setFont(new Font("Consolas",Font.BOLD, 40));
			g.drawString("Game Over!", 230, 280);
			paintThread.pause();
		}
		g.setColor(co);
		
		egg.draw(g);
		snake.draw(g);
		snake.eat(egg);	
	}
	public void stopGame() {
		overGame = true;
	}
	//Thread
	private class PaintThread implements Runnable {
		private boolean running = true;
		public void run() {
			while(running) {
				repaint();
				try {
					Thread.sleep(180);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		public void pause() {
			running = false;
		}
	}
	
	class KeyMonitor extends KeyAdapter {
		public void keyPressed(KeyEvent ke) {
			snake.keyPressed(ke);
		}
	}
	
	public static void main(String[] args) {
		new Yard().launchFrame();
	}
}
