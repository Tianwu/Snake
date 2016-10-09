import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Egg {
	int row, col;
	int width = Yard.BLOCK_SIZE;
	int height = Yard.BLOCK_SIZE;
	private static Random r = new Random();
	public int getRow() {
		return row;
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public int getCol() {
		return col;
	}
	
	public void setCol(int col) {
		this.col = col;
	}
	public Egg(int row, int col) {
		this.row = row;
		this.col = col;
	}
	public Egg() {
		//这里的this相当于里一个构造器
		this(r.nextInt(Yard.ROWS),r.nextInt(Yard.COLS));
	}
	public void reAppear() {
		this.row = r.nextInt(Yard.ROWS);
		this.col = r.nextInt(Yard.COLS);
	}
	public void draw(Graphics g) {
		Random random = new Random();
		int x = random.nextInt(256);
		int y = random.nextInt(256);
		int z = random.nextInt(256);
		Color c = g.getColor();
		//g.setColor(Color.RED);
		g.setColor(new Color(x, y, z));
g.fillOval(Yard.OFFX + Yard.BLOCK_SIZE * col,Yard.BARSIZE + Yard.OFFY + Yard.BLOCK_SIZE * row,width, height);
		g.setColor(c);
		//if(c == Color.RED) c = Color.BLUE;
		//else c = Color.RED;
	}

	public Rectangle getRect() {
return new Rectangle(Yard.OFFX + Yard.BLOCK_SIZE * col,Yard.BARSIZE + Yard.OFFY + Yard.BLOCK_SIZE * row, width, height);
	}	
}
