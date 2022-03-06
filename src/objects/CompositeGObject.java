package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class CompositeGObject extends GObject {

	private List<GObject> gObjects;

	public CompositeGObject() {
		super(0, 0, 0, 0);
		gObjects = new ArrayList<GObject>();
	}

	public List<GObject> getgObjects() {
		return  gObjects;
	}

	public void add(GObject gObject) {
		gObjects.add(gObject);
		recalculateRegion();
	}

	public void remove(GObject gObject) {
		// TODO: Implement this method.
		gObjects.remove(gObject);
	}

	@Override
	public void move(int dX, int dY) {
		for(GObject obj : gObjects) {
			obj.move(dX, dY);
		}
		recalculateRegion();
	}
	
	public void recalculateRegion() {
		int minX = gObjects.get(0).x;
		int minY = gObjects.get(0).y;
		int maxX = gObjects.get(0).x + gObjects.get(0).width;
		int maxY = gObjects.get(0).y + gObjects.get(0).height;

		for(GObject obj : gObjects) {
			if(obj.x < minX) {
				minX = obj.x;
			}
			if(obj.y < minY) {
				minY = obj.y;
			}
			if(obj.x + obj.width > maxX) {
				maxX = obj.x + obj.width;
			}
			if(obj.y + obj.height > maxY) {
				maxY = obj.y + obj.height;
			}
		}

		x = minX;
		y = minY;
		width = maxX - minX;
		height = maxY - minY;
	}

	@Override
	public void paintObject(Graphics g) {
		for(GObject obj: gObjects) {
			obj.paintObject(g);
		}
	}

	@Override
	public void paintLabel(Graphics g) {
		// TODO: Implement this method.
	}
	
}
