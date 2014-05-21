/*
 * MainWindow.java
 * Created November 4, 2013
 * By Douglas Chidester
 * finished Nov 5, 2013
 * 
 * This is the main user interface for the program.
 * 
 * Copyright (C) 2013 Douglas Chidester
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package com.localarea.network.doug;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author Doug
 */
public class WindowsGUI
{
	private JFrame mainWindow;
	private String frameTitle = "Website Usage Tracker";
	private int frameWidth = 610;
	private int frameHeight = 470;
	private String author = "Doug Chidester";
	private String version = " v0.95";
	private String helpMessage = "Put a website URL or name in the fields that you will be using.\nStart and stop the timer at will.\n" +
								"WARNING: save to a file with a unique name before quitting otherwise your times will be lost forever.\n" +
			"However, using File->Quit from the menu will auto-save to a file.\n" +
			"WARNING: website fields with the default 'website' value will not be written to the save file.\n\n" +
			"The saved file should be in the same place as where you ran this program from (Windows) or your home directory (Unix).";
	//private String updates = "\n\nUpdates available at:\nlocalarea-network.com under the 'programs' link.";
	private String source = "\n\nWant to suggest an improvement? Create an issue at:" +
			"\nhttps://github.com/objectDisorientedProgrammer/WebsiteUsageTracker";
	
	private int numberOfGUIelements = 3;
	private int currentNumberOfTimers;
//	private int initialX = 10;
//	private int initialY = 60;
//	private int paddingX = 30;
//	private int paddingY = 10;
//	private int height = 25;
	
	private WebsiteTimerGUIelement[] trackers;
	private ArrayList<JPanel> guiElements;
	private ArrayList<WebsiteTimerGUIelement> timers;
	
	String imagePath = "/images/";	// path in jar file
	
	// save to file components
	private JPanel mainWindowPanel;
	private JPanel fileManagerPanel;
	
	private JTextField filenameTextfield;
	private String defaultFileString = "filename";
//	private int filenameTextfieldX = 10;
//	private int filenameTextfieldY = 20;
//	private int filenameTextfieldWidth = 250;
	
	// save button components
	private JButton saveButton;
//	private int saveButtonX = 10 + filenameTextfieldWidth + paddingX;
//	private int saveButtonY = filenameTextfieldY;
//	private int saveButtonWidth = 70;
	
	private JCheckBoxMenuItem saveAsCsvCheckboxMenuItem;
	
	public WindowsGUI()
	{
		super();
		// set up main JFrame
		mainWindow = new JFrame(frameTitle);
		//mainWindow.setSize(frameWidth, frameHeight);
		mainWindow.setLocationRelativeTo(null);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// create a panel for mainWindow
		mainWindowPanel = new JPanel(new GridLayout(0, 1, 0, 5)); // any number of rows & 1 column, vgap of 5
		
		// create a file management panel
		createAndAddFilePanel();
		
		// A variable number of panels to add timers to
		guiElements = new ArrayList<JPanel>(numberOfGUIelements);
		timers = new ArrayList<WebsiteTimerGUIelement>(numberOfGUIelements);
		
		// create and add all timers to all panels, then add all panels to the panel on mainWindow
		for(int i = 0; i < numberOfGUIelements; ++i)
		{
			guiElements.add(new JPanel());
			timers.add(new WebsiteTimerGUIelement());
			timers.get(i).addToContainer(guiElements.get(i));
			mainWindowPanel.add(guiElements.get(i));
		}
		currentNumberOfTimers = numberOfGUIelements;
		mainWindow.add(mainWindowPanel);
		
		
		/*
		 * Attempt 1 at stopping all timers with 1 button. 11/22/13
		 * 
		JButton stopAllButton = new JButton("Stop All");
		stopAllButton.setBounds(390, 21, 91, 23);
		stopAllButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				for(int i = 0; i < numberOfGUIelements; i++)
					if(trackers[i] != null)	// if the element exists
						if(trackers[i].isRunning())
							trackers[i].setRunning(true);
			}
		});
		mainWindow.getContentPane().add(stopAllButton);*/
		
		createAndAddMenuBar();
		
		
		mainWindow.pack();
		mainWindow.setVisible(true);
	}

