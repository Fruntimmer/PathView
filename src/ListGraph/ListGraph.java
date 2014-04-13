package ListGraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ListGraph<T> extends Graph<T> {
	private HashMap<T, List<Edge<T>>> data;
	public ListGraph(){
		 data = new HashMap<T, List<Edge<T>>>();
	}
	public void add(T n){
		if(!data.keySet().contains(n))
			data.put(n, new ArrayList<Edge<T>>());
		else{
			//Error here
		}
	}
	public void connect(T from, T to, Integer weight){
		List<Edge<T>> fromList = data.get(from);
		List<Edge<T>> toList = data.get(to);
		if(fromList != null && toList != null){
			fromList.add(new Edge<T>(to, weight));
			toList.add(new Edge<T>(from, weight));
		}
		else{
			//Error here
		}
	}
	public ArrayList<T> getNodes(){
		return new ArrayList<T>(data.keySet());
	}
	public ArrayList<Edge<T>> getEdges(T n){
		return new ArrayList<Edge<T>>(data.get(n));
	}
	public void clear(){
		data = new HashMap<T, List<Edge<T>>>();
	}
	public void disconnect(T from, T to) {
		data.get(from).remove(getEdgeBetween(from, to));
		data.get(to).remove(getEdgeBetween(to, from));
	}
	public Edge<T> getEdgeBetween(T from, T to){
		for(Edge<T> e: getEdges(from)){
			if(e.getDest() == to)
				return e;
		}
		return null;
	}
}
