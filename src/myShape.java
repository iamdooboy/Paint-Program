import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

interface myShape {
	 
	void draw(Graphics g);
	
	void setPoints(Point start, Point end);
	
	void setColor(Color color);
	
	void setFilled(boolean filled); 
	
	void move(int dx, int dy);
	
	public boolean isSelected(Point point);

	int getNewX();
	
	int getNewY();
	
}