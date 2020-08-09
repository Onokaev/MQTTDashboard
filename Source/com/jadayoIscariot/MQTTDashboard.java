package com.jadayoIscariot;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;  


public class MQTTDashboard{
    ArrayList<mqttClass> connList = new ArrayList<mqttClass>();
	ArrayList<mqttClass> temporaryList = new ArrayList<mqttClass>();
    public static void main(String[] args){
       
       // gui.addConnectionObjectsToPanel();

        String subTopic = "testTopic";
        String pubTopic = "testTopic";
        String content = "This is the message";
        int qoS = 2;
        String broker = "tailor.cloudmqtt.com";
        String clientidd = "tester";
        MemoryPersistence persistence = new MemoryPersistence();

        try{
            MqttClient client = new MqttClient(broker, clientidd, persistence);

            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setUserName("qxthyopl");
            connOpts.setPassword("VcMajZtxlQVJ".toCharArray());
            connOpts.setCleanSession(true);

            client.setCallback(new MqttCallback(){
                public void messageArrived(String topic, MqttMessage message){
                    System.out.println("Received message topic:" + topic);
                    System.out.println("Received message Qos:" + message.getQos());
                    System.out.println("Received message content:" + new String(message.getPayload()));

                }

                public void deliveryComplete(IMqttDeliveryToken token){
                    System.out.println("Delivery complete "+token.isComplete());

                }

                public void connectionLost(Throwable cause){
                    System.out.println("Connection Lost");
                    
                }
            });

            //establish a connection
            System.out.println("Connecting to "+broker);
            client.connect(connOpts);
            System.out.println("Connected");
            System.out.println("Publishing message");

            //subscribe
            client.subscribe(subTopic);

            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qoS);
            client.publish(pubTopic,message);
            System.out.println("Message published");

            client.disconnect();
            client.close();

        }catch(MqttException me){
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
            
        }
    }

    
    public class Gui extends JPanel{
        JFrame frame = new JFrame("Mqtt Dashboard");
        JPanel panel;
        Box connectionObjectsBox = new Box(BoxLayout.Y_AXIS);
        mqttClass mqttObj = new mqttClass();

        public void makeFrame(){
            BorderLayout layout = new BorderLayout();
            panel = new JPanel(layout);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JButton homeAddButton = new JButton("Add Connection Variables");
            homeAddButton.addActionListener(new AddConnectionObjectsListener());
            panel.add(BorderLayout.SOUTH, homeAddButton);;
            
            frame.getContentPane().add(BorderLayout.CENTER, panel);
            frame.setSize(400,400);
            frame.setVisible(true);
        }


        public class AddConnectionObjectsListener implements ActionListener{
            JTextField clientidField = new JTextField(20);
            JTextField serverField = new JTextField(20);
            JTextField portField = new JTextField(20);
            JTextField userField = new JTextField(20);
            JTextField passField = new JTextField(20);
            JFrame Connframe = new JFrame("Add Connection Variables");

            public void actionPerformed(ActionEvent e){
                Connframe = new JFrame("Add Connection Variables");
                JPanel ConnPanel = new JPanel();
                ConnPanel.setLayout(new BoxLayout(ConnPanel, BoxLayout.Y_AXIS));

                clientidField = new JTextField(20);
                serverField = new JTextField(20);
                portField = new JTextField(20);
                userField = new JTextField(20);
                passField = new JTextField(20);

                JLabel theClientID = new JLabel("Client ID");
                JLabel theServerLabel = new JLabel("Server");
                JLabel thePortLabel = new JLabel("Port");
                JLabel theUserLabel = new JLabel("Username");
                JLabel thePassLabel = new JLabel("Password");
                JButton saveButton = new JButton("Save variables");
                saveButton.addActionListener(new SaveVariablesListener());

                ConnPanel.add(theClientID);
                ConnPanel.add(clientidField);
                ConnPanel.add(theUserLabel);
                ConnPanel.add(userField);
                ConnPanel.add(thePassLabel);
                ConnPanel.add(passField);
                ConnPanel.add(theServerLabel);
                ConnPanel.add(serverField);
                ConnPanel.add(thePortLabel);
                ConnPanel.add(portField);
                ConnPanel.add(saveButton);
                Connframe.setSize(300,300);
                Connframe.getContentPane().add(BorderLayout.CENTER, ConnPanel);
                Connframe.setVisible(true);
            }

            public class SaveVariablesListener implements ActionListener{

                public void actionPerformed(ActionEvent e){
                    //saves the variables and adds them to the screen
                    mqttObj.clientId = clientidField.getText();
                    mqttObj.serverString = serverField.getText();
                    mqttObj.userName = userField.getText();
                    mqttObj.passWord = passField.getText();
                    connList.add(mqttObj);
                    temporaryList.add(mqttObj);     
                    Connframe.setVisible(false);
                    addConnectionObjectsToPanel();   
                }
            }

        }

        public void addConnectionObjectsToPanel(){
            for(mqttClass mqttObject : temporaryList){
                JButton connectionButton = new JButton(mqttObject.clientId);
                connectionButton.addActionListener(new ConnectionButtonListener());
                connectionObjectsBox.add(connectionButton);
            }
            temporaryList.clear();
            panel.add(BorderLayout.CENTER, connectionObjectsBox);
        }

        public class ConnectionButtonListener implements ActionListener{

            JFrame topicFrame;
            JPanel topicPanel;

            JTextField friendlyNameField;
            JTextField topicField;
            JTextField qosField;

            String MTopic;
            String MFriendlyName;
            String MQos;


            public void actionPerformed(ActionEvent e){
                makeObjectsGui();

            }

            public void makeObjectsGui(){
                JFrame objectsFrame = new JFrame("Sub-Dash");
                BorderLayout objectsLayout = new BorderLayout();
                JPanel objectsPanel = new JPanel(objectsLayout);
                JPanel northFramePanel = new JPanel();
                JTabbedPane thePJTabbedPane = new JTabbedPane();
                JComponent subscribePanel = new JPanel();
                JComponent publishPanel = new JPanel();
 
                JButton connectToBrokerButton = new JButton("Connect To Broker");
                connectToBrokerButton.addActionListener(new ConnectToBrokerListener());
                northFramePanel.add(connectToBrokerButton);

                JButton addTopic = new JButton("+ Topic");
				addTopic.addActionListener(new addTopicListener());
                northFramePanel.add(addTopic);
                
                objectsFrame.getContentPane().add(BorderLayout.NORTH, northFramePanel);

                //tabs for subscribe and publish
                thePJTabbedPane.add("Subscriptions Tab", subscribePanel);
                thePJTabbedPane.add("Publications Tab", publishPanel);
                objectsPanel.add(BorderLayout.CENTER, thePJTabbedPane);
                
                objectsFrame.getContentPane().add(BorderLayout.CENTER, objectsPanel);
                objectsFrame.setSize(400,300);
                objectsFrame.setVisible(true);
            }

            public class ConnectToBrokerListener implements ActionListener{
                public void actionPerformed(ActionEvent e){
                    mqttObj.connectToBroker();
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
                        topicFrame.setVisible(false);
                    }
                }
            }
        }

    }
}


class mqttClass{
    String clientId;
    String serverString;
    String port;
    String userName;
    String passWord;

    public void setClientId(String clientid){
        clientId = clientid;

    }
    public void setServerString(String server){
        serverString = server;
    }
    public void setPort(String Serverport){
        port = Serverport;

    }
    public void setUsername(String user){
        userName = user;
    }

    public void setPassword(String pass){
        passWord = pass;
    }




    public void connectToBroker(){

        // MqttDashboard mqtt = new MqttDashboard();
        // MqttDashboard.Gui gui = mqtt.new Gui();
        // gui.makeFrame();

    }

    public void subsribeToTopic(int qos){
        

    }

    public void publishToTopic(int qos){

    }
}

//java com.jadayoIscariot.MQTTDashboard
	//javac -d ../Classes com/jadayoIscariot/MQTTDashboard.java



