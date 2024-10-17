package dsem.mqtt4j.mqtt_broker;

import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;
import dsem.mqtt4j.global.*;
import dsem.mqtt4j.global.structure.*;

public class MQTTBroker {
	private int port;
	private static HashMap<String, ArrayList<Connection>> map;

	public MQTTBroker() {		
		port = GlobalConfig.default_broker_port;
		map = new HashMap<String, ArrayList<Connection>>();
	}

	public void startBroker() {
		try (ServerSocket serverSocket = new ServerSocket(this.port)) {
			System.out.println("MQTT Broker started");

			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println("Connection Requested.");

				Connection clientConn = new Connection(socket);
				
				SubscriberManager sm = new SubscriberManager(clientConn, map);
				sm.start();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		new MQTTBroker().startBroker();
	}
}
