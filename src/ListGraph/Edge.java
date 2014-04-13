package ListGraph;

public class Edge<T> {
	private T dest;
	private Integer weight;
	public Edge(T dest, Integer weight){
		this.dest = dest;
		this.weight = weight;
	}
	public String toString(){
		return "goes to "+dest;
	}
	public T getDest(){
		return dest;
	}
	public Integer getWeight(){
		return weight;
	}
}
