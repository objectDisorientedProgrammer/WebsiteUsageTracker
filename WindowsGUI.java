/*
 * MainWindow.java
 * Created November 4, 2013
 * By Douglas Chidester
 * 
 */
package com.localarea.network.doug;

import java.awt.Rectangle;
import java.io.BufferedWriter;
import java.io.FileWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
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
	private int frameWidth = 520;
	private int frameHeight = 450;
	
	private int numberOfGUIelements = 10;
	private int initialX = 10;
	private int initialY = 60;
	private int paddingX = 20;
	private int paddingY = 10;
	private int height = 25;
	
	private WebsiteTimerGUIelement[] trackers;
	
	// save to file components
	private JTextField filenameTextfield;
	private String defaultFileString = "filename";
	private int filenameTextfieldX = 10;
	private int filenameTextfieldY = 20;
	private int filenameTextfieldWidth = 250;
	
	private JButton saveButton;
	private String saveString = "Save";
	private int saveButtonX = 10 + filenameTextfieldWidth + paddingX;
	private int saveButtonY = filenameTextfieldY;
	private int saveButtonWidth = 70;
	
	
	// website textfield properties
	/*private JTextField websiteTFs[];
	private int webTextfieldX = 10;
	private int webTextfieldY = yPosition;
	private int webTextfieldWidth = 250;
	private int webTextfieldHeight = height;
	
	// time label properties
	private JLabel timeLabels[];
	private String zeroTime = "00:00:00";
	private int timeLblX = webTextfieldX + webTextfieldWidth + paddingX;
	private int timeLblY = yPosition;
	private int timeLblWidth = 55;
	private int timeLblHeight = height;
	
	// start/stop button properties
	private JButton startStopButtons[];
	private String startString = "Start";
	private String stopString = "Stop";
	private int startStopBtnX = timeLblX + timeLblWidth + paddingX;
	private int startStopBtnY = yPosition;
	private int startStopBtnWidth = 70;
	
	// reset button properties
	
	// thread stuff
	private boolean running = false;
	private long sleepTime = 1000;	// time in milliseconds
	private Thread t1;
	
	HashMap<JButton, JLabel> buttonLabelLink;*/
	
	public WindowsGUI()
	{
		super();
		mainWindow = new JFrame(frameTitle);
		mainWindow.setLayout(null);
		
		filenameTextfield = new JTextField(defaultFileString);
		filenameTextfield.setBounds(filenameTextfieldX, filenameTextfieldY, filenameTextfieldWidth, height);
		mainWindow.getContentPane().add(filenameTextfield);
		
		saveButton = new JButton(saveString);
		saveButton.setBounds(saveButtonX, saveButtonY, saveButtonWidth, height);
		mainWindow.getContentPane().add(saveButton);
		
		trackers = new WebsiteTimerGUIelement[numberOfGUIelements];
		for(int t = 0; t < numberOfGUIelements; t++)
		{
			trackers[t] = new WebsiteTimerGUIelement(mainWindow, initialX, initialY + (paddingY+height)*t, height, paddingX);
		}
		//new WebsiteTimerGUIelement(mainWindow, initialX, initialY, height, paddingX);
		//new WebsiteTimerGUIelement(mainWindow, 10, 20+height+paddingY, height, paddingX);
		
		/*
		createWebsiteTextFields();
		createTimeLabels();
		createStartStopButtons();
		*/
		//linkButtonsToLabels();
		
		
		mainWindow.setSize(frameWidth, frameHeight);
		mainWindow.setLocationRelativeTo(null);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setVisible(true);
	}

	/*private void linkButtonsToLabels()
	{
		buttonLabelLink = new HashMap<JButton, JLabel>(numberOfGUIelements);
		for(int k = 0; k < numberOfGUIelements; k++)
		{
			buttonLabelLink.put(startStopButtons[k], timeLabels[k]);	// map buttons to labels
			
			startStopButtons[k].addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					if(e.getSource() instanceof JButton)
					{
						if(((JButton)(e.getSource())).getText().equals(startString))
						{
							((JButton)(e.getSource())).setText(stopString);
							t1 = new Thread(new Runnable()
							{
								public void run() {
									try {
										updateTime(buttonLabelLink.get((JButton)e.getSource()));
									} catch(Exception ie) {
										System.out.println(ie.getMessage());
									}
								}
							});
							t1.start();
						}
						else
							((JButton)(e.getSource())).setText(startString);
						System.out.println(((JButton)(e.getSource())).getText());
					}
						
				}
			});
		}
		
	}*/

	/*private void createStartStopButtons()
	{
		startStopButtons = new JButton[numberOfGUIelements];
		for(int s = 0; s < numberOfGUIelements; s++)
		{
			startStopButtons[s] = new JButton(startString);
			startStopButtons[s].setBounds(startStopBtnX, (startStopBtnY + ((paddingY+height)*s)), startStopBtnWidth, height);
			mainWindow.getContentPane().add(startStopButtons[s]);
		}
	}*/

	/*private void linkButtonToTime(final JButton b, final JLabel l)
	{
		
		b.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(b.getText() == "Start")
				{
					running = true;
					// make a thread object for each thread?
					//starting new Thread which will update time
					new Thread(new Runnable()
					{
						public void run() {
							try {
								updateTime(b, l);
							} catch(Exception ie) {
								System.out.println(ie.getMessage());
							}
						}
					}).start();
					b.setText("Stop");
				}
				else if(b.getText() == "Stop")
				{
					b.setText("Start");
					running = false;
				}
			}
		});
	}*/

	/*private void createTimeLabels()
	{
		timeLabels = new JLabel[numberOfGUIelements];
		for(int t = 0; t < numberOfGUIelements; t++)
		{
			timeLabels[t] = new JLabel(zeroTime);
			timeLabels[t].setBounds(timeLblX, (timeLblY + ((paddingY+timeLblHeight)*t)), timeLblWidth, timeLblHeight);
			mainWindow.getContentPane().add(timeLabels[t]);
		}
	}*/

	/*private void createWebsiteTextFields()
	{
		websiteTFs = new JTextField[numberOfGUIelements];
		for(int w = 0; w < numberOfGUIelements; w++)
		{
			websiteTFs[w] = new JTextField("website" + (w+1), 10);
			websiteTFs[w].setBounds(webTextfieldX, webTextfieldY + ((paddingY+webTextfieldHeight)*w), webTextfieldWidth, webTextfieldHeight);
			mainWindow.getContentPane().add(websiteTFs[w]);
		}
	}*/
	
	/*public void updateTime(JLabel timeLabel)
	{
		int seconds = 0;
		int minutes = 0;
		int hours = 0;
		try
		{
			while(true)
			{
				seconds++;
				if(seconds == 60)
				{
					minutes++;
					seconds = 0;
				}
				if(minutes == 60)
				{
					hours++;
					minutes = 0;
				}
				
				String second = Integer.toString(seconds);
				String minute = Integer.toString(minutes);
				String hour = Integer.toString(hours);
				
				if(second.length() < 2)
					second = "0" + second;
				if(minute.length() < 2)
					minute = "0" + minute;
				if(hour.length() < 2)
					hour = "0" + hour;
				
				//geting Time in desire format
				timeLabel.setText(hour+":"+minute+":"+second);
				Thread.currentThread();
				//Thread sleeping for 1 sec
				Thread.sleep(sleepTime);
			}
		}
		catch (Exception e)
		{
			System.out.println("Exception in Thread Sleep : "+e);
		}
	}*/
	
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
			// write each website and time to file
			for(int i = 0; i < numberOfGUIelements; i++)
			{
				//out.write(websiteTFs[i].getText()+"    "+lblTime.getText());
				out.newLine();
			}
			// add all times for a grand total of internet usage
			out.close();
		} catch(Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
}
