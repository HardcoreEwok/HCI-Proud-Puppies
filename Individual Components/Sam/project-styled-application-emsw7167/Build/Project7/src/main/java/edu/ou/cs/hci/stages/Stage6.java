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
import java.io.FileNotFoundException;
import java.util.Enumeration;
import java.util.Map.Entry;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.FontUIResource;

import edu.ou.cs.hci.resources.Resources;


//******************************************************************************

/**
 * The <CODE>Base</CODE> class.<P>
 *
 * @author  Chris Weaver
 * @version %I%, %G%
 */
public final class Stage6
{
	private static final Font	FONT =
		new Font(Font.SERIF, Font.ITALIC, 20);
	private static final Color	FILL_COLOR = Color.WHITE;
	private static final Color	EDGE_COLOR = Color.BLACK;
	
	private static final Color porcelain = Color.decode("#F3F6F6");
	private static final Color havelockBlue = Color.decode("#6090D9");
	private static final Color cello = Color.decode("#253A5E");
	private static final Color viking = Color.decode("#76b0db");
	private static final Font font = new Font("Tahoma", Font.PLAIN|Font.BOLD, 11);

	//**********************************************************************
	// Main
	//**********************************************************************

	public static void main(String[] args) throws FileNotFoundException
	{
		
		setUIFont(font);

		JFrame	frame = new JFrame("Proud Puppies");
		JPanel	logInUpload = new JPanel();
		JPanel	filterSearch = new JPanel();
		JPanel	bigPicture = new HelloPanel("Display");
		JPanel	previewOne = new HelloPanel("P1");
		JPanel	previewTwo = new HelloPanel("P2");
		JPanel	previewThree = new HelloPanel("P3");
		JPanel  previewFour = new HelloPanel("P4");
		JPanel	content = new HelloPanel("Content...");
		JPanel  toolbar = new JPanel();
		
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
		
		//Add borders to distinguish text zones
		Border bevelBorder = BorderFactory.createEtchedBorder();
		content.setBorder(bevelBorder);
		itemList.setBorder(bevelBorder);
		
		//Creation of Widgets for Menu
		JButton logInOutBtn = new JButton("Log In/Out");
		JButton uploadBtn = new JButton("Upload");
		JButton filterBtn = new JButton("Filter");
		JButton searchBtn = new JButton("Search");
		toolbar.setAlignmentX(Component.LEFT_ALIGNMENT);
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
		c.weighty = 0.05;
		c.weightx = 0.05;
		c.gridx = 0;
		c.gridy = 0;
		//Insets(top,left,bottom,right)
		c.insets = new Insets(10,0,0,0);
		logInUpload.add(logInOutBtn);
		logInUpload.add(uploadBtn);
		frame.getContentPane().add(logInUpload, c);
		
		c.weighty = 0.05;
		c.weightx = 0.05;
		c.gridx = 0;
		c.gridy = 1;
		//Insets(top,left,bottom,right)
		filterSearch.add(searchBtn);
		filterSearch.add(filterBtn);
		frame.getContentPane().add(filterSearch, c);
		
		c.weighty = 11;
		c.weightx = 0.05;
		c.gridx = 0;
		c.gridy = 2;
		c.gridheight = 11;
		//Insets(top,left,bottom,right)
		c.insets = new Insets(10,15,20,15);
		frame.getContentPane().add(itemList, c);

		c.weighty = 0.6;
		c.weightx = 1.5;
		c.gridx = 1;
		c.gridy = 0;
		c.gridheight = 8;
		c.gridwidth = 2;
		//Insets(top,left,bottom,right)
	    c.insets = new Insets(10,0,0,10);
		frame.getContentPane().add(bigPicture, c);
		
		c.weighty = 0.05;
		c.weightx = 0.25;
		c.gridx = 4;
		c.gridy = 0;
		c.gridheight = 2;
		c.gridwidth = 1;
		//Insets(top,left,bottom,right)
	    c.insets = new Insets(10,0,15,20);
		frame.getContentPane().add(previewOne, c);

		c.weighty = 0.4;
		c.weightx = 0.25;
		c.gridx = 4;
		c.gridy = 2;
		c.gridheight = 2;
		c.gridwidth = 1;
		//Insets(top,left,bottom,right)
	    c.insets = new Insets(0,0,15,20);
		frame.getContentPane().add(previewTwo, c);

		c.weighty = 0.4;
		c.weightx = 0.25;
		c.gridx = 4;
		c.gridy = 5;
		c.gridheight = 1;
		c.gridwidth = 1;
		//Insets(top,left,bottom,right)
	    c.insets = new Insets(0,0,15,20);
		frame.getContentPane().add(previewThree, c);
		
		c.weighty = 0.4;
		c.weightx = 0.25;
		c.gridx = 4;
		c.gridy = 6;
		c.gridheight = 1;
		c.gridwidth = 1;
		//Insets(top,left,bottom,right)
	    c.insets = new Insets(0,0,0,20);
		frame.getContentPane().add(previewFour, c);
		
		c.weighty = 0.6;
		c.weightx = 0.75;
		c.gridx = 1;
		c.gridy = 8;
		c.gridwidth = 4;
		c.gridheight = 4;
		//Insets(top,left,bottom,right)
	    c.insets = new Insets(10,0,20,20);
		frame.getContentPane().add(content, c);
		
		frame.getContentPane().setBackground(havelockBlue);
		logInUpload.setBackground(havelockBlue);
		filterSearch.setBackground(havelockBlue);
		logInOutBtn.setBackground(cello);
		logInOutBtn.setForeground(porcelain);
		uploadBtn.setBackground(cello);
		uploadBtn.setForeground(porcelain);
		filterBtn.setBackground(cello);
		filterBtn.setForeground(porcelain);
		searchBtn.setBackground(cello);
		searchBtn.setForeground(porcelain);
		itemList.setBackground(porcelain);
		itemList.setForeground(cello);
		itemList.setSelectionBackground(viking);
		
		createMenuBar(frame);
		
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
	    
	}
	
