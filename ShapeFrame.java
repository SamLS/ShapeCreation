import java.awt.*;
import java.awt.geom.Ellipse2D;
import javax.swing.JComponent;
import javax.swing.JFrame;
import java.util.ArrayList;

/**
	This class creates a component that draws
		three different shapes.
	@author Samantha Sturges
	CS 250
	Assignment 3, Problem 3
*/

public class ShapeFrame extends JComponent
{
	private static final int SHAPE_X = 100;
	private static final int SHAPE_Y = 100;
	private static final int WIDTH = 20;
	private static final int HEIGHT = 30;

	ShapeMenu shape = new ShapeMenu();
	private static int findShape;
	private static int count = 0;
	private static int lastSave = 0;

	private static ArrayList<Shape> shapeArray = new ArrayList<Shape>();
	public static ArrayList<Integer> shapeTy = new ArrayList<Integer>();
	private static ArrayList<Integer> xValues = new ArrayList<Integer>();
	private static ArrayList<Integer> yValues = new ArrayList<Integer>();
	private Rectangle box;
	private Shape ellipse;
	private Polygon triangle;

	/**
		Constructs a component that can draw three shapes at specified locations with specified height and width.
	*/
	public ShapeFrame()
	{
		box = new Rectangle(SHAPE_X, SHAPE_Y, WIDTH, HEIGHT);
		ellipse = new Ellipse2D.Double(SHAPE_X, SHAPE_Y, WIDTH, HEIGHT);
		triangle = new Polygon();

		return;
	}

	/**
		Gets paint component to draw graphics.
		@param g graphics object.
	*/
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		if (shapeArray.size() > 0)
		{
			for (int i = 0; i < shapeArray.size(); i++)
			{
				g2.draw(shapeArray.get(i));
			}
		}
	}

	/**
		Draws the shape based on x and y coordinates.
		@param x coordinate x of where the mouse is clicked.
		@param y coordinate y of where the mouse is clicked.
	*/
	public void drawShape(int x, int y)
	{
		findShape = shape.findShape();

		System.out.println(findShape); //Checks that it is adding the right shape.
		//If statement draws the shape based on return value of shape.findShape();
		if (findShape == 1)
		{
			box = new Rectangle(x, y, WIDTH, HEIGHT);
			shapeArray.add(box);

			shapeTy.add(1);		//Adds to 3 arraylists numbers
			xValues.add(x);		// for easy storage and retrieval
			yValues.add(y);		// to and from a file

			repaint();
		}
		else if (findShape == 2)
		{
			ellipse = new Ellipse2D.Double(x, y, WIDTH, HEIGHT);

			shapeArray.add(ellipse);
			shapeTy.add(2);
			xValues.add(x);
			yValues.add(y);

			repaint();
		}
		else if (findShape == 3)
		{
			triangle = new Polygon();
			triangle.addPoint(x, y);
			triangle.addPoint(x-15, y+30);
		    triangle.addPoint(x+15, y+30);

		    shapeArray.add(triangle);
		    shapeTy.add(3);
		    xValues.add(x);
		    yValues.add(y);

		    repaint();
		}
	}

	/**
		Gets the size of the array of shapes.
		@return shapeArray.size() the size of the array at time of call.
	*/
	public int shapeSize()
	{
		for (int i = 0; i < shapeArray.size(); i++)
		{
			System.out.println(shapeArray.get(i).toString());
		}
		return shapeArray.size();
	}

	/**
		Clears the data from the component.
		@param frame the frame that holds the component.
	*/
	public static void clearData(JFrame frame)
	{
		shapeArray.clear();
		shapeTy.clear();
		xValues.clear();
		yValues.clear();
		findShape = 0;
		count = 0;
		frame.getContentPane().revalidate();
		frame.getContentPane().repaint();
	}

	/**
		Gets the data from the shape arraylist.
		@param frame the frame to get the component information from.
		@return shapeArray the shape arraylist.
	*/
	public static ArrayList<Shape> getData(JFrame frame)
	{
		return shapeArray;
	}

	/**
		Sets the arraylists data to the data inside a file.
		@param frame the frame.
		@param shapeType determines which shape to draw.
		@param getX determines x coordinate.
		@param getY determines y coordinate.
	*/
	public static void setData(JFrame frame, int shapeType, int getX, int getY)
	{
		if (shapeType == 1)
		{
			shapeArray.add(new Rectangle(getX, getY, WIDTH, HEIGHT));
			shapeTy.add(1);
			xValues.add(getX);
			yValues.add(getY);
		}
		else if (shapeType == 2)
		{
			shapeArray.add(new Ellipse2D.Double(getX, getY, WIDTH, HEIGHT));
			shapeTy.add(2);
			xValues.add(getX);
			yValues.add(getY);
		}
		else if (shapeType == 3)
		{
			Polygon tri = new Polygon();
			tri.addPoint(getX, getY);
			tri.addPoint(getX-15, getY+30);
		    tri.addPoint(getX+15, getY+30);

			shapeArray.add(tri);
			shapeTy.add(3);
			xValues.add(getX);
			yValues.add(getY);
		}
	}

	/**
		Gets the three arrays in to a string to print through a loop.
		@param frame the frame.
		@param index the index of arraylist in the loop.
		@return shapeValues the string of 3 integers representing shape, x, and y values.
	*/
	public static String getArrays(JFrame frame, int index)
	{
		String shapeValues;

		String s = shapeTy.get(index).toString();
		String x = xValues.get(index).toString();
		String y = yValues.get(index).toString();
		shapeValues = s + " " + x + " " + y;

		return shapeValues;
	}

	/**
		Counts number of shapes by registering user clicks.
	*/
	public static void countObjects()
	{
		count++;
	}

	/**
		Returns the number of shapes on the board.
		@return count the number of shapes.
	*/
	public static int getCount()
	{
		return count;
	}

	/**
		Count is set back to 0 if the frame is cleared.
	*/
	public static void setCount()
	{
		count = 0;
	}

	/**
		Sets the number of shapes on the board as of the last time a user saved.
	*/
	public static void setLastSave()
	{
		lastSave = shapeArray.size();
	}

	/**
		Gets the number of shapes on the board at the time of last save.
		@return lastSave number of shapes on the board at time of last save.
	*/
	public static int getLastSave()
	{
		return lastSave;
	}
}