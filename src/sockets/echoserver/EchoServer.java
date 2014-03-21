package sockets.echoserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class EchoServer {

	public static void start() {
		System.out.println("Subindo servidor...");
		int porta = 60000;
		byte[] buffer = new byte[1000];
		DatagramPacket pacoteRecebido = new DatagramPacket(buffer,
				buffer.length);
		DatagramSocket server = null;
		try {
			server = new DatagramSocket(porta);
			server.setSoTimeout(5000);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (true) {
			try {
				server.receive(pacoteRecebido);
//				String conteudo = new String(pacoteRecebido.getData());
//				System.out.println(conteudo);
				pacoteRecebido.setPort(server.getLocalPort() + 1);
				server.send(pacoteRecebido);
			} catch (SocketTimeoutException e) {
				// caso solte um sockettimeoutexception, sai do loop, nao tem
				// mais pacotes pra receber
//				e.printStackTrace();
				break;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Fechando servidor...");
		server.close();
	}
	public static void main(String[] args) {
		start();
	}
}
