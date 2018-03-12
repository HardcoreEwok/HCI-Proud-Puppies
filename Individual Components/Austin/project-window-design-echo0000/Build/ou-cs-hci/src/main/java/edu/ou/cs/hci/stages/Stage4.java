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
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;  

//******************************************************************************

/**
 * The <CODE>BuildTest</CODE> class.<P>
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

		JFrame			frame = new JFrame();
		JPanel			menu = new JPanel();
		JPanel			bigPicture = new JPanel();
		JPanel			smallPicPrevious = new JPanel();
		JPanel			smallPicNext = new JPanel();
		JPanel			content = new JPanel();
		
		JButton filter = new JButton("Filter    ");
		JButton upload = new JButton("Upload");
		
		menu.setBackground(Color.WHITE);
		bigPicture.setBackground(Color.WHITE);
		smallPicPrevious.setBackground(Color.WHITE);
		smallPicNext.setBackground(Color.WHITE);
		content.setBackground(Color.WHITE);
		
		JTextArea infoText = new JTextArea();
		JScrollPane infoScroll = new JScrollPane(infoText);
		infoText.setWrapStyleWord(true);
		infoText.setLineWrap(true);
		
		BoxLayout boxLayout = new BoxLayout(menu, BoxLayout.Y_AXIS);
		menu.setLayout(boxLayout);
		menu.add(upload);
		menu.add(Box.createRigidArea(new Dimension(5,5)));
		menu.add(filter);
		menu.add(Box.createRigidArea(new Dimension(5,5)));
		menu.add(infoScroll);
		
		bigPicture.add(new JLabel("BIG PICTURE"));
		smallPicPrevious.add(new JLabel("PREV PIC"));
		smallPicNext.add(new JLabel("NEXT PIC"));
		content.add(new JLabel("CONTENT"));
		
		frame.setSize(1000, 700); //width, height 
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//add the panel inside the frame
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.1;
		c.weightx = 0.1;
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 6;
		c.insets = new Insets(3,3,3,3);
		frame.getContentPane().add(menu, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.1;
		c.weightx = 0.6;
		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = 4;
		c.gridheight = 1;
		frame.getContentPane().add(content, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.weighty = .3;
		c.weightx = .6;
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 4;
		c.gridheight = 3;
		frame.getContentPane().add(bigPicture, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.1;
		c.weightx = 0.6;
		c.gridx = 2;
		c.gridy = 3;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.insets = new Insets(3,150,3,3);
		frame.getContentPane().add(smallPicPrevious, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.1;
		c.weightx = 0.6;
		c.gridx = 3;
		c.gridy = 3;
		c.insets = new Insets(3,3,3,150);
		frame.getContentPane().add(smallPicNext, c);
		
		frame.addWindowListener(new WindowAdapter() 
		{
				public void windowClosing(WindowEvent e) 
				{
					System.exit(0);
				}
			});
			
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//------------------------------------------------------------------------NEW FRAME (Filter Frame)------------------------------------------------------------------------------------\\
		
		JFrame filterFrame = new JFrame("Filter");
		
		filterFrame.setSize(320, 500); //width, height 
		filterFrame.setLocationRelativeTo(null);
		
		JPanel filterPanel = new JPanel();
		
		// BoxLayout boxLayout2 = new BoxLayout(filterPanel, BoxLayout.Y_AXIS);
		// filterPanel.setLayout(boxLayout2);
		
		String[] animalList = { "Bird", "Cat", "Dog", "Rabbit", "Pig", "Cow", "Snake", "Lizard", "Chicken", "Monkey"};
		JComboBox animalSelect = new JComboBox(animalList);
		
		ArrayList<String> ageList = new ArrayList<String>();
		
		for(int i = 0; i <= 100; ++i)
		{
			ageList.add(String.valueOf(i));
		}
		
		JComboBox ageSelect = new JComboBox(ageList.toArray());
		
		String[] colorList = { "Black", "White", "Brown", "Gold", "Green", "Yellow", "Purple", "Blue", "Red", "Orange"};
		JComboBox colorSelect = new JComboBox(colorList);
		
		String[] sexList = {"Female", "Male"};
		JComboBox sexSelect = new JComboBox(sexList);
		
		String[] yesNo = {"Yes", "No"};
		JComboBox adoptSelect = new JComboBox(yesNo);
		
		String[] fixedList = {"Spay", "Neuter", "No"};
		JComboBox fixedSelect = new JComboBox(fixedList);
		
		filterPanel.setLayout(new GridBagLayout());
		GridBagConstraints c2 = new GridBagConstraints();
		
		
		JLabel typeAnimal = new JLabel("Type of Animal");
		JLabel age = new JLabel("Age");
		JLabel color = new JLabel("Color");
		JLabel adoptLabel = new JLabel("Up for Adoption?");
		JLabel fixedLabel = new JLabel("Fixed");
		JLabel sex = new JLabel("Sex");
		
		c2.fill = GridBagConstraints.CENTER;
		c2.weighty = .1;
		c2.weightx = .1;
		c2.gridx = 0;
		c2.gridy = 0;
		c2.gridwidth = 1;
		c2.gridheight = 1;
		filterPanel.add(typeAnimal, c2);
		
		c2.fill = GridBagConstraints.CENTER;
		c2.weighty = .1;
		c2.weightx = .1;
		c2.gridx = 0;
		c2.gridy = 1;
		c2.gridwidth = 1;
		c2.gridheight = 1;
		filterPanel.add(animalSelect, c2);
		
		c2.fill = GridBagConstraints.CENTER;
		c2.weighty = .1;
		c2.weightx = .1;
		c2.gridx = 0;
		c2.gridy = 2;
		c2.gridwidth = 1;
		c2.gridheight = 1;
		filterPanel.add(age, c2);
		
		c2.fill = GridBagConstraints.CENTER;
		c2.weighty = .1;
		c2.weightx = .1;
		c2.gridx = 0;
		c2.gridy = 3;
		c2.gridwidth = 1;
		c2.gridheight = 1;
		filterPanel.add(ageSelect, c2);
		
		c2.fill = GridBagConstraints.CENTER;
		c2.weighty = .1;
		c2.weightx = .1;
		c2.gridx = 0;
		c2.gridy = 4;
		c2.gridwidth = 1;
		c2.gridheight = 1;
		filterPanel.add(color, c2);
		
		c2.fill = GridBagConstraints.CENTER;
		c2.weighty = .1;
		c2.weightx = .1;
		c2.gridx = 0;
		c2.gridy = 5;
		c2.gridwidth = 1;
		c2.gridheight = 1;
		filterPanel.add(colorSelect, c2);
		
		c2.fill = GridBagConstraints.CENTER;
		c2.weighty = .1;
		c2.weightx = .1;
		c2.gridx = 0;
		c2.gridy = 6;
		c2.gridwidth = 1;
		c2.gridheight = 1;
		filterPanel.add(sex, c2);
		
		c2.fill = GridBagConstraints.CENTER;
		c2.weighty = .1;
		c2.weightx = .1;
		c2.gridx = 0;
		c2.gridy = 7;
		c2.gridwidth = 1;
		c2.gridheight = 1;
		filterPanel.add(sexSelect, c2);
		
		c2.fill = GridBagConstraints.CENTER;
		c2.weighty = .1;
		c2.weightx = .1;
		c2.gridx = 0;
		c2.gridy = 8;
		c2.gridwidth = 1;
		c2.gridheight = 1;
		filterPanel.add(adoptLabel, c2);
		
		c2.fill = GridBagConstraints.CENTER;
		c2.weighty = .1;
		c2.weightx = .1;
		c2.gridx = 0;
		c2.gridy = 9;
		c2.gridwidth = 1;
		c2.gridheight = 1;
		filterPanel.add(adoptSelect, c2);
		
		c2.fill = GridBagConstraints.CENTER;
		c2.weighty = .1;
		c2.weightx = .1;
		c2.gridx = 0;
		c2.gridy = 10;
		c2.gridwidth = 1;
		c2.gridheight = 1;
		filterPanel.add(fixedLabel, c2);
		
		c2.fill = GridBagConstraints.CENTER;
		c2.weighty = .1;
		c2.weightx = .1;
		c2.gridx = 0;
		c2.gridy =11;
		c2.gridwidth = 1;
		c2.gridheight = 1;
		filterPanel.add(fixedSelect, c2);
		
		filterFrame.getContentPane().add(filterPanel);
		
				filterFrame.addWindowListener(new WindowAdapter() 
		{
				public void windowClosing(WindowEvent e) 
				{
						
				}
			});
			
		filterFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//------------------------------------------------------------------------NEW FRAME (Upload Frame)------------------------------------------------------------------------------------\\

		
		JFrame uploadFrame = new JFrame("Upload");
		
		JPanel uploadPanel = new JPanel();
		
		uploadFrame.setSize(320, 500); //width, height 
		uploadFrame.setLocationRelativeTo(null);
		
		uploadPanel.setLayout(new GridBagLayout());
		GridBagConstraints c3 = new GridBagConstraints();
		
		String[] animalList2 = { "Bird", "Cat", "Dog", "Rabbit", "Pig", "Cow", "Snake", "Lizard", "Chicken", "Monkey"};
		JComboBox animalSelect2 = new JComboBox(animalList2);
		
		ArrayList<String> ageList2 = new ArrayList<String>();
		
		for(int i = 0; i <= 100; ++i)
		{
			ageList2.add(String.valueOf(i));
		}
		
		JComboBox ageSelect2 = new JComboBox(ageList2.toArray());
		
		String[] colorList2 = { "Black", "White", "Brown", "Gold", "Green", "Yellow", "Purple", "Blue", "Red", "Orange"};
		JComboBox colorSelect2 = new JComboBox(colorList2);
		
		String[] sexList2 = {"Female", "Male"};
		JComboBox sexSelect2 = new JComboBox(sexList2);
		
		String[] yesNo2 = {"Yes", "No"};
		JComboBox adoptSelect2 = new JComboBox(yesNo2);
		
		String[] fixedList2 = {"Spay", "Neuter", "No"};
		JComboBox fixedSelect2 = new JComboBox(fixedList2);
		
		
		
		JTextArea nameText = new JTextArea("Doggo");
		JButton picUploadButton = new JButton("Upload");
		
		JButton submit = new JButton("Submit");
		
		
		JLabel typeAnimal2 = new JLabel("Type of Animal");
		JLabel age2 = new JLabel("Age");
		JLabel color2 = new JLabel("Color");
		JLabel adoptLabel2 = new JLabel("Up for Adoption?");
		JLabel fixedLabel2 = new JLabel("Fixed");
		JLabel sex2 = new JLabel("Sex");
		
		JLabel nameLabel = new JLabel("Name");
		JLabel picUploadLabel = new JLabel("Upload Picture");
		
		nameText.setWrapStyleWord(false);
		JScrollPane scroll = new JScrollPane(nameText);	
		
		
		c3.fill = GridBagConstraints.CENTER;
		c3.weighty = .1;
		c3.weightx = .1;
		c3.gridx = 0;
		c3.gridy = 0;
		c3.gridwidth = 1;
		c3.gridheight = 1;
		uploadPanel.add(nameLabel, c3);
		
		c3.fill = GridBagConstraints.CENTER;
		c3.weighty = .1;
		c3.weightx = .1;
		c3.gridx = 0;
		c3.gridy = 1;
		c3.gridwidth = 1;
		c3.gridheight = 1;
		uploadPanel.add(scroll, c3);
		
		c3.fill = GridBagConstraints.CENTER;
		c3.weighty = .1;
		c3.weightx = .1;
		c3.gridx = 0;
		c3.gridy = 2;
		c3.gridwidth = 1;
		c3.gridheight = 1;
		uploadPanel.add(picUploadLabel, c3);
		
		c3.fill = GridBagConstraints.CENTER;
		c3.weighty = .1;
		c3.weightx = .1;
		c3.gridx = 0;
		c3.gridy = 3;
		c3.gridwidth = 1;
		c3.gridheight = 1;
		uploadPanel.add(picUploadButton, c3);
		
		c3.fill = GridBagConstraints.CENTER;
		c3.weighty = .1;
		c3.weightx = .1;
		c3.gridx = 0;
		c3.gridy = 4;
		c3.gridwidth = 1;
		c3.gridheight = 1;
		uploadPanel.add(typeAnimal2, c3);
		
		c3.fill = GridBagConstraints.CENTER;
		c3.weighty = .1;
		c3.weightx = .1;
		c3.gridx = 0;
		c3.gridy = 5;
		c3.gridwidth = 1;
		c3.gridheight = 1;
		uploadPanel.add(animalSelect2, c3);
		
		c3.fill = GridBagConstraints.CENTER;
		c3.weighty = .1;
		c3.weightx = .1;
		c3.gridx = 0;
		c3.gridy = 6;
		c3.gridwidth = 1;
		c3.gridheight = 1;
		uploadPanel.add(age2, c3);
		
		c3.fill = GridBagConstraints.CENTER;
		c3.weighty = .1;
		c3.weightx = .1;
		c3.gridx = 0;
		c3.gridy = 7;
		c3.gridwidth = 1;
		c3.gridheight = 1;
		uploadPanel.add(ageSelect2, c3);
		
		c3.fill = GridBagConstraints.CENTER;
		c3.weighty = .1;
		c3.weightx = .1;
		c3.gridx = 0;
		c3.gridy = 8;
		c3.gridwidth = 1;
		c3.gridheight = 1;
		uploadPanel.add(color2, c3);
		
		c3.fill = GridBagConstraints.CENTER;
		c3.weighty = .1;
		c3.weightx = .1;
		c3.gridx = 0;
		c3.gridy = 9;
		c3.gridwidth = 1;
		c3.gridheight = 1;
		uploadPanel.add(colorSelect2, c3);
		
		c3.fill = GridBagConstraints.CENTER;
		c3.weighty = .1;
		c3.weightx = .1;
		c3.gridx = 0;
		c3.gridy = 10;
		c3.gridwidth = 1;
		c3.gridheight = 1;
		uploadPanel.add(sex2, c3);
		
		c3.fill = GridBagConstraints.CENTER;
		c3.weighty = .1;
		c3.weightx = .1;
		c3.gridx = 0;
		c3.gridy = 11;
		c3.gridwidth = 1;
		c3.gridheight = 1;
		uploadPanel.add(sexSelect2, c3);
		
		c3.fill = GridBagConstraints.CENTER;
		c3.weighty = .1;
		c3.weightx = .1;
		c3.gridx = 0;
		c3.gridy = 12;
		c3.gridwidth = 1;
		c3.gridheight = 1;
		uploadPanel.add(adoptLabel2, c3);
		
		c3.fill = GridBagConstraints.CENTER;
		c3.weighty = .1;
		c3.weightx = .1;
		c3.gridx = 0;
		c3.gridy = 13;
		c3.gridwidth = 1;
		c3.gridheight = 1;
		uploadPanel.add(adoptSelect2, c3);
		
		c3.fill = GridBagConstraints.CENTER;
		c3.weighty = .1;
		c3.weightx = .1;
		c3.gridx = 0;
		c3.gridy = 14;
		c3.gridwidth = 1;
		c3.gridheight = 1;
		uploadPanel.add(fixedLabel2, c3);
		
		c3.fill = GridBagConstraints.CENTER;
		c3.weighty = .1;
		c3.weightx = .1;
		c3.gridx = 0;
		c3.gridy =15;
		c3.gridwidth = 1;
		c3.gridheight = 1;
		uploadPanel.add(fixedSelect2, c3);
		
		c3.fill = GridBagConstraints.CENTER;
		c3.weighty = .1;
		c3.weightx = .1;
		c3.gridx = 0;
		c3.gridy =16;
		c3.gridwidth = 1;
		c3.gridheight = 1;
		uploadPanel.add(submit, c3);
		
		uploadFrame.getContentPane().add(uploadPanel);
		
				uploadFrame.addWindowListener(new WindowAdapter() 
		{
				public void windowClosing(WindowEvent e) 
				{
					
					JFileChooser saveChooser = new JFileChooser();
					saveChooser.setCurrentDirectory(new File("./../../../../../../Results"));
					int retrival = saveChooser.showSaveDialog(null);
					
					
					
					if (retrival == JFileChooser.APPROVE_OPTION) 
					{
						try(FileWriter fw = new FileWriter(saveChooser.getSelectedFile()+".txt")) 
						{
							fw.write("Upload Widget Animal Field: " + animalSelect2.getSelectedItem().toString() + "\n");
							fw.write("Upload Widget Age Field: " + ageSelect2.getSelectedItem().toString() + "\n");
							fw.write("Upload Widget Color Field: " + colorSelect2.getSelectedItem().toString() + "\n");
							fw.write("Upload Widget Sex Field: " + sexSelect2.getSelectedItem().toString() + "\n");
							fw.write("Upload Widget Adopt Field: " + adoptSelect2.getSelectedItem().toString() + "\n");
							fw.write("Upload Widget Fixed Field: " + fixedSelect2.getSelectedItem().toString() + "\n");
							
						}
						catch (Exception ex) 
						{
							ex.printStackTrace();
						}
					}
					
				}
			});
			
			
			 animalSelect2.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        JComboBox combo = (JComboBox)e.getSource();
                        String currVal = (String)combo.getSelectedItem();
						System.out.println("Upload Widget Animal Field: " + currVal);
                    }
                }            
        );
		
		ageSelect2.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        JComboBox combo = (JComboBox)e.getSource();
                        String currVal = (String)combo.getSelectedItem();
						System.out.println("Upload Widget Age Field: " + currVal);
                    }
                }            
        );
		
		colorSelect2.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        JComboBox combo = (JComboBox)e.getSource();
                        String currVal = (String)combo.getSelectedItem();
						System.out.println("Upload Widget Color Field: " + currVal);
                    }
                }            
        );

			
		sexSelect2.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        JComboBox combo = (JComboBox)e.getSource();
                        String currVal = (String)combo.getSelectedItem();
						System.out.println("Upload Widget Sex Field: " + currVal);
                    }
                }            
        );
		
		adoptSelect2.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        JComboBox combo = (JComboBox)e.getSource();
                        String currVal = (String)combo.getSelectedItem();
						System.out.println("Upload Widget Adopt Field: " + currVal);
                    }
                }            
        );
		
		fixedSelect2.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        JComboBox combo = (JComboBox)e.getSource();
                        String currVal = (String)combo.getSelectedItem();
						System.out.println("Upload Widget Fixed Field: " + currVal);
                    }
                }            
        );
			
			
			
		uploadFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
		//-----------------------------------------------------------------------Set Visibles and other Variables----------------------------------------------------------------------------------\\
		
		
		frame.setVisible(true);
		
		filterFrame.setVisible(true);
		filterFrame.setResizable(false);
		
		uploadFrame.setVisible(true);
		uploadFrame.setResizable(false);
		
		
		
		
		
	}
}

//******************************************************************************
