
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

/*
 * MyRectangle class extending Rectangle and implements interface myShape
 */
class myRectangle extends Rectangle implements myShape{
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
	
	private Rectangle2D rect;
	
	public myRectangle(){
		super();
	}
	
	public void draw(Graphics g) {
        g.setColor(color);
        if(filled == false){
        	g.drawRect(x, y, width, height);
        }else {
        	g.fillRect(x, y, width, height);
        	g.drawRect(x, y, width, height);  	
        }
        //Initialize rect to a new rectangle with the same dimensions
     	rect = new Rectangle2D.Float(x, y, width, height);
	}
	
	public void setColor(Color color) {	
		this.color = color;
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
		//drawing a slightly bigger rectangle (outside outline)
		Rectangle2D bigger =  new Rectangle2D.Float(x - 3 , y - 3, 
														width + 6, height + 6);

		Rectangle2D smaller = new Rectangle2D.Float(x + 3 , y + 3, 
														width - 6, height - 6);
		//selecting the outline of rectangle
		if (filled == false) {

			if (bigger.contains(point) && !smaller.contains(point)) {
				//System.out.println("Outline is clicked"); //testing
				return true;
				
			} else{
				//System.out.println("Outline is NOT clicked"); //testing
				return false;
			}
		} else {
			if (rect.contains(point)) {
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
