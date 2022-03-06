package main;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import objects.*;

public class DrawingBoard extends JPanel {

	private MouseAdapter mouseAdapter; 
	private List<GObject> gObjects;
	private GObject target;
	
	private int gridSize = 10;
	
	public DrawingBoard() {
		gObjects = new ArrayList<GObject>();
		mouseAdapter = new MAdapter();
		addMouseListener(mouseAdapter);
		addMouseMotionListener(mouseAdapter);
		setPreferredSize(new Dimension(800, 600));
	}
	
	public void addGObject(GObject gObject) {
		gObjects.add(gObject);
		repaint();
	}
	
	public void groupAll() {
		CompositeGObject composite = new CompositeGObject();
		for(GObject obj : gObjects) {
			composite.add(obj);
		}
		gObjects.clear();
		gObjects.add(composite);
		repaint();
	}

	public void unGroupAll() {
		for (int i=0; i<gObjects.size(); i++){
			if (gObjects.get(i) instanceof CompositeGObject) {
				List<GObject> newList = ((CompositeGObject) gObjects.get(i)).getgObjects();
				gObjects.remove(i);
				for (int j=0; j< newList.size();j++) {
					gObjects.add(newList.get(j));
				}
				repaint();
				break;
			}
		}

	}

	public void deleteSelected() {
		for(GObject obj : gObjects) {
			if(obj.isSelected()) {
				gObjects.remove(obj);
				break;
			}
		}
		repaint();
	}
	
	public void clear() {
		gObjects.clear();
		repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		paintBackground(g);
		paintGrids(g);
		paintObjects(g);
	}

	private void paintBackground(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	private void paintGrids(Graphics g) {
		g.setColor(Color.lightGray);
		int gridCountX = getWidth() / gridSize;
		int gridCountY = getHeight() / gridSize;
		for (int i = 0; i < gridCountX; i++) {
			g.drawLine(gridSize * i, 0, gridSize * i, getHeight());
		}
		for (int i = 0; i < gridCountY; i++) {
			g.drawLine(0, gridSize * i, getWidth(), gridSize * i);
		}
	}

	private void paintObjects(Graphics g) {
		for (GObject go : gObjects) {
			go.paint(g);
		}
	}

	class MAdapter extends MouseAdapter {
		int x;
		int y;
		
		private void deselectAll() {
			for(GObject obj : gObjects) {
				obj.deselected();
			}
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			deselectAll();

			x = e.getX();
			y = e.getY();
			for(GObject obj : gObjects) {
				if(obj.pointerHit(x, y)) {
					obj.selected();
					break;
				}
			}
			repaint();
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			int newX = e.getX();
			int newY = e.getY();
			int dX = newX - x;
			int dY = newY - y;
			for(GObject obj : gObjects) {
				if(obj.isSelected()) {
					obj.move(dX, dY);
				}
			}
			x = newX;
			y = newY;
			repaint();
		}
	}
	
}