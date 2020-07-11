package com.jadayoIscariot;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.util.*;




public class MQTTDashboard extends JPanel implements Serializable{

	private static final long serialVersionUID = -1655725478146553709L;

	JFrame frame;
	JPanel homePanel;
	Box objectsBox = new Box(BoxLayout.Y_AXIS);
	JPanel objectsPanel;
	ArrayList<connectionObjects> connList = new ArrayList<connectionObjects>();
	connectionObjects cObject;
	static int counter = 0;


	public static void main(String[] args) {
		new MQTTDashboard().homePage();
	}

	public void homePage(){   //creates the GUI for homepage and kickstarts the process
		frame = new JFrame("MQTT Dashboard");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		BorderLayout layout = new BorderLayout();
		homePanel = new JPanel(layout);
		objectsPanel = new JPanel(layout);

		//box layout for the objects panel

		JButton homeAddButton = new JButton("Add Connection Variables");
		homeAddButton.addActionListener(new AddConnectionObjectsListener());
		homePanel.add(BorderLayout.SOUTH, homeAddButton);

		JButton loadButton = new JButton("Load connection objects");
		loadButton.addActionListener(new loadConnectionObjectsListener());
		homePanel.add(BorderLayout.NORTH, loadButton);
		homePanel.add(BorderLayout.CENTER, objectsPanel);

		frame.setSize(400,400);
		frame.getContentPane().add(BorderLayout.CENTER, homePanel);
		frame.setVisible(true);
	}


	public class AddConnectionObjectsListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			//create a connection object
			cObject = new connectionObjects();
		}
	}

	//loads the connection objects to the homescreen
	public class loadConnectionObjectsListener implements ActionListener{
		public void actionPerformed(ActionEvent e){

			for(connectionObjects a : connList){
				JButton connButton = new JButton(a.getClientID());
				connButton.addActionListener(new connObjectListener());
				objectsBox.add(connButton);
			}
			objectsPanel.add(BorderLayout.CENTER, objectsBox);
			connList.clear();
		}

		//the connection objects do the same thing, either subscribe to a topic or post to one
		public class connObjectListener implements ActionListener{
			public void actionPerformed(ActionEvent e){

			}
		}
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
			connList.add(this);
			System.out.println(connList);
			frame.setVisible(false);

		}


		public String getClientID(){
			return clientId;
		}
	}
}


//java com.jadayoIscariot.MQTTDashboard
	//javac -d ../Classes com/jadayoIscariot/MQTTDashboard.java