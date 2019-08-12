package com.vivian.java.vchat;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientChat {
	public static void startChatThreads(Socket socket) {
		SocketReceiveRun receiveRun = new SocketReceiveRun(socket);
		new Thread(receiveRun).start();
		SocketSendRun sendRun=new SocketSendRun(socket);
		new Thread(sendRun).start();
	}
	
	public static class SocketSendRun implements Runnable {
		private PrintWriter socketOut = null;
		Socket socket;

		public SocketSendRun(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try {
				socketOut = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			Scanner in = new Scanner(System.in);
			while (true) {
				String userInput = in.nextLine();
				if ("stop".equals(userInput)) {
					in.close();
					return;
				}
				socketOut.println(userInput);
			}
		}
	}

	public static class SocketReceiveRun implements Runnable {
		Socket socket;

		public SocketReceiveRun(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			BufferedReader socketIn = null;
			try {
				socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			while (true) {
				try {
					String line = socketIn.readLine();
					System.out.println(socket.getInetAddress() + " > " + line);
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}
			}
		}
	}
}
