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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
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
public final class Stage4
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

	

	//**********************************************************************
	// Main
	//**********************************************************************

	public static void main(String[] args) throws FileNotFoundException
	{
		String outputText = "";
		JFrame	frame = new JFrame("Proud Puppies");
		JPanel	logInUpload = new JPanel();
		JPanel	filterSearch = new JPanel();
		JPanel	bigPicture = new HelloPanel("Display");
		JPanel	smallPicPrevious = new HelloPanel("Prev.");
		JPanel	smallPicCurrent = new HelloPanel("Current");
		JPanel	smallPicNext = new HelloPanel("Next");
		JPanel	content = new HelloPanel("Content...");
		
		// Collection selection window
		DefaultListModel<String> itemListModel = new DefaultListModel<String>();
		JList<String> itemList = new JList<String>(itemListModel);
		itemListModel.addElement("Example Datum 1");
		itemListModel.addElement("Example Datum 2");
		itemListModel.addElement("Example Datum 3");
		//Add Listener to the list that prints the widget name followed by the element selected
		itemList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (!arg0.getValueIsAdjusting()) {
					System.out.println("Collection List: " + itemListModel.getElementAt(itemList.getSelectedIndex()));
				}
			}
		});
		
		//Creation of Widgets for Menu
		JButton logInOutBtn = new JButton("Log In/Out");
		JButton uploadBtn = new JButton("Upload");
		JButton filterBtn = new JButton("Filter");
		JButton searchBtn = new JButton("Search");
		logInOutBtn.setPreferredSize(new Dimension(100, 40));
		uploadBtn.setPreferredSize(new Dimension(100, 40));
		filterBtn.setPreferredSize(new Dimension(100, 40));
		searchBtn.setPreferredSize(new Dimension(100, 40));
		
		frame.setSize(1000, 700); //width, height 
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		//add the panels inside the frame
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0;
		c.weightx = 0.1;
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 1;
		logInUpload.add(logInOutBtn);
		logInUpload.add(uploadBtn);
		frame.getContentPane().add(logInUpload, c);
		
		c.weighty = 0;
		c.weightx = 0.05;
		c.gridx = 0;
		c.gridy = 1;
		c.gridheight = 1;
		filterSearch.add(searchBtn);
		filterSearch.add(filterBtn);
		frame.getContentPane().add(filterSearch, c);
		
		c.weighty = 0.9;
		c.weightx = 0.05;
		c.gridx = 0;
		c.gridy = 2;
		c.gridheight = 10;
		//Insets(top,left,bottom,right)
		c.insets = new Insets(10,15,15,15);
		frame.getContentPane().add(itemList, c);

		c.weighty = 0.6;
		c.weightx = 1.5;
		c.gridx = 1;
		c.gridy = 0;
		c.gridheight = 6;
		c.gridwidth = 3;
	    c.insets = new Insets(10,0,0,20);
		frame.getContentPane().add(bigPicture, c);
		
		c.weighty = 0.1;
		c.weightx = 0.25;
		c.gridx = 1;
		c.gridy = 6;
		c.gridheight = 2;
		c.gridwidth = 1;
	    c.insets = new Insets(0,50,3,3); 
		frame.getContentPane().add(smallPicPrevious, c);

		c.weighty = 0.1;
		c.weightx = 0.25;
		c.gridx = 2;
		c.gridy = 6;
		c.gridheight = 2;
		c.gridwidth = 1;
	    c.insets = new Insets(3,60,3,60); 
		frame.getContentPane().add(smallPicCurrent, c);

		c.weighty = 0.1;
		c.weightx = 0.25;
		c.gridx = 3;
		c.gridy = 6;
		c.gridheight = 2;
		c.gridwidth = 1;
	    c.insets = new Insets(3,3,3,70); 
		frame.getContentPane().add(smallPicNext, c);
		
		c.weighty = 0.3;
		c.weightx = 0.75;
		c.gridx = 1;
		c.gridy = 8;
		c.gridwidth = 3;
		c.gridheight = 4;
	    c.insets = new Insets(0,0,20,20);
		frame.getContentPane().add(content, c);
		
		filterBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				filterPopUp();
			}
		});
		uploadBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				UploadPopUp();
			}
		});
		
		searchBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				SearchPopUp();
			}
		});
		
		logInOutBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				LoginPopUp();
			}
		});
		frame.setVisible(true);
		frame.setResizable(false);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	    
	    frame.addWindowListener(new WindowAdapter() {
	    	public void windowClosing(WindowEvent e) 
	    	{

	    		JFileChooser saveChooser = new JFileChooser();
	    		saveChooser.setCurrentDirectory(new File("./../../../../../../Results"));
	    		int retrival = saveChooser.showSaveDialog(null);



	    		if (retrival == JFileChooser.APPROVE_OPTION) 
	    		{
	    			try(FileWriter fw = new FileWriter(saveChooser.getSelectedFile()+".txt")) 
	    			{
	    				//Put code here to write console to file
	    			}
	    			catch (Exception ex) 
	    			{
	    				ex.printStackTrace();
	    			}
	    		}

	    	}
	    });
