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
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import edu.ou.cs.hci.resources.*;

//******************************************************************************

/**
 * The <CODE>BuildTest</CODE> class.<P>
 *
 * @author  Sam Emswiler
 * @version %I%, %G%
 */
public final class ScenarioDisplay
{
	  //********************************************************************** 
	  // Public Class Members 
	  //********************************************************************** 
	 
	  private static final Font  FONT = new Font(Font.SERIF, Font.ITALIC, 36); 
	  private static final Color  FILL_COLOR = Color.WHITE; 
	  private static final Color  EDGE_COLOR = Color.BLACK; 

	//**********************************************************************
	// Main
	//**********************************************************************

	public static void main(String[] args)
	{
		
		/*
		 * Create Main Wireframe
		 */
		
		JFrame      mainFrame = new JFrame("Proud Puppies"); 
		JPanel      menu = new HelloPanel("Menu..."); 
		JPanel      bigPicture = new HelloPanel("Display"); 
		JPanel      smallPicPrevious = new HelloPanel("Previous"); 
		JPanel      smallPicCurrent = new HelloPanel("Current"); 
		JPanel      smallPicNext = new HelloPanel("Next"); 
		JPanel      content = new HelloPanel("Content..."); 
	 
	     
	    mainFrame.setSize(1000, 700); //width, height  
	    mainFrame.setLocationRelativeTo(null); 
	    mainFrame.getContentPane().setLayout(new GridBagLayout()); 
	    GridBagConstraints c = new GridBagConstraints(); 
	     
	    //add the panel inside the frame 
	    c.fill = GridBagConstraints.BOTH; 
	    c.weighty = 0.3; 
	    c.weightx = 0.6; 
	    c.gridx = 0; 
	    c.gridy = 0; 
	    c.gridheight = 6; 
	    mainFrame.getContentPane().add(menu, c); 
	     
	    c.fill = GridBagConstraints.BOTH; 
	    c.weighty = 0.7; 
	    c.weightx = 0.5; 
	    c.gridx = 2; 
	    c.gridy = 5; 
	    c.gridwidth = 3; 
	    c.gridheight = 1; 
	    mainFrame.getContentPane().add(content, c); 
	     
	    c.fill = GridBagConstraints.BOTH; 
	    c.weighty = 1.5; 
	    c.weightx = 0.9; 
	    c.gridx = 1; 
	    c.gridy = 0; 
	    c.gridheight = 3; 
	    c.gridwidth = 4; 
	    mainFrame.getContentPane().add(bigPicture, c); 
	     
	    c.fill = GridBagConstraints.BOTH; 
	    c.weighty = 0.4; 
	    c.weightx = 0.5; 
	    c.gridx = 2; 
	    c.gridy = 4; 
	    c.gridheight = 1; 
	    c.gridwidth = 1; 
	    c.insets = new Insets(3,150,3,3); 
	    mainFrame.getContentPane().add(smallPicPrevious, c); 
	     
	    c.fill = GridBagConstraints.BOTH; 
	    c.weighty = 0.4; 
	    c.weightx = 0.5; 
	    c.gridx = 3; 
	    c.gridy = 4; 
	    c.insets = new Insets(3,3,3,3); 
	    mainFrame.getContentPane().add(smallPicCurrent, c); 
	     
	    c.fill = GridBagConstraints.BOTH; 
	    c.weighty = 0.4; 
	    c.weightx = 0.5; 
	    c.gridx = 4; 
	    c.gridy = 4; 
	    c.insets = new Insets(3,3,3,150); 
	    mainFrame.getContentPane().add(smallPicNext, c); 
	    
	    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	 
	    /*
	     * Create Scenario Frame
	     */
	    
		JFrame			scenarioFrame = new JFrame("Scenario Viewer");
		ScenarioPane	scenarioPane = new ScenarioPane();
		
		//Grab the data from the text files and give them to scenarioPane
		ArrayList<String> scenarioTitles = Resources.getLines("scenarios/titles.txt");
		scenarioPane.setTitleData(scenarioTitles);
		ArrayList<String> scenarioDesc = Resources.getLines("scenarios/descriptions.txt");
		scenarioPane.setDescriptionData(scenarioDesc);


		scenarioFrame.getContentPane().setLayout(new BorderLayout());
		scenarioFrame.getContentPane().add(scenarioPane, BorderLayout.CENTER);
		scenarioFrame.pack ();
		scenarioFrame.setLocationRelativeTo ( null );
		scenarioFrame.setBounds(50, 50, 600, 600);
		scenarioFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		scenarioFrame.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
		
		scenarioFrame.setVisible(true);
	    mainFrame.setVisible(true); 
	    menu.setVisible(true); 

		
	}

