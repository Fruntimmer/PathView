package ListGraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GraphMethods {
	public static <T> boolean pathExists(T from, T to, Graph<T> g){
		Set<T> closed = new HashSet<T>();
		depthFirst(from, closed, g);
		return closed.contains(to);
	}
	public static <T> void depthFirst(T from, Set<T> closed, Graph<T> g){
		closed.add(from);
		for(Edge e : g.getEdges(from)){
			if(!closed.contains(e.getDest()))
				depthFirst((T)e.getDest(), closed, g);
		}
	}
	public static <T> ArrayList<T> dijsktraFind(T from, T to, Graph<T> g){
		if(!pathExists(from, to, g))
			return new ArrayList<T>();
		HashMap<T, Boolean> visited = new HashMap<>();	
		HashMap<T, Integer> cost = new HashMap<>();
		HashMap<T, T> prevNode = new HashMap<>();	
		//Initiate
		for(T n : g.getNodes()){
			visited.put(n, false);
			prevNode.put(n, null);
			if(n == from)
				cost.put(n, 0);
			else
				cost.put(n, Integer.MAX_VALUE);
		}
		T activeNode = from;
		dijkstraStep(activeNode, cost, visited, prevNode, to, g);
		return extractPath(prevNode, to);
	}
	private static <T>void dijkstraStep(T activeNode, HashMap<T, Integer> cost, HashMap<T, Boolean> visited, HashMap<T, T> prevNode, T to, Graph<T> g) {
		visited.put(activeNode, true);
		for(Edge e : g.getEdges(activeNode)){
			if(cost.get(activeNode) +e.getWeight() < cost.get(e.getDest())){
				//If it's cheaper we set the new price and where we came from
				cost.put((T) e.getDest(), cost.get(activeNode) +e.getWeight());
				prevNode.put((T) e.getDest(), activeNode);
			}
		}
		T lowestNode = lowestNode(cost, visited);
		if(visited.get(to)){
			return;
		}
		else{
			dijkstraStep(lowestNode, cost, visited, prevNode, to, g);
		}
	}
	private static <T>T lowestNode(HashMap<T, Integer> cost, HashMap<T, Boolean> visited){
		T lowestNode = null;
		Integer lowestCost = Integer.MAX_VALUE;
		for(Map.Entry<T, Integer> line : cost.entrySet()){
			if(visited.get(line.getKey()) == false && line.getValue() < lowestCost){
				lowestNode = line.getKey();
				lowestCost = line.getValue();
			}
		}
		return lowestNode;
	}
	public static <T> ArrayList<T> extractPath(HashMap<T, T> prevNode, T stepNode){
		ArrayList<T> path = new ArrayList<>();
		while(stepNode != null){
			path.add(stepNode);
			stepNode = prevNode.get(stepNode);
		}
		Collections.reverse(path);
		return path;
	}
}
