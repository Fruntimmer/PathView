package ListGraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ListGraph {
	private HashMap<Node, List<Edge>> data;
	public ListGraph(){
		 data = new HashMap<Node, List<Edge>>();
	}
	public void add(Node n){
		if(!data.keySet().contains(n))
			data.put(n, new ArrayList<Edge>());
		else{
			//Error here
		}
	}
	public void connect(Node from, Node to, Integer weight){
		List<Edge> fromList = data.get(from);
		List<Edge> toList = data.get(to);
		if(fromList != null && toList != null){
			fromList.add(new Edge(to, weight));
			toList.add(new Edge(from, weight));
		}
		else{
			//Error here
		}
	}
	public void depthFirst(Node from, Set<Node> closed){
		closed.add(from);
		for(Edge e : data.get(from)){
			if(!closed.contains(e.getDest()))
				depthFirst(e.getDest(), closed);
		}
	}
	public boolean pathExists(Node from, Node to){
		Set<Node> closed = new HashSet<Node>();
		depthFirst(from, closed);
		return closed.contains(to);
	}
	public ArrayList<Node> dijsktraFind(Node from, Node to){
		if(!pathExists(from, to))
			return new ArrayList<Node>();
		HashMap<Node, Boolean> visited = new HashMap<>();	
		HashMap<Node, Integer> cost = new HashMap<>();
		HashMap<Node, Node> prevNode = new HashMap<>();	
		//Initiate
		for(Node n : getNodes()){
			visited.put(n, false);
			prevNode.put(n, null);
			if(n == from)
				cost.put(n, 0);
			else
				cost.put(n, Integer.MAX_VALUE);
		}
		Node activeNode = from;
		dijkstraStep(activeNode, cost, visited, prevNode, to);
		System.out.println(cost.get(to));
		return extractPath(prevNode, to);
	}
	private void dijkstraStep(Node activeNode, HashMap<Node, Integer> cost, HashMap<Node, Boolean> visited, HashMap<Node, Node> prevNode, Node to) {
		visited.put(activeNode, true);
		for(Edge e : data.get(activeNode)){
			if(cost.get(activeNode) +e.getWeight() < cost.get(e.getDest())){
				//If it's cheaper we set the new price and where we came from
				cost.put(e.getDest(), cost.get(activeNode) +e.getWeight());
				prevNode.put(e.getDest(), activeNode);
			}
		}
		Node lowestNode = lowestNode(cost, visited);
		if(visited.get(to)){
			return;
		}
		else{
			dijkstraStep(lowestNode, cost, visited, prevNode, to);
		}
	}
	private Node lowestNode(HashMap<Node, Integer> cost, HashMap<Node, Boolean> visited){
		Node lowestNode = null;
		Integer lowestCost = Integer.MAX_VALUE;
		for(Map.Entry<Node, Integer> line : cost.entrySet()){
			if(visited.get(line.getKey()) == false && line.getValue() < lowestCost){
				lowestNode = line.getKey();
				lowestCost = line.getValue();
			}
		}
		return lowestNode;
	}
	private ArrayList<Node> extractPath(HashMap<Node, Node> prevNode, Node stepNode){
		ArrayList<Node> path = new ArrayList<>();
		while(stepNode != null){
			path.add(stepNode);
			stepNode = prevNode.get(stepNode);
		}
		Collections.reverse(path);
		return path;
	}
	public ArrayList<Node> getNodes(){
		return new ArrayList<Node>(data.keySet());
	}
	public ArrayList<Edge> getEdges(Node n){
		return new ArrayList<Edge>(data.get(n));
	}
	public void clear(){
		data = new HashMap<Node, List<Edge>>();
	}
}
