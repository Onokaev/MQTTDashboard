package com.jadayoIscariot;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
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



	public void makeConnectionGui(){
		JFrame frame = new JFrame();
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
		public void actionPerformed(ActionEvent e){
			//display a dialog box for entering the connection vars
			JFrame insFrame = new JFrame("Connection variables");
			insFrame.setSize(200,200);
		
			BorderLayout layout = new BorderLayout();
			JPanel panel = new JPanel();

			JLabel theClientID = new JLabel("Client ID");
			JLabel theServerLabel = new JLabel("Server");
			JLabel thePortLabel = new JLabel("Port");
			JLabel theUserLabel = new JLabel("Username");
			JLabel thePassLabel = new JLabel("Password");

			panel.add(BorderLayout.CENTER, theClientID);
			panel.add(BorderLayout.CENTER, theUserLabel);
			panel.add(BorderLayout.CENTER, thePassLabel);
			panel.add(BorderLayout.CENTER, thePortLabel);
			panel.add(BorderLayout.CENTER, theServerLabel);

			insFrame.setVisible(true);


		}
	}


	//java com.jadayoIscariot.MQTTDashboard
	//javac -d ../Classes com/jadayoIscariot/MQTTDashboard.java
}