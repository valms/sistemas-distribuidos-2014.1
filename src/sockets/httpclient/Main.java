package sockets.httpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {
	public static void main(String[] args) {
		Socket socket;
		try {
			socket = new Socket();
			socket.connect(new InetSocketAddress("www.amazon.com", 80), 1000);
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			sendCommand(out, in, "GET", "/index.html");
			sendCommand(out, in, "POST", "/index.html");
			sendCommand(out, in, "TRACE", "/index.html");
			sendCommand(out, in, "HEAD", "/index.html");

			out.close();
			in.close();
			socket.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void sendCommand(PrintWriter out, BufferedReader in, String metodo, String recurso) throws InterruptedException,
			IOException {
		// recursos começam com barra
		out.write(metodo + " " + recurso + " HTTP/1.0\r\n\n");
		// só envia se der flush
		out.flush();

		char[] buffer = new char[1000];
		// espera 1 segundo antes de verificar o buffer de entrada
		Thread.sleep(1000);
		if (in.ready()) {
			in.read(buffer);
			System.out.println("Buscando recurso "+recurso+" com método "+metodo);
			System.out.println(buffer);
			System.out.println("--------------------------------------------------");
		}
	}
}
