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
	ArrayList<connectionObjects> temporaryList = new ArrayList<connectionObjects>();
	connectionObjects cObject;


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
			cObject.makeConnectionObject();
		}
	}

	//loads the connection objects to the homescreen
	public class loadConnectionObjectsListener extends connectionObjects implements ActionListener{
		JFrame topicFrame;
		JPanel topicPanel;

		JTextField friendlyNameField;
		JTextField topicField;
		JTextField qosField;

		String MTopic;
		String MFriendlyName;
		String MQos;

		public void actionPerformed(ActionEvent e){

			for(connectionObjects a : connList){
				JButton connButton = new JButton(a.getClientID());
				connButton.addActionListener(new connObjectListener());
				objectsBox.add(connButton);
			}
			objectsPanel.add(BorderLayout.CENTER, objectsBox);
			connList.clear();
			//temporary list is already there. so just clear this one so that we dont have duplicates when loading to screen
		}

		//creating gui for the specific objects
		public class connObjectListener implements ActionListener{
			JFrame objectFrame = new JFrame("MQTT");
			JPanel northFramePanel = new JPanel();
			BorderLayout layout = new BorderLayout();
			JTabbedPane thePane = new JTabbedPane();
			JComponent subscribePanel = new JPanel(layout);  
			JComponent publishPanel = new JPanel(layout);

			

			public void actionPerformed(ActionEvent e){
				//display a new frame for the specific connection object selected
				JButton connectMQTTButton = new JButton("Connect To Broker");
				connectMQTTButton.addActionListener(new connectMQTTListener());
				northFramePanel.add(connectMQTTButton);

				JButton addTopic = new JButton("+ Topic");
				addTopic.addActionListener(new addTopicListener());
				northFramePanel.add(addTopic);

				objectFrame.getContentPane().add(BorderLayout.NORTH, northFramePanel);
		

				thePane.addTab("Subscriptions Tab", subscribePanel);				

				thePane.addTab("Publications Tab", publishPanel);
				
				objectFrame.getContentPane().add(BorderLayout.CENTER, thePane);
				objectFrame.setSize(500,500);
				objectFrame.setVisible(true);

			}
		}

		public class addTopicListener implements ActionListener{
			
			public void actionPerformed(ActionEvent e){
				topicFrame  = new JFrame("Add Topic");
				topicPanel = new JPanel();
				topicPanel.setLayout(new BoxLayout(topicPanel, BoxLayout.Y_AXIS));

				JLabel theFriendlyNameLabel = new JLabel("Friendly Name");
				JLabel theTopicLabel = new JLabel("Topic");
				JLabel theQosLabel = new JLabel("QoS");

				friendlyNameField = new JTextField(20);
				topicField = new JTextField(20);
				qosField = new JTextField(20);

				JButton createTopicButton = new JButton("Create");
				createTopicButton.addActionListener(new CreateTopicListener());


				topicPanel.add(theFriendlyNameLabel);
				topicPanel.add(friendlyNameField);
				topicPanel.add(theTopicLabel);
				topicPanel.add(topicField);
				topicPanel.add(theQosLabel);
				topicPanel.add(qosField);
				topicPanel.add(createTopicButton);

				topicFrame.getContentPane().add(BorderLayout.CENTER, topicPanel);
				topicFrame.setSize(250,250);
				topicFrame.setVisible(true);
			}

			public class CreateTopicListener implements ActionListener{
				public void actionPerformed(ActionEvent e){
					MTopic = topicField.getText();
					MFriendlyName = friendlyNameField.getText();
					MQos = qosField.getText();
				}
			}
		}

		public class connectMQTTListener implements ActionListener{
			public void actionPerformed(ActionEvent e){
				//connect to MQTT broker
				

				

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
			connList.add(this);   //this list is used to lead objects to the objectPane. cleared later
			temporaryList.add(this);  //this is maintained
			frame.setVisible(false);

		}


		public String getClientID(){
			return clientId;
		}

		//add behaviour
		public void connectToBroker(){

		}

		public void subscribeToTopic(int qos){

		}

		public void publishToTopic(int qos){

		}

	}
}


//java com.jadayoIscariot.MQTTDashboard
	//javac -d ../Classes com/jadayoIscariot/MQTTDashboard.java