//		frame.addWindowListener(new WindowAdapter() {
//				public void windowClosing(WindowEvent e)
//				{
//					JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
//					if(fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION )
//					{
//						File selectedFile = fc.getSelectedFile();
//						System.out.print(selectedFile.getAbsolutePath());
//						
//						//TODO: Handle creating/updating the file retrieved above
//						try
//						{
//							BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//						    String lineFromInput = in.readLine();
//						    PrintWriter out = new PrintWriter(new FileWriter(selectedFile.getAbsolutePath()));
//						    out.println(lineFromInput);
//						    out.close();
//						}
//						catch(IOException e1) 
//						{
//					    System.out.println("Error during reading/writing");
//						}
//						System.exit(0);
//					}
//				}
//			});
	}

	public static void LoginPopUp()
	{
		//Create components for Filter Pop-up
				JFrame LoginPopUp = new JFrame("Login");
				JPanel username = new JPanel();
				JPanel password = new JPanel();
				JPanel submitPanel = new JPanel();
				username.setAlignmentX(Component.CENTER_ALIGNMENT);
				password.setAlignmentX(Component.CENTER_ALIGNMENT);
				submitPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
				JLabel usernameLabel = new JLabel("Username: ");
				JLabel passwordLabel = new JLabel("Password: ");
				JTextField usernameBox = new JTextField();
				JTextField passwordBox = new JTextField();
				JButton submitBtn = new JButton("Submit");
				
				//Add and group components
				LoginPopUp.setSize(300, 700); //width, height 
				LoginPopUp.setLocationRelativeTo(null);
				LoginPopUp.getContentPane().setLayout(new BoxLayout(LoginPopUp.getContentPane(), BoxLayout.Y_AXIS));
				LoginPopUp.getContentPane().add(username);
				LoginPopUp.getContentPane().add(password);
				LoginPopUp.getContentPane().add(submitPanel);
				username.add(usernameLabel);
				username.add(usernameBox);
				usernameBox.setPreferredSize(new Dimension(200, 30));
				password.add(passwordLabel);
				password.add(passwordBox);
				passwordBox.setPreferredSize(new Dimension(200, 30));
				submitPanel.add(submitBtn);
				LoginPopUp.setResizable(true);
				LoginPopUp.setVisible(true);

				
				LoginPopUp.pack();
				submitBtn.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						
						System.out.println("Log In Data: ");
						System.out.println("Username: " + usernameBox.getText());
						System.out.println("Password: " + passwordBox.getText());
						System.out.println("");
						
						LoginPopUp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						LoginPopUp.setVisible(false);
					}
				});
	}
	
	public static void SearchPopUp()
	{
		//Create components for Filter Pop-up
				JFrame searchPopUp = new JFrame("Search...");
				JPanel search = new JPanel();
				JPanel submitPanel = new JPanel();
				search.setAlignmentX(Component.CENTER_ALIGNMENT);
				submitPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
				JLabel searchLabel = new JLabel("Search: ");
				JTextField searchBox = new JTextField();
				JButton submitBtn = new JButton("Submit");
				
				//Add and group components
				searchPopUp.setSize(300, 700); //width, height 
				searchPopUp.setLocationRelativeTo(null);
				searchPopUp.getContentPane().setLayout(new BoxLayout(searchPopUp.getContentPane(), BoxLayout.Y_AXIS));
				searchPopUp.getContentPane().add(search);
				searchPopUp.getContentPane().add(submitPanel);
				search.add(searchLabel);
				searchBox.setPreferredSize(new Dimension(200, 30));
				search.add(searchBox);
				submitPanel.add(submitBtn);
				searchPopUp.setResizable(true);
				searchPopUp.setVisible(true);

				
				searchPopUp.pack();
				submitBtn.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						
						System.out.println("Search Data: ");
						System.out.println("Search Query: " + searchBox.getText() );
						System.out.println("");
						
						searchPopUp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						searchPopUp.setVisible(false);
					}
				});
	}
	
	public static void filterPopUp()
	{
		//Create components for Filter Pop-up
				JFrame filterPopUp = new JFrame("Set Filters");
				JPanel ageQ = new JPanel();
				JPanel colorQ = new JPanel();
				JPanel genderQ = new JPanel();
				JPanel speciesQ = new JPanel();
				JPanel submitPanel = new JPanel();
				ageQ.setAlignmentX(Component.CENTER_ALIGNMENT);
				colorQ.setAlignmentX(Component.CENTER_ALIGNMENT);
				genderQ.setAlignmentX(Component.CENTER_ALIGNMENT);
				speciesQ.setAlignmentX(Component.CENTER_ALIGNMENT);
				submitPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
				JLabel ageLabel = new JLabel("Age:");
				JLabel speciesLabel = new JLabel("Species:");
				JLabel colorLabel = new JLabel("Color:");
				JLabel genderLabel = new JLabel("Gender:");
				SpinnerModel model = new SpinnerNumberModel(20, 0, 200, 1);
				JSpinner ageSpinner = new JSpinner(model);
				JRadioButton male = new JRadioButton("Male");
				JRadioButton female = new JRadioButton("Female");
				ButtonGroup radio = new ButtonGroup();
				JButton submitBtn = new JButton("Submit");
				String[] speciesList = {
				         "Cat",
				         "Dog",
				         "Turtle",
				         "Snake"
				};
				JComboBox<Object> species = new JComboBox<Object>(speciesList);
				species.setSelectedIndex(0);
				String[] colorList = {
				         "Black",
				         "Brown",
				         "Blonde",
				         "Red",
				         "White",
				         "Yellow",
				         "Purple",
				         "Green",
				         "Blue",
				         "Orange"
				};
				JComboBox<Object> colors = new JComboBox<Object>(colorList);
				species.setSelectedIndex(0);
				
				//Add and group components
				male.setActionCommand("Male");
				male.setSelected(true);
				female.setActionCommand("Female");
				radio.add(male);
				radio.add(female);
				filterPopUp.setSize(300, 700); //width, height 
				filterPopUp.setLocationRelativeTo(null);
				filterPopUp.getContentPane().setLayout(new BoxLayout(filterPopUp.getContentPane(), BoxLayout.Y_AXIS));
				filterPopUp.getContentPane().add(speciesQ);
				filterPopUp.getContentPane().add(colorQ);
				filterPopUp.getContentPane().add(genderQ);
				filterPopUp.getContentPane().add(ageQ);
				filterPopUp.getContentPane().add(submitPanel);
				speciesQ.add(speciesLabel);
				speciesQ.add(species);
				colorQ.add(colorLabel);
				colorQ.add(colors);
				ageQ.add(ageLabel);
				ageQ.add(ageSpinner);
				genderQ.add(genderLabel);
				genderQ.add(male);
				genderQ.add(female);
				submitPanel.add(submitBtn);
				filterPopUp.setResizable(false);
				filterPopUp.setVisible(true);

				
				filterPopUp.pack();
				submitBtn.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						
						System.out.println("Filter Data: ");
						System.out.println("Species: " + speciesList[species.getSelectedIndex()].toString());
						System.out.println("Color: " + colorList[colors.getSelectedIndex()].toString());
						System.out.println("Age: " + ageSpinner.getValue());
						System.out.println("Gender: " + radio.getSelection().getActionCommand());
						System.out.println("");
						
						filterPopUp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						filterPopUp.setVisible(false);
					}
				});
	}
	
	
	public static void UploadPopUp()
	{
		//Create components for Filter Pop-up
				JFrame uploadPopUp = new JFrame("Upload");
				JPanel nameQ = new JPanel();
				JPanel ageQ = new JPanel();
				JPanel colorQ = new JPanel();
				JPanel genderQ = new JPanel();
				JPanel speciesQ = new JPanel();
				JPanel description = new JPanel();
				JPanel submitPanel = new JPanel();
				nameQ.setAlignmentX(Component.CENTER_ALIGNMENT);
				ageQ.setAlignmentX(Component.CENTER_ALIGNMENT);
				colorQ.setAlignmentX(Component.CENTER_ALIGNMENT);
				genderQ.setAlignmentX(Component.CENTER_ALIGNMENT);
				speciesQ.setAlignmentX(Component.CENTER_ALIGNMENT);
				description.setAlignmentX(Component.CENTER_ALIGNMENT);
				submitPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
				JLabel nameLabel = new JLabel("Animal Name:");
				JLabel ageLabel = new JLabel("Age:");
				JLabel speciesLabel = new JLabel("Species:");
				JLabel colorLabel = new JLabel("Color:");
				JLabel genderLabel = new JLabel("Gender:");
				JLabel descriptionLabel = new JLabel("Description:");
				JTextField nameBox = new JTextField();
				JTextArea descriptionBox = new JTextArea();
				descriptionBox.setText("Enter description of animal...");
				descriptionBox.setPreferredSize(new Dimension(120, 200));
				descriptionBox.setEditable(true);
				descriptionBox.setLineWrap(true);
				descriptionBox.setWrapStyleWord(true);
				SpinnerModel model = new SpinnerNumberModel(20, 0, 200, 1);
				JSpinner ageSpinner = new JSpinner(model);
				JRadioButton male = new JRadioButton("Male");
				JRadioButton female = new JRadioButton("Female");
				ButtonGroup radio = new ButtonGroup();
				JButton submitBtn = new JButton("Submit");
				JScrollPane scroll = new JScrollPane(descriptionBox);
				String[] speciesList = {
				         "Cat",
				         "Dog",
				         "Turtle",
				         "Snake"
				};
				JComboBox<Object> species = new JComboBox<Object>(speciesList);
				species.setSelectedIndex(0);
				String[] colorList = {
				         "Black",
				         "Brown",
				         "Blonde",
				         "Red",
				         "White",
				         "Yellow",
				         "Purple",
				         "Green",
				         "Blue",
				         "Orange"
				};
				JComboBox<Object> colors = new JComboBox<Object>(colorList);
				species.setSelectedIndex(0);
				
				//Add and group components
				nameBox.setPreferredSize(new Dimension(150,30));
				male.setActionCommand("Male");
				male.setSelected(true);
				female.setActionCommand("Female");
				radio.add(male);
				radio.add(female);
				uploadPopUp.setSize(300, 700); //width, height 
				uploadPopUp.setLocationRelativeTo(null);
				uploadPopUp.getContentPane().setLayout(new BoxLayout(uploadPopUp.getContentPane(), BoxLayout.Y_AXIS));
				uploadPopUp.getContentPane().add(nameQ);
				uploadPopUp.getContentPane().add(speciesQ);
				uploadPopUp.getContentPane().add(colorQ);
				uploadPopUp.getContentPane().add(genderQ);
				uploadPopUp.getContentPane().add(ageQ);
				uploadPopUp.getContentPane().add(description);
				uploadPopUp.getContentPane().add(scroll);
				uploadPopUp.getContentPane().add(submitPanel);
				nameQ.add(nameLabel);
				nameQ.add(nameBox);
				speciesQ.add(speciesLabel);
				speciesQ.add(species);
				colorQ.add(colorLabel);
				colorQ.add(colors);
				ageQ.add(ageLabel);
				ageQ.add(ageSpinner);
				genderQ.add(genderLabel);
				genderQ.add(male);
				genderQ.add(female);
				description.add(descriptionLabel);
				description.add(scroll);
				scroll.setViewportView(descriptionBox);
				submitPanel.add(submitBtn);
				uploadPopUp.setResizable(false);
				uploadPopUp.setVisible(true);

				
				uploadPopUp.pack();
				submitBtn.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						
						System.out.println("Upload Data: ");
						System.out.println("Animal Name: " + nameBox.getText());
						System.out.println("Species: " + speciesList[species.getSelectedIndex()].toString());
						System.out.println("Color: " + colorList[colors.getSelectedIndex()].toString());
						System.out.println("Age: " + ageSpinner.getValue());
						System.out.println("Gender: " + radio.getSelection().getActionCommand());
						System.out.println("Description: " + descriptionBox.getText());
						System.out.println("");
						
						uploadPopUp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						uploadPopUp.setVisible(false);
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
