package ListGraph;

public class Edge {
	private Node dest;
	private Integer weight;
	public Edge(Node dest, Integer weight){
		this.dest = dest;
		this.weight = weight;
	}
	public String toString(){
		return "goes to "+dest;
	}
	public Node getDest(){
		return dest;
	}
	public Integer getWeight(){
		return weight;
	}
}