	//**********************************************************************
	// Private Inner Classes
	//**********************************************************************

	private static final class HelloPanel extends JPanel 
	{ 
	    private final String  message; 
	 
	    public HelloPanel(String message) 
	    { 
	      this.message = ((message != null) ? message : ""); 
	    } 
	 
	    public HelloPanel() 
	    { 
	      this(""); 
	    } 
	 
	    public void  paintComponent(Graphics g) 
	    { 
	      FontMetrics  fm = g.getFontMetrics(FONT); 
	      int      fw = fm.stringWidth(message); 
	      int      fh = fm.getMaxAscent() + fm.getMaxDescent(); 
	      int      x = (getWidth() - fw) / 2; 
	      int      y = (getHeight() - fh) / 2; 
	      Rectangle    r = new Rectangle(x, y, fw + 4, fh + 1); 
	 
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
	
	
	private static final class ScenarioPane extends JPanel
	{
		private String[] array;
		private ArrayList<String> descData;
		private JList<String> scenarioTitle;
		private JScrollPane titleScroll;
		private JScrollPane descScroll;
		private JTextArea scenarioDescription;
		private GridBagLayout layout;
		private GridBagConstraints titleC;
		private GridBagConstraints descC;

		public ScenarioPane()
		{
			layout = new GridBagLayout();
			descData = new ArrayList<String>();
			
			//Set constraints for title scroll area
			titleC = new GridBagConstraints();
			titleC.gridx = 0;
			titleC.gridy = 0;
			/* We want this to take up less room than the description
			 * so make weight in the y direction really low to take up 
			 * as little space as possible
			 */
			titleC.weighty = 0.5;
			titleC.weightx = 1;
			//Anchor it to the top of the window
			titleC.anchor = GridBagConstraints.NORTH;
			titleC.fill = GridBagConstraints.BOTH;
			
			descC = new GridBagConstraints();
			descC.gridx = 0;
			descC.gridy = 1;
			descC.weighty = 1;
			descC.weightx = 1;
			//Add extra padding so description is always bigger than title section
			descC.ipady = 200;
			descC.fill = GridBagConstraints.BOTH;
			
			/* Create title area as a string JList, set it as single selection,
			 * and put it in a JScrollPane to look nice and so it scrolls if need
			 * be
			 */
			scenarioTitle = new JList<String>();
			scenarioTitle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			titleScroll = new JScrollPane(scenarioTitle);
			//Add a fancy border because we're fancy people
			titleScroll.setBorder (new TitledBorder(new EtchedBorder(), "Scenario Title"));
			layout.setConstraints(titleScroll, titleC);
			
			/* Create description area which will word and line wrap as necessary,
			 * and will be added to a scroll panel in case the description is larger
			 * than the window
			 */
			scenarioDescription = new JTextArea ();
			scenarioDescription.setEditable (false);
			//Lines will wrap if they can't fit
			scenarioDescription.setLineWrap(true);
			//Words will also wrap if they can't fit
			scenarioDescription.setWrapStyleWord(true);
			descScroll = new JScrollPane (scenarioDescription);
			//Always display the scrollbar
			descScroll.setVerticalScrollBarPolicy (ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			//Add a fancy border because we're fancy people
			descScroll.setBorder (new TitledBorder(new EtchedBorder(), "Scenario Description"));
			layout.setConstraints(descScroll, descC);
			
			//Add Listener to JList to update description when the scenario title is selected
			scenarioTitle.addListSelectionListener(new ListSelectionListener() {

	            @Override
	            public void valueChanged(ListSelectionEvent arg0) {
	                if (!arg0.getValueIsAdjusting()) {
	                	//Grab the index selected
	                	int index = scenarioTitle.getSelectedIndex();
	                	scenarioDescription.setText(descData.get(index));
	                }
	            }
	        });
			
			setLayout(layout);
		    add(titleScroll);
		    add(descScroll);
		}
		
		public void setTitleData(ArrayList<String> data)
		{
			//Must convert ArrayList<String> to String[] array to set JList data
			String[] array = new String[data.size()];
			data.toArray(array);
			scenarioTitle.setListData(array);
		}
		
		public void setDescriptionData(ArrayList<String> data)
		{
			descData = data;
			//Set initial selection when setting new data
			scenarioTitle.setSelectedIndex(0);
		}
	}
}

//******************************************************************************
