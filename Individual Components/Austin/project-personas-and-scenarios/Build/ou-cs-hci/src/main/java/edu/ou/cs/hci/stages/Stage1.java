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

package edu.ou.cs.hci.stages;

//import java.lang.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import edu.ou.cs.hci.resources.Resources;
import java.util.ArrayList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


//******************************************************************************

/**
 * The <CODE>Stage1</CODE> class.<P>
 *
 * @author  Chris Weaver
 * @version %I%, %G%
 */
public final class Stage1
{
	//**********************************************************************
	// Public Class Members
	//**********************************************************************

	private static final Font	FONT =
		new Font(Font.SERIF, Font.ITALIC, 36);
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
		
		Resources r = new Resources();
		
		ArrayList<String> titleList = new ArrayList<>(r.getLines("scenarios/titles.txt"));
		ArrayList<String> descriptionList = new ArrayList<>(r.getLines("scenarios/descriptions.txt"));
		
		Font			FONT2 = new Font(Font.SERIF, Font.BOLD, 20);
		Font			FONT3 = new Font(Font.SERIF, Font.PLAIN, 18);

		JFrame			frame = new JFrame("Proud Puppies");
		JPanel			topPanel = new JPanel();
		JPanel			bottomPanel = new JPanel();
		JPanel			menu = new HelloPanel("Menu...");
		JPanel			bigPicture = new HelloPanel("Display");
		JPanel			smallPicPrevious = new HelloPanel("Previous");
		JPanel			smallPicCurrent = new HelloPanel("Current");
		JPanel			smallPicNext = new HelloPanel("Next");
		JPanel			content = new HelloPanel("Content...");
		JTextArea       textArea = new JTextArea();
		JList           list = new JList(titleList.toArray());
		JScrollPane     scroll1 = new JScrollPane(list);
		JScrollPane     scroll2 = new JScrollPane(textArea);
	
		
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setFont(FONT3);
		
		list.setVisibleRowCount(5);
		list.setFont(FONT2);
		list.setSelectionInterval(0,0);
		textArea.setText(descriptionList.get(0));
		
		list.addListSelectionListener(new ListSelectionListener() 
		{
		public void valueChanged(ListSelectionEvent le) 
		{
			if(!le.getValueIsAdjusting())
			{
				if(list.getSelectedValue().toString().equals("The Cat Ladies Dilemma"))
				{
					textArea.setText(descriptionList.get(0));
				}
				else if(list.getSelectedValue().toString().equals("Joses Hand"))
				{
					textArea.setText(descriptionList.get(1));
				}
				else if(list.getSelectedValue().toString().equals("Adals Neugier"))
				{
					textArea.setText(descriptionList.get(2));
				}
				else
				{
					textArea.setText("No Senario's");
				}
			}
		}
		});

		
		
		frame.setSize(1000, 700); //width, height 
		frame.setLocationRelativeTo(null);
		BoxLayout boxLayout = new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS);
		frame.setLayout(boxLayout);
		topPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//add the panel inside the frame
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.3;
		c.weightx = 0.6;
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 6;
		topPanel.add(menu, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.7;
		c.weightx = 0.5;
		c.gridx = 2;
		c.gridy = 5;
		c.gridwidth = 3;
		c.gridheight = 1;
		topPanel.add(content, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 1.5;
		c.weightx = 0.9;
		c.gridx = 1;
		c.gridy = 0;
		c.gridheight = 3;
		c.gridwidth = 4;
		topPanel.add(bigPicture, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.4;
		c.weightx = 0.5;
		c.gridx = 2;
		c.gridy = 4;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.insets = new Insets(3,150,3,3);
		topPanel.add(smallPicPrevious, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.4;
		c.weightx = 0.5;
		c.gridx = 3;
		c.gridy = 4;
		c.insets = new Insets(3,3,3,3);
		topPanel.add(smallPicCurrent, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.4;
		c.weightx = 0.5;
		c.gridx = 4;
		c.gridy = 4;
		c.insets = new Insets(3,3,3,150);
		topPanel.add(smallPicNext, c);
		
		BoxLayout boxLayout2 = new BoxLayout(bottomPanel, BoxLayout.X_AXIS);
		bottomPanel.setLayout(boxLayout2);
		
		bottomPanel.add(scroll1);
		bottomPanel.add(scroll2);
		
		
		frame.getContentPane().add(topPanel);
		frame.getContentPane().add(bottomPanel);
		
		
		
		frame.setVisible(true);
		menu.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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
				g.drawRect(10, 10, 1000, 1000);
				g.setFont(FONT);
				g.drawString(message, r.x + 2, r.y + fm.getMaxAscent());
			}
		}
	}
}

//******************************************************************************
