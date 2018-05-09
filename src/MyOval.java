
import java.awt.*;
import java.awt.geom.Ellipse2D;

/*
 * MyOval class extending Ellipse2D.Float and implements interface myShape
 */
class myOval extends Ellipse2D.Float implements myShape {
	private Point start;
	private Point end;
	
	private Color color;
	private boolean filled;
	
	private int x;
	private int y;
	
	private int width;
	private int height;
	
	private int x1; 
	private int x2; 
	private int y1; 
	private int y2; 
	
	private Ellipse2D oval;
	
	public myOval() {
		super();
	}

	public void draw(Graphics g) {
		g.setColor(color);
		
		if (filled == false) {
			g.drawOval(x, y, width, height);
		} else {
			g.fillOval(x, y, width, height);
		}
		//Initialize oval to a new ellipse with the same dimensions
		oval = new Ellipse2D.Float(x, y, width, height);
	}

	public void setFilled(boolean filled) {
		this.filled = filled;
	}

	public void move(int dx, int dy) {
		//add the difference between the coordinates of point being 
		//drag and the X and Y coordinates of shape selected to the x and y 
		//coordinates
		this.start.x +=  dx;
		this.x = this.start.x;
		this.start.y += dy;
		this.y = this.start.y;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setPoints(Point start, Point end) {
		this.start = start;
		this.end = end;
		
		x1 = start.x; 
		x2 = end.x;
		y1 = start.y;
		y2 = end.y;

		//set the smaller value between the two x coordinates to x
		x = Math.min(x1, x2);
		//set the smaller value between the two y coordinates to y
	    y = Math.min(y1, y2);
	    
	    //set the width and height to be the absolute value of the difference
	    width = Math.abs(x1 - x2);
        height = Math.abs(y1 - y2);
	}

	public boolean isSelected(Point point) {
		//drawing a slightly bigger oval (outside outline)
		Ellipse2D bigger = new Ellipse2D.Float(x - 3 , y - 3, 
														width + 6, height + 6);
		//drawing a slight smaller oval (inside outline)
		Ellipse2D smaller =new Ellipse2D.Float(x + 3, y + 3, 
														width - 6, height - 6);
		
		//selecting the outline of oval
		if (filled == false) {

			//check if coordinates is smaller but not bigger oval
			if (bigger.contains(point) && !smaller.contains(point)) {
				//System.out.println("Outline is clicked");//testing
				return true;

			} else{
				//System.out.println("Outline is NOT clicked"); //testing
				return false;
			}
		} else {
			if (oval.contains(point)) {
				//System.out.println("Shape is selected"); //testing
				return true;
			}
		}
		return false;
	}

	public int getNewX() {
		return this.start.x;
	}

	public int getNewY() {
		return this.start.y;
	}


}