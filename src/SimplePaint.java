
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;

/*
 * Last 4 UIN: 1062
 * Last date modified: 11/03/2016
 * COP 3003
 * Simple paint program that lets user draw three kinds of geometric shapes: 
 * rectangle, oval, and edge in four colors: red, green, blue, and black. 
 * They can be either filled or unfilled. The drawn shapes can be removed one 
 * by one by clicking the "Undo" button or completely by clicking the "Clear" 
 * button. Shapes can be selected and moved by right-dragging the mouse
 */
public class SimplePaint extends JPanel {

	private JPanel controlPanel;
	private JPanel statusPanel;
	
	private Button undo;
	private Button clear;
	private String[] colors = { "Black", "Red", "Blue", "Green" };
	private String[] shapes = { "Rectangle", "Oval", "Line" };
	private JComboBox colorBox;
	private JComboBox shapeBox;
	private JCheckBox filled;

	private JLabel coordinates;

	private MouseHandler mouse;

	private ArrayList<Shape> figures;

	private myShape form;
	private myShape shapeSelect;

	private Point start;
	private Point end;
	private Point last;

	private String currentShape = "Rectangle";
	private Color currentColor;

	private boolean fill = false;
	
	private boolean canDrag = false;
	
	private int dx;
	private int dy;
	
