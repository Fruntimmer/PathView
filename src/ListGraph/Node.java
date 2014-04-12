package ListGraph;

public class Node {
	private String name;
	private int id;
	private static int amount = 0;
	public Node (String name){
		this.name = name;
		id = amount++;
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
}
