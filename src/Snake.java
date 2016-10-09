import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Snake {
	private SnakeNode head = null;
	private SnakeNode tail = null;
	private int NodeSize  = 1;
	
	private SnakeNode sn = new SnakeNode(20, 40, Direction.L);
	private Yard yard;
	public Snake(Yard yard) {
		head = sn;
		tail = sn;
		NodeSize = 1;
		this.yard = yard;
	}
	//添加到头节点之前
	public void addToHead() {
		SnakeNode sNode = null;
		switch(head.direction) {
		case L:sNode = new SnakeNode(head.row, head.col - 1, head.direction);break;
		case R:sNode = new SnakeNode(head.row, head.col + 1, head.direction);break;
		case U:sNode = new SnakeNode(head.row - 1, head.col, head.direction);break;
		case D:sNode = new SnakeNode(head.row + 1, head.col, head.direction);break;
		}
		sNode.next = head;
		sNode.prev = null;
		head.prev = sNode;
		head = sNode;
		NodeSize ++;
	}
	//添加到为节点之后
	public void addToTail() {
		SnakeNode sNode = null;
		switch(tail.direction) {
		case L:sNode = new SnakeNode(tail.row, tail.col + 1, tail.direction);break;
		case R:sNode = new SnakeNode(tail.row, tail.col - 1, tail.direction);break;
		case U:sNode = new SnakeNode(tail.row + 1, tail.col, tail.direction);break;
		case D:sNode = new SnakeNode(tail.row - 1, tail.col, tail.direction);break;
		}
		tail.next = sNode;
		sNode.prev = tail;
		tail = sNode;
		NodeSize ++;
	}
	
	public void draw(Graphics g) {
		move();
		SnakeNode node = head;
		while(node != null) {
			node.draw(g);
			node = node.next;
		}
		
	}
	
	private void move() {
		addToHead();
		deleteFromTail();
		checkHead();
	}
	
	private void checkHead() {
		if(head.row<0 || head.col<0 || head.row>Yard.ROWS || head.col>Yard.COLS) {
			yard.stopGame();
		}	
		SnakeNode node = head.next;
		while(node != null) {
			if(head.row==node.row && head.col==node.col) {
				yard.stopGame();
			}
			node = node.next;
		}
	}
	private void deleteFromTail() {
		if(NodeSize == 0) return ;
		tail = tail.prev;
		tail.next = null;
	}
	
	private class SnakeNode {
		int width = Yard.BLOCK_SIZE;
		int height = Yard.BLOCK_SIZE;
		int offX = Yard.OFFX;
		int offY = Yard.OFFY;
		int barSize = Yard.BARSIZE;
		int row, col;
		Direction direction;
		SnakeNode next = null;
		SnakeNode prev = null;
		//构造器
		SnakeNode(int row,int col, Direction dir) {
			this.row = row;
			this.col = col;
			this.direction = dir;
		}
		
		void draw(Graphics g) {
			Random random = new Random();
			int x = random.nextInt(256);
			int y = random.nextInt(256);
			int z = random.nextInt(256);
			Color c = g.getColor();
			//g.setColor(Color.BLACK);
			g.setColor(new Color(x, y, z));
			g.fillOval(offX + Yard.BLOCK_SIZE *col, offY + barSize + Yard.BLOCK_SIZE *row, width, height);
			g.setColor(c);
		}
	}


	public void keyPressed(KeyEvent ke) {
		int key = ke.getKeyCode();
		switch(key) {
		case KeyEvent.VK_LEFT :
			if(head.direction != Direction.R)
				head.direction = Direction.L;
			break;
		case KeyEvent.VK_RIGHT:
			if(head.direction != Direction.L)
				head.direction = Direction.R;
			break;
		case KeyEvent.VK_UP   :
			if(head.direction != Direction.D)
				head.direction = Direction.U;
			break;
		case KeyEvent.VK_DOWN :
			if(head.direction != Direction.U)
				head.direction = Direction.D;
			break;
		}
	}
	private Rectangle getRect() {
return new Rectangle(Yard.OFFX + Yard.BLOCK_SIZE * head.col, Yard.BARSIZE + Yard.OFFY + Yard.BLOCK_SIZE * head.row, head.width, head.height);
	}
	public void eat(Egg egg) {
		if(this.getRect().intersects(egg.getRect())) {
			egg.reAppear();
			this.addToTail();
			yard.setScore(yard.getScore()+5);
		}	
	}
}
