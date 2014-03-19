package sockets.echoserver;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Main {
	public static void main(String[] args) {
		DatagramSocket analisador = null;
		int porta = 60000;
		int pacotes = 1000;
		try {

			EchoServer es = new EchoServer(porta);

			InetAddress localhost = InetAddress.getLocalHost();
			DatagramSocket client = new DatagramSocket();
			DatagramPacket pacoteEnviado;

			// gerar pacotes e enviá-los
			for (int i = 0; i < pacotes; i++) {
				byte[] dados = Integer.toString(i).getBytes();
				pacoteEnviado = new DatagramPacket(dados, dados.length, localhost, porta);

				client.send(pacoteEnviado);

			}
			analisador = new DatagramSocket(porta + 1);
			es.ouvir();
			es.fechar();
			client.close();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		byte[] buffer = new byte[1000];
		DatagramPacket pacoteRecebido = new DatagramPacket(buffer, buffer.length);
		int valorAnterior = -1;
		int pacotesRecebidos = 0;
		boolean mudouOrdem = false;
		try {
			analisador.setSoTimeout(1000);
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// analisar os pacotes enviados pelo servidor de eco
		while (true) {
			try {
				analisador.receive(pacoteRecebido);
			} catch (IOException e) {
				break;
			}
			pacotesRecebidos++;
			String conteudo = new String(pacoteRecebido.getData(), 0, pacoteRecebido.getLength());
			int valor = Integer.parseInt(conteudo);
			if (valor <= valorAnterior) {
				mudouOrdem = true;
			}
			valorAnterior = valor;

		}
		analisador.close();
		System.out.println("Pacotes enviados: " + pacotes);
		System.out.println("Pacotes recebidos: " + pacotesRecebidos);
		System.out.println("Mudou ordem de entrega do recebimento? " + mudouOrdem);

	}
}