	private void createAndAddMenuBar()
	{
		JMenuBar menuBar = new JMenuBar();
		mainWindow.setJMenuBar(menuBar);
		
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		menuBar.add(fileMenu);
		
		JMenuItem saveMenuItem = new JMenuItem("Save");
		saveMenuItem.setMnemonic(KeyEvent.VK_S);
		saveMenuItem.setIcon(new ImageIcon(this.getClass().getResource(imagePath + "save.png")));
		saveMenuItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				writeToFile(filenameTextfield.getText());
			}
		});
		fileMenu.add(saveMenuItem);
		
		JMenuItem quitMenuItem = new JMenuItem("Quit", new ImageIcon(this.getClass().getResource(imagePath+"exit.png")));
		quitMenuItem.setMnemonic(KeyEvent.VK_Q);
		quitMenuItem.addActionListener(new ActionListener()
		{
            public void actionPerformed(ActionEvent e)
            {
                // save data and close program if user clicks: File -> Quit
            	writeToFile(filenameTextfield.getText());
                mainWindow.dispose();
            }
		});
		fileMenu.add(quitMenuItem);
		
		JMenu optionsMenu = new JMenu("Options");
		optionsMenu.setMnemonic(KeyEvent.VK_O);
		menuBar.add(optionsMenu);
		
		saveAsCsvCheckboxMenuItem = new JCheckBoxMenuItem("Save as CSV");
		saveAsCsvCheckboxMenuItem.setMnemonic(KeyEvent.VK_C);
		saveAsCsvCheckboxMenuItem.setSelected(true);
		optionsMenu.add(saveAsCsvCheckboxMenuItem);
		
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		menuBar.add(helpMenu);
		
		JMenuItem helpMenuItem = new JMenuItem("Getting Started", new ImageIcon(this.getClass().getResource(imagePath+"help.png")));
		helpMenuItem.setMnemonic(KeyEvent.VK_G);
		helpMenuItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// show basic use instructions if user clicks: Help -> Getting Started
                JOptionPane.showMessageDialog(null, helpMessage, "Usage",
						JOptionPane.PLAIN_MESSAGE, new ImageIcon(this.getClass().getResource(imagePath+"help64.png")));
			}
		});
		helpMenu.add(helpMenuItem);
		
		JMenuItem aboutMenuItem = new JMenuItem("About", new ImageIcon(this.getClass().getResource(imagePath+"about.png")));
		aboutMenuItem.setMnemonic(KeyEvent.VK_A);
		aboutMenuItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// show author and version if user clicks: Help -> About
				JOptionPane.showMessageDialog(null, "Created by " + author + "\nVersion " + version + source, "About",
						JOptionPane.INFORMATION_MESSAGE, new ImageIcon(this.getClass().getResource(imagePath+"person.png")));
			}
		});
		helpMenu.add(aboutMenuItem);
	}

	private void createAndAddFilePanel()
	{
		fileManagerPanel = new JPanel(new GridLayout(1, 2, 25, 5));
		
		filenameTextfield = new JTextField(defaultFileString);
		fileManagerPanel.add(filenameTextfield);
		
		saveButton = new JButton("");
		saveButton.setIcon(new ImageIcon(WindowsGUI.class.getResource(imagePath + "save22.png")));
		saveButton.setBounds(290, 20, 22, 22);
		saveButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				writeToFile(filenameTextfield.getText());
			}
		});
		fileManagerPanel.add(saveButton);
		
		
		// make add button for new website timers
		JButton newTimerButton = new JButton("New Timer");
		newTimerButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				guiElements.add(new JPanel());
				timers.add(new WebsiteTimerGUIelement());
				timers.get(currentNumberOfTimers).addToContainer(guiElements.get(currentNumberOfTimers));
				mainWindowPanel.add(guiElements.get(currentNumberOfTimers));
				++currentNumberOfTimers;
				
				//for(JPanel timer : guiElements)
				//	timer.repaint();
			}
		});
		fileManagerPanel.add(newTimerButton);
		
		mainWindowPanel.add(fileManagerPanel); // always needs to be last
	}
	
	/**
	 * Check filename for valid format and fix it if necessary.
	 * @param filename - name of the file
	 */
	private void writeToFile(String filename)
	{
		// check for valid filename
		if(filename.equals(""))
			JOptionPane.showMessageDialog(mainWindow, "Please enter a valid filename", "Filename Error", JOptionPane.ERROR_MESSAGE);
		else if(filename.length() >= 5)	// might have .txt extension, add it if necessary
		{
			String ending = filename.substring(filename.length()-4, filename.length());
			if(!ending.equals(".txt"))
				filename += ".txt";
			createFile(filename);
		}
		else	// filename < 5 characters, needs .txt extension
		{
			filename += ".txt";
			createFile(filename);
		}
	}
	
	/**
	 * Write the file to the drive.
	 * @param filename - name of the file to write
	 */
	private void createFile(String filename)
	{
		try
		{
			FileWriter fstream = new FileWriter(filename);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write("Output from WebsiteUsageTracker");
			out.newLine();
			out.write("-------------------------------");
			out.newLine();
			out.newLine();
			// if the 'save as CSV' checkbox is selected
			if(saveAsCsvCheckboxMenuItem.isSelected())
			{
				out.write("Website,Time(HH:MM:SS),VisitFrequency");
				out.newLine();
				// write each website, time, and visit frequency to file
				for(int i = 0; i < numberOfGUIelements; i++)
				{
					// skip unused trackers
					if(!trackers[i].getWebsite().equals(trackers[i].getDefaultWebsiteString()))
					{
						out.write(trackers[i].getWebsite()+","+trackers[i].getTime()+","+trackers[i].getVisitCount());
						out.newLine();
					}
				}
			}
			else
			{
				out.write("Website    Time (HH:MM:SS)    Visit Frequency");
				out.newLine();
				out.newLine();
				// write each website, time, and visit frequency to file
				for(int i = 0; i < numberOfGUIelements; i++)
				{
					// skip unused trackers
					if(!trackers[i].getWebsite().equals(trackers[i].getDefaultWebsiteString()))
					{
						out.write(trackers[i].getWebsite()+"    "+trackers[i].getTime()+"    "+trackers[i].getVisitCount());
						out.newLine();
					}
				}
			}
			// add all times for a grand total of internet usage?
			out.close();
		} catch(Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
}