	//Sets fonts globally
	public static void setUIFont(Font f){
		Enumeration keys = UIManager.getLookAndFeelDefaults().keys();
		while(keys.hasMoreElements()){
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if(value instanceof FontUIResource)
				UIManager.put(key,f);
		}
	}
	
	public static void createMenuBar(JFrame frame)
	{
		JMenuBar  menuBar = new JMenuBar();
		menuBar.setPreferredSize(new Dimension(987, 40));
		JMenu 	  fileMenu = new JMenu("File");
		JMenu 	  editMenu = new JMenu("Edit");
		JMenu 	  helpMenu = new JMenu("Help");
		JMenu 	  accountMenu = new JMenu("Account");
		JMenuItem fileUpload = new JMenuItem("Upload");
		JMenuItem fileDelete = new JMenuItem("Delete");
		JMenuItem fileOpen = new JMenuItem("Open");
		JMenuItem fileSave = new JMenuItem("Save");
		JMenuItem filePrint = new JMenuItem("Print");
		JMenuItem fileShare = new JMenuItem("Share");
		JMenuItem fileQuit = new JMenuItem("Quit");
		JMenuItem editZoom = new JMenuItem("Zoom");
		JMenuItem editCut = new JMenuItem("Cut");
		JMenuItem editCopy = new JMenuItem("Copy");
		JMenuItem editPaste = new JMenuItem("Paste");
		JMenu 	  adorablesSubMenu = new JMenu("Adorables");
		JMenuItem adorablesView = new JMenuItem("View");
		JMenuItem adorablesAdd = new JMenuItem("Add");
		JMenuItem adorablesReorder = new JMenuItem("Reorder");
		JMenuItem adorablesRemove = new JMenuItem("Remove");
		JMenuItem helpReport = new JMenuItem("Report");
		JMenuItem helpPartners = new JMenuItem("Proud Partners");
		JMenuItem helpContact = new JMenuItem("Contact Us");
		JMenuItem accountLogging = new JMenuItem("Log In/Out");
		JMenuItem accountSettings = new JMenuItem("Settings");
		
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(helpMenu);
		menuBar.add(Box.createHorizontalGlue());
		menuBar.add(accountMenu);
		createToolBar(menuBar);
		
		fileMenu.add(fileUpload);
		fileMenu.add(fileDelete);
		fileMenu.add(fileOpen);
		fileMenu.add(fileSave);
		fileMenu.add(filePrint);
		fileMenu.add(fileShare);
		fileMenu.addSeparator();
		fileMenu.add(fileQuit);
		editMenu.add(editZoom);
		editMenu.addSeparator();
		editMenu.add(editCut);
		editMenu.add(editCopy);
		editMenu.add(editPaste);
		editMenu.addSeparator();
		editMenu.add(adorablesSubMenu);
		
		adorablesSubMenu.add(adorablesView);
		adorablesSubMenu.add(adorablesAdd);
		adorablesSubMenu.add(adorablesReorder);
		adorablesSubMenu.add(adorablesRemove);
		
		helpMenu.add(helpReport);
		helpMenu.add(helpPartners);
		helpMenu.add(helpContact);
		
		accountMenu.add(accountLogging);
		accountMenu.add(accountSettings);
		
		menuBar.setOpaque(true);
		menuBar.setBackground(cello);
		menuBar.setForeground(porcelain);
		fileMenu.setForeground(porcelain);
		fileMenu.setBackground(cello);
		editMenu.setForeground(porcelain);
		helpMenu.setForeground(porcelain);
		accountMenu.setForeground(porcelain);
		
		frame.setJMenuBar(menuBar);
		
		fileUpload.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent ev) {
		        System.out.println("'Upload' was selected. This selection would bring up a pop-up to prompt the "
		        		+ "user to enter the necessary info to upload a new animal.");
		      }});
		
		fileDelete.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent ev) {
		        System.out.println("'Delete' was selected. This selection would bring up a pop-up to confirm if the "
		        		+ "user wants to delete the current animal or inform them this animal can not be deleted because "
		        		+ "they were not the uploader.");
		      }});
		
		fileOpen.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent ev) {
		        System.out.println("'Open' was selected. This feature was required but we didn't feel there was an equivalent for our "
		        		+ "application. I would remove this for the final product.");
		      }});
		
		fileSave.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent ev) {
		        System.out.println("'Save' was selected. This feature was required but we didn't feel there was an equivalent for our "
		        		+ "application. I would remove this for the final product.");
		      }});
		
		filePrint.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent ev) {
		        System.out.println("'Print' was selected. This selection would initiate a print dialog box to print the current application "
		        		+ "frame in its entirety.");
		      }});
		
		fileShare.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent ev) {
		        System.out.println("'Share' was selected. This selection would bring up a pop-up to prompt the user "
		        		+ "to enter an email address to send a link the applicaiton that would open on the current application frame.");
		      }});
		
		fileShare.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent ev) {
		       //TODO: Implement 
		      }});
		
		editZoom.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent ev) {
		        System.out.println("'Zoom' was selected. This selection would zoom in on the window by 20%. To return to normal viewing size "
		        		+ "the user would have to zoom to 80% larger then it would automatically circle back to 100% viewing");
		      }});
		
		editCut.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent ev) {
		        System.out.println("'Cut' was selected. I feel this selection is irrelevent since you can't actually edit a normal "
		        		+ "viewing page in this app. I would remove this for the final product.");
		      }});
		
		editCopy.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent ev) {
		        System.out.println("'Copy' was selected. This selection would copy any currently highlighted text on the screen to "
		        		+ "the users clipboard.");
		      }});
		
		editPaste.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent ev) {
		        System.out.println("'Paste' was selected. I feel this selection is irrelevent since you can't actually edit a normal "
		        		+ "viewing page in this app. I would remove this for the final product.");
		      }});
		
		adorablesView.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent ev) {
		        System.out.println("'View' was selected from the Adorables sub menu. This selection would load the users "
		        		+ "adorables list into the side view panel for selection.");
		      }});
		
		adorablesAdd.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent ev) {
		        System.out.println("'Add' was selected from the Adorables sub menu. This selection would add the current animal currently "
		        		+ "in the main view panel to the users adorables list.");
		      }});
		
		adorablesReorder.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent ev) {
		        System.out.println("'Reorder' was selected from the Adorables sub menu. This selection would open a pop-up menu that "
		        		+ "would allow the user to reorder the animals currently saved in their adorables list.");
		      }});
		
		adorablesRemove.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent ev) {
		        System.out.println("'Remove' was selected from the Adorables sub menu. This selection would open a pop-up menu that "
		        		+ "would allow the user to delete single or multiple animals currently saved in their adorables list.");
		      }});
		
		helpReport.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent ev) {
		        System.out.println("'Report' was selected. This selection would open a pop-up menu that "
		        		+ "would allow the users to report bugs or major issues in the program. These would typically get higher priority than "
		        		+ "suggestions from 'Contact' submissions.");
		      }});
		
		helpPartners.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent ev) {
		        System.out.println("'Proud Partners' was selected. This selection would open a pop-up menu that would link to a "
		        		+ "page listing partners of the applicaiton such a animal rescues and adoption agencies.");
		      }});
		
		helpContact.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent ev) {
		        System.out.println("'Contact' was selected. This selection would open a pop-up menu that would provide a text area "
		        		+ "where application users could leave feedback, questions, and suggestions. There would also be a text field "
		        		+ "where the user could choose to leave an email address for a response if applicable");
		      }});
		
		accountLogging.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent ev) {
		        System.out.println("'Log In/Out' was selected. This selection would open a pop-up menu that prompt a user to log in with "
		        		+ "user name and password or (if already logged in) display a confirmation of logging out.");
		      }});
		
		accountSettings.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent ev) {
		        System.out.println("'Settings' was selected. This selection would open a pop-up menu that prompt a user to update any "
		        		+ "previously set account settings such as display name, email, etc.");
		      }});
		
		fileQuit.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent ev) {
		        System.out.println("'Quit' was selected.");
		        frame.dispose();
		      }});
	}
	
	public static void createToolBar(JMenuBar menuBar)
	{
		JToolBar tb = new JToolBar();
		JButton upload = new JButton();
		JButton delete = new JButton();
		JButton addToAdorables = new JButton();
		JButton filter = new JButton();
		JButton search = new JButton();
		
		ImageIcon filterIcon = Resources.getImage("icons/filter.png");
	    ImageIcon uploadIcon = Resources.getImage("icons/upload.png");
	    ImageIcon deleteIcon = Resources.getImage("icons/delete.png");
	    ImageIcon searchIcon = Resources.getImage("icons/search.png");
	    ImageIcon adorablesIcon = Resources.getImage("icons/adorables.png");
	    
	    
	    //For Resizing the icons
	    Image filterImage = filterIcon.getImage();
	    Image uploadImage = uploadIcon.getImage();
	    Image deleteImage = deleteIcon.getImage();
	    Image searchImage = searchIcon.getImage();
	    Image adorablesImage = adorablesIcon.getImage();
		filterIcon = new ImageIcon(filterImage.getScaledInstance(25, 20, java.awt.Image.SCALE_SMOOTH));
		uploadIcon = new ImageIcon(uploadImage.getScaledInstance(25, 20, java.awt.Image.SCALE_SMOOTH));
		deleteIcon = new ImageIcon(deleteImage.getScaledInstance(25, 20, java.awt.Image.SCALE_SMOOTH));
		searchIcon = new ImageIcon(searchImage.getScaledInstance(25, 20, java.awt.Image.SCALE_SMOOTH));
		adorablesIcon = new ImageIcon(adorablesImage.getScaledInstance(25, 20, java.awt.Image.SCALE_SMOOTH));
		
	    upload.setIcon(uploadIcon);
	    delete.setIcon(deleteIcon);
	    filter.setIcon(filterIcon);
	    search.setIcon(searchIcon);
	    addToAdorables.setIcon(adorablesIcon);
		
	    tb.setFloatable(false);
		tb.add(upload);
		tb.add(delete);
		tb.add(addToAdorables);
		tb.add(search);
	    tb.add(filter);
	    
		Color cello = Color.decode("#253A5E");
	    upload.setBackground(cello);
	    delete.setBackground(cello);
	    addToAdorables.setBackground(cello);
	    filter.setBackground(cello);
	    search.setBackground(cello);
	    tb.setBackground(cello);

		menuBar.add(tb);
		
		upload.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent ev) {
		        System.out.println("'Upload' icon was selected. This selection would perform the same action as the corresponding menu item.");
		      }});
		
		delete.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent ev) {
		        System.out.println("'Delete' icon was selected. This selection would perform the same action as the corresponding menu item.");
		      }});
		
		search.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent ev) {
		        System.out.println("'Search' icon was selected. This selection would perform the same action as the corresponding menu item.");
		      }});
		
		filter.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent ev) {
		        System.out.println("'Filter' icon was selected. This selection would perform the same action as the corresponding menu item.");
		      }});
		
		addToAdorables.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent ev) {
		        System.out.println("'Add to Adorables' icon was selected. This selection would perform the same action as the corresponding menu item.");
		      }});
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
				
				LoginPopUp.getContentPane().setBackground(havelockBlue);
				username.setBackground(havelockBlue);
				password.setBackground(havelockBlue);
				submitPanel.setBackground(havelockBlue);
				submitBtn.setBackground(cello);
				submitBtn.setForeground(porcelain);
				usernameBox.setBackground(porcelain);
				passwordBox.setBackground(porcelain);
				usernameLabel.setForeground(cello);
				passwordLabel.setForeground(cello);
				
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
				
				search.setBackground(havelockBlue);
				submitPanel.setBackground(havelockBlue);
				searchLabel.setForeground(cello);
				searchBox.setBackground(porcelain);
				searchBox.setForeground(cello);
				submitBtn.setBackground(cello);
				submitBtn.setForeground(porcelain);
				
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
				
				
				ageQ.setBackground(havelockBlue);
				colorQ.setBackground(havelockBlue);
				genderQ.setBackground(havelockBlue);
				speciesQ.setBackground(havelockBlue);
				submitPanel.setBackground(havelockBlue);
				submitPanel.setForeground(cello);
				ageLabel.setForeground(cello);
				colorLabel.setForeground(cello);
				genderLabel.setForeground(cello);
				speciesLabel.setForeground(cello);
				colors.setBackground(porcelain);
				colors.setForeground(cello);
				ageSpinner.setBackground(porcelain);
				ageSpinner.setForeground(cello);
				male.setBackground(havelockBlue);
				male.setForeground(cello);
				female.setBackground(havelockBlue);
				female.setForeground(cello);
				submitBtn.setBackground(cello);
				submitBtn.setForeground(porcelain);
				species.setBackground(porcelain);
				species.setForeground(cello);
				colors.setBackground(porcelain);
				colors.setForeground(cello);
				
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
				colors.setSelectedIndex(0);
				
				
				nameQ.setBackground(havelockBlue);
				ageQ.setBackground(havelockBlue);
				colorQ.setBackground(havelockBlue);
				genderQ.setBackground(havelockBlue);
				speciesQ.setBackground(havelockBlue);
				description.setBackground(havelockBlue);
				submitPanel.setBackground(havelockBlue);
				nameLabel.setForeground(cello);
				ageLabel.setForeground(cello);
				genderLabel.setForeground(cello);
				speciesLabel.setForeground(cello);
				descriptionLabel.setForeground(cello);
				nameBox.setBackground(porcelain);
				nameBox.setForeground(cello);
				descriptionBox.setBackground(porcelain);
				descriptionBox.setForeground(cello);
				ageSpinner.setBackground(porcelain);
				ageSpinner.setForeground(cello);
				male.setBackground(havelockBlue);
				male.setForeground(cello);
				female.setBackground(havelockBlue);
				female.setForeground(cello);
				submitBtn.setBackground(cello);
				submitBtn.setForeground(porcelain);
				species.setBackground(porcelain);
				species.setForeground(cello);
				colors.setForeground(cello);
				colors.setBackground(porcelain);
				
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
				g.fillRect(0, 0, 1000, 1000);
			}

			if (EDGE_COLOR != null)
			{
				g.setColor(EDGE_COLOR);
				//g.drawRect(10, 10, 1000, 1000);
				g.setFont(new Font("Tahoma", Font.ITALIC, 24));
				g.drawString(message, r.x + 2, r.y + fm.getMaxAscent());
			}
		}
	}
}

//******************************************************************************
