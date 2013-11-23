package com.localarea.network.doug;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class WebsiteTimerGUIelement
{
	private JTextField websiteTextfield;
	private int webTextfieldWidth = 250;
	private String defaultWebsiteString = "website";
	
	private JLabel timeLabel;
	private int timeLabelWidth = 65;
	private String defaultTime = "00:00:00";
	
	Timer timer;	// for updating time elapsed
	
	private JButton startStopButton;
	private int startStopButtonWidth = 70;
	private String startString = "Start";
	private String stopString = "Stop";
	
	private JButton resetButton;
	private String resetString = "Reset";
	private int resetButtonWidth = 75;
	
	private JLabel visitCountLabel;
	private int visitCount = 0;
	private int visitCountLabelWidth = 70;
	
	private boolean running = false;
	private int sleepInterval = 1000;	// time in milliseconds
	private int seconds;
	private int minutes;
	private int hours;
	
	/**
	 * A five part element to add to a JFrame. Includes a JTextfield, JLabel, JLabel, JButton, and JButton.
	 * @param frame	- frame to add this element to
	 * @param startX - x coord on frame
	 * @param startY - y coord on frame
	 * @param rowHeight - height of components
	 * @param padding - space between components
	 */
	public WebsiteTimerGUIelement(JFrame frame, int startX, int startY, int rowHeight, int padding)
	{
		super();
		// website textfield ===================================================================
		websiteTextfield = new JTextField(defaultWebsiteString);
		websiteTextfield.setBounds(startX, startY, webTextfieldWidth, rowHeight);
		frame.getContentPane().add(websiteTextfield);
		
		// time label ===================================================================
		timeLabel = new JLabel(defaultTime);
		timeLabel.setBounds(startX + webTextfieldWidth + padding, startY, timeLabelWidth, rowHeight);
		frame.getContentPane().add(timeLabel);

		timer = new Timer(sleepInterval, new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				updateTime();
				timeLabel.setText(formatTimeString());
			}
		});
		
		visitCountLabel = new JLabel(""+visitCount);
		visitCountLabel.setBounds(startX + webTextfieldWidth + timeLabelWidth + padding, startY, visitCountLabelWidth, rowHeight);
		frame.getContentPane().add(visitCountLabel);
		
		// start/stop button ===================================================================
		int startStopButtonX = startX*2 +webTextfieldWidth+timeLabelWidth+visitCountLabelWidth+padding;
		startStopButton = new JButton(startString);
		startStopButton.setBounds(startStopButtonX, startY, startStopButtonWidth, rowHeight);
		startStopButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(!running)
				{
					timer.start();
					startStopButton.setText(stopString);
					running = true;
					visitCountLabel.setText(""+ ++visitCount);
				}
				else
				{
					timer.stop();
					startStopButton.setText(startString);
					running = false;
				}
			}
		});
		frame.getContentPane().add(startStopButton);
		
		// reset button ===================================================================
		int resetButtonX = startX*3+webTextfieldWidth+timeLabelWidth+visitCountLabelWidth+startStopButtonWidth+padding;
		resetButton = new JButton(resetString);
		resetButton.setBounds(resetButtonX, startY, resetButtonWidth , rowHeight);
		resetButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// reset timer value and visit value
				seconds = 0;
				minutes = 0;
				hours = 0;
				timeLabel.setText("00:00:00");
				
				visitCount = 0;
				visitCountLabel.setText("" + visitCount);
			}
		});
		frame.getContentPane().add(resetButton);
	}
	
	public String getWebsite()
	{
		return websiteTextfield.getText();
	}
	
	public String getDefaultWebsiteString()
	{
		return this.defaultWebsiteString;
	}
	
	public String getTime()
	{
		return timeLabel.getText();
	}
	
	public int getVisitCount()
	{
		return visitCount;
	}
	
	/**
	 * Check if the timer is running.
	 * @return true if the timer is on, otherwise false.
	 */
	public boolean isRunning()
	{
		return running;
	}
	
	public void setRunning(boolean stop)
	{
		this.running = stop;
	}
	
	/**
	 * Increments 'seconds' every time its called. Increments 'minutes' and 'hours' when needed.
	 */
	private void updateTime()
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
	}
	
	/**
	 * Take raw data and turn it into a nicely formatted time string HH:MM:SS.
	 * @return formatted time HH:MM:SS
	 */
	private String formatTimeString()
	{
		String second = Integer.toString(seconds);
		String minute = Integer.toString(minutes);
		String hour = Integer.toString(hours);
		
		if(second.length() < 2)
			second = "0" + second;
		if(minute.length() < 2)
			minute = "0" + minute;
		if(hour.length() < 2)
			hour = "0" + hour;
		return hour+":"+minute+":"+second;
	}
}
