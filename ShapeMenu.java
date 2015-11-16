import javax.swing.*;
import java.io.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Rectangle;
import java.awt.BorderLayout;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.Scanner;

/**
	This class creates a frame with a menu bar
		and saves/creates files.
	@author Samantha Sturges
	CS 250
	Assignment 3, Problem 3
*/

public class ShapeMenu extends JFrame
{
	private static final int FRAME_WIDTH = 400;
	private static final int FRAME_HEIGHT = 500;

	private final int shape1 = 1;
	private final int shape2 = 2;
	private final int shape3 = 3;
	private static int setShape;

	/**
		Constructs a frame with menu bar.
	*/
	public ShapeMenu()
	{
		JMenuBar bar = new JMenuBar();
		setJMenuBar(bar);
		bar.add(createFileMenu());
		bar.add(createShapeMenu());

		setSize(FRAME_WIDTH, FRAME_HEIGHT);
	}

	/**
		Makes a File menu with 3 items.
		@return File menu to display.
	*/
	public JMenu createFileMenu()
	{
		JMenu fileMenu = new JMenu("File");
		fileMenu.add(createFileNewItem());
		fileMenu.add(createFileOpenItem());
		fileMenu.add(createFileSaveItem());

		return fileMenu;
	}

	/**
		Creates a menu item called New.
		@return New menu item to add to File menu.
	*/
	public JMenuItem createFileNewItem()
	{

		JMenuItem newFile = new JMenuItem("New");
		//Inner class on selection clears component, or saves file if needed.
		class MenuItemListener implements ActionListener
		{
			JFileChooser fileChooser = new JFileChooser();
			public void actionPerformed(ActionEvent event)
			{
				ShapeFrame accessSize = new ShapeFrame();
				if (0 < accessSize.getCount())
				{
					try{
					int response = JOptionPane.showConfirmDialog(null, "Do you want to save?", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					ShapeFrame writer = new ShapeFrame();
					BufferedWriter output = null;

					if (response == JFileChooser.APPROVE_OPTION)
					{
						int userSelection = fileChooser.showSaveDialog(ShapeMenu.this);
					    File fileToSave = fileChooser.getSelectedFile();	//Gets file to save.
					    System.out.println("Save as file: " + fileToSave.getAbsolutePath());
					    output = new BufferedWriter(new FileWriter(fileToSave));
					    //Loops through an array to save a string of data to a file with new line.
					    for (int i = 0; i < writer.getData(ShapeMenu.this).size(); i++)
					    {
							output.write(writer.getArrays(ShapeMenu.this, i));
							if(i < writer.getData(ShapeMenu.this).size()-1)
							{
								output.write("\n");
							}
						}
						output.close();
					}
					}catch (Exception ex){
						JOptionPane.showMessageDialog(null, "Exception " + ex);} //Catches exception.
				}
				accessSize.clearData(ShapeMenu.this); //Clears array information.
				accessSize.setCount();				//Sets counter to 0.
			}
		}


		ActionListener listener = new MenuItemListener();
		newFile.addActionListener(listener);

		return newFile;
	}

	/**
		Creates a menu item called Open.
		@return Open menu item to add to File menu.
	*/
	public JMenuItem createFileOpenItem()
	{
		JMenuItem openFile = new JMenuItem("Open");
		//Inner class on selection opens a file, or saves current file if needed.
		class MenuItemListener implements ActionListener
		{
			JFileChooser fileChooser = new JFileChooser();
			public void actionPerformed(ActionEvent event)
			{
				try{
				ShapeFrame reader = new ShapeFrame();
				if (0 < reader.getCount()) //Determines if current file has been altered and, if yes, asks user if they want to save.
				{
					int response = JOptionPane.showConfirmDialog(null, "Do you want to save?", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

					 if (response == JOptionPane.YES_OPTION)
					{
						fileChooser.setDialogTitle("Specify a file to save");
						BufferedWriter output = null;

						int userSelection = fileChooser.showSaveDialog(ShapeMenu.this);
						 //Loops through an array to save a string of data to a file with new line.
						if (userSelection == JFileChooser.APPROVE_OPTION)
						{
						    File fileToSave = fileChooser.getSelectedFile(); //Gets file to save as.
						    System.out.println("Save as file: " + fileToSave.getAbsolutePath());
						    output = new BufferedWriter(new FileWriter(fileToSave));
						    for (int i = 0; i < reader.getData(ShapeMenu.this).size(); i++)
						    {
								output.write(reader.getArrays(ShapeMenu.this, i));
								if(i < reader.getData(ShapeMenu.this).size()-1)
								{
									output.write("\n");
								}
							}
							output.close();
							reader.setCount();	//Sets count back to 0.
						}
					}

				}

					fileChooser.setDialogTitle("Specify a file to open");
					int result = fileChooser.showOpenDialog(ShapeMenu.this);
					//Asks user which file to open.
					if (result == JFileChooser.APPROVE_OPTION)
					{
						reader.clearData(ShapeMenu.this);
					    File selectedFile = fileChooser.getSelectedFile();	//Gets file to open.
						Scanner scan = new Scanner(selectedFile);
						 //Loops through an array to open a string of data as shapes.
						while (scan.hasNextLine())
						{
							reader.setData(ShapeMenu.this, scan.nextInt(), scan.nextInt(), scan.nextInt());
						}

						ShapeMenu.this.getContentPane().repaint(); //Calls repaint to display shapes from file.
					}
				}
				catch (Exception ex){
					JOptionPane.showMessageDialog(null, "Exception " + ex);} //Catches exceptions.
			}
		}
		ActionListener listener = new MenuItemListener();
		openFile.addActionListener(listener);

		return openFile;
	}

	/**
		Creates a menu item called Save.
		@return Save menu item to add to File menu.
	*/
	public JMenuItem createFileSaveItem()
	{
		JMenuItem saveFile = new JMenuItem("Save");
		//Inner class on selection saves current file.
		class MenuItemListener implements ActionListener
		{
			JFileChooser fileChooser = new JFileChooser();
			public void actionPerformed(ActionEvent event)
			{
				try{
				ShapeFrame writer = new ShapeFrame();
				fileChooser.setDialogTitle("Specify a file to save");
				BufferedWriter output = null;

				int userSelection = fileChooser.showSaveDialog(ShapeMenu.this);

				if (userSelection == JFileChooser.APPROVE_OPTION)
				{
					File fileToSave = fileChooser.getSelectedFile(); //Gets file to save to.
					System.out.println("Save as file: " + fileToSave.getAbsolutePath());
					output = new BufferedWriter(new FileWriter(fileToSave));
					//Loops through an array to save a string of data to a file with new line.
					for (int i = 0; i < writer.getData(ShapeMenu.this).size(); i++)
					{
						System.out.println(writer.shapeTy.get(i));
						output.write(writer.getArrays(ShapeMenu.this, i));
						if(i < writer.getData(ShapeMenu.this).size()-1)
						{
							output.write("\n");
						}
					}
					output.close();
					writer.setCount(); //Sets count back to 0.
				}
				}catch (Exception ex){
					JOptionPane.showMessageDialog(null, "Exception " + ex);} //Catches Exceptions.
			}
		}
		ActionListener listener = new MenuItemListener();
		saveFile.addActionListener(listener);

		return saveFile;
	}

	/**
		Makes a Shape menu with 3 items.
		@return Shape menu to display.
	*/
	public JMenu createShapeMenu()
	{
		JMenu shapeMenu = new JMenu("Shapes");
		shapeMenu.add(createRectangle());
		shapeMenu.add(createEllipse());
		shapeMenu.add(createTriangle());

		return shapeMenu;
	}

	/**
		Makes a Rectangle menu item.
		@return Rectangle menu item to add to Shape menu.
	*/
	public JMenuItem createRectangle()
	{
		JMenuItem rectShape = new JMenuItem("Rectangle");
		//Inner class which on selection determines shape to draw.
		class MenuItemListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				ShapeMenu.shapeInfo(shape1); //Value placeholder of shape name.
			}
		}
		ActionListener listener = new MenuItemListener();
		rectShape.addActionListener(listener);

		return rectShape;
	}

