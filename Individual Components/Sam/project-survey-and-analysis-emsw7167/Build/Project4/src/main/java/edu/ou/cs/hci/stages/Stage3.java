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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import edu.ou.cs.hci.resources.*;

//******************************************************************************

/**
 * The <CODE>Stage3</CODE> class.<P>
 *
 * @author  Chris Weaver
 * @version %I%, %G%
 */
public final class Stage3
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
		
		QuestionnaireFrame	quesFrame = new QuestionnaireFrame("Questionnaire"); 
		quesFrame.setResizable(false);
		quesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		quesFrame.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
		
		//scenarioFrame.setVisible(true);
	    //mainFrame.setVisible(true); 
		quesFrame.setVisible(true);
		
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
	
	private static final class QuestionnaireFrame extends JFrame implements ItemListener, 
			ActionListener, ChangeListener, DocumentListener{
				
		private JPanel q1;
		private JLabel l1;
		private String q1String;
		private SpinnerNumberModel spinnerModel;
		private JSpinner q1Spinner;

		private JPanel q2;
		private JLabel l2;
		private String q2String;
		private ButtonGroup radioGroup;
		private JRadioButton yButton;
		private JRadioButton nButton;
		
		private JPanel q3;
		private JLabel l3;
		private String q3String;
		private JSlider q3Slider;

		private JPanel q4;
		private JLabel l4;
		private String q4String;
		private JCheckBox cd;
		private JCheckBox rep;
		private JCheckBox rod;
		private JCheckBox rab;
		private JCheckBox fish;
		private ArrayList<String> checkedItems;

		private JPanel q5;
		private JLabel l5;
		private String q5String;
		private JScrollPane q5Scroll;
		private JTextArea q5Text;
		
		private JPanel buttonPanel;
		private JButton finishButton;
		private JButton cancelButton;
		
		private String answer1;
		private String answer2;
		private String answer3;
		private String answer4;
		private String answer5;
		
		
		public QuestionnaireFrame(){
			
			q1 = new JPanel(new GridLayout(0,1));
			q1.setBorder (new EtchedBorder());
			q1String = "1) Enter your age:";
			l1 = new JLabel(q1String);
			spinnerModel = new SpinnerNumberModel(0, 0, 120, 1.0);  
			q1Spinner = new JSpinner(spinnerModel);
			q1Spinner.addChangeListener(this);
			q1.add(l1);
			q1.add(q1Spinner);
			q1.setAlignmentX( Component.CENTER_ALIGNMENT );
			
			q2 = new JPanel(new GridLayout(0,1));
			q2.setBorder (new EtchedBorder());
			q2String = "2) Have you adopted an animal before?";
			l2 = new JLabel(q2String);
			yButton = new JRadioButton("Yes");
			yButton.addItemListener(this);
		    nButton = new JRadioButton("No");
		    nButton.addItemListener(this);
		    radioGroup = new ButtonGroup();
		    radioGroup.add(yButton);
		    radioGroup.add(nButton);
		    q2.add(l2);
		    q2.add(yButton);
		    q2.add(nButton);
			q2.setAlignmentX( Component.CENTER_ALIGNMENT );
			
			q3 = new JPanel(new GridLayout(0,1));
			q3.setBorder (new EtchedBorder());
			q3String = "3) How likely would you be to use this application "
					+ "in seeking to adopt an animal? (1 being not at all, 10 being "
					+ "you would absolutely use it)";
			l3 = new JLabel("<html>" + q3String + "</html>");
			q3Slider = new JSlider(SwingConstants.HORIZONTAL, 1, 10, 5);
			q3Slider.setMajorTickSpacing(1);
			q3Slider.setPaintTicks(true);
			q3Slider.setPaintLabels(true);
			q3Slider.addChangeListener(this);
			q3.add(l3);
			q3.add(q3Slider);
			q3.setAlignmentX( Component.CENTER_ALIGNMENT );
			
			q4 = new JPanel(new GridLayout(0, 1));
			q4.setBorder (new EtchedBorder());
			q4String = "4) Which of these animals would you like to see in this application?";
			l4 = new JLabel(q4String);
			cd = new JCheckBox("Cats and Dogs");
		    rep = new JCheckBox("Snakes and Lizards");
		    rod = new JCheckBox("Hamsters and Mice");
		    rab = new JCheckBox("Rabbits");
		    fish = new JCheckBox("Fish");
		    cd.addItemListener(this);
		    rep.addItemListener(this);
		    rod.addItemListener(this);
		    rab.addItemListener(this);
		    fish.addItemListener(this);
		    checkedItems = new ArrayList<String>();
		    q4.add(l4);
		    q4.add(cd);
		    q4.add(rep);
		    q4.add(rod);
		    q4.add(rab);
		    q4.add(fish);
			q4.setAlignmentX( Component.CENTER_ALIGNMENT );
			
			q5 = new JPanel(new BorderLayout());
			q5.setBorder (new EtchedBorder());
			q5String = "5) What feature would you like to see the most in this application?";
			l5 = new JLabel(q5String);
			q5Text = new JTextArea();
			q5Text.setEditable (true);
			q5Text.setLineWrap(true);
			q5Text.setWrapStyleWord(true);
		    q5Text.getDocument().addDocumentListener(this);			
		    q5Scroll = new JScrollPane(q5Text);
			q5.add(l5, BorderLayout.PAGE_START);
			q5.add(q5Scroll, BorderLayout.CENTER);
			q5.setAlignmentX( Component.CENTER_ALIGNMENT );
			
			buttonPanel = new JPanel();
			buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
			finishButton = new JButton("Finish");
			finishButton.setEnabled(false);
			finishButton.addActionListener(this);
			cancelButton = new JButton("Cancel");
			cancelButton.addActionListener(this);
			buttonPanel.add(Box.createRigidArea(new Dimension(425,0)));
			buttonPanel.add(finishButton);
			buttonPanel.add(Box.createRigidArea(new Dimension(5,0)));
			buttonPanel.add(cancelButton);
			
			this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
			this.add(q1);
			this.add(Box.createRigidArea(new Dimension(0,10)));
			this.add(q2);
			this.add(Box.createRigidArea(new Dimension(0,10)));
			this.add(q3);
			this.add(Box.createRigidArea(new Dimension(0,10)));
			this.add(q4);
			this.add(Box.createRigidArea(new Dimension(0,10)));
			this.add(q5);
			this.add(Box.createRigidArea(new Dimension(0,10)));
			this.add(buttonPanel);
			this.add(Box.createRigidArea(new Dimension(0,10)));
			this.pack ();
			this.setLocationRelativeTo ( null );
			this.setBounds(50, 50, 600, 600);
		}

		//Parameter constructor that calls default then sets title
		public QuestionnaireFrame(String title) {
			this();
			setTitle(title);
		}
		
		private void checkFinish()
		{
			// If all answers are populated, enable finish button
			if (answer1 != null && answer2 != null && answer3 != null && 
					answer4 != null && answer5 != null)
			{
				finishButton.setEnabled(true);
			}
			// One of the anwers is null still, make sure finish is re-disabled
			else if(finishButton.isEnabled() == true)
			{
				finishButton.setEnabled(false);
			}
		}

		@Override
		public void itemStateChanged(ItemEvent e) {
			Object source = e.getItemSelectable();

	    	if (e.getStateChange() == ItemEvent.SELECTED){

	    		if (source == yButton) 
	    		{
	    			answer2 = yButton.getText();
	    		} 
	    		else if (source == nButton)
	    		{
	    			answer2 = nButton.getText();
	    		}
	    		else if (source == cd)
	    		{
	    			checkedItems.add("Cats and Dogs");
	    			answer4 = checkedItems.toString();
	    		}
	    		else if (source == rep) 
	    		{
	    			checkedItems.add("Snakes and Lizards");
	    			answer4 = checkedItems.toString();
	    		} 
	    		else if (source == rod) 
	    		{
	    			checkedItems.add("Hamsters and Mice");
	    			answer4 = checkedItems.toString();
	    		} 
	    		else if (source == rab) 
	    		{
	    			checkedItems.add("Rabbits");
	    			answer4 = checkedItems.toString();
	    		} 
	    		else if (source == fish) 
	    		{
	    			checkedItems.add("Fish");
	    			answer4 = checkedItems.toString();
	    		}
	    		checkFinish();
	    	}

	    	// React if a check box is de-selected
	    	if (e.getStateChange() == ItemEvent.DESELECTED){
	    		if (source == cd)
		    	{
		    		checkedItems.remove("Cats and Dogs");
		    	}
		    	else if (source == rep) 
		    	{
		    		checkedItems.remove("Snakes and Lizards");
		    	} 
		    	else if (source == rod) 
		    	{
		    		checkedItems.remove("Hamsters and Mice");
		    	} 
		    	else if (source == rab) 
		    	{
		    		checkedItems.remove("Rabbits");
		    	} 
		    	else if (source == fish) 
		    	{
		    		checkedItems.remove("Fish");
		    	}
	    		
	    		//If the array of checked items is empty, set answer
	    		//back to null to disable finish button
	    		if(checkedItems.isEmpty())
	    		{
	    			answer4 = null;
	    			checkFinish();
	    		}
	    	}
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
		    Object source = e.getSource();
		    
		    if(source == cancelButton)
		    {
		    	this.setVisible(false);
		    	dispose();
		    } 
		    else if (source == finishButton) 
		    {
		    	System.out.println(q1String + " " + answer1);
		    	System.out.println(q2String + " " + answer2);
		    	System.out.println(q3String + " " + answer3 + "/10");
		    	System.out.println(q4String + " " + answer4);
		    	System.out.println(q5String + " " + answer5);
		    	this.setVisible(false);
		    	dispose();
		    }
		}

		@Override
		public void stateChanged(ChangeEvent e) {
			Object source = e.getSource();
			
			if(source == q1Spinner)
			{
				answer1 = q1Spinner.getValue().toString();
				checkFinish();
			}
			else if(source == q3Slider)
			{
				answer3 = Integer.toString(q3Slider.getValue());
				checkFinish();
			}
		}

		@Override
		public void changedUpdate(DocumentEvent arg0) {
			// Do nothing
		}

		@Override
		public void insertUpdate(DocumentEvent arg0) {
			answer5 = q5Text.getText();
			checkFinish();
		}

		@Override
		public void removeUpdate(DocumentEvent arg0) {
			if(q5Text.getText().isEmpty())
			{
				answer5 = null;
				checkFinish();
			}
			else
			{
				answer5 = q5Text.getText();
				checkFinish();
			}
		}
	}

}

//******************************************************************************