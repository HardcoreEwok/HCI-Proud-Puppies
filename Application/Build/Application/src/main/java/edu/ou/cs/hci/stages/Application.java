//******************************************************************************
// Copyright (C) 2016-2018 University of Oklahoma Board of Trustees.
//******************************************************************************
// Last modified: Sunday April 29 19:04:00 2016 by Sam Emswiler
//******************************************************************************


package edu.ou.cs.hci.stages;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import edu.ou.cs.hci.resources.Resources;


/**
 * The <CODE>Base</CODE> class.<P>
 *
 * @author  Sam Emswiler, Brennan Connolly, Austin Echols, Ashley Finley
 * @version %I%, %G%
 */


public final class Application
{
	//Create color and font objects for the application
	private static final Color porcelain = Color.decode("#F3F6F6");
	private static final Color havelockBlue = Color.decode("#6090D9");
	private static final Color cello = Color.decode("#253A5E");
	private static final Font font = new Font("Tahoma", Font.PLAIN|Font.BOLD, 11);

	private static DataTable table = new DataTable();				//Table with all values
	private static DataTable displayedTable = new DataTable();		//Table with only 4 of the values for compactness

	private static JLabel idLabel = new JLabel("ID: ");
	private static JLabel nameLabel = new JLabel("Name: ");
	private static JLabel speciesLabel = new JLabel("Species: ");
	private static JLabel breedLabel = new JLabel("Breed: ");
	private static JLabel sexLabel = new JLabel("Sex: ");
	private static JLabel ageLabel = new JLabel("Age: ");
	private static JLabel colorLabel = new JLabel("Color: ");
	private static JLabel ufaLabel = new JLabel("UFA: ");
	private static JLabel vaccineLabel = new JLabel("Vaccinated: ");
	private static JLabel fixedLabel = new JLabel("Fixed: ");
	private static JLabel descriptionLabel = new JLabel("Description: ");

	//Counts used for number of columns and rows in the data table
	private static int nCol = 0;
	private static int nRow = 0;
	
	//Global boolean used to determine if the data loaded has been altered
	private static boolean changed = false;

	//Global objects to access any additional pictures of the animal and the table data
	private static String[][] data;
	private static JButton button1 = new JButton();
	private static JButton button2 = new JButton();
	private static JButton button3 = new JButton();
	private static JButton button4 = new JButton();

	//Temporary holder of an image and string path that must be static for use, therefore declared here
	private static ImageIcon img5 = null;
	private static String currentPicPath = "";


	//**********************************************************************
	// Main
	//**********************************************************************

