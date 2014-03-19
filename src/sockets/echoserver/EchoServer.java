package sockets.echoserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class EchoServer {

	private DatagramSocket server;

	public EchoServer(int porta) throws SocketException {
		server = new DatagramSocket(porta);
	}

	public void ouvir() {

		byte[] buffer = new byte[1000];
		DatagramPacket pacoteRecebido = new DatagramPacket(buffer, buffer.length);
		try {
			server.setSoTimeout(1000);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (true) {

			try {
				server.receive(pacoteRecebido);
				String conteudo = new String(pacoteRecebido.getData());
				System.out.println(conteudo);
				pacoteRecebido.setPort(server.getLocalPort() + 1);
				server.send(pacoteRecebido);
			} catch (IOException e) {
				// caso solte um servertimeoutexception, sai do loop, não tem
				// mais pacotes pra receber
				break;
			}

		}
	}

	public void fechar() {
		server.close();
	}
}
