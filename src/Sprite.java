

public class Sprite {
	private int x;
	private int y;
	private static int height = 16;
	private static int width = 16;
	public Sprite(int x, int y){
		this.x = x;
		this.y = y;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public static int getHeight(){
		return height;
	}
	public static int getWidth(){
		return width;
	}
}
