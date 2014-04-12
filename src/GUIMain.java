import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class GUIMain extends JFrame{
	private ImgPanel mapPanel;
	private JButton addConnection;
	private EventHandler eventHandler;
	public GUIMain(EventHandler eventHandler){
		super("PathView");
		this.eventHandler = eventHandler;
		setLayout(new BorderLayout());
		setSize(600, 600);
		setVisible(true);
		Container content = getContentPane();
		
		//Top container
		Container topContainer = new Container();
		topContainer.setLayout(new GridLayout(2,1));
		
		JMenuBar menu = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenu edit = new JMenu ("Edit");
		menu.add(file);
		menu.add(edit);
		JMenuItem save = new JMenuItem("Save");
		JMenuItem load = new JMenuItem("Load");
		JMenuItem importImg = new JMenuItem("Import Image");
		
		file.add(save);
		file.add(load);
		file.add(importImg);
		
		JButton findPath = new JButton("Find Path");
		JButton addLocation = new JButton("Add Location");
		addConnection = new JButton ("Add Connection");
		addConnection.setEnabled(false);
		Container buttonContainer = new Container();
		buttonContainer.setLayout(new FlowLayout());
		buttonContainer.add(addLocation);
		buttonContainer.add(addConnection);
		buttonContainer.add(findPath);
		
		topContainer.add(menu);
		topContainer.add(buttonContainer);
		
		//Center container
		mapPanel = new ImgPanel(eventHandler);
		
		//Add to final container
		content.add(mapPanel, BorderLayout.CENTER);
		content.add(topContainer, BorderLayout.NORTH);
		//AddListeners
		importImg.addActionListener(new ImportImageListener());
		addLocation.addActionListener(new AddLocationListener());
		addConnection.addActionListener(new AddConnectionListener());
		mapPanel.addMouseListener(new picListener());
		findPath.addActionListener(new PathListener());
		save.addActionListener(new SaveListener());
		load.addActionListener(new LoadListener());
	}
	public class PathListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			eventHandler.validPath();
			repaint();
		}
	}
	//Listener for map
	public class picListener extends MouseAdapter{
		public void mousePressed(MouseEvent e) {
			eventHandler.clickRegistered(e);
			addConnection.setEnabled(eventHandler.validSelection());
			repaint();
		}	
	}
	public class AddLocationListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			eventHandler.setPaintActive(true);
		}
	}
	public class ImportImageListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			importImg(null);
		}
	}
	public class AddConnectionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			eventHandler.connectNodes();
			addConnection.setEnabled(eventHandler.validSelection());
			repaint();
		}
	}
	public class SaveListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try {
                FileDialog file = new FileDialog(new JFrame(), "Save", FileDialog.SAVE);
                file.setDirectory("c:\\devel\\PathView");
                file.setVisible(true);
                String savePath = file.getFile()+".map";
				eventHandler.save(savePath, mapPanel.getImgPath());
			} catch (FileNotFoundException e1) {
				// Error here
				e1.printStackTrace();
			}
		}
	}
	public class LoadListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try {
				FileDialog file = new FileDialog(new JFrame(), "Choose Map", FileDialog.LOAD);
				file.setDirectory("C:\\devel\\PathView");
				file.setFile("*.map");
				file.setVisible(true);
				String path = file.getFile();
				String imgPath  = eventHandler.load(path);
				if(imgPath != null)
					importImg(imgPath);
				repaint();
			} catch (IOException e1) {
				// Error here
				e1.printStackTrace();
			}
		}
	}
	public void importImg(String path){
			if(path == null){
				FileDialog file = new FileDialog(this, "Choose Map", FileDialog.LOAD);
				file.setDirectory("C:\\devel\\PathView");
				file.setFile("*.jpg");
				file.setVisible(true);
				path = file.getFile();
			}
			if(path != null){
				Dimension dim = mapPanel.setImage(new File(path));
				dim.setSize(dim.getWidth()+15, dim.getHeight()+110);
				setSize(dim);
			}
	}
}

