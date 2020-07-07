package com.jadayoIscariot;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import jdk.internal.org.objectweb.asm.tree.analysis.Frame;

import java.util.*;

public class MQTTDashboard{

	JFrame frame;
	static ConnectionsGui connectionsGui;

	public static void main(String[] args) {
		System.out.println("MQTT is up");
		connectionsGui = new ConnectionsGui();
		connectionsGui.makeConnectionGui();
	}
}


class ConnectionsGui extends JPanel{

	private static final long serialVersionUID = 1L;
	String clientId;
	String serverString;
	String port;
	String userName;
	String passWord;
	JFrame frame;



	public void makeConnectionGui(){
		frame = new JFrame("MQTT Dashboard");
		BorderLayout layout = new BorderLayout();
		JPanel panel = new JPanel(layout);
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JButton addButton = new JButton("Add Connection");
		addButton.addActionListener(new AddButtonListener());
		panel.add(BorderLayout.SOUTH, addButton);	

		frame.getContentPane().add(BorderLayout.CENTER, panel);
		frame.setSize(400,400);
		frame.setVisible(true);

	}
	

	public class AddButtonListener implements ActionListener{
		JTextField clientidField = new JTextField(20);
		JTextField serverField = new JTextField(20);
		JTextField portField = new JTextField(20);
		JTextField userField = new JTextField(20);
		JTextField passField = new JTextField(20);
	
		public void actionPerformed(ActionEvent e){
			//display a dialog box for entering the connection vars
			frame.setVisible(false);
			JFrame insFrame = new JFrame("Connection variables");
			insFrame.setSize(300,400);
		
			BorderLayout layout = new BorderLayout();
			JPanel panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

			JLabel theClientID = new JLabel("Client ID");
			JLabel theServerLabel = new JLabel("Server");
			JLabel thePortLabel = new JLabel("Port");
			JLabel theUserLabel = new JLabel("Username");
			JLabel thePassLabel = new JLabel("Password");

			panel.add(theClientID);
			panel.add(clientidField);

			panel.add(theUserLabel);
			panel.add(userField);

			panel.add(thePassLabel);
			panel.add(passField);

			panel.add(theServerLabel);
			panel.add(serverField);

			panel.add(thePortLabel);
			panel.add(portField);

			JButton saveButton = new JButton("Save Variables");
			saveButton.addActionListener(new connectionSaveActionListener());
			panel.add(saveButton);

			insFrame.getContentPane().add(BorderLayout.CENTER, panel);
			insFrame.setVisible(true);


		}

		public class connectionSaveActionListener implements ActionListener{
			public void actionPerformed(ActionEvent e){
				//save the variables
				clientId = clientidField.getText();
				serverString = serverField.getText();
				port = portField.getText();
				userName = userField.getText();
				passWord = passField.getText();
				frame.setVisible(true);
			}
		}
	}


	//java com.jadayoIscariot.MQTTDashboard
	//javac -d ../Classes com/jadayoIscariot/MQTTDashboard.java
}