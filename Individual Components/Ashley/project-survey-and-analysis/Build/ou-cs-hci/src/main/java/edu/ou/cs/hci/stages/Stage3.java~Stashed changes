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
import java.util.ArrayList;
import java.util.Hashtable;

import edu.ou.cs.hci.resources.Resources;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


//******************************************************************************

/**
 * The <CODE>Base</CODE> class.<P>
 *
 * @author  Chris Weaver
 * @version %I%, %G%
 */
public final class Stage3
{
	//**********************************************************************
	// Public Class Members
	//**********************************************************************

	private static final Font	FONT =
		new Font(Font.SERIF, Font.ITALIC, 20);
	private static final Color	FILL_COLOR = Color.WHITE;
	private static final Color	EDGE_COLOR = Color.BLACK;
	private static Boolean Q2 = false;
	private static Boolean Q3 = false;
	private static Boolean Q5 = false;

	//**********************************************************************
	// Private Members
	//**********************************************************************

	// State (internal) variables
	private static String message;

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
		
		//*********Set to not visible for Project 4
		//Set frame and menu visibility to true to see the main wireframe
		frame.setVisible(false);
		menu.setVisible(false);
		scenarios.setVisible(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		scenarios.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		frame.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
	
		//Create components
		JFrame surveyFrame = new JFrame("Survey");
		JPanel age = new JPanel();
		JPanel use = new JPanel();
		JPanel freq = new JPanel();
		JPanel useful = new JPanel();
		JPanel feat = new JPanel();
		JPanel button = new JPanel();
		age.setAlignmentX(Component.CENTER_ALIGNMENT);
		use.setAlignmentX(Component.CENTER_ALIGNMENT);
		freq.setAlignmentX(Component.CENTER_ALIGNMENT);
		useful.setAlignmentX(Component.CENTER_ALIGNMENT);
		feat.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		age.setBorder(BorderFactory.createTitledBorder("Question 1"));
		use.setBorder(BorderFactory.createTitledBorder("Question 2"));
		freq.setBorder(BorderFactory.createTitledBorder("Question 3"));
		useful.setBorder(BorderFactory.createTitledBorder("Question 4"));
		feat.setBorder(BorderFactory.createTitledBorder("Question 5"));
		JLabel ageLabel = new JLabel("What is your age?");
		JLabel useLabel = new JLabel("<html>What purpose would this type of application serve for you?<br/> Check all that may apply.<html/>");
		JLabel freqLabel = new JLabel("How often would you use this type of application?");
		JLabel usefulLabel = new JLabel("<html>From extremely useful to extremely useless, please rate this <br/> applications usefulness based on the most important purpose selected in Question #2.</html>");
		JLabel featLabel = new JLabel("<html>Describe two features that you feel are absolutely necessary<br/> for a productive and enjoyable user experience with this application.<html/>");
		SpinnerModel model = new SpinnerNumberModel(20, 0, 100, 1);
		JSpinner q1 = new JSpinner(model);
		JCheckBox q2_1 = new JCheckBox("Personal");
		JCheckBox q2_2 = new JCheckBox("Professional");
		JCheckBox q2_3 = new JCheckBox("Educational");
		JSlider q3 = new JSlider(JSlider.HORIZONTAL, 0, 4, 2);
		q3.setPaintLabels(true);
		q3.setMajorTickSpacing(1);
		Hashtable<Integer, JLabel> table = new Hashtable<Integer, JLabel>();
	    table.put (0, new JLabel("Just Once"));
	    table.put (1, new JLabel("A Few Times"));
	    table.put (2, new JLabel("Monthly"));
	    table.put (3, new JLabel("Weekly"));
	    table.put (4, new JLabel("Daily"));
	    q3.setLabelTable (table);
		JRadioButton q4_1 = new JRadioButton("Extremely Useful");
		JRadioButton q4_2 = new JRadioButton("Somewhat Useful");
		JRadioButton q4_3 = new JRadioButton("Neutral");
		JRadioButton q4_4 = new JRadioButton("Somewhat Useless");
		JRadioButton q4_5 = new JRadioButton("Extremely Useless");
		ButtonGroup radio = new ButtonGroup();
		JTextArea q5 = new JTextArea(10, 10);
		JScrollPane jScrollPane = new JScrollPane(q5);
		JButton submit = new JButton("Submit Responses");
		
		//Add and group components
		q4_1.setActionCommand("Extremely Useful");
		q4_1.setSelected(true);
		q4_2.setActionCommand("Somewhat Useful");
		q4_3.setActionCommand("Neutral");
		q4_4.setActionCommand("Somewhat Useless");
		q4_5.setActionCommand("Extremely Useless");
		radio.add(q4_1);
		radio.add(q4_2);
		radio.add(q4_3);
		radio.add(q4_4);
		radio.add(q4_5);
		surveyFrame.setSize(500, 1200); //width, height 
		surveyFrame.setLocationRelativeTo(null);
		surveyFrame.getContentPane().setLayout(new BoxLayout(surveyFrame.getContentPane(), BoxLayout.Y_AXIS));
		surveyFrame.getContentPane().add(age);
		surveyFrame.getContentPane().add(use);
		surveyFrame.getContentPane().add(freq);
		surveyFrame.getContentPane().add(useful);
		surveyFrame.getContentPane().add(feat);
		surveyFrame.getContentPane().add(button);
		age.add(ageLabel);
		age.add(q1);
		use.setLayout(new BoxLayout(use, BoxLayout.Y_AXIS));
		use.add(useLabel);
		use.add(q2_1);
		use.add(q2_2);
		use.add(q2_3);
		freq.setLayout(new BoxLayout(freq, BoxLayout.Y_AXIS));
		freq.add(freqLabel);
		freq.add(q3);
		useful.setLayout(new BoxLayout(useful, BoxLayout.Y_AXIS));
		useful.add(usefulLabel);
		useful.add(q4_1);
		useful.add(q4_2);
		useful.add(q4_3);
		useful.add(q4_4);
		useful.add(q4_5);
		feat.setLayout(new BoxLayout(feat, BoxLayout.Y_AXIS));
		feat.add(featLabel);
		feat.add(jScrollPane);
		jScrollPane.setViewportView(q5);
		button.add(submit);
		submit.setVisible(true);
		
		//Set up interaction with components
		if(q2_1.isSelected() || q2_2.isSelected() || q2_3.isSelected())
		{
			Q2 = true;
		}		
		
		//a radio button is selected by default so no check for action. They will either leave it or change it and either way a value is selected.
		
		q5.getDocument().addDocumentListener(new DocumentListener()
		{
			public void removeUpdate(DocumentEvent e)
			{
	        	Q5 = true;
	        }
	        public void insertUpdate(DocumentEvent e)
	        {
	        	Q5 = true;
	        }
	        public void changedUpdate(DocumentEvent arg0)
	        {
	        	Q5 = true;
	        }
	    });
		
		surveyFrame.pack();
		surveyFrame.setVisible(true);
		
		//********NOT WORKING
		//TODO: GET BUTTON ON/OFF WORKING
//		if(Q2 && Q3 && Q5)
//		{
//			submit.setVisible(true);
//		}
//		else
//		{
//			submit.setVisible(false);
//		}
		submit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				
				System.out.println("What is your age: " + q1.getValue());
				System.out.println("What primary purpose would this type of application serve for you? Check all that may apply: ");
				if(q2_1.isSelected()){
				System.out.println("  Personal");
				}
				if(q2_2.isSelected()){
				System.out.println("  Professional");
				}
				if(q2_3.isSelected()){
				System.out.println("  Educational");
				}
				System.out.println("How often would you use this type of application?: " + q3.getValue());
				System.out.println("From extremely useful to extremely useless, please rate this applications usefulness based on the most important purpose selected in Question #2: " + radio.getSelection().getActionCommand());
				System.out.println("Describe two features that you feel are absolutely necessary for a productive and enjoyable user experience with this application:: " + q5.getText());
				
				surveyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				surveyFrame.setVisible(false);
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
