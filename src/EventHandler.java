import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ListGraph.Edge;
import ListGraph.Graph;
import ListGraph.GraphMethods;
import ListGraph.ListGraph;

public class EventHandler {
	private ArrayList<Node> selection = new ArrayList<>();
	private ArrayList<Node> foundPath = new ArrayList<>();
	private Graph<Node> graph;
	private boolean paintActive = false;
	public EventHandler(){
		graph = new ListGraph<Node>();
	}
	public void clickRegistered(MouseEvent e){
		Node n = spriteDetection(e.getX(), e.getY());
		if(n != null){
			select(n);
		}
		else if(paintActive){
			createNode(e.getX(), e.getY());
			paintActive = false;
		}
		else{
			clearSelection();
		}
		//Assuming that user doesnt want to see previously found paths anymore
		foundPath.clear();
	}
	public void createNode(int x, int y){
		CityGUI gui = new CityGUI();
		int choice = JOptionPane.showConfirmDialog(null, gui, "Create City", JOptionPane.OK_CANCEL_OPTION);
		if(choice == JOptionPane.OK_OPTION){			
			graph.add(new Node(gui.getName(),x, y));
		}
		clearSelection();
	}
	public void connectNodes(){		
		if(validSelection()){
			ConnectGUI gui = new ConnectGUI();
			int choice = JOptionPane.showConfirmDialog(null, gui, "Create Connection", JOptionPane.OK_CANCEL_OPTION);
			if(choice == JOptionPane.OK_OPTION){
				graph.connect(selection.get(0), selection.get(1), Integer.parseInt(gui.getTime()));
				clearSelection();
			}
		}		
	}
	public void disconnect(){
		if(validSelection()){
			graph.disconnect(selection.get(0), selection.get(1));
			clearSelection();
		}
	}
	public void validPath(){
		foundPath = GraphMethods.dijsktraFind(selection.get(0), selection.get(1), graph);
		clearSelection();
	}
	public boolean isInPath(Node n){
		return foundPath.contains(n);
	}
	public ArrayList<Node> getNodes(){
		return graph.getNodes();
	}
	public ArrayList<Edge<Node>> getConnections(Node n){
		return graph.getEdges(n);
	}
	public void select(Node n){
		if(selection.contains(n)){
			selection.remove(n);
			return;
		}
		else if(selection.size() > 1){
			selection.remove(0);
		}
		selection.add(n);
	}
	public void clearSelection(){
		selection.clear();
	}
	public boolean isSelected(Node n){
		return selection.contains(n);
	}
	public boolean validSelection(){
		if(selection.size()==2){
			//more stuff here later?
			return true;
		}
		else{
			return false;
		}
	}
	public boolean isPaintActive(){
		return paintActive;
	}
	public void setPaintActive(boolean b){
		paintActive = b;
	}
	public Node spriteDetection(int x, int y){
		int w = Sprite.getWidth();
		int h = Sprite.getHeight();
		//Adding a pixel bias so you can click a little outside but it will still register as a hit
		int bias = 6;
		for (Node n : graph.getNodes()){
			Sprite s = n.getSprite();
			if((x > s.getX()-(w/2+bias) && x < s.getX()+(w/2+bias)) && y>s.getY()-(h/2 +bias) && y<s.getY()+(h/2+bias)){
				return n;
			}
		}
		return null;
	}
	private Node findNodeById(int id){
		for(Node n : graph.getNodes()){
			if(n.getId() == id)
				return n;
		}
		return null;
	}
	public void save(String savePath, String imgPath) throws FileNotFoundException{
		PrintWriter pw = new PrintWriter(new FileOutputStream(savePath));
		ArrayList<Node> allNodes = graph.getNodes();
		Collections.sort(allNodes, new Comparator<Node>(){
			public int compare(Node n1, Node n2){
				return n1.getId()-n2.getId();
			}
		});
		for(Node n : allNodes){
			Sprite s = n.getSprite();
			pw.write(n +" "+s.getX()+ " "+s.getY()+"\n");
		}
		pw.write("|-edges-| \n");
		for(Node n : allNodes){	
			for(Edge<Node> e : graph.getEdges(n)){
				pw.write(n.getId() +" " + ((Node)e.getDest()).getId() + " " +e.getWeight() + " ");
				pw.write("\n");
			}
		}
		pw.write("|-Image-| \n");
		if(imgPath != null)
			pw.write(imgPath);
		else
			pw.write("void");
		pw.close();
	}
	public String load(String loadPath) throws IOException{
		graph.clear();
		clearSelection();
		Node.reset();
		Scanner in = new Scanner(new FileReader(loadPath));
		boolean readNode = true;
		while(in.hasNextLine() && readNode){
			String next = in.next();
			if(next.equals("|-edges-|")){
				readNode = false;
				System.out.println(in.nextLine());
			}
			else{
				graph.add(new Node(next,in.nextInt(),in.nextInt()));
			}
		}
		while(in.hasNextLine()){
			String next = in.next();
			if(next.equals("|-Image-|")){
				in.nextLine();
				break;
			}
			graph.connect(findNodeById(Integer.parseInt(next)), findNodeById(in.nextInt()), in.nextInt());
			in.nextLine();
		}
		String path = in.nextLine();
		in.close();
		if(path.equals("void"))
			return null;
		return path;
	}
	private class CityGUI extends JPanel{
		private JTextField name;
		private JTextField time;
		public CityGUI(){
			setLayout(new FlowLayout());
			name = new JTextField(12);			
			add(new JLabel("Name: "));
			add(name);
		}
		public String getName(){
			return name.getText();
		}
	}
	private class ConnectGUI extends JPanel{
		private JTextField type;
		private JTextField time;
		public ConnectGUI(){
			setLayout(new GridLayout(2,1));
			type = new JTextField(12);			
			time = new JTextField(12);
			Container typeCont = new Container();
			typeCont.setLayout(new FlowLayout());
			typeCont.add(new JLabel("Type: "));
			typeCont.add(type);
			
			Container timeCont = new Container();
			timeCont.setLayout(new FlowLayout());
			timeCont.add(new JLabel("Time: "));
			timeCont.add(time);
			
			add(typeCont);
			add(timeCont);
		}
		public String getTime(){
			return time.getText();
		}
	}
}
