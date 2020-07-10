package com.jadayoIscariot;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;



public class MQTTDashboard extends JPanel implements Serializable, ActionListener{

	private static final long serialVersionUID = -1655725478146553709L;
	JFrame frame;
	JPanel homePanel;
	public static void main(String[] args) {
		new MQTTDashboard().homePage();

	}

	public void homePage(){   //creates the GUI for homepage and kickstarts the process
		frame = new JFrame("MQTT Dashboard");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		BorderLayout layout = new BorderLayout();
		homePanel = new JPanel(layout);

		JButton homeAddButton = new JButton("Add Connection Variables");
		homeAddButton.addActionListener(this);
		homePanel.add(BorderLayout.SOUTH, homeAddButton);

		frame.setSize(400,400);
		frame.getContentPane().add(BorderLayout.CENTER, homePanel);
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent e){
		//create a connection object
		connectionObjects conn1 = new connectionObjects();
		//display the objects
	}



	public class connectionObjects extends JPanel implements Serializable, ActionListener{
		private static final long serialVersionUID = 1L;
		String clientId;
		String serverString;
		String port;
		String userName;
		String passWord;
		JFrame frame;
		JPanel panel;
	
		transient JTextField clientidField = new JTextField(20);
		transient JTextField serverField = new JTextField(20);
		transient JTextField portField = new JTextField(20);
		transient JTextField userField = new JTextField(20);
		transient JTextField passField = new JTextField(20);
	
		public connectionObjects(){
			makeConnectionObject();
		}
	
		public void makeConnectionObject(){
	
			frame = new JFrame("Enter variables");
			panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	
			JLabel theClientID = new JLabel("Client ID");
			JLabel theServerLabel = new JLabel("Server");
			JLabel thePortLabel = new JLabel("Port");
			JLabel theUserLabel = new JLabel("Username");
			JLabel thePassLabel = new JLabel("Password");
			JButton saveButton = new JButton("Save variables");
			saveButton.addActionListener(this);
	
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
			panel.add(saveButton);
			frame.setSize(300,300);
			frame.getContentPane().add(BorderLayout.CENTER, panel);
			frame.setVisible(true);
		}
	
		public void actionPerformed(ActionEvent e){
			clientId = clientidField.getText();
			serverString = serverField.getText();
			port = portField.getText();
			userName = userField.getText();
			passWord = passField.getText();
			frame.setVisible(false);
		}
	}



}


