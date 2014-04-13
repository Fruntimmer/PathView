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
	private MapPanel mapPanel;
	private JButton addConnection;
	private JButton removeConnection;
	private JButton findPath;
	private EventHandler eventHandler;
	public GUIMain(EventHandler eventHandler){
		super("PathView");
		this.eventHandler = eventHandler;
		setLayout(new BorderLayout());
		setSize(600, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		
		findPath = new JButton("Find Path");
		JButton addLocation = new JButton("Add Location");
		addConnection = new JButton ("Add Connection");
		removeConnection = new JButton("Remove Connection");
		addConnection.setEnabled(false);
		removeConnection.setEnabled(false);
		findPath.setEnabled(false);
		Container buttonContainer = new Container();
		buttonContainer.setLayout(new FlowLayout());
		buttonContainer.add(addLocation);
		buttonContainer.add(addConnection);
		buttonContainer.add(removeConnection);
		buttonContainer.add(findPath);
		
		topContainer.add(menu);
		topContainer.add(buttonContainer);
		
		//Center container
		mapPanel = new MapPanel(eventHandler);
		
		//Add to final container
		content.add(mapPanel, BorderLayout.CENTER);
		content.add(topContainer, BorderLayout.NORTH);
		//AddListeners
		importImg.addActionListener(new ImportImageListener());
		addLocation.addActionListener(new AddLocationListener());
		addConnection.addActionListener(new AddConnectionListener());
		removeConnection.addActionListener(new RemoveConnectionListener());
		mapPanel.addMouseListener(new picListener());
		findPath.addActionListener(new PathListener());
		save.addActionListener(new SaveListener());
		load.addActionListener(new LoadListener());
	}
	private void updateButtons(){
		addConnection.setEnabled(eventHandler.validSelection());
		removeConnection.setEnabled(eventHandler.validSelection());
		findPath.setEnabled(eventHandler.validSelection());
	}
	private class PathListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			eventHandler.validPath();
			updateButtons();
			repaint();
		}
	}
	//Listener for map
	private class picListener extends MouseAdapter{
		public void mousePressed(MouseEvent e) {
			eventHandler.clickRegistered(e);
			updateButtons();
			repaint();
		}	
	}
	private class AddLocationListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			eventHandler.setPaintActive(true);
			updateButtons();
		}
	}
	private class ImportImageListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			importImg(null);
		}
	}
	private class AddConnectionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			eventHandler.connectNodes();
			updateButtons();
			repaint();
		}
	}
	private class RemoveConnectionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			eventHandler.disconnect();
			updateButtons();
			repaint();
		}
	}
	private class SaveListener implements ActionListener{
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
	private class LoadListener implements ActionListener{
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
	private void importImg(String path){
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