	/**
		Makes an Ellipse menu item.
		@return Ellipse menu item to add to Shape menu.
	*/
	public JMenuItem createEllipse()
	{
		JMenuItem ellipseShape = new JMenuItem("Ellipse");
		//Inner class which on selection determines shape to draw.
		class MenuItemListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				ShapeMenu.shapeInfo(shape2); //Value placeholder of shape name.
			}
		}
		ActionListener listener = new MenuItemListener();
		ellipseShape.addActionListener(listener);

		return ellipseShape;
	}

	/**
		Makes a Triangle menu item.
		@return Triangle menu item to add to Shape menu.
	*/
	public JMenuItem createTriangle()
	{
		JMenuItem triangleShape = new JMenuItem("Triangle");
		//Inner class which on selection determines shape to draw.
		class MenuItemListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				ShapeMenu.shapeInfo(shape3); //Value placeholder of shape name.
			}
		}
		ActionListener listener = new MenuItemListener();
		triangleShape.addActionListener(listener);

		return triangleShape;
	}

	/**
		Sets value placeholder of shape to determine shape name.
		@return the shape value to draw.
	*/
	public int findShape()
	{
		return setShape;
	}

	/**
		Gets value placeholder of shape to determine shape name.
		@param x the shape value to get.
	*/
	public static void shapeInfo (int x)
	{
		setShape = x;
	}
}