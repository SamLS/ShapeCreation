import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;

/**
	This class displays the frame and its component.
		Gets on mouse press event to draw shapes.
	@author Samantha Sturges
	CS 250
	Assignment 3, Problem 3
*/

public class ShapeViewer
{
	public static void main(String[] args)
	{
		final ShapeFrame component = new ShapeFrame();

		// Add mouse press listener
		class MousePressListener implements MouseListener
		{
			public void mousePressed(MouseEvent event)
			{
				int x = event.getX();
				int y = event.getY();
				component.drawShape(x, y);
				component.countObjects();
			}
				// Do-nothing methods
				public void mouseReleased(MouseEvent event) {}
				public void mouseClicked(MouseEvent event) {}
				public void mouseEntered(MouseEvent event) {}
				public void mouseExited(MouseEvent event) {}
		}

		MouseListener listener = new MousePressListener();
		component.addMouseListener(listener);

		JFrame frame = new ShapeMenu();
		frame.add(component);

		frame.setTitle("Basic Shapes");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}