package objects.obstacles;

import java.awt.Color;
import objects.Object;
import objects.ObjectType;

public class Block extends Object {

	
	public Block(int x, int y, int width, int height, Color color) {
		setType(ObjectType.OBSTACLE);
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setColor(color);
	}
}
