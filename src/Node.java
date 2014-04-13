public class Node {
	private String name;
	private int id;
	private Sprite sprite;
	private static int amount = 0;
	public Node (String name, int x, int y){
		this.name = name;
		id = amount++;
		sprite = new Sprite(x,y);
	}
	public String toString(){
		return name;
	}
	public int getId(){
		return id;
	}
	public static void reset(){
		amount = 0;
	}
	public Sprite getSprite(){
		return sprite;
	}
}
