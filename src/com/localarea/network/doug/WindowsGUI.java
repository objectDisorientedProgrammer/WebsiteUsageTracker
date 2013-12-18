/*
 * MainWindow.java
 * Created November 4, 2013
 * By Douglas Chidester
 * finished Nov 5, 2013
 * 
 */
package com.localarea.network.doug;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * @author Doug
 *
 */
public class WindowsGUI
{
	private JFrame mainWindow;
	private String frameTitle = "Website Usage Tracker";
	private int frameWidth = 610;
	private int frameHeight = 470;
	private String author = "Doug Chidester";
	private String version = " v0.80";
	private String helpMessage = "Put a website URL or name in the fields that you will be using.\nStart and stop the timer at will.\n" +
								"WARNING: save to a file with a unique name before quitting otherwise your times will be lost forever.\n" +
			"However, using File->Quit from the menu will auto-save to a file.\n" +
			"WARNING: website fields with the default 'website' value will not be written to the save file.\n\n" +
			"The saved file should be in the same place as where you ran this program from (Windows) or your home directory (Unix).";
	//private String updates = "\n\nUpdates available at:\nlocalarea-network.com under the 'programs' link.";
	private String source = "\n\nWant to suggest an improvement? Create an issue at:" +
			"\nhttps://github.com/objectDisorientedProgrammer/WebsiteUsageTracker";
	
	private int numberOfGUIelements = 10;
	private int initialX = 10;
	private int initialY = 60;
	private int paddingX = 30;
	private int paddingY = 10;
	private int height = 25;
	
	private WebsiteTimerGUIelement[] trackers;
	
	// save to file components
	private JTextField filenameTextfield;
	private String defaultFileString = "filename";
	private int filenameTextfieldX = 10;
	private int filenameTextfieldY = 20;
	private int filenameTextfieldWidth = 250;
	
	// save button components
	private JButton saveButton;
	private String saveString = "Save";
	private int saveButtonX = 10 + filenameTextfieldWidth + paddingX;
	private int saveButtonY = filenameTextfieldY;
	private int saveButtonWidth = 70;
	
	private JCheckBoxMenuItem saveAsCsvCheckboxMenuItem;
	
	public WindowsGUI()
	{
		super();
		// set up main JFrame
		mainWindow = new JFrame(frameTitle);
		mainWindow.getContentPane().setLayout(null);
		mainWindow.setSize(frameWidth, frameHeight);
		mainWindow.setLocationRelativeTo(null);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// create and add WebsiteTimer rows
		trackers = new WebsiteTimerGUIelement[numberOfGUIelements];
		for(int t = 0; t < numberOfGUIelements; t++)
		{
			trackers[t] = new WebsiteTimerGUIelement(mainWindow, initialX, initialY + (paddingY+height)*t, height, paddingX);
		}
		
		// create and add a textfield for the user to enter a filename
		filenameTextfield = new JTextField(defaultFileString);
		filenameTextfield.setBounds(filenameTextfieldX, filenameTextfieldY, filenameTextfieldWidth, height);
		mainWindow.getContentPane().add(filenameTextfield);
		
		// create and add a save button for saving values to a file
		saveButton = new JButton("");
		saveButton.setIcon(new ImageIcon(WindowsGUI.class.getResource("/com/sun/java/swing/plaf/windows/icons/FloppyDrive.gif")));
		saveButton.setBounds(290, 20, 35, 25);
		saveButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				writeToFile(filenameTextfield.getText());
			}
		});
		mainWindow.getContentPane().add(saveButton);
		
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
		
		// create and add a menu bar with several items (for looks)
		JMenuBar menuBar = new JMenuBar();
		mainWindow.setJMenuBar(menuBar);
		
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		JMenuItem saveMenuItem = new JMenuItem("Save");
		saveMenuItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				writeToFile(filenameTextfield.getText());
			}
		});
		fileMenu.add(saveMenuItem);
		
		JMenuItem quitMenuItem = new JMenuItem("Quit");
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
		menuBar.add(optionsMenu);
		
		saveAsCsvCheckboxMenuItem = new JCheckBoxMenuItem("Save as CSV");
		saveAsCsvCheckboxMenuItem.setSelected(true);
		optionsMenu.add(saveAsCsvCheckboxMenuItem);
		
		JMenu helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);
		
		JMenuItem helpMenuItem = new JMenuItem("Help");
		helpMenuItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// show basic use instructions if user clicks: Help -> Help
                JOptionPane.showMessageDialog(null, helpMessage, "How to use", JOptionPane.PLAIN_MESSAGE);
			}
		});
		helpMenu.add(helpMenuItem);
		
		JMenuItem aboutMenuItem = new JMenuItem("About");
		aboutMenuItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// show author and version if user clicks: Help -> About
				JOptionPane.showMessageDialog(null, "Created by "+author+"\nVersion "+version/*+updates*/+source, "About", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		helpMenu.add(aboutMenuItem);
		mainWindow.setVisible(true);
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