	public static void main(String[] args) throws FileNotFoundException
	{
		JFrame	frame = new JFrame("Proud Puppies");		//Over-arching fram
		JPanel	logInUpload = new JPanel();					
		JPanel	filterSearch = new JPanel();
		JPanel	previewOne = new JPanel();					//JPanels to house all the images
		JPanel	previewTwo = new JPanel();					//for a specific pet
		JPanel	previewThree = new JPanel();
		JPanel  previewFour = new JPanel();
		ImagePanel	bigPicture = new ImagePanel();			//Main display image
		JPanel	content = new JPanel();						//Where animal data is housed
		JPanel  toolbar = new JPanel();
		
		JScrollPane scrollTable = new JScrollPane(displayedTable);
		displayedTable.setPreferredScrollableViewportSize(table.getPreferredSize());
		displayedTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		//Create Buttons that will be activated when clicking on the preview images
		button1.setPreferredSize(new Dimension(25, 25));
		button1.setBorder(BorderFactory.createEmptyBorder());
		button1.setBorderPainted(false);
		button1.setBackground(havelockBlue);
		button1.setEnabled(false);

		button2.setPreferredSize(new Dimension(25, 25));
		button2.setBorder(BorderFactory.createEmptyBorder());
		button2.setBorderPainted(false);
		button2.setBackground(havelockBlue);
		button2.setEnabled(false);

		button3.setPreferredSize(new Dimension(25, 25));
		button3.setBorder(BorderFactory.createEmptyBorder());
		button3.setBorderPainted(false);
		button3.setBackground(havelockBlue);
		button3.setEnabled(false);

		button4.setPreferredSize(new Dimension(25, 25));
		button4.setBorder(BorderFactory.createEmptyBorder());
		button4.setBorderPainted(false);
		button4.setBackground(havelockBlue);
		button4.setEnabled(false);

		//Sets default font of entire UI
		setUIFont(font);
		
		//Setup region that houses animal-specific data
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

		content.add(idLabel);
		content.add(nameLabel);
		content.add(speciesLabel);
		content.add(breedLabel);
		content.add(sexLabel);
		content.add(ageLabel);
		content.add(colorLabel);
		content.add(ufaLabel);
		content.add(vaccineLabel);
		content.add(fixedLabel);
		content.add(descriptionLabel);

		JScrollPane contentScroll = new JScrollPane(content);
		displayedTable.setPreferredScrollableViewportSize(table.getPreferredSize());
		contentScroll.setPreferredSize(new Dimension(100,150));
		displayedTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		//Add borders to distinguish text zones
		Border bevelBorder = BorderFactory.createEtchedBorder();
		content.setBorder(bevelBorder);

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

		//Frame management
		frame.setSize(1000, 700); //width, height 
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(new GridBagLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		frame.getContentPane().add(scrollTable, c);

		c.weighty = 0.6;
		c.weightx = 1.5;
		c.gridx = 1;
		c.gridy = 0;
		c.gridheight = 8;
		c.gridwidth = 2;
		//Insets(top,left,bottom,right)
		c.insets = new Insets(10,0,0,10);
		bigPicture.setBackground(havelockBlue);
		frame.getContentPane().add(bigPicture, c);

		c.weighty = 0.05;
		c.weightx = 0.25;
		c.gridx = 4;
		c.gridy = 0;
		c.gridheight = 2;
		c.gridwidth = 1;
		//Insets(top,left,bottom,right)
		c.insets = new Insets(10,0,15,20);
		previewOne.setLayout(new BorderLayout());
		previewOne.add(button1, BorderLayout.CENTER);
		frame.getContentPane().add(previewOne, c);
		
		c.weighty = 0.4;
		c.weightx = 0.25;
		c.gridx = 4;
		c.gridy = 2;
		c.gridheight = 2;
		c.gridwidth = 1;
		//Insets(top,left,bottom,right)
		c.insets = new Insets(0,0,15,20);
		previewTwo.setLayout(new BorderLayout());
		previewTwo.add(button2, BorderLayout.CENTER);
		frame.getContentPane().add(previewTwo, c);
		
		c.weighty = 0.4;
		c.weightx = 0.25;
		c.gridx = 4;
		c.gridy = 5;
		c.gridheight = 1;
		c.gridwidth = 1;
		//Insets(top,left,bottom,right)
		c.insets = new Insets(0,0,15,20);
		previewThree.setLayout(new BorderLayout());
		previewThree.add(button3, BorderLayout.CENTER);
		frame.getContentPane().add(previewThree, c);
		
		c.weighty = 0.4;
		c.weightx = 0.25;
		c.gridx = 4;
		c.gridy = 6;
		c.gridheight = 1;
		c.gridwidth = 1;
		//Insets(top,left,bottom,right)
		c.insets = new Insets(0,0,0,20);
		previewFour.setLayout(new BorderLayout());
		previewFour.add(button4, BorderLayout.CENTER);
		frame.getContentPane().add(previewFour, c);

		c.weighty = 0.005;
		c.weightx = 0.75;
		c.gridx = 1;
		c.gridy = 8;
		c.gridwidth = 4;
		c.gridheight = 4;
		//Insets(top,left,bottom,right)
		c.insets = new Insets(10,0,20,20);
		frame.getContentPane().add(contentScroll, c);

		//Set colors
		frame.getContentPane().setBackground(havelockBlue);
		previewOne.setBackground(havelockBlue);
		previewTwo.setBackground(havelockBlue);
		previewThree.setBackground(havelockBlue);
		previewFour.setBackground(havelockBlue);
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
		scrollTable.setBackground(porcelain);
		scrollTable.setForeground(cello);
		displayedTable.setSelectionBackground(cello);
		displayedTable.setSelectionForeground(porcelain);

		//Add menu bar to frame
		createMenuBar(frame);
		
		//Define all action listeners within main window of application
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
		
		//Preview Image 1 Action Listener
		button1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				currentPicPath = table.getValueAt(displayedTable.getSelectedRow(), 11).toString();
				if(currentPicPath.equalsIgnoreCase("N/A"))
				{
					currentPicPath = "Xicon.png";
				}
				img5 = Resources.getImage("images/" + currentPicPath);
				Image img15 = img5.getImage();
				img5 = new ImageIcon(img15.getScaledInstance(610, 450, java.awt.Image.SCALE_SMOOTH));
				bigPicture.setImage(img5);
			}
		});
		
		//Preview Image 2 Action Listener
		button2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				currentPicPath = table.getValueAt(displayedTable.getSelectedRow(), 12).toString();

				if(currentPicPath.equalsIgnoreCase("N/A"))
				{
					currentPicPath = "Xicon.png";
				}

				img5 = Resources.getImage("images/" + currentPicPath);
				Image img15 = img5.getImage();
				img5 = new ImageIcon(img15.getScaledInstance(610, 450, java.awt.Image.SCALE_SMOOTH));
				bigPicture.setImage(img5);


			}
		});

		//Preview Image 3 Action Listener
		button3.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				currentPicPath = table.getValueAt(displayedTable.getSelectedRow(), 13).toString();
				if(currentPicPath.equalsIgnoreCase("N/A"))
				{
					currentPicPath = "Xicon.png";
				}
				img5 = Resources.getImage("images/" + currentPicPath);
				Image img15 = img5.getImage();
				img5 = new ImageIcon(img15.getScaledInstance(610, 450, java.awt.Image.SCALE_SMOOTH));
				bigPicture.setImage(img5);
			}
		});

		//Preview Image 4 Action Listener
		button4.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				currentPicPath = table.getValueAt(displayedTable.getSelectedRow(), 14).toString();
				if(currentPicPath.equalsIgnoreCase("N/A"))
				{
					currentPicPath = "Xicon.png";
				}
				img5 = Resources.getImage("images/" + currentPicPath);
				Image img15 = img5.getImage();
				img5 = new ImageIcon(img15.getScaledInstance(610, 450, java.awt.Image.SCALE_SMOOTH));
				bigPicture.setImage(img5);
			}
		});

		frame.setVisible(true);
		frame.setResizable(false);
		
		//Prompt to save data in table on window close if the data had been altered in the table
		frame.addWindowListener(new WindowAdapter() {
	    	public void windowClosing(WindowEvent e) 
	    	{
	    		if(changed)
				{
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog (null, "Would You Like to Save your "
							+ "changes?","Warning", dialogButton);
					
					if(dialogResult == JOptionPane.YES_OPTION)
					{
						changed = true;
						JFileChooser saveChooser = new JFileChooser();
						saveChooser.setCurrentDirectory(new File("./../../Results"));
						int retrival = saveChooser.showSaveDialog(null);

						if (retrival == JFileChooser.APPROVE_OPTION) 
						{
							try(FileWriter fw = new FileWriter(saveChooser.getSelectedFile() + ".csv")) 
							{

								for(int i = 0; i < nRow; ++i)
								{
									for(int j = 0; j < nCol; ++j)
									{
										if(j != nCol - 1)
										{
											fw.append(data[i][j]);
											fw.append(",");
										}
										else
										{
											fw.append(data[i][j]);
											fw.append("\n");
										}
									}
								}

							}
							catch (Exception ex) 
							{
								ex.printStackTrace();
							}
						}
					}
					
					else
					{
						System.out.println("'Quit' was selected.");
						frame.dispose();
					}
				}
	    		
	    	}
	    });

	}

	//Sets fonts globally
	public static void setUIFont(Font f){
		Enumeration<Object> keys = UIManager.getLookAndFeelDefaults().keys();
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
		menuBar.setOpaque(true);
		menuBar.setBackground(cello);
		menuBar.setForeground(porcelain);
		menuBar.setPreferredSize(new Dimension(987, 40));

		JMenu 	  fileMenu = new JMenu("File");
		fileMenu.setForeground(porcelain);

		JMenu 	  editMenu = new JMenu("Edit");
		editMenu.setForeground(porcelain);

		JMenu 	  helpMenu = new JMenu("Help");
		helpMenu.setForeground(porcelain);

		JMenu 	  accountMenu = new JMenu("Account");
		accountMenu.setForeground(porcelain);

		JMenuItem fileUpload = new JMenuItem("Upload");
		fileUpload.setBackground(cello);
		fileUpload.setForeground(porcelain);

		JMenuItem fileDelete = new JMenuItem("Delete");
		fileDelete.setBackground(cello);
		fileDelete.setForeground(porcelain);

		JMenuItem fileOpen = new JMenuItem("Open");
		fileOpen.setBackground(cello);
		fileOpen.setForeground(porcelain);

		JMenuItem fileSave = new JMenuItem("Save");
		fileSave.setBackground(cello);
		fileSave.setForeground(porcelain);

		JMenuItem filePrint = new JMenuItem("Print");
		filePrint.setBackground(cello);
		filePrint.setForeground(porcelain);

		JMenuItem fileShare = new JMenuItem("Share");
		fileShare.setBackground(cello);
		fileShare.setForeground(porcelain);

		JMenuItem fileQuit = new JMenuItem("Quit");
		fileQuit.setBackground(cello);
		fileQuit.setForeground(porcelain);

		JMenuItem editZoom = new JMenuItem("Zoom");
		editZoom.setBackground(cello);
		editZoom.setForeground(porcelain);

		JMenuItem editCut = new JMenuItem("Cut");
		editCut.setBackground(cello);
		editCut.setForeground(porcelain);

		JMenuItem editCopy = new JMenuItem("Copy");
		editCopy.setBackground(cello);
		editCopy.setForeground(porcelain);

		JMenuItem editPaste = new JMenuItem("Paste");
		editPaste.setBackground(cello);
		editPaste.setForeground(porcelain);

		JMenuItem adorablesSubMenu = new JMenu("Adorables");
		adorablesSubMenu.setBackground(cello);
		adorablesSubMenu.setForeground(porcelain);
		adorablesSubMenu.setOpaque(true);

		JMenuItem adorablesView = new JMenuItem("View");
		adorablesView.setBackground(cello);
		adorablesView.setForeground(porcelain);

		JMenuItem adorablesAdd = new JMenuItem("Add");
		adorablesAdd.setBackground(cello);
		adorablesAdd.setForeground(porcelain);

		JMenuItem adorablesReorder = new JMenuItem("Reorder");
		adorablesReorder.setBackground(cello);
		adorablesReorder.setForeground(porcelain);

		JMenuItem adorablesRemove = new JMenuItem("Remove");
		adorablesRemove.setBackground(cello);
		adorablesRemove.setForeground(porcelain);

		JMenuItem helpReport = new JMenuItem("Report");
		helpReport.setBackground(cello);
		helpReport.setForeground(porcelain);

		JMenuItem helpPartners = new JMenuItem("Proud Partners");
		helpPartners.setBackground(cello);
		helpPartners.setForeground(porcelain);

		JMenuItem helpContact = new JMenuItem("Contact Us");
		helpContact.setBackground(cello);
		helpContact.setForeground(porcelain);

		JMenuItem accountLogging = new JMenuItem("Log In/Out");
		accountLogging.setBackground(cello);
		accountLogging.setForeground(porcelain);

		JMenuItem accountSettings = new JMenuItem("Settings");
		accountSettings.setBackground(cello);
		accountSettings.setForeground(porcelain);
		
		JMenuItem helpAbout = new JMenuItem("About");
		helpAbout.setBackground(cello);
		helpAbout.setForeground(porcelain);

		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(helpMenu);
		menuBar.add(helpAbout);
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
		
		frame.setJMenuBar(menuBar);


		//Action Listener creation for menu bar. Mostly printing of text corresponding to what item was pressed
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

		//Open and load data into the app
		fileOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) 
			{
				String path = "";

				JFileChooser openChooser = new JFileChooser();
				openChooser.setCurrentDirectory(new File("./src/main/java/edu/ou/cs/hci/resources/data"));
				int retrival = openChooser.showOpenDialog(null);

				if (retrival == JFileChooser.APPROVE_OPTION) 
				{
					File file = openChooser.getSelectedFile();
					path = file.getAbsolutePath();

					try 
					{
						data = readCSVFile(table, path);
					} 
					catch (MalformedURLException e) 
					{
						e.printStackTrace();
					}
				}

			}});

		fileSave.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent ev) 
			{
		        System.out.println("'Save' was selected. This feature was required but we didn't feel there was an equivalent for our "
		        		+ "application. I would remove this for the final product.");
		        saveCSV();
			}
		});

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
		helpAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				
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
		
		helpAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				System.out.println("'About' was selected.");
				aboutPopup();
			}});

		fileQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) 
			{
				if(changed)
				{
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog (null, "Would You Like to Save your changes?","Warning", dialogButton);
					
					if(dialogResult == JOptionPane.YES_OPTION)
					{
						saveCSV();
					}
					
					else
					{
						System.out.println("'Quit' was selected.");
						frame.dispose();
					}
				}

				System.out.println("'Quit' was selected.");
				frame.dispose();
			}});
	}
	
	//Triggers about HTML window to popup and display information about the app
	public static void aboutPopup()
	{
		JFrame aboutPopup = new JFrame("About");			//Frame of about window
		JButton closeAbout = new JButton("Close");			//Close button for the about window
		AboutWindow aboutWindow = new AboutWindow();
		closeAbout.setPreferredSize(new Dimension(100, 30));
		aboutPopup.getContentPane().setBackground(havelockBlue);
		aboutPopup.getContentPane().setLayout(new BoxLayout(aboutPopup.getContentPane(), BoxLayout.Y_AXIS));
		aboutPopup.getContentPane().add(aboutWindow);
		closeAbout.setAlignmentX(Component.CENTER_ALIGNMENT);
		aboutPopup.getContentPane().add(closeAbout);
		
		//Action listener for close button
		closeAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				aboutPopup.dispose();
		}});
		
		aboutPopup.setSize(800, 850); //width, height 
		aboutPopup.setLocationRelativeTo(null);
		aboutPopup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		aboutPopup.setResizable(false);
		aboutPopup.setVisible(true);
	}

	//Creates toolbar and attaches it to the JMenuBar passed in as a parameter
	public static void createToolBar(JMenuBar menuBar)
	{
		JToolBar tb = new JToolBar();
		JButton upload = new JButton();
		JButton delete = new JButton();
		JButton addToAdorables = new JButton();
		JButton filter = new JButton();
		JButton search = new JButton();

		//Create ImageIcons for each icon
		ImageIcon filterIcon = Resources.getImage("icons/filter.png");
		ImageIcon uploadIcon = Resources.getImage("icons/upload.png");
		ImageIcon deleteIcon = Resources.getImage("icons/delete.png");
		ImageIcon searchIcon = Resources.getImage("icons/search.png");
		ImageIcon adorablesIcon = Resources.getImage("icons/adorables.png");

		//Resize the icons appropriately to fit the toolbar
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

		//Must set floatable to false so the toolbar can't be moved
		tb.setFloatable(false);
		tb.add(upload);
		tb.add(delete);
		tb.add(addToAdorables);
		tb.add(search);
		tb.add(filter);

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

	//Window that prompts users to log into their account
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

	//Window that allows user to search through the data for specific keywords
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

	//Window that filters results in displayed table to reflect what users filter
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
		
		//Currently Supported Species list
		String[] speciesList = {
				"Cat",
				"Dog",
				"Turtle",
				"Snake"
		};
		JComboBox<Object> species = new JComboBox<Object>(speciesList);
		species.setSelectedIndex(0);
		
		//Curently supported colors for animals
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

		//Set colors
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

	//Window that allows logged in users to upload a new animal to the collection
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

		//Set colors
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

	//Returns String Matrix of a valid CSV file
	//Takes as parameters a JTable where the CSV data will be loaded, and a String designating
	//where the CSV to load is located
	private static String[][] readCSVFile(JTable table, String path) throws MalformedURLException
	{	
		String[] COLUMNS =
		{
					"Animal ID",
					"Name",
					"Species",
					"Breed",
					"Sex",
					"Age",
					"Color",
					"UFA",
					"Vaccines",
					"Fixed",
					"Description",
					"Img 1",
					"Img 2",
					"Img 3",
					"Img 4",
		};

		// Access the CSV as a resource (you won't access them this way!!!)
		URL	url = new File(path).toURI().toURL(); //Resources.getResource("data/stadiums.csv");

		String[] tableColumns = 
			{
				"Name",
				"Species",
				"Breed",
				"Sex"
			};
		try
		{
			// Create a reader for the CSV
			InputStream		is = url.openStream();
			InputStreamReader	isr = new InputStreamReader(is);
			BufferedReader		r = new BufferedReader(isr);

			// Use the Apache Commons CSV library to read records from it
			CSVFormat			format = CSVFormat.DEFAULT;
			CSVParser			parser = CSVParser.parse(r, format);
			java.util.List<CSVRecord>	records = parser.getRecords();

			// Allocate a 2-D array to keep the rows and columns in memory
			String[][]	values = new String[records.size()][COLUMNS.length];

			nRow = records.size();
			nCol = COLUMNS.length;

			//Populate the back-end table
			for (int i = 0; i < records.size(); ++i)	// Loop over the rows...
			{
				Iterator<String>	k = records.get(i).iterator();
				int				j = 0;		// Column number
				while (j < COLUMNS.length)			// Loop over the columns...
				{
					values[i][j] = k.next();	// Grab each cell's value
					j++;
				}
				// ...and have the table show the entire value array.
				table.setModel(new DefaultTableModel(values, COLUMNS));
			}
			
			//Populate the front-end table that the user will see
			for (CSVRecord record : records)	// Loop over the rows...
			{
				Iterator<String>	k = record.iterator();
				int				i = (int)record.getRecordNumber() - 1;
				int				j = 0;		// Column number

				// Print each record to the console
				//System.out.println("***** #" + i + " *****");

				while (k.hasNext())			// Loop over the columns...
				{
					values[i][j] = k.next();	// Grab each cell's value

					//System.out.println(COLUMNS[j] + " = " + values[i][j]);
					j++;
				}
				
				String[][]	tableValues = new String[records.size()][tableColumns.length];
				
				for(int columnNum = 1; columnNum < 5; columnNum++)	//Go through columns 1 - 5
				{
					for(int rowNum = 0; rowNum < records.size(); rowNum++)
					{
						tableValues[rowNum][columnNum-1] = values[rowNum][columnNum];
					}
				}
				
				//Set model of the table that will be displayed in the app
				displayedTable.setModel(new DefaultTableModel(tableValues, tableColumns));
				
				//Sets width of the columns in the displayed table so they fit appropriately
				for(int c = 0; c < 4; c++)
				{
					TableColumn tabCol = displayedTable.getColumnModel().getColumn(c);
					switch(c)
					{
						case 0:	
							tabCol.setPreferredWidth(50);
							break;
						case 1:
							tabCol.setPreferredWidth(40);
							break;
						case 2:
							tabCol.setPreferredWidth(65);
							break;
						case 3:
							tabCol.setPreferredWidth(30);
							break;
					}
				}
			}

			//Disable auto-resizing of the table.
			displayedTable.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );

			//Close IO
			is.close();

			return values;
		}
		//CSV failed to load
		catch (Exception ex)
		{

			JOptionPane.showMessageDialog(null, "Oops. Dunno What Happened tbh. T.T");

		}

		return null;
	}

	//Extension of JTable 
	private static final class DataTable extends JTable
	{
		
		private static final long serialVersionUID = -6766548447721293844L;
		
		//Need an int to keep track of clicks in the table, as any time the table is clicked
		//on it registers as two clicks
		private static int count = 0;

		//Default constructor that calls JTable constructor
		public DataTable()
		{
			super();
		}

		//When the table is clicked on
		public void valueChanged(ListSelectionEvent e)
		{
			++count;
			this.repaint();		//Prompt table to redraw so row highlighting functions for the selected row

			//Each click counts as two, so mod by 2 to make sure value_changed executes only once.
			if(count % 2 == 0)
			{
				changed = true;		//Set changed to true so the window prompts for save on close
				
				//Populate data in the content panel according to what was selected in the table
				idLabel.setText("ID: " + table.getValueAt(this.getSelectedRow(), 0).toString());
				nameLabel.setText("Name: " + table.getValueAt(this.getSelectedRow(), 1).toString());
				speciesLabel.setText("Species: " + table.getValueAt(this.getSelectedRow(), 2).toString());
				breedLabel.setText("Breed: " + table.getValueAt(this.getSelectedRow(), 3).toString());
				sexLabel.setText("Sex: " + table.getValueAt(this.getSelectedRow(), 4).toString());
				ageLabel.setText("Age: " + table.getValueAt(this.getSelectedRow(), 5).toString());
				colorLabel.setText("Color: " + table.getValueAt(this.getSelectedRow(), 6).toString());
				ufaLabel.setText("UFA: " + table.getValueAt(this.getSelectedRow(), 7).toString());
				vaccineLabel.setText("Vaccinated: " + table.getValueAt(this.getSelectedRow(), 8).toString());
				fixedLabel.setText("Fixed: " + table.getValueAt(this.getSelectedRow(), 9).toString());
				descriptionLabel.setText("Description: " + table.getValueAt(this.getSelectedRow(), 10).toString());

				//Load data from the table for access
				data[this.getSelectedRow()][this.getSelectedColumn()] = table.getValueAt(this.getSelectedRow(), this.getSelectedColumn()).toString();

				//Load Images if there are any for each preview. If none exist for a certain preview, 
				//disable the button and set the icon to null so nothing appears
				try 
				{
					if(data[displayedTable.getSelectedRow()][11].equalsIgnoreCase("N/A"))
					{
						button1.setIcon(null);
						button1.setEnabled(false);
					}
					else
					{	
						ImageIcon img1 = Resources.getImage("images/" + data[displayedTable.getSelectedRow()][11]);
						Image img11 = img1.getImage();
						img1 = new ImageIcon(img11.getScaledInstance(125, 100, java.awt.Image.SCALE_SMOOTH));
						button1.setFocusPainted(true);
						button1.setIcon(img1);
						button1.setBorder(BorderFactory.createEmptyBorder());
						button1.setEnabled(true);
						//Prompt the first preview action listener to switch it to the main image on data load
						button1.doClick();
					}

					if(data[displayedTable.getSelectedRow()][12].equalsIgnoreCase("N/A"))
					{
						button2.setIcon(null);
						button2.setEnabled(false);
						}
					else
					{	
						ImageIcon img2 = Resources.getImage("images/" + data[displayedTable.getSelectedRow()][12]);
						Image img12 = img2.getImage();
						img2 = new ImageIcon(img12.getScaledInstance(125, 100, java.awt.Image.SCALE_SMOOTH));
						button2.setFocusPainted(true);
						button2.setIcon(img2);
						button2.setBorder(BorderFactory.createEmptyBorder());
						button2.setEnabled(true);
					}

					if(data[displayedTable.getSelectedRow()][13].equalsIgnoreCase("N/A"))
					{ 
						button3.setIcon(null);
						button3.setEnabled(false);
					}
					else
					{	
						ImageIcon img3 =  Resources.getImage("images/" + data[displayedTable.getSelectedRow()][13]);
						Image img13 = img3.getImage();
						img3 = new ImageIcon(img13.getScaledInstance(125, 100, java.awt.Image.SCALE_SMOOTH));
						button3.setFocusPainted(true);
						button3.setIcon(img3);
						button3.setBorder(BorderFactory.createEmptyBorder());
						button3.setEnabled(true);
					}

					if(data[displayedTable.getSelectedRow()][14].equalsIgnoreCase("N/A"))
					{
						button4.setIcon(null);
						button4.setEnabled(false);
					}
					else
					{	
						ImageIcon img4 = Resources.getImage("images/" + data[displayedTable.getSelectedRow()][14]);
						Image img14 = img4.getImage();
						img4 = new ImageIcon(img14.getScaledInstance(125, 100, java.awt.Image.SCALE_SMOOTH));
						button4.setFocusPainted(true);
						button4.setIcon(img4);
						button4.setBorder(BorderFactory.createEmptyBorder());
						button4.setEnabled(true);
					}

				} 
				catch (Exception ex) 
				{
					ex.printStackTrace();
				}
			}
		}
	}


	//Custom class that extends JPanel to show the main image of the app
	private static class ImagePanel extends JPanel
	{
		
		private static final long serialVersionUID = 8399724196915811626L;
		private ImageIcon image;

		//Default constructor
		public ImagePanel()
		{
			ImageIcon img1 = Resources.getImage("");
			Image img11 = img1.getImage();
			img1 = new ImageIcon(img11.getScaledInstance(610, 450, java.awt.Image.SCALE_SMOOTH));
			this.image = img1;
		}

		//Set the image displayed and re-prompts to update
		public void setImage(ImageIcon newImage)
		{
			this.image = newImage;
			this.revalidate();
			this.repaint();
		}

		@Override
		protected void paintComponent(Graphics g) 
		{
			super.paintComponent(g);
			g.drawImage(image.getImage(), 0, 0, this); // see javadoc for more info on the parameters            
		}

	}
	
	//Extention fo JPanel and HyperLinkListener to display a web page
	private static class AboutWindow extends JPanel implements HyperlinkListener
	{
		
		private static final long serialVersionUID = 2529215172260301562L;

		//Default constructor
		public AboutWindow(){
			JEditorPane	info;
			URL url = Resources.getResource("about/about.html");

			try
			{
				// Try to load the about.html file in resources
				info = new JEditorPane(url);
			}
			catch (IOException ex)
			{
				// If loading fails, use a default message
				info = new JEditorPane("text/plain", "[Loading info failed.]");
			}
			info.setEditable(false);
			info.setPreferredSize(new Dimension(800, 850));
			info.addHyperlinkListener(this);
			add(new JScrollPane(info));
		}
		
		public void	hyperlinkUpdate(HyperlinkEvent e)
		{
			if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
				Resources.openURLInSystemBrowser(e.getURL());
		}

	}
	
	//Function to save data from the table back to CSV
	public static void saveCSV()
	{
		JFileChooser saveChooser = new JFileChooser();
		//Save the CSVs in the results directory
 		saveChooser.setCurrentDirectory(new File("./../../../../../../Results"));
 		int retrieval = saveChooser.showSaveDialog(null);
 		
 		if (retrieval == JFileChooser.APPROVE_OPTION) 
 		{
 			//If the file doesn't already exist
 			if(!saveChooser.getSelectedFile().exists())
 			{
	 			try(FileWriter fw = new FileWriter(saveChooser.getSelectedFile()+".csv")) 
				{
	 				try {
	
	 			        TableModel model = table.getModel();
	 			        FileWriter csv = new FileWriter(new File(saveChooser.getSelectedFile()+".csv"));
	
	 			        for (int i = 0; i < model.getColumnCount(); i++) {
	 			            csv.write(model.getColumnName(i) + ",");
	 			        }
	
	 			        csv.write("\n");
	
	 			        for (int i = 0; i < model.getRowCount(); i++) {
	 			            for (int j = 0; j < model.getColumnCount(); j++) {
	 			                csv.write(model.getValueAt(i, j).toString() + ",");
	 			            }
	 			            csv.write("\n");
	 			        }
	
	 			        csv.close();
	 			    } catch (IOException e) {
	 			        e.printStackTrace();
	 			    }
				}
				catch (Exception ex) 
				{
					ex.printStackTrace();
				}
	 		}
 			//If the file already exists, prompt for different file name
 			else
 	 		{
 	 			System.out.println("ALREADY EXISTS");
 	 			FileExistsPopUp();
 	 		}
 		}
	}
	
	//Prompts user to overwrite current file
	public static void FileExistsPopUp()
	{
		JFrame fileExists = new JFrame("File Already Exists");
		JPanel panel = new JPanel();
		panel.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel textLable = new JLabel("File already exists. Please try again.");
		JButton submitBtn = new JButton("Close");

		fileExists.getContentPane().setBackground(havelockBlue);
		panel.setBackground(havelockBlue);
		submitBtn.setBackground(cello);
		submitBtn.setForeground(porcelain);
		submitBtn.setOpaque(true);
		submitBtn.setBorderPainted(false);
		textLable.setForeground(cello);

		//Add and group components
		fileExists.setSize(300, 700); //width, height 
		fileExists.setLocationRelativeTo(null);
		fileExists.getContentPane().setLayout(new BoxLayout(fileExists.getContentPane(), BoxLayout.Y_AXIS));
		fileExists.getContentPane().add(panel);
		panel.add(textLable);
		panel.add(submitBtn);
		fileExists.setResizable(true);
		fileExists.setVisible(true);
		fileExists.pack();
		
		//Action Listener for submit button
		submitBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				fileExists.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				fileExists.setVisible(false);
				fileExists.dispose();
			}
		});
	}
}
