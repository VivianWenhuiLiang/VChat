package com.vivian.java.vchat;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;

public class ServerChat {

	public static void startChatThreads(Socket socketA, Map<Integer, PrintWriter> map) {
		SocketReceiveSendRun aToB = new SocketReceiveSendRun(socketA, map);
		new Thread(aToB).start();
	}

	public static class SocketReceiveSendRun implements Runnable {
		Socket receiveSocket;
		Map<Integer, PrintWriter> map;

		public SocketReceiveSendRun(Socket receiveSocket, Map<Integer, PrintWriter> map) {
			this.receiveSocket = receiveSocket;
			this.map = map;
		}
		@SuppressWarnings("resource")
		@Override
		public void run() {
			Scanner socketIn = null;

			try {
				socketIn = new Scanner(receiveSocket.getInputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			while (true) {
				int toId = socketIn.nextInt();
				String line = socketIn.nextLine();

				PrintWriter writer = map.get(toId);
				writer.println(line);
			}
		}
	}
}
