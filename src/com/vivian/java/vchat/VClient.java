package com.vivian.java.vchat;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class VClient {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws UnknownHostException, IOException {
		Scanner in = new Scanner(System.in);
		System.out.println("what is the URL?");
		String url = in.nextLine();
		System.out.println("what is the port number will the server be on?");
		int port = in.nextInt();
		Socket socket = new Socket(url, port);
		ClientChat.startChatThreads(socket);
	}
}
