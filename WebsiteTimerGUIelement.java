package com.localarea.network.doug;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class WebsiteTimerGUIelement extends Component
{
	private JTextField websiteTextfield;
	private int webTextfieldWidth = 250;
	private String defaultText = "website";
	
	private JLabel timeLabel;
	private int timeWidth = 55;
	private String defaultTime = "00:00:00";
	
	Timer timer;
	
	private JButton startStopButton;
	private int startStopButtonWidth = 70;
	private String startString = "Start";
	private String stopString = "Stop";
	
	private JButton resetButton;
	private String resetString = "Reset";
	private int resetButtonWidth = 70;
	
	private boolean running = false;
	private int sleepInterval = 1000;	// time in milliseconds
	private int seconds;
	private int minutes;
	private int hours;
	
	public WebsiteTimerGUIelement(JFrame frame, int startX, int startY, int rowHeight, int padding)
	{
		super();
		websiteTextfield = new JTextField(defaultText);
		websiteTextfield.setBounds(startX, startY, webTextfieldWidth, rowHeight);
		frame.getContentPane().add(websiteTextfield);
		
		timeLabel = new JLabel(defaultTime);
		timeLabel.setBounds(startX + webTextfieldWidth + padding, startY, timeWidth, rowHeight);
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
		
		startStopButton = new JButton(startString);
		startStopButton.setBounds(startX*2 + webTextfieldWidth + timeWidth + padding, startY, startStopButtonWidth, rowHeight);
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
		
		resetButton = new JButton(resetString);
		resetButton.setBounds(startX*3 + webTextfieldWidth + timeWidth + startStopButtonWidth + padding, startY, resetButtonWidth , rowHeight);
		resetButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				seconds = 0;
				minutes = 0;
				hours = 0;
				timeLabel.setText("00:00:00");
			}
		});
		frame.getContentPane().add(resetButton);
	}
	
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