	/*
	 * Constructor
	 * Setup the panel
	 */
	public SimplePaint() {
		super();

		figures = new ArrayList<Shape>();

		mouse = new MouseHandler();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);

		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(600, 600));
		setBackground(Color.WHITE);

		buildControlPanel();
		buildStatusPanel();

		setVisible(true);
	}

	/*
	 * Setup and build the control panel on the top of the frame
	 * which contains the undo and clear buttons,
	 * shape and color selection, and fill check box
	 */
	public void buildControlPanel() {
		controlPanel = new JPanel();
		undo = new Button("Undo");
		clear = new Button("Clear");
		colorBox = new JComboBox(colors);
		shapeBox = new JComboBox(shapes);
		filled = new JCheckBox("filled");

		add(controlPanel, BorderLayout.NORTH);
		controlPanel.setBackground(Color.LIGHT_GRAY);
		controlPanel.add(undo);
		controlPanel.add(clear);
		controlPanel.add(colorBox);
		controlPanel.add(shapeBox);
		controlPanel.add(filled);

		shapeBox.addActionListener(mouse);
		colorBox.addActionListener(mouse);
		undo.addActionListener(mouse);
		clear.addActionListener(mouse);
		filled.addActionListener(mouse);
	}

	/*
	 * Setup and build the status panel on the bottom of the frame
	 * which contains the coordinates of mouse
	 */
	public void buildStatusPanel() {
		statusPanel = new JPanel();
		coordinates = new JLabel();

		add(statusPanel, BorderLayout.SOUTH);
		statusPanel.setBackground(Color.LIGHT_GRAY);
		statusPanel.setLayout(new BorderLayout());
		statusPanel.add(coordinates, BorderLayout.WEST);
	}

	/*
	 * Main method
	 * Setup the JFrame
	 */
	public static void main(String args[]) {
		SimplePaint panel = new SimplePaint();
		JFrame frame = new JFrame("Paint");
		frame.add(panel);
		frame.setVisible(true);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (start != null) {
			form.setColor(currentColor);
			form.setPoints(start, end);
			form.setFilled(fill);
		}
		if (end != null) {
			for (int i = 0; i < figures.size(); i++) {
				((myShape) figures.get(i)).draw(g);
			}
		}
	}

	/*
	 * Inner class that handles all the mouse events and action performed
	 * 
	 */
	private class MouseHandler extends MouseAdapter implements 
										  MouseMotionListener, ActionListener {
		/*
		 * handle all the events from the buttons, shape and color selections,
		 * and fill option
		 */
		public void actionPerformed(ActionEvent e) {
			//Shape selection
			if (e.getSource() == shapeBox) {
				currentShape = shapeBox.getSelectedItem().toString();
			}
			//Color selection
			if (e.getSource() == colorBox) {
				String colorSelect = colorBox.getSelectedItem().toString();
				//System.out.println(colorSelect); //testing
				switch (colorSelect) {
					case "Black":
						currentColor = Color.BLACK;
						break;
					case "Green":
						currentColor = Color.GREEN;
						break;
					case "Red":
						currentColor = Color.RED;
						break;
					case "Blue":
						currentColor = Color.BLUE;
						break;
				}
			}
			//Undo pressed
			if (e.getSource() == undo && (figures.isEmpty() == false)) {
				undo();
				repaint();
			}
			//Clear pressed
			if (e.getSource() == clear && (figures.isEmpty() == false)) {
				clear();
				repaint();
			}
			
			//Filled checked
			if (e.getSource() == filled) {
				if (filled.isSelected()) {
					fill = true;
					//System.out.println("Filled"); //testing
				} else {
					fill = false;
					//System.out.println("NOT filled"); //testing
				}
			}
		}

		public void mousePressed(MouseEvent e) {
			//Right mouse pressed
			if (SwingUtilities.isRightMouseButton(e)) {
				//store coordinates of right pressed in last
				last = e.getPoint(); 
				//iterate through arrayList
				for (int i = figures.size() - 1; i >= 0; i--) {
					//check if shape in arrayList is a Rectangle, Oval, or Line
					if (figures.get(i) instanceof Rectangle2D || 
						figures.get(i) instanceof Ellipse2D ||
						figures.get(i) instanceof Line2D ) {
						
						//check if that shape is being selected
						if (((myShape) figures.get(i)).isSelected(last)) {
							//Store the boolean value of isSelected in drag
							canDrag = ((myShape) figures.get(i)).
															   isSelected(last);
							
							//If shape is selected
							if (canDrag){
								//Store the shape is was selected in shapeSelect
								shapeSelect = (myShape) figures.get(i);
							}
						}
					}
				} //end for loop
			//Left mouse pressed
			} else {
				//determine which shape to be drawn from shape selection
				switch (currentShape) { 
					case "Rectangle":
						form = new myRectangle();
						break;
					case "Oval":
						form = new myOval();
						break;
					case "Line":
						form = new myLine();
						break;
				}
				//Store coordinates of left mouse pressed in start 
				start = e.getPoint();
				//Add the shape that's being drawn into arayList
				figures.add((Shape) form);
			}
		}//end MousePressed
		
		public void mouseDragged(MouseEvent e) {
			//Dragging with the right mouse button
			if  (SwingUtilities.isRightMouseButton(e)) {
				//Find the difference between the coordinates of point being 
				//drag and the X and Y coordinates of shape selected
				dx = e.getX() - shapeSelect.getNewX();
	        	dy = e.getY() - shapeSelect.getNewY();
	        	
	        	//If shape is selected
		        if (canDrag){
		        	//User can move the shape
		        	shapeSelect.move(dx, dy);
		        }
		    //Dragging with the left mouse button
			} else {
				//Store coordinates of the left mouse drag in end
				end = e.getPoint();
			}
			repaint();
		}//end MouseDragged
		
		public void mouseReleased(MouseEvent e) {
			
			//Right mouse button released
			if (SwingUtilities.isRightMouseButton(e)) {
				//set the shape was selected to false
				canDrag = false;
			//Left mouse button released
			} else {
				//store the coordinates of left mouse released in end
				end = e.getPoint();
				//set start to null
				start = null;
			}
		}//end mouseReleased

		public void mouseExited(MouseEvent e) {
			//display message if mouse exit the panel
			coordinates.setText("Mouse is out");
		}

		public void mouseMoved(MouseEvent e) {
			//display the coordinates of the mouse as it moves
			coordinates.setText("(" + e.getX() + "," + e.getY() + ")");
		}

		//clear the arrayList
		private void clear() {
			figures.clear();
		}
		
		//remove the last shape that was drawn in the arayList
		private void undo() {
			figures.remove(figures.size() - 1);
			repaint();
		}	
	}

}
