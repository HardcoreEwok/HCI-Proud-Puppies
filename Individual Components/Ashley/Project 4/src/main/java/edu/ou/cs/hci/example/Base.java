//******************************************************************************
// Copyright (C) 2016-2018 University of Oklahoma Board of Trustees.
//******************************************************************************
// Last modified: Tue Feb  9 20:33:16 2016 by Chris Weaver
//******************************************************************************
// Major Modification History:
//
// 20160209 [weaver]:	Original file (for CS 4053/5053 homeworks).
// 20180123 [weaver]:	Modified for use in CS 3053 team projects.
//
//******************************************************************************
// Notes:
//
//******************************************************************************

package edu.ou.cs.hci.example;

//import java.lang.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import edu.ou.cs.hci.resources.Resources;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


//******************************************************************************

/**
 * The <CODE>Base</CODE> class.<P>
 *
 * @author  Chris Weaver
 * @version %I%, %G%
 */
public final class Base
{
	//**********************************************************************
	// Public Class Members
	//**********************************************************************

	private static final Font	FONT =
		new Font(Font.SERIF, Font.ITALIC, 20);
	private static final Color	FILL_COLOR = Color.WHITE;
	private static final Color	EDGE_COLOR = Color.BLACK;

	//**********************************************************************
	// Private Members
	//**********************************************************************

	// State (internal) variables
	private static String		message;

	//**********************************************************************
	// Main
	//**********************************************************************

	public static void main(String[] args)
	{
		message = "Ashley Test";	// Could use an arg for this

		ArrayList<String> titleList = new ArrayList<>(Resources.getLines("scenarios/titles.txt"));
		ArrayList<String> descriptions = new ArrayList<>(Resources.getLines("scenarios/descriptions.txt"));
		
		JFrame			frame = new JFrame("Proud Puppies");
		JPanel			menu = new HelloPanel("Menu...");
		JPanel			bigPicture = new HelloPanel("Display");
		JPanel			smallPicPrevious = new HelloPanel("Prev.");
		JPanel			smallPicCurrent = new HelloPanel("Current");
		JPanel			smallPicNext = new HelloPanel("Next");
		JPanel			content = new HelloPanel("Content...");
		
		//Content for Scenarios ----------------------------------------------------
		JFrame			scenarios = new JFrame("Scenarios");
		JPanel			titlesBox = new JPanel();
		JPanel			descriptionsBox = new JPanel();		
		DefaultListModel<String>listModel = new DefaultListModel<>();
		JList<String>	titles = new JList<>(listModel);
		JTextArea		descriptionsArea = new JTextArea(17,22);
		JScrollPane		scroll2 = new JScrollPane(descriptionsArea);
		JScrollPane		scroll1 = new JScrollPane(titles);
		ListSelectionListener 	listener = new ListSelectionListener()
		{public void valueChanged(ListSelectionEvent e)
		//TODO: Error Handling	
		{if(e.getValueIsAdjusting() != true)
				{
					try
					{ 
						descriptionsArea.setText(descriptions.get(titles.getSelectedIndex()).toString()); 
					}
					catch(Exception exc)
					{ 
						descriptionsArea.setText("No Descriptions Found..");
					}	
					descriptionsArea.setCaretPosition(0);
				}
			}
		};
		
		scenarios.setResizable(false);
		scroll2.setViewportView(descriptionsArea);
		scroll1.setViewportView(titles);
		titles.setVisibleRowCount(4);
		titles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		titles.addListSelectionListener(listener);
		scenarios.setSize(300, 400); //width, height 
		scenarios.isAlwaysOnTop();
		scenarios.setLocationRelativeTo(null);
		scenarios.getContentPane().setLayout(new GridBagLayout());
		
		for(int i = 0; i < titleList.size(); i++) 
		{
			listModel.addElement(titleList.get(i).toString());
		}
		
		//Sets default displays
		titles.setSelectedIndex(0);
		try
		{ 
			descriptionsArea.setText(descriptions.get(titles.getSelectedIndex())); 
		}
		catch(Exception exc)
		{ 
			descriptionsArea.setText("No Info Found..");
		}
		
		descriptionsBox.add(scroll2);
		titlesBox.add(scroll1);
		descriptionsArea.setCaretPosition(0);
		descriptionsArea.setEditable(false);
		descriptionsArea.setLineWrap(true);
		descriptionsArea.setWrapStyleWord(true);
		//End Content for Scenarios ----------------------------------------------------
		
		frame.setSize(1000, 700); //width, height 
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		//add the panels inside the frame
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.3;
		c.weightx = 1;
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 5;
		frame.getContentPane().add(menu, c);

		c.weighty = 0.7;
		c.weightx = 1.5;
		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = 3;
		c.gridheight = 1;
		frame.getContentPane().add(content, c);

		c.weighty = 1.5;
		c.weightx = 1.5;
		c.gridx = 1;
		c.gridy = 0;
		c.gridheight = 3;
		c.gridwidth = 3;
		frame.getContentPane().add(bigPicture, c);
		
		c.weighty = 0.4;
		c.weightx = 0.6;
		c.gridx = 1;
		c.gridy = 3;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.insets = new Insets(3,75,3,3);
		frame.getContentPane().add(smallPicPrevious, c);

		c.weighty = 0.4;
		c.weightx = 0.6;
		c.gridx = 2;
		c.gridy = 3;
		c.insets = new Insets(3,3,3,3);
		frame.getContentPane().add(smallPicCurrent, c);

		c.weighty = 0.4;
		c.weightx = 0.6;
		c.gridx = 3;
		c.gridy = 3;
		c.insets = new Insets(3,3,3,75);
		frame.getContentPane().add(smallPicNext, c);
		
		//Scenario Titles
		c.insets = new Insets(0,0,0,0);
		c.weighty = 0.2;
		c.weightx = 1;
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 1;
		c.gridwidth = 1;
		scenarios.getContentPane().add(titlesBox, c);
		
		//Scenario Descriptions
		c.weighty = 1;
		c.weightx = 1;
		c.gridx = 0;
		c.gridy = 1;
		c.gridheight = 1;
		c.gridwidth = 1;
		scenarios.getContentPane().add(descriptionsBox, c);
		
		frame.setVisible(true);
		menu.setVisible(true);
		scenarios.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		scenarios.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		frame.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
	}

	//**********************************************************************
	// Private Inner Classes
	//**********************************************************************

	private static final class HelloPanel extends JPanel
	{
		private final String	message;

		public HelloPanel(String message)
		{
			this.message = ((message != null) ? message : "");
		}

		public HelloPanel()
		{
			this("");
		}

		public void	paintComponent(Graphics g)
		{
			FontMetrics	fm = g.getFontMetrics(FONT);
			int			fw = fm.stringWidth(message);
			int			fh = fm.getMaxAscent() + fm.getMaxDescent();
			int			x = (getWidth() - fw) / 2;
			int			y = (getHeight() - fh) / 2;
			Rectangle		r = new Rectangle(x, y, fw + 4, fh + 1);

			if (FILL_COLOR != null)
			{
				g.setColor(FILL_COLOR);
				g.fillRect(10, 10, 1000, 1000);
			}

			if (EDGE_COLOR != null)
			{
				g.setColor(EDGE_COLOR);
				//g.drawRect(10, 10, 1000, 1000);
				g.setFont(FONT);
				g.drawString(message, r.x + 2, r.y + fm.getMaxAscent());
			}
		}
	}
}

//******************************************************************************
