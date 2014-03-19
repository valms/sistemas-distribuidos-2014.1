package sockets.echoserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class EchoServer {

	private DatagramSocket server;

	public EchoServer(int porta) throws SocketException {
		server = new DatagramSocket(porta);
	}

	public void ouvir() {

		//aumentar o buffer caso queira receber e ecoar mais pacotes
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
			} catch (SocketTimeoutException e) {
				// caso solte um sockettimeoutexception, sai do loop, não tem
				// mais pacotes pra receber
				break;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public void fechar() {
		server.close();
	}
}
