package com.jadayoIscariot;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;



public class MQTTDashboard{

	JFrame frame;
	//static ConnectionsGui connectionsGui;

	public static void main(String[] args) {
		ConnectionsGui connectionsGui = new ConnectionsGui();

		System.out.println("MQTT is up");

		//before this, we should have a try catch that checks for any serialized objects of connectionsGui
		try{
			ObjectInputStream oStream = new ObjectInputStream(new FileInputStream("conneection.ser"));
			Object one = oStream.readObject();

			ConnectionsGui.AddButtonListener.connectionSaveActionListener conn1 = (ConnectionsGui.AddButtonListener.connectionSaveActionListener) one;
			//solve this

			oStream.close();

		}catch(Exception ex){
			System.out.println("Couldn't find any serialized objects. Create new ones");
			ex.printStackTrace();
			connectionsGui = new ConnectionsGui();
			connectionsGui.makeConnectionGui();
		}

		//call this when serialization returns null for objects


	}
}



class ConnectionsGui extends JPanel {

	private static final long serialVersionUID = 1L;
	String clientId;
	String serverString;
	String port;
	String userName;
	String passWord;
	JFrame frame;
	JPanel panel;

	public ConnectionsGui(){
		makeConnectionGui();
	}

	public void makeConnectionGui(){

		frame = new JFrame("MQTT Dashboard");
		BorderLayout layout = new BorderLayout();
		panel = new JPanel(layout);
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JButton addButton = new JButton("Add Connection");
		addButton.addActionListener(new AddButtonListener());
		panel.add(BorderLayout.SOUTH, addButton);	

		frame.getContentPane().add(BorderLayout.CENTER, panel);
		frame.setSize(400,400);
		frame.setVisible(true);

	}
	
	public class AddButtonListener implements ActionListener, Serializable{
		private static final long serialVersionUID = 1L;
		JTextField clientidField = new JTextField(20);
		JTextField serverField = new JTextField(20);
		JTextField portField = new JTextField(20);
		JTextField userField = new JTextField(20);
		JTextField passField = new JTextField(20);
		JFrame insFrame;
	
		public void actionPerformed(ActionEvent e){
			//display a dialog box for entering the connection vars
			frame.setVisible(false);
			insFrame = new JFrame("Connection variables");
			insFrame.setSize(300,400);
		
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

		public class connectionSaveActionListener implements ActionListener, Serializable{
			/**
			 *
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				//save the variables
				clientId = clientidField.getText();
				serverString = serverField.getText();
				port = portField.getText();
				userName = userField.getText();
				passWord = passField.getText();
				frame.setVisible(true);
				insFrame.setVisible(false);

				try{
					FileOutputStream fs = new FileOutputStream("conneection.ser");
					ObjectOutputStream oStream = new ObjectOutputStream(fs);
					oStream.writeObject(this);
					oStream.close();

				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		}
	}

	//how should an object of this class behave. Add behaviour
	//add the objects to the screen

	public void addConnectionToScreen(){
		JButton connButton = new JButton(this.clientId);
		connButton.addActionListener(new ConnButtonActionListener());
		panel.add(BorderLayout.NORTH, connButton);

	}

	public class ConnButtonActionListener implements ActionListener, Serializable{
		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			
		}
	}


	//java com.jadayoIscariot.MQTTDashboard
	//javac -d ../Classes com/jadayoIscariot/MQTTDashboard.java
}