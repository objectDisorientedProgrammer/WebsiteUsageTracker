/*
 * WebsiteTimerGUIelement.java
 * 
 * A custom GUI element comprised of multiple other GUI elements.
 * Includes a JTextfield, JLabel, JLabel, JButton, and JButton in that order.
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

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class WebsiteTimerGUIelement
{
	private JTextField websiteTextfield;
	private String defaultWebsiteString = "website";
	
	private JLabel timeLabel;
	private String defaultTime = "00:00:00";
	
	private Timer timer;	// for updating time elapsed
	
	private JButton startStopButton;
	private String startString = "Start";
	private String stopString = "Stop";
	
	private JButton resetButton;
	private String resetString = "Reset";
	
	private JLabel visitCountLabel;
	private int visitCount = 0;
	
	private boolean running = false;
	private int sleepInterval = 1000;	// time in milliseconds
	private int seconds;
	private int minutes;
	private int hours;
	
	public WebsiteTimerGUIelement()
	{
		super();
		// website textfield ==================================================
		websiteTextfield = new JTextField(defaultWebsiteString);
		// time label =========================================================
		timeLabel = new JLabel(defaultTime);
		timer = new Timer(sleepInterval, new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				updateTime();
				timeLabel.setText(formatTimeString());
			}
		});
		// visit count label ==================================================
		visitCountLabel = new JLabel(""+visitCount);
		// start/stop button ==================================================
		startStopButton = new JButton(startString);
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
		// reset button =======================================================
		resetButton = new JButton(resetString);
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
	}
	
	/**
	 * Five swing components to add to a Container.
	 * Includes a JTextfield, JLabel, JLabel, JButton, and JButton.
	 * @param elementPanel - the container to add the components to.
	 */
	public void addToContainer(Container elementPanel)
	{
		elementPanel.setLayout(new GridLayout(1, 5, 10, 10));
		
		elementPanel.add(websiteTextfield);
		elementPanel.add(timeLabel);
		elementPanel.add(visitCountLabel);
		elementPanel.add(startStopButton);
		elementPanel.add(resetButton);
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
		++seconds;
		if(seconds == 60)
		{
			++minutes;
			seconds = 0;
		}
		if(minutes == 60)
		{
			++hours;
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
