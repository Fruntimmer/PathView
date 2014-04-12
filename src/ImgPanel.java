import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ListGraph.Edge;
import ListGraph.Node;
import ListGraph.Sprite;

public class ImgPanel extends JPanel{
	//private JLabel picLabel;
	private Image pic = null;
	private String imgPath = null;
	private EventHandler eventHandler;
	public ImgPanel(EventHandler eventHandler){
		this.eventHandler = eventHandler;
		setLayout(null);
	}
	public Dimension setImage(File img){
		try {	
			pic = ImageIO.read(img);
			imgPath = img.getPath();
			//picLabel.setBounds(0, 0, pic.getWidth(this), pic.getHeight(this));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setSize(pic.getWidth(this),pic.getHeight(this));
		return new Dimension(pic.getWidth(this), pic.getHeight(this));
	}
	public String getImgPath(){
		return imgPath;
	}
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		if(pic!= null)
			g.drawImage(pic, 0, 0, pic.getWidth(this), pic.getHeight(this), this);
		for(Node n : eventHandler.getNodes()){
			Sprite s = ((CityNode)n).getSprite();
			//Decide color then draw city
			if(eventHandler.isInPath(n)){
				g.setColor(Color.YELLOW);
			}else if(eventHandler.isSelected(n)){
				g.setColor(Color.GREEN);
			}else{
				g.setColor(Color.RED);
			}
			int w = Sprite.getWidth();
			int h = Sprite.getHeight();
			
			g.fillOval(s.getX()-w/2, s.getY()-h/2, w, h);
			//Set up text
			g.setColor(Color.BLACK);
			//Fontmetrics is only used to get pixel width of string to center it under the node nicely.
			Font font = new Font("Arial", Font.BOLD, 16);
			FontMetrics fm = getFontMetrics(font);
			g.setFont(font);
			g.drawString(n.toString(),s.getX()-fm.stringWidth(n.toString())/2, s.getY()+25);
			//DrawConnections
			for(Edge edge : eventHandler.getConnections(n)){
				g.setColor(Color.BLUE);
				Sprite s1 = ((CityNode)n).getSprite();
				Sprite s2 = ((CityNode)edge.getDest()).getSprite();
				g.drawLine(s1.getX(), s1.getY(), s2.getX(), s2.getY());
				//Draw edge weight.
				g.setColor(Color.BLACK);
				Point mid = pointOnLine(s1.getX(), s1.getY(), s2.getX(), s2.getY());
				g.drawString(String.valueOf(edge.getWeight()), (int)mid.getX(), (int)mid.getY());
				
			}
		}
	}
	private Point pointOnLine(int x1, int y1, int x2, int y2){
		double d = Math.sqrt((x2-x1)^2 + (y2 - y1)^2);
		double r = .5;
		int x3 = (int) (r * x2 + (1 - r) * x1);
		int y3 = (int) (r * y2 + (1 - r) * y1);
		return new Point(x3, y3);
	}
}