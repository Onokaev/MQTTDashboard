package com.jadayoIscariot;

<<<<<<< HEAD
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
=======
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
>>>>>>> 920851221a59b1ca8a669d2d0f46442fe4dd42f4

public class MQTTDashboard{

	JFrame frame;
	public static void main(String[] args){
		System.out.println("MQTT is up");
		new Gui().makeFrame();
	}
}

class Gui extends JPanel{

	private static final long serialVersionUID = 1L;
	JPanel panel;
	JFrame frame;


	public void makeFrame(){
		frame = new JFrame("MQTT Dashboard");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		BorderLayout layout = new BorderLayout();
		panel = new JPanel(layout);
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JTextField userName = new JTextField(20);
		JLabel usernameLabel = new JLabel("Username");
		JLabel passwordLabel = new JLabel("Password");
		JLabel friendName = new JLabel("Friendly Name");
		JLabel topic = new JLabel("Topic");

		panel.add(usernameLabel);
		panel.add(userName);


		frame.getContentPane().add(BorderLayout.WEST, panel);
		frame.setSize(600,400);
		frame.setVisible(true);
		
	}
	//java com.jadayoIscariot.MQTTDashboard
	//javac -d ../Classes com/jadayoIscariot/MQTTDashboard.java
}