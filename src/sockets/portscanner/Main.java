package sockets.portscanner;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class Main {
	public static void main(String[] args) {
		Socket socket;

		for (int i = 80; i < 100; i++) {
			try {

				socket = new Socket();
				// O número no connect indica o timeout em milissegundos
				socket.connect(new InetSocketAddress("www.amazon.com", i), 1000);
				socket.close();
				System.out.println("A porta " + i + " aguarda conexões TCP.");

			} catch (UnknownHostException e) {

				e.printStackTrace();
			} catch (SocketTimeoutException e) {

				System.out.println("A porta " + i + " não aguarda conexões TCP.");
				continue;
			} catch (ConnectException e) {

				System.out.println("A porta " + i + " não aguarda conexões TCP.");
				continue;
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

	}
}
