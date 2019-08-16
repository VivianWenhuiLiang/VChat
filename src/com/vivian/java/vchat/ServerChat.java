package com.vivian.java.vchat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ServerChat {

	public static void startChatThreads(Socket socketA, Map<Integer, PrintWriter> map,
			Map<Integer, Queue<String>> leftMessageMap) {
		SocketReceiveSendRun aToB = new SocketReceiveSendRun(socketA, map, leftMessageMap);
		new Thread(aToB).start();
	}

	public static class SocketReceiveSendRun implements Runnable {
		Socket receiveSocket;
		Map<Integer, PrintWriter> map;
		Map<Integer, Queue<String>> leftMessageMap;

		public SocketReceiveSendRun(Socket receiveSocket, Map<Integer, PrintWriter> map,
				Map<Integer, Queue<String>> leftMessageMap) {
			this.receiveSocket = receiveSocket;
			this.map = map;
			this.leftMessageMap = leftMessageMap;
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
				if (writer == null) {
					Queue<String> offlineMessageQueue = leftMessageMap.get(toId);
					if (offlineMessageQueue == null) {
						offlineMessageQueue = new ConcurrentLinkedQueue<>();
						leftMessageMap.put(toId, offlineMessageQueue);
					}
					offlineMessageQueue.offer(line);
				} else {
					writer.println(line);
				}
			}
		}
	}
}
