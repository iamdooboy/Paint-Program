import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/*
 * MyLine class extending Line2D.Double and implements interface myShape
 */
class myLine extends Line2D.Double implements myShape {
	
	private Color color;
	private Shape line;
	
	boolean drag = false;
	
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	
	public myLine() {
		super();
	}

	public void setPoints(Point start, Point end) {
		this.x1 = start.x;
		this.y1 = start.y;
		this.x2 = end.x;
		this.y2 = end.y;
	}

	public void draw(Graphics g) {

		g.setColor(color);
		g.drawLine(x1, y1, x2, y2);
		//Initialize line to a new line with the same dimensions
		line = new Line2D.Double(x1, y1, x2, y2);
		
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void move(int dx, int dy) {
		x1 += dx;
		y1 += dy;
		x2 += dx;
		y2 += dy;
	}

	//creating a box around the line and check if point intersects it
	public boolean isSelected(Point point) {
		int boxX = point.x - 1;
		int boxY = point.y - 1;

		int boxWidth = 2;
		int boxHeight = 2;

		if (line.intersects(boxX, boxY, boxWidth, boxHeight)) {
			return true;
		} else {
			return false;
		}
	}

	public int getNewX() {
		return this.x1;
	}

	public int getNewY() {
		return this.y1;
	}
	
	/*
	 * unimplemented methods
	 */
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}
	public Rectangle2D getBounds2D() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setFilled(boolean filled) {
		// TODO Auto-generated method stub

	}
	@Override
	public double getX1() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public double getY1() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Point2D getP1() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public double getX2() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public double getY2() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Point2D getP2() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setLine(double x1, double y1, double x2, double y2) {
		// TODO Auto-generated method stub

	}

}