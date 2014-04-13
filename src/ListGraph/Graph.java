package ListGraph;

import java.util.ArrayList;

public abstract class Graph<T> {
	public abstract void add(T n);
	public abstract void connect(T from, T to, Integer weight);	
	public abstract ArrayList<T> getNodes();
	public abstract ArrayList<Edge<T>> getEdges(T n);
	public abstract Edge<T> getEdgeBetween(T from, T to);
	public abstract void disconnect(T from, T to);
	public abstract void clear();
}
