package com.vivian.java.vchat;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class VServer {

	public static void main(String[] args) throws IOException {
		Map<Integer, PrintWriter> map = new HashMap<>();
		Map<Integer, Queue<String>> leftMessageMap = new ConcurrentHashMap<>();
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		System.out.println("what is the port will server be on? ");
		int port = in.nextInt();
		int name = 0;

		try (ServerSocket serverSocket = new ServerSocket(port);) {// try with results(the scourse is closeAble), finish
			while (true) {
				Socket socket = serverSocket.accept();// clientA
				System.out.println("connetion A & accepted!");
				System.out.println(socket.getInetAddress() + "has connected!");
				PrintWriter socketOut = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
				Queue<String> offlineMessages = leftMessageMap.get(name);
				while (offlineMessages != null && !offlineMessages.isEmpty()) {
					socketOut.println(offlineMessages.poll());
				}
				
				map.put(name++, socketOut);
				ServerChat.startChatThreads(socket, map, leftMessageMap);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		} finally {
		}
	}
}
