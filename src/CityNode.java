

import ListGraph.Node;
import ListGraph.Sprite;

public class CityNode extends Node {
	private Sprite sprite;
	public CityNode(String name, int x, int y){
		super(name);
		sprite = new Sprite(x,y);
	}
	public Sprite getSprite(){
		return sprite;
	}
}
